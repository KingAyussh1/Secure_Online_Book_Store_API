package com.example.bookstore.service;

import com.example.bookstore.model.User;

public interface UserService {
    User registerUser(User user);
    User findByUsername(String username);
}
