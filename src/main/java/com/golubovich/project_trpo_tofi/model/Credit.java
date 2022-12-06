package com.golubovich.project_trpo_tofi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"requests", "creditTermRateVariants"})
@EqualsAndHashCode(exclude = {"requests", "creditTermRateVariants"})
@Table(name = "Credits")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 200)
    private String creditType;

    @Column(precision=18, scale=2, nullable = false)
    private BigDecimal maxSum;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL)
    private Set<Request> requests;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Bank_ID")
    private Bank bank;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL)
    private Set<CreditTermRateVariant> creditTermRateVariants;
}
