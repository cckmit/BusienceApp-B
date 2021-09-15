package com.busience.standard.Controller;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.Dto.USER_INFO_TBL;

@RestController("userManageRestController")
@RequestMapping("userManageRest")
public class userManageRestController {

	@Autowired
	DataSource dataSource;

	@RequestMapping(value = "/userManageRestSelect", method = RequestMethod.GET)
	public List<USER_INFO_TBL> view(HttpServletRequest request) throws SQLException {
		List<USER_INFO_TBL> list = new ArrayList<USER_INFO_TBL>();

		String sql = "select \r\n" + "			t1.USER_CODE\r\n" + "		,	t1.USER_PASSWORD\r\n"
				+ "        ,	t1.USER_NAME\r\n" + "        ,	t1.USER_TYPE\r\n"
				+ "		,	t2.CHILD_TBL_TYPE 'user_TYPE_NAME'\r\n" + "        ,	t1.COMPANY\r\n"
				+ "        ,	t3.CHILD_TBL_TYPE 'company_NAME'\r\n" + "        ,	t1.DEPT_CODE\r\n"
				+ "        ,	t4.CHILD_TBL_TYPE 'dept_NAME'\r\n" + "        ,	t1.USER_MODIFIER\r\n"
				+ "        ,	t1.USER_MODIFY_D\r\n" + "         ,	t1.USER_USE_STATUS\r\n"
				+ "from USER_INFO_TBL t1 \r\n" + "left outer join \r\n" + "(\r\n" + "	select\r\n" + "			*\r\n"
				+ "	from\r\n" + "			DTL_TBL\r\n" + "	where 	NEW_TBL_CODE = '1'\r\n" + ") t2\r\n"
				+ "on t1.USER_TYPE = t2.CHILD_TBL_NO\r\n" + "left outer join \r\n" + "(\r\n" + "	select\r\n"
				+ "			*\r\n" + "	from\r\n" + "			DTL_TBL\r\n" + "	where 	NEW_TBL_CODE = '2'\r\n"
				+ ") t3\r\n" + "on t1.COMPANY = t3.CHILD_TBL_NO\r\n" + "left outer join \r\n" + "(\r\n"
				+ "	select\r\n" + "			*\r\n" + "	from\r\n" + "			DTL_TBL\r\n"
				+ "	where 	NEW_TBL_CODE = '3'\r\n" + ") t4\r\n" + "on t1.DEPT_CODE = t4.CHILD_TBL_NO";


		String where = "";

		if (request.getParameter("USER_CODE") != "" && request.getParameter("USER_NAME") == "")
			where = " where t1.USER_CODE like '%" + request.getParameter("USER_CODE") + "%'";

		if (request.getParameter("USER_CODE") == "" && request.getParameter("USER_NAME") != "")
			where = " where t1.USER_NAME like '%" + request.getParameter("USER_NAME") + "%'";

		if (request.getParameter("USER_CODE") == "" && request.getParameter("USER_NAME") == "")
			where = " where t1.USER_CODE like '%" + request.getParameter("USER_CODE") + "%'"
					+ " AND t1.USER_NAME like '%" + request.getParameter("USER_NAME") + "%'";

		// if (request.getParameter("USER_NAME") != "")
		// sql += " where t1.USER_NAME like '%" + request.getParameter("USER_NAME") +
		// "%'";
		if (request.getParameter("USER_NAME") != null) {
			if (request.getParameter("USER_NAME") != "")
				sql += " where t1.USER_NAME like '%" + request.getParameter("USER_NAME") + "%'";
		}
		if (request.getParameter("USER_CODE") == null)
			where = "";

		sql += where;

		//System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		while (rs.next()) {
			USER_INFO_TBL data = new USER_INFO_TBL();
			i++;
			data.setId(i);
			data.setUSER_CODE(rs.getString("user_CODE"));
			data.setDEPT_CODE(rs.getString("dept_CODE"));
			data.setUSER_USE_STATUS(rs.getString("user_USE_STATUS"));
			data.setCOMPANY(rs.getString("company"));
			data.setUSER_TYPE(rs.getString("user_TYPE"));
			data.setUSER_MODIFY_D(rs.getString("user_MODIFY_D"));
			data.setUSER_NAME(rs.getString("user_NAME"));
			data.setUSER_PASSWORD(rs.getString("user_PASSWORD"));
			data.setUSER_TYPE_NAME(rs.getString("user_TYPE_NAME"));
			data.setUSER_MODIFIER(rs.getString("user_MODIFIER"));
			data.setCOMPANY_NAME(rs.getString("company_NAME"));
			data.setDEPT_NAME(rs.getString("dept_NAME"));
			list.add(data);
			//System.out.println("�ߵ�����? : " + data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// ����
	@RequestMapping(value = "/userManageInsert", method = RequestMethod.POST)
	public String userManageInsert(HttpServletRequest request, Model model)
			throws SQLException, ParseException, UnknownHostException, ClassNotFoundException {
		String data = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);

		//System.out.println(obj.toJSONString());
		HttpSession httpSession = request.getSession();
		String modifier = (String) httpSession.getAttribute("id");

		Connection conn = dataSource.getConnection();
		String checkSql = "select USER_CODE from USER_INFO_TBL where USER_CODE='" + obj.get("USER_CODE") + "'";
		PreparedStatement pstmt = conn.prepareStatement(checkSql);
		ResultSet rs = pstmt.executeQuery();

		// �ߺ�üũ
		while (rs.next()) {
			String check_DEFECT_CODE = rs.getString("USER_CODE");

			if (check_DEFECT_CODE.length() > 0) {
				return "Overlap";
			}
		}
		// ��¥ ����
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = sdf.format(date.getTime());
		//System.out.println(datestr);

		String sql = "";
		sql = "insert into USER_INFO_TBL values (";
		sql += "'" + obj.get("USER_CODE") + "',";
		sql += "hex(aes_encrypt('1234','a')),";
		sql += "'" + obj.get("USER_NAME") + "',";
		sql += "'" + obj.get("USER_TYPE") + "',";
		sql += "'" + obj.get("COMPANY") + "',";
		sql += "'" + obj.get("DEPT_CODE") + "',";
		sql += "'" + modifier + "',";
		sql += "now(),";
		sql += "'" + obj.get("USER_USE_STATUS") + "')";

		//System.out.println(sql);

		// HomeController.LogInsert("", "1. Insert", sql, request);

		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();

		sql = "";
		sql += "INSERT INTO MENU_MGMT_TBL";
		sql += " SELECT T1.USER_CODE, T3.RIGHTS_PROGRAM_CODE, 'true', 'true', 'true', T3.RIGHTS_MGMT_USE_STATUS   FROM USER_INFO_TBL T1";
		sql += " INNER JOIN RIGHTS_MGMT_TBL T3 ON T1.USER_TYPE = T3.RIGHTS_USER_TYPE";
		sql += " WHERE USER_CODE = '" + obj.get("USER_CODE") + "'";

		// System.out.println("����� �޴� ���� : " + sql);

		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		// ����� ���� ������ �ִ� ���� ��ȸ�ؼ� �޴� ������ ���� INSERT �� �ش�.
		rs.close();
		pstmt.close();
		conn.close();

		return "Success";
	}

	// ����
	@RequestMapping(value = "/userManageUpdate", method = RequestMethod.POST)
	public String userManageUpdate(HttpServletRequest request, Model model)
			throws SQLException, ParseException, UnknownHostException, ClassNotFoundException {
		String data = request.getParameter("data");
		//System.out.println("update data : " + data);
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);

		//System.out.println(obj.toJSONString());
		// Ư�� USER_CODE�� ���� ���� ��ȸ
		String sql = "SELECT USER_TYPE FROM USER_INFO_TBL where USER_CODE='" + obj.get("USER_CODE") + "'";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			// System.out.println(obj.get("USER_TYPE"));
			// System.out.println(rs.getString("USER_TYPE"));
			// �޴� ���� USER_TYPE�� ����� ���� ������ USER_TYPE�� �������� �ʴٸ�, �޴� ������ USER ���� ����
			// ���� �̸��� ���� ���� ����
			if (!obj.get("USER_TYPE").equals(rs.getString("USER_TYPE"))) {
				sql = "delete from MENU_MGMT_TBL where MENU_USER_CODE='" + obj.get("USER_CODE") + "'";
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();

				sql = "INSERT INTO MENU_MGMT_TBL";
				sql += " SELECT T1.USER_CODE, T3.RIGHTS_PROGRAM_CODE, 'true', 'true', 'true', T3.RIGHTS_MGMT_USE_STATUS   FROM USER_INFO_TBL T1";
				sql += " INNER JOIN RIGHTS_MGMT_TBL T3 ON T1.USER_TYPE = T3.RIGHTS_USER_TYPE";
				sql += " WHERE USER_CODE = '" + obj.get("USER_CODE") + "'";

				// System.out.println("����� �޴� ���� : " + sql);

				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
			}
		}
		
		HttpSession httpSession = request.getSession();
		String modifier = (String) httpSession.getAttribute("id");

		sql = "update USER_INFO_TBL set ";
		sql += "USER_NAME='" + obj.get("USER_NAME") + "'";
		sql += ",COMPANY='" + obj.get("COMPANY") + "'";
		sql += ",USER_TYPE='" + obj.get("USER_TYPE") + "'";
		sql += ",USER_MODIFIER='" + modifier + "'";
		sql += ",USER_USE_STATUS='" + obj.get("USER_USE_STATUS") + "'";
		sql += ",DEPT_CODE='" + obj.get("DEPT_CODE") + "'";
		sql += ",USER_MODIFY_D=now()";
		sql += " where USER_CODE='" + obj.get("USER_CODE") + "'";

		//System.out.println(sql);

		// HomeController.LogInsert("", "2. Update", sql, request);

		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		//pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		rs.close();
		pstmt.close();
		conn.close();

		return "Success";
	}

	@RequestMapping(value = "/userManagePW", method = RequestMethod.POST)
	public String userManagePW(HttpServletRequest request, Model model)
			throws SQLException, UnknownHostException, ClassNotFoundException {
		String USER_CODE = request.getParameter("update_user_CODE");

		// System.out.println(USER_CODE);

		String sql = "update USER_INFO_TBL set ";
		sql += "USER_PASSWORD=hex(aes_encrypt('1234','a'))";
		sql += " where USER_CODE='" + USER_CODE + "'";

		//HomeController.LogInsert("", "7. Pw Reset", sql, request);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();

		return USER_CODE;
	}

}
