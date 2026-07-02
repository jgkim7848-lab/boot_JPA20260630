package com.example.boot.service;

import com.example.boot.dto.BoardDTO;
import com.example.boot.entity.Board;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardService {
    //
    /* interafce 추상 메서드만 가능한 객체
    default method 인터페이스에서 규칙을 잡거나 로직을 잡을때 사용
    호환성 유지



    * DTO - 화면용. Entity DB저장용
    convert DTO  > entity    entity > DTO
    boardDTO >> board  로 변환해야됨.

    BoardDto   bno title writer content readCount cmtQty fileQty  regdate moddate
    근데 Board(entity)는 보드Dto만큼 필요한게 많은게 아니란 말이지....이거 신경서서 변환쳐야됨.
    * */
    default Board convertDtoToEntity(BoardDTO boardDTO){
        return Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .writer(boardDTO.getWriter())
                .content(boardDTO.getContent())
                .readCount(boardDTO.getReadCount())
                .cmtQty(boardDTO.getCmtQty())
                .fileQty(boardDTO.getFileQty())
                .build();
    }

    //반대 케이스 DB
    default BoardDTO convertEntityToDto(Board board){

        return BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .writer(board.getWriter())
                .content(board.getContent())
                .readCount(board.getReadCount())
                .cmtQty(board.getCmtQty())
                .fileQty(board.getFileQty())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();
    }


//paging 없는 리스트 메서드
    Long insert(BoardDTO boardDTO);


    List<BoardDTO> getList();

    BoardDTO getDetail(Long bno);


    void update(BoardDTO boardDTO);

    void delete(Long bno);

    Page<BoardDTO> getList(int pageNo);
}
