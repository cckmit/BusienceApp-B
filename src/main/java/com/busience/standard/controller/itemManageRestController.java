package com.busience.standard.controller;

import java.net.UnknownHostException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.tomcat.util.json.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.service.ItemService;
import com.busience.common.service.MenuService;
import com.busience.standard.Dto.PRODUCT_INFO_TBL;

@RestController("itemManageRestController")
@RequestMapping("itemManageRest")
public class itemManageRestController {

	@Autowired
	DataSource dataSource;
	
	private ItemService itemService;
	
	public itemManageRestController(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping("/itemManageSelect")
	public List<PRODUCT_INFO_TBL> itemManageSelect(HttpServletRequest request) throws SQLException {
		
		List<PRODUCT_INFO_TBL> list = new ArrayList<PRODUCT_INFO_TBL>();

		String sql = "select \r\n"
				+ "A.PRODUCT_BUSINESS_PLACE,\r\n"
				+ "B.CHILD_TBL_TYPE PRODUCT_BUSINESS_PLACE_NAME,\r\n"
				+ "A.PRODUCT_ITEM_CODE,\r\n"
				+ "A.PRODUCT_OLD_ITEM_CODE,\r\n"
				+ "A.PRODUCT_ITEM_NAME,\r\n"
				+ "A.PRODUCT_INFO_STND_1,\r\n"
				+ "A.PRODUCT_INFO_STND_2,\r\n"
				+ "A.PRODUCT_UNIT,\r\n"
				+ "C.CHILD_TBL_TYPE PRODUCT_UNIT_NAME,\r\n"
				+ "A.PRODUCT_MATERIAL,\r\n"
				+ "D.CHILD_TBL_TYPE PRODUCT_MATERIAL_NAME,\r\n"
				+ "A.PRODUCT_MTRL_CLSFC,\r\n"
				+ "E.CHILD_TBL_TYPE PRODUCT_MTRL_CLSFC_NAME,\r\n"
				+ "A.PRODUCT_ITEM_CLSFC_1,\r\n"
				+ "F.CHILD_TBL_TYPE PRODUCT_ITEM_CLSFC_1_NAME,\r\n"
				+ "A.PRODUCT_ITEM_CLSFC_2,\r\n"
				+ "G.CHILD_TBL_TYPE PRODUCT_ITEM_CLSFC_2_NAME,\r\n"
				+ "A.PRODUCT_SUBSID_MATL_MGMT,\r\n"
				+ "A.PRODUCT_ITEM_STTS,\r\n"
				+ "H.CHILD_TBL_TYPE PRODUCT_ITEM_STTS_NAME,\r\n"
				+ "A.PRODUCT_BASIC_WAREHOUSE,\r\n"
				+ "I.CHILD_TBL_TYPE PRODUCT_BASIC_WAREHOUSE_NAME,\r\n"
				+ "A.PRODUCT_SAVE_AREA,\r\n"
				+ "J.CHILD_TBL_TYPE PRODUCT_SAVE_AREA_NAME,\r\n"
				+ "A.PRODUCT_SFTY_STOCK,\r\n"
				+ "A.PRODUCT_BUYER,\r\n"
				+ "A.PRODUCT_WRHSN_INSPC,\r\n"
				+ "A.PRODUCT_USE_STATUS,\r\n"
				+ "A.PRODUCT_MODIFY_D,\r\n"
				+ "K.USER_NAME PRODUCT_MODIFIER\r\n"
				+ "from PRODUCT_INFO_TBL A\r\n"
				+ "inner join DTL_TBL B on A.PRODUCT_BUSINESS_PLACE = B.CHILD_TBL_NO\r\n"
				+ "inner join DTL_TBL C on A.PRODUCT_UNIT = C.CHILD_TBL_NO\r\n"
				+ "inner join DTL_TBL D on A.PRODUCT_MATERIAL = D.CHILD_TBL_NO\r\n"
				+ "inner join DTL_TBL E on A.PRODUCT_MTRL_CLSFC = E.CHILD_TBL_NO\r\n"
				+ "inner join DTL_TBL F on A.PRODUCT_ITEM_CLSFC_1 = F.CHILD_TBL_NO\r\n"
				+ "inner join DTL_TBL G on A.PRODUCT_ITEM_CLSFC_2 = G.CHILD_TBL_NO\r\n"
				+ "inner join DTL_TBL H on A.PRODUCT_ITEM_STTS = H.CHILD_TBL_NO\r\n"
				+ "inner join DTL_TBL I on A.PRODUCT_BASIC_WAREHOUSE = I.CHILD_TBL_NO\r\n"
				+ "left outer join DTL_TBL J on A.PRODUCT_SAVE_AREA = J.CHILD_TBL_NO\r\n"
				+ "inner join USER_INFO_TBL K on A.PRODUCT_MODIFIER = K.USER_CODE;";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			PRODUCT_INFO_TBL data = new PRODUCT_INFO_TBL();

			data.setPRODUCT_BUSINESS_PLACE(rs.getString("PRODUCT_BUSINESS_PLACE"));
			data.setPRODUCT_BUSINESS_PLACE_NAME(rs.getString("PRODUCT_BUSINESS_PLACE_NAME"));
			data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
			data.setPRODUCT_OLD_ITEM_CODE(rs.getString("PRODUCT_OLD_ITEM_CODE"));
			data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
			data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			data.setPRODUCT_INFO_STND_2(rs.getString("PRODUCT_INFO_STND_2"));
			data.setPRODUCT_UNIT(rs.getString("PRODUCT_UNIT"));
			data.setPRODUCT_UNIT_NAME(rs.getString("PRODUCT_UNIT_NAME"));
			data.setPRODUCT_MATERIAL(rs.getString("PRODUCT_MATERIAL"));
			data.setPRODUCT_MATERIAL_NAME(rs.getString("PRODUCT_MATERIAL_NAME"));
			data.setPRODUCT_MTRL_CLSFC(rs.getString("PRODUCT_MTRL_CLSFC"));
			data.setPRODUCT_MTRL_CLSFC_NAME(rs.getString("PRODUCT_MTRL_CLSFC_NAME"));
			data.setPRODUCT_ITEM_CLSFC_1(rs.getString("PRODUCT_ITEM_CLSFC_1"));
			data.setPRODUCT_ITEM_CLSFC_1_NAME(rs.getString("PRODUCT_ITEM_CLSFC_1_NAME"));
			data.setPRODUCT_ITEM_CLSFC_2(rs.getString("PRODUCT_ITEM_CLSFC_2"));
			data.setPRODUCT_ITEM_CLSFC_2_NAME(rs.getString("PRODUCT_ITEM_CLSFC_2_NAME"));
			data.setPRODUCT_SUBSID_MATL_MGMT(rs.getString("PRODUCT_SUBSID_MATL_MGMT"));
			data.setPRODUCT_ITEM_STTS(rs.getString("PRODUCT_ITEM_STTS"));
			data.setPRODUCT_ITEM_STTS_NAME(rs.getString("PRODUCT_ITEM_STTS_NAME"));
			data.setPRODUCT_BASIC_WAREHOUSE(rs.getString("PRODUCT_BASIC_WAREHOUSE"));
			data.setPRODUCT_BASIC_WAREHOUSE_NAME(rs.getString("PRODUCT_BASIC_WAREHOUSE_NAME"));
			data.setPRODUCT_SAVE_AREA(rs.getString("PRODUCT_SAVE_AREA"));
			data.setPRODUCT_SAVE_AREA_NAME(rs.getString("PRODUCT_SAVE_AREA_NAME"));
			data.setPRODUCT_SFTY_STOCK(rs.getInt("PRODUCT_SFTY_STOCK"));
			data.setPRODUCT_BUYER(rs.getString("PRODUCT_BUYER"));
			data.setPRODUCT_WRHSN_INSPC(rs.getString("PRODUCT_WRHSN_INSPC"));
			data.setPRODUCT_USE_STATUS(rs.getString("PRODUCT_USE_STATUS"));
			data.setPRODUCT_MODIFY_D(rs.getString("PRODUCT_MODIFY_D"));
			data.setPRODUCT_MODIFIER(rs.getString("PRODUCT_MODIFIER"));
			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return list;
	}

	@GetMapping("/itemManageInsert")
	public String itemManageInsert(PRODUCT_INFO_TBL product_INFO_TBL, Principal principal) {
		String modifier = principal.getName();

		product_INFO_TBL.setPRODUCT_MODIFIER(modifier);

		//update
		itemService.insertItemCode(product_INFO_TBL);
		
		return "Success";
	}

	// ����
	@GetMapping("/itemManageUpdate")
	public String itemManageUpdate(PRODUCT_INFO_TBL product_INFO_TBL, Principal principal) {
		
		String modifier = principal.getName();

		product_INFO_TBL.setPRODUCT_MODIFIER(modifier);

		//update
		itemService.updateItemCode(product_INFO_TBL);
		
		return "Success";
	}

	// ����
	@GetMapping("/itemManageDelete")
	public String itemManageDelete(PRODUCT_INFO_TBL product_INFO_TBL) {
		
		//update
		itemService.deleteItemCode(product_INFO_TBL.getPRODUCT_ITEM_CODE());
		
		return "Success";
	}
}
