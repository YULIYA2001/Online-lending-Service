package com.golubovich.project_trpo_tofi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision=18, scale=2, nullable = false)
    private BigDecimal sum;

    @Column(nullable = false, length = 20)
    private String term;

    @Column(precision=18, scale=2, nullable = false)
    private BigDecimal income;

    @Column(nullable = false)
    private int creditsCount;

    @Column(precision=18, scale=2, nullable = false)
    private BigDecimal creditsPayments;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date start = new Date();

    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "User_ID")
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Credit_ID")
    private Credit credit;

    @OneToOne(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Response response;
}
