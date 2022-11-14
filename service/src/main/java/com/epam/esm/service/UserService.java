package com.epam.esm.service;


import com.epam.esm.dto.orderDto.CreateOrder;
import com.epam.esm.dto.userDto.CreateUser;
import com.epam.esm.dto.userDto.ReadUser;
import com.epam.esm.dto.userDto.UserDto;
import com.epam.esm.entity.User;
import org.springframework.transaction.annotation.Transactional;


public interface UserService extends EntityService<ReadUser, CreateUser, UserDto> {


    @Transactional
    ReadUser getUserByName(String name);

    @Transactional
    User findByName(String name);

    @Transactional
    CreateOrder purchaseCertificate(long userId, long certificateId);

}
