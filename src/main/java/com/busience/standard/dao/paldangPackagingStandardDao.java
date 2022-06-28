package com.busience.standard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.standard.dto.PaldangPackagingStandardDto;

@Mapper
public interface paldangPackagingStandardDao {

	public List<PaldangPackagingStandardDto> paldangPackagingListDao();
	
	public List<PaldangPackagingStandardDto> paldangPackagingCheckNo(PaldangPackagingStandardDto paldangPackagingStandardDto);
	
	public int paldangPackaingInsertDao(PaldangPackagingStandardDto paldangPackagingStandardDto);
	
	public int paldangPackaingDeleteDao(PaldangPackagingStandardDto paldangPackagingStandardDto);
}
