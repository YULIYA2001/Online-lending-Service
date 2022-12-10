package com.golubovich.project_trpo_tofi.service;

import com.golubovich.project_trpo_tofi.model.Credit;
import com.golubovich.project_trpo_tofi.repository.CreditRepository;
import com.golubovich.project_trpo_tofi.service.api.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditServiceImpl implements CreditService {
    private final CreditRepository creditRepository;

    @Autowired
    public CreditServiceImpl(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    public Credit findById(Long id) {
        return creditRepository.findById(id).orElse(null);
    }

    public List<Credit> findAll() {
        return (List<Credit>) creditRepository.findAll();
    }
}
