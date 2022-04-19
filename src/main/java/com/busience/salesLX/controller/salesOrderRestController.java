package com.busience.salesLX.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dto.SalesOrderListDto;
import com.busience.salesLX.dto.SalesOrderMasterDto;
import com.busience.salesLX.dto.Sales_OrderList_tbl;
import com.busience.salesLX.dto.Sales_OrderMaster_tbl;
import com.busience.salesLX.dto.Sales_StockMat_tbl;
import com.busience.salesLX.service.SalesOrderListService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("salesOrderLXRestController")
@RequestMapping("salesOrderRest")
public class salesOrderRestController {

	@Autowired
	DataSource dataSource;

	@Autowired
	SalesOrderListService salesOrderService;

	// SalesOrderMaster select
	@GetMapping("/SO_Search")
	public List<SalesOrderMasterDto> salesOrderMasterSelectDao(SearchDto searchDto) {
		return salesOrderService.salesOrderMasterSelectDao(searchDto);
	}

	// SalesOrderList select
	@GetMapping("/SOL_Search")
	public List<SalesOrderListDto> salesOrderListSelectDao(SearchDto searchDto) {
		return salesOrderService.salesOrderListSelectDao(searchDto);
	}

	// save
	@PostMapping("/SOL_Save")
	public int SO_Save(@RequestParam("masterData") String masterData, @RequestParam("subData") String subData,
			Principal principal) {
		
		System.out.println("masterData=" + masterData);
		System.out.println("subData=" + subData);
		ObjectMapper mapper = new ObjectMapper();

		try {
			SalesOrderMasterDto salesOrderMasterDto = mapper.readValue(masterData, SalesOrderMasterDto.class);

			List<SalesOrderListDto> salesOrderListDtoList = Arrays
					.asList(mapper.readValue(subData, SalesOrderListDto[].class));

			return salesOrderService.salesOrderInsertUpdate(salesOrderMasterDto, salesOrderListDtoList,
					principal.getName());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	// salesOrderList delete
	@DeleteMapping("/SOL_Delete")
	public int SOL_Delete(@RequestBody List<SalesOrderListDto> salesOrderDtoList) {
		return salesOrderService.salesOrderListDeleteDao(salesOrderDtoList);				
	}
	
}
