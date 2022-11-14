package com.epam.esm.mapper.impl.userMapper;


import com.epam.esm.dto.userDto.CreateUser;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TransitionCreateUserFromUser implements Mapper<User, CreateUser> {

    @Override
    public CreateUser mapFrom(User object) {
        return new CreateUser(
                object.getNickName(),
                object.getEmail(),
                object.getPassword()
                );
    }
}
