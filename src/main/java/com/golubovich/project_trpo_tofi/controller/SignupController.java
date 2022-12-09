package com.golubovich.project_trpo_tofi.controller;

import com.golubovich.project_trpo_tofi.model.Role;
import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.model.UserDetails;
import com.golubovich.project_trpo_tofi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
        List<User> usersFromDb = userRepository.findByEmailOrPhone(user.getEmail(), user.getPhone());
        User userFromDb = usersFromDb.isEmpty() ? null : usersFromDb.get(0);

        if (userFromDb != null) {
            if (userFromDb.getEmail().equals(user.getEmail())) {
                model.addAttribute("msgExistingEmail", "Пользователь с таким email уже существует!");
                user.setUserDetails(userDetails);
                model.addAttribute("user", user);
                return "signup";
            }
            else if (userFromDb.getPhone().equals(user.getPhone())) {
                model.addAttribute("msgExistingPhone", "Пользователь с таким телефоном уже существует!");
                user.setUserDetails(userDetails);
                model.addAttribute("user", user);
                return "signup";
            }
        }

        user.setUserDetails(userDetails);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/auth/login";
    }

}
