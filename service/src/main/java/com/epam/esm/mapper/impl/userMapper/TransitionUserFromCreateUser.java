package com.epam.esm.mapper.impl.userMapper;


import com.epam.esm.dto.userDto.CreateUser;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service
public class TransitionUserFromCreateUser implements Mapper<CreateUser, User> {
    @Override
    public User mapFrom(CreateUser object) {
        return User.builder()
                .nickName(object.getNickName())
                .email(object.getEmail())
                .build();
    }
}
