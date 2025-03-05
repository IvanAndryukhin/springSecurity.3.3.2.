package net.javacode.spring_311.service;

import jakarta.validation.Valid;
import net.javacode.spring_311.model.Role;
import net.javacode.spring_311.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsersList(); // Получить список всех пользователей

    User getUser(Long id); // Получить пользователя по ID

    void addUser(@Valid User user); // Добавить нового пользователя

    void updateUser(@Valid User user); // Обновить существующего пользователя

    void deleteUser(Long id); // Удалить пользователя по ID

    User findByUsername(String username); // Найти пользователя по имени

    boolean isUsernameTaken(String username); // Проверка, занято ли имя пользователя

    List<Role> getAllRoles(); // Получить список всех ролей
}

