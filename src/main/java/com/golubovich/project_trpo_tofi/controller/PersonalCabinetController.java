package com.golubovich.project_trpo_tofi.controller;

import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.model.UserDetails;
import com.golubovich.project_trpo_tofi.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/cabinet")
public class PersonalCabinetController {
    private final UserServiceImpl userService;

    @Autowired
    public PersonalCabinetController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user_admin:write')")
    public String cabinet(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        model.addAttribute("user", user);
        return "personal-cabinet";
    }

    @GetMapping("/update-user")
    @PreAuthorize("hasAuthority('user_admin:write')")
    public String updateUserForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        model.addAttribute("user", user);
        return "/update-user";
    }

    @PostMapping("/update-user")
    @PreAuthorize("hasAuthority('user_admin:write')")
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