package com.epam.esm.security.filter;

import com.epam.esm.entity.Role;
import com.epam.esm.security.configSecury.JwtAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
@Component
public class IsValidUser {

    public boolean isValidId(Long id){
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
         boolean result = false;
         if (authentication.getRoles().contains(Role.ADMIN) ||
                 authentication.getRoles().contains(Role.USER) && authentication.getId().equals(id)) {
             result = true;
         }
         return result;
    }
    public boolean isValidName(String name){
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
         boolean result = false;
         if (authentication.getRoles().contains(Role.ADMIN) ||
                 authentication.getRoles().contains(Role.USER) && authentication.getUsername().equals(name)) {
             result = true;
         }
         return result;
    }
}
