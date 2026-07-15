package com.example.boot.repository;

import com.example.boot.entity.Board;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.boot.entity.QBoard.board;


@Slf4j
public class BoardCustomRepositoryImpl implements BoardCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public BoardCustomRepositoryImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Board> searchBoard(String type, String keyword, Pageable pageable) {
        /* select * from board
         * where title like '%aaa%'
         * where title like '%aaa%' or content like '%aaa%'
         * where title like '%aaa%' or content like '%aaa%' or writer like '%aaa%'
         * BooleanExpression : 동적 쿼리를 사용할 때 실행 여부 확인 객체 (필수)
         * */
        BooleanExpression condition = null;

        // 동적 검색 조건
        if(type != null && keyword != null){
            // 타입이 여러개 들어올 경우 배열로 처리
            String[] typeArr = type.split(""); // 한글자씩 배열로 리턴
            for(String t : typeArr){
                switch (t){
                    case "t" :
                        condition = (condition == null) ?
                                // 처음 조건이라면...
                                board.title.containsIgnoreCase(keyword) :
                                // 이전 조건이 있었다면...
                                condition.or(board.title.containsIgnoreCase(keyword));                                ;
                        break;
                    case "w" :
                        condition = (condition == null) ?
                                // 처음 조건이라면...
                                board.writer.containsIgnoreCase(keyword) :
                                // 이전 조건이 있었다면...
                                condition.or(board.writer.containsIgnoreCase(keyword));
                        break;
                    case "c" :
                        condition = (condition == null) ?
                                // 처음 조건이라면...
                                board.content.containsIgnoreCase(keyword) :
                                // 이전 조건이 있었다면...
                                condition.or(board.content.containsIgnoreCase(keyword));
                        break;
                    default: break;
                }
            }
        }

        // 쿼리 작성
        List<Board> result = jpaQueryFactory
                .selectFrom(board)
                .where(condition)
                .orderBy(board.bno.desc())
                .offset(pageable.getOffset())  // limit 번지, 개수
                .limit(pageable.getPageSize()) // 개수
                .fetch();
        log.info("condition >> {}", condition);
        log.info("offset >> {}", pageable.getOffset());
        log.info("result >> {}", result);

        // 검색 데이터를 반영한 결과 전체 개수 조회
        long total = jpaQueryFactory.selectFrom(board)
                .where(condition)
                .fetch()
                .size();

        /* public PageImpl(
            @NotNull   java.util.List<T> content,
            @NotNull   org.springframework.data.domain.Pageable pageable,
            long total)
        * */

        return new PageImpl<>(result, pageable, total);
    }
}