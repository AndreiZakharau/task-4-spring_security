package com.epam.esm.util.validator.impl;

import com.epam.esm.entity.User;

import com.epam.esm.util.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidator implements Validator<User> {

    @Override
    public boolean isValid(User user) {
        return isPresent(user);
    }

    public boolean isPresent(User user){
        if(user.getNickName().isEmpty() || user.getEmail().isEmpty()
        || user.getPassword().isEmpty()){
            return false;
        } else {
            return true;
        }
    }

}
