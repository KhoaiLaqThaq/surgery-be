package com.hangnv.surgery.service;

import com.hangnv.surgery.dto.StatisticChartDto;
import com.hangnv.surgery.search.StatisticDto;

public interface IStatisticService {

	public StatisticDto getStatistic(StatisticDto query);
	public StatisticChartDto getStatisticChart();
	
}
