package net.javacode.spring_311.controller;

import net.javacode.spring_311.model.Role;
import net.javacode.spring_311.model.User;
import net.javacode.spring_311.service.RoleService;
import net.javacode.spring_311.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String listUsers(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Получаем текущего аутентифицированного пользователя
        User currentUser = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", currentUser); // Передаем текущего пользователя в модель

        // Получаем список всех пользователей и ролей
        List<User> users = userService.getUsersList();
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);

        // Передаем нового пользователя в модель для формы добавления
        User newUser = new User();
        model.addAttribute("newUser", newUser);

        return "admin";
    }

    // Отображение формы редактирования пользователя
    @GetMapping("/edit")
    public String editUser(@RequestParam Long id, Model model) {
        User user = userService.getUser(id); // Получаем пользователя по ID
        List<Role> roles = roleService.getAllRoles(); // Получаем список всех ролей
        model.addAttribute("user", user); // Передаем пользователя в модель
        model.addAttribute("roles", roles); // Передаем роли в модель
        return "admin";
    }

    // Обновление пользователя
    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user); // Обновляем пользователя
        return "redirect:/admin/users"; // Перенаправляем на список пользователей
    }

    // Удаление пользователя
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id); // Удаляем пользователя по ID
        return "redirect:/admin/users"; // Перенаправляем на список пользователей
    }

    // Отображение формы добавления нового пользователя
    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        User user = new User(); // Создаем нового пользователя
        List<Role> roles = roleService.getAllRoles(); // Получаем список всех ролей
        model.addAttribute("user", user); // Передаем нового пользователя в модель
        model.addAttribute("roles", roles); // Передаем роли в модель
        return "admin"; // Возвращаем страницу для добавления пользователя
    }

    // Сохранение нового пользователя
    @PostMapping()
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user); // Сохраняем нового пользователя
        return "redirect:/admin/users"; // Перенаправляем на список пользователей
    }

    // Выход из системы
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout"; // Перенаправляем на страницу входа с параметром logout
    }
}