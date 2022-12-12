package com.golubovich.project_trpo_tofi.controller;


import com.golubovich.project_trpo_tofi.model.Role;
import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.service.BankServiceImpl;
import com.golubovich.project_trpo_tofi.service.CreditServiceImpl;
import com.golubovich.project_trpo_tofi.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    private final CreditServiceImpl creditService;
    private final BankServiceImpl bankService;
    private final UserServiceImpl userService;

    @Autowired
    public HomeController(CreditServiceImpl creditService, BankServiceImpl bankService, UserServiceImpl userService) {
        this.creditService = creditService;
        this.bankService = bankService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        if (user != null && user.getRole() != Role.USER) {
            return "/admin/admin-panel";
        }
        model.addAttribute("credits", creditService.findAll());
        return "index";
    }

    @GetMapping("/bank-about/{id}")
    public String getAboutBankPageById(@PathVariable("id") Long bankId, Model model) {
        model.addAttribute("bank", bankService.findById(bankId));
        return "about";
    }
}