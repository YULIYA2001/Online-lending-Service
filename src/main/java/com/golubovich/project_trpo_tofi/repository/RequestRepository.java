package com.golubovich.project_trpo_tofi.repository;

import com.golubovich.project_trpo_tofi.model.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    @Query(value = "SELECT REQUESTS.* FROM CREDITS" +
            " JOIN CREDIT_TERM_RATE_VARIANTS ON CREDITS.id = CREDIT_TERM_RATE_VARIANTS.credit_id" +
            " JOIN REQUESTS ON REQUESTS.credit_term_rate_variant_id = CREDIT_TERM_RATE_VARIANTS.id" +
            " WHERE CREDITS.bank_id = ?1 ORDER BY CREDITS.id", nativeQuery = true)
    List<Request> findByBankId(Long bankId);
}

