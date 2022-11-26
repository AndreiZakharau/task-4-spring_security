package com.epam.esm.mapper.impl.userMapper;

import com.epam.esm.dto.userDto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransitionUserDtoFromUser implements Mapper< User, UserDto> {
    @Override
    public UserDto mapFrom(User object) {
        return new UserDto(
                object.getId(),
                object.getNickName(),
                object.getEmail()
                );
    }

    public List<UserDto> buildListUserDto(List<User> users) {
        List<UserDto> list = new ArrayList<>();
        for (User user : users) {
            UserDto ud = UserDto.builder()
                    .id(user.getId())
                    .nickName(user.getNickName())
                    .email(user.getEmail())
                    .build();
            list.add(ud);
        }
        return list;
    }
}
