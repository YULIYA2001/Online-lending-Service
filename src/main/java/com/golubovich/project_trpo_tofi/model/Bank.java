package com.golubovich.project_trpo_tofi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"addresses", "credits"})
@EqualsAndHashCode(exclude = {"addresses", "credits"})
@Table(name = "Banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(length = 2000)
    private String aboutInfo;

    @Column(nullable = false)
    private int trustZone;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BankAddress> addresses;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Credit> credits;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "admin_id")
    private User admin;
}
