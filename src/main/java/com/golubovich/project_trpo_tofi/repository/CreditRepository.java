package com.golubovich.project_trpo_tofi.repository;

import com.golubovich.project_trpo_tofi.model.Credit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditRepository extends CrudRepository<Credit, Long> {

    @Query(value = "SELECT Credits.* FROM Credits" +
            " JOIN Banks ON Banks.id = Credits.bank_id" +
            " WHERE Banks.id = ?1", nativeQuery = true)
    List<Credit> findAllByBankId(Long bankId);
}
