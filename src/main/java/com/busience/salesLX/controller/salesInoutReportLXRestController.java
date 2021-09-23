package com.busience.salesLX.controller;

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
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.salesLX.dto.Sales_LotTranse_tbl;

@RestController("salesInoutReportLXRestController")
@RequestMapping("salesInoutReportLXRest")
public class salesInoutReportLXRestController {

	@Autowired
	DataSource dataSource;

	// fgoodsInoutListView
	@RequestMapping(value = "/FIO_ListView", method = RequestMethod.GET)
	public List<Sales_LotTranse_tbl> FIO_ListView(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		String sql = "select A.Sales_Code,\r\n" + "B.PRODUCT_ITEM_NAME sales_Name,\r\n"
				+ "case when A.Sales_Clsfc='171' then A.Sales_Qty else 0 end as Sales_InMat_Qty,\r\n"
				+ "case when A.Sales_Clsfc='175' then A.Sales_Qty else 0 end as Sales_InReturn_Qty,\r\n"
				+ "case when A.Sales_Clsfc in (172,173,174) then A.Sales_Qty else 0 end as Sales_InOther_Qty,\r\n"
				+ "case when A.Sales_Clsfc='191' then A.Sales_Qty else 0 end as Sales_OutMat_Qty,\r\n"
				+ "case when A.Sales_Clsfc='194' then A.Sales_Qty else 0 end as Sales_OutReturn_Qty,\r\n"
				+ "case when A.Sales_Clsfc in (192, 193) then A.Sales_Qty else 0 end as Sales_OutOther_Qty,\r\n"
				+ "A.Sales_Clsfc,\r\n" + "C.CHILD_TBL_TYPE,\r\n" + "A.Sales_Date\r\n"
				+ "from (SELECT Sales_InMat_Code Sales_Code, sum(Sales_InMat_Qty) Sales_Qty, Sales_InMat_Rcv_Clsfc Sales_Clsfc, Sales_InMat_Date Sales_Date\r\n"
				+ "FROM Sales_InMatLX_tbl\r\n" + "group by Sales_InMat_Code, Sales_InMat_Rcv_Clsfc\r\n"
				+ "union all\r\n"
				+ "SELECT Sales_OutMat_Code Sales_Code, sum(Sales_OutMat_Qty) Sales_Qty, Sales_OutMat_Send_Clsfc Sales_Clsfc, Sales_OutMat_Date Sales_Date\r\n"
				+ "FROM Sales_OutMatLX_tbl\r\n" + "group by Sales_OutMat_Code, Sales_OutMat_Send_Clsfc\r\n"
				+ "order by Sales_Code) A\r\n"
				+ "inner join PRODUCT_INFO_TBL B on A.Sales_Code = B.PRODUCT_ITEM_CODE\r\n"
				+ "inner join DTL_TBL C on A.Sales_Clsfc = C.CHILD_TBL_NO\r\n" + "where Sales_Date between '"
				+ obj.get("startDate") + " 00:00:00' and '" + obj.get("endDate") + " 23:59:59' ";

		if (obj.get("sales_ItemCode") != null && !obj.get("sales_ItemCode").equals("")) {
			sql += " and Sales_Code like'%" + obj.get("sales_ItemCode") + "%'";
		}

		sql += " order by Sales_Code, Sales_Clsfc";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		int j = 0;
		String itemNull = null;
		int Sales_LTranse_InQtySum = 0;
		int Sales_LTranse_InReturnQtySum = 0;
		int Sales_LTranse_InOtherQtySum = 0;
		int Sales_LTranse_OutQtySum = 0;
		int Sales_LTranse_OutReturnQtySum = 0;
		int Sales_LTranse_OutOtherQtySum = 0;

		List<Sales_LotTranse_tbl> list = new ArrayList<Sales_LotTranse_tbl>();

		while (rs.next()) {
			// 품목 이름이 기억한 품목과 같지 않을 경우
			if (!rs.getString("sales_Code").equals(itemNull)) {
				if (j > 0) {
					Sales_LotTranse_tbl data = new Sales_LotTranse_tbl();

					i++;
					data.setID(i);
					data.setSales_LTranse_LotNo("Total");
					data.setSales_LTranse_InQty(Sales_LTranse_InQtySum);
					data.setSales_LTranse_InReturnQty(Sales_LTranse_InReturnQtySum);
					data.setSales_LTranse_InOtherQty(Sales_LTranse_InOtherQtySum);
					data.setSales_LTranse_OutQty(Sales_LTranse_OutQtySum);
					data.setSales_LTranse_OutReturnQty(Sales_LTranse_OutReturnQtySum);
					data.setSales_LTranse_OutOtherQty(Sales_LTranse_OutOtherQtySum);

					Sales_LTranse_InQtySum = 0;
					Sales_LTranse_InReturnQtySum = 0;
					Sales_LTranse_InOtherQtySum = 0;
					Sales_LTranse_OutQtySum = 0;
					Sales_LTranse_OutReturnQtySum = 0;
					Sales_LTranse_OutOtherQtySum = 0;

					list.add(data);

				}

				Sales_LotTranse_tbl data = new Sales_LotTranse_tbl();

				// 정상적인 리스트 출력
				i++;
				j++;
				data.setID(i);
				data.setSales_LTranse_ItemCode(rs.getString("sales_Code"));
				data.setSales_LTranse_ItemName(rs.getString("sales_Name"));
				data.setSales_LTranse_InQty(rs.getInt("sales_InMat_Qty"));
				data.setSales_LTranse_InReturnQty(rs.getInt("sales_InReturn_Qty"));
				data.setSales_LTranse_InOtherQty(rs.getInt("sales_InOther_Qty"));
				data.setSales_LTranse_OutQty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_LTranse_OutReturnQty(rs.getInt("sales_OutReturn_Qty"));
				data.setSales_LTranse_OutOtherQty(rs.getInt("sales_OutOther_Qty"));
				data.setSales_LTranse_dCreate_Time(rs.getString("Sales_Date"));
				data.setSales_LTranse_Remark(rs.getString("CHILD_TBL_TYPE"));

				// 합계를 나타내기위한 변수
				Sales_LTranse_InQtySum += rs.getInt("sales_InMat_Qty");
				Sales_LTranse_InReturnQtySum += rs.getInt("sales_InReturn_Qty");
				Sales_LTranse_InOtherQtySum += rs.getInt("sales_InOther_Qty");
				Sales_LTranse_OutQtySum += rs.getInt("sales_OutMat_Qty");
				Sales_LTranse_OutReturnQtySum += rs.getInt("sales_OutReturn_Qty");
				Sales_LTranse_OutOtherQtySum += rs.getInt("sales_OutOther_Qty");

				// 품목명을 기억해둠
				itemNull = rs.getString("sales_Code");

				list.add(data);

			} else {
				// 품목 이름이 없을경우
				Sales_LotTranse_tbl data = new Sales_LotTranse_tbl();

				i++;
				data.setID(i);
				data.setSales_LTranse_InQty(rs.getInt("sales_InMat_Qty"));
				data.setSales_LTranse_InReturnQty(rs.getInt("sales_InReturn_Qty"));
				data.setSales_LTranse_InOtherQty(rs.getInt("sales_InOther_Qty"));
				data.setSales_LTranse_OutQty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_LTranse_OutReturnQty(rs.getInt("sales_OutReturn_Qty"));
				data.setSales_LTranse_OutOtherQty(rs.getInt("sales_OutOther_Qty"));
				data.setSales_LTranse_dCreate_Time(rs.getString("Sales_Date"));
				data.setSales_LTranse_Remark(rs.getString("CHILD_TBL_TYPE"));

				Sales_LTranse_InQtySum += rs.getInt("sales_InMat_Qty");
				Sales_LTranse_InReturnQtySum += rs.getInt("sales_InReturn_Qty");
				Sales_LTranse_InOtherQtySum += rs.getInt("sales_InOther_Qty");
				Sales_LTranse_OutQtySum += rs.getInt("sales_OutMat_Qty");
				Sales_LTranse_OutReturnQtySum += rs.getInt("sales_OutReturn_Qty");
				Sales_LTranse_OutOtherQtySum += rs.getInt("sales_OutOther_Qty");

				list.add(data);
			}
		}

		if (!rs.next()) {
			Sales_LotTranse_tbl data = new Sales_LotTranse_tbl();

			i++;
			data.setID(i);
			data.setSales_LTranse_LotNo("Total");
			data.setSales_LTranse_InQty(Sales_LTranse_InQtySum);
			data.setSales_LTranse_InReturnQty(Sales_LTranse_InReturnQtySum);
			data.setSales_LTranse_InOtherQty(Sales_LTranse_InOtherQtySum);
			data.setSales_LTranse_OutQty(Sales_LTranse_OutQtySum);
			data.setSales_LTranse_OutReturnQty(Sales_LTranse_OutReturnQtySum);
			data.setSales_LTranse_OutOtherQty(Sales_LTranse_OutOtherQtySum);

			Sales_LTranse_InQtySum = 0;
			Sales_LTranse_InReturnQtySum = 0;
			Sales_LTranse_InOtherQtySum = 0;
			Sales_LTranse_OutQtySum = 0;
			Sales_LTranse_OutReturnQtySum = 0;
			Sales_LTranse_OutOtherQtySum = 0;

			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
}
