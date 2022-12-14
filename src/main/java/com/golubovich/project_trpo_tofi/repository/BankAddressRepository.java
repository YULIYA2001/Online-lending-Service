package com.golubovich.project_trpo_tofi.repository;

import com.golubovich.project_trpo_tofi.model.BankAddress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BankAddressRepository extends CrudRepository<BankAddress, Long>  {

//    @Query(value = "INSERT INTO bank_addresses(address, bank_id) " +
//            "VALUES(?1, ?2)", nativeQuery = true)
//    void save(String address, Long bankId);

    @Query(value = "SELECT * FROM bank_addresses WHERE bank_addresses.address=?1 " +
            "AND bank_addresses.bank_id=?2)", nativeQuery = true)
    BankAddress find(String address, Long bankId);
}
