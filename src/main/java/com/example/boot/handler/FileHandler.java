package com.example.boot.handler;

import com.example.boot.dto.FileDTO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component//내가 만든 객체를 스프링의 우레임워크에 반으로 설정하는 이노테이션.
public class FileHandler {
    private final String UP_DIR = "C:\\web_260315_kim\\_myProject\\java\\_fileUpload";
    public List<FileDTO> uploadFile(MultipartFile[] files) {
        List<FileDTO> fileDTOList = new ArrayList<>();

        //날짜 형태의 폴더 구성 ex 2026/07/07같이....
        //해당 날짜로 폴더를 구성하고싶단말이지이????
        LocalDate date = LocalDate.now(); //>>>>> 폴더의 경로로 바꾸고싶다
        String today = date.toString().replace("-", File.separator);
        File folders = new File(UP_DIR, today); //경로 + 파일이름

        //해당 폴더가 없으면 생성을 하고 있으면 생성을 안하기...
        //mkdir(폴더를 1개만 만듬. )  mkdirs하위폴더까지 싹다 생성함.
        if(!folders.exists()){
            folders.mkdirs();
        }

        //파일 정보 생성 FileDTO
        for(MultipartFile file: files){
            //file의 name size type saveDir(경로임)
            FileDTO fileDTO = new FileDTO();
            log.info("file >> {}", file.getName());
            log.info("file >> {}", file.getOriginalFilename());

            fileDTO.setFileName(file.getOriginalFilename());
            fileDTO.setFileSize(file.getSize());
            fileDTO.setFileType(file.getContentType().startsWith("image")?1:0);
            fileDTO.setSaveDir(today);


            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString();
            fileDTO.setUuid(uuidString);

            fileDTOList.add(fileDTO);
            //----------------------------------
            //저장
            String fileName = uuidString + "_" + file.getOriginalFilename();
            String fileThName = uuidString + "_th_" + file.getOriginalFilename();

            //살 저장 파일 객체
            // D: ~fileNAme
            File storeFile = new File(folders, fileName);

            //저장
            try {
                file.transferTo(storeFile);
                //썸네일은 그림파일만
                if(fileDTO.getFileType()==1){
                    File thumbnail = new File(folders, fileThName);
                    Thumbnails.of(storeFile)
                            .size(100, 100)
                            .toFile(thumbnail);
                }

            } catch (Exception e) {
                log.info("file save Error~!!");
                e.printStackTrace();
            }


        }


        return fileDTOList;
    }
}
