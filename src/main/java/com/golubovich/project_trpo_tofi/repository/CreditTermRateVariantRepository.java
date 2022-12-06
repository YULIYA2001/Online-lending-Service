package com.golubovich.project_trpo_tofi.repository;

import com.golubovich.project_trpo_tofi.model.CreditTermRateVariant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditTermRateVariantRepository extends CrudRepository<CreditTermRateVariant, Long> {
    Iterable<CreditTermRateVariant> findAll();
    Optional<CreditTermRateVariant> findById(Long id);
}