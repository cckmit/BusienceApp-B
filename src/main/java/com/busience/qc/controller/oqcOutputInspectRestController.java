package com.busience.qc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.productionLX.dto.WorkOrder_tbl;
import com.busience.qc.dto.OQCInspectType_tbl;
import com.busience.qc.dto.OQCInspect_tbl;

@RestController("oqcOutputInspectRestController")
@RequestMapping("oqcOutputInspectRest")
public class oqcOutputInspectRestController {

	@Autowired
	DataSource dataSource;

	@RequestMapping(value = "/Search", method = RequestMethod.GET)
	public List<WorkOrder_tbl> Search(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		String startDate = (String) obj.get("startDate");
		String endDate = (String) obj.get("endDate");
		String PRODUCT_ITEM_CODE = (String) obj.get("PRODUCT_ITEM_CODE");

		List<WorkOrder_tbl> list = new ArrayList<WorkOrder_tbl>();

		String sql = "";
		String where = "";

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int CHILD_TBL_NO = 0;

		sql = "select CHILD_TBL_NO from DTL_TBL where CHILD_TBL_RMARK='E'";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		CHILD_TBL_NO = 1;
		while (rs.next())
			CHILD_TBL_NO = rs.getInt("CHILD_TBL_NO");

		where = " where t1.WorkOrder_WorkStatus='" + CHILD_TBL_NO + "'" + "and t1.WorkOrder_ReceiptTime between '"
				+ startDate + " 00:00:00' and '" + endDate + " 23:59:59'";

		sql = "select WorkOrder_RQty,t4.EQUIPMENT_INFO_NAME WorkOrder_EquipName,t1.*,t2.CHILD_TBL_Type WorkOrder_WorkStatusName,t3.PRODUCT_ITEM_NAME WorkOrder_ItemName,t3.*,(select a.Sales_SM_Last_Qty+a.Sales_SM_In_Qty-a.Sales_SM_Out_Qty from Sales_StockMat_tbl a where a.Sales_SM_Code=t1.WorkOrder_ItemCode) Qty from WorkOrder_tbl t1 inner join DTL_TBL t2 on t1.WorkOrder_WorkStatus = t2.CHILD_TBL_NO inner join PRODUCT_INFO_TBL t3 on t1.WorkOrder_ItemCode=t3.PRODUCT_ITEM_CODE inner join EQUIPMENT_INFO_TBL t4 on t1.WorkOrder_EquipCode=t4.EQUIPMENT_INFO_CODE";
		sql += where;

		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			String sql2 = "SELECT * FROM OQCInspect_tbl ot where OQCInspect_OqcInNo = '" + rs.getString("WorkOrder_ONo")
					+ "'";
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			ResultSet rs2 = pstmt2.executeQuery();
			if (rs2.next()) {
				pstmt2.close();
				rs2.close();
				continue;
			}

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
			data.setWorkOrder_OrderTime(rs.getString("WorkOrder_OrderTime"));
			data.setWorkOrder_CompleteOrderTime(rs.getString("WorkOrder_CompleteOrderTime").substring(0, 10));
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
	/*
	 * public List<InMat_tbl> Search(HttpServletRequest request) throws
	 * ParseException, SQLException { String originData =
	 * request.getParameter("data"); JSONParser parser = new JSONParser();
	 * JSONObject obj = (JSONObject) parser.parse(originData);
	 * 
	 * String sql =
	 * "select t1.*,t3.PRODUCT_ITEM_NAME InMat_Name,t3.PRODUCT_INFO_STND_1,t3.PRODUCT_UNIT,t4.CHILD_TBL_TYPE InMat_Rcv_Clsfc_Name from Sales_InMat_tbl t1 inner join PRODUCT_INFO_TBL t3 on t1.Sales_InMat_Code = t3.PRODUCT_ITEM_CODE inner join DTL_TBL t4 on t1.Sales_InMat_Rcv_Clsfc = t4.CHILD_TBL_NO "
	 * ; String where = " where t1.Sales_InMat_Date between '" +
	 * obj.get("startDate") + " 00:00:00' and '" + obj.get("endDate") +
	 * " 23:59:59' and t1.Sales_InMat_Check_1 is null and t1.Sales_InMat_Rcv_Clsfc <> 175 "
	 * ;
	 * 
	 * //where += obj.get("startDate"); String LotNo = (String) obj.get("LotNo");
	 * 
	 * if(!LotNo.trim().equals("")) where +=
	 * " and Sales_InMat_Lot_No = '"+LotNo+"'";
	 * 
	 * where += " order by t1.Sales_InMat_Date desc";
	 * 
	 * sql += where;
	 * 
	 * System.out.println(sql);
	 * 
	 * Connection conn = dataSource.getConnection(); PreparedStatement pstmt =
	 * conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();
	 * 
	 * List<InMat_tbl> list = new ArrayList<InMat_tbl>();
	 * 
	 * int i = 0;
	 * 
	 * while (rs.next()) { i++; InMat_tbl data = new InMat_tbl();
	 * //data.setIndex1(i); //data.setInMat_No(rs.getString("Sales_InMat_No"));
	 * //data.setInMat_Order_No(rs.getString("inMat_Order_No"));
	 * data.setInMat_Lot_No(rs.getString("Sales_InMat_Lot_No"));
	 * data.setInMat_Code(rs.getString("Sales_InMat_Code"));
	 * data.setInMat_Name(rs.getString("inMat_Name"));
	 * data.setInMat_Qty(rs.getInt("Sales_InMat_Qty"));
	 * //data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
	 * //data.setInMat_Price(rs.getInt("inMat_Price"));
	 * //data.setInMat_Client_Code(rs.getString("InMat_Client_Code"));
	 * //data.setInMat_Client_Name(rs.getString("InMat_Client_Name"));
	 * data.setInMat_Date(rs.getString("Sales_InMat_Date"));
	 * data.setInMat_Rcv_Clsfc(rs.getString("Sales_InMat_Rcv_Clsfc"));
	 * data.setInMat_Rcv_Clsfc_Name(rs.getString("InMat_Rcv_Clsfc_Name"));
	 * data.setInMat_dInsert_Time(rs.getString("Sales_InMat_dInsert_Time"));
	 * data.setInMat_Modifier(rs.getString("Sales_InMat_Modifier"));
	 * data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
	 * data.setPRODUCT_UNIT(rs.getString("PRODUCT_UNIT")); list.add(data); }
	 * 
	 * rs.close(); pstmt.close(); conn.close();
	 * 
	 * return list; }
	 */

	@RequestMapping(value = "/Search2", method = RequestMethod.GET)
	public List<OQCInspect_tbl> Search2(HttpServletRequest request) throws ParseException, SQLException {
		String sql = "select \r\n" + "			t1.OQCInspect_OqcInNo									-- 출하검사번호\r\n"
				+ "        ,	t1.OQCInspect_Date										-- 등록일자\r\n"
				+ "        ,	t1.OQCInspect_ItemCode PRODUCT_ITEM_CODE				-- 품목코드\r\n"
				+ "        ,	t1.PRODUCT_ITEM_NAME									-- 품목명\r\n"
				+ "        ,	t1.PRODUCT_INFO_STND_1									-- 규격\r\n"
				+ "        ,	t1.OQCInspect_Worker									-- 검사자 코드\r\n"
				+ "        ,	t1.OQCInspect_Worker_Name 								-- 검사자 이름\r\n"
				+ "        ,	t1.OQCInspect_Prcsn_Clsfc								-- 처리구분 코드\r\n"
				+ "        ,	t1.OQCInspect_Prcsn_Clsfc_Name							-- 처리구분 이름\r\n"
				+ "		,	t1.OQCInspect_DQty										-- 불량수량\r\n"
				+ "        ,	t1.OQCInspect_PQty										-- 합격수량\r\n"
				+ "        ,	t1.OQCInspect_SQty										-- 특채수량\r\n" + "        ,(\r\n"
				+ "				select sum(OQCInspectType_SaQty) from OQCInspectType_tbl\r\n"
				+ "                where OQCInspect_OqcInNo = OQCInspect_OqcChNo\r\n"
				+ "			) OQCInspect_SaQty										-- Sample수량\r\n"
				+ "		,	t1.OQCInspect_Problem									-- 문제점\r\n" + "from	\r\n"
				+ "		(\r\n" + "			select \r\n"
				+ "					a1.*,a2.CHILD_TBL_TYPE OQCInspect_Worker_Name,a3.CHILD_TBL_TYPE OQCInspect_Prcsn_Clsfc_Name,a4.*\r\n"
				+ "			from	OQCInspect_tbl a1\r\n"
				+ "            inner join (select * from DTL_TBL where NEW_TBL_CODE='23') a2 on a1.OQCInspect_Worker = a2.CHILD_TBL_NO\r\n"
				+ "            inner join (select * from DTL_TBL where NEW_TBL_CODE='22') a3 on a1.OQCInspect_Prcsn_Clsfc = a3.CHILD_TBL_NO\r\n"
				+ "            inner join PRODUCT_INFO_TBL a4 on a1.OQCInspect_ItemCode = a4.PRODUCT_ITEM_CODE\r\n"
				+ "            where date_format(OQCInspect_Date,'%Y-%m-%d') = curdate()\r\n" + "        ) t1 ";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<OQCInspect_tbl> list = new ArrayList<OQCInspect_tbl>();

		while (rs.next()) {
			OQCInspect_tbl data = new OQCInspect_tbl();
			data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
			data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
			data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			// data.setOQCInspect_Lot_No(rs.getString("OQCInspect_Lot_No"));
			// data.setOQCInspect_INo(rs.getString("OQCInspect_INo"));
			data.setOQCInspect_OqcInNo(rs.getString("OQCInspect_OqcInNo"));
			data.setOQCInspect_Prcsn_Clsfc(rs.getString("OQCInspect_Prcsn_Clsfc"));
			data.setOQCInspect_DQty(rs.getString("OQCInspect_DQty"));
			data.setOQCInspect_PQty(rs.getString("OQCInspect_PQty"));
			data.setOQCInspect_SQty(rs.getString("OQCInspect_SQty"));
			data.setOQCInspect_SaQty(rs.getString("OQCInspect_SaQty"));
			data.setOQCInspect_Date(rs.getString("OQCInspect_Date"));
			data.setOQCInspect_Worker(rs.getString("OQCInspect_Worker"));
			data.setOQCInspect_Problem(rs.getString("OQCInspect_Problem"));
			data.setOQCInspect_Prcsn_Clsfc_Name(rs.getString("OQCInspect_Prcsn_Clsfc_Name"));
			data.setOQCInspect_Worker_Name(rs.getString("OQCInspect_Worker_Name"));

			sql = "SELECT OQCInspectType_IQty,CONCAT(t2.CHILD_TBL_TYPE,\" (\",t3.CHILD_TBL_TYPE,\", 불\",t1.OQCInspectType_DQty,\")\") remark,t1.OQCInspectType_DQty FROM OQCInspectType_tbl t1 \r\n"
					+ "inner join DTL_TBL t2 on t1.OQCInspectType_Clsfc = t2.CHILD_TBL_NO \r\n"
					+ "inner join DTL_TBL t3 on t1.OQCInspectType_CRT = t3.CHILD_TBL_NO \r\n"
					+ "where t1.OQCInspect_OqcChNo='" + data.getOQCInspect_OqcInNo() + "'";

			String remark = "";
			String OQCInspectType_IQty = "";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs2 = pstmt.executeQuery();
			while (rs2.next()) {
				OQCInspectType_IQty = rs2.getString("OQCInspectType_IQty");

				if (rs2.getString("t1.OQCInspectType_DQty").equals("0"))
					continue;
				remark += rs2.getString("remark") + " ";
			}
			data.setRemark(remark);
			data.setOQCInspectType_IQty(OQCInspectType_IQty);

			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	@RequestMapping(value = "/Search3", method = RequestMethod.GET)
	public List<OQCInspectType_tbl> Search3(HttpServletRequest request) throws SQLException {
		String OQCInspect_OqcNo = request.getParameter("OQCInspect_OqcNo");

		List<OQCInspectType_tbl> list = new ArrayList<OQCInspectType_tbl>();
		String sql = "SELECT t1.*,t2.CHILD_TBL_TYPE OQCInspectType_Clsfc_Name,t3.CHILD_TBL_TYPE OQCInspectType_CRT_Name FROM OQCInspectType_tbl t1 inner join DTL_TBL t2 on t1.OQCInspectType_Clsfc=t2.CHILD_TBL_NO inner join DTL_TBL t3 on t1.OQCInspectType_CRT=t3.CHILD_TBL_NO where OQCInspect_OqcChNo = '"
				+ OQCInspect_OqcNo + "'";
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		OQCInspectType_tbl data;
		for (int i = 0; rs.next(); list.add(data)) {
			data = new OQCInspectType_tbl();
			data.setOQCInspectType_Clsfc_Code_Code(rs.getString("OQCInspectType_Clsfc"));
			data.setOQCInspectType_Clsfc_Code_Name(rs.getString("OQCInspectType_Clsfc_Name"));
			data.setOQCInspectType_CRT(rs.getString("OQCInspectType_CRT_Name"));
			data.setOQCInspectType_CRT_Name(rs.getString("OQCInspectType_CRT"));
			data.setOQCInspectType_DQty(rs.getString("OQCInspectType_DQty"));
			data.setOQCInspectType_IQty(rs.getString("OQCInspectType_IQty"));
			// data.setOQCInspectType_Lot_No(rs.getString("OQCInspectType_Lot_No"));
			data.setOQCInspectType_No(rs.getString("OQCInspectType_No"));
			data.setOQCInspectType_SaQty(rs.getString("OQCInspectType_SaQty"));
			data.setIndex1(i++);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	@RequestMapping(value = "/check_defect_num", method = RequestMethod.GET)
	public int check_defect_num() throws SQLException {
		String sql = "select * from DTL_TBL where NEW_TBL_CODE='26'";

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int CHILD_TBL_RMARK = 0;

		while (rs.next())
			CHILD_TBL_RMARK = rs.getInt("CHILD_TBL_RMARK");

		rs.close();
		pstmt.close();
		conn.close();

		return CHILD_TBL_RMARK;
	}

	@RequestMapping(value = "/Save1", method = { RequestMethod.GET })
	public void Save1(Model model, HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		String sql = "";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		sql = "select CHILD_TBL_NO from DTL_TBL where CHILD_TBL_RMARK='e'";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		int CHILD_TBL_NO = 1;
		while (rs.next())
			CHILD_TBL_NO = rs.getInt("CHILD_TBL_NO");
		sql = "update WorkOrder_tbl set WorkOrder_WorkStatus='" + CHILD_TBL_NO + "' where WorkOrder_ONo='"
				+ obj.get("OQCInspect_OqcNo") + "'";
		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();

		// 자재검사 테이블에 인서트하기
		sql = "INSERT INTO `OQCInspect_tbl`\r\n" + "(\r\n" + "`OQCInspect_OqcInNo`,\r\n" + "`OQCInspect_ItemCode`,\r\n"
				+ "`OQCInspect_Prcsn_Clsfc`,\r\n" + "`OQCInspect_DQty`,\r\n" + "`OQCInspect_PQty`,\r\n"
				+ "`OQCInspect_SQty`,\r\n" + "`OQCInspect_SaQty`,\r\n" + "`OQCInspect_Worker`,\r\n"
				+ "`OQCInspect_Problem`\r\n" + ")\r\n" + "VALUES\r\n" + "(\r\n" + "'" + obj.get("OQCInspect_OqcNo")
				+ "'" + ",\r\n" + "'" + obj.get("inMat_Code") + "'" + ",\r\n" + "'" + obj.get("OQCInspect_Prcsn_Clsfc")
				+ "'" + ",\r\n" + "'" + obj.get("OQCInspect_DQty") + "'" + ",\r\n" + "'" + obj.get("OQCInspect_PQty")
				+ "'" + ",\r\n" + "'" + obj.get("OQCInspect_SQty") + "'" + ",\r\n" + "'"
				+ obj.get("OQCInspectType_SaQty") + "'" + ",\r\n" + "'" + obj.get("OQCInspect_Worker") + "'" + ",\r\n"
				+ "'" + obj.get("OQCInspect_Problem") + "'" + ")";

		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();

		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();
	}

	@RequestMapping(value = "/Save2", method = { RequestMethod.GET })
	public void Save2(Model model, HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		// String LotNo = request.getParameter("LotNo");
		String OQCInspect_OqcNo = request.getParameter("OQCInspect_OqcNo");
		JSONParser parser = new JSONParser();
		JSONArray dataList = (JSONArray) parser.parse(originData);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn.setAutoCommit(false);

			if (dataList.size() > 0) {
				for (int i = 0; i < dataList.size(); i++) {
					JSONObject obj = (JSONObject) dataList.get(i);

					String OQCInspectType_CRTs = (String) obj.get("OQCInspectType_CRT");
					String sql = "SELECT CHILD_TBL_NO OQCInspectType_CRT FROM DTL_TBL WHERE CHILD_TBL_TYPE = '"
							+ OQCInspectType_CRTs.trim() + "'";
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					int OQCInspectType_CRT = 0;
					while (rs.next())
						OQCInspectType_CRT = rs.getInt("OQCInspectType_CRT");

					sql = "INSERT INTO `OQCInspectType_tbl`\r\n" + "(\r\n"
					// + "`OQCInspectType_Lot_No`,\r\n"
							+ "`OQCInspect_OqcChNo`,\r\n" + "`OQCInspectType_Clsfc`,\r\n" + "`OQCInspectType_IQty`,\r\n"
							+ "`OQCInspectType_SaQty`,\r\n" + "`OQCInspectType_CRT`,\r\n" + "`OQCInspectType_DQty`\r\n"
							+ ")\r\n" + "VALUES\r\n" + "(\r\n"
							// + "'"+LotNo+"'"
							// + ",\r\n"
							+ "'" + OQCInspect_OqcNo + "'" + ",\r\n" + "'" + obj.get("OQCInspectType_Clsfc_Code_Code")
							+ "'" + ",\r\n" + "'" + obj.get("OQCInspectType_IQty") + "'" + ",\r\n" + "'"
							+ obj.get("OQCInspectType_SaQty") + "'" + ",\r\n" + "'" + OQCInspectType_CRT + "'" + ",\r\n"
							+ "'" + obj.get("OQCInspectType_DQty") + "'" + ")";

					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);
					pstmt.executeUpdate();
				}
			}

			conn.commit();
		} catch (Exception ex) {
			System.out.println("---");
			System.out.println(ex.getMessage());
			System.out.println("---");
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
	}

	@RequestMapping(value = "/Update1", method = { RequestMethod.GET })
	public void Update1(Model model, HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		String sql = "UPDATE `OQCInspect_tbl`\r\n" + "SET\r\n" + "`OQCInspect_Prcsn_Clsfc` = \r\n" + "'"
				+ obj.get("OQCInspect_Prcsn_Clsfc") + "'" + ",\r\n" + "`OQCInspect_DQty` = \r\n" + "'"
				+ obj.get("OQCInspect_DQty") + "'" + ",\r\n" + "`OQCInspect_PQty` = \r\n" + "'"
				+ obj.get("OQCInspect_PQty") + "'" + ",\r\n" + "`OQCInspect_SQty` = \r\n" + "'"
				+ obj.get("OQCInspect_SQty") + "'" + ",\r\n" + "`OQCInspect_SaQty` = \r\n" + "'"
				+ obj.get("OQCInspectType_SaQty") + "'" + ",\r\n" + "`OQCInspect_Date` = \r\n" + "now()" + ",\r\n"
				+ "`OQCInspect_Worker` = \r\n" + "'" + obj.get("OQCInspect_Worker") + "'" + ",\r\n"
				+ "`OQCInspect_Problem` = \r\n" + "'" + obj.get("OQCInspect_Problem") + "'\r\n" + "WHERE \r\n"
				+ "`OQCInspect_OqcInNo` = \r\n" + "'" + obj.get("OQCInspect_OqcNo") + "'";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.execute();

		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();
	}

	@RequestMapping(value = "/Update2", method = { RequestMethod.GET })
	public void Update2(Model model, HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		String LotNo = request.getParameter("LotNo");
		JSONParser parser = new JSONParser();
		JSONArray dataList = (JSONArray) parser.parse(originData);

		Connection conn = dataSource.getConnection();
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		String OQCInspect_OqcNo = request.getParameter("OQCInspect_OqcNo");

		try {
			if (dataList.size() > 0) {
				for (int i = 0; i < dataList.size(); i++) {
					JSONObject obj = (JSONObject) dataList.get(i);
					System.out.println(obj);

					String OQCInspectType_CRTs = (String) obj.get("OQCInspectType_CRT");
					String sql = "SELECT CHILD_TBL_NO OQCInspectType_CRT FROM DTL_TBL WHERE CHILD_TBL_TYPE = '"
							+ OQCInspectType_CRTs.trim() + "'";

					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					int OQCInspectType_CRT = 0;
					while (rs.next())
						OQCInspectType_CRT = rs.getInt("OQCInspectType_CRT");

					// System.out.println("테스트");
					// System.out.println(sql);
					// System.out.println("테스트");

					sql = "UPDATE `OQCInspectType_tbl`\r\n" + "SET\r\n" + "`OQCInspectType_Clsfc` = \r\n" + "'"
							+ obj.get("OQCInspectType_Clsfc_Code_Code") + "'" + ",\r\n" + "`OQCInspectType_IQty` = \r\n"
							+ "'" + obj.get("OQCInspectType_IQty") + "'" + ",\r\n" + "`OQCInspectType_SaQty` = \r\n"
							+ "'" + obj.get("OQCInspectType_SaQty") + "'" + ",\r\n" + "`OQCInspectType_CRT` = \r\n"
							+ "'" + OQCInspectType_CRT + "'" + ",\r\n" + "`OQCInspectType_DQty` = \r\n" + "'"
							+ obj.get("OQCInspectType_DQty") + "'\r\n" + "WHERE \r\n" + "`OQCInspect_OqcChNo` = \r\n"
							+ "'" + OQCInspect_OqcNo + "'" + " and OQCInspectType_Clsfc=" + "'"
							+ obj.get("oqcinspectType_Clsfc_Code_Code") + "'";

					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);
					pstmt.executeUpdate();
				}
			}
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
	}
}
