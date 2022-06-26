package com.busience.production.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.production.dto.PRODUCTION_MGMT_TBL2;
import com.busience.standard.dto.EQUIPMENT_INFO_TBL;

@RestController("worktdListRestController")
@RequestMapping("worktdListRest")
public class worktdListRestController {

	@Autowired
	DataSource dataSource;

	@Autowired
	JdbcTemplate jdbctemplate;

	@RequestMapping(value = "/One_Grid_init", method = RequestMethod.GET)
	public List<EQUIPMENT_INFO_TBL> One_Grid_init(HttpServletRequest request)
			throws org.json.simple.parser.ParseException, SQLException {
		/*
		 * List<EQUIPMENT_INFO_TBL> list = new ArrayList<EQUIPMENT_INFO_TBL>();
		 * 
		 * String sql = "select * from EQUIPMENT_INFO_TBL";
		 * 
		 * Connection conn = dataSource.getConnection(); PreparedStatement pstmt = null;
		 * ResultSet rs = null;
		 * 
		 * pstmt = conn.prepareStatement(sql); rs = pstmt.executeQuery();
		 * 
		 * while (rs.next()) { EQUIPMENT_INFO_TBL data = new EQUIPMENT_INFO_TBL();
		 * data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
		 * data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
		 * list.add(data); }
		 * 
		 * return list;
		 */

		String sql = "select * from Equipment_Info_tbl";
		return jdbctemplate.query(sql, new RowMapper<EQUIPMENT_INFO_TBL>() {
			@Override
			public EQUIPMENT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				EQUIPMENT_INFO_TBL data = new EQUIPMENT_INFO_TBL();
				data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
				data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
				data.setEQUIPMENT_TYPE(rs.getString("EQUIPMENT_TYPE"));
				return data;
			}
		});
	}

	@RequestMapping(value = "/Month_Select", method = RequestMethod.GET)
	public List<PRODUCTION_MGMT_TBL2> Month_Select(HttpServletRequest request) throws SQLException {
		List<PRODUCTION_MGMT_TBL2> list = new ArrayList<PRODUCTION_MGMT_TBL2>();
		String year = request.getParameter("year");
		String equipment_INFO_CODE = request.getParameter("equipment_INFO_CODE");
		String equipment_TYPE = request.getParameter("equipment_TYPE");
		String sql = null;
		
		for (int i = 0; i < 12; i++)
			list.add(new PRODUCTION_MGMT_TBL2(year + "년-" + String.valueOf(i + 1) + "월"));

		if(equipment_TYPE.equals("330")) {
			sql = "SELECT \r\n" + "		CONCAT(YEAR(clt.Create_Date),'년-',MONTH(clt.Create_Date),'월') ym\r\n"
					+ "		,SUM(clt.Qty) PRODUCTION_Volume\r\n"
					+ "		,CONCAT(YEAR(clt.Create_Date),'-',MONTH(clt.Create_Date)) ym2\r\n"
					+ "FROM 	Small_Packaging_tbl clt\r\n" + "WHERE	YEAR(clt.Create_Date) = " + year + "\r\n"
					+ "AND      clt.MachineCode = '" + equipment_INFO_CODE + "'\r\n" + "GROUP BY ym";
		} else if(equipment_TYPE.equals("324")){
			sql = "SELECT \r\n" + "		CONCAT(YEAR(clt.CL_Create_Date),'년-',MONTH(clt.CL_Create_Date),'월') ym\r\n"
					+ "		,SUM(clt.CL_ProductionQty) PRODUCTION_Volume\r\n"
					+ "		,CONCAT(YEAR(clt.CL_Create_Date),'-',MONTH(clt.CL_Create_Date)) ym2\r\n"
					+ "FROM 	Crate_Lot_tbl clt\r\n" + "WHERE	YEAR(clt.CL_Create_Date) = " + year + "\r\n"
					+ "AND      clt.CL_MachineCode = '" + equipment_INFO_CODE + "'\r\n" + "GROUP BY ym";
		} else if(equipment_TYPE.equals("325")) {
			System.out.println("Ddd");
			sql = "SELECT \r\n" + "		CONCAT(YEAR(clt.CL_Create_Date),'년-',MONTH(clt.CL_Create_Date),'월') ym\r\n"
					+ "		,SUM(clt.CL_Qty) PRODUCTION_Volume\r\n"
					+ "		,CONCAT(YEAR(clt.CL_Create_Date),'-',MONTH(clt.CL_Create_Date)) ym2\r\n"
					+ "FROM 	Crate_Lot_tbl clt\r\n" + "WHERE	YEAR(clt.CL_Create_Date) = " + year + "\r\n"
					+ "AND      clt.CL_MachineCode2 = '" + equipment_INFO_CODE + "'\r\n" + "GROUP BY ym";
		}
		
		System.out.println(sql);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			for (int i = 0; i < list.size(); i++) {
				if (rs.getString("ym").equals(list.get(i).getYm())) {
					list.get(i).setPRODUCTION_Volume(rs.getString("PRODUCTION_Volume"));

					continue;
				}
			}
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getPRODUCTION_Volume() == null)
				list.get(i).setPRODUCTION_Volume("0");
		}

		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();

		return list;
	}
	
	@RequestMapping(value = "/Month_SelectX", method = RequestMethod.GET)
	public List<PRODUCTION_MGMT_TBL2> Month_SelectX(HttpServletRequest request) throws SQLException {
		List<PRODUCTION_MGMT_TBL2> list = new ArrayList<PRODUCTION_MGMT_TBL2>();
		String year = request.getParameter("year");
		String equipment_INFO_CODE = request.getParameter("equipment_INFO_CODE");

		for (int i = 0; i < 12; i++)
			list.add(new PRODUCTION_MGMT_TBL2(year + "년-" + String.valueOf(i + 1) + "월"));

		String sql = "SELECT \r\n" + "		CONCAT(YEAR(PRODUCTION_MODIFY_D),'년-',MONTH(PRODUCTION_MODIFY_D),'월') ym\r\n"
				+ "		,SUM(PRODUCTION_VOLUME) PRODUCTION_Volume\r\n"
				+ "		,CONCAT(YEAR(PRODUCTION_MODIFY_D),'-',MONTH(PRODUCTION_MODIFY_D)) ym2\r\n"
				+ "FROM 	PRODUCTION_MGMT_TBL_X pmt\r\n" + "WHERE	YEAR(PRODUCTION_MODIFY_D) = " + year + "\r\n"
				+ "AND      PRODUCTION_EQUIPMENT_CODE = '" + equipment_INFO_CODE + "'\r\n" + "GROUP BY ym";

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			for (int i = 0; i < list.size(); i++) {
				if (rs.getString("ym").equals(list.get(i).getYm())) {
					list.get(i).setPRODUCTION_Volume(rs.getString("PRODUCTION_Volume"));

					continue;
				}
			}
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getPRODUCTION_Volume() == null)
				list.get(i).setPRODUCTION_Volume("0");
		}
		
		System.out.println(list);

		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();

		return list;
	}

	@RequestMapping(value = "/Month_Select_Detail", method = RequestMethod.GET)
	public List<PRODUCTION_MGMT_TBL2> Month_Select_Detail(HttpServletRequest request) throws SQLException {
		List<PRODUCTION_MGMT_TBL2> list = new ArrayList<PRODUCTION_MGMT_TBL2>();
		// List<PRODUCTION_MGMT_TBL2> list2 = new ArrayList<PRODUCTION_MGMT_TBL2>();
		String year = request.getParameter("year");
		String equipment_INFO_CODE = request.getParameter("equipment_INFO_CODE");
		String equipment_TYPE = request.getParameter("equipment_TYPE");
		String sql = null;
		
		if(equipment_TYPE.equals("330")) {
			sql = "SELECT \r\n" + "		date_format(clt.Create_Date, '%Y-%m') ym\r\n"
					+ "		,SUM(clt.Qty) PRODUCTION_Volume\r\n"
					+ "		,CONCAT(YEAR(clt.Create_Date),'-',MONTH(clt.Create_Date)) ym2\r\n"
					+ "FROM 	Small_Packaging_tbl clt\r\n" + "WHERE	YEAR(clt.Create_Date) = ?\r\n"
					+ "AND      clt.MachineCode = ?\r\n" + "GROUP BY ym";
		} else if(equipment_TYPE.equals("324")){
			sql = "SELECT \r\n" + "		date_format(clt.CL_Create_Date, '%Y-%m') ym\r\n"
					+ "		,SUM(clt.CL_ProductionQty) PRODUCTION_Volume\r\n"
					+ "		,CONCAT(YEAR(clt.CL_Create_Date),'-',MONTH(clt.CL_Create_Date)) ym2\r\n"
					+ "FROM 	Crate_Lot_tbl clt\r\n" + "WHERE	YEAR(clt.CL_Create_Date) = ?\r\n"
					+ "AND      clt.CL_MachineCode = ?\r\n" + "GROUP BY ym";
		} else if(equipment_TYPE.equals("325")) {
			System.out.println("Ddd");
			sql = "SELECT \r\n" + "		date_format(clt.CL_Create_Date, '%Y-%m') ym\r\n"
					+ "		,SUM(clt.CL_Qty) PRODUCTION_Volume\r\n"
					+ "		,CONCAT(YEAR(clt.CL_Create_Date),'-',MONTH(clt.CL_Create_Date)) ym2\r\n"
					+ "FROM 	Crate_Lot_tbl clt\r\n" + "WHERE	YEAR(clt.CL_Create_Date) = ?\r\n"
					+ "AND      clt.CL_MachineCode2 = ?\r\n" + "GROUP BY ym";
		}
		
		for (int i = 0; i < 12; i++) {
			if (i < 9)
				list.add(new PRODUCTION_MGMT_TBL2(year + "-" + "0" + String.valueOf(i + 1)));
			else
				list.add(new PRODUCTION_MGMT_TBL2(year + "-" + String.valueOf(i + 1)));
		}

		jdbctemplate.query(sql, new RowMapper<PRODUCTION_MGMT_TBL2>() {
			@Override
			public PRODUCTION_MGMT_TBL2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCTION_MGMT_TBL2 data = new PRODUCTION_MGMT_TBL2();
				for (int i = 0; i < list.size(); i++) {
					// System.out.println(rs.getString("ym"));

					if (rs.getString("ym").equals(list.get(i).getYm())) {
						list.get(i).setPRODUCTION_Volume(rs.getString("PRODUCTION_Volume"));

						break;
					}
				}
				return data;
			}
		}, year, equipment_INFO_CODE);

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getPRODUCTION_Volume() == null)
				list.get(i).setPRODUCTION_Volume("0");
		}

		// for(PRODUCTION_MGMT_TBL2 sd : list)
		// System.out.println(sd.toString());
		if(equipment_TYPE.equals("330")) {
			sql = "SELECT 	t2.ItemCode, t2.PRODUCT_ITEM_NAME ,SUM(t2.Qty) PRODUCTION_Volume, date_format(t2.Create_Date, '%Y-%m') ym,t2.PRODUCT_ITEM_CODE\r\n"
					+ "FROM		Small_Packaging_tbl t1\r\n" + "LEFT JOIN \r\n" + "(\r\n" + "	SELECT \r\n"
					+ "		* \r\n" + "	FROM			Small_Packaging_tbl a1\r\n" + "	LEFT JOIN	Product_Info_tbl a2\r\n"
					+ "	ON a1.ItemCode = a2.PRODUCT_ITEM_CODE\r\n" + ") t2\r\n"
					+ "ON t1.Production_LotNo = t2.Production_LotNo and t1.Small_Packaging_LotNo = t2.Small_Packaging_LotNo\r\n" + "WHERE	   YEAR(t1.Create_Date) = ?\r\n"
					+ "AND      t2.MachineCode = ?\r\n" + "GROUP BY ym, t2.ItemCode";
		} else if(equipment_TYPE.equals("324")){
			sql = "SELECT 	t2.CL_ItemCode, t2.PRODUCT_ITEM_NAME ,SUM(t2.CL_ProductionQty) PRODUCTION_Volume, date_format(t2.CL_Create_Date, '%Y-%m') ym,t2.PRODUCT_ITEM_CODE\r\n"
					+ "FROM		Crate_Lot_tbl t1\r\n" + "LEFT JOIN \r\n" + "(\r\n" + "	SELECT \r\n"
					+ "		* \r\n" + "	FROM			Crate_Lot_tbl a1\r\n" + "	LEFT JOIN	Product_Info_tbl a2\r\n"
					+ "	ON a1.CL_ItemCode = a2.PRODUCT_ITEM_CODE\r\n" + ") t2\r\n"
					+ "ON t1.CL_LotNo = t2.CL_LotNo\r\n" + "WHERE	   YEAR(t1.CL_Create_Date) = ?\r\n"
					+ "AND      t2.CL_MachineCode = ?\r\n" + "GROUP BY ym, t2.CL_ItemCode";
		} else if(equipment_TYPE.equals("325")) {
			sql = "SELECT 	t2.CL_ItemCode, t2.PRODUCT_ITEM_NAME ,SUM(t2.CL_Qty) PRODUCTION_Volume, date_format(t2.CL_Create_Date, '%Y-%m') ym,t2.PRODUCT_ITEM_CODE\r\n"
					+ "FROM		Crate_Lot_tbl t1\r\n" + "LEFT JOIN \r\n" + "(\r\n" + "	SELECT \r\n"
					+ "		* \r\n" + "	FROM			Crate_Lot_tbl a1\r\n" + "	LEFT JOIN	Product_Info_tbl a2\r\n"
					+ "	ON a1.CL_ItemCode = a2.PRODUCT_ITEM_CODE\r\n" + ") t2\r\n"
					+ "ON t1.CL_LotNo = t2.CL_LotNo\r\n" + "WHERE	   YEAR(t1.CL_Create_Date) = ?\r\n"
					+ "AND      t2.CL_MachineCode2 = ?\r\n" + "GROUP BY ym, t2.CL_ItemCode";
		}
		
		

		jdbctemplate.query(sql, new RowMapper<PRODUCTION_MGMT_TBL2>() {
			@Override
			public PRODUCTION_MGMT_TBL2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCTION_MGMT_TBL2 data = new PRODUCTION_MGMT_TBL2();
				for (int i = 0; i < list.size(); i++) {
					if (rs.getString("ym").equals(list.get(i).getYm())) {
						PRODUCTION_MGMT_TBL2 datas = new PRODUCTION_MGMT_TBL2();
						datas.setPercent(rs.getString("ym"));
						datas.setYm(rs.getString("PRODUCT_ITEM_NAME"));
						datas.setPRODUCTION_Volume(rs.getString("PRODUCTION_Volume"));
						datas.setPRODUCTION_Item_Code(rs.getString("PRODUCT_ITEM_CODE"));
						datas.setPRODUCTION_Equipment_Code(equipment_INFO_CODE);
						datas.setPRODUCTION_Equip_TYPE(equipment_TYPE);

						list.get(i).set_children(datas);
						continue;
					}
				}
				return data;
			}
		}, year, equipment_INFO_CODE);

		// for(PRODUCTION_MGMT_TBL2 sd : list)
		// System.out.println(sd.toString());

		return list;
	}
	
	@RequestMapping(value = "/Month_Select_Detai", method = RequestMethod.GET)
	public List<PRODUCTION_MGMT_TBL2> Month_Select_Detai(HttpServletRequest request) throws SQLException{
		List<PRODUCTION_MGMT_TBL2> list = new ArrayList<PRODUCTION_MGMT_TBL2>();
		// List<PRODUCTION_MGMT_TBL2> list2 = new ArrayList<PRODUCTION_MGMT_TBL2>();
		String year = request.getParameter("year");
		String equipment_INFO_CODE = request.getParameter("equipment_INFO_CODE");

		String sql = "SELECT \r\n" + "date_format(PRODUCTION_MODIFY_D, '%Y-%m') ym\r\n"
				+ "		,SUM(PRODUCTION_VOLUME) PRODUCTION_Volume\r\n"
				+ "		,CONCAT(YEAR(PRODUCTION_MODIFY_D),'-',MONTH(PRODUCTION_MODIFY_D)) ym2\r\n"
				+ "FROM 	PRODUCTION_MGMT_TBL_X pmt\r\n" + "WHERE	YEAR(PRODUCTION_MODIFY_D) = ?\r\n"
				+ "AND      PRODUCTION_EQUIPMENT_CODE = ?\r\n" + "GROUP BY ym";

		for (int i = 0; i < 12; i++) {
			if (i < 9)
				list.add(new PRODUCTION_MGMT_TBL2(year + "-" + "0" + String.valueOf(i + 1)));
			else
				list.add(new PRODUCTION_MGMT_TBL2(year + "-" + String.valueOf(i + 1)));
		}

		jdbctemplate.query(sql, new RowMapper<PRODUCTION_MGMT_TBL2>() {
			@Override
			public PRODUCTION_MGMT_TBL2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCTION_MGMT_TBL2 data = new PRODUCTION_MGMT_TBL2();
				for (int i = 0; i < list.size(); i++) {
					System.out.println(rs.getString("ym"));

					if (rs.getString("ym").equals(list.get(i).getYm())) {
						list.get(i).setPRODUCTION_Volume(rs.getString("PRODUCTION_Volume"));

						break;
					}
				}
				return data;
			}
		}, year, equipment_INFO_CODE);

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getPRODUCTION_Volume() == null)
				list.get(i).setPRODUCTION_Volume("0");
		}

		// for(PRODUCTION_MGMT_TBL2 sd : list)
		// System.out.println(sd.toString());

		sql = "SELECT 	t1.PRODUCTION_PRODUCT_CODE WorkOrder_ItemCode,t2.PRODUCT_ITEM_NAME ,SUM(PRODUCTION_VOLUME) PRODUCTION_Volume,date_format(PRODUCTION_MODIFY_D, '%Y-%m') ym,t1.PRODUCTION_PRODUCT_CODE PRODUCT_ITEM_CODE\r\n"
				+ "FROM		PRODUCTION_MGMT_TBL_X t1\r\n"
				+ "LEFT JOIN PRODUCT_INFO_TBL t2 ON t1.PRODUCTION_PRODUCT_CODE = t2.PRODUCT_ITEM_CODE\r\n"
				+ "WHERE	   YEAR(PRODUCTION_MODIFY_D) = ?\r\n"
				+ "AND      PRODUCTION_Equipment_Code = ?\r\n"
				+ "GROUP BY ym,WorkOrder_ItemCode";

		jdbctemplate.query(sql, new RowMapper<PRODUCTION_MGMT_TBL2>() {
			@Override
			public PRODUCTION_MGMT_TBL2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCTION_MGMT_TBL2 data = new PRODUCTION_MGMT_TBL2();
				for (int i = 0; i < list.size(); i++) {
					if (rs.getString("ym").equals(list.get(i).getYm())) {
						PRODUCTION_MGMT_TBL2 datas = new PRODUCTION_MGMT_TBL2();
						datas.setPercent(rs.getString("ym"));
						datas.setYm(rs.getString("PRODUCT_ITEM_NAME"));
						datas.setPRODUCTION_Volume(rs.getString("PRODUCTION_Volume"));
						datas.setPRODUCTION_Item_Code(rs.getString("PRODUCT_ITEM_CODE"));
						datas.setPRODUCTION_Equipment_Code(equipment_INFO_CODE);

						list.get(i).set_children(datas);
						continue;
					}
				}
				return data;
			}
		}, year, equipment_INFO_CODE);

		// for(PRODUCTION_MGMT_TBL2 sd : list)
		// System.out.println(sd.toString());

		return list;
	}

	@RequestMapping(value = "/Month_Select_Detail2", method = RequestMethod.GET)
	public List<PRODUCTION_MGMT_TBL2> Month_Select_Detail2(HttpServletRequest request) throws SQLException {
		List<PRODUCTION_MGMT_TBL2> list = new ArrayList<PRODUCTION_MGMT_TBL2>();
		// List<PRODUCTION_MGMT_TBL2> list2 = new ArrayList<PRODUCTION_MGMT_TBL2>();
		String year = request.getParameter("year");
		String equipment_INFO_CODE = request.getParameter("production_Equipment_Code");
		String production_Item_Code = request.getParameter("production_Item_Code");
		String equipment_TYPE = request.getParameter("production_equipment_TYPE");
		String sql = null;

		if(equipment_TYPE.equals("330")) {
			sql = "SELECT \r\n" + "		date_format(t1.Create_Date, '%Y-%m') ym\r\n"
					+ "		,SUM(t1.Qty) PRODUCTION_Volume\r\n"
					+ "		,CONCAT(YEAR(t1.Create_Date),'-',MONTH(t1.Create_Date)) ym2\r\n"
					+ "FROM 	Small_Packaging_tbl t1\r\n" + "LEFT JOIN \r\n" + "(\r\n" + "	SELECT \r\n"
					+ "		* \r\n" + "	FROM			Small_Packaging_tbl a1\r\n" + "	LEFT JOIN	Product_Info_tbl a2\r\n"
					+ "	ON a1.ItemCode = a2.PRODUCT_ITEM_CODE\r\n" + ") t2\r\n"
					+ "ON t1.Small_Packaging_LotNo = t2.Small_Packaging_LotNo and t1.Production_LotNo = t2.Production_LotNo\r\n" + "WHERE	YEAR(t1.Create_Date) = ?\r\n"
					+ "AND      t1.MachineCode = ?\r\n" + "AND 		PRODUCT_ITEM_CODE = ?\r\n" + "GROUP BY ym";
		} else if(equipment_TYPE.equals("324")){
			sql = "SELECT \r\n" + "		date_format(t1.CL_Create_Date, '%Y-%m') ym\r\n"
					+ "		,SUM(t1.CL_ProductionQty) PRODUCTION_Volume\r\n"
					+ "		,CONCAT(YEAR(t1.CL_Create_Date),'-',MONTH(t1.CL_Create_Date)) ym2\r\n"
					+ "FROM 	Crate_Lot_tbl t1\r\n" + "LEFT JOIN \r\n" + "(\r\n" + "	SELECT \r\n"
					+ "		* \r\n" + "	FROM			Crate_Lot_tbl a1\r\n" + "	LEFT JOIN	Product_Info_tbl a2\r\n"
					+ "	ON a1.CL_ItemCode = a2.PRODUCT_ITEM_CODE\r\n" + ") t2\r\n"
					+ "ON t1.CL_LotNo = t2.CL_LotNo\r\n" + "WHERE	YEAR(t1.CL_Create_Date) = ?\r\n"
					+ "AND      t1.CL_MachineCode = ?\r\n" + "AND 		PRODUCT_ITEM_CODE = ?\r\n" + "GROUP BY ym";
		} else if(equipment_TYPE.equals("325")) {
			sql = "SELECT \r\n" + "		date_format(t1.CL_Create_Date, '%Y-%m') ym\r\n"
					+ "		,SUM(t1.CL_Qty) PRODUCTION_Volume\r\n"
					+ "		,CONCAT(YEAR(t1.CL_Create_Date),'-',MONTH(t1.CL_Create_Date)) ym2\r\n"
					+ "FROM 	Crate_Lot_tbl t1\r\n" + "LEFT JOIN \r\n" + "(\r\n" + "	SELECT \r\n"
					+ "		* \r\n" + "	FROM			Crate_Lot_tbl a1\r\n" + "	LEFT JOIN	Product_Info_tbl a2\r\n"
					+ "	ON a1.CL_ItemCode = a2.PRODUCT_ITEM_CODE\r\n" + ") t2\r\n"
					+ "ON t1.CL_LotNo = t2.CL_LotNo\r\n" + "WHERE	YEAR(t1.CL_Create_Date) = ?\r\n"
					+ "AND      t1.CL_MachineCode2 = ?\r\n" + "AND 		PRODUCT_ITEM_CODE = ?\r\n" + "GROUP BY ym";
		}
		
		

		for (int i = 0; i < 12; i++) {
			if (i < 9)
				list.add(new PRODUCTION_MGMT_TBL2(year + "-" + "0" + String.valueOf(i + 1)));
			else
				list.add(new PRODUCTION_MGMT_TBL2(year + "-" + String.valueOf(i + 1)));
		}

		jdbctemplate.query(sql, new RowMapper<PRODUCTION_MGMT_TBL2>() {
			@Override
			public PRODUCTION_MGMT_TBL2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCTION_MGMT_TBL2 data = new PRODUCTION_MGMT_TBL2();
				for (int i = 0; i < list.size(); i++) {
					// System.out.println(rs.getString("ym"));

					if (rs.getString("ym").equals(list.get(i).getYm())) {
						list.get(i).setPRODUCTION_Volume(rs.getString("PRODUCTION_Volume"));

						break;
					}
				}
				return data;
			}
		}, year, equipment_INFO_CODE, production_Item_Code);

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getPRODUCTION_Volume() == null)
				list.get(i).setPRODUCTION_Volume("0");
		}

		return list;
	}
	
	@RequestMapping(value = "/Month_Select_Detail2X", method = RequestMethod.GET)
	public List<PRODUCTION_MGMT_TBL2> Month_Select_Detail2X(HttpServletRequest request) throws SQLException{
		List<PRODUCTION_MGMT_TBL2> list = new ArrayList<PRODUCTION_MGMT_TBL2>();
		// List<PRODUCTION_MGMT_TBL2> list2 = new ArrayList<PRODUCTION_MGMT_TBL2>();
		String year = request.getParameter("year");
		String equipment_INFO_CODE = request.getParameter("production_Equipment_Code");
		String production_Item_Code = request.getParameter("production_Item_Code");

		String sql = "SELECT \r\n"
				+ "		date_format(PRODUCTION_MODIFY_D, '%Y-%m') ym\r\n"
				+ "		,SUM(PRODUCTION_VOLUME) PRODUCTION_Volume\r\n"
				+ "		,CONCAT(YEAR(PRODUCTION_MODIFY_D),'-',MONTH(PRODUCTION_MODIFY_D)) ym2\r\n"
				+ "FROM 	PRODUCTION_MGMT_TBL_X t1\r\n"
				+ "WHERE	YEAR(PRODUCTION_MODIFY_D) = ?\r\n"
				+ "AND      PRODUCTION_EQUIPMENT_CODE = ?\r\n"
				+ "AND 		PRODUCTION_PRODUCT_CODE = ?\r\n"
				+ "GROUP BY ym";

		for (int i = 0; i < 12; i++) {
			if (i < 9)
				list.add(new PRODUCTION_MGMT_TBL2(year + "-" + "0" + String.valueOf(i + 1)));
			else
				list.add(new PRODUCTION_MGMT_TBL2(year + "-" + String.valueOf(i + 1)));
		}

		jdbctemplate.query(sql, new RowMapper<PRODUCTION_MGMT_TBL2>() {
			@Override
			public PRODUCTION_MGMT_TBL2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCTION_MGMT_TBL2 data = new PRODUCTION_MGMT_TBL2();
				for (int i = 0; i < list.size(); i++) {
					// System.out.println(rs.getString("ym"));

					if (rs.getString("ym").equals(list.get(i).getYm())) {
						list.get(i).setPRODUCTION_Volume(rs.getString("PRODUCTION_Volume"));

						break;
					}
				}
				return data;
			}
		}, year, equipment_INFO_CODE, production_Item_Code);

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getPRODUCTION_Volume() == null)
				list.get(i).setPRODUCTION_Volume("0");
		}

		return list;
	}

	@RequestMapping(value = "/Quarter_Select", method = RequestMethod.GET)
	public List<PRODUCTION_MGMT_TBL2> Quarter_Select(HttpServletRequest request) throws SQLException {
		List<PRODUCTION_MGMT_TBL2> list = new ArrayList<PRODUCTION_MGMT_TBL2>();
		String year = request.getParameter("year");
		String equipment_INFO_CODE = request.getParameter("equipment_INFO_CODE");

		for (int i = 0; i < 4; i++)
			list.add(new PRODUCTION_MGMT_TBL2(year + "년-" + String.valueOf(i + 1) + "분기"));

		String sql = "SELECT \r\n" + "			CONCAT(YEAR(PRODUCTION_Date),'년-',QUARTER(PRODUCTION_Date),'분기') ym\r\n"
				+ "			,SUM(PRODUCTION_Volume) PRODUCTION_Volume\r\n" + "FROM 		PRODUCTION_MGMT_TBL2\r\n"
				+ "WHERE\r\n" + "			DATE_FORMAT(PRODUCTION_Date, '%Y') = ?\r\n"
				+ "AND      PRODUCTION_Equipment_Code = ?\r\n" + "GROUP BY ym";

		jdbctemplate.query(sql, new RowMapper<PRODUCTION_MGMT_TBL2>() {
			@Override
			public PRODUCTION_MGMT_TBL2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCTION_MGMT_TBL2 data = new PRODUCTION_MGMT_TBL2();
				for (int i = 0; i < list.size(); i++) {
					if (rs.getString("ym").equals(list.get(i).getYm())) {
						list.get(i).setPRODUCTION_Volume(rs.getString("PRODUCTION_Volume"));
						break;
					}
				}
				return data;
			}
		}, year, equipment_INFO_CODE);

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getPRODUCTION_Volume() == null)
				list.get(i).setPRODUCTION_Volume("0");
		}

		return list;
	}

	@RequestMapping(value = "/Quarter_Select_Detail", method = RequestMethod.GET)
	public List<PRODUCTION_MGMT_TBL2> Quarter_Select_Detail(HttpServletRequest request) throws SQLException {
		List<PRODUCTION_MGMT_TBL2> list = new ArrayList<PRODUCTION_MGMT_TBL2>();
		String year = request.getParameter("year");
		String equipment_INFO_CODE = request.getParameter("equipment_INFO_CODE");

		for (int i = 0; i < 4; i++)
			list.add(new PRODUCTION_MGMT_TBL2(year + "년-" + String.valueOf(i + 1) + "분기"));

		String sql = "SELECT \r\n" + "			CONCAT(YEAR(PRODUCTION_Date),'년-',QUARTER(PRODUCTION_Date),'분기') ym\r\n"
				+ "			,SUM(PRODUCTION_Volume) PRODUCTION_Volume\r\n" + "FROM 		PRODUCTION_MGMT_TBL2\r\n"
				+ "WHERE\r\n" + "			DATE_FORMAT(PRODUCTION_Date, '%Y') = ?\r\n"
				+ "AND      PRODUCTION_Equipment_Code = ?\r\n" + "GROUP BY ym";

		jdbctemplate.query(sql, new RowMapper<PRODUCTION_MGMT_TBL2>() {
			@Override
			public PRODUCTION_MGMT_TBL2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCTION_MGMT_TBL2 data = new PRODUCTION_MGMT_TBL2();
				for (int i = 0; i < list.size(); i++) {
					if (rs.getString("ym").equals(list.get(i).getYm())) {
						list.get(i).setPRODUCTION_Volume(rs.getString("PRODUCTION_Volume"));
						break;
					}
				}
				return data;
			}
		}, year, equipment_INFO_CODE);

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getPRODUCTION_Volume() == null)
				list.get(i).setPRODUCTION_Volume("0");
		}

		// ----------------------------------------------------------------------------------------------

		sql = "SELECT 	t2.WorkOrder_ItemCode,t2.PRODUCT_ITEM_NAME ,SUM(PRODUCTION_Volume) PRODUCTION_Volume,CONCAT(YEAR(PRODUCTION_Date),'년-',QUARTER(PRODUCTION_Date),'분기') ym\r\n"
				+ "FROM		PRODUCTION_MGMT_TBL2 t1\r\n" + "LEFT JOIN \r\n" + "(\r\n" + "	SELECT \r\n"
				+ "		* \r\n" + "	FROM			WorkOrder_tbl a1\r\n" + "	LEFT JOIN	PRODUCT_INFO_TBL a2\r\n"
				+ "	ON a1.WorkOrder_ItemCode = a2.PRODUCT_ITEM_CODE\r\n" + ") t2\r\n"
				+ "ON t1.PRODUCTION_WorkOrder_ONo = t2.WorkOrder_ONo\r\n" + "WHERE	   YEAR(PRODUCTION_Date) = ?\r\n"
				+ "AND      PRODUCTION_Equipment_Code = ?\r\n" + "GROUP BY ym,WorkOrder_ItemCode";

		jdbctemplate.query(sql, new RowMapper<PRODUCTION_MGMT_TBL2>() {
			@Override
			public PRODUCTION_MGMT_TBL2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCTION_MGMT_TBL2 data = new PRODUCTION_MGMT_TBL2();
				for (int i = 0; i < list.size(); i++) {
					if (rs.getString("ym").equals(list.get(i).getYm())) {
						PRODUCTION_MGMT_TBL2 datas = new PRODUCTION_MGMT_TBL2();
						datas.setYm(rs.getString("PRODUCT_ITEM_NAME"));
						datas.setPRODUCTION_Volume(rs.getString("PRODUCTION_Volume"));

						list.get(i).set_children(datas);
						continue;
					}
				}
				return data;
			}
		}, year, equipment_INFO_CODE);

		return list;
	}

	@RequestMapping(value = "/Year_Select", method = RequestMethod.GET)
	public List<PRODUCTION_MGMT_TBL2> Year_Select(HttpServletRequest request) {
		List<PRODUCTION_MGMT_TBL2> list = new ArrayList<PRODUCTION_MGMT_TBL2>();
		String year = request.getParameter("year");
		String year1 = request.getParameter("year1");
		String year2 = request.getParameter("year2");
		String equipment_INFO_CODE = request.getParameter("equipment_INFO_CODE");
		String equipment_TYPE = request.getParameter("equipment_TYPE");
		
		int int_year1 = Integer.parseInt(year1);
		int int_year2 = Integer.parseInt(year2);
		String sql = null;

		List<Integer> year_list = new ArrayList<Integer>();
		for (int i = int_year1; i <= int_year2; i++) {
			list.add(new PRODUCTION_MGMT_TBL2(year+"년-"+String.valueOf(i+1)+"분기"));
			year_list.add(i);
		}
		
		if(equipment_TYPE.equals("330")) {
			sql = "SELECT \r\n" + "		YEAR(Create_Date) ym\r\n"
					+ "		,SUM(Qty) PRODUCTION_Volume\r\n" + "FROM 	Small_Packaging_tbl pmt\r\n"
					+ "WHERE	MachineCode = ?\r\n" + "GROUP BY ym";
		} else if(equipment_TYPE.equals("324")){
			sql = "SELECT \r\n" + "		YEAR(CL_Create_Date) ym\r\n"
					+ "		,SUM(CL_ProductionQty) PRODUCTION_Volume\r\n" + "FROM 	Crate_Lot_tbl pmt\r\n"
					+ "WHERE	CL_MachineCode = ?\r\n" + "GROUP BY ym";
		} else if(equipment_TYPE.equals("325")) {
			sql = "SELECT \r\n" + "		YEAR(CL_Create_Date) ym\r\n"
					+ "		,SUM(CL_Qty) PRODUCTION_Volume\r\n" + "FROM 	Crate_Lot_tbl pmt\r\n"
					+ "WHERE	CL_MachineCode2 = ?\r\n" + "GROUP BY ym";
		}

		list = jdbctemplate.query(sql,
				new RowMapper<PRODUCTION_MGMT_TBL2>() {
					@Override
					public PRODUCTION_MGMT_TBL2 mapRow(ResultSet rs, int rowNum) throws SQLException {

						PRODUCTION_MGMT_TBL2 data = new PRODUCTION_MGMT_TBL2();

						String flag_ym = rs.getString("ym");
						System.out.println("flag_ym : " + flag_ym);
						data.setYm(flag_ym);
						data.setPRODUCTION_Volume(rs.getString("PRODUCTION_Volume"));

						return data;
					}
				}, equipment_INFO_CODE);
		
		if(equipment_TYPE.equals("330")) {
			sql = "SELECT 	t2.ItemCode CL_ItemCode,t2.PRODUCT_ITEM_NAME ,SUM(t1.Qty) PRODUCTION_Volume,YEAR(t1.Create_Date) ym\r\n"
					+ "FROM		Small_Packaging_tbl t1\r\n" + "LEFT JOIN \r\n" + "(\r\n" + "	SELECT \r\n"
					+ "		* \r\n" + "	FROM			Small_Packaging_tbl a1\r\n"
					+ "	LEFT JOIN	Product_Info_tbl a2\r\n"
					+ "	ON a1.ItemCode = a2.PRODUCT_ITEM_CODE\r\n" + ") t2\r\n"
					+ "ON t1.Small_Packaging_LotNo = t2.Small_Packaging_LotNo and t1.Production_LotNo = t2.Production_LotNo\r\n"
					+ "WHERE	t1.MachineCode = ?\r\n" + "GROUP BY ym, t1.ItemCode";
		} else if(equipment_TYPE.equals("324")){
			sql = "SELECT 	t2.CL_ItemCode,t2.PRODUCT_ITEM_NAME ,SUM(t1.CL_ProductionQty) PRODUCTION_Volume,YEAR(t1.CL_Create_Date) ym\r\n"
					+ "FROM		Crate_Lot_tbl t1\r\n" + "LEFT JOIN \r\n" + "(\r\n" + "	SELECT \r\n"
					+ "		* \r\n" + "	FROM			Crate_Lot_tbl a1\r\n"
					+ "	LEFT JOIN	Product_Info_tbl a2\r\n"
					+ "	ON a1.CL_ItemCode = a2.PRODUCT_ITEM_CODE\r\n" + ") t2\r\n"
					+ "ON t1.CL_LotNo = t2.CL_LotNo\r\n"
					+ "WHERE	t1.CL_MachineCode = ?\r\n" + "GROUP BY ym, t1.CL_ItemCode";
		} else if(equipment_TYPE.equals("325")) {
			sql = "SELECT 	t2.CL_ItemCode,t2.PRODUCT_ITEM_NAME ,SUM(t1.CL_Qty) PRODUCTION_Volume,YEAR(t1.CL_Create_Date) ym\r\n"
					+ "FROM		Crate_Lot_tbl t1\r\n" + "LEFT JOIN \r\n" + "(\r\n" + "	SELECT \r\n"
					+ "		* \r\n" + "	FROM			Crate_Lot_tbl a1\r\n"
					+ "	LEFT JOIN	Product_Info_tbl a2\r\n"
					+ "	ON a1.CL_ItemCode = a2.PRODUCT_ITEM_CODE\r\n" + ") t2\r\n"
					+ "ON t1.CL_LotNo = t2.CL_LotNo\r\n"
					+ "WHERE	t1.CL_MachineCode2 = ?\r\n" + "GROUP BY ym, t1.CL_ItemCode";
		}

	
		
		List<PRODUCTION_MGMT_TBL2> list2 = jdbctemplate.query(sql,
				new RowMapper<PRODUCTION_MGMT_TBL2>() {
					@Override
					public PRODUCTION_MGMT_TBL2 mapRow(ResultSet rs, int rowNum) throws SQLException {

						PRODUCTION_MGMT_TBL2 data = null;
						data = new PRODUCTION_MGMT_TBL2();
						data.setPercent(rs.getString("ym"));
						data.setPRODUCTION_Equipment_Code(rs.getString("CL_ItemCode"));
						data.setYm(rs.getString("PRODUCT_ITEM_NAME"));
						data.setPRODUCTION_Volume(rs.getString("PRODUCTION_Volume"));

						return data;
					}
				}, equipment_INFO_CODE);

		for (PRODUCTION_MGMT_TBL2 data1 : list) {
			for (PRODUCTION_MGMT_TBL2 data2 : list2) {
				if (data1.getYm().equals(data2.getPercent()))
					data1.set_children(data2);
			}
		}
		
		List<PRODUCTION_MGMT_TBL2> _list = new ArrayList<PRODUCTION_MGMT_TBL2>();
		for (int num : year_list) {
			list.forEach(x -> {
				if (x.getYm().equals(String.valueOf(num))) {
					_list.add(x);
				}
			});
		}

		return _list;
	}
	
	@RequestMapping(value = "/Year_SelectX", method = RequestMethod.GET)
	public List<PRODUCTION_MGMT_TBL2> Year_SelectX(HttpServletRequest request) {
		List<PRODUCTION_MGMT_TBL2> list = new ArrayList<PRODUCTION_MGMT_TBL2>();
		String year = request.getParameter("year");
		String year1 = request.getParameter("year1");
		String year2 = request.getParameter("year2");
		String equipment_INFO_CODE = request.getParameter("equipment_INFO_CODE");

		int int_year1 = Integer.parseInt(year1);
		int int_year2 = Integer.parseInt(year2);

		List<Integer> year_list = new ArrayList<Integer>();
		for (int i = int_year1; i <= int_year2; i++) {
			list.add(new PRODUCTION_MGMT_TBL2(year+"년-"+String.valueOf(i+1)+"분기"));
			year_list.add(i);
		}

		String sql = "SELECT \r\n"
				+ "		YEAR(PRODUCTION_MODIFY_D) ym\r\n"
				+ "		,SUM(PRODUCTION_VOLUME) PRODUCTION_Volume\r\n"
				+ "FROM 	PRODUCTION_MGMT_TBL_X pmt\r\n"
				+ "WHERE	PRODUCTION_EQUIPMENT_CODE = ?\r\n"
				+ "GROUP BY ym";
		
		list = jdbctemplate.query(sql,
				new RowMapper<PRODUCTION_MGMT_TBL2>() {
					@Override
					public PRODUCTION_MGMT_TBL2 mapRow(ResultSet rs, int rowNum) throws SQLException {

						PRODUCTION_MGMT_TBL2 data = new PRODUCTION_MGMT_TBL2();

						String flag_ym = rs.getString("ym");
						System.out.println("flag_ym : " + flag_ym);
						data.setYm(flag_ym);
						data.setPRODUCTION_Volume(rs.getString("PRODUCTION_Volume"));

						return data;
					}
				}, equipment_INFO_CODE);

		sql = "SELECT 	t1.PRODUCTION_PRODUCT_CODE WorkOrder_ItemCode,t2.PRODUCT_ITEM_NAME ,SUM(PRODUCTION_VOLUME) PRODUCTION_Volume ,YEAR(t1.PRODUCTION_MODIFY_D) ym\r\n"
				+ "FROM		PRODUCTION_MGMT_TBL_X t1\r\n"
				+ "LEFT JOIN	PRODUCT_INFO_TBL t2\r\n"
				+ "ON t1.PRODUCTION_PRODUCT_CODE = t2.PRODUCT_ITEM_CODE\r\n"
				+ "WHERE	PRODUCTION_EQUIPMENT_CODE = ?\r\n"
				+ "GROUP BY ym,WorkOrder_ItemCode";
		
		List<PRODUCTION_MGMT_TBL2> list2 = jdbctemplate.query(sql,
				new RowMapper<PRODUCTION_MGMT_TBL2>() {
					@Override
					public PRODUCTION_MGMT_TBL2 mapRow(ResultSet rs, int rowNum) throws SQLException {

						PRODUCTION_MGMT_TBL2 data = null;
						data = new PRODUCTION_MGMT_TBL2();
						data.setPercent(rs.getString("ym"));
						data.setPRODUCTION_Equipment_Code(rs.getString("WorkOrder_ItemCode"));
						data.setYm(rs.getString("PRODUCT_ITEM_NAME"));
						data.setPRODUCTION_Volume(rs.getString("PRODUCTION_Volume"));

						return data;
					}
				}, equipment_INFO_CODE);

		for (PRODUCTION_MGMT_TBL2 data1 : list) {
			for (PRODUCTION_MGMT_TBL2 data2 : list2) {
				if (data1.getYm().equals(data2.getPercent()))
					data1.set_children(data2);
			}
		}
		
		List<PRODUCTION_MGMT_TBL2> _list = new ArrayList<PRODUCTION_MGMT_TBL2>();
		for (int num : year_list) {
			list.forEach(x -> {
				if (x.getYm().equals(String.valueOf(num))) {
					_list.add(x);
				}
			});
		}

		return _list;
	}
}
