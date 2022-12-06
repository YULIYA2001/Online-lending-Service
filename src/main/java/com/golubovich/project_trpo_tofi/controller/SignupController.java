package com.golubovich.project_trpo_tofi.controller;

import com.golubovich.project_trpo_tofi.model.Role;
import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.model.UserDetails;
import com.golubovich.project_trpo_tofi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.Optional;

@Controller
@RequestMapping(value = "/auth")
public class SignupController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String addUser(User user, UserDetails userDetails, Model model) {
        Optional<User> optionalUserFromDb = userRepository.findByEmailOrPhone(user.getEmail(), user.getPhone());
        User userFromDb = optionalUserFromDb.orElse(null);

        if (userFromDb != null) {
            if (userFromDb.getEmail().equals(user.getEmail())) {
                model.addAttribute("msgExistingEmail", "Пользователь с таким email уже существует!");
                return "redirect:/auth/signup";
            }
            else if (userFromDb.getEmail().equals(user.getPhone())) {
                model.addAttribute("msgExistingPhone", "Пользователь с таким телефоном уже существует!");
                return "redirect:/auth/signup";
            }
        }

        user.setUserDetails(userDetails);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/auth/login";
    }
}
