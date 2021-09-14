package com.busience.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {
	
	@GetMapping("/")
	public String root() {
		return "main";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "thymeleaf/login";
	}
	
	@RequestMapping("/hello")
	public String hello() {
		return "thymeleaf/hello";
	}
	
	@GetMapping("/main")
	public String main(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		/*
		HttpSession session = request.getSession();

		session.setAttribute("menuName", "Home");

		String Name = (String) session.getAttribute("name");
		if (Name == null)
			return "redirect:/";

		String sql = "select \r\n" + "		ifnull(t1.MENU_MGMT_USE_STATUS,'true') CHILD_TBL_USE_STATUS,\r\n"
				+ "        t2.CHILD_TBL_TYPE MENU_PROGRAM_NAME,\r\n" + "		t2.CHILD_TBL_RMARK,\r\n"
				+ "		t2.CHILD_TBL_TYPE,\r\n" + "		t2.CHILD_TBL_NUM\r\n" + "from	\r\n" + "		(\r\n"
				+ "			select\r\n" + "					*\r\n" + "			from\r\n"
				+ "					MENU_MGMT_TBL\r\n" + "			where MENU_USER_CODE = '"
				+ session.getAttribute("id") + "'\r\n" + "        ) t1\r\n" + "right outer join \r\n" + "		(\r\n"
				+ "			select \r\n" + "					* \r\n"
				+ "			from DTL_TBL where NEW_TBL_CODE='13'  and CHILD_TBL_USE_STATUS <> 'false'\r\n"
				+ "        ) t2\r\n" + "on t1.MENU_PROGRAM_CODE = t2.CHILD_TBL_NO" + " order by t2.CHILD_TBL_NUM+0";

		//System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<DTL_TBL> list = new ArrayList<DTL_TBL>();

		while (rs.next()) {
			DTL_TBL inputdata = new DTL_TBL();

			inputdata.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			inputdata.setCHILD_TBL_NUM(rs.getString("CHILD_TBL_NUM"));

			String[] array = rs.getString("CHILD_TBL_RMARK").split("/");
			inputdata.setURL(array[0]);
			inputdata.setPARENT(array[1]);
			inputdata.setCHILD_TBL_USE_STATUS(rs.getString("CHILD_TBL_USE_STATUS"));

			// System.out.println(inputdata.toString());

			list.add(inputdata);
		}

		// session.setAttribute("catagorylist", list);

		sql = "select \r\n" + "		t1.*,t2.CHILD_TBL_TYPE Group_Name,t3.CHILD_TBL_TYPE Program_Name\r\n" + "from\r\n"
				+ "(\r\n" + "	select * from User_Menu_Tbl where User_Code = '" + session.getAttribute("id") + "'\r\n"
				+ ") t1\r\n"
				+ "inner join (select * from DTL_TBL where NEW_TBL_CODE='16') t2 on t1.Group_Code = t2.CHILD_TBL_NO\r\n"
				+ "inner join (select * from DTL_TBL where NEW_TBL_CODE='13') t3 on t1.Program_Code = t3.CHILD_TBL_NO";

		// System.out.println(sql);

		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		List<User_Menu_Tbl> list2 = new ArrayList<User_Menu_Tbl>();

		// System.out.println("-----");
		while (rs.next()) {
			User_Menu_Tbl data = new User_Menu_Tbl();

			data.setURL(rs.getString("URL"));
			data.setGroup_Code(rs.getString("Group_Code"));
			data.setProgram_Code(rs.getString("Program_Code"));
			data.setGroup_Name(rs.getString("Group_Name"));
			data.setProgram_Name(rs.getString("Program_Name"));

			// System.out.println(data.toString());

			list2.add(data);
		}
		// System.out.println("-----");

		// System.out.println(list2.size());

		session.setAttribute("catagorylist", list);
		session.setAttribute("favoriteslist", list2);

		rs.close();
		pstmt.close();
		conn.close();
*/
		return "main";
	}
}
