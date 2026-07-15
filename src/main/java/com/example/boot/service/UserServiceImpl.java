package com.example.boot.service;

import com.example.boot.dto.UserDTO;
import com.example.boot.entity.AuthRole;
import com.example.boot.entity.User;
import com.example.boot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String insert(UserDTO userDTO) {
        // pwd 암호화 해서 DB에 넣기
        userDTO.setPwd(passwordEncoder.encode(userDTO.getPwd()));
        User user = convertDtoToEntity(userDTO);
        user.addAuth(AuthRole.USER);
        log.info("user >> {}", user);
        log.info("auth_user >> {}", user.getAuthList());
        // cascade.ALL  설정으로 인해 User 테이블이 변경되면 AuthUser도 자동 저장
        return userRepository.save(user).getEmail();
    }
}