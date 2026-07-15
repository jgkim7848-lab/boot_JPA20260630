package com.example.boot.handler;

import com.example.boot.dto.FileDTO;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class FileRemoveHandler {
    private final String DIR = "D:\\web_260316_omr\\_myProject\\_java\\_fileUpload";

    public boolean removeFile(FileDTO fileDTO){
        // file.delete() => 파일 삭제
        // 이미지라면 썸네일도 같이 삭제
        // 파일을 삭제 하려면 삭제할 경로 D: ~ 파일명.확장자  .delete()

        File fileDir = new File(DIR, fileDTO.getSaveDir());

        String deleteFile = fileDTO.getUuid()+"_"+fileDTO.getFileName();
        String deleteThFile = fileDTO.getUuid()+"_th_"+fileDTO.getFileName();

        File removeFile = new File(fileDir, deleteFile);
        File removeThFile = new File(fileDir, deleteThFile);

        boolean isDel = false;

        try {
            // 파일이 존재하는지 확인
            if(removeFile.exists()){
                isDel = removeFile.delete(); // 삭제
                log.info("file remove success >> {}", removeFile);
                if(isDel && fileDTO.getFileType() == 1 && removeThFile.exists()){
                    isDel = removeThFile.delete();
                    log.info("Thfile remove success >> {}", removeThFile);
                }
            }
        } catch (Exception e) {
            log.info("file remove Error~!!");
            e.printStackTrace();
        }
        return isDel;
    }
}