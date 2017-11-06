package com.testspringboot.service.Impl;

import com.testspringboot.Dto.UserDto;
import com.testspringboot.persistance.UserEntity;
import com.testspringboot.repo.UserRepository;
import com.testspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean saveNewUser(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setEmail(userDto.getEmail());
        return Objects.isNull(userRepository.findByEmail(userDto.getEmail())) ? saveUser(userDto, user) : false;
    }

    private boolean saveUser(UserDto userDto, UserEntity userEntity) {
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        UserEntity user = userRepository.save(userEntity);
        return true;
    }


}

