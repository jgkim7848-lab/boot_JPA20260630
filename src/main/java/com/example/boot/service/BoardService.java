package com.example.boot.service;

import com.example.boot.dto.BoardDTO;
import com.example.boot.dto.BoardFileDTO;
import com.example.boot.dto.FileDTO;
import com.example.boot.entity.Board;
import com.example.boot.entity.File;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardService {
    // interface 추상메서드만 가능한 객체
    // default method : 인터페이스에서 규칙을 잡거나, 로직을 잡거나 할 때 사용
    // 호환성 유지

    // DTO (화면용)  Entity (DB저장용)
    // convert DTO -> Entity   / Entity -> DTO
    // BoardDTO => board(Entity)  변환
    // BoardDTO : bno, title, writer, content, readCount, cmtQty, fileQty, regDate, modDate
    // Board(entity) : bno, title, writer, content, readCount, cmtQty, fileQty
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

    /* 반대 케이스 DB => 화면 */
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

    Long insert(BoardDTO boardDTO);

    // 페이징 없는 리스트 메서드
    List<BoardDTO> getList();

    BoardFileDTO getDetail(Long bno);

    void update(BoardDTO boardDTO);

    void remove(Long bno);

    Page<BoardDTO> getList(int pageNo);

    // file convert
    // FileDTO => FileEntity (date x)
    default File convertDtoToEntity(FileDTO fileDTO){
        return File.builder()
                .uuid(fileDTO.getUuid())
                .saveDir(fileDTO.getSaveDir())
                .fileName(fileDTO.getFileName())
                .fileType(fileDTO.getFileType())
                .bno(fileDTO.getBno()) // 의미 없는 값 => 나중에 따로 설정해야 됨.
                .fileSize(fileDTO.getFileSize())
                .build();
    }

    // FileEntity => FileDTO (date o)
    default FileDTO convertEntityToDto(File file){
        return FileDTO.builder()
                .uuid(file.getUuid())
                .saveDir(file.getSaveDir())
                .fileName(file.getFileName())
                .fileType(file.getFileType())
                .bno(file.getBno())
                .fileSize(file.getFileSize())
                .regDate(file.getRegDate())
                .modDate(file.getModDate())
                .build();
    }

    Long insert(BoardFileDTO boardFileDTO);

    FileDTO getFile(String uuid);

    long fileRemove(String uuid);

    void modify(BoardFileDTO boardFileDTO);

    List<FileDTO> getTodayFileList(String today);

    Page<BoardDTO> getList(int pageNo, String type, String keyword);
}





//package com.example.boot.service;
////BoardService
//import com.example.boot.dto.BoardDTO;
//import com.example.boot.dto.BoardFileDTO;
//import com.example.boot.dto.FileDTO;
//import com.example.boot.entity.Board;
//import com.example.boot.entity.File;
//import org.springframework.data.domain.Page;
//
//import java.util.List;
//
//public interface BoardService {
//    //
//    /* interafce 추상 메서드만 가능한 객체
//    default method 인터페이스에서 규칙을 잡거나 로직을 잡을때 사용
//    호환성 유지
//
//
//
//    * DTO - 화면용. Entity DB저장용
//    convert DTO  > entity    entity > DTO
//    boardDTO >> board  로 변환해야됨.
//
//    BoardDto   bno title writer content readCount cmtQty fileQty  regdate moddate
//    근데 Board(entity)는 보드Dto만큼 필요한게 많은게 아니란 말이지....이거 신경서서 변환쳐야됨.
//    * */
//    default Board convertDtoToEntity(BoardDTO boardDTO){
//        return Board.builder()
//                .bno(boardDTO.getBno())
//                .title(boardDTO.getTitle())
//                .writer(boardDTO.getWriter())
//                .content(boardDTO.getContent())
//                .readCount(boardDTO.getReadCount())
//                .cmtQty(boardDTO.getCmtQty())
//                .fileQty(boardDTO.getFileQty())
//                .build();
//    }
//
//    //반대 케이스 DB
//    default BoardDTO convertEntityToDto(Board board){
//
//        return BoardDTO.builder()
//                .bno(board.getBno())
//                .title(board.getTitle())
//                .writer(board.getWriter())
//                .content(board.getContent())
//                .readCount(board.getReadCount())
//                .cmtQty(board.getCmtQty())
//                .fileQty(board.getFileQty())
//                .regDate(board.getRegDate())
//                .modDate(board.getModDate())
//                .build();
//    }
//
//
////paging 없는 리스트 메서드
//    Long insert(BoardDTO boardDTO);
//
//
//    List<BoardDTO> getList();
//
//    BoardFileDTO getDetail(Long bno);
//
//
//    void update(BoardDTO boardDTO);
//
//    void delete(Long bno);
//
//    Page<BoardDTO> getList(int pageNo);
//
//    //file에 대한 convert도 만들거임
//    //fileDTO를 넣으면 FileEntity가 나오는거 하나...그 반대로도 작동하는거 하나.
//    default File convertDtoToEntity(FileDTO fileDTO){
//        return File.builder()
//                .uuid(fileDTO.getUuid())
//                .saveDir(fileDTO.getSaveDir())
//                .fileName(fileDTO.getFileName())
//                .fileType(fileDTO.getFileType())
//                .fileSize(fileDTO.getFileSize())
//                .bno(fileDTO.getBno())//의미 없는 bno   나중에따로 설정해야함.
//                .build();
//    }
//    default FileDTO convertEntityToDto(File file){
//        return FileDTO.builder()
//                .uuid(file.getUuid())
//                .saveDir(file.getSaveDir())
//                .fileName(file.getFileName())
//                .fileType(file.getFileType())
//                .bno(file.getBno()) // 필요 시 나중에 별도 설정 가능
//                .fileSize(file.getFileSize())
//                .regDate(file.getRegDate())
//                .modDate(file.getModDate())
//                .build();
//    }
//
//    Long insert(BoardFileDTO boardFileDTO);
//}
