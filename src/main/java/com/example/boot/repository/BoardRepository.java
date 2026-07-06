package com.example.boot.repository;
//BoardRepository

import com.example.boot.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

/* JPARepository를 상속 받아서 사용함. */
/*jpaRepository<테이블명, id Type을어떻게쓸지> id Type*/
public interface BoardRepository extends JpaRepository<Board, Long> {
    /*기본 CRUD repository에서 알아서 사용가능. 특수 문법이 필요할 경우 사용함.  구현체없음주의.*/


}
