package com.golubovich.project_trpo_tofi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"request"})
@EqualsAndHashCode(exclude = {"request"})
@Table(name = "RequestDetails")
public class RequestDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision=18, scale=2, nullable = false)
    private BigDecimal income;

    @Column(nullable = false)
    private int creditsCount;

    @Column(precision=18, scale=2, nullable = false)
    private BigDecimal creditsPayments;

    @OneToOne(mappedBy = "details")
    private Request request;
}

