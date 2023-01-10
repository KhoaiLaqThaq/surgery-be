package com.hangnv.surgery.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hangnv.surgery.common.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MaterialBatchDto extends BaseDto {

    private Long id;
    private String code;
    private String unit;
    private Integer amount;
    private LocalDate receiptDate;
    private LocalDate expiredDate;
    private BigDecimal price;
    private BigDecimal sales;
    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedDate;

    private MaterialDto material;
    
    private Long materialTypeId;
    private String displayExpiredDate;
    
    // for search
    private LocalDateTime searchFromDate;
    private LocalDateTime searchToDate;
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    
}
