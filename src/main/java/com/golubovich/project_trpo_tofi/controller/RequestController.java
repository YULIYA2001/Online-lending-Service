package com.golubovich.project_trpo_tofi.controller;

import com.golubovich.project_trpo_tofi.model.Request;
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
@RequestMapping(value = "/online-request")
public class RequestController  {
    private final CreditServiceImpl creditService;
    private final RequestServiceImpl requestService;

    @Autowired
    public RequestController(CreditServiceImpl creditService, RequestServiceImpl requestService) {
        this.creditService = creditService;
        this.requestService = requestService;
    }

    @GetMapping("/create")
    public String createOnlineRequestForm(Long creditId, Model model) {
        model.addAttribute("credit", creditService.findById(creditId));
        return "online-request";
    }

    @PostMapping("/create")
    public String createOnlineRequest(Request request) {
        requestService.create(request);
        return "redirect:/";
    }

    @GetMapping("/delete-{id}")
    public String deleteOnlineRequest(@PathVariable("id") Long requestId) {
        requestService.deleteById(requestId);
        return "redirect:/cabinet";
    }

    @GetMapping("/update-{id}")
    public String updateOnlineRequestForm(@PathVariable("id") Long requestId, Model model) {
        Request request = requestService.findById(requestId);
        model.addAttribute("request", request);
        model.addAttribute("credit", request.getCredit());
        return "/update-request";
    }

    @PostMapping("/update-{id}")
    public String updateOnlineRequest(Request request) {
        requestService.update(request);
        return "redirect:/cabinet";
    }
}
