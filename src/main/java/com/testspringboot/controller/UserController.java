package com.testspringboot.controller;

import com.testspringboot.Dto.UserDto;
import com.testspringboot.persistance.UserEntity;
import com.testspringboot.repo.UserRepository;
import com.testspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

     @Autowired
    private UserService userService;

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
     public List<UserEntity> findAll(){
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public UserDto addUser(@RequestBody UserEntity userEntity){
        return new UserDto();
    }

   @RequestMapping(value = "/sign-up", method = RequestMethod.POST, consumes = "application/json")
    public boolean signUp(@RequestBody UserDto user){
        return userService.saveNewUser(user);
    }



}
