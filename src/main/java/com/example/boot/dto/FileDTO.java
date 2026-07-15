package com.example.boot.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDTO {
    private String uuid;
    private String saveDir;
    private String fileName;
    private int fileType;
    private Long bno;
    private Long fileSize;
    private LocalDateTime regDate, modDate;

    public String getRegDate() {
        return regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getModDate() {
        return modDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}





//package com.example.boot.dto;
////FileDTO
//import lombok.*;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class FileDTO {
//    private String uuid;
//    private String saveDir;
//    private String fileName;
//    private int fileType;
//    private Long bno;
//    private Long fileSize;
//    private LocalDateTime regDate, modDate;
//
////    public String getRegDate(){
////        return regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
////    }
////
////    public String getModDate(){
////        return modDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
////    }
//}
