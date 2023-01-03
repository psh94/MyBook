package com.psh.mybook.task;

import com.psh.mybook.mapper.ImageMapper;
import com.psh.mybook.model.book.AttachImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class AttachFileCheckTask {

    // 경로 상의 이미지와 같은 썸네일의 이미지가 서로 존재하는지 여부를 확인한다.

    private final ImageMapper imageMapper;

    private String getFolderYesterDay() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -1);

        String str = sdf.format(cal.getTime());

        return str.replace("-", File.separator);
    }

    @Scheduled(cron="0 * * * * *") // 1분마다 실행
    public void checkFiles() throws Exception{

        log.warn("File Check Task Run..........");
        log.warn(String.valueOf(new Date()));
        log.warn("========================================");

        // DB에 저장된 파일 리스트
        List<AttachImage> fileList = imageMapper.checkFileList();


        // 비교 기준 파일 리스트(Path객체)
        List<Path> checkFilePath = new ArrayList<Path>();

        //원본 이미지
        fileList.forEach(vo -> {
            Path path = Paths.get("C:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName());
            checkFilePath.add(path);
        });

        //썸네일 이미지
        fileList.forEach(vo -> {
            Path path = Paths.get("C:\\upload", vo.getUploadPath(), "s_" +  vo.getUuid() + "_" + vo.getFileName());
            checkFilePath.add(path);
        });


        // 디렉토리 파일 리스트

        // 2. Path 객체를 얻은 후, toFile()을 사용 File객체로 변환
        // 3. 디렉토리에 저장된 정보를 얻기 위해 File.listFile()를 사용
        File targetDir = Paths.get("C:\\upload", getFolderYesterDay()).toFile();
        File[] targetFile = targetDir.listFiles();


        // removeFileList에는 삭제할 file list를 담아둔다.
        // removeFileList에는 targetFile이 담겨 있다.
        // 이를 checkFile과 비교해 equals가 성립한다면 removeFileList에서 제거한다. (제거 목록에서 빠짐)
        List<File> removeFileList = new ArrayList<File>(Arrays.asList(targetFile));

        for(File file : targetFile){
            checkFilePath.forEach(checkFile ->{
                if(file.toPath().equals(checkFile))
                    removeFileList.remove(file);
            });
        }

        // removeFileList에 담겨있는 것들을 제거한다.
        log.warn("file Delete : ");
        for(File file : removeFileList) {
            file.delete();
        }

        log.warn("========================================");

    }


}