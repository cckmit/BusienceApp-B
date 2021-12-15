package com.busience.materialLX.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.DtlDto;
import com.busience.common.service.DtlService;
import com.busience.standard.dto.DTL_TBL;
import com.busience.standard.dto.PRODUCT_INFO_TBL;

@RestController("matOutputLXTabletRestController")
@RequestMapping("/tablet/matOutputLXTabletRest")
public class matOutputLXTabletRestController {
	
	@Autowired
	DtlService dtls;
	
	@Autowired
	JdbcTemplate jdbctemplate;

	@RequestMapping(value = "/MOM_Save", method = RequestMethod.GET)
	public void MOM_Save(HttpServletRequest request, Model model) {
		String sql = "INSERT INTO OutMatLX_tbl\r\n"
				+ "VALUES(\r\n"
				+ "	(\r\n"
				+ "		SELECT\r\n"
				+ "					MAX(t2.OutMat_No)+1 max_value\r\n"
				+ "		FROM		OutMatLX_tbl t2\r\n"
				+ "	),\r\n"
				+ "	'"+request.getParameter("pdcode")+"',\r\n"
				+ "	'"+request.getParameter("qty")+"',\r\n"
				+ "	'"+request.getParameter("dtcode")+"',\r\n"
				+ "	'13',\r\n"
				+ "	'208',\r\n"
				+ "	NOW(),\r\n"
				+ "	NOW(),\r\n"
				+ "	'admin'\r\n"
				+ ")";
		
		jdbctemplate.update(sql);
		
		sql = " update StockMatLX_tbl set" + " SM_Out_Qty = SM_Out_Qty+'" + request.getParameter("qty")
		+ "'" + " where SM_Code = '" + request.getParameter("pdcode") + "'";
		
		jdbctemplate.update(sql);
	}
	
	@RequestMapping(value = "/Current_Save", method = RequestMethod.GET)
	public String Current_Save(HttpServletRequest request, Model model)
	{
		String sql = "select SM_Last_Qty+SM_In_Qty-SM_Out_Qty cqty from StockMatLX_tbl WHERE SM_Code = ?";
		
		try
		{
			return jdbctemplate.queryForObject(sql, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("cqty");
				}
			},request.getParameter("code"));
		}
		catch(Exception ex)
		{
			return "0";
		}
	}
	
	@RequestMapping(value = "/Current_List", method = RequestMethod.GET)
	public List<PRODUCT_INFO_TBL> Current_List(HttpServletRequest request, Model model) {
		//PRODUCT_ITEM_CLSFC_1
		String sql = "SELECT *,t2.CHILD_TBL_TYPE FROM PRODUCT_INFO_TBL t1 INNER JOIN DTL_TBL t2 ON t1."+request.getParameter("CLSFC")+" = t2.CHILD_TBL_NO WHERE t2.CHILD_TBL_TYPE = '"+request.getParameter("value")+"'";
		
		return jdbctemplate.query(sql, new RowMapper() {

			@Override
			public PRODUCT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCT_INFO_TBL data = new PRODUCT_INFO_TBL();
				data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
				data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				data.setPRODUCT_UNIT_PRICE(rs.getInt("PRODUCT_UNIT_PRICE"));
				return data;
			}
		});
	}
	
	@RequestMapping(value = "/Out_List", method = RequestMethod.GET)
	public List<DtlDto> Out_List(HttpServletRequest request, Model model){
		return dtls.getAlldtl(3);
	}
	
	@RequestMapping(value = "/Out_Clsfc", method = RequestMethod.GET)
	public PRODUCT_INFO_TBL Out_Clsfc(HttpServletRequest request, Model model){
		String sql = "SELECT t1.*,t2.CHILD_TBL_TYPE FROM PRODUCT_INFO_TBL t1 INNER JOIN DTL_TBL t2 ON t1."+request.getParameter("PRODUCT_ITEM_CLSFC")+" = t2.CHILD_TBL_NO WHERE PRODUCT_ITEM_NAME LIKE '%"+request.getParameter("PRODUCT_ITEM_NAME")+"%' LIMIT 1";
		
		try {
			return jdbctemplate.queryForObject(sql, new RowMapper<PRODUCT_INFO_TBL>() {

				@Override
				public PRODUCT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
					PRODUCT_INFO_TBL data = new PRODUCT_INFO_TBL();
					data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
					data.setPRODUCT_ITEM_CLSFC_1(rs.getString("CHILD_TBL_TYPE"));
					return data;
				}
			});
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
}
