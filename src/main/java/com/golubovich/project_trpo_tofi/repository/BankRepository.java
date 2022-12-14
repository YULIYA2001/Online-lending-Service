package com.golubovich.project_trpo_tofi.repository;

import com.golubovich.project_trpo_tofi.model.Bank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends CrudRepository<Bank, Long> {

    @Query(value = "SELECT Banks.* FROM Banks" +
            " JOIN Users ON Banks.admin_id = Users.id" +
            " WHERE Users.email = ?1", nativeQuery = true)
    Optional<Bank> findByAdminEmail(String adminEmail);

    @Query(value = "SELECT Banks.* FROM Banks WHERE Banks.name = ?1", nativeQuery = true)
    Optional<Bank> findByName(String name);
}
