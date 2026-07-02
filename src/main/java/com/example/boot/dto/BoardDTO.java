package com.example.boot.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {
    private long bno;
    private String title;
    private String writer;
    private String content;
    private int readCount;
    private int cmtQty;
    private int fileQty;
    private LocalDateTime regDate, modDate;



}
