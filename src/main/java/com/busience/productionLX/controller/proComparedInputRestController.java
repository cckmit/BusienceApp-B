package com.busience.productionLX.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.material.dto.OutMat_tbl;
import com.busience.productionLX.dto.ProComparedInput;

@RestController("proComparedInputRestController")
@RequestMapping("proComparedInputRest")
public class proComparedInputRestController {

	@Autowired
	DataSource dataSource;

	// 작업지시 총생산량 조회
	@RequestMapping(value = "CW_ListView", method = RequestMethod.GET)
	public List<ProComparedInput> CW_ListView(HttpServletRequest request)
			throws ParseException, SQLException, java.text.ParseException {

		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println("obj = " + obj);
		Connection conn;
		PreparedStatement pstmt;
		ResultSet rs;
		String days_ago;

		String workOrdersql = "SELECT sum(WorkOrder_RQty) Total_RQty, date(WorkOrder_CompleteTime) WorkOrder_CompleteTime\r\n"
				+ "FROM WorkOrder_tbl wt\r\n" + "WHERE WorkOrder_WorkStatus='245' ";

		String where = " AND date_format(WorkOrder_CompleteTime, '%Y-%m') = '" + obj.get("startDate") + "'\r\n";

		workOrdersql += where;

		workOrdersql += " GROUP BY date(wt.WorkOrder_CompleteTime)";

		// System.out.println("where : " + where);
		System.out.println(workOrdersql);

		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(workOrdersql);
		rs = pstmt.executeQuery();

		List<ProComparedInput> list = new ArrayList<ProComparedInput>();

		while (rs.next()) {

			ProComparedInput data = new ProComparedInput();

			data.setTotal_RQty(rs.getInt("Total_RQty"));
			data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));

			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// 작업지시 총생산량 및 자재 출고 조회
	@RequestMapping(value = "CWOM_ListView", method = RequestMethod.GET)
	public List<ProComparedInput> CWOM_ListView(HttpServletRequest request)
			throws ParseException, SQLException, java.text.ParseException {

		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println("obj = " + obj);
		Connection conn;
		PreparedStatement pstmt;
		ResultSet rs;
		String back_days = null;

		// 2021년 11월에 총생산량이 있는 마지막 날짜
		// 그 날부터 -4일인 날(공휴일 제외)
		String workOrdersql = "SELECT date(WorkOrder_CompleteTime) WorkOrder_CompleteTime\r\n"
				+ "FROM WorkOrder_tbl wt\r\n" + "WHERE WorkOrder_WorkStatus='245' ";

		String where = " AND date_format(WorkOrder_CompleteTime, '%Y-%m') = '" + obj.get("startDate") + "'\r\n";

		workOrdersql += where;

		workOrdersql += " GROUP BY date(wt.WorkOrder_CompleteTime) ORDER BY date(wt.WorkOrder_CompleteTime) DESC LIMIT 1";

		// System.out.println("where : " + where);
		System.out.println(workOrdersql);

		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(workOrdersql);
		rs = pstmt.executeQuery();

		List<ProComparedInput> list = new ArrayList<ProComparedInput>();

		while (rs.next()) {

			ProComparedInput data = new ProComparedInput();

			data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));

			list.add(data);

			if (list.size() > 0) {
				back_days = list.get(list.size() - 1).getWorkOrder_CompleteTime();
				System.out.println("back_days = " + back_days);
			}
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

		Calendar outmatStart = Calendar.getInstance();
		// 날짜 형 변환
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

		String datestr1 = sdf2.format(start.getTime());

		System.out.println("datestr1 = " + datestr1);

		rs.close();
		pstmt.close();
		conn.close();

		List<OutMat_tbl> outMatlist = new ArrayList<OutMat_tbl>();

		// 자재 출고 조회(기준일 ~ 전 달 1일)
		String outMatsql = "SELECT omlt.OutMat_Code, pit.PRODUCT_ITEM_NAME OutMat_Name, SUM(omlt.OutMat_Qty) OutMat_Qty, omlt.OutMat_Consignee, dt2.CHILD_TBL_TYPE OutMat_Consignee_Name, date(OutMat_Date) AS OutMat_Date, LAST_DAY(OutMat_Date - interval 2 month) + interval 1 DAY AS Last_Month\r\n"
				+ "FROM OutMatLX_tbl omlt\r\n"
				+ "INNER JOIN DTL_TBL dt2 ON omlt.OutMat_Consignee = dt2.CHILD_TBL_NO\r\n"
				+ "INNER JOIN PRODUCT_INFO_TBL pit ON omlt.OutMat_Code = pit.PRODUCT_ITEM_CODE";

		String outMatwhere = " where DATE(OutMat_Date) BETWEEN LAST_DAY(OutMat_Date - interval 2 month) + interval 1 DAY AND '"
				+ datestr1 + "'" + " AND OutMat_Qty != 0 ";

		outMatsql += outMatwhere;

		outMatsql += " GROUP BY OutMat_Name, OutMat_Consignee, DATE(OutMat_Date) order by DATE(OutMat_Date) desc";

		System.out.println("outMatsql : " + outMatsql);
		// System.out.println(sql);

		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(outMatsql);
		rs = pstmt.executeQuery();

		List<ProComparedInput> OutMatList = new ArrayList<ProComparedInput>();

		while (rs.next()) {

			ProComparedInput data = new ProComparedInput();

			data.setOutMat_Code(rs.getString("OutMat_Code"));
			data.setOutMat_Name(rs.getString("OutMat_Name"));
			data.setOutMat_Qty(rs.getInt("OutMat_Qty"));
			data.setOutMat_Consignee(rs.getString("OutMat_Consignee"));
			data.setOutMat_Consignee_Name(rs.getString("OutMat_Consignee_Name"));
			data.setOutMat_Date(rs.getString("OutMat_Date"));
			data.setLast_Month(rs.getString("Last_Month"));

			OutMatList.add(data);

		}

		rs.close();
		pstmt.close();
		conn.close();

		return OutMatList;
	}

	// 자재 출고일 당 개수 구하기
	@RequestMapping(value = "CW_Count", method = RequestMethod.GET)
	public List<ProComparedInput> CW_Count(HttpServletRequest request)
			throws ParseException, SQLException, java.text.ParseException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println("obj = " + obj);
		Connection conn;
		PreparedStatement pstmt;
		ResultSet rs;

		String back_days = null;

		// 2021년 11월에 총생산량이 있는 마지막 날짜
		// 그 날부터 -4일인 날(공휴일 제외)
		String workOrdersql = "SELECT date(WorkOrder_CompleteTime) WorkOrder_CompleteTime\r\n"
				+ "FROM WorkOrder_tbl wt\r\n" + "WHERE WorkOrder_WorkStatus='245' ";

		String where = " AND date_format(WorkOrder_CompleteTime, '%Y-%m') = '" + obj.get("startDate") + "'\r\n";

		workOrdersql += where;

		workOrdersql += " GROUP BY date(wt.WorkOrder_CompleteTime) ORDER BY date(wt.WorkOrder_CompleteTime) DESC LIMIT 1";

		// System.out.println("where : " + where);
		System.out.println(workOrdersql);

		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(workOrdersql);
		rs = pstmt.executeQuery();

		List<ProComparedInput> list = new ArrayList<ProComparedInput>();

		while (rs.next()) {

			ProComparedInput data = new ProComparedInput();

			data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));

			list.add(data);

			if (list.size() > 0) {
				back_days = list.get(list.size() - 1).getWorkOrder_CompleteTime();
				System.out.println("back_days = " + back_days);
			}
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

		Calendar outmatStart = Calendar.getInstance();
		// 날짜 형 변환
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

		String datestr1 = sdf2.format(start.getTime());

		System.out.println("datestr1 = " + datestr1);

		rs.close();
		pstmt.close();
		conn.close();
		
		String outmatCountsql = "SELECT rocal.OutMat_Date, COUNT(date(rocal.OutMat_Date)) OutMat_Count\r\n" + "FROM \r\n"
				+ " (SELECT OutMat_Consignee, pit.PRODUCT_ITEM_NAME OutMat_Name, DATE(OutMat_Date) AS OutMat_Date\r\n"
				+ "FROM OutMatLX_tbl omlt\r\n"
				+ "INNER JOIN DTL_TBL dt2 ON omlt.OutMat_Consignee = dt2.CHILD_TBL_NO\r\n"
				+ "INNER JOIN PRODUCT_INFO_TBL pit ON omlt.OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n";
		
		String countWhere = "WHERE DATE(OutMat_Date) BETWEEN LAST_DAY(OutMat_Date - INTERVAL 2 MONTH) + INTERVAL 1 DAY AND '" + datestr1 + "' AND OutMat_Qty != 0\r\n"
				+ "GROUP BY  OutMat_Consignee, OutMat_Name, DATE(OutMat_Date)) AS rocal\r\n"
				+ "GROUP BY DATE(OutMat_Date) ORDER BY DATE(OutMat_Date) desc";
				
		outmatCountsql += countWhere;	

		// System.out.println("where : " + where);
		System.out.println(outmatCountsql);

		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(outmatCountsql);
		rs = pstmt.executeQuery();

		List<ProComparedInput> outMatCountlist = new ArrayList<ProComparedInput>();

		while (rs.next()) {

			ProComparedInput data = new ProComparedInput();

			data.setOutMat_Date(rs.getString("OutMat_Date"));
			data.setOutMat_Count(rs.getInt("OutMat_Count"));

			outMatCountlist.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return outMatCountlist;
	}
}
