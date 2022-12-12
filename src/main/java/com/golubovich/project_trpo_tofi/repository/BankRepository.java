package com.golubovich.project_trpo_tofi.repository;

import com.golubovich.project_trpo_tofi.model.Bank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends CrudRepository<Bank, Long> {
}
