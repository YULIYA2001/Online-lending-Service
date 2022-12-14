package com.golubovich.project_trpo_tofi.service.api;

import com.golubovich.project_trpo_tofi.model.Bank;

import java.util.List;

public interface BankService {
    Bank findById(Long id);

    List<Bank> findAll();

    Bank findByAdminEmail(String adminEmail);

    void createBank(Bank bank);

    void addContacts(Long bankId, String address, String[] phones);

    void deleteContacts(Long bankAddressId);

    boolean update(Bank bank);
}
