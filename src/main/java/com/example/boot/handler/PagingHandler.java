package com.example.boot.handler;
//PagingHandler
import com.example.boot.dto.BoardDTO;
import com.example.boot.entity.Board;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@ToString
public class PagingHandler{
    private int totalPage;
    private long totalElement;
    private int pageNo;
    private int startPage;
    private int endPage;
    private boolean prev, next;
    private List<BoardDTO> list;

    public PagingHandler(Page<BoardDTO> list, int pageNo) {
        this.totalPage = list.getTotalPages();
        this.totalElement = list.getTotalElements();
        this.pageNo = pageNo;
        this.list = list.getContent(); // DTO → Entity 변환 필요 시

        // 페이지 블록 계산 (예: 10개 단위)
        this.endPage = (int) Math.ceil(this.pageNo / 10.0) * 10;
        this.startPage = this.endPage - 9;

        if (this.endPage > this.totalPage) {
            this.endPage = this.totalPage;
        }


        this.prev = startPage > 1;
        this.next = endPage < totalPage;
    }



}
