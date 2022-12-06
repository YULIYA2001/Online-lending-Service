package com.golubovich.project_trpo_tofi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"phones"})
@EqualsAndHashCode(exclude = {"phones"})
@Table(name="BankAddresses")
public class BankAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String address;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Bank_ID")
    private Bank bank;

    @OneToMany(mappedBy = "bankAddress", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AddressPhones> phones;
}