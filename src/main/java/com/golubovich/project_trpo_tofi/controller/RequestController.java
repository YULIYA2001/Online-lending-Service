package com.golubovich.project_trpo_tofi.controller;

import com.golubovich.project_trpo_tofi.model.Credit;
import com.golubovich.project_trpo_tofi.model.Request;
import com.golubovich.project_trpo_tofi.model.RequestStatus;
import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.service.CreditServiceImpl;
import com.golubovich.project_trpo_tofi.service.RequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/create-online-request")
public class RequestController  {
    private final CreditServiceImpl creditService;
    private final RequestServiceImpl requestService;

    @Autowired
    public RequestController(CreditServiceImpl creditService, RequestServiceImpl requestService) {
        this.creditService = creditService;
        this.requestService = requestService;
    }

    @GetMapping("/{id}")
    public String createOnlineRequestForm(@PathVariable("id") Long creditId, Model model) {
        model.addAttribute("credit", creditService.findById(creditId));
        return "online-request";
    }

    @PostMapping("/{id}")
    public String createOnlineRequest(@PathVariable("id") Long creditId, Model model,
                                      Request request) {
        requestService.createRequest(request);
        return "redirect:/";
    }
}
