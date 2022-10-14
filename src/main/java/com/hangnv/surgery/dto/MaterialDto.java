package com.hangnv.surgery.dto;

import java.math.BigDecimal;
import java.util.List;

import com.hangnv.surgery.common.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MaterialDto extends BaseDto {

    private Long id;
    private String code;
    private String name;
    private String composition;
    private String suggest;
    private Integer total;
    private String unit;
    private BigDecimal price;
    private BigDecimal sales;

    private MaterialTypeDto materialType;
    private List<PrescriptionDto> prescriptions;
    
    private String keyword;
    private Long minPrice;
    private Long maxPrice;

}
