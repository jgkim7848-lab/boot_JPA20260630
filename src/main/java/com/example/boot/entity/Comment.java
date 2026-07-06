package com.example.boot.entity;
//Comment
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class Comment extends TimeBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;
    @Column(nullable = false)
    private Long bno;
    @Column(length = 100, nullable = false)
    private String writer;
    private String content;




}
