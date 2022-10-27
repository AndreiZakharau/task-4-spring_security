package com.epam.esm.mapper.impl.userMapper;


import com.epam.esm.dto.userDto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service
public class TransitionUserFromUserDto implements Mapper<UserDto, User> {
    @Override
    public User mapFrom(UserDto object) {
        return User.builder()
                .id(object.getId())
                .nickName(object.getNickName())
                .email(object.getEmail()).build();
    }
}
