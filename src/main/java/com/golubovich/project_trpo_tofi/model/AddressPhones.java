package com.golubovich.project_trpo_tofi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "BankAddressPhones")
public class AddressPhones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 17)
    private String phone;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "BankAddress_ID")
    private BankAddress bankAddress;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public BankAddress getBankAddress() {
//        return bankAddress;
//    }
//
//    public void setBankAddress(BankAddress bankAddress) {
//        this.bankAddress = bankAddress;
//    }
//
//    public AddressPhones() {
//    }
//
//    @Override
//    public String toString() {
//        return "AddressPhones{" +
//                "id=" + id +
//                ", phone='" + phone + '\'' +
//                ", bankAddress=" + bankAddress +
//                '}';
//    }
}
