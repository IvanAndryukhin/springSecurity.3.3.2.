package net.javacode.spring_311.service;

import net.javacode.spring_311.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsersList();
    User getUser(Long id);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
    boolean isUsernameTaken(String username);
}
