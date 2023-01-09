package com.hangnv.surgery.search;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StatisticDto {
	private List<LocalDateTime> range;
	private long totalPatient; 	// benh nhan
	private long totalSession;	// phien kham
	private BigDecimal totalRevenue; // doanh thu
	private BigDecimal totalReceive; // tong nhap
}
