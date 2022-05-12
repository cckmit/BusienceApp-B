package com.busience.tablet.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.dto.SearchDto;
import com.busience.common.service.DtlService;
import com.busience.standard.dto.DTL_TBL;
import com.busience.standard.dto.EQUIPMENT_INFO_TBL;
import com.busience.standard.dto.MachineDto;
import com.busience.standard.dto.PRODUCT_INFO_TBL;
import com.busience.standard.service.MachineService;

@Controller
public class tabletController {
	
	@Autowired
	DtlService dtlService;
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	@Autowired
	MachineService machineService;

	//workOrderTablet
	@GetMapping("/tablet/workOrderTablet")
	public String workOrderTablet(Model model, SearchDto searchDto) {
		if(searchDto.getMachineCode() == null) {
			searchDto.setMachineCode("M001");
		}
		// 설비 리스트 가져오기
		model.addAttribute("machineList", machineService.selectMachineList());
		model.addAttribute("machineCode", searchDto.getMachineCode());
		return "normal/tablet/workOrderStartBB";
	}
	
	// WorkOrderInsertB
		@GetMapping("/tablet/workOrderInsertB")
		public String WorkOrderInsertB(Model model, SearchDto searchDto) {
			if(searchDto.getMachineCode() == null) {
				searchDto.setMachineCode("M001");
			}
			
			model.addAttribute("machineList", machineService.selectMachineList());
			model.addAttribute("machineCode", searchDto.getMachineCode());
			model.addAttribute("list2", dtlService.getDtl(29));
			
			return "normal/tablet/workOrderInsertB";
		}
		
		@GetMapping("/tablet/workOrderStartBB")
		public String WorkOrderStartBB(Model model, HttpServletRequest request) throws SQLException {
			String sql = "SELECT * FROM EQUIPMENT_INFO_TBL WHERE EQUIPMENT_INFO_CODE='"+( (request.getParameter("code")==null  || request.getParameter("code").equals("null")) ? "' or EQUIPMENT_INFO_CODE = 'M001'" : request.getParameter("code")+"'" );
			//String sql = "SELECT * FROM EQUIPMENT_INFO_TBL WHERE EQUIPMENT_INFO_CODE='"+( (request.getParameter("code")==null  || request.getParameter("code").equals("null")) ? "' or 1=1" : request.getParameter("code")+"'" );
			
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
	
			String sql = "SELECT * FROM EQUIPMENT_INFO_TBL WHERE EQUIPMENT_INFO_CODE='"+( (request.getParameter("code")==null || request.getParameter("code").equals("null")) ? "' or EQUIPMENT_INFO_CODE = 'M001'" : request.getParameter("code")+"'" );
	
			//String sql = "SELECT * FROM EQUIPMENT_INFO_TBL WHERE EQUIPMENT_INFO_CODE='"+( (request.getParameter("code")==null || request.getParameter("code").equals("null")) ? "' or 1=1" : request.getParameter("code")+"'" );
			
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
			
			return "normal/production/workOrderInsertBB";	
		}
		
	@GetMapping("/tablet/workOrderTabletSeiyon")
	public String workOrderTabletSeiyon()
	{
		return "normal/tablet/workOrderTabletSeiyon";
	}
	
	@GetMapping("/tablet/workOrderTabletMaster")
	public String workOrderTabletMaster(Model model) {

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
		
		return "normal/tablet/workOrderTabletMaster";
	}
	
	@GetMapping("/tablet/maskProductionTablet")
	public String maskProductionTablet(Model model, SearchDto searchDto) {
		if(searchDto.getMachineCode() == null) {
			searchDto.setMachineCode("M001");
		}
		MachineDto machineDto = machineService.selectMachineInfo(searchDto);
		
		model.addAttribute("machineInfo", machineDto);
		
		return "normal/tablet/maskProductionTablet";
	}
	
	@GetMapping("/tablet/maskInputTablet")
	public String maskInputTablet(Model model, SearchDto searchDto) {
		if(searchDto.getMachineCode() == null) {
			searchDto.setMachineCode("M001");
		}
		MachineDto machineDto = machineService.selectMachineInfo(searchDto);
		
		model.addAttribute("machineInfo", machineDto);
		
		return "normal/tablet/maskInputTablet";
	}
}
