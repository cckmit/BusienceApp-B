package com.busience.standard.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.busience.standard.dto.Machine_File_tbl;
import com.busience.standard.dto.Spare_Type_tbl;

@RestController("spareTypeRestController")
@RequestMapping("spareTypeRest")
public class spareTypeRestController {

	@Autowired
	DataSource dataSource;

	@RequestMapping(value = "/squareTypeManageRestSelect", method = RequestMethod.GET)
	public List<Spare_Type_tbl> squareTypeManageRestSelect(Model model, HttpServletRequest request, Machine_File_tbl machineFile)
			throws SQLException, ParseException {
		
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		

		String sql = "SELECT Component_Code, pit.PRODUCT_ITEM_NAME AS Component_Name,\r\n"
				+ "pit.PRODUCT_INFO_STND_1 AS Component_Standard,\r\n" + "Component_Producer,\r\n"
				+ "Component_Model,\r\n" + "Component_Cus_Code,\r\n" + "cut.Cus_Name AS Component_Cus_Name,\r\n"
				+ "Component_Latest_Unit_Price,\r\n" + "Component_Use_Date,\r\n" + "Component_Use_Status,\r\n"
				+ "Component_Latest_InMat_Date,\r\n" + "Component_Latest_OutMat_Date,\r\n" + "Component_Picture,\r\n"
				+ "Component_Info_Remark\r\n" + "FROM Spare_Part_tbl AS spt\r\n"
				+ "LEFT JOIN Product_Info_tbl AS pit ON spt.Component_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "LEFT JOIN Customer_tbl AS cut ON spt.Component_Cus_Code = cut.Cus_Code";

		if (obj.get("spareType_itemCode") != null && !obj.get("spareType_itemCode").equals("")) {
			sql += " WHERE Component_Code like '%" + obj.get("spareType_itemCode") + "%'";
		}
		
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		System.out.println("spare_Type_tbl_sql = " + sql);

		List<Spare_Type_tbl> list = new ArrayList<Spare_Type_tbl>();

		int i = 0;
		while (rs.next()) {
			Spare_Type_tbl data = new Spare_Type_tbl();
			i++;
			data.setId(i);
			data.setComponent_Code(rs.getString("component_Code"));
			data.setComponent_Name(rs.getString("component_Name"));
			data.setComponent_Standard(rs.getString("component_Standard"));
			data.setComponent_Producer(rs.getString("component_Producer"));
			data.setComponent_Model(rs.getString("component_Model"));
			data.setComponent_Cus_Code(rs.getString("component_Cus_Code"));
			data.setComponent_Cus_Name(rs.getString("component_Cus_Name"));
			data.setComponent_Latest_Unit_Price(rs.getInt("component_Latest_Unit_Price"));
			data.setComponent_Use_Date(rs.getString("Component_Use_Date"));
			data.setComponent_Use_Status(rs.getString("component_Use_Status"));
			data.setComponent_Latest_InMat_Date(rs.getString("component_Latest_InMat_Date"));
			data.setComponent_Latest_OutMat_Date(rs.getString("component_Latest_OutMat_Date"));
			data.setComponent_Picture(rs.getString("component_Picture"));
			data.setComponent_Info_Remark(rs.getString("component_Info_Remark"));
			list.add(data);

		}
		
		if(machineFile != null) {
			model.addAttribute("machineFile",machineFile);
		}

		rs.close();
		pstmt.close();
		conn.close();
		return list;
	}

	// 데이터 수정
	@RequestMapping(value = "/squareTypeManageRestUpdate", method = RequestMethod.GET)
	public List<Spare_Type_tbl> squareTypeManageRestUpdate(HttpServletRequest request)
			throws SQLException, ParseException {
		List<Spare_Type_tbl> list = new ArrayList<Spare_Type_tbl>();
		String data = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);

		String sql = "update Spare_Part_tbl set ";
		sql += "Component_Producer='" + obj.get("component_Producer") + "'";
		sql += ",Component_Model='" + obj.get("component_Model") + "'";
		sql += ",Component_MachineCode='" + obj.get("component_MachineCode") + "'";
		sql += ",Component_Use_Date='" + obj.get("component_Use_Date") + "'";
		sql += ",Component_Picture='" + obj.get("component_Picture") + "'";
		sql += ",Component_Info_Remark='" + obj.get("component_Info_Remark") + "'";
		sql += " where Component_Code='" + obj.get("component_Code") + "'";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();

		return list;
	}

	// 사진 파일 업로드
	@RequestMapping(value = "/spareImgUpload", method = RequestMethod.POST)
	public List<Machine_File_tbl> spareImgUpload(@RequestParam("uploadFile") MultipartFile uploadFile,
			HttpServletRequest request) throws SQLException, ParseException {

		String data = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);

		List<Machine_File_tbl> list = new ArrayList<Machine_File_tbl>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String spareSql = null;

		System.out.println("들어왔당");
		// 파일 서버에 저장하는 작업
		if (!uploadFile.getOriginalFilename().equals("")) {
			String filePath = saveFile(uploadFile, request);

			// db에 저장
			if (filePath != null) {
				sql = "insert into Machine_file_tbl ("
						+ "Mfile_Changed_Name, Mfile_Save_Path, Mfile_Origin_Name, Mfile_type, Mfile_Size) values ("
						+ "'" + uploadFile.getOriginalFilename() + "'," + "'" + filePath + "'," + "'"
						+ uploadFile.getOriginalFilename() + "'," + "'SP'" + "," + "'" + uploadFile.getSize() + "')";

				// sparePart Update

				spareSql = "update Spare_Part_tbl set ";
				spareSql += "Component_Picture='" + uploadFile.getOriginalFilename() + "'";
				spareSql += " where Component_Code='" + obj.get("component_Code") + "'";

			}

			System.out.println(sql);

			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

			pstmt = conn.prepareStatement(spareSql);
			pstmt.executeUpdate();

		}

		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 파일 저장 메소드
	public String saveFile(MultipartFile file, HttpServletRequest request) {

		// 파일 저장 경로 설정
		String root = request.getSession().getServletContext().getRealPath("resources");
		// spareUploadFiles = spUploadFiles
		String savePath = root + "//spUploadFiles";

		// 저장 폴더 선택
		File folder = new File(savePath);

		// 만약 폴더가 없을 경우 자동 생성
		if (!folder.exists()) {
			folder.mkdir();
		}

		String filePath = folder + "\\" + file.getOriginalFilename();

		try {
			// 실제로 파일 저장
			file.transferTo(new File(filePath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	// 파일 수정
	@RequestMapping(value = "/spareImgUpdate", method = RequestMethod.POST)
	public List<Machine_File_tbl> spareImgUpdate(@RequestParam("updateFile") MultipartFile updateFile,
			HttpServletRequest request ) throws SQLException, ParseException {
		List<Machine_File_tbl> list = new ArrayList<Machine_File_tbl>();

		String data = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = null;
		String spareSql = null;

		if (updateFile != null && !updateFile.isEmpty()) {
			// 등록된 사진이 있을 경우
			if (obj.get("component_Picture") != null) {
				deleteFile((String) obj.get("component_Picture"), request);
			}
			// 새로 업로드된 파일 저장
			String savePath = saveFile(updateFile, request);
			// 새로 업로드된 파일이 잘 저장이 되었따면 디비 저장
			if (savePath != null) {
				// db에 저장
				sql = "update Machine_file_tbl set ";
				sql	+= "Mfile_Changed_Name ='" + obj.get("component_Picture") + "',";
				sql += "Mfile_Save_Path ='" + savePath + "',";
				sql	+= "Mfile_Origin_Name ='" + obj.get("component_Picture") + "',";
				sql	+= "Mfile_type ='SP'";
				sql	+= "Mfile_Size ='" + updateFile.getSize() + "'";
				sql += "where Mfile_Changed_Name ='" + obj.get("component_Picture") + "'";

				// sparePart Update
				spareSql = "update Spare_Part_tbl set ";
				spareSql += "Component_Picture='" + obj.get("component_Picture") + "'";
				spareSql += " where Component_Code='" + obj.get("component_Code") + "'";

				System.out.println(sql);

				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(spareSql);
				pstmt.executeUpdate();
			}
			
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return list;
	}

	// 파일 삭제 메소드
	// 글 수정으로 업로드 파일이 변한 경우 기존 파일 삭제
	public void deleteFile(String fileName, HttpServletRequest request) {
		// 파일 저장 경로 설정
		String root = request.getSession().getServletContext().getRealPath("resources");

		String savePath = root + "//spUploadFiles";
		// 삭제할 파일 경로 + 파일명
		File deleteFile = new File(savePath + "\\" + fileName);
		// 해당 파일이 존재할 경우 삭제
		if (deleteFile.exists()) {
			deleteFile.delete();
		}
	}

}
