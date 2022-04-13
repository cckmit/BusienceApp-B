package com.busience.standard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.dto.CustomerDto;
import com.busience.standard.service.CustomerService;

@RestController("customerManageRestController")
@RequestMapping("customerManageRest")
public class customerManageRestController {

	@Autowired
	CustomerService customerService;
	
	@GetMapping("/customerManageSelect")
	public List<CustomerDto> customerManageSelect() {
		return customerService.selectCustomerList();
	}
	
	// 선택 조회
	@GetMapping("/selectOneCustomer")
	public CustomerDto selectCustomerDao(String Cus_Code) {
		return customerService.selectCustomerDao(Cus_Code);
	}

	// insert
	@PostMapping("/customerManageInsert")
	public int customerManageInsert(CustomerDto customerDto) {
		return customerService.insertCustomer(customerDto);
	}

	// update
	@PutMapping("/customerManageUpdate")
	public int customerManageUpdate(CustomerDto customerDto) {
		return customerService.updateCustomer(customerDto);
	}

	// delete
	@DeleteMapping("/customerManageDelete")
	public int customerManageDelete(CustomerDto customerDto) {		
		return customerService.deleteCustomer(customerDto.getCus_Code());
	}
}
