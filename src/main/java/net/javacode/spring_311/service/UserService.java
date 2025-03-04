package net.javacode.spring_311.service;

import jakarta.validation.Valid;
import net.javacode.spring_311.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsersList();
    void addUser(User user);
    boolean isUsernameTaken(String username);
    User getUser(Long id);
    void updateUser(User user);
    void deleteUser(Long id);
}
