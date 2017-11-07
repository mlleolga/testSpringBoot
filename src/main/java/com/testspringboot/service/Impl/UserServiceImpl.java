package com.testspringboot.service.Impl;

import com.testspringboot.Dto.UserDto;
import com.testspringboot.persistance.BalanceEntity;
import com.testspringboot.persistance.UserEntity;
import com.testspringboot.repo.BalanceRepository;
import com.testspringboot.repo.UserRepository;
import com.testspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private BalanceRepository balanceRepository;

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
        return Objects.isNull(userRepository.findByEmail(userDto.getEmail())) && saveUser(userDto, user);
    }

    private boolean saveUser(UserDto userDto, UserEntity userEntity) {
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));



  //      userEntity.setBalance(balanceEntity1);
        userEntity.setUsername(userDto.getEmail());

        UserEntity user = userRepository.save(userEntity);
        BalanceEntity balanceEntity1 = new BalanceEntity();
        balanceEntity1.setUser(user);
        balanceEntity1.setBalance(new BigDecimal(0));
        balanceEntity1.setModifiedDate(new Date(System.currentTimeMillis()));
        balanceRepository.save(balanceEntity1);
        return true;
    }


}

