 package com.busience.tablet.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.SearchDto;
import com.busience.material.dao.LotMasterDao;
import com.busience.material.dao.LotNoDao;
import com.busience.material.dao.LotTransDao;
import com.busience.material.dao.StockDao;
import com.busience.production.dao.EquipWorkOrderDao;
import com.busience.production.dao.WorkOrderDao;
import com.busience.standard.dao.BOMDao;
import com.busience.standard.dao.ItemDao;
import com.busience.standard.dto.BOMDto;
import com.busience.standard.dto.ItemDto;
import com.busience.tablet.dao.CrateDao;
import com.busience.tablet.dao.CrateLotDao;
import com.busience.tablet.dao.CrateProductionDao;
import com.busience.tablet.dao.RawMaterialDao;
import com.busience.tablet.dao.RawMaterialMasterDao;
import com.busience.tablet.dao.RawMaterialSubDao;
import com.busience.tablet.dto.CrateDto;
import com.busience.tablet.dto.CrateLotDto;
import com.busience.tablet.dto.CrateProductionDto;
import com.busience.tablet.dto.RawMaterialDto;
import com.busience.tablet.dto.RawMaterialMasterDto;
import com.busience.tablet.dto.RawMaterialSubDto;

@Service
public class MaskProductionService {

	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	ItemDao itemDao;
	
	@Autowired
	BOMDao bomDao;
	
	@Autowired
	WorkOrderDao workOrderDao;
	
	@Autowired
	EquipWorkOrderDao equipWorkOrderDao;
	
	@Autowired
	LotNoDao lotNoDao;
	
	@Autowired
	LotMasterDao lotMasterDao;
	
	@Autowired
	LotTransDao lotTransDao;
	
	@Autowired
	StockDao stockDao;
	
	@Autowired
	RawMaterialMasterDao rawMaterialMasterDao;
	
	@Autowired
	RawMaterialSubDao rawMaterialSubDao;
	
	@Autowired
	RawMaterialDao rawMaterialDao;
	
	@Autowired
	CrateDao crateDao;
	
	@Autowired
	CrateLotDao crateLotDao;
	
	@Autowired
	CrateProductionDao crateProductionDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	public List<RawMaterialMasterDto> rawMaterialMasterSelect(SearchDto searchDto) {
		return rawMaterialMasterDao.rawMaterialMasterSelectDao(searchDto);
	}
	
	public List<RawMaterialMasterDto> rawMaterialRecordSelect(SearchDto searchDto){
		return rawMaterialMasterDao.rawMaterialRecordSelectDao(searchDto);
	}
	
	public List<RawMaterialDto> rawMaterialSelect(SearchDto searchDto) {
		return rawMaterialDao.rawMaterialSelectDao(searchDto);
	}
	
	//수량 업데이트
	public int rawMaterialQtyUpdate(RawMaterialMasterDto rawMaterialMasterDto) {
		return rawMaterialMasterDao.rawMaterialQtyUpdateDao(rawMaterialMasterDto);
	}
	
	// 설비명으로 조회
	public ItemDto workingSelectByMachine(SearchDto searchDto) {
		// 설비명으로 품목 조회
		String itemCode = workOrderDao.workingSelectByMachineDao(searchDto).get(0).getWorkOrder_ItemCode();

		return itemDao.selectItemCode(itemCode);		
	}
	
	
	//원자재 투입 저장
	public String rawMaterialSave(RawMaterialDto rawMaterialDto, List<RawMaterialSubDto> rawMaterialSubDtoList) {
		
		try {			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					
					for(int i=0;i<rawMaterialSubDtoList.size();i++) {
						rawMaterialDto.setMaterial_ItemCode(rawMaterialSubDtoList.get(i).getRMS_ItemCode());
						rawMaterialDto.setMaterial_LotNo(rawMaterialSubDtoList.get(i).getRMS_LotNo());
						rawMaterialDao.rawMaterialSaveDao(rawMaterialDto);
					}															
				}				
			});
			
			return null;			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<CrateLotDto> crateLotRecordSelect(SearchDto searchDto) {
		//검색해서 있는지 파악
		//있으면 해당내용을 뿌림
		return crateLotDao.crateLotRecordSelectDao(searchDto);
	}
	/*
	//상자 저장
	public CrateDto crateSave(CrateDto crateDto) {		
		try {			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					
					//기존 값이 있으면 상태값 변경
					if(crateDto.getC_Before_CrateCode().length()>0) {
						CrateDto crateDtoTemp = new CrateDto();
						String before_CrateCode = crateDto.getC_Before_CrateCode();
						crateDtoTemp.setC_CrateCode(before_CrateCode);
						
						if(crateDto.getC_Qty()>0) {
							crateDtoTemp.setC_Condition("2");
							crateDao.crateUpdateDao(crateDtoTemp);
						}else {
							crateDtoTemp.setC_Production_LotNo(null);
							crateDtoTemp.setC_Condition("0");
							crateLotDao.crateLotDeleteDao(before_CrateCode);
							crateDao.crateUpdateDao(crateDtoTemp);
						}
					}

					//그 후 새로운 상자 등록
					String LotNo = lotNoDao.crateLotNoSelectDao(crateDto.getC_ItemCode());
					lotNoDao.lotNoMatUpdateDao();
					crateDto.setC_Condition("1");
					crateDto.setC_Production_LotNo(LotNo);
					crateDao.crateUpdateDao(crateDto);
					
					CrateLotDto crateLotDto = new CrateLotDto();
					crateLotDto.setCL_LotNo(LotNo);
					crateLotDto.setCL_ItemCode(crateDto.getC_ItemCode());
					crateLotDto.setCL_CrateCode(crateDto.getC_CrateCode());
					crateLotDto.setCL_MachineCode(crateDto.getC_MachineCode());
					crateLotDao.crateLotSaveDao(crateLotDto);
				}				
			});

			return crateDto;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}*/
	
	// 코드 조건으로 조회
	public int crateProductionSave(CrateProductionDto crateProductionDto) {
		return crateProductionDao.crateProductionSaveDao(crateProductionDto);
	}
	
	public int wholeQtyUpdate(String equip, double value) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					SearchDto searchDto = new SearchDto();
										
					searchDto.setMachineCode(equip);
					
					//crateLotNo 수정
					CrateLotDto crateLotDto = new CrateLotDto();
					crateLotDto.setCL_MachineCode(equip);
					crateLotDto.setCL_Qty(value);
					crateLotDao.crateLotQtyUpdateDao(crateLotDto);
					
				}				
			});			
			return 1;			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public CrateDto CrateStatusCheck(SearchDto searchDto) {
		return crateDao.crateStatusCheckDao(searchDto);
	}
	
	// 자재 투입 현황
	public List<CrateLotDto> crateLotSelectList(SearchDto searchDto) {
		return crateLotDao.crateLotSelectList(searchDto);
	}

	public String rawMaterialChange(RawMaterialDto rawMaterialDto) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					List<DtlDto> WarehouseList = dtlDao.findByCode(10);
					
					String lotNo = rawMaterialDto.getMaterial_LotNo();
					// 랏트랜스번호 가져오기
					int no = lotTransDao.lotTransNoSelectDao(lotNo);
					String itemCode = rawMaterialDto.getMaterial_ItemCode();
					String warehouse = WarehouseList.get(1).getCHILD_TBL_NO();
					String before = warehouse;
					String after = warehouse;
					String classfy = dtlDao.findByCode(18).get(5).getCHILD_TBL_NO();
					double qty = 1;

					boolean check = rawMaterialDto.isCheck();
					//check = 1 되돌림,= 0 소모 
					if(check) {
						//되돌림
						rawMaterialDao.rawMaterialDeleteDao(rawMaterialDto.getProduction_LotNo(), lotNo);
					}else {
						rawMaterialDao.rawMaterialSaveDao(rawMaterialDto);
						//소모
						qty *= -1;
					}
					
					//생산창고 관리 안함
					boolean prod_wh = false;
					if(prod_wh) {
						//랏마스터
						lotMasterDao.lotMasterUpdateDao(qty, lotNo, warehouse);
											
						// 재고 저장
						stockDao.stockInsertUpdateDao(itemCode, qty, warehouse);

						//랏트랜스
						lotTransDao.lotTransInsertDao(no, lotNo, itemCode, qty, before, after, classfy);
					}										
				}				
			});			
			return rawMaterialDao.rawMaterialLastestSelectDao(rawMaterialDto.getProduction_LotNo(), rawMaterialDto.getMaterial_ItemCode());			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public CrateDto crateChange(CrateDto info, List<RawMaterialDto> RawMaterialDtoList) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					//교체할 상자가 사용 가능한지 부터 파악
					if(crateDao.crateSelectbyCodeDao(info.getC_CrateCode()).getC_Condition().equals("0")) {
						//기존 값이 있으면 상태값 변경
						String before_CrateCode = info.getC_Before_CrateCode();
						
						if(before_CrateCode.length()>0) {
							CrateDto crateDtoTemp = new CrateDto();
							crateDtoTemp.setC_CrateCode(before_CrateCode);
							//수량이 0보다 크면 상태 2로, 아니면 상태 0으로 되돌림
							if(info.getC_Qty()>0) {
								crateDtoTemp.setC_Condition("2");
								crateDao.crateUpdateDao(crateDtoTemp);
							}else {
								String before_LotNo = crateDao.crateSelectbyCodeDao(before_CrateCode).getC_Production_LotNo();
								rawMaterialDao.rawMaterialDeleteDao(before_LotNo, null);
								crateLotDao.crateLotDeleteDao(before_LotNo);
								
								crateDtoTemp.setC_Condition("0");
								crateDao.crateUpdateDao(crateDtoTemp);
							}
						}

						//그 후 새로운 상자 등록
						String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
						
						SearchDto searchDto = new SearchDto();
						searchDto.setMachineCode(info.getC_MachineCode());
						//작업지시에서 설비에 따른 아이템 값을 가져와서 랏번호 생성하고, 봄을 가져옴
						String itemCode = equipWorkOrderDao.equipWorkOrderSelectDao(searchDto).get(0).getEquip_WorkOrder_ItemCode();
						String lotNo = lotNoDao.crateLotNoSelect2Dao(date, itemCode);
						
						searchDto.setItemCode(itemCode);
						List<BOMDto> bomDtoList = bomDao.BOMBOMListDao(searchDto);
						
						//생성한 랏번호로 저장하고, 가져온봄과 lotlist를 비교하여 일치하는거 저장
						CrateDto crateDto = new CrateDto();
						crateDto.setC_CrateCode(info.getC_CrateCode());
						crateDto.setC_Condition("1");
						crateDto.setC_Production_LotNo(lotNo);
												
						//랏번호 생성, 상태값1로 변경
						crateDao.crateUpdateDao(crateDto);
						
						CrateLotDto crateLotDto = new CrateLotDto();
						crateLotDto.setCL_LotNo(lotNo);
						crateLotDto.setCL_ItemCode(itemCode);
						crateLotDto.setCL_CrateCode(crateDto.getC_CrateCode());
						crateLotDto.setCL_MachineCode(info.getC_MachineCode());
						crateLotDao.crateLotSaveDao(crateLotDto);
						
						for(int i=0;i<bomDtoList.size();i++) {
							RawMaterialDto rawMaterialDto = new RawMaterialDto();
							rawMaterialDto.setProduction_LotNo(lotNo);
							rawMaterialDto.setMaterial_ItemCode(bomDtoList.get(i).getBOM_ItemCode());
							for(int j=0;j<RawMaterialDtoList.size();j++) {
								if(bomDtoList.get(i).getBOM_ItemCode().equals(RawMaterialDtoList.get(j).getMaterial_ItemCode())) {
									rawMaterialDto.setMaterial_LotNo(RawMaterialDtoList.get(j).getMaterial_LotNo());
									rawMaterialDao.rawMaterialSaveDao(rawMaterialDto);
									break;
								}
							}
							
						}
					}else {
						System.out.println("변경 불가능한 상자");
					}
				}				
			});			
			return crateDao.crateSelectByMachineDao(info.getC_MachineCode(), "1");			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/*
	public String lotInput(RawMaterialDto rawMaterialDto) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					if(rawMaterialChange(rawMaterialDto) == 1) {
						rawMaterialDto.setCheck(false);
						rawMaterialDao.rawMaterialSaveDao(rawMaterialDto);
					}else {
						System.out.println("저장할 수 없습니다.");
					}
									
				}				
			});			
			return rawMaterialDao.rawMaterialLastestSelectDao(rawMaterialDto.getProduction_LotNo(), rawMaterialDto.getMaterial_ItemCode());	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}*/
	
	public String workComplete(RawMaterialDto rawMaterialDto) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					//수량이 있으면 상자상태 2로변경
					//수량이 없으면 상자상태 0으로 변경한 후 이력 삭제
				}				
			});			
			return rawMaterialDao.rawMaterialLastestSelectDao(rawMaterialDto.getProduction_LotNo(), rawMaterialDto.getMaterial_ItemCode());	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
