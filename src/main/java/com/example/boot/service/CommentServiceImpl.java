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

    @Transactional
    @Override
    public List<CommentDTO> getList(long bno) {
        //select * from comment where bno = #{bno} order by cno desc
        List<Comment> list = commentRepository.findByBno(bno);
        List<CommentDTO> commentDTOList = list.stream()
                .map(this::convertEntityToDto)
                .toList();

        return commentDTOList;
    }

    @Transactional
//위는 page없는애 아래는 page있는애.
    @Override
    public Page<CommentDTO> getList(long bno, int page) {
        //page된 값을 리턴바등려면 pageable값을 파라미터로 전송해야됨. orderby 뭐시기....
        Pageable pageable = PageRequest.of(page-1, 5,
                Sort.by("cno").descending());

        Page<Comment> list = commentRepository.findByBno(bno, pageable);

        return list.map(this::convertEntityToDto);
    }

    @Transactional
    @Override
    public long modify(CommentDTO commentDTO) {
         Comment comment = commentRepository.findById(commentDTO.getCno())
                   .orElseThrow(()->new EntityNotFoundException("해당 댓글을 찾을수 없습니다."));
         comment.setContent(commentDTO.getContent());
         return comment.getCno();
        /*
         * save() id가 없으면 insert고 있으면 update를 한다.
         * EntityNotFoundException 은 where에서 검색한 조건의 값이 없을 경우 발생하게된다....정보 유실의 가능성이 커지게됨.
         * dirty checking 변동 감지
         * findById(cno)  먼저 조회해서 영속성 상태를 만듬.
         * 있는게 확인되면 수정하는것.  그리고 save()를 진행.
         *
         * @Transactional  이 dirty checking만으로 업데이트를 가능하게한다. = save()없이도 업데이트가 가능해짐.
         *
         * dirtychecking 엔티티가 영속성 컨텍스트에 올라가있는 상태일때 = 영속상태라고 부름.
         * 해당 객체의 필드가 변경되면 트랜젝션이 종료되기전에 jpa가 변경한 부분만 자동으로 감지하여 update querry를 실행해줌.
         * save가 없어도 명시적으로 호출하지않아도 수정된 필드를 자동으로 db에 반영이 가능하다.
         *
         * */
    }

    @Transactional
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
