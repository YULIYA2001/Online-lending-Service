package com.golubovich.project_trpo_tofi.controller;

import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.model.UserDetails;
import com.golubovich.project_trpo_tofi.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/auth")
public class SignupController {
    private final UserServiceImpl userService;
    @Autowired
    public SignupController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(User user, UserDetails userDetails, Model model) {
        user.setUserDetails(userDetails);

        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("msgExistingEmail", "Пользователь с таким email уже существует!");
            model.addAttribute("user", user);
            return "signup";
        }
        if (userService.findByPhone(user.getPhone()) != null) {
            model.addAttribute("msgExistingPhone", "Пользователь с таким телефоном уже существует!");
            model.addAttribute("user", user);
            return "signup";
        }

        userService.createUser(user);

        return "redirect:/auth/login";
    }

}
