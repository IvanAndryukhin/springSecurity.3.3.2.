package net.javacode.spring_311.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import net.javacode.spring_311.exception.NotUniqueUserNameException;
import net.javacode.spring_311.model.Role;
import net.javacode.spring_311.model.User;
import net.javacode.spring_311.repository.RoleRepository;
import net.javacode.spring_311.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository; // Добавлено для работы с ролями
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository; // Инициализация роли
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersList() {
        return userRepository.findAll(); // Возвращает список всех пользователей
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Пользователь с id " + id + " не найден")
        ); // Получает пользователя по ID
    }

    @Override
    public void addUser(@Valid User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new NotUniqueUserNameException("Пользователь с именем '" + user.getUsername() + "' уже существует."); // Проверяет уникальность имени
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Хеширует пароль
        userRepository.save(user); // Сохраняет нового пользователя
    }

    @Override
    public void updateUser(@Valid User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден с ID: " + user.getId()));

        // Проверка уникальности имени пользователя при обновлении
        if (!existingUser.getUsername().equals(user.getUsername()) &&
                userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new NotUniqueUserNameException("Имя пользователя уже существует: " + user.getUsername());
        }

        // Обновление данных пользователя
        existingUser.setUsername(user.getUsername());
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAddress(user.getAddress());

        // Если новый пароль предоставлен, обновляем его
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(existingUser); // Сохраняем обновленного пользователя
    }

    @Override
    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден с ID: " + id));
        userRepository.delete(existingUser); // Удаление пользователя по ID
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username); // Проверка занятости имени пользователя
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)

                .orElseThrow(() -> new EntityNotFoundException("Пользователь с именем '" + username + "' не найден.")); // Поиск пользователя по имени
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll(); // Получаем список всех ролей
    }
}
