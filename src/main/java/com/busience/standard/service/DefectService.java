package com.busience.standard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.standard.dao.DefectDao;
import com.busience.standard.dto.DefectInfoDto;

@Service
public class DefectService {

	@Autowired
	DefectDao defectDao;
	
	//조회
	public List<DefectInfoDto> selectDefectList() {
		return defectDao.selectDefectListDao();
	}
	
	//등록
	public int insertDefect(DefectInfoDto defectInfoDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		defectInfoDto.setDefect_Modifier(authentication.getName());
		
		return defectDao.insertDefectDao(defectInfoDto);
	};

	//수정
	public int updateDefect(DefectInfoDto defectInfoDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		defectInfoDto.setDefect_Modifier(authentication.getName());
		
		return defectDao.updateDefectDao(defectInfoDto);
	};
	
	//삭제
	public int deleteDefect(String defect_Code) {
		return defectDao.deleteDefectDao(defect_Code);
	};
}
