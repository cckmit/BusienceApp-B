package com.busience.standard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busience.standard.dao.CustomerDao;
import com.busience.standard.dto.CustomerDto;

@Service
public class CustomerService {

	@Autowired
	CustomerDao CustomerDao;
		
	//조회
	public List<CustomerDto> selectCustomerList() {
		return CustomerDao.selectCustomerListDao();
	}
	
	//등록
	public int insertCustomer(CustomerDto CustomerDto) {				
		try {
			CustomerDao.insertCustomerDao(CustomerDto);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	};

	//수정
	public int updateCustomer(CustomerDto CustomerDto) {
		return CustomerDao.updateCustomerDao(CustomerDto);
	};
	
	//삭제
	public int deleteCustomer(String Cus_Code) {
		return CustomerDao.deleteCustomerDao(Cus_Code);
	};
}
