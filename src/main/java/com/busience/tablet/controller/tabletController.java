package com.busience.tablet.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.dto.DtlDto;
import com.busience.common.service.DtlService;
import com.busience.productionLX.dto.PRODUCTION_INFO_TBL;
import com.busience.standard.dto.DTL_TBL;
import com.busience.standard.dto.EQUIPMENT_INFO_TBL;
import com.busience.standard.dto.PRODUCT_INFO_TBL;

@Controller
public class tabletController {
	
	@Autowired
	DtlService dtlService;
	
	@Autowired
	JdbcTemplate jdbctemplate;

	@GetMapping("tablet/matOutputLXTablet")
	public String matOutputLXTablet(Model model) {

		model.addAttribute("b1", jdbctemplate.queryForObject("SELECT NEW_TBL_CODE FROM CMN_TBL WHERE NEW_TBL_NAME = '품목분류1'", new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("NEW_TBL_CODE");
			}
		}));
		
		model.addAttribute("b2", jdbctemplate.queryForObject("SELECT NEW_TBL_CODE FROM CMN_TBL WHERE NEW_TBL_NAME = '품목분류2'", new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("NEW_TBL_CODE");
			}
		}));
		
		String sql = "SELECT * FROM DTL_TBL WHERE NEW_TBL_CODE = (SELECT NEW_TBL_CODE FROM CMN_TBL WHERE NEW_TBL_NAME = '품목분류1') Order BY CHILD_TBL_NUM + 0";

		model.addAttribute("list1",jdbctemplate.query(sql, new RowMapper() {

			@Override
			public DTL_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				DTL_TBL data = new DTL_TBL();
				data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
				data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
				data.setCHILD_TBL_NUM(rs.getString("CHILD_TBL_NUM"));
				data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
				data.setCHILD_TBL_RMARK(rs.getString("CHILD_TBL_RMARK"));
				data.setCHILD_TBL_USE_STATUS(rs.getString("CHILD_TBL_USE_STATUS"));
				return data;
			}
		}));
		
		sql = "SELECT * FROM DTL_TBL WHERE NEW_TBL_CODE = (SELECT NEW_TBL_CODE FROM CMN_TBL WHERE NEW_TBL_NAME = '품목분류2') Order BY CHILD_TBL_NUM + 0";

		model.addAttribute("list2",jdbctemplate.query(sql, new RowMapper() {

			@Override
			public DTL_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				DTL_TBL data = new DTL_TBL();
				data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
				data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
				data.setCHILD_TBL_NUM(rs.getString("CHILD_TBL_NUM"));
				data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
				data.setCHILD_TBL_RMARK(rs.getString("CHILD_TBL_RMARK"));
				data.setCHILD_TBL_USE_STATUS(rs.getString("CHILD_TBL_USE_STATUS"));
				return data;
			}
		}));
		
		List<DtlDto> list3 = dtlService.getDtl(3);
		model.addAttribute("list3",list3);
		model.addAttribute("list3_flag",(list3.size() == 1)?"off":"on");
		
		return "normal/tablet/matOutputLXTablet";
	}
	
	@GetMapping("tablet/matOutputLXTabletA")
	public String matOutputLXTabletA(Model model) {
		
		model.addAttribute("product_list",jdbctemplate.query("SELECT * FROM PRODUCT_INFO_TBL WHERE PRODUCT_ITEM_CLSFC_1 = '32'", new RowMapper() {

	         @Override
	         public PRODUCTION_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
	            PRODUCTION_INFO_TBL data = new PRODUCTION_INFO_TBL();
	            data.setPRODUCTION_PRODUCT_CODE(rs.getString("PRODUCT_ITEM_CODE"));
	            data.setPRODUCTION_EQUIPMENT_INFO_NAME(rs.getString("PRODUCT_ITEM_NAME"));
	            data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
	            return data;
	         }
	      }));

		model.addAttribute("dtl_list",jdbctemplate.query("SELECT * FROM DTL_TBL WHERE NEW_TBL_CODE='3' AND CHILD_TBL_RMARK='실'", new RowMapper() {

	         @Override
	         public DTL_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	 DTL_TBL data = new DTL_TBL();
	            data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
	            data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
	            return data;
	         }
	      }));
		
		return "normal/tablet/matOutputLXTabletA";
	}
	
	//workOrderTablet
	@GetMapping("/tablet/workOrderTablet")
	public String workOrderTablet(Model model, HttpServletRequest request) throws SQLException
	{
			return jdbctemplate.queryForObject("select CHILD_TBL_RMARK from DTL_TBL where CHILD_TBL_NO = '281'", new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					String sql = "SELECT * FROM EQUIPMENT_INFO_TBL WHERE EQUIPMENT_INFO_CODE='"+( (request.getParameter("code")==null || request.getParameter("code").equals("null")) ? "' or 1=1" : request.getParameter("code")+"'" );
					
					List<EQUIPMENT_INFO_TBL> list = jdbctemplate.query(sql, new RowMapper<EQUIPMENT_INFO_TBL>() {
						@Override
						public EQUIPMENT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
							EQUIPMENT_INFO_TBL data = new EQUIPMENT_INFO_TBL();
							data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
							data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
							return data;
						}
					});
					
					model.addAttribute("list",list);
					
					model.addAttribute("list2",
							jdbctemplate.query("SELECT * FROM DTL_TBL WHERE NEW_TBL_CODE='29' AND CHILD_TBL_NO <> '246'", new RowMapper<DTL_TBL>() {
								@Override
								public DTL_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
									DTL_TBL data = new DTL_TBL();
									
									data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
									
									if(rs.getString("CHILD_TBL_NO").equals("242"))
										data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE")+"<br/>Not accepted");
									else if(rs.getString("CHILD_TBL_NO").equals("243"))
										data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE")+"<br/>Accepted");
									else if(rs.getString("CHILD_TBL_NO").equals("244"))
										data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE")+"<br/>Start time of work");
									else if(rs.getString("CHILD_TBL_NO").equals("245"))
										data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE")+"<br/>End time of work");
									return data;
								}
							}));
					
					model.addAttribute("visibility",jdbctemplate.queryForObject("select CHILD_TBL_RMARK from DTL_TBL where CHILD_TBL_NO = '282'", new RowMapper<String>() {

						@Override
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getString(1);
						}
					}));
					
					return "normal/tablet/"+rs.getString(1);
				}
			});
	}
	
	// WorkOrderInsertB
		@GetMapping("/tablet/workOrderInsertB")
		public String WorkOrderInsertB(Model model, HttpServletRequest request) throws SQLException {

			System.out.println(request.getParameter("code"));
			
			String sql = "SELECT * FROM EQUIPMENT_INFO_TBL WHERE EQUIPMENT_INFO_CODE='"+( (request.getParameter("code")==null || request.getParameter("code").equals("null")) ? "' or 1=1" : request.getParameter("code")+"'" );
			
			model.addAttribute("list",
					jdbctemplate.query(sql, new RowMapper<EQUIPMENT_INFO_TBL>() {
						@Override
						public EQUIPMENT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
							EQUIPMENT_INFO_TBL data = new EQUIPMENT_INFO_TBL();
							data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
							data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
							return data;
						}
					}));

			model.addAttribute("list2",
					jdbctemplate.query("SELECT * FROM DTL_TBL WHERE NEW_TBL_CODE='29' AND CHILD_TBL_NO <> '246'", new RowMapper<DTL_TBL>() {
						@Override
						public DTL_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
							DTL_TBL data = new DTL_TBL();
							
							data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
							
							if(rs.getString("CHILD_TBL_NO").equals("242"))
								data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
							else if(rs.getString("CHILD_TBL_NO").equals("243"))
								data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
							else if(rs.getString("CHILD_TBL_NO").equals("244"))
								data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
							else if(rs.getString("CHILD_TBL_NO").equals("245"))
								data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
							return data;
						}
					}));
			
			model.addAttribute("list3",
					jdbctemplate.query("SELECT * FROM PRODUCT_INFO_TBL LIMIT 10", new RowMapper<PRODUCT_INFO_TBL>() {
						@Override
						public PRODUCT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
							PRODUCT_INFO_TBL data = new PRODUCT_INFO_TBL();

							data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
							data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
							data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
							data.setPRODUCT_UNIT_PRICE(rs.getInt("PRODUCT_UNIT_PRICE"));
							return data;
						}
					}));

			return "normal/tablet/workOrderInsertB";
		}
		
		@GetMapping("/tablet/workOrderStartBB")
		public String WorkOrderStartBB(Model model, HttpServletRequest request) throws SQLException {

			String sql = "SELECT * FROM EQUIPMENT_INFO_TBL WHERE EQUIPMENT_INFO_CODE='"+( (request.getParameter("code")==null  || request.getParameter("code").equals("null")) ? "' or 1=1" : request.getParameter("code")+"'" );
			
			System.out.println(sql);
			
			model.addAttribute("list",
					jdbctemplate.query(sql, new RowMapper<EQUIPMENT_INFO_TBL>() {
						@Override
						public EQUIPMENT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
							EQUIPMENT_INFO_TBL data = new EQUIPMENT_INFO_TBL();
							data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
							data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
							return data;
						}
					}));
			
			model.addAttribute("visibility",jdbctemplate.queryForObject("select CHILD_TBL_RMARK from DTL_TBL where CHILD_TBL_NO = '282'", new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString(1);
				}
			}));
			
			return "normal/tablet/workOrderStartBB";
		}
		
		// WorkOrderInsertB
		@GetMapping("/tablet/workOrderInsertBB")
		public String WorkOrderInsertBB(Model model, HttpServletRequest request) throws SQLException{
			
	System.out.println(request.getParameter("code"));
			
			String sql = "SELECT * FROM EQUIPMENT_INFO_TBL WHERE EQUIPMENT_INFO_CODE='"+( (request.getParameter("code")==null || request.getParameter("code").equals("null")) ? "' or 1=1" : request.getParameter("code")+"'" );
			
			model.addAttribute("list",
					jdbctemplate.query(sql, new RowMapper<EQUIPMENT_INFO_TBL>() {
						@Override
						public EQUIPMENT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
							EQUIPMENT_INFO_TBL data = new EQUIPMENT_INFO_TBL();
							data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
							data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
							return data;
						}
					}));

			model.addAttribute("list2",
					jdbctemplate.query("SELECT * FROM DTL_TBL WHERE NEW_TBL_CODE='29' AND CHILD_TBL_NO <> '246'", new RowMapper<DTL_TBL>() {
						@Override
						public DTL_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
							DTL_TBL data = new DTL_TBL();
							
							data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
							
							if(rs.getString("CHILD_TBL_NO").equals("242"))
								data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
							else if(rs.getString("CHILD_TBL_NO").equals("243"))
								data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
							else if(rs.getString("CHILD_TBL_NO").equals("244"))
								data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
							else if(rs.getString("CHILD_TBL_NO").equals("245"))
								data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
							return data;
						}
					}));
			
			model.addAttribute("list3",
					jdbctemplate.query("SELECT * FROM PRODUCT_INFO_TBL LIMIT 10", new RowMapper<PRODUCT_INFO_TBL>() {
						@Override
						public PRODUCT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
							PRODUCT_INFO_TBL data = new PRODUCT_INFO_TBL();

							data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
							data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
							data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
							data.setPRODUCT_UNIT_PRICE(rs.getInt("PRODUCT_UNIT_PRICE"));
							return data;
						}
					}));
			
			return "normal/productionLX/workOrderInsertBB";	
		}
		
		@GetMapping("/tablet/workOrderStartBBB")
		public String WorkOrderStartBBB(Model model, HttpServletRequest request) throws SQLException{
			
			String sql = "SELECT * FROM EQUIPMENT_INFO_TBL WHERE EQUIPMENT_INFO_CODE='"+( (request.getParameter("code")==null  || request.getParameter("code").equals("null")) ? "' or 1=1" : request.getParameter("code")+"'" );
			
			System.out.println(sql);
			
			model.addAttribute("list",
					jdbctemplate.query(sql, new RowMapper<EQUIPMENT_INFO_TBL>() {
						@Override
						public EQUIPMENT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
							EQUIPMENT_INFO_TBL data = new EQUIPMENT_INFO_TBL();
							data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
							data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
							return data;
						}
					}));
			
			model.addAttribute("visibility",jdbctemplate.queryForObject("select CHILD_TBL_RMARK from DTL_TBL where CHILD_TBL_NO = '282'", new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString(1);
				}
			}));
			
			return "normal/productionLX/workOrderStartBBB";
		}
	
	@GetMapping("/tablet/workOrderTabletP")
	public String workOrderTabletP(Model model) {
		return "normal/tablet/workOrderTabletP";
	}	
	
	@GetMapping("/tablet/workOrderTabletP2")
	public String workOrderTabletP2(Model model) {
		return "normal/tablet/workOrderTabletP2";
	}
	
	@GetMapping("/tablet/workOrderTabletSeyeon")
	public String workOrderTabletSeyeon(Model model, HttpServletRequest request) throws SQLException
	{
		return "normal/tablet/workOrderTabletSeyeon";
	}
		
}
