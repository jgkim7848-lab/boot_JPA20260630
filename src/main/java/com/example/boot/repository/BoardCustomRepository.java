package com.example.boot.repository;

import com.example.boot.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {
    //pageable  type   keyword   >>> page<baord>
    Page<Board> searchBoard(String type, String keyword, Pageable pageable);


}
