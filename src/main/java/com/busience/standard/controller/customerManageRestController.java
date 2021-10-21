package com.busience.standard.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.Dto.Customer_tbl;

@RestController("customerManageRestController")
@RequestMapping("customerManageRest")
public class customerManageRestController {

	@Autowired
	DataSource dataSource;
	
	@GetMapping("/view")
	public List<Customer_tbl> view() throws SQLException
	{
		List<Customer_tbl> list = new ArrayList<Customer_tbl>();
		
		String sql = "SELECT Cus_Code, Cus_Name, Cus_Status, dt2.CHILD_TBL_TYPE Cus_Clsfc, Cus_Rprsn, Cus_Mng, Cus_Co, Cus_Co_EstYr, Cus_Rprsn_PhNr, Cus_Mng_PhNr, Cus_Mng_Email, \r\n"
				+ "Cus_Adr, Cus_Pymn_Date, Cus_Rgstr_Nr, dt.CHILD_TBL_NO, dt.NEW_TBL_CODE,\r\n"
				+ "dt.CHILD_TBL_NUM, dt.CHILD_TBL_TYPE, dt.CHILD_TBL_RMARK, dt.CHILD_TBL_USE_STATUS, dt.CHILD_TBL_TYPE Cus_Status_Name\r\n"
				+ "FROM Customer_tbl cst\r\n"
				+ "INNER JOIN (SELECT * FROM DTL_TBL WHERE NEW_TBL_CODE='15') dt ON cst.Cus_Status = dt.CHILD_TBL_NO\r\n"
				+ "INNER JOIN (SELECT * FROM DTL_TBL WHERE NEW_TBL_CODE='28') dt2 ON cst.Cus_Clsfc = dt2.CHILD_TBL_NO";
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		System.out.println(sql);
		
		while (rs.next()) {
			Customer_tbl data = new Customer_tbl();
			
			data.setCus_Code(rs.getString("cus_Code"));
			data.setCus_Name(rs.getString("cus_Name"));
			data.setCus_Status(rs.getString("cus_Status"));
			data.setCus_Clsfc(rs.getString("cus_Clsfc"));
			data.setCus_Rprsn(rs.getString("cus_Rprsn"));
			data.setCus_Mng(rs.getString("cus_Mng"));
			data.setCus_Co(rs.getString("cus_Co"));
			data.setCus_Co_EstYr(rs.getString("cus_Co_EstYr"));
			data.setCus_Rprsn_PhNr(rs.getString("cus_Rprsn_PhNr"));
			data.setCus_Mng_PhNr(rs.getString("cus_Mng_PhNr"));
			data.setCus_Mng_Email(rs.getString("cus_Mng_Email"));
			data.setCus_Adr(rs.getString("cus_Adr"));
			data.setCus_Pymn_Date(rs.getString("cus_Pymn_Date"));
			data.setCus_Rgstr_Nr(rs.getString("cus_Rgstr_Nr"));
			data.setCus_Status_Name(rs.getString("cus_Status_Name"));
			
			list.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	@GetMapping("/insert")
	public String insert(HttpServletRequest request) throws SQLException
	{
		Customer_tbl insert_Data = new Customer_tbl();
		
		insert_Data.setCus_Code(request.getParameter("cus_Code"));
		insert_Data.setCus_Name(request.getParameter("cus_Name"));
		insert_Data.setCus_Status(request.getParameter("cus_Status"));
		insert_Data.setCus_Clsfc(request.getParameter("cus_Clsfc"));
		insert_Data.setCus_Rprsn(request.getParameter("cus_Rprsn"));
		insert_Data.setCus_Mng(request.getParameter("cus_Mng"));
		insert_Data.setCus_Co(request.getParameter("cus_Co"));
		insert_Data.setCus_Co_EstYr(request.getParameter("cus_Co_EstYr"));
		insert_Data.setCus_Rprsn_PhNr(request.getParameter("cus_Rprsn_PhNr"));
		insert_Data.setCus_Mng_PhNr(request.getParameter("cus_Mng_PhNr"));
		insert_Data.setCus_Mng_Email(request.getParameter("cus_Mng_Email"));
		insert_Data.setCus_Adr(request.getParameter("cus_Adr"));
		insert_Data.setCus_Pymn_Date(request.getParameter("cus_Pymn_Date"));
		insert_Data.setCus_Rgstr_Nr(request.getParameter("cus_Rgstr_Nr"));
		
		System.out.println(insert_Data.toString());
		Connection conn = dataSource.getConnection();
		String checkSql = "SELECT Cus_Code FROM Customer_tbl where Cus_Code=?";
		PreparedStatement pstmt = conn.prepareStatement(checkSql);
		pstmt.setString(1, insert_Data.getCus_Code());
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			String check_Cus_Code = rs.getString("cus_Code");
			
			if(check_Cus_Code.length() > 0)
				return "Overlap";
		}
		
		String sql = "INSERT INTO `Customer_tbl`\r\n"
				+ "(`Cus_Code`,\r\n"
				+ "`Cus_Name`,\r\n"
				+ "`Cus_Status`,\r\n"
				+ "`Cus_Clsfc`,\r\n"
				+ "`Cus_Rprsn`,\r\n"
				+ "`Cus_Mng`,\r\n"
				+ "`Cus_Co`,\r\n"
				+ "`Cus_Co_EstYr`,\r\n"
				+ "`Cus_Rprsn_PhNr`,\r\n"
				+ "`Cus_Mng_PhNr`,\r\n"
				+ "`Cus_Mng_Email`,\r\n"
				+ "`Cus_Adr`,\r\n"
				+ "`Cus_Pymn_Date`,\r\n"
				+ "`Cus_Rgstr_Nr`)\r\n"
				+ "VALUES\r\n"
				+ "(?,\r\n"
				+ "?,\r\n"
				+ "?,\r\n"
				+ "?,\r\n"
				+ "?,\r\n"
				+ "?,\r\n"
				+ "?,\r\n"
				+ "?,\r\n"
				+ "?,\r\n"
				+ "?,\r\n"
				+ "?,\r\n"
				+ "?,\r\n"
				+ "?,\r\n"
				+ "?)";
		
		pstmt = conn.prepareStatement(sql);
		System.out.println(sql);
		pstmt.setString(1, insert_Data.getCus_Code());
		pstmt.setString(2, insert_Data.getCus_Name());
		pstmt.setString(3, insert_Data.getCus_Status());
		pstmt.setString(4, insert_Data.getCus_Clsfc());
		pstmt.setString(5, insert_Data.getCus_Rprsn());
		pstmt.setString(6, insert_Data.getCus_Mng());
		pstmt.setString(7, insert_Data.getCus_Co());
		pstmt.setString(8, insert_Data.getCus_Co_EstYr());
		pstmt.setString(9, insert_Data.getCus_Rprsn_PhNr());
		pstmt.setString(10, insert_Data.getCus_Mng_PhNr());
		pstmt.setString(11, insert_Data.getCus_Mng_Email());
		pstmt.setString(12, insert_Data.getCus_Adr());
		pstmt.setString(13, insert_Data.getCus_Pymn_Date());
		pstmt.setString(14, insert_Data.getCus_Rgstr_Nr());
		pstmt.executeUpdate();
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return "Success";
	}
	
	@GetMapping("/update")
	public String update(HttpServletRequest request) throws SQLException
	{
		Customer_tbl update_Data = new Customer_tbl();
		
		update_Data.setCus_Code(request.getParameter("cus_Code"));
		update_Data.setCus_Name(request.getParameter("cus_Name"));
		update_Data.setCus_Status(request.getParameter("cus_Status"));
		update_Data.setCus_Clsfc(request.getParameter("cus_Clsfc"));
		update_Data.setCus_Rprsn(request.getParameter("cus_Rprsn"));
		update_Data.setCus_Mng(request.getParameter("cus_Mng"));
		update_Data.setCus_Co(request.getParameter("cus_Co"));
		update_Data.setCus_Co_EstYr(request.getParameter("cus_Co_EstYr"));
		update_Data.setCus_Rprsn_PhNr(request.getParameter("cus_Rprsn_PhNr"));
		update_Data.setCus_Mng_PhNr(request.getParameter("cus_Mng_PhNr"));
		update_Data.setCus_Mng_Email(request.getParameter("cus_Mng_Email"));
		update_Data.setCus_Adr(request.getParameter("cus_Adr"));
		update_Data.setCus_Pymn_Date(request.getParameter("cus_Pymn_Date"));
		update_Data.setCus_Rgstr_Nr(request.getParameter("cus_Rgstr_Nr"));
		
		String sql = "UPDATE `Customer_tbl`\r\n"
				+ "SET\r\n"
				+ "`Cus_Name` = ?,\r\n"
				+ "`Cus_Status` = ?,\r\n"
				+ "`Cus_Clsfc` = ?,\r\n"
				+ "`Cus_Rprsn` = ?,\r\n"
				+ "`Cus_Mng` = ?,\r\n"
				+ "`Cus_Co` = ?,\r\n"
				+ "`Cus_Co_EstYr` = ?,\r\n"
				+ "`Cus_Rprsn_PhNr` = ?,\r\n"
				+ "`Cus_Mng_PhNr` = ?,\r\n"
				+ "`Cus_Mng_Email` = ?,\r\n"
				+ "`Cus_Adr` = ?,\r\n"
				+ "`Cus_Pymn_Date` = ?,\r\n"
				+ "`Cus_Rgstr_Nr` = ?\r\n"
				+ "WHERE `Cus_Code` = ?;";
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		System.out.println(sql);
		pstmt.setString(1, update_Data.getCus_Name());
		pstmt.setString(2, update_Data.getCus_Status());
		pstmt.setString(3, update_Data.getCus_Clsfc());
		pstmt.setString(4, update_Data.getCus_Rprsn());
		pstmt.setString(5, update_Data.getCus_Mng());
		pstmt.setString(6, update_Data.getCus_Co());
		pstmt.setString(7, update_Data.getCus_Co_EstYr());
		pstmt.setString(8, update_Data.getCus_Rprsn_PhNr());
		pstmt.setString(9, update_Data.getCus_Mng_PhNr());
		pstmt.setString(10, update_Data.getCus_Mng_Email());
		pstmt.setString(11, update_Data.getCus_Adr());
		pstmt.setString(12, update_Data.getCus_Pymn_Date());
		pstmt.setString(13, update_Data.getCus_Rgstr_Nr());
		pstmt.setString(14, update_Data.getCus_Code());
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return "Success";
	}
	
	@GetMapping("delete")
	public void delete(HttpServletRequest request) throws ParseException, SQLException, UnknownHostException, ClassNotFoundException
	{
		String no = request.getParameter("cus_Code");
		
		String sql = "delete from Customer_tbl where Cus_Code = '"+no+"'";
		
		//HomeController.LogInsert("", "3. Delete", sql, request);
		//System.out.println(sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
	}
	
}
