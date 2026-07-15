package com.example.boot.handler;

import com.example.boot.dto.FileDTO;
import com.example.boot.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 스케줄링 사용
@EnableScheduling
@Slf4j
@Component
@RequiredArgsConstructor
public class FileSweeper {
    // 매일 정해진 시간에 스케줄러 실행
    // 매일 해당 날짜의 경로에 DB의 데이터와 폴더안의 파일이 일치하는지 비교
    // DB == file 일치하면 남기고, DB != 폴더 삭제

    private final BoardService boardService;
    private final String BASE_PATH = "D:\\web_260316_omr\\_myProject\\_java\\_fileUpload\\";

    // cron 방식 : 초 분 시 일 월 요일 년도(생략가능)
    // 스프링(Spring) 기반 환경: 초(0~59) 분(0~59) 시(0~23) 일(1~31) 월(1~12) 요일(0~7)
    @Scheduled(cron = "00 29 11 * * * ")
    public void fileSweeper(){
        log.info(">>>>> fileSweeper Start >> {}", LocalDateTime.now());
        // DB에 등록된 파일 리스트 가져오기 (오늘날짜 폴더만)
        // 오늘날짜의 경로가 필요
        LocalDate now = LocalDate.now();
        String today = now.toString().replace("-", File.separator);

        // DB에서 today == saveDir 일치하는 값만 가져오기
        // select * from file where save_dir = today
        List<FileDTO> dbFileList = boardService.getTodayFileList(today);
        log.info(">>> dbFileList >> {}", dbFileList);

        List<String> currFile = new ArrayList<>();
        for(FileDTO fileDTO : dbFileList){
            String fileName = BASE_PATH + today + File.separator + fileDTO.getUuid()+"_"+fileDTO.getFileName();
            currFile.add(fileName);
            // 이미지 파일이라면 썸네일도 추가
            if(fileDTO.getFileType() == 1){
                String fileThName = BASE_PATH + today + File.separator + fileDTO.getUuid()+"_th_"+fileDTO.getFileName();
                currFile.add(fileThName);
            }
        }
        log.info(">>> currFile >> {}", currFile);

        // today 경로 기반 저장된 파일 검색
        // D:\web_260316_omr\_myProject\_java\_fileUpload\2026\07\08
        File dir = Paths.get(BASE_PATH+today).toFile();

        // 해당 경로 안에 있는 파일을 가져오기 (배열로 리턴)
        File[] allFileObject = dir.listFiles();

        // allFileObject, currFile 비교하여 DB에 존재하지 않는 파일은 삭제
        // DB가 기준
        for(File file: allFileObject){
            String storedFileName = file.toPath().toString();
            if(!currFile.contains(storedFileName)){
                // currFile 안에 storedFileName이 존재하지 않는다면
                file.delete(); // 삭제
                log.info(">>> 삭제되는 파일이름 >> {}", storedFileName);
            }
        }

        log.info(">>>>> fileSweeper End >> {}", LocalDateTime.now());
    }
}




//package com.example.boot.handler;
//
//import com.example.boot.dto.FileDTO;
//import com.example.boot.service.BoardService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.ssl.SslProperties;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.nio.file.Paths;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//// 스케줄링 사용
//@EnableScheduling
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class FileSweeper {
//    // 매일 정해진 시간에 스케줄러 실행
//    // 매일 해당 날짜의 경로에 DB의 데이터와 폴더안의 파일이 일치하는지 비교
//    // DB == file 일치하면 남기고, DB != 폴더 삭제
//
//    private final BoardService boardService;
//    private final String BASE_PATH = "C:\\web_260315_kim\\_myProject\\java\\_fileUpload\\";
//
//    // cron 방식 : 초 분 시 일 월 요일 년도(생략가능)
//    // 스프링(Spring) 기반 환경: 초(0~59) 분(0~59) 시(0~23) 일(1~31) 월(1~12) 요일(0~7)
//    @Scheduled(cron = "00 24 15 * * * ")
//    public void fileSweeper(){
//        log.info(">>>>> fileSweeper Start >> {}", LocalDateTime.now());
//        // DB에 등록된 파일 리스트 가져오기 (오늘날짜 폴더만)
//        // 오늘날짜의 경로가 필요
//        LocalDate now = LocalDate.now();
//        String today = now.toString().replace("-", File.separator);
//
//        // DB에서 today == saveDir 일치하는 값만 가져오기
//        // select * from file where save_dir = today
//        List<FileDTO> dbFileList = boardService.getTodayFileList(today);
//        log.info(">>> dbFileList >> {}", dbFileList);
//
//        List<String> currFile = new ArrayList<>();
//        for(FileDTO fileDTO : dbFileList){
//            String fileName = BASE_PATH + today + File.separator + fileDTO.getUuid()+"_"+fileDTO.getFileName();
//            currFile.add(fileName);
//            // 이미지 파일이라면 썸네일도 추가
//            if(fileDTO.getFileType() == 1){
//                String fileThName = BASE_PATH + today + File.separator + fileDTO.getUuid()+"_th_"+fileDTO.getFileName();
//                currFile.add(fileThName);
//            }
//        }
//        log.info(">>> currFile >> {}", currFile);
//
//        // today 경로 기반 저장된 파일 검색
//        // D:\web_260316_omr\_myProject\_java\_fileUpload\2026\07\08
//        File dir = Paths.get(BASE_PATH+today).toFile();
//
//        // 해당 경로 안에 있는 파일을 가져오기 (배열로 리턴)
//        File[] allFileObject = dir.listFiles();
//
//        // allFileObject, currFile 비교하여 DB에 존재하지 않는 파일은 삭제
//        // DB가 기준
//        for(File file: allFileObject){
//            String storedFileName = file.toPath().toString();
//            if(!currFile.contains(storedFileName)){
//                // currFile 안에 storedFileName이 존재하지 않는다면
//                file.delete(); // 삭제
//                log.info(">>> 삭제되는 파일이름 >> {}", storedFileName);
//            }
//        }
//
//        log.info(">>>>> fileSweeper End >> {}", LocalDateTime.now());
//    }
//}