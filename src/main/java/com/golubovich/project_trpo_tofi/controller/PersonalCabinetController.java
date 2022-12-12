package com.golubovich.project_trpo_tofi.controller;

import com.golubovich.project_trpo_tofi.model.Request;
import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.model.UserDetails;
import com.golubovich.project_trpo_tofi.repository.RequestRepository;
import com.golubovich.project_trpo_tofi.repository.UserRepository;
import com.golubovich.project_trpo_tofi.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping(value = "/cabinet")
public class PersonalCabinetController  {
    private final UserServiceImpl userService;

    @Autowired
    public PersonalCabinetController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public String cabinet(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        model.addAttribute("user", user);
        return "personal-cabinet";
    }

    @GetMapping("/update-user")
    public String updateUserForm(Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        model.addAttribute("user", user);
        return "/update-user";
    }

    @PostMapping("/update-user")
    public String updateUser(User user, UserDetails userDetails, String passwordOld, Model model) {
        user.setUserDetails(userDetails);

        String updateResults = userService.updateUser(user, passwordOld);

        if (updateResults.equals("email")) {
            model.addAttribute("msgExistingEmail", "Пользователь с таким email уже существует!");
            model.addAttribute("user", user);
            return "update-user";
        }
        if (updateResults.equals("phone")) {
            model.addAttribute("msgExistingPhone", "Пользователь с таким телефоном уже существует!");
            model.addAttribute("user", user);
            return "update-user";
        }
        if (updateResults.equals("password")) {
            model.addAttribute("msgWrongPasswordOld", "Неверно введен старый пароль");
            model.addAttribute("user", user);
            model.addAttribute("passwordOld", passwordOld);
            return "update-user";
        }

        return "redirect:/cabinet";
    }

}