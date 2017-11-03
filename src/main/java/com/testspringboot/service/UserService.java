package com.testspringboot.service;

import com.testspringboot.persistance.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserEntity> getAllUsers();

    UserEntity findUserById (Long id);

    UserEntity findUserByEmail (String email);




}
