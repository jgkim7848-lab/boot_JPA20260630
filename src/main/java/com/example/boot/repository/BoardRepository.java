package com.example.boot.repository;

import com.example.boot.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

/* JPARepository 상속 받아서 사용 */
/* jpaRepository<테이블명, id Type> id Type 클래스 타입만 가능 */
public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {
    /* 기본 CRUD repository에서 알아서 사용가능.  특수 문법이 필요할 경우사용 */
}