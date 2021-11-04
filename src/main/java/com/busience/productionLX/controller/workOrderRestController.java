package com.busience.productionLX.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.productionLX.dto.WorkOrder_tbl;
import com.busience.salesLX.dto.Sales_OrderMasterList_tbl;
import com.busience.salesLX.dto.Sales_StockMat_tbl;

@RestController("workOrderRestController")
@RequestMapping("workOrderRest")
public class workOrderRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbctemplate;

	// 위 그리드 재고수량
	@RequestMapping(value = "/MI_Search1", method = RequestMethod.GET)
	public List<Sales_StockMat_tbl> MI_Search1(HttpServletRequest request) throws SQLException {
		String workOrder_ItemCode = request.getParameter("workOrder_ItemCode");
		String sql = "select t1.*,t2.* from Sales_StockMatLX_tbl t1 inner join PRODUCT_INFO_TBL t2 on t1.Sales_SM_Code = t2.PRODUCT_ITEM_CODE where Sales_SM_Code='"
				+ workOrder_ItemCode + "'";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<Sales_StockMat_tbl> list = new ArrayList<Sales_StockMat_tbl>();
		while (rs.next()) {
			Sales_StockMat_tbl data = new Sales_StockMat_tbl();
			data.setSales_SM_Code(rs.getString("Sales_SM_Code"));
			data.setSales_SM_Name(rs.getString("PRODUCT_ITEM_NAME"));
			data.setSales_SM_Last_Qty(rs.getInt("Sales_SM_Last_Qty"));
			data.setSales_SM_In_Qty(rs.getInt("Sales_SM_In_Qty"));
			data.setSales_SM_Out_Qty(rs.getInt("Sales_SM_Out_Qty"));
			data.setSales_SM_Prcs_Date(rs.getString("Sales_SM_Prcs_Date"));
			list.add(data);
		}

		return list;
	}

	// 수주현황
	// ERROR : 작업지시 - 작업지시완료일을 입력하면 뒤에 조회되는 기능 : Sales_OrderMasterLX_tbl에 데이터가 많이 삭제되서 조회가 안됨 ex) A01001 코드가 없음
	@RequestMapping(value = "/MI_Search2", method = RequestMethod.GET)
	public List<Sales_OrderMasterList_tbl> MI_Search2(HttpServletRequest request) throws SQLException, ParseException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		String endDate = (String) obj.get("endDate");

		String sql = "select * from Sales_OrderMasterLX_tbl t1\r\n"
				+ "inner join Sales_OrderListLX_tbl t2 on t1.Sales_Order_mCus_No = t2.Sales_Order_lCus_No\r\n"
				+ "inner join Customer_tbl t3 on t1.Sales_Order_mCode=t3.Cus_Code\r\n"
				+ "inner join PRODUCT_INFO_TBL t4 on t2.Sales_Order_lCode = t4.PRODUCT_ITEM_CODE\r\n" + "where \r\n"
				+ "t1.Sales_Order_mDlvry_Date >= " + "'" + endDate + "'\r\n";

		if ((String) obj.get("Sales_Order_lCode") != null) {
			sql += "and\r\n";
			sql += "t2.Sales_Order_lCode =\r\n";
			sql += "'" + (String) obj.get("Sales_Order_lCode") + "'";
		}

		System.out.println(sql);

		List<Sales_OrderMasterList_tbl> list = new ArrayList<Sales_OrderMasterList_tbl>();

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Sales_OrderMasterList_tbl data = new Sales_OrderMasterList_tbl();
			data.setSales_Order_mCus_No(rs.getString("Sales_Order_mCus_No"));
			data.setSales_Order_mCode(rs.getString("Sales_Order_mCode"));
			data.setSales_Order_mName(rs.getString("Cus_Name"));
			data.setSales_Order_mDate(rs.getString("Sales_Order_mDate"));
			data.setSales_Order_mTotal(rs.getInt("Sales_Order_mTotal"));
			data.setSales_Order_mDlvry_Date(rs.getString("Sales_Order_mDlvry_Date"));
			data.setSales_Order_mRemarks(rs.getString("Sales_Order_mRemarks"));
			data.setSales_Order_mCheck(rs.getString("Sales_Order_mCheck"));
			data.setSales_Order_mModifier(rs.getString("Sales_Order_mModifier"));
			data.setSales_Order_mModify_Date(rs.getString("Sales_Order_mModify_Date"));
			data.setSales_Order_lNo(rs.getInt("Sales_Order_lNo"));
			data.setSales_Order_lCus_No(rs.getString("Sales_Order_lCus_No"));
			data.setSales_Order_lCode(rs.getString("Sales_Order_lCode"));
			data.setSales_Order_lName(rs.getString("PRODUCT_ITEM_NAME"));
			data.setSales_Order_lQty(rs.getInt("Sales_Order_lQty"));
			data.setSales_Order_lSum(rs.getInt("Sales_Order_lSum"));
			data.setSales_Order_lUnit_Price(rs.getInt("Sales_Order_lUnit_Price"));
			data.setSales_Order_lPrice(rs.getInt("Sales_Order_lPrice"));
			data.setSales_Order_lNot_Stocked(rs.getInt("Sales_Order_lNot_Stocked"));
			data.setSales_Order_Send_Clsfc(rs.getString("Sales_Order_Send_Clsfc"));
			data.setSales_Order_lInfo_Remark(rs.getString("Sales_Order_lInfo_Remark"));
			list.add(data);
		}

		return list;
	}

	// 맨위에 그리드 서치기능
	@RequestMapping(value = "/MI_Search3", method = RequestMethod.GET)
	public List<WorkOrder_tbl> MI_Search3(HttpServletRequest request) throws SQLException, ParseException {
		String startDate = request.getParameter("startDate")+" 00:00:00";
		String endDate = request.getParameter("endDate")+" 23:59:59";

		String sql = "select DATE_FORMAT(t1.WorkOrder_OrderTime, '%Y-%m-%d') WorkOrder_OrderTime2,t4.EQUIPMENT_INFO_NAME WorkOrder_EquipName,t1.*,t2.CHILD_TBL_Type WorkOrder_WorkStatusName,t3.PRODUCT_ITEM_NAME WorkOrder_ItemName,t3.*,(select a.Sales_SM_Last_Qty+a.Sales_SM_In_Qty-a.Sales_SM_Out_Qty from Sales_StockMat_tbl a where a.Sales_SM_Code=t1.WorkOrder_ItemCode) Qty from WorkOrder_tbl t1 inner join DTL_TBL t2 on t1.WorkOrder_WorkStatus = t2.CHILD_TBL_NO inner join PRODUCT_INFO_TBL t3 on t1.WorkOrder_ItemCode=t3.PRODUCT_ITEM_CODE inner join EQUIPMENT_INFO_TBL t4 on t1.WorkOrder_EquipCode=t4.EQUIPMENT_INFO_CODE where WorkOrder_WorkStatus = 242";
		String where = " and t1.WorkOrder_RegisterTime between ? and "
				+ "? order by t1.WorkOrder_RegisterTime desc, t1.WorkOrder_No desc";

		sql += where;

		System.out.println(sql);
		
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
				data.setWorkOrder_RegisterTime(rs.getString("WorkOrder_RegisterTime").substring(0, 10));
				data.setWorkOrder_ReceiptTime(rs.getString("WorkOrder_ReceiptTime"));

				data.setWorkOrder_OrderTime(rs.getString("WorkOrder_OrderTime2"));
				data.setWorkOrder_CompleteOrderTime(rs.getString("WorkOrder_CompleteOrderTime").substring(0, 10));
				data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));
				data.setWorkOrder_WorkStatus(rs.getString("WorkOrder_WorkStatus"));
				data.setWorkOrder_WorkStatusName(rs.getString("WorkOrder_WorkStatusName"));
				data.setWorkOrder_WorkStatusName(rs.getString("WorkOrder_WorkStatusName"));
				data.setWorkOrder_Worker(rs.getString("WorkOrder_Worker"));
				data.setWorkOrder_Remark(rs.getString("WorkOrder_Remark"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				data.setPRODUCT_UNIT_PRICE(rs.getString("PRODUCT_UNIT_PRICE"));
				data.setQty(rs.getString("Qty"));
				data.setDbdata_flag("y");
				return data;
			}
		},startDate,endDate);
	}

	@RequestMapping(value = "/MO_Save", method = RequestMethod.GET)
	public String MO_Save(HttpServletRequest request) throws org.json.simple.parser.ParseException, SQLException {
		String originData = request.getParameter("data");
		
		JSONParser parser = new JSONParser();
		JSONArray dataList = (JSONArray) parser.parse(originData);

		HttpSession session = request.getSession();

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn.setAutoCommit(false);

			if (dataList.size() > 0) {
				for (int i = 0; i < dataList.size(); i++) {
					JSONObject datas = (JSONObject) dataList.get(i);

					System.out.println(datas.toJSONString());

					// {"product_UNIT_PRICE":"10000","workOrder_ItemCode":"A01001",
					// "workOrder_CompleteOrderTime":"2021-04-26","dbdata_flag":"y",
					// "workOrder_WorkStatus":"291","workOrder_ItemName":"제오닉 밀폐 XE011",
					// "workOrder_RegisterTime":"2021-04-23 12:43:11.0","workOrder_Worker":null,
					// "workOrder_WorkStatusName":"미접수","workOrder_RQty":null,"workOrder_Remark":"3",
					// "product_INFO_STND_1":"160ml","workOrder_ONo":"202104023-A01001-3",
					// "qty":"161","workOrder_ReceiptTime":null,"workOrder_PQty":"30",
					// "workOrder_OrderTime":"2021-04-23","workOrder_CompleteTime":null}
					
					String dbdata_flag = (String) datas.get("dbdata_flag");
					String sql = "";

					String workOrder_Remark = (String) datas.get("workOrder_Remark");
					if (workOrder_Remark == null)
						workOrder_Remark = "";

					if (dbdata_flag == null) {
						sql = "SELECT count(*)+1 WorkOrder_ONo FROM WorkOrder_tbl a1 where LEFT(WorkOrder_ONo,8) = CURDATE()";
						//sql = "SELECT count(*)+1 WorkOrder_ONo FROM WorkOrder_tbl where date_format(WorkOrder_RegisterTime,\"%Y-%m-%d\") = curdate()";
						pstmt = conn.prepareStatement(sql);
						rs = pstmt.executeQuery();
						int WorkOrder_No = 1;
						while (rs.next())
							WorkOrder_No = rs.getInt("WorkOrder_ONo");

						String WorkOrder_No_Value = "";
						if (WorkOrder_No <= 9)
							WorkOrder_No_Value = "0" + String.valueOf(WorkOrder_No);
						else
							WorkOrder_No_Value = String.valueOf(WorkOrder_No);

						sql = "select CHILD_TBL_NO from DTL_TBL where CHILD_TBL_RMARK='n'";
						pstmt = conn.prepareStatement(sql);
						rs = pstmt.executeQuery();
						int CHILD_TBL_NO = 1;
						while (rs.next())
							CHILD_TBL_NO = rs.getInt("CHILD_TBL_NO");

						SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date time = new Date();

						sql = "INSERT INTO `WorkOrder_tbl`\r\n" + "(" + "`WorkOrder_No`,\r\n" + "`WorkOrder_ONo`,\r\n"
								+ "`WorkOrder_ItemCode`,\r\n" + "`WorkOrder_EquipCode`,\r\n" + "`WorkOrder_PQty`,\r\n"
								+ "`WorkOrder_WorkStatus`,\r\n" + "`WorkOrder_OrderTime`,\r\n"
								+ "`WorkOrder_CompleteOrderTime`,\r\n" + "`WorkOrder_Remark`\r\n" + ")\r\n"
								+ "VALUES\r\n" + "(" + "'" + WorkOrder_No + "'\r\n" + ",\r\n" + "'"
								+ datas.get("workOrder_ONo") + WorkOrder_No_Value + "'\r\n" + ",\r\n" + "'"
								+ datas.get("workOrder_ItemCode") + "'\r\n" + ",\r\n" + "'"
								+ datas.get("workOrder_EquipCode") + "'\r\n" + ",\r\n" + "'"
								+ datas.get("workOrder_PQty") + "'\r\n" + ",\r\n" + "'" + CHILD_TBL_NO + "'\r\n"
								+ ",\r\n" + "'" + format2.format(time) + "'\r\n" + ",\r\n" + "'"
								+ datas.get("workOrder_CompleteOrderTime") + "'\r\n" + ",\r\n" + "'" + workOrder_Remark
								+ "'\r\n" + ")";

						System.out.println(sql);

						pstmt = conn.prepareStatement(sql);
						pstmt.executeUpdate();
					} else {
						SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date time = new Date();

						String workOrder_ItemCode = (String) datas.get("workOrder_ItemCode");
						String workOrder_ONo = (String) datas.get("workOrder_ONo");
						String workOrder_OrderTime_t = (String) datas.get("workOrder_OrderTime");
						LocalTime now = LocalTime.now();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
						String formatedNow = now.format(formatter);
						String workOrder_OrderTime = workOrder_OrderTime_t + " " + formatedNow;

						sql = "UPDATE `WorkOrder_tbl`\r\n" + "SET\r\n" + "`WorkOrder_ONo` = \r\n" + "'"
								+ workOrder_ONo.replace(workOrder_ONo.split("-")[1], workOrder_ItemCode) + "',\r\n"
								+ "`WorkOrder_ItemCode` = \r\n" + "'" + workOrder_ItemCode + "',\r\n"
								+ "`WorkOrder_PQty` = \r\n" + "'" + datas.get("workOrder_PQty") + "',\r\n"
								+ "`WorkOrder_OrderTime` = \r\n" + "'" + workOrder_OrderTime + "',\r\n"
								+ "`WorkOrder_CompleteOrderTime` =\r\n" + "'" + datas.get("workOrder_CompleteOrderTime")
								+ "',\r\n" + "`WorkOrder_Remark` = \r\n" + "'" + workOrder_Remark + "'\r\n"
								+ "WHERE `WorkOrder_ONo` = \r\n" + "'" + datas.get("workOrder_ONo") + "'";

						System.out.println(sql);

						pstmt = conn.prepareStatement(sql);
						pstmt.executeUpdate();
					}
				}
			}

			conn.commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			conn.setAutoCommit(true);
			conn.rollback();
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null) {
				conn.setAutoCommit(true);
				conn.close();
			}
		}

		return "";
	}

}
