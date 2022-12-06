package com.golubovich.project_trpo_tofi.controller;

import com.golubovich.project_trpo_tofi.model.Bank;
import com.golubovich.project_trpo_tofi.model.Role;
import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.model.UserDetails;
import com.golubovich.project_trpo_tofi.repository.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final ResponseRepository responseRepository;
    private final CreditRepository creditRepository;
    private final BankRepository bankRepository;
    private final CreditTermRateVariantRepository creditTermRateVariantRepository;



    public AdminController(UserRepository userRepository, RequestRepository requestRepository,
                           ResponseRepository responseRepository, CreditRepository creditRepository,
                           BankRepository bankRepository,CreditTermRateVariantRepository creditTermRateVariantRepository) {
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.responseRepository = responseRepository;
        this.creditRepository = creditRepository;
        this.bankRepository = bankRepository;
        this.creditTermRateVariantRepository = creditTermRateVariantRepository;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAllWithDetails());
        return "admin/users";
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('admin:write')")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userRepository.findByIdWithDetails(id));
        model.addAttribute("model_name", "user");
        return "admin/model-show";
    }

    @GetMapping("/requests")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllRequests(Model model) {
        model.addAttribute("requests", requestRepository.findAll());
        return "admin/requests";
    }

    @GetMapping("/requests/{id}")
    @PreAuthorize("hasAuthority('admin:write')")
    public String getRequestById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("request", requestRepository.findById(id).get());
        model.addAttribute("model_name", "request");
        return "admin/model-show";
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
    @PreAuthorize("hasAuthority('admin:write')")
    public String getCreditById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("credit", creditRepository.findById(id).get());
        model.addAttribute("model_name", "credit");
        return "admin/model-show";
    }

    @GetMapping("/banks")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllBanks(Model model) {
        model.addAttribute("banks", bankRepository.findAll());
        return "admin/banks";
    }

    @GetMapping("/banks/{id}")
    @PreAuthorize("hasAuthority('admin:write')")
    public String getBankById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("bank", bankRepository.findById(id).get());
        model.addAttribute("model_name", "bank");
        return "admin/model-show";
    }

    @GetMapping("/credits/{id}/variants")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllCreditTermRateVariantByCreditId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("creditVariants", creditRepository.findById(id).get().getCreditTermRateVariants());
        return "admin/credit-variants";
    }

    @GetMapping("/credits/{id2}/variants/{id}")
    @PreAuthorize("hasAuthority('admin:write')")
    public String getCreditTermRateVariantById(@PathVariable("id") Long id, Model model, @PathVariable String id2) {
        model.addAttribute("creditVariant", creditTermRateVariantRepository.findById(id).get());
        model.addAttribute("model_name", "creditVariant");
        return "admin/model-show";
    }

    @GetMapping("/banks/{id}/addresses-phones")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllBankAddressesPhones(@PathVariable("id") Long id, Model model) {
        model.addAttribute("bankAddresses", bankRepository.findById(id).get().getAddresses());
        return "admin/bank-addresses-phones";
    }


//    @PostMapping("/admin/banks/add")
//    public String addBank(Bank bank, Model model) {
//        Optional<User> optionalUserFromDb = userRepository.findByEmailOrPhone(user.getEmail(), user.getPhone());
//        User userFromDb = optionalUserFromDb.orElse(null);
//
//        if (userFromDb != null) {
//            if (userFromDb.getEmail().equals(user.getEmail())) {
//                model.addAttribute("msgExistingEmail", "Пользователь с таким email уже существует!");
//                return "redirect:/auth/signup";
//            }
//            else if (userFromDb.getEmail().equals(user.getPhone())) {
//                model.addAttribute("msgExistingPhone", "Пользователь с таким телефоном уже существует!");
//                return "redirect:/auth/signup";
//            }
//        }
//
//        user.setUserDetails(userDetails);
//        user.setRole(Role.USER);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//
//        return "redirect:/auth/login";
//    }
}
