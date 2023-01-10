package com.hangnv.surgery.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StatisticChartDto {
	private List<Integer> months;
	private List<BigDecimal> incomes;
	private List<Integer> sessions;
}
