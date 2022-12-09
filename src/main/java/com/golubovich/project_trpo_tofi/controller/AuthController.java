package com.golubovich.project_trpo_tofi.controller;

import com.golubovich.project_trpo_tofi.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String getLoginPage(Model model, HttpSession httpSession) {
        Object error = httpSession.getAttribute("error");
        if (error != null) {
            model.addAttribute("msgNoUserFound", error);
            httpSession.removeAttribute("error");
        }
        return "login";
    }
}
