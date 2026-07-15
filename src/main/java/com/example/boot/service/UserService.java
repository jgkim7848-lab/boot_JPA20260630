package com.example.boot.service;

import com.example.boot.dto.UserDTO;
import com.example.boot.entity.User;

public interface UserService {

    // convert
    // DTO -> entity
    default User convertDtoToEntity(UserDTO userDTO){
        return User.builder()
                .email(userDTO.getEmail())
                .pwd(userDTO.getPwd())
                .nickName(userDTO.getNickName())
                .lastLogin(userDTO.getLastLogin())
                .build();
    }


    String insert(UserDTO userDTO);
}