package com.hangnv.surgery.dto;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PrescriptionDto {

    private Long id;
    private String dosage;
    private Integer amount;
    private String unit;
    private BigDecimal totalPrice;
    private String note;

    private SessionDto session;
    private MaterialDto material;

}