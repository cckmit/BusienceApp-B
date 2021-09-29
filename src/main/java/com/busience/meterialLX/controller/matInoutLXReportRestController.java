package com.busience.meterialLX.controller;

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

import com.busience.standard.Dto.LotTranse_tbl;

@RestController("matInoutLXReportRestController")
@RequestMapping("matInoutLXReportRest")
public class matInoutLXReportRestController {

	@Autowired
	DataSource dataSource;

	// InOutListView
	@RequestMapping(value = "/MIO_ListView", method = RequestMethod.GET)
	public List<LotTranse_tbl> MIO_ListView(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		String sql = "SELECT \r\n"
				+ "mat.Mat_Code,\r\n"
				+ "pit.PRODUCT_ITEM_NAME Mat_Name,\r\n"
				+ "case when mat.Mat_Clsfc='171' then mat.Mat_Qty ELSE 0 END AS InMat_Qty,\r\n"
				+ "case when mat.Mat_Clsfc='175' then mat.Mat_Qty ELSE 0 END AS InMat_InReturn_Qty,\r\n"
				+ "case when mat.Mat_Clsfc IN (172, 173, 174) then mat.Mat_Qty ELSE 0 END AS InMat_InOther_Qty,\r\n"
				+ "case when mat.Mat_Clsfc='181' then mat.Mat_Qty ELSE 0 END AS OutMat_Qty,\r\n"
				+ "case when mat.Mat_Clsfc='183' then mat.Mat_Qty ELSE 0 END AS OutMat_OutReturn_Qty,\r\n"
				+ "case when mat.Mat_Clsfc IN (182, 193) then mat.Mat_Qty ELSE 0 END AS OutMat_OutOther_Qty,\r\n"
				+ "mat.Mat_Clsfc,\r\n"
				+ "dt.CHILD_TBL_TYPE,\r\n"
				+ "mat.Mat_Date\r\n"
				+ "FROM(SELECT InMat_Code Mat_Code, sum(InMat_Qty) Mat_Qty, InMat_Rcv_Clsfc Mat_Clsfc, InMat_Date Mat_Date\r\n"
				+ "FROM InMatLX_tbl\r\n"
				+ "group by InMat_Code, InMat_Rcv_Clsfc\r\n"
				+ "union all\r\n"
				+ "SELECT OutMat_Code Mat_Code, sum(OutMat_Qty) Mat_Qty, OutMat_Send_Clsfc Mat_Clsfc, OutMat_Date Mat_Date\r\n"
				+ "FROM OutMatLX_tbl\r\n"
				+ "group by OutMat_Code, OutMat_Send_Clsfc\r\n"
				+ "order by Mat_Code) mat \r\n"
				+ "INNER JOIN PRODUCT_INFO_TBL pit ON mat.Mat_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "INNER JOIN DTL_TBL dt ON mat.Mat_Clsfc = dt.CHILD_TBL_NO"
				+ " where Mat_Date between '" + obj.get("startDate") + " 00:00:00' and '" + obj.get("endDate") + " 23:59:59'";
		
		if (obj.get("mat_ItemCode") != null && !obj.get("mat_ItemCode").equals("")) {
			sql += " and Mat_Code like '%" + obj.get("mat_ItemCode") + "%'";
		}

		sql +=  " order by Mat_Code, Mat_Clsfc";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		int j = 0;
		String itemNull = null;
		int LTranse_InQtySum = 0;
		int LTranse_InReturnQtySum = 0;
		int LTranse_OutQtySum = 0;
		int LTranse_OutReturnQtySum = 0;
		int LTranse_OutOtherQtySum = 0;

		List<LotTranse_tbl> list = new ArrayList<LotTranse_tbl>();

		while (rs.next()) {
			// 품목 이름이 기억한 품목과 같지 않을 경우
			if (!rs.getString("mat_Code").equals(itemNull)) {

				if (j > 0) {
					// total 행 출력
					LotTranse_tbl data = new LotTranse_tbl();

					i++;
					data.setID(i);
					data.setLTranse_ItemName("Total");
					data.setLTranse_InQty(LTranse_InQtySum);
					data.setLTranse_InReturnQty(LTranse_InReturnQtySum);
					data.setLTranse_OutQty(LTranse_OutQtySum);
					data.setLTranse_OutReturnQty(LTranse_OutReturnQtySum);
					data.setLTranse_OutOtherQty(LTranse_OutOtherQtySum);

					// 출력한 후에 0으로 초기화
					LTranse_InQtySum = 0;
					LTranse_InReturnQtySum = 0;
					LTranse_OutQtySum = 0;
					LTranse_OutReturnQtySum = 0;
					LTranse_OutOtherQtySum = 0;

					list.add(data);

				}

				LotTranse_tbl data = new LotTranse_tbl();

				// 정상적인 리스트 출력
				i++;
				j++;
				data.setID(i);
				data.setLTranse_ItemCode(rs.getString("Mat_Code"));
				data.setLTranse_ItemName(rs.getString("Mat_Name"));
				data.setLTranse_InQty(rs.getInt("InMat_Qty"));
				data.setLTranse_InReturnQty(rs.getInt("InMat_InReturn_Qty"));
				data.setLTranse_OutQty(rs.getInt("OutMat_Qty"));
				data.setLTranse_OutReturnQty(rs.getInt("OutMat_OutReturn_Qty"));
				data.setLTranse_OutOtherQty(rs.getInt("OutMat_OutOther_Qty"));
				data.setLTranse_dCreate_Time(rs.getString("Mat_Date"));
				data.setLTranse_Remark(rs.getString("CHILD_TBL_TYPE"));

				// 합계를 나타내기위한 변수
				LTranse_InQtySum += rs.getInt("InMat_Qty");
				LTranse_InReturnQtySum += rs.getInt("InMat_InReturn_Qty");
				LTranse_OutQtySum += rs.getInt("OutMat_Qty");
				LTranse_OutReturnQtySum += rs.getInt("OutMat_OutReturn_Qty");
				LTranse_OutOtherQtySum += rs.getInt("OutMat_OutOther_Qty");

				// 품목명을 기억해둠
				itemNull = rs.getString("mat_Code");

				list.add(data);

			} else {
				// 품목 이름이 없을경우
				LotTranse_tbl data = new LotTranse_tbl();

				i++;
				data.setID(i);
				data.setLTranse_InQty(rs.getInt("InMat_Qty"));
				data.setLTranse_InReturnQty(rs.getInt("InMat_InReturn_Qty"));
				data.setLTranse_OutQty(rs.getInt("OutMat_Qty"));
				data.setLTranse_OutReturnQty(rs.getInt("OutMat_OutReturn_Qty"));
				data.setLTranse_OutOtherQty(rs.getInt("OutMat_OutOther_Qty"));
				data.setLTranse_dCreate_Time(rs.getString("Mat_Date"));
				data.setLTranse_Remark(rs.getString("CHILD_TBL_TYPE"));

				LTranse_InQtySum += rs.getInt("InMat_Qty");
				LTranse_InReturnQtySum += rs.getInt("InMat_InReturn_Qty");
				LTranse_OutQtySum += rs.getInt("OutMat_Qty");
				LTranse_OutReturnQtySum += rs.getInt("OutMat_OutReturn_Qty");
				LTranse_OutOtherQtySum += rs.getInt("OutMat_OutOther_Qty");

				list.add(data);
			}

		}

		// 마지막에 합계 출력
		if (!rs.next()) {

			LotTranse_tbl data = new LotTranse_tbl();

			i++;
			data.setID(i);
			data.setLTranse_ItemName("Total");
			data.setLTranse_InQty(LTranse_InQtySum);
			data.setLTranse_InReturnQty(LTranse_InReturnQtySum);
			data.setLTranse_OutQty(LTranse_OutQtySum);
			data.setLTranse_OutReturnQty(LTranse_OutReturnQtySum);
			data.setLTranse_OutOtherQty(LTranse_OutOtherQtySum);

			LTranse_InQtySum = 0;
			LTranse_InReturnQtySum = 0;
			LTranse_OutQtySum = 0;
			LTranse_OutReturnQtySum = 0;
			LTranse_OutOtherQtySum = 0;

			list.add(data);

		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
}
