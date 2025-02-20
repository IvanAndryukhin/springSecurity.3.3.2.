package net.javacode.spring_311.service;

import net.javacode.spring_311.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsersList();
    User getUser(Long id);
    void updateUser(User user);
    void deleteUser(Long id);
}
