package com.harriet.shopiify.auth.service;

import com.harriet.shopiify.auth.model.User;
import com.harriet.shopiify.auth.repository.UserRepository;
import com.harriet.shopiify.cart.model.Cart;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User findById ( Long userId){
        return userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("no user found"));
    }

    public void updateById ( Long userId, User user){
        User userToUpdate = userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("no user found"));
        BeanUtils.copyProperties(user, userToUpdate);
        userRepository.save(userToUpdate);
    }

}
