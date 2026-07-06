package com.example.boot.service;
//CommentServiceImpl
import com.example.boot.dto.CommentDTO;
import com.example.boot.entity.Board;
import com.example.boot.entity.Comment;
import com.example.boot.repository.BoardRepository;
import com.example.boot.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional //항상 framework가 있는걸 찾아서 달아줄것.   이것이 달렸다는건 2개 이상의 명령어가 실행될때....둘중 하나라도 잘못됐을때 에러가 떠야함.
    //
    @Override
    public long post(CommentDTO commentDTO) {
        //저장 대상은 항상 entity   이 말은 곧 commentDTO를 comment로 변환을 해야 저장이 가능하다는 소리....
        //저장은 save()
        //댓글이 등록되면 해당 board의 cmt_qty update를 해줘야함.
//        Optional<Board> optional = boardRepository.findById(commentDTO.getBno());
//        if(optional.isPresent()){
//            Board board = optional.get();
//            board.setCmtQty(board.getCmtQty());
//        }
        //내가 굳이 save를 안해도 update가 생길수있게됨.
        Board board = boardRepository.findById(commentDTO.getBno())
                .orElseThrow(() -> new EntityNotFoundException());
        board.setCmtQty(board.getCmtQty()+1);
        long cno = commentRepository.save(convertDtoToEntity(commentDTO)).getCno();// 잘들어갔는지확인을위해getCno를한다?????
        return cno;


    }

    @Override
    public List<CommentDTO> getList(long bno) {
        //select * from comment where bno = #{bno} order by cno desc
        List<Comment> list = commentRepository.findByBno(bno);
        List<CommentDTO> commentDTOList = list.stream()
                .map(this::convertEntityToDto)
                .toList();

        return commentDTOList;
    }
//위는 page없는애 아래는 page있는애.
    @Override
    public Page<CommentDTO> getList(long bno, int page) {
        //page된 값을 리턴바등려면 pageable값을 파라미터로 전송해야됨. orderby 뭐시기....
        Pageable pageable = PageRequest.of(page-1, 5,
                Sort.by("cno").descending());

        Page<Comment> list = commentRepository.findByBno(bno, pageable);

        return list.map(this::convertEntityToDto);
    }

    @Override
    public void remove(long cno) {
        Comment comment = commentRepository.findById(cno)
                .orElseThrow(()-> new EntityNotFoundException());
        Board board = boardRepository.findById(comment.getBno())
                .orElseThrow(() -> new EntityNotFoundException());
        board.setCmtQty(board.getCmtQty()-1);
        commentRepository.deleteById(cno);
    }


}
