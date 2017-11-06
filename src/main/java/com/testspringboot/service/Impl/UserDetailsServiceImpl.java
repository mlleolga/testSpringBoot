package com.testspringboot.service.Impl;


import com.testspringboot.persistance.UserEntity;
import com.testspringboot.repo.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

/* Коли користувач намагається автентифікувати, цей метод отримує ім'я користувача,
 шукає в базі даних запис, що містить його,
  і (якщо знайдено) повертає екземпляр User*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //    UserEntity userEntity = userRepository.findByEmail(username);

      UserEntity user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException(username);

        }

        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
