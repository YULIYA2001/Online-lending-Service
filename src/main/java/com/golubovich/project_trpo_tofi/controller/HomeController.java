package com.golubovich.project_trpo_tofi.controller;


import com.golubovich.project_trpo_tofi.service.BankServiceImpl;
import com.golubovich.project_trpo_tofi.service.CreditServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final CreditServiceImpl creditService;
    private final BankServiceImpl bankService;

    @Autowired
    public HomeController(CreditServiceImpl creditService, BankServiceImpl bankService) {
        this.creditService = creditService;
        this.bankService = bankService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("credits", creditService.findAll());
        return "home";
    }

    @GetMapping("/bank-about/{id}")
    public String getAboutBankPageById(@PathVariable("id") Long bankId, Model model) {
        model.addAttribute("bank", bankService.findById(bankId));
        return "about";
    }
}