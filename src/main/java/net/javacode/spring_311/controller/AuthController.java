package net.javacode.spring_311.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    public AuthController() {

    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Неверные учетные данные. Пожалуйста, попробуйте снова.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(Authentication authentication) {
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_ADMIN")) {
            return "redirect:/admin";
        } else if (role.equals("ROLE_USER")) {
            return "redirect:/user";
        } else {
            return "redirect:/login?error";
        }
    }
}


