package com.busience.system.controller;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.dto.CMN_TBL;
import com.busience.standard.dto.DTL_TBL;

@RestController("codeManageRestController")
@RequestMapping("codeManageRest")
public class codeManageRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbctemplate;

	@RequestMapping(value = "/view1", method = RequestMethod.GET)
	public List<CMN_TBL> view(HttpServletRequest request) throws SQLException {
		List<CMN_TBL> list = new ArrayList<CMN_TBL>();

		String sql = "select * from CMN_TBL";

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			CMN_TBL data = new CMN_TBL();
			data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
			data.setNEW_TBL_NAME(rs.getString("NEW_TBL_NAME"));
			if (rs.getString("NEW_TBL_INDEX") == null)
				data.setNEW_TBL_INDEX("");
			else
				data.setNEW_TBL_INDEX(rs.getString("NEW_TBL_INDEX"));

			list.add(data);
		}

		rs.close();

		pstmt.close();
		conn.close();

		return list;
	}

	@RequestMapping(value = "/view2", method = RequestMethod.GET)
	public List<DTL_TBL> view2(HttpServletRequest request) throws SQLException {
		List<DTL_TBL> list = new ArrayList<DTL_TBL>();

		String sql = "select * from DTL_TBL";
		sql += " where NEW_TBL_CODE=" + request.getParameter("NEW_TBL_CODE") + " order by CHILD_TBL_NUM+0";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			DTL_TBL data = new DTL_TBL();
			data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
			data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
			data.setCHILD_TBL_NUM(rs.getString("CHILD_TBL_NUM"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			data.setCHILD_TBL_RMARK(rs.getString("CHILD_TBL_RMARK"));
			data.setCHILD_TBL_USE_STATUS(rs.getString("CHILD_TBL_USE_STATUS"));

			System.out.println(data.toString());

			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	@RequestMapping("/insert")
	public String insert(HttpServletRequest request)
			throws SQLException, UnknownHostException, ClassNotFoundException, ParseException {

		
		String CHILD_TBL_NO = request.getParameter("CHILD_TBL_NO");
		String NEW_TBL_CODE = request.getParameter("NEW_TBL_CODE");
		String CHILD_TBL_TYPE = request.getParameter("CHILD_TBL_TYPE");
		String CHILD_TBL_RMARK = request.getParameter("CHILD_TBL_RMARK");
		String CHILD_TBL_USE_STATUS = request.getParameter("CHILD_TBL_USE_STATUS");

		String CHILD_TBL_NUM = "";
		
		String sql = "select CAST(IFNULL(max(CHILD_TBL_NO+0)+1,1) as char) CHILD_TBL_NO, "
				+ "(select CAST(IFNULL(max(CHILD_TBL_NUM+0)+1,1) as char) FROM DTL_TBL WHERE NEW_TBL_CODE='" + NEW_TBL_CODE + "') CHILD_TBL_NUM from DTL_TBL";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next())
		{
			CHILD_TBL_NUM = rs.getString("CHILD_TBL_NUM");
			CHILD_TBL_NO = rs.getString("CHILD_TBL_NO");
		}

		//System.out.println(CHILD_TBL_NO);

		sql = "INSERT INTO DTL_TBL " + "VALUES (" + "'" + CHILD_TBL_NO + "'," + "'" + NEW_TBL_CODE + "'," + "'"
				+ CHILD_TBL_NUM + "'," + "'" + CHILD_TBL_TYPE + "'," + "'" + CHILD_TBL_RMARK + "'," + "'"
				+ CHILD_TBL_USE_STATUS + "')";

		System.out.println(sql);

		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		
		// 권한관리일 경우에 데이터를 입력하는 코드 권한관리는 1
		/*
		 * if(NEW_TBL_CODE.equals("1")) { List<DTL_TBL> list1 =
		 * jdbctemplate.query("SELECT * FROM DTL_TBL WHERE NEW_TBL_CODE = '13'", new
		 * RowMapper<DTL_TBL>() {
		 * 
		 * @Override public DTL_TBL mapRow(ResultSet rs, int rowNum) throws SQLException
		 * { DTL_TBL data = new DTL_TBL();
		 * data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
		 * data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
		 * data.setCHILD_TBL_NUM(rs.getString("CHILD_TBL_NUM"));
		 * data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
		 * data.setCHILD_TBL_RMARK(rs.getString("CHILD_TBL_RMARK"));
		 * data.setCHILD_TBL_USE_STATUS(rs.getString("CHILD_TBL_USE_STATUS")); return
		 * data; }} );
		 * 
		 * }
		 */
		rs.close();
		pstmt.close();
		conn.close();

		return "Success";
	}

	@RequestMapping("/update")
	public String update(HttpServletRequest request) throws SQLException, UnknownHostException, ClassNotFoundException {
		String NEW_TBL_CODE = request.getParameter("NEW_TBL_CODE");
		String CHILD_TBL_NUM = request.getParameter("CHILD_TBL_NUM");
		String CHILD_TBL_TYPE = request.getParameter("CHILD_TBL_TYPE");
		String CHILD_TBL_RMARK = request.getParameter("CHILD_TBL_RMARK");
		String CHILD_TBL_USE_STATUS = request.getParameter("CHILD_TBL_USE_STATUS");

		String sql = "update DTL_TBL set ";
		sql += "CHILD_TBL_TYPE='" + CHILD_TBL_TYPE + "'";
		sql += ",CHILD_TBL_RMARK='" + CHILD_TBL_RMARK + "'";
		sql += ",CHILD_TBL_USE_STATUS='" + CHILD_TBL_USE_STATUS + "'";
		sql += " where NEW_TBL_CODE=" + NEW_TBL_CODE;
		sql += " and CHILD_TBL_NUM=" + CHILD_TBL_NUM;

		System.out.println(sql);
		// HomeController.LogInsert("", "2. Update", sql, request);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();

		return "Success";
	}

}
