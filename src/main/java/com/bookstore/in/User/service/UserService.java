package com.bookstore.in.User.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.in.User.repository.UserRepository;
import com.bookstore.in.User.model.User;
import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repo;

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User createUser(User user) {
        return repo.save(user);
    }
}
