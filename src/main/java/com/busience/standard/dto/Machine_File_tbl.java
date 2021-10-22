package com.busience.standard.dto;

public class Machine_File_tbl {

	private String Mfile_Changed_Name; // 저장파일명
	private String Mfile_Save_Path; // 저장 파일 경로
	private String Mfile_Origin_Name; // 최초파일명
	private String Mfile_type; // 저장타입
	private Long Mfile_Size; // 파일 크기

	public String getMfile_Changed_Name() {
		return Mfile_Changed_Name;
	}

	public void setMfile_Changed_Name(String mfile_Changed_Name) {
		Mfile_Changed_Name = mfile_Changed_Name;
	}

	public String getMfile_Save_Path() {
		return Mfile_Save_Path;
	}

	public void setMfile_Save_Path(String mfile_Save_Path) {
		Mfile_Save_Path = mfile_Save_Path;
	}

	public String getMfile_Origin_Name() {
		return Mfile_Origin_Name;
	}

	public void setMfile_Origin_Name(String mfile_Origin_Name) {
		Mfile_Origin_Name = mfile_Origin_Name;
	}

	public String getMfile_type() {
		return Mfile_type;
	}

	public void setMfile_type(String mfile_type) {
		Mfile_type = mfile_type;
	}

	public Long getMfile_Size() {
		return Mfile_Size;
	}

	public void setMfile_Size(Long mfile_Size) {
		Mfile_Size = mfile_Size;
	}

}
