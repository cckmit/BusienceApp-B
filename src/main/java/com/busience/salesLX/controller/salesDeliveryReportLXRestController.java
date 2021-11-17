package com.busience.salesLX.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.common.service.HometaxApiService;
import com.busience.salesLX.dto.Sales_OutMat_tbl;

@RestController("salesDeliveryReportLXRestController")
@RequestMapping("salesDeliveryReportLXRest")
public class salesDeliveryReportLXRestController {

	@Autowired
	HometaxApiService hometaxApiService;
	
	@Autowired
	DataSource dataSource;

	// SOCL_Search
	@GetMapping("/SOCL_Search")
	public List<Sales_OutMat_tbl> SOCL_Search(SearchDto searchDto) throws SQLException {

		String sql = "select \r\n"
				+ "somt.Sales_OutMat_Date,\r\n"
				+ "dt.CHILD_TBL_TYPE Sales_OutMat_Send_Clsfc,\r\n"
				+ "somt.Sales_OutMat_Client_Code,\r\n"
				+ "ct.Cus_Name Sales_OutMat_Client_Name,\r\n"
				+ "somt.Sales_OutMat_Code,\r\n"
				+ "pit.PRODUCT_ITEM_NAME Sales_OutMat_Name,\r\n"
				+ "pit.PRODUCT_INFO_STND_1 Sales_OutMat_STND_1,\r\n"
				+ "dt2.CHILD_TBL_TYPE Sales_OutMat_UNIT,\r\n"
				+ "sum(somt.Sales_OutMat_Qty) Sales_OutMat_Qty,\r\n"
				+ "sum(somt.Sales_OutMat_Price) Sales_OutMat_Price \r\n "
				+ "from Sales_OutMatLX_tbl somt \r\n"
				+ "inner join DTL_TBL dt on somt.Sales_OutMat_Send_Clsfc = dt.CHILD_TBL_NO\r\n"
				+ "inner join Customer_tbl ct on somt.Sales_OutMat_Client_Code  = ct.Cus_Code\r\n"
				+ "inner join PRODUCT_INFO_TBL pit on somt.Sales_OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "inner join DTL_TBL dt2 on pit.PRODUCT_UNIT = dt2.CHILD_TBL_NO"

				+ " where somt.Sales_OutMat_Date between '" + searchDto.getStartDate() + " 00:00:00' and '"
				+ searchDto.getEndDate() + " 23:59:59'";

		if (searchDto.getClientCode() != null) {
			sql += " and Sales_OutMat_Client_Code like '%" + searchDto.getClientCode() + "%'";
		}

		if (!searchDto.getItemSendClsfc().equals("all")) {
			sql += " and Sales_OutMat_Send_Clsfc = '" + searchDto.getItemSendClsfc() + "'";
		}

		sql += " group by somt.Sales_OutMat_Client_Code, somt.Sales_OutMat_Date with rollup";

		System.out.println("SOCL_Search = " + sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<Sales_OutMat_tbl> list = new ArrayList<Sales_OutMat_tbl>();

		while (rs.next()) {
			Sales_OutMat_tbl data = new Sales_OutMat_tbl();

			if (rs.getString("sales_OutMat_Date") == null && rs.getString("sales_OutMat_Client_Code") != null) {

				data.setSales_OutMat_Send_Clsfc("Sub Total");
				data.setSales_OutMat_Date(rs.getString("sales_OutMat_Date"));
				data.setSales_OutMat_Client_Code(rs.getString("sales_OutMat_Client_Code"));
				data.setSales_OutMat_Client_Name(rs.getString("Sales_OutMat_Client_Name"));
				data.setSales_OutMat_Code(rs.getString("sales_OutMat_Code"));
				data.setSales_OutMat_Name(rs.getString("sales_OutMat_Name"));
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_OutMat_Price(rs.getInt("sales_OutMat_Price"));

				list.add(data);

			} else if(rs.getString("sales_OutMat_Date") == null && rs.getString("sales_OutMat_Client_Code") == null){
				
				data.setSales_OutMat_Send_Clsfc("Grand Total");
				data.setSales_OutMat_Date(rs.getString("sales_OutMat_Date"));
				data.setSales_OutMat_Code(rs.getString("sales_OutMat_Code"));
				data.setSales_OutMat_Name(rs.getString("sales_OutMat_Name"));
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_OutMat_Price(rs.getInt("sales_OutMat_Price"));

				list.add(data);
				
			}else {

				i++;
				data.setID(i);
				data.setSales_OutMat_Date(rs.getString("sales_OutMat_Date"));
				if (searchDto.getItemSendClsfc().equals("all")) {
					data.setSales_OutMat_Send_Clsfc("all");
				} else {
					data.setSales_OutMat_Send_Clsfc(rs.getString("sales_OutMat_Send_Clsfc"));
				}
				data.setSales_OutMat_Client_Code(rs.getString("sales_OutMat_Client_Code"));
				data.setSales_OutMat_Client_Name(rs.getString("sales_OutMat_Client_Name"));
				data.setSales_OutMat_Code(rs.getString("sales_OutMat_Code"));
				data.setSales_OutMat_Name(rs.getString("sales_OutMat_Name"));
				data.setSales_OutMat_STND_1(rs.getString("sales_OutMat_STND_1"));
				data.setSales_OutMat_UNIT(rs.getString("sales_OutMat_UNIT"));
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_OutMat_Price(rs.getInt("sales_OutMat_Price"));

				list.add(data);
			}
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// SDL_Search
	@GetMapping("/SDL_Search")
	public List<Sales_OutMat_tbl> SDL_Search(SearchDto searchDto) throws SQLException {
		System.out.println(searchDto);
		
		Connection conn = dataSource.getConnection();

		String sql = "select \r\n"
				+ "somt.Sales_OutMat_No,\r\n"
				+ "somt.Sales_OutMat_Client_Code,\r\n"
				+ "ct.Cus_Name Sales_OutMat_Client_Name,\r\n"
				+ "sum(somt.Sales_OutMat_Qty) Sales_OutMat_Qty\r\n"
				+ "from Sales_OutMatLX_tbl somt \r\n"
				+ "inner join Customer_tbl ct on somt.Sales_OutMat_Client_Code = ct.Cus_Code\r\n"
				+ " where somt.Sales_OutMat_Date >= '" + searchDto.getStartDate() + "'\r\n"
				+ " and somt.Sales_OutMat_Date < '" + searchDto.getEndDate() + "'\r\n";

		sql += " group by Sales_OutMat_Client_Code with rollup";

		System.out.println("SOC_DeliveryView = " + sql);

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<Sales_OutMat_tbl> list = new ArrayList<Sales_OutMat_tbl>();

		while (rs.next()) {
			if (rs.getString("sales_OutMat_Client_Code") == null) {
				Sales_OutMat_tbl data = new Sales_OutMat_tbl();

				data.setSales_OutMat_Client_Code("Sub Total");
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));

				list.add(data);
			} else {
				Sales_OutMat_tbl data = new Sales_OutMat_tbl();

				i++;
				data.setID(i);
				// data.setSales_OutMat_No(rs.getInt("sales_OutMat_No"));
				data.setSales_OutMat_Client_Code(rs.getString("sales_OutMat_Client_Code"));
				data.setSales_OutMat_Client_Name(rs.getString("sales_OutMat_Client_Name"));
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));

				list.add(data);
			}
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// SDC_Search
	@GetMapping("/SDC_Search")
	public List<Sales_OutMat_tbl> SDC_Search(SearchDto searchDto) throws SQLException {
		
		String sql = "select \r\n"
				+ "somt.Sales_OutMat_No,\r\n"
				+ "somt.Sales_OutMat_Cus_No, "
				+ "somt.Sales_OutMat_Date,\r\n"
				+ "dt.CHILD_TBL_TYPE Sales_OutMat_Send_Clsfc_Name,\r\n"
				+ "somt.Sales_OutMat_Code,\r\n"
				+ "somt.Sales_OutMat_Client_Code,\r\n"
				+ "pit.PRODUCT_ITEM_NAME Sales_OutMat_Name,\r\n"
				+ "pit.PRODUCT_INFO_STND_1 Sales_OutMat_STND_1,\r\n"
				+ "dt2.CHILD_TBL_TYPE Sales_OutMat_UNIT,\r\n"
				+ "sum(somt.Sales_OutMat_Qty) Sales_OutMat_Qty,\r\n"
				+ "sum(somt.Sales_OutMat_Price) Sales_OutMat_Price \r\n "
				+ "from Sales_OutMatLX_tbl somt \r\n"
				+ "inner join DTL_TBL dt on somt.Sales_OutMat_Send_Clsfc = dt.CHILD_TBL_NO\r\n"
				+ "inner join PRODUCT_INFO_TBL pit on somt.Sales_OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "inner join DTL_TBL dt2 on pit.PRODUCT_UNIT = dt2.CHILD_TBL_NO\r\n"
				+ "where somt.Sales_OutMat_Date >= '" + searchDto.getStartDate() + "'\r\n"
				+ "and somt.Sales_OutMat_Date < '" + searchDto.getEndDate() + "'\r\n"
				+ "and somt.Sales_OutMat_Client_Code ='" + searchDto.getClientCode() + "'\r\n"
				+ "group by somt.Sales_OutMat_Code with rollup";

		System.out.println("SOC_DeliveryLastCustomer =" + sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<Sales_OutMat_tbl> list = new ArrayList<Sales_OutMat_tbl>();
		
		while (rs.next()) {
			Sales_OutMat_tbl data = new Sales_OutMat_tbl();
			if (rs.getString("sales_OutMat_Code") == null) {

				data.setSales_OutMat_Send_Clsfc_Name("Sub Total");
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_OutMat_Price(rs.getInt("sales_OutMat_Price"));

				list.add(data);
			} else {

				data.setSales_OutMat_Cus_No(rs.getString("sales_OutMat_Cus_No"));
				data.setSales_OutMat_Date(rs.getString("sales_OutMat_Date"));
				data.setSales_OutMat_Send_Clsfc_Name(rs.getString("sales_OutMat_Send_Clsfc_Name"));
				data.setSales_OutMat_Code(rs.getString("sales_OutMat_Code"));
				data.setSales_OutMat_Client_Code(rs.getString("sales_OutMat_Client_Code"));
				data.setSales_OutMat_Name(rs.getString("sales_OutMat_Name"));
				data.setSales_OutMat_STND_1(rs.getString("sales_OutMat_STND_1"));
				data.setSales_OutMat_UNIT(rs.getString("sales_OutMat_UNIT"));
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_OutMat_Price(rs.getInt("sales_OutMat_Price"));
				data.setSales_OutMat_No(rs.getInt("sales_OutMat_No"));

				list.add(data);
			}
		}

		if (list.size() > 0) {
			list.get(list.size() - 1).setSales_OutMat_Code("");
			list.get(list.size() - 1).setSales_OutMat_Name("");
			list.get(list.size() - 1).setSales_OutMat_STND_1("");
			list.get(list.size() - 1).setSales_OutMat_UNIT("");
		}

		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	// hometaxApiDataSave
	@PostMapping("/hometaxApiDataSave")
	public int hometaxApiDataSave(SearchDto searchDto) {
		return hometaxApiService.hometaxApiDataSave(searchDto);
	}
}
