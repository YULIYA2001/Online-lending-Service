package com.golubovich.project_trpo_tofi.service.api;

import com.golubovich.project_trpo_tofi.model.Credit;

import java.util.List;

public interface CreditService {

    Credit findById(Long id);

    List<Credit> findAll();
}
