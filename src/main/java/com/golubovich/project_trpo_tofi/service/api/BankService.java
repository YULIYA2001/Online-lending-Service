package com.golubovich.project_trpo_tofi.service.api;

import com.golubovich.project_trpo_tofi.model.Bank;

import java.util.List;

public interface BankService {
    Bank findById(Long id);

    List<Bank> findAll();
}
