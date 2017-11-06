package com.testspringboot.service;

import com.testspringboot.Dto.UserDto;
import com.testspringboot.persistance.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService{

    List<UserEntity> getAllUsers();

    UserEntity findUserById (Long id);

    UserEntity findUserByEmail (String email);

    boolean saveNewUser(UserDto userDto);




}
