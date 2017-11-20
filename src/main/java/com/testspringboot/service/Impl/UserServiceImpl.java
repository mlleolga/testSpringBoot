package com.testspringboot.service.Impl;

import com.testspringboot.Dto.HistoryRequest;
import com.testspringboot.Dto.UserDto;
import com.testspringboot.persistance.BalanceEntity;
import com.testspringboot.persistance.HistoryType;
import com.testspringboot.persistance.UserEntity;
import com.testspringboot.repo.BalanceRepository;
import com.testspringboot.repo.UserRepository;
import com.testspringboot.service.HistoryService;
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
    @Autowired
    private HistoryService historyService;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity findUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean saveNewUser(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setEmail(userDto.getEmail());
        if (Objects.isNull(userRepository.findByEmail(userDto.getEmail()))) {
            user = saveUser(userDto, user);
            historyService.logHistory(createHistoryRequest(user));
            return true;
        }else{
            return false;
        }


    }

    private UserEntity saveUser(UserDto userDto, UserEntity userEntity) {
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
        return user;
    }

    private HistoryRequest createHistoryRequest(UserEntity userEntity){
        HistoryRequest historyRequest = new HistoryRequest();
        historyRequest.setUserId(userEntity.getId());
        historyRequest.setHistoryType(HistoryType.CREATE_USER.name());
        historyRequest.setHistoryContent("user with name " + userEntity.getUsername()+ " was created at " + new Date(System.currentTimeMillis()));
        return historyRequest;
    }

}

