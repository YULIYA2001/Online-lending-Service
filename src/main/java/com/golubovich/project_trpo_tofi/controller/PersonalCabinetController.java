package com.golubovich.project_trpo_tofi.controller;

import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonalCabinetController  {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/cabinet")
    public String cabinet(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userRepository.findByEmail(authentication.getName()).get());
        return "personal-cabinet";
    }
}