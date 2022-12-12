package com.golubovich.project_trpo_tofi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Responses")
public class Response {
    @Id
    @Column(name = "request_id")
    private Long id;

    @Column(precision=18, scale=2, nullable = false)
    private BigDecimal sum;

    @Column(precision=18, scale=2, nullable = false)
    private BigDecimal payment;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime = new Date();

    @OneToOne
    @MapsId
    @JoinColumn(name = "request_id")
    private Request request;
}
