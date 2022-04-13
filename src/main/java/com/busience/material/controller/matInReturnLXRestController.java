package com.busience.material.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.InMatDto;
import com.busience.material.dto.InMat_tbl;
import com.busience.material.service.MatInReturnService;

@RestController("matInReturnLXRestController")
@RequestMapping("matInReturnLXRest")
public class matInReturnLXRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	MatInReturnService matInReturnService;
	
	//MIRI_Search
	@GetMapping("/MIRI_Search")
	public List<InMatDto> MIRI_Search(SearchDto searchDto) {
	 return matInReturnService.matInReturnSelect(searchDto);
	}
	
	//MIRS_Search
	@RequestMapping(value = "/MIRS_Search", method = RequestMethod.GET)
	public List<InMat_tbl> MIRS_Search(HttpServletRequest request) throws org.json.simple.parser.ParseException, SQLException{
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		
		String sql = " SELECT\r\n"
				+ " A.InMat_No,\r\n"
				+ " A.InMat_Order_No,\r\n"
				+ " A.InMat_Code,\r\n"
				+ " B.PRODUCT_ITEM_NAME InMat_Name,\r\n"
				+ " A.InMat_Qty InReturn_Qty,\r\n"
				+ " A.InMat_Unit_Price,\r\n"
				+ " A.InMat_Client_Code,\r\n"
				+ " C.Cus_Name InMat_Client_Name,\r\n"
				+ " A.InMat_Rcv_Clsfc,\r\n"
				+ " D.CHILD_TBL_TYPE InMat_Rcv_Clsfc_Name,\r\n"
				+ " date_format(A.InMat_Date,'%Y-%m-%d %T') InMat_Date,\r\n"
				+ " date_format(A.InMat_dInsert_Time,'%Y-%m-%d %T') InMat_dInsert_Time,\r\n"
				+ " A.InMat_Modifier\r\n"
				+ " FROM InMatLX_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.InMat_Code = B.PRODUCT_ITEM_CODE \r\n"
				+ " inner join Customer_tbl C on A.InMat_Client_Code = C.Cus_Code\r\n"
				+ " inner join DTL_TBL D on A.InMat_Rcv_Clsfc = D.CHILD_TBL_NO  \r\n";
		
		String where = " where A.InMat_dInsert_Time between '"+obj.get("startDate")+" 00:00:00' and '"+obj.get("endDate")+" 23:59:59' \r\n";
				
		if (obj.get("inMat_Code") != null && !obj.get("inMat_Code").equals("")) {
			where += " and A.InMat_Code like '%" + obj.get("inMat_Code") + "%'";
		}
		
		if (obj.get("inMat_Client_Code") != null && !obj.get("inMat_Client_Code").equals("")) {
			where += " and A.InMat_Client_Code like '%" + obj.get("inMat_Client_Code") + "%'";
		}
		
		where += " and A.InMat_Rcv_Clsfc = '207'";
		
		sql += where;
		System.out.println(sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<InMat_tbl> list = new ArrayList<InMat_tbl>();
		
		int i = 0;
		
		while (rs.next()) {
			InMat_tbl data = new InMat_tbl();
			
			i++;
			data.setID(i);
			data.setInMat_Order_No(rs.getString("inMat_Order_No"));
			data.setInMat_Code(rs.getString("inMat_Code"));
			data.setInMat_Name(rs.getString("inMat_Name"));
			data.setInReturn_Qty(rs.getInt("inReturn_Qty"));
			data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
			data.setInMat_Price(rs.getInt("inReturn_Qty")*rs.getInt("inMat_Unit_Price"));
			data.setInMat_Client_Name(rs.getString("inMat_Client_Name"));
			data.setInMat_Date(rs.getString("inMat_Date"));
			data.setInMat_Rcv_Clsfc(rs.getString("inMat_Rcv_Clsfc"));
			data.setInMat_Rcv_Clsfc_Name(rs.getString("inMat_Rcv_Clsfc_Name"));
			data.setInMat_dInsert_Time(rs.getString("inMat_dInsert_Time"));
			data.setInMat_Modifier(rs.getString("inMat_Modifier"));
			list.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	//MIRI_Save
	@PostMapping("/MIRI_Save")
	public int MIRI_Save(@RequestBody List<InMatDto> inMatDtoList, Principal principal) {
		return matInReturnService.matInReturnSave(inMatDtoList, principal.getName());
	}
}
