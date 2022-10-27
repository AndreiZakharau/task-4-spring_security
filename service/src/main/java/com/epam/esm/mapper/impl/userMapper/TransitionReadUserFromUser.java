package com.epam.esm.mapper.impl.userMapper;


import com.epam.esm.dto.userDto.ReadUser;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.orderMapper.TransitionOrderDtoFromOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransitionReadUserFromUser implements Mapper<User, ReadUser> {

    private final TransitionOrderDtoFromOrder orderDtoMapper;

    @Override
    public ReadUser mapFrom(User object) {
        return new ReadUser(
                object.getId(),
                object.getNickName(),
                object.getEmail(),
                orderDtoMapper.buildListOrderDto(object.getOrders())
        );
    }

    public List<ReadUser> buildUserModelReadMapper(List<User> users) {
        List<ReadUser> list = new ArrayList<>();
        for (User user : users) {
            ReadUser readUser = ReadUser.builder()
                    .id(user.getId())
                    .nickName(user.getNickName())
                    .email(user.getEmail())
                    .orders(orderDtoMapper.buildListOrderDto(user.getOrders()))
                    .build();
            list.add(readUser);
        }
        return list;
    }
}
