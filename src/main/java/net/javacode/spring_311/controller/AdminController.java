package net.javacode.spring_311.controller;

import net.javacode.spring_311.model.Role;
import net.javacode.spring_311.model.User;
import net.javacode.spring_311.service.RoleService;
import net.javacode.spring_311.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService , RoleService roleService) {
        this.userService = userService;
        this.roleService= roleService;
    }

    @GetMapping
    public String showAdminPage() {
        return "admin";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getUsersList();
        model.addAttribute("users", users);
        return "user_list";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam Long id, Model model) {
        User user = userService.getUser(id);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "edit_user";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}

