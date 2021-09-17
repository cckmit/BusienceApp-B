package com.busience.system.controller;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.system.Dto.RIGHTS_MGMT_TBL;

@RestController("typeAuthorityRestController")
@RequestMapping("typeAuthorityRest")
public class typeAuthorityRestController {

	@Autowired
	DataSource dataSource;

	@RequestMapping(value = "/view1", method = { RequestMethod.GET, RequestMethod.POST })
	public List<RIGHTS_MGMT_TBL> view1(HttpServletRequest request, Model model) throws ParseException, SQLException {
		String CHILD_TBL_NUM = request.getParameter("RIGHTS_USER_TYPE");
		//System.out.println(CHILD_TBL_NUM);

		String sql = "select  \r\n"
				+ "	t1.CHILD_TBL_NUM, RIGHTS_PROGRAM_CODE, t2.CHILD_TBL_TYPE ,RIGHTS_MGMT_USE_STATUS, RIGHTS_USER_TYPE\r\n"
				+ "from RIGHTS_MGMT_TBL\r\n" + "left outer join DTL_TBL t1 on  t1.CHILD_TBL_NO = RIGHTS_USER_TYPE \r\n"
				+ "left outer join DTL_TBL t2 on t2.CHILD_TBL_NO = RIGHTS_PROGRAM_CODE \r\n"
                + "where t1.CHILD_TBL_NUM = '" + CHILD_TBL_NUM + "' and t2.CHILD_TBL_TYPE != '' and t1.NEW_TBL_CODE = '1'\r\n" + ";";

		//System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<RIGHTS_MGMT_TBL> deptList = new ArrayList<RIGHTS_MGMT_TBL>();

		while (rs.next()) {
			RIGHTS_MGMT_TBL data = new RIGHTS_MGMT_TBL();
			data.setRIGHTS_PROGRAM_CODE(rs.getString("RIGHTS_PROGRAM_CODE"));
			data.setRIGHTS_MGMT_USE_STATUS(rs.getString("RIGHTS_MGMT_USE_STATUS"));
			data.setCHILD_TBL_NUM(rs.getString("CHILD_TBL_NUM"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			data.setRIGHTS_USER_TYPE(rs.getString("RIGHTS_USER_TYPE"));
			//System.out.println(data);
			deptList.add(data);
			//System.out.println(deptList);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return deptList;
	}

}
