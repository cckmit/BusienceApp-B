package com.busience.common.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "USER_INFO_TBL")
@EqualsAndHashCode(of = "USER_CODE")
@ToString
public class Member {
	
	@Id
	private String USER_CODE;
	
	private String USER_PASSWORD;
	
	private String USER_NAME;
	
	private String USER_TYPE;
	
	private String COMPANY;
	
	private String DEPT_CODE;
	
	private String USER_MODIFIER;
	
	@CreationTimestamp
	private LocalDateTime USER_REGDTATE;
	
	@UpdateTimestamp
	private LocalDateTime USER_MODIFY_D;
	
	private String USER_USE_STATUS;

}
