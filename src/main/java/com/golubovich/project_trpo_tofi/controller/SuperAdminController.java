package com.golubovich.project_trpo_tofi.controller;

import com.golubovich.project_trpo_tofi.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/super-admin")
public class SuperAdminController {

    private final UserServiceImpl userService;

    @Autowired
    public SuperAdminController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('superAdmin:write')")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAllWithDetails());
        return "admin/users";
    }

    @GetMapping("/users/delete-{id}")
    @PreAuthorize("hasAuthority('superAdmin:write')")
    public String deleteUser(@PathVariable("id") Long userId) {
        userService.deleteById(userId);
        return "redirect:/super-admin/users";
    }

    @GetMapping("/users/make-admin-{id}")
    @PreAuthorize("hasAuthority('superAdmin:write')")
    public String updateUser(@PathVariable("id") Long userId) {
        userService.updateUserMakeAdmin(userId);
        return "redirect:/super-admin/users";
    }
}
