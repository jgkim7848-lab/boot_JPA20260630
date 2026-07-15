package com.example.boot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "auth_user")
@Getter
@Setter
@ToString(exclude = "user")  // 양방향 참조시 순환 참조 방지
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUser {
    // M : 1(하나의 유저에 권한이 여러개 M:1 mapping)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // String email -> 대신에 User 객체를 맵핑
    // fetch type : jpa에서 엔티티를 조회할 때 연관된 엔티티를 찾을을 방법을 결정하는 전략
    // 즉시로딩(EAGER), 지연로딩(LAZY) => 일반적.
    // 즉시로딩 : A 엔티티를 조회할 때 연관된 B엔티티를 바로 조회하는 방식
    // 필요하지 않은 데이터도 함께 조회되어 성능 이슈
    // 지연로딩 : A 엔티티를 조회할 때 B 엔티티의 값은 따로 조회하지 않고, A엔티티만 조회
    // B 가 필요할 때만 조회 => 불필요한 데이터 가져오는 것을 방지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private User user; // DB의 FK 칼럼명을 적어줌.
    @Enumerated(EnumType.STRING)
    private AuthRole auth; // Enum의 이름을 문자열로 저장
}