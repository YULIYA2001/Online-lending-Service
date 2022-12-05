package com.golubovich.project_trpo_tofi.repository;

import com.golubovich.project_trpo_tofi.model.Credit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditRepository extends CrudRepository<Credit, Long> {
    Iterable<Credit> findAll();
    Optional<Credit> findById(Long id);
}
