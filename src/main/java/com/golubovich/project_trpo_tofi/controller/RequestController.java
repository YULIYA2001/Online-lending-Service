package com.golubovich.project_trpo_tofi.controller;

import com.golubovich.project_trpo_tofi.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/form-online-request")
public class RequestController  {
    @Autowired
    private CreditRepository creditRepository;

    @GetMapping("/{id}")
    public String getOnlineRequestPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("credit", creditRepository.findById(id).get());
        return "online-request";
    }

    @PostMapping("/{id}")
    public String createOnlineRequest(@PathVariable("id") Long id, Model model) {
        return "redirect:/";
    }
}
