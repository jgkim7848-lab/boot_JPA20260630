package com.example.boot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "authList")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends TimeBase {
    @Id
    private String email;
    @Column(nullable = false)
    private String pwd;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    /* mappedBy : 연관관계의 테이블 쪽의 연결된 필드명
            auth_user의 user와 연결
       JPA에서는 주 테이블이 아닌쪽에서는 조회만 가능하도록 관리
       - Cascade.ALL : User를 수정/삭제 될 때 auth_user도 같이 수정/삭제되도록 설계
       - orphanRemoval = true : 삭제시 같이 제거
    * */
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuthUser> authList = new ArrayList<>();

    // 편의 메서드
    public void addAuth(AuthRole role){
        if(this.authList == null){
            this.authList = new ArrayList<>();
        }
        this.authList.add(AuthUser.builder()
                .user(this)
                .auth(role)
                .build());
    }
}