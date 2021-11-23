package com.busience.system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.MenuDto;
import com.busience.standard.dao.UserDao;
import com.busience.standard.dto.UserDto;
import com.busience.system.dao.MenuAddDao;

@Service
public class MenuAddService {

	@Autowired
	MenuAddDao menuAddDao;
	
	@Autowired
	UserDao userDao;

	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//메뉴 리스트
	public List<MenuDto> menuAddList() {
        return menuAddDao.menuAddListDao();
	}
	
	//메뉴 리스트
	public int menuAddInsert(MenuDto menuDto) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// 메뉴저장
					menuAddDao.menuAddInsertDao(menuDto);
					
					List<UserDto> userDtoList = new ArrayList<UserDto>(); 
					//유저코드별로 Menu_MGMT_tbl에 추가
					userDtoList = userDao.selectUserList();
					for(int i=0;i<userDtoList.size();i++) {
						menuDto.setUser_Code(userDtoList.get(i).getUSER_CODE());
						menuAddDao.menuMgmtInsertDao(menuDto);
					}
					
					List<DtlDto> dtlDtoList = new ArrayList<DtlDto>();
					//사용자타입별로 Rights_MGMT_tbl에 추가
					dtlDtoList = dtlDao.findByCode(1);
					for(int j=0;j<dtlDtoList.size();j++) {
						menuDto.setUser_Code(dtlDtoList.get(j).getCHILD_TBL_NO());
						menuAddDao.rightsMgmtInsertDao(menuDto);
					}
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
