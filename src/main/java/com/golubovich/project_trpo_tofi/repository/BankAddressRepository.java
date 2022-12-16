package com.golubovich.project_trpo_tofi.repository;

import com.golubovich.project_trpo_tofi.model.BankAddress;
import org.springframework.data.repository.CrudRepository;

public interface BankAddressRepository extends CrudRepository<BankAddress, Long> {
}
