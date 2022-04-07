package com.busience.material.controller;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.OrderListDto;
import com.busience.material.dto.OrderMasterDto;
import com.busience.material.dto.StockDto;
import com.busience.material.service.MatOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("matOrderLXRestController")
@RequestMapping("matOrderLXRest")
public class matOrderLXRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	MatOrderService matOrderService;

	@GetMapping("MOM_Search")
	public List<OrderMasterDto> MOM_Search(SearchDto searchDto){
		return matOrderService.matOrderMasterSelect(searchDto);
	}
	
	@GetMapping("MOL_Search")
	public List<OrderListDto> MOL_Search(SearchDto searchDto){
		return matOrderService.matOrderListSelect(searchDto);
	}
	
	@GetMapping("MOS_Search")
	public List<StockDto> MOS_Search(SearchDto searchDto){
		return matOrderService.stockSelect(searchDto);
	}
	
	@PostMapping("MO_Save")
	public int MO_Save(@RequestParam("masterData") String masterData,
						@RequestParam("subData") String subData,
						Principal principal) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			OrderMasterDto orderMasterDto = mapper.readValue(masterData, OrderMasterDto.class);
			
			List<OrderListDto> orderListDtoList = Arrays.asList(mapper.readValue(subData, OrderListDto[].class));

			return matOrderService.matOrderInsertUpdate(orderMasterDto, orderListDtoList, principal.getName());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	// orderMaster delete
	@DeleteMapping("/MOM_Delete")
	public int MOM_Delete(OrderMasterDto orderMasterDto) {
		return matOrderService.matOrderMasterDelete(orderMasterDto);
	}
		
	// orderList delete
	@DeleteMapping("/MOL_Delete")
	public int MOL_Delete(@RequestBody List<OrderListDto> orderListDtoList) {		
		return matOrderService.matOrderListDelete(orderListDtoList);
	}
}