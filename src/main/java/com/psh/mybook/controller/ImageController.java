package com.psh.mybook.controller;

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


@RestController
@Slf4j
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageMapper imageMapper;

    /* 첨부 파일 업로드 */
    @PostMapping("/admin/uploadImage")
    public ResponseEntity<List<AttachImage>> uploadImage(MultipartFile[] uploadFile) {

        /* 이미지 파일 체크 */
        for (MultipartFile multipartFile : uploadFile) {

            File checkfile = new File(multipartFile.getOriginalFilename());
            String type = null;

            try {

                type = Files.probeContentType(checkfile.toPath());
                log.info("MIME TYPE : " + type);

            } catch (IOException e) {

                e.printStackTrace();

            }

            if (!type.startsWith("image")) {

                List<AttachImage> list = null;
                return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);

            }

        }

        String uploadFolder = "C:\\upload";


        /* 날짜 폴더 경로 */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();

        String str = sdf.format(date);

        String datePath = str.replace("-", File.separator);


        /* 폴더 생성 */
        File uploadPath = new File(uploadFolder, datePath);

        if (uploadPath.exists() == false) {
            uploadPath.mkdirs();
        }

        /* 이미저 정보 담는 객체 */
        List<AttachImage> list = new ArrayList();

        for (MultipartFile multipartFile : uploadFile) {

            AttachImage attachImage = new AttachImage();

            /* 파일 이름 */
            String uploadFileName = multipartFile.getOriginalFilename();
            attachImage.setFileName(uploadFileName);
            attachImage.setUploadPath(datePath);

            /* uuid 적용 파일 이름 */
            String uuid = UUID.randomUUID().toString();
            attachImage.setUuid(uuid);

            uploadFileName = uuid + "_" + uploadFileName;

            /* 파일 위치, 파일 이름을 합친 File 객체 */
            File saveFile = new File(uploadPath, uploadFileName);

            /* 파일 저장 */
            try {

                File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);

                BufferedImage bo_image = ImageIO.read(saveFile);

                //비율
                double ratio = 3;
                //넓이 높이
                int width = (int) (bo_image.getWidth() / ratio);
                int height = (int) (bo_image.getHeight() / ratio);


                Thumbnails.of(saveFile)
                        .size(width, height)
                        .toFile(thumbnailFile);

            } catch (Exception e) {
                e.printStackTrace();
            }

            list.add(attachImage);
        }
        ResponseEntity<List<AttachImage>> result = new ResponseEntity<List<AttachImage>>(list, HttpStatus.OK);

        return result;
    }

    // 이미지 출력
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

    /* 이미지 정보 반환 */
    @GetMapping("/getAttachList")
    public ResponseEntity<List<AttachImage>> getAttachList(int bookId){

        log.info("getAttachList = {}" + bookId);

        return new ResponseEntity<List<AttachImage>>(imageMapper.getAttachList(bookId), HttpStatus.OK);

    }




}
