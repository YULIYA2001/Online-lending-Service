package com.golubovich.project_trpo_tofi.repository;

import com.golubovich.project_trpo_tofi.model.Request;
import com.golubovich.project_trpo_tofi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    @Query (value = "SELECT Requests.* FROM Credits" +
            " JOIN Credit_Term_Rate_Variants ON Credits.id = Credit_Term_Rate_Variants.credit_id" +
            " JOIN Requests ON Requests.credit_term_rate_variant_id = Credit_Term_Rate_Variants.id" +
//            " JOIN Request_Details ON Requests.details_id = Request_Details.id" +
            " WHERE Credits.bank_id = ?1 ORDER BY Credits.id", nativeQuery = true)
    List<Request> findByBankId(Long bankId);
}

