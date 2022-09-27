package com.busience.standard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.busience.standard.dao.CustomerDao;
import com.busience.standard.dto.CustomerDto;

@Service
public class CustomerService {

	@Autowired
	CustomerDao customerDao;
		
	//조회
	public List<CustomerDto> selectCustomerList() {
		return customerDao.selectCustomerListDao();
	}
	
	//선택 조회
	public CustomerDto selectCustomer(String Cus_Code) {
		return customerDao.selectCustomerDao(Cus_Code);
	}
	
	//등록
	public int insertCustomer(CustomerDto customerDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		customerDto.setCus_Modifier(authentication.getName());
		
		return customerDao.insertCustomerDao(customerDto);	
	}

	//수정
	public int updateCustomer(CustomerDto customerDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		customerDto.setCus_Modifier(authentication.getName());
		
		return customerDao.updateCustomerDao(customerDto);
	}
	
	//삭제
	public int deleteCustomer(String Cus_Code) {
		return customerDao.deleteCustomerDao(Cus_Code);
	}
}
