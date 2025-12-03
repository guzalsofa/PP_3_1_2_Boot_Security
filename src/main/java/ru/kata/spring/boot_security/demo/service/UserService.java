package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    void addUser(User user, List<String> roleNames);
    User updateUser(User user);
    void deleteUser(Long id);
    User findByUsername(String username);
}
