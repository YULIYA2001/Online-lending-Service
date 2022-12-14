package com.golubovich.project_trpo_tofi.repository;

import com.golubovich.project_trpo_tofi.model.AddressPhones;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AddressPhonesRepository extends CrudRepository<AddressPhones, Long> {

//    @Query(value = "INSERT INTO bank_address_phones VALUES(?1, ?2)", nativeQuery = true)
//    AddressPhones save(String phone, Long bankAddressId);
}
