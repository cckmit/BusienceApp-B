package com.busience.monitoring.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TempChartDto {
	private String ChartTime;
	private float ChartTemp;
}
