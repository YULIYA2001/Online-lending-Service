package com.golubovich.project_trpo_tofi.controller;

import com.golubovich.project_trpo_tofi.model.Request;
import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.repository.RequestRepository;
import com.golubovich.project_trpo_tofi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
public class PersonalCabinetController  {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private RequestRepository requestRepository;

    @GetMapping("/cabinet")
    public String cabinet(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).get();
        model.addAttribute("user", user);
        model.addAttribute("modalWindowContent", "user-request-response");
        model.addAttribute("modalWindowPath", "blocks/user-requests-list");
        model.addAttribute("modalWindowBtnText", "Показать");
        return "personal-cabinet";
    }

}