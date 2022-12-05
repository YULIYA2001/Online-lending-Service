package com.golubovich.project_trpo_tofi.controller;


import com.golubovich.project_trpo_tofi.model.Credit;
import com.golubovich.project_trpo_tofi.repository.CreditRepository;
import com.golubovich.project_trpo_tofi.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private CreditRepository creditRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("credits", creditRepository.findAll());
        return "home";
    }
    @GetMapping("/online-request")
    public String createRequest(Model model) {
        model.addAttribute("title", "Оформление заявки");
        return "online-request";
    }
}