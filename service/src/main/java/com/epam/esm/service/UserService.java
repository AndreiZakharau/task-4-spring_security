package com.epam.esm.service;


import com.epam.esm.dto.userDto.CreateUser;
import com.epam.esm.dto.userDto.ReadUser;
import com.epam.esm.dto.userDto.UserDto;


public interface UserService extends EntityService<ReadUser, CreateUser, UserDto> {


}
