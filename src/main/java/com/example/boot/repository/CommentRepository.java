package com.example.boot.repository;
//CommentRopository
import com.example.boot.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /* findBy**    **테이블 안의 모든 칼럼
    * select * from comment where 칼럼명=??
    * 기본키id가 아닌 일반 칼럼은 등록을 해야 사용이 가능해짐.
    *
    * */
    List<Comment> findByBno(Long bno);

    Page<Comment> findByBno(Long bno, Pageable pageable);

}
