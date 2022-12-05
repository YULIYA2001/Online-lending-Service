package com.golubovich.project_trpo_tofi.controller;

import com.golubovich.project_trpo_tofi.repository.CreditRepository;
import com.golubovich.project_trpo_tofi.repository.RequestRepository;
import com.golubovich.project_trpo_tofi.repository.ResponseRepository;
import com.golubovich.project_trpo_tofi.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final ResponseRepository responseRepository;

    private final CreditRepository creditRepository;


    public AdminController(UserRepository userRepository, RequestRepository requestRepository,
                           ResponseRepository responseRepository, CreditRepository creditRepository) {
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.responseRepository = responseRepository;
        this.creditRepository = creditRepository;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAllWithDetails());
        return "admin/users";
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('admin:change')")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userRepository.findByIdWithDetails(id));
        return "admin/user";
    }

    @GetMapping("/requests")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllRequests(Model model) {
        model.addAttribute("requests", requestRepository.findAll());
        return "admin/requests";
    }

    @GetMapping("/requests/{id}")
    @PreAuthorize("hasAuthority('admin:change')")
    public String getRequestById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("request", requestRepository.findById(id).get());
        return "admin/request";
    }

    @GetMapping("/requests/{id}/response")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getResponseToRequestById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("response", responseRepository.findById(id).get());
        model.addAttribute("model_name", "response");
        return "admin/model-show";
    }

    @GetMapping("/credits")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllCredits(Model model) {
        model.addAttribute("credits", creditRepository.findAll());
        return "admin/credits";
    }

    @GetMapping("/credits/{id}")
    @PreAuthorize("hasAuthority('admin:change')")
    public String getCreditById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("credit", creditRepository.findById(id).get());
        return "admin/credit";
    }
}
