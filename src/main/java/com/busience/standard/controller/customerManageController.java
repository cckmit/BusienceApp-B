package com.busience.standard.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.busience.standard.Dto.DTL_TBL;

@Controller
public class customerManageController {

	@Autowired
	DataSource dataSource;
	
	@GetMapping("customerManage")
	public String customer(Model model) throws SQLException {
		String sql = "select * from DTL_TBL where NEW_TBL_CODE = '15' and CHILD_TBL_USE_STATUS='true'";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<DTL_TBL> inoutList = new ArrayList<DTL_TBL>();
		
		while (rs.next()) {
			DTL_TBL data =new DTL_TBL();
			data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
			inoutList.add(data);
		}
		
		model.addAttribute("inoutList", inoutList);
		
		rs.close();
		pstmt.close();
		conn.close();
		
		model.addAttribute("pageName", "customerManage");
		model.addAttribute("user_name", "관리자");
		
		return "standard/customerManage";
	}
	
	public int solution(int[] nums) {
        int answer = 0;
        
        int[] answers = new int[nums.length];
        
        for (int i = 0; i < nums.length; i++) {
			for (int j = 0; j < answers.length; j++) {
				if(answers[j] != nums[i])
					answers[j] = nums[i];
			}
		}
        
        answer = answers.length;
        
        return answer;
    }
	
}
