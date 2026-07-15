package com.example.boot.entity;
//Board

import jakarta.persistence.*;
import lombok.*;
/*@Table(name=""   박으면 테이블 생성시 테이블 이름변경이 가능해진다.
*일반적으로 클래스 명이 테이블 명으로 적용된다.
*
* @Entity  DB테이블 매핑 클래스
*
* DTO  객체 생성 클래스 화면용임.
*
* JPA Auditing   regdate moddate같은 모든 테이블에 동일하게 적용되는 칼럼으로 별도 관리.   base class(super class)로 관리함.
*
* @id  > primarykey
* 기본키 생성 전략 GeneratedValue
* auto_increment <>>>> GenerationType.Identity
* */


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="board")
public class Board extends TimeBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bno;

    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 100, nullable = false)
    private String writer;
    @Column(length = 2000, nullable = false)
    private String content;

    @Column(name="read_count", columnDefinition="int default 0")
    private int readCount;
    @Column(name="cmt_qty", columnDefinition="int default 0")
    private int cmtQty;
    @Column(name="file_qty", columnDefinition="int default 0")
    private int fileQty;

}
