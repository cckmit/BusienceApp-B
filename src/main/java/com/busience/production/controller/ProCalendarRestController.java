package com.busience.production.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.production.dto.ProComparedInput;
import com.busience.production.service.ProCalendarService;

@RestController("proCalendarRestController")
@RequestMapping("proCalendarRest")
public class ProCalendarRestController {

	@Autowired
	ProCalendarService proCalendarService;

	// 총 생산량 조회
	@GetMapping("/CW_ListView")
	public List<ProComparedInput> totalWorkOrderRQty(ProComparedInput proComparedInput) {
		return proCalendarService.totalWorkOrderRQty(proComparedInput);
	}

	// 작업지시 총생산량
	@GetMapping("/CWOM_ListView")
	public List<ProComparedInput> workOrderTotalQty(ProComparedInput proComparedInput) throws ParseException {
		String back_days = null;

		// 2021년 11월에 총생산량이 있는 마지막 날짜
		// 그 날부터 -4일인 날(공휴일 제외)
		List<ProComparedInput> list = proCalendarService.workOrderTotalQty(proComparedInput);

		if (list.size() > 0) {
			back_days = list.get(list.size() - 1).getWorkOrder_CompleteTime();

		}

		Calendar start = Calendar.getInstance();
		// 날짜 형 변환
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

		// 시작시간 설정
		Date complete_date = null;

		// 작업완료일이 있는 경우
		if (back_days != null) {
			complete_date = sdf1.parse(back_days);
		} else if (back_days == null) {
			return list;
		}

		start.setTime(complete_date);

		start.add(Calendar.DATE, -4);

		int day = start.get(Calendar.DAY_OF_WEEK);
		// 토요일, 일요일 계산
		if (day == Calendar.SUNDAY) {
			start.add(Calendar.DATE, -2);

		} else if (day == Calendar.SATURDAY) {
			start.add(Calendar.DATE, -1);
		}

		// 날짜 형 변환
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

		String datestr1 = sdf2.format(start.getTime());

		System.out.println("datestr1 = " + datestr1);
		
		proComparedInput.setOutMat_Date(datestr1);
		System.out.println(proComparedInput);
		
		return proCalendarService.outMatList(proComparedInput);
	}
	

	// 자재 출고 수량
	@GetMapping("/CW_Count")
	public List<ProComparedInput> outMatTotalQty(ProComparedInput proComparedInput) throws ParseException {
		String back_days = null;
		
		// 2021년 11월에 총생산량이 있는 마지막 날짜
		// 그 날부터 -4일인 날(공휴일 제외)
		List<ProComparedInput> list = proCalendarService.workOrderTotalQty(proComparedInput);
		
		if(list.size() > 0) {
			back_days = list.get(list.size() - 1).getWorkOrder_CompleteTime();
		}
		
		Calendar start = Calendar.getInstance();
		
		// 날짜 형 변환
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		
		// 시작시간 설정
		Date complete_date = null;
		
		// 작업완료일이 있는 경우
		if (back_days != null) {
			complete_date = sdf1.parse(back_days);
		} else if (back_days == null) {
			return list;
		}
		
		start.setTime(complete_date);
		
		start.add(Calendar.DATE, -4);
		
		int day = start.get(Calendar.DAY_OF_WEEK);
		// 토요일, 일요일 계산
		if (day == Calendar.SUNDAY) {
			start.add(Calendar.DATE, -2);

		} else if (day == Calendar.SATURDAY) {
			start.add(Calendar.DATE, -1);
		}
		
		// 날짜 형 변환
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

		String datestr1 = sdf2.format(start.getTime());

		proComparedInput.setOutMat_Date(datestr1);
		
		return proCalendarService.outMatTotalQty(proComparedInput);
	}

}
