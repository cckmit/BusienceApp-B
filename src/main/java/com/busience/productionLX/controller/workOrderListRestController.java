package com.busience.productionLX.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.materialLX.dto.StockMat_tbl;
import com.busience.productionLX.dto.WorkOrder_tbl;

@RestController("workOrderListRestController")
@RequestMapping("workOrderListRest")
public class workOrderListRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbctemplate;

	@RequestMapping(value = "/MI_Search1", method = RequestMethod.GET)
	public List<WorkOrder_tbl> MI_Search1(HttpServletRequest request)
			throws org.json.simple.parser.ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println("obj = " + obj);
		String startDate = (String) obj.get("startDate");
		String endDate = (String) obj.get("endDate");
		String PRODUCT_ITEM_CODE = (String) obj.get("PRODUCT_ITEM_CODE");
		String Machine_Code = (String) obj.get("Machine_Code");

		List<WorkOrder_tbl> list = new ArrayList<WorkOrder_tbl>();

		String sql = "";
		String where = "";

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int CHILD_TBL_NO = 0;

		String WorkOrder_Check = (String) obj.get("WorkOrder_Check");
		if (WorkOrder_Check.equals("N")) {
			sql = "select CHILD_TBL_NO from DTL_TBL where CHILD_TBL_RMARK='N'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			CHILD_TBL_NO = 1;
			while (rs.next())
				CHILD_TBL_NO = rs.getInt("CHILD_TBL_NO");

			where = " where t1.WorkOrder_WorkStatus='" + CHILD_TBL_NO + "'" + "and t1.WorkOrder_RegisterTime between '"
					+ startDate + " 00:00:00' and '" + endDate + " 23:59:59'";
		} else if (WorkOrder_Check.equals("Y")) {
			sql = "select CHILD_TBL_NO from DTL_TBL where CHILD_TBL_RMARK='Y'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			CHILD_TBL_NO = 1;
			while (rs.next())
				CHILD_TBL_NO = rs.getInt("CHILD_TBL_NO");

			where = " where t1.WorkOrder_WorkStatus='" + CHILD_TBL_NO + "'" + "and t1.WorkOrder_ReceiptTime between '"
					+ startDate + " 00:00:00' and '" + endDate + " 23:59:59'";
		} else if (WorkOrder_Check.equals("E")) {
			sql = "select CHILD_TBL_NO from DTL_TBL where CHILD_TBL_RMARK='E'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			CHILD_TBL_NO = 1;
			while (rs.next())
				CHILD_TBL_NO = rs.getInt("CHILD_TBL_NO");

			where = " where t1.WorkOrder_WorkStatus='" + CHILD_TBL_NO + "'" + "and t1.WorkOrder_ReceiptTime between '"
					+ startDate + " 00:00:00' and '" + endDate + " 23:59:59'";
		} else if (WorkOrder_Check.equals("C2")) {
			sql = "select CHILD_TBL_NO from DTL_TBL where CHILD_TBL_RMARK='S'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			CHILD_TBL_NO = 1;
			while (rs.next())
				CHILD_TBL_NO = rs.getInt("CHILD_TBL_NO");

			where = " where t1.WorkOrder_WorkStatus='" + CHILD_TBL_NO + "'";
		}

		if (!PRODUCT_ITEM_CODE.equals(""))
			where += " and t1.WorkOrder_ItemCode='" + PRODUCT_ITEM_CODE + "'";

		if (!Machine_Code.equals(""))
			where += " and t1.WorkOrder_EquipCode='" + Machine_Code + "'";

		if (WorkOrder_Check.equals("Y") || WorkOrder_Check.equals("C")) {
			if ((String) obj.get("order_flag") != null) {
				where = " where (t1.WorkOrder_WorkStatus='292' or t1.WorkOrder_WorkStatus='293' or t1.WorkOrder_WorkStatus='294') ";
				where += "and t1.WorkOrder_ReceiptTime between '" + startDate + " 00:00:00' and '" + endDate
						+ " 23:59:59' and t1.WorkOrder_WorkStatus<>'293' ";
				where += " order by WorkOrder_EquipCode,t1.WorkOrder_ReceiptTime desc, t1.WorkOrder_No desc";
			} else
				where += " order by t1.WorkOrder_ReceiptTime desc, t1.WorkOrder_No desc";
		} else if (WorkOrder_Check.equals("C2"))
			where += "  order by t1.WorkOrder_RegisterTime desc, t1.WorkOrder_EquipCode";
		else
			where += "  order by t1.WorkOrder_RegisterTime desc, t1.WorkOrder_No desc";

		sql = "select (select sum(PRODUCTION_Volume) from PRODUCTION_MGMT_TBL2 a1 where a1.PRODUCTION_WorkOrder_ONo=t1.WorkOrder_ONo) WorkOrder_RQty2,t4.EQUIPMENT_INFO_NAME WorkOrder_EquipName,t1.*,t2.CHILD_TBL_Type WorkOrder_WorkStatusName,t3.PRODUCT_ITEM_NAME WorkOrder_ItemName,t3.*,(select a.Sales_SM_Last_Qty+a.Sales_SM_In_Qty-a.Sales_SM_Out_Qty from Sales_StockMatLX_tbl a where a.Sales_SM_Code=t1.WorkOrder_ItemCode) Qty from WorkOrder_tbl t1 inner join DTL_TBL t2 on t1.WorkOrder_WorkStatus = t2.CHILD_TBL_NO inner join PRODUCT_INFO_TBL t3 on t1.WorkOrder_ItemCode=t3.PRODUCT_ITEM_CODE inner join EQUIPMENT_INFO_TBL t4 on t1.WorkOrder_EquipCode=t4.EQUIPMENT_INFO_CODE";
		sql += where;

		System.out.println(sql);

		if ((String) obj.get("order_flag") != null) {
			// String sql2 = "select (select sum(PRODUCTION_Volume) from
			// PRODUCTION_MGMT_TBL2 a1 where a1.PRODUCTION_WorkOrder_ONo=t1.WorkOrder_ONo)
			// WorkOrder_RQty2,t4.EQUIPMENT_INFO_NAME
			// WorkOrder_EquipName,t1.*,t2.CHILD_TBL_Type
			// WorkOrder_WorkStatusName,t3.PRODUCT_ITEM_NAME WorkOrder_ItemName,t3.*,(select
			// a.Sales_SM_Last_Qty+a.Sales_SM_In_Qty-a.Sales_SM_Out_Qty from
			// Sales_StockMat_tbl a where a.Sales_SM_Code=t1.WorkOrder_ItemCode) Qty from
			// WorkOrder_tbl t1 inner join DTL_TBL t2 on t1.WorkOrder_WorkStatus =
			// t2.CHILD_TBL_NO inner join PRODUCT_INFO_TBL t3 on
			// t1.WorkOrder_ItemCode=t3.PRODUCT_ITEM_CODE inner join EQUIPMENT_INFO_TBL t4
			// on t1.WorkOrder_EquipCode=t4.EQUIPMENT_INFO_CODE where
			// t1.WorkOrder_WorkStatus='293' order by
			// WorkOrder_EquipCode,t1.WorkOrder_ReceiptTime desc, t1.WorkOrder_No desc";
			String sql2 = "select WorkOrder_RQty,t4.EQUIPMENT_INFO_NAME WorkOrder_EquipName,t1.*,t2.CHILD_TBL_Type WorkOrder_WorkStatusName,t3.PRODUCT_ITEM_NAME WorkOrder_ItemName,t3.*,(select a.Sales_SM_Last_Qty+a.Sales_SM_In_Qty-a.Sales_SM_Out_Qty from Sales_StockMatLX_tbl a where a.Sales_SM_Code=t1.WorkOrder_ItemCode) Qty from WorkOrder_tbl t1 inner join DTL_TBL t2 on t1.WorkOrder_WorkStatus = t2.CHILD_TBL_NO inner join PRODUCT_INFO_TBL t3 on t1.WorkOrder_ItemCode=t3.PRODUCT_ITEM_CODE inner join EQUIPMENT_INFO_TBL t4 on t1.WorkOrder_EquipCode=t4.EQUIPMENT_INFO_CODE where t1.WorkOrder_WorkStatus='293' order by WorkOrder_EquipCode,t1.WorkOrder_ReceiptTime desc, t1.WorkOrder_No desc";

			pstmt = conn.prepareStatement(sql2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				WorkOrder_tbl data = new WorkOrder_tbl();
				data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
				data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
				data.setWorkOrder_ItemName(rs.getString("WorkOrder_ItemName"));
				data.setWorkOrder_EquipCode(rs.getString("WorkOrder_EquipCode"));
				data.setWorkOrder_EquipName(rs.getString("WorkOrder_EquipName"));
				data.setWorkOrder_PQty(rs.getString("WorkOrder_PQty"));
				// data.setWorkOrder_RQty(rs.getString("WorkOrder_RQty2"));
				data.setWorkOrder_RQty(rs.getString("WorkOrder_RQty"));
				data.setWorkOrder_RegisterTime(rs.getString("WorkOrder_RegisterTime"));
				data.setWorkOrder_ReceiptTime(rs.getString("WorkOrder_ReceiptTime"));

				data.setWorkOrder_OrderTime(rs.getString("WorkOrder_OrderTime"));

				data.setWorkOrder_CompleteOrderTime(rs.getString("WorkOrder_CompleteOrderTime"));
				data.setWorkOrder_StartTime(rs.getString("WorkOrder_StartTime"));
				data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));
				data.setWorkOrder_WorkStatus(rs.getString("WorkOrder_WorkStatus"));
				data.setWorkOrder_WorkStatusName(rs.getString("WorkOrder_WorkStatusName"));
				data.setWorkOrder_WorkStatusName(rs.getString("WorkOrder_WorkStatusName"));
				data.setWorkOrder_Worker(rs.getString("WorkOrder_Worker"));
				data.setWorkOrder_Remark(rs.getString("WorkOrder_Remark"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				data.setPRODUCT_UNIT_PRICE(rs.getString("PRODUCT_UNIT_PRICE"));
				list.add(data);
			}

			pstmt.close();
			rs.close();
		}

		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			WorkOrder_tbl data = new WorkOrder_tbl();
			data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
			data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
			data.setWorkOrder_ItemName(rs.getString("WorkOrder_ItemName"));
			data.setWorkOrder_EquipCode(rs.getString("WorkOrder_EquipCode"));
			data.setWorkOrder_EquipName(rs.getString("WorkOrder_EquipName"));
			data.setWorkOrder_PQty(rs.getString("WorkOrder_PQty"));
			// data.setWorkOrder_RQty(rs.getString("WorkOrder_RQty2"));
			data.setWorkOrder_RQty(rs.getString("WorkOrder_RQty"));
			data.setWorkOrder_RegisterTime(rs.getString("WorkOrder_RegisterTime"));
			data.setWorkOrder_ReceiptTime(rs.getString("WorkOrder_ReceiptTime"));

			data.setWorkOrder_OrderTime(rs.getString("WorkOrder_OrderTime"));

			data.setWorkOrder_CompleteOrderTime(rs.getString("WorkOrder_CompleteOrderTime"));
			data.setWorkOrder_StartTime(rs.getString("WorkOrder_StartTime"));
			data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));
			data.setWorkOrder_WorkStatus(rs.getString("WorkOrder_WorkStatus"));
			data.setWorkOrder_WorkStatusName(rs.getString("WorkOrder_WorkStatusName"));
			data.setWorkOrder_WorkStatusName(rs.getString("WorkOrder_WorkStatusName"));
			data.setWorkOrder_Worker(rs.getString("WorkOrder_Worker"));
			data.setWorkOrder_Remark(rs.getString("WorkOrder_Remark"));
			data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			data.setPRODUCT_UNIT_PRICE(rs.getString("PRODUCT_UNIT_PRICE"));
			list.add(data);
		}

		list.sort(new Comparator<WorkOrder_tbl>() {
			@Override
			public int compare(WorkOrder_tbl o1, WorkOrder_tbl o2) {
				int a = Integer.parseInt(o1.getWorkOrder_EquipCode().substring(1));
				int b = Integer.parseInt(o2.getWorkOrder_EquipCode().substring(1));

				if (a == b)
					return 0;
				else if (a > b)
					return 1;
				else
					return -1;
			}
		});

		pstmt.close();
		rs.close();
		conn.close();
		return list;
	}

	@RequestMapping(value = "/workorderList_top", method = RequestMethod.GET)
	public List<WorkOrder_tbl> workorderList_top(HttpServletRequest request)
			throws org.json.simple.parser.ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		String startDate = (String) obj.get("startDate");
		String endDate = (String) obj.get("endDate");
		String PRODUCT_ITEM_CODE = (String) obj.get("PRODUCT_ITEM_CODE");
		String Machine_Code = (String) obj.get("Machine_Code");

		String sql = "SELECT	\r\n"
				+ "			t1.WorkOrder_No,								\r\n"
				+ "			t1.WorkOrder_ONo,								\r\n"
				+ "			t1.WorkOrder_ItemCode,\r\n"
				+ "			t3.EQUIPMENT_INFO_NAME WorkOrder_EquipName,							\r\n"
				+ "			t1.WorkOrder_EquipCode,	\r\n"
				+ "			t2.PRODUCT_ITEM_NAME WorkOrder_ItemName,					\r\n"
				+ "			t1.WorkOrder_PQty,								\r\n"
				+ "			t1.WorkOrder_RQty,								\r\n"
				+ "			DATE_FORMAT(t1.WorkOrder_RegisterTime, '%Y-%m-%d') WorkOrder_RegisterTime,						\r\n"
				+ "			DATE_FORMAT(t1.WorkOrder_ReceiptTime, '%Y-%m-%d') WorkOrder_ReceiptTime,						\r\n"
				+ "			DATE_FORMAT(t1.WorkOrder_OrderTime, '%Y-%m-%d') WorkOrder_OrderTime,							\r\n"
				+ "			DATE_FORMAT(t1.WorkOrder_StartTime, '%Y-%m-%d') WorkOrder_StartTime,							\r\n"
				+ "			DATE_FORMAT(t1.WorkOrder_CompleteOrderTime, '%Y-%m-%d') WorkOrder_CompleteOrderTime,					\r\n"
				+ "			DATE_FORMAT(t1.WorkOrder_CompleteTime, '%Y-%m-%d') WorkOrder_CompleteTime,						\r\n"
				+ "			t1.WorkOrder_WorkStatus,		\r\n"
				+ "			t4.CHILD_TBL_TYPE WorkOrder_WorkStatusName,				\r\n"
				+ "			t1.WorkOrder_Worker,							\r\n"
				+ "			t1.WorkOrder_Remark,\r\n"
				+ "			t2.PRODUCT_INFO_STND_1	\r\n"
				+ "			,(select a.Sales_SM_Last_Qty+a.Sales_SM_In_Qty-a.Sales_SM_Out_Qty from Sales_StockMatLX_tbl a where a.Sales_SM_Code=t1.WorkOrder_ItemCode) Qty \r\n"
				+ "FROM		WorkOrder_tbl t1\r\n"
				+ "LEFT JOIN	PRODUCT_INFO_TBL t2 ON t1.WorkOrder_ItemCode = t2.PRODUCT_ITEM_CODE\r\n"
				+ "LEFT JOIN	EQUIPMENT_INFO_TBL t3 ON t1.WorkOrder_EquipCode = t3.EQUIPMENT_INFO_CODE\r\n"
				+ "LEFT JOIN	DTL_TBL t4 ON t1.WorkOrder_WorkStatus = t4.CHILD_TBL_NO	\r\n"
				+ "WHERE			t1.WorkOrder_RegisterTime between ? AND ?\r\n"
				+ "AND 			t1.WorkOrder_WorkStatus = '242'\r\n"
				+ "AND			(t2.PRODUCT_ITEM_CODE = '"+((PRODUCT_ITEM_CODE==null || PRODUCT_ITEM_CODE=="")?"' OR 1=1":PRODUCT_ITEM_CODE)+")\r\n"
				+ "AND			(t3.EQUIPMENT_INFO_CODE = '"+((Machine_Code==null || Machine_Code=="")?"' OR 1=1":Machine_Code)+")\r\n"
				+ "ORDER BY 	t1.WorkOrder_RegisterTime DESC, t1.WorkOrder_No DESC";
		
		return jdbctemplate.query(sql, new RowMapper<WorkOrder_tbl>() {

			@Override
			public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkOrder_tbl data = new WorkOrder_tbl();
				data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
				data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
				data.setWorkOrder_ItemName(rs.getString("WorkOrder_ItemName"));
				data.setWorkOrder_EquipCode(rs.getString("WorkOrder_EquipCode"));
				data.setWorkOrder_EquipName(rs.getString("WorkOrder_EquipName"));
				data.setWorkOrder_PQty(rs.getString("WorkOrder_PQty"));
				data.setWorkOrder_RQty(rs.getString("WorkOrder_RQty"));
				data.setWorkOrder_RegisterTime(rs.getString("WorkOrder_RegisterTime"));
				data.setWorkOrder_ReceiptTime(rs.getString("WorkOrder_ReceiptTime"));

				data.setWorkOrder_OrderTime(rs.getString("WorkOrder_OrderTime"));
				data.setWorkOrder_CompleteOrderTime(rs.getString("WorkOrder_CompleteOrderTime"));
				data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));
				data.setWorkOrder_WorkStatus(rs.getString("WorkOrder_WorkStatus"));
				data.setWorkOrder_WorkStatusName(rs.getString("WorkOrder_WorkStatusName"));
				data.setWorkOrder_Worker(rs.getString("WorkOrder_Worker"));
				data.setWorkOrder_Remark(rs.getString("WorkOrder_Remark"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				data.setQty(String.valueOf(rs.getInt("Qty")));
				data.setDbdata_flag("y");
				return data;
			}
		},startDate,endDate);
	}

	@RequestMapping(value = "/workorderList_down", method = RequestMethod.GET)
	public List<WorkOrder_tbl> workorderList_down(HttpServletRequest request)
			throws org.json.simple.parser.ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		String startDate = (String) obj.get("startDate");
		String endDate = (String) obj.get("endDate");
		String PRODUCT_ITEM_CODE = (String) obj.get("PRODUCT_ITEM_CODE");
		String Machine_Code = (String) obj.get("Machine_Code");

		String sql = "SELECT	\r\n"
				+ "			t1.WorkOrder_No,								\r\n"
				+ "			t1.WorkOrder_ONo,								\r\n"
				+ "			t1.WorkOrder_ItemCode,\r\n"
				+ "			t3.EQUIPMENT_INFO_NAME WorkOrder_EquipName,							\r\n"
				+ "			t1.WorkOrder_EquipCode,	\r\n"
				+ "			t2.PRODUCT_ITEM_NAME WorkOrder_ItemName,					\r\n"
				+ "			t1.WorkOrder_PQty,								\r\n"
				+ "			t1.WorkOrder_RQty,								\r\n"
				+ "			DATE_FORMAT(t1.WorkOrder_RegisterTime, '%Y-%m-%d') WorkOrder_RegisterTime,						\r\n"
				+ "			DATE_FORMAT(t1.WorkOrder_ReceiptTime, '%Y-%m-%d') WorkOrder_ReceiptTime,						\r\n"
				+ "			DATE_FORMAT(t1.WorkOrder_OrderTime, '%Y-%m-%d') WorkOrder_OrderTime,							\r\n"
				+ "			DATE_FORMAT(t1.WorkOrder_StartTime, '%Y-%m-%d') WorkOrder_StartTime,							\r\n"
				+ "			DATE_FORMAT(t1.WorkOrder_CompleteOrderTime, '%Y-%m-%d') WorkOrder_CompleteOrderTime,					\r\n"
				+ "			DATE_FORMAT(t1.WorkOrder_CompleteTime, '%Y-%m-%d') WorkOrder_CompleteTime,						\r\n"
				+ "			t1.WorkOrder_WorkStatus,		\r\n"
				+ "			t4.CHILD_TBL_TYPE WorkOrder_WorkStatusName,				\r\n"
				+ "			t1.WorkOrder_Worker,							\r\n"
				+ "			t1.WorkOrder_Remark,\r\n"
				+ "			t2.PRODUCT_INFO_STND_1	\r\n"
				+ "			,(select a.Sales_SM_Last_Qty+a.Sales_SM_In_Qty-a.Sales_SM_Out_Qty from Sales_StockMatLX_tbl a where a.Sales_SM_Code=t1.WorkOrder_ItemCode) Qty \r\n"
				+ "FROM		WorkOrder_tbl t1\r\n"
				+ "LEFT JOIN	PRODUCT_INFO_TBL t2 ON t1.WorkOrder_ItemCode = t2.PRODUCT_ITEM_CODE\r\n"
				+ "LEFT JOIN	EQUIPMENT_INFO_TBL t3 ON t1.WorkOrder_EquipCode = t3.EQUIPMENT_INFO_CODE\r\n"
				+ "LEFT JOIN	DTL_TBL t4 ON t1.WorkOrder_WorkStatus = t4.CHILD_TBL_NO	\r\n"
				+ "WHERE			t1.WorkOrder_RegisterTime between ? AND ?\r\n"
				+ "AND 			t1.WorkOrder_WorkStatus = '243'\r\n"
				+ "AND			(t2.PRODUCT_ITEM_CODE = '"+((PRODUCT_ITEM_CODE==null || PRODUCT_ITEM_CODE=="")?"' OR 1=1":PRODUCT_ITEM_CODE)+")\r\n"
				+ "AND			(t3.EQUIPMENT_INFO_CODE = '"+((Machine_Code==null || Machine_Code=="")?"' OR 1=1":Machine_Code)+")\r\n"
				+ "ORDER BY 	t1.WorkOrder_RegisterTime DESC, t1.WorkOrder_No DESC";
		
		return jdbctemplate.query(sql, new RowMapper<WorkOrder_tbl>() {

			@Override
			public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkOrder_tbl data = new WorkOrder_tbl();
				data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
				data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
				data.setWorkOrder_ItemName(rs.getString("WorkOrder_ItemName"));
				data.setWorkOrder_EquipCode(rs.getString("WorkOrder_EquipCode"));
				data.setWorkOrder_EquipName(rs.getString("WorkOrder_EquipName"));
				data.setWorkOrder_PQty(rs.getString("WorkOrder_PQty"));
				data.setWorkOrder_RQty(rs.getString("WorkOrder_RQty"));
				data.setWorkOrder_RegisterTime(rs.getString("WorkOrder_RegisterTime"));
				data.setWorkOrder_ReceiptTime(rs.getString("WorkOrder_ReceiptTime"));

				data.setWorkOrder_OrderTime(rs.getString("WorkOrder_OrderTime"));
				data.setWorkOrder_CompleteOrderTime(rs.getString("WorkOrder_CompleteOrderTime"));
				data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));
				data.setWorkOrder_WorkStatus(rs.getString("WorkOrder_WorkStatus"));
				data.setWorkOrder_WorkStatusName(rs.getString("WorkOrder_WorkStatusName"));
				data.setWorkOrder_Worker(rs.getString("WorkOrder_Worker"));
				data.setWorkOrder_Remark(rs.getString("WorkOrder_Remark"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				data.setQty(String.valueOf(rs.getInt("Qty")));
				data.setDbdata_flag("y");
				return data;
			}
		},startDate,endDate);
	}

	@RequestMapping(value = "/OrderUpdate", method = RequestMethod.GET)
	public WorkOrder_tbl OrderUpdate(HttpServletRequest request, Principal principal)
			throws org.json.simple.parser.ParseException, SQLException {
		String workOrder_ONo = request.getParameter("workOrder_ONo");

		// 접속자 정보
		String modifier = principal.getName();

		String sql = "update WorkOrder_tbl set WorkOrder_WorkStatus='243',WorkOrder_ReceiptTime=now(),WorkOrder_Worker='"
				+ modifier + "'" + " where workOrder_ONo='" + workOrder_ONo + "'";
		
		String where = " AND WorkOrder_WorkStatus = '242'";
		
		sql += where;
		
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();

		pstmt.close();
		conn.close();

		return jdbctemplate.queryForObject("SELECT \r\n" + "					*,\r\n"
				+ "					t2.PRODUCT_ITEM_NAME WorkOrder_ItemName\r\n" + "		FROM WorkOrder_tbl t1\r\n"
				+ "		LEFT JOIN PRODUCT_INFO_TBL t2\r\n"
				+ "		ON t1.WorkOrder_ItemCode = t2.PRODUCT_ITEM_CODE where workOrder_ONo='" + workOrder_ONo + "'",
				new RowMapper<WorkOrder_tbl>() {
					@Override
					public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
						WorkOrder_tbl data = new WorkOrder_tbl();
						data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
						data.setWorkOrder_ReceiptTime(rs.getString("WorkOrder_ReceiptTime"));
						data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
						data.setWorkOrder_ItemName(rs.getString("WorkOrder_ItemName"));
						data.setWorkOrder_OrderTime(rs.getString("WorkOrder_OrderTime"));
						data.setWorkOrder_CompleteOrderTime(rs.getString("WorkOrder_CompleteOrderTime"));
						data.setWorkOrder_StartTime(rs.getString("WorkOrder_StartTime"));
						data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));
						data.setWorkOrder_WorkStatus(rs.getString("WorkOrder_WorkStatus"));
						return data;
					}
				});
	}

	@RequestMapping(value = "/OrderUpdate2", method = RequestMethod.GET)
	public WorkOrder_tbl OrderUpdate2(HttpServletRequest request, Principal principal)
			throws org.json.simple.parser.ParseException, SQLException {
		String workOrder_ONo = request.getParameter("workOrder_ONo");

		// 접속자 정보
		String modifier = principal.getName();

		String sql = "update WorkOrder_tbl set WorkOrder_WorkStatus='242',WorkOrder_ReceiptTime=now(),WorkOrder_Worker='"
				+ modifier + "',WorkOrder_ReceiptTime=null" + " where workOrder_ONo='" + workOrder_ONo + "'";
		
		String where = " AND WorkOrder_WorkStatus = '243'";
		
		sql += where;
		
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();

		pstmt.close();
		conn.close();

		return jdbctemplate.queryForObject("SELECT \r\n" + "					*,\r\n"
				+ "					t2.PRODUCT_ITEM_NAME WorkOrder_ItemName\r\n" + "		FROM WorkOrder_tbl t1\r\n"
				+ "		LEFT JOIN PRODUCT_INFO_TBL t2\r\n"
				+ "		ON t1.WorkOrder_ItemCode = t2.PRODUCT_ITEM_CODE where workOrder_ONo='" + workOrder_ONo + "'",
				new RowMapper<WorkOrder_tbl>() {
					@Override
					public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
						WorkOrder_tbl data = new WorkOrder_tbl();
						data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
						data.setWorkOrder_ReceiptTime(rs.getString("WorkOrder_ReceiptTime"));
						data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
						data.setWorkOrder_ItemName(rs.getString("WorkOrder_ItemName"));
						data.setWorkOrder_OrderTime(rs.getString("WorkOrder_OrderTime"));
						data.setWorkOrder_CompleteOrderTime(rs.getString("WorkOrder_CompleteOrderTime"));
						data.setWorkOrder_StartTime(rs.getString("WorkOrder_StartTime"));
						data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));
						data.setWorkOrder_WorkStatus(rs.getString("WorkOrder_WorkStatus"));
						return data;
					}
				});
	}

	@RequestMapping(value = "/MI_Search2", method = RequestMethod.GET)
	public List<StockMat_tbl> MI_Search2(HttpServletRequest request) throws SQLException {
		String workOrder_ItemCode = request.getParameter("workOrder_ItemCode");
		String sql = "select t1.*,t2.* from StockMatLX_tbl t1 inner join PRODUCT_INFO_TBL t2 on t1.SM_Code = t2.PRODUCT_ITEM_CODE where SM_Code='"
				+ workOrder_ItemCode + "'";

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<StockMat_tbl> list = new ArrayList<StockMat_tbl>();
		while (rs.next()) {
			StockMat_tbl data = new StockMat_tbl();
			data.setSM_Code(rs.getString("SM_Code"));
			data.setSM_Name(rs.getString("PRODUCT_ITEM_NAME"));
			data.setSM_Last_Qty(rs.getInt("SM_Last_Qty"));
			data.setSM_In_Qty(rs.getInt("SM_In_Qty"));
			data.setSM_Out_Qty(rs.getInt("SM_Out_Qty"));
			data.setSM_Prcs_Date(rs.getInt("SM_Prcs_Date"));
			list.add(data);
		}

		return list;
	}

}
