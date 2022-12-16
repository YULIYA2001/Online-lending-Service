package com.golubovich.project_trpo_tofi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "BANK_ADDRESS_PHONES")
public class AddressPhones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 17)
    private String phone;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "BankAddress_ID")
    private BankAddress bankAddress;

    public AddressPhones(String phone, BankAddress bankAddress) {
        this.phone = phone;
        this.bankAddress = bankAddress;
    }
}
