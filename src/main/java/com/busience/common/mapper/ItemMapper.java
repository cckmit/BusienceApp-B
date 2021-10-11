package com.busience.common.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.busience.standard.Dto.PRODUCT_INFO_TBL;

@Mapper
public interface ItemMapper {
	
	//등록
	@Insert("insert into PRODUCT_INFO_TBL("
			+ "PRODUCT_BUSINESS_PLACE,\r\n"
			+ "PRODUCT_ITEM_CODE,\r\n"
			+ "PRODUCT_OLD_ITEM_CODE,\r\n"
			+ "PRODUCT_ITEM_NAME,\r\n"
			+ "PRODUCT_INFO_STND_1,\r\n"
			+ "PRODUCT_INFO_STND_2,\r\n"
			+ "PRODUCT_UNIT,\r\n"
			+ "PRODUCT_MATERIAL,\r\n"
			+ "PRODUCT_MTRL_CLSFC,\r\n"
			+ "PRODUCT_ITEM_CLSFC_1,\r\n"
			+ "PRODUCT_ITEM_CLSFC_2,\r\n"
			+ "PRODUCT_SUBSID_MATL_MGMT,\r\n"
			+ "PRODUCT_ITEM_STTS,\r\n"
			+ "PRODUCT_BASIC_WAREHOUSE,\r\n"
			+ "PRODUCT_SFTY_STOCK,\r\n"
			+ "PRODUCT_BUYER,\r\n"
			+ "PRODUCT_WRHSN_INSPC,\r\n"
			+ "PRODUCT_USE_STATUS,\r\n"
			+ "PRODUCT_MODIFY_D,\r\n"
			+ "PRODUCT_MODIFIER\r\n"
			+ ") values(\r\n"
			+ "#{PRODUCT_BUSINESS_PLACE},\r\n"
			+ "#{PRODUCT_ITEM_CODE},\r\n"
			+ "#{PRODUCT_OLD_ITEM_CODE},\r\n"
			+ "#{PRODUCT_ITEM_NAME},\r\n"
			+ "#{PRODUCT_INFO_STND_1},\r\n"
			+ "#{PRODUCT_INFO_STND_2},\r\n"
			+ "#{PRODUCT_UNIT},\r\n"
			+ "#{PRODUCT_MATERIAL},\r\n"
			+ "#{PRODUCT_MTRL_CLSFC},\r\n"
			+ "#{PRODUCT_ITEM_CLSFC_1},\r\n"
			+ "#{PRODUCT_ITEM_CLSFC_2},\r\n"
			+ "#{PRODUCT_SUBSID_MATL_MGMT},\r\n"
			+ "#{PRODUCT_ITEM_STTS},\r\n"
			+ "#{PRODUCT_BASIC_WAREHOUSE},\r\n"
			+ "#{PRODUCT_SFTY_STOCK},\r\n"
			+ "#{PRODUCT_BUYER},\r\n"
			+ "#{PRODUCT_WRHSN_INSPC},\r\n"
			+ "#{PRODUCT_USE_STATUS},\r\n"
			+ "now(),\r\n"
			+ "#{PRODUCT_MODIFIER})")
	int insertItemCode(PRODUCT_INFO_TBL product_INFO_TBL);

	//수정
	@Update("update PRODUCT_INFO_TBL\r\n"
			+ "set\r\n"
			+ "PRODUCT_BUSINESS_PLACE = #{PRODUCT_BUSINESS_PLACE},\r\n"
			+ "PRODUCT_OLD_ITEM_CODE = #{PRODUCT_OLD_ITEM_CODE},\r\n"
			+ "PRODUCT_ITEM_NAME = #{PRODUCT_ITEM_NAME},\r\n"
			+ "PRODUCT_INFO_STND_1 = #{PRODUCT_INFO_STND_1},\r\n"
			+ "PRODUCT_INFO_STND_2 = #{PRODUCT_INFO_STND_2},\r\n"
			+ "PRODUCT_UNIT = #{PRODUCT_UNIT},\r\n"
			+ "PRODUCT_MATERIAL = #{PRODUCT_MATERIAL},\r\n"
			+ "PRODUCT_MTRL_CLSFC = #{PRODUCT_MTRL_CLSFC},\r\n"
			+ "PRODUCT_ITEM_CLSFC_1 = #{PRODUCT_ITEM_CLSFC_1},\r\n"
			+ "PRODUCT_ITEM_CLSFC_2 = #{PRODUCT_ITEM_CLSFC_2},\r\n"
			+ "PRODUCT_SUBSID_MATL_MGMT = #{PRODUCT_SUBSID_MATL_MGMT},\r\n"
			+ "PRODUCT_ITEM_STTS = #{PRODUCT_ITEM_STTS},\r\n"
			+ "PRODUCT_BASIC_WAREHOUSE = #{PRODUCT_BASIC_WAREHOUSE},\r\n"
			+ "PRODUCT_SFTY_STOCK = #{PRODUCT_SFTY_STOCK},\r\n"
			+ "PRODUCT_BUYER = #{PRODUCT_BUYER},\r\n"
			+ "PRODUCT_WRHSN_INSPC = #{PRODUCT_WRHSN_INSPC},\r\n"
			+ "PRODUCT_USE_STATUS = #{PRODUCT_USE_STATUS},\r\n"
			+ "PRODUCT_MODIFY_D = now(),\r\n"
			+ "PRODUCT_MODIFIER = #{PRODUCT_MODIFIER}\r\n"
			+ "where PRODUCT_ITEM_CODE = #{PRODUCT_ITEM_CODE}")
	int updateItemCode(PRODUCT_INFO_TBL product_INFO_TBL);
	
	//삭제
	@Delete("delete from PRODUCT_INFO_TBL\r\n"
			+ "where PRODUCT_ITEM_CODE= #{itemCode}")
	int deleteItemCode(@Param("itemCode") String itemCode);
}
