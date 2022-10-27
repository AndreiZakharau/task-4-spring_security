package com.epam.esm.service.impl;

import com.epam.esm.dto.userDto.CreateUser;
import com.epam.esm.dto.userDto.ReadUser;
import com.epam.esm.dto.userDto.UserDto;
import com.epam.esm.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Transactional
    @Override
    public List<ReadUser> getAllEntity(int limit, int offset) {
        return null;
    }

    @Transactional
    @Override
    public void saveEntity(CreateUser createUser) {


    }

    @Transactional
    @Override
    public void updateEntity(long id, UserDto userDto) {

    }

    @Transactional
    @Override
    public ReadUser findById(long id) {
        return null;
    }

    @Transactional
    @Override
    public void deleteEntity(long id) {

    }

    @Transactional
    @Override
    public long countAll() {
        return 0;
    }
}
