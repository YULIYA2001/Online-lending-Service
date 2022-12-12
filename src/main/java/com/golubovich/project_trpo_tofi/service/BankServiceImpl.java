package com.golubovich.project_trpo_tofi.service;

import com.golubovich.project_trpo_tofi.model.Bank;
import com.golubovich.project_trpo_tofi.repository.BankRepository;
import com.golubovich.project_trpo_tofi.service.api.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }


    public Bank findById(Long id) {
        return bankRepository.findById(id).orElse(null);
    }

    public List<Bank> findAll() {
        return (List<Bank>) bankRepository.findAll();
    }
}
