package com.busience.system.Dto;

public class User_Menu_Tbl {

	// ���̺� �÷�
	String User_Code,
	Group_Code,
	Program_Code,
	URL;
	
	// ���� �÷�
	String Group_Name,
	Program_Name;

	public String getUser_Code() {
		return User_Code;
	}

	public void setUser_Code(String user_Code) {
		User_Code = user_Code;
	}

	public String getGroup_Code() {
		return Group_Code;
	}

	public void setGroup_Code(String group_Code) {
		Group_Code = group_Code;
	}

	public String getProgram_Code() {
		return Program_Code;
	}

	public void setProgram_Code(String program_Code) {
		Program_Code = program_Code;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getGroup_Name() {
		return Group_Name;
	}

	public void setGroup_Name(String group_Name) {
		Group_Name = group_Name;
	}

	public String getProgram_Name() {
		return Program_Name;
	}

	public void setProgram_Name(String program_Name) {
		Program_Name = program_Name;
	}

	@Override
	public String toString() {
		return "User_Menu_Tbl [User_Code=" + User_Code + ", Group_Code=" + Group_Code + ", Program_Code=" + Program_Code
				+ ", URL=" + URL + ", Group_Name=" + Group_Name + ", Program_Name=" + Program_Name + "]";
	}
	
}
