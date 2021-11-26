package com.busience.productionLX.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.standard.dto.DTL_TBL;
import com.busience.standard.dto.EQUIPMENT_INFO_TBL;
import com.busience.standard.dto.Equip_Monitoring_TBL;
import com.busience.standard.dto.PRODUCT_INFO_TBL;

@Controller
public class productionLXController {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	// proResultLX
	@GetMapping("proResultLX")
	public String proResultLX(Model model) {
		model.addAttribute("pageName", "생산 실적 관리");
		return "productionLX/proResult";
	}

	// proResultLXX
	@GetMapping("proResultLXX")
	public String proResultLXX(Model model) {
		model.addAttribute("pageName", "생산 실적 관리");
		return "productionLX/proResultX";
	}

	// proItemSumLX
	@GetMapping("proItemSumLX")
	public String proItemSumLX(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(제품별)");
		return "productionLX/proItemSumLX";
	}

	// proItemSumLXX
	@GetMapping("proItemSumLXX")
	public String proItemSumLXX(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(제품별)");
		return "productionLX/proItemSumLXX";
	}

	// proMachineSumLX
	@GetMapping("proMachineSumLX")
	public String proMachineSumLX(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(설비별)");
		return "productionLX/proMachineSumLX";
	}

	// proMachineSumLXX
	@GetMapping("proMachineSumLXX")
	public String proMachineSumLXX(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(설비별)");
		return "productionLX/proMachineSumLXX";
	}

	// proItemListLX
	@GetMapping("proItemListLX")
	public String proItemListLX(Model model) {
		model.addAttribute("pageName", "생산 이력 관리(제품별)");
		return "productionLX/proItemListLX";
	}

	// proItemListLXX
	@GetMapping("proItemListLXX")
	public String proItemListLXX(Model model) {
		model.addAttribute("pageName", "생산 이력 관리(제품별)");
		return "productionLX/proItemListLXX";
	}

	// proMachineListLX
	@GetMapping("proMachineListLX")
	public String proMachineListLX(Model model) {
		model.addAttribute("pageName", "생산 이력 관리(설비별)");
		return "productionLX/proMachineListLX";
	}

	// proMachineListLXX
	@GetMapping("proMachineListLXX")
	public String proMachineListLXX(Model model) {
		model.addAttribute("pageName", "생산 이력 관리(설비별)");
		return "productionLX/proMachineListLXX";
	}

	// proSumMonth
	@GetMapping("proSumMonth")
	public String proSumMonth(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(월별)");
		return "productionLX/proSumMonth";
	}

	// proSumMonth2
	@GetMapping("proSumMonthX")
	public String proSumMonth2(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(월별)");
		return "productionLX/proSumMonthX";
	}

	// proSumYear
	@GetMapping("proSumYear")
	public String proSumYear(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(연별)");
		return "productionLX/proSumYear";
	}

	// proSumYear
	@GetMapping("proSumYearX")
	public String proSumYearX(Model model) {
		model.addAttribute("pageName", "생산 실적 관리(연별)");
		return "productionLX/proSumYearX";
	}
	
	// workorder
	@GetMapping("workorder")
	public String workorder(Model model) {
		model.addAttribute("pageName", "작업지시 조회");
		return "productionLX/workorder";
	}
	
	// workorderList
	@GetMapping("workorderList")
	public String workorderList(Model model) {
		model.addAttribute("pageName", "작업지시 조회");
		return "productionLX/workorderList";
	}

	// workList
	@GetMapping("workList")
	public String workList(Model model) {
		model.addAttribute("pageName", "작업 현황");
		
		return "productionLX/workList";
	}

	// workdList
	@GetMapping("workdList")
	public String workdList(Model model) {
		model.addAttribute("pageName", "세부 작업 현황");
		return "productionLX/workdList";
	}

	// worktdList
	@GetMapping("worktdList")
	public String workList2(Model model) {
		model.addAttribute("pageName", "기간별 세부 작업 현황");
		return "productionLX/worktdList/worktdListMaster";
	}

	// WorkOrderTAB
	@GetMapping("WorkOrderTAB")
	public String WorkOrderTAB(Model model, HttpServletRequest request) throws SQLException {
		model.addAttribute("list",
				jdbctemplate.query("SELECT * FROM EQUIPMENT_INFO_TBL", new RowMapper<EQUIPMENT_INFO_TBL>() {
					@Override
					public EQUIPMENT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
						EQUIPMENT_INFO_TBL data = new EQUIPMENT_INFO_TBL();
						data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
						data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
						return data;
					}
				}));

		model.addAttribute("list2",
				jdbctemplate.query("SELECT * FROM DTL_TBL WHERE NEW_TBL_CODE='29'", new RowMapper<DTL_TBL>() {
					@Override
					public DTL_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
						DTL_TBL data = new DTL_TBL();
						data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
						data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
						return data;
					}
				}));

		return "normal/productionLX/work_order_TAB";
	}

	// WorkOrderTABX
	@GetMapping("WorkOrderTABX")
	public String WorkOrderTABX(Model model, HttpServletRequest request) throws SQLException {
		model.addAttribute("list",
				jdbctemplate.query("SELECT * FROM EQUIPMENT_INFO_TBL", new RowMapper<EQUIPMENT_INFO_TBL>() {
					@Override
					public EQUIPMENT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
						EQUIPMENT_INFO_TBL data = new EQUIPMENT_INFO_TBL();
						data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
						data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
						return data;
					}
				}));

		return "normal/productionLX/work_order_TABX";
	}

	// WorkOrderTABXO
	@GetMapping("WorkOrderTABXO")
	public String WorkOrderTABXO(Model model, HttpServletRequest request) throws SQLException {
		model.addAttribute("list",
				jdbctemplate.query("SELECT * FROM EQUIPMENT_INFO_TBL", new RowMapper<EQUIPMENT_INFO_TBL>() {
					@Override
					public EQUIPMENT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
						EQUIPMENT_INFO_TBL data = new EQUIPMENT_INFO_TBL();
						data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
						data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
						return data;
					}
				}));

		model.addAttribute("list2",
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

		return "normal/productionLX/work_order_TABXO";
	}
	
	//workOrderTablet
	@GetMapping("/tablet/workOrderTablet")
	public String workOrderTablet(Model model, HttpServletRequest request) throws SQLException
	{
		return jdbctemplate.queryForObject("select CHILD_TBL_RMARK from DTL_TBL where CHILD_TBL_NO = '281'", new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				
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
						// TODO Auto-generated method stub
						return rs.getString(1);
					}
				}));
				
				return "normal/productionLX/"+rs.getString(1);
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

		return "normal/productionLX/workOrderInsertB";
	}

	@GetMapping("/tablet/workOrderStartB")
	public String WorkOrderStartB(Model model, HttpServletRequest request) throws SQLException {
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
		
		return "normal/productionLX/workOrderStartBB";
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
				// TODO Auto-generated method stub
				return rs.getString(1);
			}
		}));
		
		return "normal/productionLX/workOrderStartBB";
	}
	
	@GetMapping("/tempDaily")
	public String tempDaily(Model model, HttpServletRequest request) throws SQLException{
		model.addAttribute("pageName", "온도 일일별 조회");
		return "/productionLX/tempDaily";
	}
	
	@GetMapping("/tempMonthly")
	public String tempMonthly(Model model, HttpServletRequest request) throws SQLException{
		model.addAttribute("pageName", "온도 월별 조회");
		return "/productionLX/tempMonthly";
	}
	
	@GetMapping("/tablet/tempStatusControl")
	public String tempStatusControl(Model model) {
		String sql = "SELECT \r\n"
				+ "			t1.Equip_Code,	\r\n"
				+ "			t1.Equip_Time,	\r\n"
				+ "			t1.Humi,\r\n"
				+ "			t1.Speed,	\r\n"
				+ "			t1.Temp,\r\n"
				+ "			t1.Equip_Status,	\r\n"
				+ "			t1.Equip_No,\r\n"
				+ "			t2.EQUIPMENT_INFO_NAME\r\n"
				+ "FROM Equip_Monitoring_TBL t1\r\n"
				+ "INNER JOIN EQUIPMENT_INFO_TBL t2\r\n"
				+ "ON t1.Equip_Code = t2.EQUIPMENT_INFO_CODE";
		
		model.addAttribute("list1", jdbctemplate.query(sql, new RowMapper() {

			@Override
			public Equip_Monitoring_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				Equip_Monitoring_TBL data = new Equip_Monitoring_TBL();
				data.setEquip_Code(rs.getString("Equip_Code"));
				data.setEquip_Time(rs.getString("Equip_Time"));
				data.setHumi(rs.getString("Humi"));
				data.setSpeed(rs.getString("Speed"));
				data.setTemp(rs.getString("Temp"));
				data.setEquip_Status(rs.getString("Equip_Status"));
				data.setEquip_No(rs.getString("Equip_No"));
				data.setEquip_Name(rs.getString("EQUIPMENT_INFO_NAME"));
				return data;
			}
		}));
		
		return "normal/productionLX/tempStatusControl";
	}
	
}
