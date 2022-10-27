package com.epam.esm.mapper.impl.userMapper;

import com.epam.esm.Dto.userDto.ReadUser;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransitionUserFromReadUser implements Mapper<ReadUser, User> {

    private final TransitionOrderFromOrderDto orderMapper;

    @Override
    public User mapFrom(ReadUser object) {
        return User.builder()
                .id(object.getId())
                .nickName(object.getNickName())
                .email(object.getEmail())
                .orders(orderMapper.buildListOrder(object.getOrders()))
                .build();
    }
}
