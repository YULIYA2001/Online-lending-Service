package com.golubovich.project_trpo_tofi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"requests"})
@EqualsAndHashCode(exclude = {"requests"})
@Table(name = "CreditTermRateVariants")
public class CreditTermRateVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double rate;

    @Column(nullable = false, length = 20)
    private String term;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Credit_ID")
    private Credit credit;

    @OneToMany(mappedBy = "creditTermRateVariant", cascade = CascadeType.ALL)
    private Set<Request> requests;
}
