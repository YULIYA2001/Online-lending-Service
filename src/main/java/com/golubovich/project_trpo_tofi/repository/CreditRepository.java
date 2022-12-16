package com.golubovich.project_trpo_tofi.repository;

import com.golubovich.project_trpo_tofi.model.Credit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepository extends CrudRepository<Credit, Long> {

    @Query(value = "SELECT CREDITS.* FROM CREDITS" +
            " JOIN BANKS ON BANKS.id = CREDITS.bank_id" +
            " WHERE BANKS.id = ?1", nativeQuery = true)
    List<Credit> findAllByBankId(Long bankId);
}
