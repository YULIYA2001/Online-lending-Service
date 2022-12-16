package com.golubovich.project_trpo_tofi.service;

import com.golubovich.project_trpo_tofi.model.Bank;
import com.golubovich.project_trpo_tofi.model.Credit;
import com.golubovich.project_trpo_tofi.model.CreditTermRateVariant;
import com.golubovich.project_trpo_tofi.repository.CreditRepository;
import com.golubovich.project_trpo_tofi.repository.CreditTermRateVariantRepository;
import com.golubovich.project_trpo_tofi.service.api.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditServiceImpl implements CreditService {
    private final CreditRepository creditRepository;
    private final CreditTermRateVariantRepository variantRepository;
    private final BankServiceImpl bankService;

    @Autowired
    public CreditServiceImpl(CreditRepository creditRepository, CreditTermRateVariantRepository variantRepository, BankServiceImpl bankService) {
        this.creditRepository = creditRepository;
        this.variantRepository = variantRepository;
        this.bankService = bankService;
    }

    public Credit findById(Long id) {
        return creditRepository.findById(id).orElse(null);
    }

    public List<Credit> findAll() {
        return (List<Credit>) creditRepository.findAll();
    }

    public List<Credit> findAllForAdmin() {
        String adminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Bank bank = bankService.findByAdminEmail(adminEmail);
        if (bank == null) {
            return null;
        }
        return creditRepository.findAllByBankId(bank.getId());
    }

    public void createCredit(Credit credit, CreditTermRateVariant creditTermRateVariant) {
        String adminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Bank bank = bankService.findByAdminEmail(adminEmail);
        credit.setBank(bank);
        creditRepository.save(credit);

        creditTermRateVariant.setCredit(credit);
        variantRepository.save(creditTermRateVariant);
    }

    public void updateCredit(Credit credit) {
        Credit oldCredit = this.findById(credit.getId());
        if (oldCredit != null) {
            oldCredit.setMaxSum(credit.getMaxSum());
            creditRepository.save(oldCredit);
        }
    }

    public void addCreditVariant(Long creditId, CreditTermRateVariant creditVariant) {
        Credit credit = this.findById(creditId);
        creditVariant.setCredit(credit);
        creditVariant.setId(null);

        variantRepository.save(creditVariant);
    }

    public void deleteCreditVariant(Long creditVariantId) {
        variantRepository.deleteById(creditVariantId);
    }

    public void deleteCredit(Long creditId) {
        creditRepository.deleteById(creditId);
    }
}
