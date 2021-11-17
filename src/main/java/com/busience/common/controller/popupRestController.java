package com.busience.common.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.HometaxApiDto;
import com.busience.common.service.HometaxApiService;
import com.busience.standard.dto.Customer_tbl;
import com.busience.standard.dto.DEFECT_INFO_TBL;
import com.busience.standard.dto.EQUIPMENT_INFO_TBL;
import com.busience.standard.dto.PRODUCT_INFO_TBL;

@RestController
public class popupRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	HometaxApiService hometaxApiService;
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	//itemPopup
	@GetMapping("/itemPopupSelect")
	public List<PRODUCT_INFO_TBL> itemPopupSelect(
			@RequestParam(value = "item_Word", required = false) String item_Word,
			@RequestParam(value = "search_value", required = false) String search_value) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		conn = dataSource.getConnection();
		
		String terms = null;
		String terms_sql = "select CHILD_TBL_RMARK from DTL_TBL\r\n"
				+ "where NEW_TBL_CODE = 37 and CHILD_TBL_TYPE = '"+search_value+"'";
		
		pstmt = conn.prepareStatement(terms_sql);
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			terms = rs.getString("CHILD_TBL_RMARK");
		}
		System.out.println("============");
		System.out.println(terms);
		
		String sql = "";
		
		sql = " select PRODUCT_ITEM_CODE,\r\n"
				+ " PRODUCT_ITEM_NAME,\r\n"
				+ " PRODUCT_INFO_STND_1,\r\n"
				+ " PRODUCT_UNIT_PRICE\r\n"
				+ " from PRODUCT_INFO_TBL\r\n"
				+ " where (PRODUCT_ITEM_CODE like '%" + item_Word + "%' or PRODUCT_ITEM_NAME like '%" + item_Word + "%')\r\n"
				+ " and PRODUCT_USE_STATUS='true'"
				+ " and PRODUCT_MTRL_CLSFC in ("+terms+")";
		
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		System.out.println("itemPopupSelect =" + sql);
		
		List<PRODUCT_INFO_TBL> list = new ArrayList<PRODUCT_INFO_TBL>();

		while (rs.next()) {
			PRODUCT_INFO_TBL data = new PRODUCT_INFO_TBL();

			data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
			data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
			data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			data.setPRODUCT_UNIT_PRICE(rs.getInt("PRODUCT_UNIT_PRICE"));
			list.add(data);
		}	
	
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
	
	//itemPopup - 규격으로 검사함
	@GetMapping("/itemPopupSelect2")
	public List<PRODUCT_INFO_TBL> itemPopupSelect2(
				@RequestParam(value = "item_Word", required = false) String item_Word,
				@RequestParam(value = "search_value", required = false) String search_value) throws SQLException {
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			conn = dataSource.getConnection();
			
			String terms = null;
			String terms_sql = "select CHILD_TBL_RMARK from DTL_TBL\r\n"
					+ "where NEW_TBL_CODE = 37 and CHILD_TBL_TYPE = '"+search_value+"'";
			
			pstmt = conn.prepareStatement(terms_sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				terms = rs.getString("CHILD_TBL_RMARK");
			}
			System.out.println("============");
			System.out.println(terms);
			
			String sql = "";
			
			sql = " select PRODUCT_ITEM_CODE,\r\n"
					+ " PRODUCT_ITEM_NAME,\r\n"
					+ " PRODUCT_INFO_STND_1,\r\n"
					+ " PRODUCT_UNIT_PRICE\r\n"
					+ " from PRODUCT_INFO_TBL\r\n"
					+ " where PRODUCT_INFO_STND_1 like '%" + item_Word + "%'\r\n"
					+ " and PRODUCT_USE_STATUS='true'"
					+ " and PRODUCT_MTRL_CLSFC in ("+terms+")";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			System.out.println("itemPopupSelect =" + sql);
			
			List<PRODUCT_INFO_TBL> list = new ArrayList<PRODUCT_INFO_TBL>();

			while (rs.next()) {
				PRODUCT_INFO_TBL data = new PRODUCT_INFO_TBL();

				data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
				data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				data.setPRODUCT_UNIT_PRICE(rs.getInt("PRODUCT_UNIT_PRICE"));
				list.add(data);
			}	
		
			rs.close();
			pstmt.close();
			conn.close();

			return list;
	}

	// machinePopup
	@GetMapping("/machinePopupSelect")
	public List<EQUIPMENT_INFO_TBL> machinePopupSelect(
			@RequestParam(value = "machine_Word", required = false) String machine_Word) throws SQLException {
		
		String sql = "";
		
		sql = " select EQUIPMENT_INFO_CODE, EQUIPMENT_INFO_NAME from EQUIPMENT_INFO_TBL\r\n"
			+ " where (EQUIPMENT_INFO_CODE like '%" + machine_Word + "%' or EQUIPMENT_INFO_NAME like '%" + machine_Word + "%')";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<EQUIPMENT_INFO_TBL> list = new ArrayList<EQUIPMENT_INFO_TBL>();
		
		while (rs.next()) {
			EQUIPMENT_INFO_TBL data = new EQUIPMENT_INFO_TBL();

			data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
			data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
	
	//defectPopup
	@RequestMapping(value = "/defectPopupSelect", method = { RequestMethod.GET })
	public List<DEFECT_INFO_TBL> defectPopupSelect(
			@RequestParam(value = "defect_Word", required = false) String defect_Word) throws SQLException {

		String sql = "";
		
		sql = " select DEFECT_CODE, DEFECT_NAME from DEFECT_INFO_TBL\r\n"
			+ " where (DEFECT_CODE like '%" + defect_Word + "%' or DEFECT_NAME like '%" + defect_Word + "%')\r\n"
			+ " and DEFECT_USE_STATUS='true'";
				
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<DEFECT_INFO_TBL> list = new ArrayList<DEFECT_INFO_TBL>();

		while (rs.next()) {
			DEFECT_INFO_TBL data = new DEFECT_INFO_TBL();
			data.setDEFECT_CODE(rs.getString("DEFECT_CODE"));
			data.setDEFECT_NAME(rs.getString("DEFECT_NAME"));
			list.add(data);
		}

		return list;
	}
	
	// customerPopup
    @RequestMapping(value = "/customerPopupSelect", method = RequestMethod.GET)
    public List<Customer_tbl> customerPopupSelect(
          @RequestParam(value = "cus_Word", required = false) String cus_Word,
          @RequestParam(value = "search_value", required = false) String search_value) throws SQLException {
       
       String sql = "";

       sql = "select A.Cus_Code, A.Cus_Name\r\n"
       		+ "from Customer_tbl A\r\n"
       		+ "inner join ("
       			+ "select * from DTL_TBL where NEW_TBL_CODE = '28'"
       		+ ") B on A.Cus_Clsfc = B.CHILD_TBL_NO\r\n"
       		+ "where (A.Cus_Code like '%"+cus_Word+"%' or A.Cus_Name like '%"+cus_Word+"%') and B.CHILD_TBL_RMARK='"+search_value+"'";

       System.out.println(sql);

       Connection conn = dataSource.getConnection();
       PreparedStatement pstmt = conn.prepareStatement(sql);
       ResultSet rs = pstmt.executeQuery();

       List<Customer_tbl> list = new ArrayList<Customer_tbl>();

       while (rs.next()) {
          Customer_tbl data = new Customer_tbl();

          data.setCus_Code(rs.getString("cus_Code"));
          data.setCus_Name(rs.getString("cus_Name"));
          list.add(data);
       }

       rs.close();
       pstmt.close();
       conn.close();

       return list;
    }
    
	//product_check
	@RequestMapping(value = "/product_check", method = RequestMethod.GET)
	public List<PRODUCT_INFO_TBL> product_check(
			@RequestParam(value = "PRODUCT_ITEM_CODE", required = false) String PRODUCT_ITEM_CODE) throws SQLException {
		
		String sql = "";
		
		//첫 select 결과가 없으면 두번쨰 select로 대체함
		sql = "select PRODUCT_ITEM_CODE, PRODUCT_ITEM_NAME, PRODUCT_INFO_STND_1, PRODUCT_UNIT_PRICE \r\n"
				+ "from PRODUCT_INFO_TBL \r\n"
				+ " where (PRODUCT_ITEM_NAME = '" + PRODUCT_ITEM_CODE + "' or PRODUCT_ITEM_CODE = '" + PRODUCT_ITEM_CODE + "')\r\n"
				+ "union\r\n"
				+ "select PRODUCT_ITEM_CODE, PRODUCT_ITEM_NAME, PRODUCT_INFO_STND_1, PRODUCT_UNIT_PRICE \r\n"
				+ "from PRODUCT_INFO_TBL \r\n"
				+ " where (PRODUCT_ITEM_NAME like '%" + PRODUCT_ITEM_CODE + "%' or PRODUCT_ITEM_CODE like '%" + PRODUCT_ITEM_CODE + "%')\r\n"
				+ "and not exists (\r\n"
				+ "	select PRODUCT_ITEM_CODE, PRODUCT_ITEM_NAME, PRODUCT_INFO_STND_1, PRODUCT_UNIT_PRICE \r\n"
					+ " from PRODUCT_INFO_TBL \r\n"
					+ " where (PRODUCT_ITEM_NAME = '" + PRODUCT_ITEM_CODE + "' or PRODUCT_ITEM_CODE = '" + PRODUCT_ITEM_CODE + "'));";		

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<PRODUCT_INFO_TBL> list = new ArrayList<PRODUCT_INFO_TBL>();
		
		int i = 0;
		while (rs.next()) {
			PRODUCT_INFO_TBL data = new PRODUCT_INFO_TBL();
			i++;
			data.setId(i);
			data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
			data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
			data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			data.setPRODUCT_UNIT_PRICE(rs.getInt("PRODUCT_UNIT_PRICE"));
			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
	
	//customer_check
	@RequestMapping(value = "/customer_check", method = RequestMethod.GET)
	public List<Customer_tbl> customer_check(
			@RequestParam(value = "Cus_Code", required = false) String cus_Code) throws SQLException {
		
		String sql = "";

		//첫 select 결과가 없으면 두번쨰 select로 대체함
		sql = "select Cus_Code, Cus_Name \r\n"
				+ "from Customer_tbl\r\n"
				+ " where (Cus_Name = '" + cus_Code + "' or Cus_Code = '" + cus_Code + "')\r\n"
				+ "union\r\n"
				+ "select Cus_Code, Cus_Name \r\n"
				+ "from Customer_tbl\r\n"
				+ " where (Cus_Name like '%" + cus_Code + "%' or Cus_Code like '%" + cus_Code + "%')\r\n"
				+ "and not exists (\r\n"
				+ "	select Cus_Code, Cus_Name \r\n"
					+ "from Customer_tbl\r\n"
					+ " where (Cus_Name = '" + cus_Code + "' or Cus_Code = '" + cus_Code + "'))";		

		
		System.out.println("customer_check ="+sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<Customer_tbl> list = new ArrayList<Customer_tbl>();
		
		int i = 0;
		while (rs.next()) {
			Customer_tbl data = new Customer_tbl();
			i++;
			data.setId(i);
			data.setCus_Code(rs.getString("cus_Code"));
			data.setCus_Name(rs.getString("cus_Name"));
			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
	
	@RequestMapping(value = "/equipment_check", method = RequestMethod.GET)
	public List<EQUIPMENT_INFO_TBL> equipment_check(
			@RequestParam(value = "EQUIPMENT_INFO_CODE", required = false) String EQUIPMENT_INFO_CODE){
		
		//첫 select 결과가 없으면 두번쨰 select로 대체함
		String sql = "SELECT EQUIPMENT_INFO_CODE, EQUIPMENT_INFO_NAME \r\n"
				+ "FROM EQUIPMENT_INFO_TBL \r\n"
				+ "where (EQUIPMENT_INFO_NAME = ? or EQUIPMENT_INFO_CODE = ?)\r\n"
				+ "union\r\n"
				+ "SELECT EQUIPMENT_INFO_CODE, EQUIPMENT_INFO_NAME \r\n"
				+ "FROM EQUIPMENT_INFO_TBL \r\n"
				+ "where (EQUIPMENT_INFO_NAME like ? or EQUIPMENT_INFO_CODE like ?)\r\n"
				+ "and not exists(\r\n"
				+ "SELECT EQUIPMENT_INFO_CODE, EQUIPMENT_INFO_NAME \r\n"
				+ "FROM EQUIPMENT_INFO_TBL \r\n"
				+ "where (EQUIPMENT_INFO_NAME = ? or EQUIPMENT_INFO_CODE = ?));";
		
		System.out.println(sql);
		return jdbctemplate.query(sql, new RowMapper<EQUIPMENT_INFO_TBL>(){
			@Override
			public EQUIPMENT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				EQUIPMENT_INFO_TBL data = new EQUIPMENT_INFO_TBL();
				data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
				data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
				return data;
			}
		}, EQUIPMENT_INFO_CODE, EQUIPMENT_INFO_CODE, "%"+EQUIPMENT_INFO_CODE+"%", 
				"%"+EQUIPMENT_INFO_CODE+"%", EQUIPMENT_INFO_CODE, EQUIPMENT_INFO_CODE);
	}
	
	@GetMapping("/hometaxApiPopupSelect")
	public List<HometaxApiDto> hometaxApiPopupSelect(){
		return hometaxApiService.hometaxApiDataSearch();
	}
}
