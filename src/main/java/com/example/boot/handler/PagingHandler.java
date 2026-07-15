package com.example.boot.handler;

import com.example.boot.dto.BoardDTO;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.util.List;


@ToString
@Getter
public class PagingHandler {
    private int startPage;
    private int endPage;
    private int totalPage; // realEndPage
    private long totalElement; // 전체 게시글 수 => return long
    private int pageNo;
    private boolean prev, next;

    private List<BoardDTO> list;

    private String type;
    private String keyword;

    public PagingHandler(Page<BoardDTO> list, int pageNo){
        this.list = list.getContent(); // page에서 list만 가져올 때
        this.pageNo = pageNo;
        this.totalPage = list.getTotalPages();
        this.totalElement = list.getTotalElements();

        // endPage 구하는 공식 1~10까지 => 10  11~20 => 20
        // pageNo / 10.0 => 0.1 (올림) => 1 * 10
        this.endPage = (int)Math.ceil(this.pageNo / 10.0)*10;
        this.startPage = this.endPage - 9;

        this.endPage = (this.endPage > this.totalPage) ? totalPage : endPage;

        this.prev = startPage > 1;
        this.next = endPage < totalPage;
    }

    public PagingHandler(Page<BoardDTO> list, int pageNo, String type, String keyword){
        this(list,pageNo);
        this.type = type;
        this.keyword = keyword;
    }

}





//package com.example.boot.handler;
//
//import com.example.boot.dto.BoardDTO;
//import lombok.Getter;
//import lombok.ToString;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Component;
//
//import java.security.PrivateKey;
//import java.util.List;
//
//
//@ToString
//@Getter
//public class PagingHandler {
//    private int startPage;
//    private int endPage;
//    private int totalPage; // realEndPage
//    private long totalElement; // 전체 게시글 수 => return long
//    private int pageNo;
//    private boolean prev, next;
//
//    private List<BoardDTO> list;
//
//    private String type;
//    private String keyword;
//
//    public PagingHandler(Page<BoardDTO> list, int pageNo){
//        this.list = list.getContent(); // page에서 list만 가져올 때
//        this.pageNo = pageNo;
//        this.totalPage = list.getTotalPages();
//        this.totalElement = list.getTotalElements();
//
//        // endPage 구하는 공식 1~10까지 => 10  11~20 => 20
//        // pageNo / 10.0 => 0.1 (올림) => 1 * 10
//        this.endPage = (int)Math.ceil(this.pageNo / 10.0)*10;
//        this.startPage = this.endPage - 9;
//
//        this.endPage = (this.endPage > this.totalPage) ?
//                totalPage : endPage;
//
//        this.prev = startPage > 1;
//        this.next = endPage < totalPage;
//    }
//
//    public PagingHandler(Page<BoardDTO> list, int pageNo, String type, String keyword){
//        this(list,pageNo);
//        this.type = type;
//        this.keyword = keyword;
//    }
//
//}