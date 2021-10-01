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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.production.dto.WorkOrder_tbl;

@RestController("workListRestController")
@RequestMapping("workListRest")
public class workListRestController {

	@Autowired
	DataSource dataSource;

	@RequestMapping(value = "/MI_Search1", method = RequestMethod.GET)
	public List<WorkOrder_tbl> MI_Search1(HttpServletRequest request)
			throws org.json.simple.parser.ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

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

		sql = "select CHILD_TBL_NO from DTL_TBL where CHILD_TBL_RMARK='Y'";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		CHILD_TBL_NO = 1;
		while (rs.next())
			CHILD_TBL_NO = rs.getInt("CHILD_TBL_NO");

		where = " where t1.WorkOrder_WorkStatus='" + CHILD_TBL_NO + "'" + "and t1.WorkOrder_ReceiptTime between '"
				+ startDate + " 00:00:00' and '" + endDate + " 23:59:59'";

		if (!PRODUCT_ITEM_CODE.equals(""))
			where += " and t1.WorkOrder_ItemCode='" + PRODUCT_ITEM_CODE + "'";

		if (!Machine_Code.equals(""))
			where += " and t1.WorkOrder_EquipCode='" + Machine_Code + "'";

		if ((String) obj.get("order_flag") != null) {
			where = " where (t1.WorkOrder_WorkStatus='292' or t1.WorkOrder_WorkStatus='293' or t1.WorkOrder_WorkStatus='294') ";
			where += "and t1.WorkOrder_ReceiptTime between '" + startDate + " 00:00:00' and '" + endDate
					+ " 23:59:59' and t1.WorkOrder_WorkStatus<>'293' ";
			if (!PRODUCT_ITEM_CODE.equals(""))
				where += " and t1.WorkOrder_ItemCode='" + PRODUCT_ITEM_CODE + "'";

			if (!Machine_Code.equals(""))
				where += " and t1.WorkOrder_EquipCode='" + Machine_Code + "'";
			where += " order by WorkOrder_EquipCode,t1.WorkOrder_ReceiptTime desc, t1.WorkOrder_No desc";
		} else
			where += " order by t1.WorkOrder_ReceiptTime desc, t1.WorkOrder_No desc";

		sql = "select (select sum(PRODUCTION_Volume) from PRODUCTION_MGMT_TBL2 a1 where a1.PRODUCTION_WorkOrder_ONo=t1.WorkOrder_ONo) WorkOrder_RQty2,t4.EQUIPMENT_INFO_NAME WorkOrder_EquipName,t1.*,t2.CHILD_TBL_Type WorkOrder_WorkStatusName,t3.PRODUCT_ITEM_NAME WorkOrder_ItemName,t3.*,(select a.Sales_SM_Last_Qty+a.Sales_SM_In_Qty-a.Sales_SM_Out_Qty from Sales_StockMatLX_tbl a where a.Sales_SM_Code=t1.WorkOrder_ItemCode) Qty from WorkOrder_tbl t1 inner join DTL_TBL t2 on t1.WorkOrder_WorkStatus = t2.CHILD_TBL_NO inner join PRODUCT_INFO_TBL t3 on t1.WorkOrder_ItemCode=t3.PRODUCT_ITEM_CODE inner join EQUIPMENT_INFO_TBL t4 on t1.WorkOrder_EquipCode=t4.EQUIPMENT_INFO_CODE";
		sql += where;

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
				// TODO Auto-generated method stub
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

	@RequestMapping(value = "/OrderUpdate", method = RequestMethod.GET)
	public String OrderUpdate(HttpServletRequest request, Principal principal) throws org.json.simple.parser.ParseException, SQLException {
		String workOrder_ONo = request.getParameter("workOrder_ONo");
		String workOrder_EquipCode = request.getParameter("workOrder_EquipCode");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT CHILD_TBL_NO FROM DTL_TBL where CHILD_TBL_RMARK='S'";
		con = dataSource.getConnection();
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		String CHILD_TBL_NO = "";
		while (rs.next())
			CHILD_TBL_NO = rs.getString("CHILD_TBL_NO");

		sql = "select * from WorkOrder_tbl where WorkOrder_WorkStatus='" + CHILD_TBL_NO + "' and WorkOrder_EquipCode='"
				+ workOrder_EquipCode + "'";
		con = dataSource.getConnection();
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();

		rs.last();
		int Count = rs.getRow();
		if (Count == 1) {
			rs.close();
			pstmt.close();
			con.close();

			return "OK";
		} else {
			// 접속자 정보
			String modifier = principal.getName();

			sql = "update WorkOrder_tbl set WorkOrder_WorkStatus='293',WorkOrder_StartTime=now(),WorkOrder_Worker='"
					+ modifier + "',WorkOrder_RQty=null,WorkOrder_CompleteTime=null" + " where workOrder_ONo='"
					+ workOrder_ONo + "'";
			System.out.println(sql);

			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();

			rs.close();
			pstmt.close();
			con.close();

			return "NO";
		}
	}

	@RequestMapping(value = "/OrderUpdate2", method = RequestMethod.GET)
	public String OrderUpdate2(HttpServletRequest request, Principal principal) throws SQLException {
		String workOrder_ONo = request.getParameter("workOrder_ONo");
		String workOrder_EquipCode = request.getParameter("workOrder_EquipCode");
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = null;

		// 접속자 정보
		String modifier = principal.getName();

		String sql = "update WorkOrder_tbl set WorkOrder_WorkStatus='294',WorkOrder_CompleteTime=now(),WorkOrder_Worker='"
				+ modifier
				+ "',WorkOrder_RQty=(select sum(PRODUCTION_Volume) from PRODUCTION_MGMT_TBL2 where PRODUCTION_WorkOrder_ONo = '"
				+ workOrder_ONo + "')\r\n" + " where workOrder_ONo='" + workOrder_ONo + "'";
		pstmt = con.prepareStatement(sql);
		pstmt.executeUpdate();
		pstmt.close();
		con.close();

		return "OK";
	}
}