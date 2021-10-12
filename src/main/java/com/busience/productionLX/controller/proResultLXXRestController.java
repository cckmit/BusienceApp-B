package com.busience.productionLX.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.productionLX.dto.PRODUCTION_INFO_TBL;

@RestController("proResultLXXRestController")
@RequestMapping("proResultLXXRest")
public class proResultLXXRestController {

	@Autowired
	DataSource dataSource;

	// proResultSelect
	@RequestMapping(value = "/proResultSelect", method = RequestMethod.GET)
	public String proResultSelect(HttpServletRequest request)
			throws ParseException, SQLException, org.json.simple.parser.ParseException {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String PRODUCT_ITEM_CODE = request.getParameter("PRODUCT_ITEM_CODE");
		String PRODUCT_ITEM_NAME = request.getParameter("PRODUCT_ITEM_NAME");
		String EQUIPMENT_INFO_CODE = request.getParameter("EQUIPMENT_INFO_CODE");
		String EQUIPMENT_INFO_NAME = request.getParameter("EQUIPMENT_INFO_NAME");

		String sql = "SELECT \r\n"
				+ "			A.* ,B.PRODUCT_ITEM_NAME,C.EQUIPMENT_INFO_NAME PRODUCTION_EQUIPMENT_INFO_NAME\r\n"
				+ "FROM PRODUCTION_MGMT_TBL_X A\r\n"
				+ "inner join PRODUCT_INFO_TBL B on A.PRODUCTION_Product_Code = B.PRODUCT_ITEM_CODE\r\n"
				+ "inner join EQUIPMENT_INFO_TBL C on A.PRODUCTION_Equipment_Code = C.EQUIPMENT_INFO_CODE";

		String where = " WHERE PRODUCTION_MODIFY_D BETWEEN ? AND ?";

		if (PRODUCT_ITEM_CODE != null) {
			where += " and A.PRODUCTION_Product_Code like '%" + PRODUCT_ITEM_CODE + "%'"
					+ " and B.PRODUCT_ITEM_NAME like '%" + PRODUCT_ITEM_NAME + "%'";
		} else {
			where += " and (B.PRODUCT_ITEM_NAME like '%" + PRODUCT_ITEM_NAME + "%'"
					+ " OR A.PRODUCTION_Product_Code like '%" + PRODUCT_ITEM_NAME + "%')";
		}

		sql += where;

		return null;
	}

}
