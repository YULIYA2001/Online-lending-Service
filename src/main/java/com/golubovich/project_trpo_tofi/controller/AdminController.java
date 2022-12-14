package com.golubovich.project_trpo_tofi.controller;

import com.golubovich.project_trpo_tofi.model.*;
import com.golubovich.project_trpo_tofi.repository.*;
import com.golubovich.project_trpo_tofi.service.BankServiceImpl;
import com.golubovich.project_trpo_tofi.service.CreditServiceImpl;
import com.golubovich.project_trpo_tofi.service.RequestServiceImpl;
import com.golubovich.project_trpo_tofi.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final RequestServiceImpl requestService;
    private final CreditServiceImpl creditService;
    private final BankServiceImpl bankService;

    @Autowired
    public AdminController(UserServiceImpl userService, RequestServiceImpl requestService,
                           CreditServiceImpl creditService, BankServiceImpl bankService) {
        this.userService = userService;
        this.requestService = requestService;
        this.creditService = creditService;
        this.bankService = bankService;
    }


    // for super admin
//    @GetMapping("/users")
//    @PreAuthorize("hasAuthority('admin:read')")
//    public String getAllUsersRoleUser(Model model) {
//        model.addAttribute("users", userService.findAllRoleUserWithDetails());
//        return "admin/users";
//    }

    /*          users             */
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findByIdWithDetails(id));
        model.addAttribute("model_name", "user-for-admin");
        model.addAttribute("title", "Данные пользователя");
        model.addAttribute("backPageHref", "/admin/requests");
        model.addAttribute("backPageHrefText", "Вернуться к заявкам");
        return "admin/model-show";
    }



    /*          requests             */

    @GetMapping("/requests")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllRequests(Model model) {
        model.addAttribute("requests", requestService.findAllByBank());
        return "admin/requests";
    }

    @GetMapping("/requests/reject-{id}")
    @PreAuthorize("hasAuthority('admin:write')")
    public String updateOnlineRequestForm(@PathVariable("id") Long requestId, Model model) {
        requestService.updateStatusReject(requestId);
        return "redirect:/admin/requests";
    }

    @GetMapping("/requests/delete-{id}")
    @PreAuthorize("hasAuthority('admin:write')")
    public String deleteOnlineRequest(@PathVariable("id") Long requestId) {
        requestService.deleteById(requestId);
        return "redirect:/admin/requests";
    }


    /*       bank      */
    @GetMapping("/bank")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getBank(Model model) {
        String adminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("bank", bankService.findByAdminEmail(adminEmail));
        return "admin/bank";
    }

    @PostMapping("/bank/create")
    @PreAuthorize("hasAuthority('admin:write')")
    public String createBank(Bank bank) {
        bankService.createBank(bank);
        return "redirect:/admin/bank";
    }

    @PostMapping("/bank/edit-{id}")
    @PreAuthorize("hasAuthority('admin:write')")
    public String editBank(Bank bank, Model model) {
        if (!bankService.update(bank)) {
            model.addAttribute("bank", bankService.findById(bank.getId()));
            model.addAttribute("msgExistingBankName",
                    "Данные не сохранены. Название банка должно быть уникальным");
            return "/admin/bank";
        }
        return "redirect:/admin/bank";
    }

    @PostMapping("/bank/{id}/add-contacts")
    @PreAuthorize("hasAuthority('admin:write')")
    public String addBankContacts(@PathVariable("id") Long bankId, String address, String phone1,
                                  String phone2, String phone3) {
        bankService.addContacts(bankId, address, new String[] {phone1,phone2,phone3});
        return "redirect:/admin/bank";
    }

    @GetMapping("/bank/delete-contacts-{id}")
    @PreAuthorize("hasAuthority('admin:write')")
    public String deleteBankContacts(@PathVariable("id") Long bankAddressId) {
        bankService.deleteContacts(bankAddressId);
        return "redirect:/admin/bank";
    }


    /*       credits      */
    @GetMapping("/credits")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAllCredits(Model model) {
        model.addAttribute("credits", creditService.findAllForAdmin());
        return "admin/credits";
    }

    @GetMapping("/credits/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public String getCreditById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("credit", creditService.findById(id));
        model.addAttribute("model_name", "credit-for-admin");
        model.addAttribute("title", "Данные по кредиту");
        model.addAttribute("backPageHref", "/admin/requests");
        model.addAttribute("backPageHrefText", "Вернуться к заявкам");
        return "admin/model-show";
    }

    @GetMapping("/credits/create")
    @PreAuthorize("hasAuthority('admin:write')")
    public String createCreditPage(Model model) {
        return "/admin/credit-create";
    }

    @PostMapping("/credits/create")
    @PreAuthorize("hasAuthority('admin:write')")
    public String createCredit(Credit credit, CreditTermRateVariant creditVariant, String termRadio) {
        creditVariant.setTerm(creditVariant.getTerm()+termRadio);
        creditService.createCredit(credit, creditVariant);
        return "redirect:/admin/credits";
    }

    @GetMapping("/credits/edit-{id}")
    @PreAuthorize("hasAuthority('admin:write')")
    public String editCreditPage(@PathVariable("id") Long creditId, Model model) {
        model.addAttribute("credit", creditService.findById(creditId));
        return "/admin/credit-edit";
    }

    @PostMapping("/credits/edit-{id}")
    @PreAuthorize("hasAuthority('admin:write')")
    public String editCredit(Credit credit) {
        creditService.updateCredit(credit);
        return "redirect:/admin/credits/edit-{id}";
    }

    @PostMapping("/credits/edit-{id}/add-variants")
    @PreAuthorize("hasAuthority('admin:write')")
    public String addCreditTermRateVariant(@PathVariable("id") Long creditId,
                                           CreditTermRateVariant creditVariant, String termRadio) {
        creditVariant.setTerm(creditVariant.getTerm()+termRadio);
        creditService.addCreditVariant(creditId, creditVariant);
        return "redirect:/admin/credits/edit-{id}";
    }

    @GetMapping("/credits/edit-{id1}/delete-variant-{id2}")
    @PreAuthorize("hasAuthority('admin:write')")
    public String deleteCreditTermRateVariant(@PathVariable("id2") Long creditVariantId,
                                              @PathVariable("id1") Long creditId) {
        creditService.deleteCreditVariant(creditVariantId);
        return "redirect:/admin/credits/edit-{id1}";
    }

    @GetMapping("/credits/delete-{id}")
    @PreAuthorize("hasAuthority('admin:write')")
    public String deleteCredit(@PathVariable("id") Long creditId) {
        creditService.deleteCredit(creditId);
        return "redirect:/admin/credits";
    }

}
