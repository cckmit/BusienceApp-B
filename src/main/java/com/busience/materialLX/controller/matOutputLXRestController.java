package com.busience.materialLX.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.materialLX.dto.OutMat_tbl;
import com.busience.materialLX.dto.StockMat_tbl;
import com.busience.materialLX.service.MatOutputService;

@RestController("matOutputLXRestController")
@RequestMapping("matOutputLXRest")
public class matOutputLXRestController {

	@Autowired
	MatOutputService matOutputService;

	@GetMapping("/MOS_Search")
	public List<StockMat_tbl> MOS_Search() {
		return matOutputService.outMatList();
	}

	// orderList save
	@PostMapping("/MOM_Save")
	public int MOM_Save(@RequestBody List<OutMat_tbl> outMat_tbl_List, Principal principal) {
		return matOutputService.matOutputRegister(outMat_tbl_List, principal.getName());
	}
}
