package com.golubovich.project_trpo_tofi.repository;

import com.golubovich.project_trpo_tofi.model.Bank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends CrudRepository<Bank, Long> {

    @Query(value = "SELECT BANKS.* FROM Banks" +
            " JOIN USERS ON BANKS.admin_id = USERS.id" +
            " WHERE USERS.email = ?1", nativeQuery = true)
    Optional<Bank> findByAdminEmail(String adminEmail);

    @Query(value = "SELECT BANKS.* FROM BANKS WHERE BANKS.name = ?1", nativeQuery = true)
    Optional<Bank> findByName(String name);
}
