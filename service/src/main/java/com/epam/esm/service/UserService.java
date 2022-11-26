package com.epam.esm.service;

import com.epam.esm.dto.orderDto.CreateOrder;
import com.epam.esm.dto.userDto.CreateUser;
import com.epam.esm.dto.userDto.ReadUser;
import com.epam.esm.dto.userDto.UserDto;
import com.epam.esm.entity.User;


public interface UserService extends EntityService<ReadUser, CreateUser, UserDto> {

    ReadUser getUserByName(String name);

    User findByName(String name);

    CreateOrder purchaseCertificate(long userId, long certificateId);

}
