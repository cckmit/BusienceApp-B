package com.busience.common.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "Menu_tbl")
@EqualsAndHashCode(of = "Menu_Code")
@ToString
public class Menu {

	@Id
	private String Menu_Code;
	
	private String Menu_Parent_No;
	
	private String Menu_Child_No;
	
	private String Menu_Name;
	
	private String Menu_PageName;
	
	private boolean Menu_Use_Status;
}
