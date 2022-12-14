package com.golubovich.project_trpo_tofi.service.api;

import com.golubovich.project_trpo_tofi.model.Credit;
import com.golubovich.project_trpo_tofi.model.CreditTermRateVariant;

import java.util.List;

public interface CreditService {

    Credit findById(Long id);

    List<Credit> findAll();
    List<Credit> findAllForAdmin();

    void createCredit(Credit credit, CreditTermRateVariant creditTermRateVariant);

    void updateCredit(Credit credit);

    void addCreditVariant(Long creditId, CreditTermRateVariant creditVariant);

    void deleteCreditVariant(Long creditVariantId);

    void deleteCredit(Long creditId);
}
