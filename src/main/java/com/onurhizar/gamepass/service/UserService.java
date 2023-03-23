package com.onurhizar.gamepass.service;


import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repository;

    public void addUser(User user){
        repository.save(user);
    }

    public List<User> listUsers(){
        return repository.findAll();
    }
}
