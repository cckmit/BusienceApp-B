package com.busience.standard.controller;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.Dto.PRODUCT_INFO_TBL;

@RestController("itemManageRestController")
@RequestMapping("itemManageRest")
public class itemManageRestController {

	@Autowired
	DataSource dataSource;

	@RequestMapping(value = "/itemManageRestSelect", method = RequestMethod.GET)
	public List<PRODUCT_INFO_TBL> itemManageRestSelect(HttpServletRequest request) throws SQLException {
		// ��ǰ ���� ���� list set
		List<PRODUCT_INFO_TBL> list = new ArrayList<PRODUCT_INFO_TBL>();

		String sql = "SELECT \r\n" 
				+ "A.PRODUCT_BUSINESS_PLACE,\r\n" 
				+ "A.PRODUCT_ITEM_CODE,\r\n"
				+ "A.PRODUCT_OLD_ITEM_CODE,\r\n" 
				+ "A.PRODUCT_ITEM_NAME,\r\n"
				+ "A.PRODUCT_INFO_STND_1,\r\n" 
				+ "A.PRODUCT_INFO_STND_2,\r\n"
				+ "A.PRODUCT_UNIT,\r\n" 
				+ "A.PRODUCT_MATERIAL,\r\n"
				+ "A.PRODUCT_MTRL_CLSFC,\r\n" 
				+ "A.PRODUCT_ITEM_CLSFC_1,\r\n"
				+ "A.PRODUCT_ITEM_CLSFC_2,\r\n" 
				+ "A.PRODUCT_SUBSID_MATL_MGMT,\r\n"
				+ "A.PRODUCT_ITEM_STTS,\r\n" 
				+ "A.PRODUCT_BASIC_WAREHOUSE,\r\n"
				+ "A.PRODUCT_SAVE_AREA,\r\n"
				+ "A.PRODUCT_SFTY_STOCK,\r\n"
				+ "A.PRODUCT_BUYER,\r\n"
				+ "A.PRODUCT_WRHSN_INSPC,\r\n" 
				+ "A.PRODUCT_USE_STATUS,\r\n"
				+ "A.PRODUCT_MODIFY_D,\r\n" 
				+ "t1.USER_NAME PRODUCT_MODIFIER,\r\n"
				+ "B.CHILD_TBL_TYPE AS PRODUCT_BUSINESS_PLACE_NAME,\r\n"
				+ "C.CHILD_TBL_TYPE AS PRODUCT_UNIT_NAME,\r\n"
				+ "D.CHILD_TBL_TYPE AS PRODUCT_MATERIAL_NAME,\r\n"
				+ "E.CHILD_TBL_TYPE AS PRODUCT_MTRL_CLSFC_NAME,\r\n"
				+ "F.CHILD_TBL_TYPE AS PRODUCT_ITEM_CLSFC_1_NAME,\r\n"
				+ "G.CHILD_TBL_TYPE AS PRODUCT_ITEM_CLSFC_2_NAME,\r\n"
				+ "H.CHILD_TBL_TYPE AS PRODUCT_ITEM_STTS_NAME,\r\n"
				+ "I.CHILD_TBL_TYPE AS PRODUCT_BASIC_WAREHOUSE_NAME,\r\n"
				+ "J.CHILD_TBL_TYPE AS PRODUCT_SAVE_AREA_NAME\r\n"
				+ "FROM PRODUCT_INFO_TBL A \r\n"
				+ "INNER JOIN USER_INFO_TBL t1 ON A.PRODUCT_MODIFIER = t1.USER_CODE \r\n"
				+ "INNER JOIN DTL_TBL B ON A.PRODUCT_BUSINESS_PLACE = B.CHILD_TBL_NO \r\n"
				+ "INNER JOIN DTL_TBL C ON A.PRODUCT_UNIT = C.CHILD_TBL_NO \r\n"
				+ "INNER JOIN DTL_TBL D ON A.PRODUCT_MATERIAL = D.CHILD_TBL_NO\r\n"
				+ "INNER JOIN DTL_TBL E ON A.PRODUCT_MTRL_CLSFC = E.CHILD_TBL_NO \r\n"
				+ "INNER JOIN DTL_TBL F ON A.PRODUCT_ITEM_CLSFC_1 = F.CHILD_TBL_NO \r\n"
				+ "INNER JOIN DTL_TBL G ON A.PRODUCT_ITEM_CLSFC_2 = G.CHILD_TBL_NO \r\n"
				+ "INNER JOIN DTL_TBL H ON A.PRODUCT_ITEM_STTS = H.CHILD_TBL_NO \r\n"
				+ "INNER JOIN DTL_TBL I ON A.PRODUCT_BASIC_WAREHOUSE = I.CHILD_TBL_NO \r\n"
				+ "left outer JOIN DTL_TBL J ON A.PRODUCT_SAVE_AREA = J.CHILD_TBL_NO \r\n"
				+ "WHERE B.NEW_TBL_CODE=2 and  C.NEW_TBL_CODE=4 and D.NEW_TBL_CODE=8 and E.NEW_TBL_CODE=5 and F.NEW_TBL_CODE=6 and G.NEW_TBL_CODE=7 and H.NEW_TBL_CODE=9 and I.NEW_TBL_CODE=10";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		while (rs.next()) {
			PRODUCT_INFO_TBL data = new PRODUCT_INFO_TBL();
			i++;
			data.setId(i);
			data.setPRODUCT_BUSINESS_PLACE(rs.getString("PRODUCT_BUSINESS_PLACE"));
			data.setPRODUCT_BUSINESS_PLACE_NAME(rs.getString("PRODUCT_BUSINESS_PLACE_NAME"));
			data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
			data.setPRODUCT_OLD_ITEM_CODE(rs.getString("PRODUCT_OLD_ITEM_CODE"));
			data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
			data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			data.setPRODUCT_INFO_STND_2(rs.getString("PRODUCT_INFO_STND_2"));
			data.setPRODUCT_UNIT(rs.getString("PRODUCT_UNIT"));
			data.setPRODUCT_UNIT_NAME(rs.getString("PRODUCT_UNIT_NAME"));
			data.setPRODUCT_MATERIAL(rs.getString("PRODUCT_MATERIAL"));
			data.setPRODUCT_MATERIAL_NAME(rs.getString("PRODUCT_MATERIAL_NAME"));
			data.setPRODUCT_MTRL_CLSFC(rs.getString("PRODUCT_MTRL_CLSFC"));
			data.setPRODUCT_MTRL_CLSFC_NAME(rs.getString("PRODUCT_MTRL_CLSFC_NAME"));
			data.setPRODUCT_ITEM_CLSFC_1(rs.getString("PRODUCT_ITEM_CLSFC_1"));
			data.setPRODUCT_ITEM_CLSFC_1_NAME(rs.getString("PRODUCT_ITEM_CLSFC_1_NAME"));
			data.setPRODUCT_ITEM_CLSFC_2(rs.getString("PRODUCT_ITEM_CLSFC_2"));
			data.setPRODUCT_ITEM_CLSFC_2_NAME(rs.getString("PRODUCT_ITEM_CLSFC_2_NAME"));
			data.setPRODUCT_SUBSID_MATL_MGMT(rs.getString("PRODUCT_SUBSID_MATL_MGMT"));
			data.setPRODUCT_ITEM_STTS(rs.getString("PRODUCT_ITEM_STTS"));
			data.setPRODUCT_ITEM_STTS_NAME(rs.getString("PRODUCT_ITEM_STTS_NAME"));
			data.setPRODUCT_BASIC_WAREHOUSE(rs.getString("PRODUCT_BASIC_WAREHOUSE"));
			data.setPRODUCT_BASIC_WAREHOUSE_NAME(rs.getString("PRODUCT_BASIC_WAREHOUSE_NAME"));
			data.setPRODUCT_SAVE_AREA(rs.getString("PRODUCT_SAVE_AREA"));
			data.setPRODUCT_SAVE_AREA_NAME(rs.getString("PRODUCT_SAVE_AREA_NAME"));
			data.setPRODUCT_SFTY_STOCK(rs.getInt("PRODUCT_SFTY_STOCK"));
			data.setPRODUCT_BUYER(rs.getString("PRODUCT_BUYER"));
			data.setPRODUCT_WRHSN_INSPC(rs.getString("PRODUCT_WRHSN_INSPC"));
			data.setPRODUCT_USE_STATUS(rs.getString("PRODUCT_USE_STATUS"));
			data.setPRODUCT_MODIFY_D(rs.getString("PRODUCT_MODIFY_D"));
			data.setPRODUCT_MODIFIER(rs.getString("PRODUCT_MODIFIER"));
			list.add(data);
			//System.out.println("��ǰ ���� ����Ʈ : " + data);

		}
		rs.close();
		pstmt.close();
		conn.close();
		return list;
	}

	@RequestMapping(value = "/itemManageInsert", method = RequestMethod.POST)
	public String itemManageInsert(HttpServletRequest request, Model model)
			throws SQLException, org.json.simple.parser.ParseException, UnknownHostException, ClassNotFoundException {
		String data = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);
		System.out.println(obj.toJSONString());

		Connection conn = dataSource.getConnection();
		String checkSql = "select PRODUCT_ITEM_CODE from PRODUCT_INFO_TBL where PRODUCT_ITEM_CODE='"
				+ obj.get("PRODUCT_ITEM_CODE") + "'";
		PreparedStatement pstmt = conn.prepareStatement(checkSql);
		ResultSet rs = pstmt.executeQuery();

		// �ߺ�üũ
		while (rs.next()) {
			String check_PRODUCT_ITEM_CODE = rs.getString("PRODUCT_ITEM_CODE");

			if (check_PRODUCT_ITEM_CODE.length() > 0) {
				return "Overlap";
			}
		}
		
		HttpSession httpSession = request.getSession();
		String modifier = (String) httpSession.getAttribute("id");
		// insert가 두번 출력되어 오류나는 상황이 발생해서 insert ignore into 문 사용함
		String sql = "insert ignore into PRODUCT_INFO_TBL values (";
		sql += "'" + obj.get("PRODUCT_BUSINESS_PLACE") + "',";
		sql += "'" + obj.get("PRODUCT_ITEM_CODE") + "',";
		sql += "'" + obj.get("PRODUCT_OLD_ITEM_CODE") + "',";
		sql += "'" + obj.get("PRODUCT_ITEM_NAME") + "',";
		sql += "'" + obj.get("PRODUCT_INFO_STND_1") + "',";
		sql += "'" + obj.get("PRODUCT_INFO_STND_2") + "',";
		sql += "'" + obj.get("PRODUCT_UNIT") + "',";
		sql += "'" + obj.get("PRODUCT_MATERIAL") + "',";
		sql += "'" + obj.get("PRODUCT_MTRL_CLSFC") + "',";
		sql += "'" + obj.get("PRODUCT_ITEM_CLSFC_1") + "',";
		sql += "'" + obj.get("PRODUCT_ITEM_CLSFC_2") + "',";
		sql += "" + obj.get("PRODUCT_UNIT_PRICE") + ",";
		sql += "'" + obj.get("PRODUCT_SUBSID_MATL_MGMT") + "',";
		sql += "'" + obj.get("PRODUCT_ITEM_STTS") + "',";
		sql += "'" + obj.get("PRODUCT_BASIC_WAREHOUSE") + "',";
		sql += "'" + obj.get("PRODUCT_SAVE_AREA") + "',";
		sql += "" + obj.get("PRODUCT_SFTY_STOCK") + ",";
		sql += "'" + obj.get("PRODUCT_BUYER") + "',";
		sql += "'" + obj.get("PRODUCT_WRHSN_INSPC") + "',";
		sql += "'" + obj.get("PRODUCT_USE_STATUS") + "',";
		sql += "now(),";
		sql += "'" + modifier + "')";

		// HomeController.LogInsert("", "1. Insert", sql, request);

		System.out.println(sql);
		// System.out.println(obj.get("PRODUCT_USE_STATUS"));

		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		
		//등록한 기본창고에 따라 맞춰서 StockMat_tbl에 리스트가 추가된다
		//101(자재창고), 102(생산창고), 103(제품창고)
		//생산창고가 현재 없으므로 일단 제품창고에 넣어놓는다.
		if(obj.get("PRODUCT_BASIC_WAREHOUSE").equals("101") && obj.get("PRODUCT_BASIC_WAREHOUSE").equals("102")) {
			sql = "insert into Sales_StockMatLX_tbl values ("; 
			sql += "'" + obj.get("PRODUCT_ITEM_CODE") + "',"; 
			sql += "'0',"; 
			sql += "'0',"; 
			sql += "'0',"; 
			sql += "(select CHILD_TBL_RMARK from DTL_TBL where CHILD_TBL_NO=206)";
			sql += ")";
		}
		if(obj.get("PRODUCT_BASIC_WAREHOUSE").equals("103")) {
			sql = "insert into StockMatLX_tbl values ("; 
			sql += "'" + obj.get("PRODUCT_ITEM_CODE") + "',"; 
			sql += "'0',"; 
			sql += "'0',"; 
			sql += "'0',"; 
			sql += "(select CHILD_TBL_RMARK from DTL_TBL where CHILD_TBL_NO=203)";
			sql += ")";
		}
		
		// 자재분류가 스페어일 경우에는 스페어 파트에 코드, 네임, 규격 데이터를 삽입한다.
		if(obj.get("PRODUCT_MTRL_CLSFC").equals("57")) {
			sql = "insert into Spare_Part_tbl values (";
			sql += "'" + obj.get("PRODUCT_ITEM_CODE")+ "',";
			sql += "'" + "',";
			sql += "'" + "',";
			sql += "'" + "',";
			sql += "'" + 0 + "',";
			sql += null+",";
			sql += "'" + "true" + "',";
			sql += null+",";
			sql += null+",";
			sql += "'" + "',";
			sql += "'" + "')";
		}
				  
		System.out.println(sql);
		pstmt = conn.prepareStatement(sql); 
		pstmt.executeUpdate();
		
		rs.close();
		pstmt.close();
		conn.close();

		return "Success";
	}

	// ����
	@RequestMapping(value = "/itemManageUpdate", method = RequestMethod.POST)
	public String itemManageUpdate(HttpServletRequest request, Model model)
			throws SQLException, org.json.simple.parser.ParseException, UnknownHostException, ClassNotFoundException {
		String data = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);

		// System.out.println(obj.toJSONString());

		HttpSession httpSession = request.getSession();
		String modifier = (String) httpSession.getAttribute("id");

		String sql = "update PRODUCT_INFO_TBL set ";
		sql += "PRODUCT_BUSINESS_PLACE='" + obj.get("PRODUCT_BUSINESS_PLACE") + "'";
		sql += ",PRODUCT_OLD_ITEM_CODE='" + obj.get("PRODUCT_OLD_ITEM_CODE") + "'";
		sql += ",PRODUCT_ITEM_NAME='" + obj.get("PRODUCT_ITEM_NAME") + "'";
		sql += ",PRODUCT_INFO_STND_1='" + obj.get("PRODUCT_INFO_STND_1") + "'";
		sql += ",PRODUCT_INFO_STND_2='" + obj.get("PRODUCT_INFO_STND_2") + "'";
		sql += ",PRODUCT_UNIT='" + obj.get("PRODUCT_UNIT") + "'";
		sql += ",PRODUCT_MATERIAL='" + obj.get("PRODUCT_MATERIAL") + "'";
		sql += ",PRODUCT_MTRL_CLSFC='" + obj.get("PRODUCT_MTRL_CLSFC") + "'";
		sql += ",PRODUCT_ITEM_CLSFC_1='" + obj.get("PRODUCT_ITEM_CLSFC_1") + "'";
		sql += ",PRODUCT_ITEM_CLSFC_2='" + obj.get("PRODUCT_ITEM_CLSFC_2") + "'";
		sql += ",PRODUCT_SUBSID_MATL_MGMT='" + obj.get("PRODUCT_SUBSID_MATL_MGMT") + "'";
		sql += ",PRODUCT_ITEM_STTS='" + obj.get("PRODUCT_ITEM_STTS") + "'";
		sql += ",PRODUCT_BASIC_WAREHOUSE='" + obj.get("PRODUCT_BASIC_WAREHOUSE") + "'";
		sql += ",PRODUCT_SFTY_STOCK=" + obj.get("PRODUCT_SFTY_STOCK") + "";
		sql += ",PRODUCT_BUYER='" + obj.get("PRODUCT_BUYER") + "'";
		sql += ",PRODUCT_WRHSN_INSPC='" + obj.get("PRODUCT_WRHSN_INSPC") + "'";
		sql += ",PRODUCT_USE_STATUS='" + obj.get("PRODUCT_USE_STATUS") + "'";
		sql += ",PRODUCT_MODIFIER='" + modifier + "'";
		sql += ",PRODUCT_MODIFY_D=now()";
		sql += " where PRODUCT_ITEM_CODE='" + obj.get("PRODUCT_ITEM_CODE") + "'";

		//System.out.println(sql);
		// HomeController.LogInsert("", "2. Update", sql, request);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();

		return "Success";
	}

	// ����
	@RequestMapping(value = "/itemManageDelete", method = RequestMethod.POST)
	public String itemManageDelete(HttpServletRequest request, Model model)
			throws SQLException, ParseException, UnknownHostException, ClassNotFoundException {
		String PRODUCT_ITEM_CODE = request.getParameter("PRODUCT_ITEM_CODE");

		// System.out.println("PRODUCT_ITEM_CODE");

		String sql = "delete from PRODUCT_INFO_TBL where PRODUCT_ITEM_CODE='" + PRODUCT_ITEM_CODE + "'";

		// HomeController.LogInsert("", "3. Delete", sql, request);
		//System.out.println(sql);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "error";
		} finally {
			pstmt.close();
			conn.close();
		}

		return PRODUCT_ITEM_CODE;
	}
}
