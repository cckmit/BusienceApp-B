package com.busience.common.controller;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.MenuDto;
import com.busience.common.dto.TestCheckDto;
import com.busience.common.service.MenuService;
import com.busience.common.service.ProductionService;
import com.busience.common.service.TestCheckService;
import com.busience.standard.dto.DTL_TBL;

@RestController
public class CommonRestController {
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	ProductionService productionService;
	
	@Autowired
	TestCheckService testCheckService;
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	// 공통코드 찾기
	@GetMapping("/dtl_tbl_select")
	public List<DTL_TBL> dtl_tbl_select(HttpServletRequest request) throws SQLException {
		String sql = "select * from DTL_TBL where NEW_TBL_CODE='"+request.getParameter("NEW_TBL_CODE")+"' order by CHILD_TBL_NUM*1";
		//System.out.println(request.getParameter("NEW_TBL_CODE"));
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<DTL_TBL> deptList = new ArrayList<DTL_TBL>();
		
		while (rs.next()) {
			DTL_TBL data =new DTL_TBL();
			data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
			data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
			data.setCHILD_TBL_NUM(rs.getString("CHILD_TBL_NUM"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			data.setCHILD_TBL_RMARK(rs.getString("CHILD_TBL_RMARK"));
			data.setCHILD_TBL_USE_STATUS(rs.getString("CHILD_TBL_USE_STATUS"));
			deptList.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return deptList;
	}
	// 공통코드 찾기
	@GetMapping(value = "/manager_select")
	public List<DTL_TBL> manager_select(HttpServletRequest request) throws SQLException {
		String sql = "select * from DTL_TBL where NEW_TBL_CODE='"+request.getParameter("NEW_TBL_CODE")+"'" + " and CHILD_TBL_NUM NOT IN('3','4','5') and CHILD_TBL_USE_STATUS='true'";
		//System.out.println(request.getParameter("NEW_TBL_CODE"));
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<DTL_TBL> managerList = new ArrayList<DTL_TBL>();
		
		while (rs.next()) 
		{
			DTL_TBL data =new DTL_TBL();
			data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
			data.setCHILD_TBL_NUM(rs.getString("CHILD_TBL_NUM"));
			//System.out.println(data.toString());
			managerList.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return managerList;
	}
	
	@GetMapping("/tablet/productAdd")
	public void productAdd(HttpServletRequest request) {
		String equip_code = request.getParameter("equip_code");
		String count = request.getParameter("count");
		
		try
		{
			String sql = "INSERT INTO PRODUCTION_MGMT_TBL2\r\n"
					+ "(\r\n"
					+ "	PRODUCTION_WorkOrder_ONo,\r\n"
					+ "	PRODUCTION_Product_Code,\r\n"
					+ "	PRODUCTION_Equipment_Code,\r\n"
					+ "	PRODUCTION_Volume\r\n"
					+ ")\r\n"
					+ "VALUES(\r\n"
					+ "	(SELECT WorkOrder_ONo FROM WorkOrder_tbl WHERE WorkOrder_EquipCode = ? AND WorkOrder_WorkStatus = '244'),\r\n"
					+ "	(SELECT WorkOrder_ItemCode FROM WorkOrder_tbl WHERE WorkOrder_EquipCode = ? AND WorkOrder_WorkStatus = '244'),\r\n"
					+ "	(SELECT WorkOrder_EquipCode FROM WorkOrder_tbl WHERE WorkOrder_EquipCode = ? AND WorkOrder_WorkStatus = '244'),\r\n"
					+ "	?\r\n"
					+ ")";
			
			System.out.println(sql);
			
			jdbctemplate.update(sql,equip_code,equip_code,equip_code,count);
			
			sql = "UPDATE WorkOrder_tbl w1,\r\n"
					+ "(\r\n"
					+ "	select\r\n"
					+ "			SUM(PRODUCTION_Volume) vvsum\r\n"
					+ "	FROM	PRODUCTION_MGMT_TBL2\r\n"
					+ "	WHERE	PRODUCTION_WorkOrder_ONo = \r\n"
					+ "	(\r\n"
					+ "		SELECT \r\n"
					+ "					WorkOrder_ONo\r\n"
					+ "		FROM		WorkOrder_tbl\r\n"
					+ "		WHERE		WorkOrder_EquipCode = ?\r\n"
					+ "		AND 		WorkOrder_WorkStatus = '244'\r\n"
					+ "	)	\r\n"
					+ ") vsum,\r\n"
					+ "(\r\n"
					+ "	SELECT\r\n"
					+ "			WorkOrder_PQty vvWorkOrder_PQty,\r\n"
					+ "			WorkOrder_ONo	vvWorkOrder_ONo\r\n"
					+ "	FROM 	WorkOrder_tbl\r\n"
					+ "	WHERE	WorkOrder_EquipCode = ?\r\n"
					+ "	AND	WorkOrder_WorkStatus = '244'\r\n"
					+ ") vWorkOrder_PQty\r\n"
					+ "SET	WorkOrder_WorkStatus = '245',\r\n"
					+ "WorkOrder_RQty = vsum.vvsum,\r\n"
					+ "WorkOrder_CompleteTime = NOW()\r\n"
					+ "WHERE	WorkOrder_Remark <> 'AUTO'\r\n"
					+ "AND	vWorkOrder_PQty.vvWorkOrder_PQty <= vsum.vvsum\r\n"
					+ "AND	vWorkOrder_PQty.vvWorkOrder_ONo = WorkOrder_ONo";
			
			System.out.println(sql);
			
			jdbctemplate.update(sql,equip_code,equip_code);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			
			// SQLIntegrityConstraintViolationException
			// 현재 작업시작중인 데이터가 없기 때문에 뜨는 에러
		}
	}

	//하위 메뉴 List
	@GetMapping("/menuList")
	public List<MenuDto> menuList(Principal principal) {
		return menuService.menuList(principal.getName());
	}
	
	@GetMapping("/bsapp2")
	public int test_insert(String equip,int value) {
		TestCheckDto testCheckDto = new TestCheckDto();
		testCheckDto.setIequip(equip);
		testCheckDto.setIvalue(value);
		
		
		return testCheckService.TestInsert(testCheckDto);
	}

	@GetMapping("/testCheck")
	public List<TestCheckDto> testCheck() {
		return testCheckService.TestCheckList();
	}
}
