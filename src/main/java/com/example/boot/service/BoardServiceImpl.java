package com.example.boot.service;
//BoardServiceImpl
import com.example.boot.dto.BoardDTO;
import com.example.boot.entity.Board;
import com.example.boot.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;

    @Override
    public Long insert(BoardDTO boardDTO) {
        //CRUD에 해당하는 메서드 제공
        //save()  저장
        //저장하는 객체는 Entity(Board)
        Board board = convertDtoToEntity(boardDTO);
        Long bno= boardRepository.save(board).getBno();
        return bno;
    }

    @Override
    public List<BoardDTO> getList() {
        //findAll();   전체값 리턴.
        //select * from board order by bno desc
        //정렬: Sort.By(sort.derection.DESC
    //db에서 가져오는 return board <신부수업 찾워야ㅔㄱㅆ다.ㅑ
        List<Board> boardList = boardRepository.findAll(
            Sort.by(Sort.Direction.DESC, "bno")

        );

        List<BoardDTO> boardDTOList = boardList
                .stream()
                .map(board -> convertEntityToDto(board))
//                .map(this :: convertEntityToDto)
                .toList();

        return boardDTOList;
    }

    @Override
    public BoardDTO getDetail(Long bno) {

        //fineOne  기본키를 이용하여 원하는 객체 검색 where bno
        //findBy 칼럼명   원하는 칼럼명을 이용해 검색
        //findByID = findOne
        //리턴값이 optional
        //Optional<T>   nullpointexception 발생 방지
        //Optional.isEmpty()   null이면 true / false
        //Optional.ispresent()   값의 존재 여부 확인 true false
        //Optional.
        //Optional.

        Optional<Board> optional = boardRepository.findById(bno);
        if(optional.isPresent()){
            Board board = optional.get();
            BoardDTO boardDTO = convertEntityToDto(board);
            //조회수 readCount ++
            //save만 있는 상태에서는 변경된 객체로 객체를 수정해야됨.  그리고 저장....그냥 모든 값을 수정해야되게 되는것임.
            board.setReadCount(board.getReadCount()+1);
            boardRepository.save(board);



            return boardDTO;
        }
        return null;
    }

    @Override
    public void update(BoardDTO boardDTO){
        Optional<Board> optional = boardRepository.findById(boardDTO.getBno());
        if(optional.isPresent()){
            Board board = optional.get();
            board.setTitle(boardDTO.getTitle());
            board.setContent(boardDTO.getContent());
            board.setReadCount(board.getReadCount());
            boardRepository.save(board);
        }
    }

    @Override
    public void delete(Long bno) {
        boardRepository.deleteById(bno);

    }

    @Override
    public Page<BoardDTO> getList(int pageNo) {
        //페이지네이션이 포함된 리스트.
        //limit는 시작번지와 개수....db입장에서 번지는 0부터 시작....
        //pageNo-1을 해야 1번부터 제대로 들어오게됨.
        Pageable pageable = PageRequest.of(pageNo-1, 10, Sort.by("bno").descending());
        Page<Board> pageList = boardRepository.findAll(pageable);
        log.info("pageList", pageList);

        Page<BoardDTO> boardDTOPage = pageList.map(this :: convertEntityToDto);


        return boardDTOPage;
    }


}
