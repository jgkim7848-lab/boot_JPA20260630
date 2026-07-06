package com.example.boot.dto;
//CommentDTO
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private Long cno;
    private Long bno;
    private String writer;
    private String content;
    private LocalDateTime regDate, modDate;


    public String getRegDate(){
        return regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getModDate(){
        return modDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}


