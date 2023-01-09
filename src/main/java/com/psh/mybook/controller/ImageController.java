package com.psh.mybook.controller;

import com.psh.mybook.annotation.LoginRequired;
import com.psh.mybook.mapper.ImageMapper;
import com.psh.mybook.model.book.AttachImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.psh.mybook.utill.HttpResponses.*;


@RestController
@Slf4j
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageMapper imageMapper;

    // --------------------첨부파일 업로드------------------------------------------------------------------------
    // MultipartFile : view에서 전송한 multipart 타입의 파일을 다룰 수 있도록 해줌(스프링에서 제공)
    @PostMapping("/upload")
    @LoginRequired
    public ResponseEntity<List<AttachImage>> uploadImage(MultipartFile[] uploadFile) {

        //--------------- 이미지 파일 체크 ---------------------------
        for (MultipartFile multipartFile : uploadFile) {

            // 뷰로부터 전달받은 파일 이름을 그대로 사용
            File checkfile = new File(multipartFile.getOriginalFilename());
            String type = null;

            try {
                // probeContentType : MIME TYPE을 반환해준다.
                // MIME TYPE : 파일이 어떤 종류의 파일인지에 대한 정보가 담긴 라벨
                type = Files.probeContentType(checkfile.toPath());

            } catch (IOException e) {

                e.printStackTrace();
                return RESPONSE_CONFLICT;
            }

            //타입이 image가 아니면
            if (!type.startsWith("image")) {

                List<AttachImage> list = null;
                return RESPONSE_BAD_REQUEST;

            }
        }


        //---------------- 폴더 생성 ---------------------------
        
        // 해당 폴더에 이미지가 저장된다.

        String uploadFolder = "C:\\upload";


        // C:\\upload의 하위 폴더로 yyyy/MM/dd의 이름을 가진 폴더를 생성하자.

        /* 날짜 폴더 경로 */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();

        String str = sdf.format(date);

        // 구분자 (-)를 File.separator로 바꾼다. ('-'를 경로 구분자인 '\'로 바꿔주어야 하기 때문에)
        String datePath = str.replace("-", File.separator);


        /* 폴더 생성 */
        // c:\\upload\yyyy\MM\dd 경로를 생성한다.
        File uploadPath = new File(uploadFolder, datePath);

        // 업로드 경로(uploadPath)가 존재하지 않으면 폴더 생성(mkdirs, mkdir은 단일 폴더 생성)
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }


        //---------------- 이미지 객체 생성 ----------------------------------------
        /* 이미저 정보 담는 객체 */
        List<AttachImage> list = new ArrayList();

        for (MultipartFile multipartFile : uploadFile) {

            AttachImage attachImage = new AttachImage();

            /* 파일 이름 */
            String uploadFileName = multipartFile.getOriginalFilename();
            attachImage.setFileName(uploadFileName);
            attachImage.setUploadPath(datePath);

            /* uuid 적용 파일 이름 */
            // 같은 이름을 갖게되면 덮어쓰기가 되기 때문에 고유 식별자인 uuid를 붙여준다.
            String uuid = UUID.randomUUID().toString();
            attachImage.setUuid(uuid);

            /* 새로운 uploadFileName */
            uploadFileName = uuid + "_" + uploadFileName;

            /* 파일 위치, 파일 이름을 합친 File 객체 */
            File saveFile = new File(uploadPath, uploadFileName);


            //---------------- 이미지 파일 저장, 썸네일 생성 --------------------------------
            /* 파일 저장 */
            try {
                // view에서 전달받은 파일을 지정한 폴더에 저장
                multipartFile.transferTo(saveFile);

                File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);

                // 썸네일 객체 생성
                BufferedImage bo_image = ImageIO.read(saveFile);

                //비율
                double ratio = 3;
                //넓이 높이
                int width = (int) (bo_image.getWidth() / ratio);
                int height = (int) (bo_image.getHeight() / ratio);

                // 썸네일 생성
               Thumbnails.of(saveFile)
                        .size(width, height)
                        .toFile(thumbnailFile);

            } catch (Exception e) {
                e.printStackTrace();
                return RESPONSE_CONFLICT;
            }

            // list에 객체들을 담는다.
            list.add(attachImage);
        }

        // 비동기 방식의 서버에서 데이터를 전송하기 위해 ResponseEntity 방식을 선택 (Http의 Body에 list 담아서 HttpStatus와 반환)
       ResponseEntity<List<AttachImage>> result = new ResponseEntity<List<AttachImage>>(list, HttpStatus.OK);

        return result;
    }



    // ---------------------------------------- 이미지 출력 ----------------------------------------
    // 이미지 파일은 바이너리 파일 범주에 들어가는데 바이너리 파일을 주고 받을 때에는 바이트 단위로 데이터를 주고 받는다. (자바의 최소 단위가 바이트)
    @GetMapping("/display")
    public ResponseEntity<byte[]> getImage(String fileName){

        File file = new File("c:\\upload\\" + fileName);

        ResponseEntity<byte[]> result = null;

        try {

            HttpHeaders header = new HttpHeaders();

            header.add("Content-type", Files.probeContentType(file.toPath()));

            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        }catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    // ---------------------------------------- 이미지 정보 반환 ----------------------------------------
    @GetMapping("/getAttachList")
    public ResponseEntity<List<AttachImage>> getAttachList(int bookId){

        log.info("getAttachList = {}" + bookId);

        return new ResponseEntity<List<AttachImage>>(imageMapper.getAttachList(bookId), HttpStatus.OK);

    }




}
