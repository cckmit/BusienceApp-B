package com.busience.wip.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.SearchDto;
import com.busience.wip.dao.WipLotManageDao;
import com.busience.wip.dto.WipLotMasterDto;
import com.busience.wip.dto.WipLotTransDto;

@Service
public class WipLotManageService {
	
	@Autowired
	WipLotManageDao wipLotManageDao;
	
	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;

	//Lot 목록
	public List<WipLotMasterDto> wipLotManageList(SearchDto searchDto) {
		return wipLotManageDao.wipLotManageListDao(searchDto);
	}
	
	//라벨 프린터
	@Transactional(rollbackFor = Exception.class)
	public WipLotMasterDto wipLabelPrint(WipLotMasterDto wipLotMasterDto){
				
		SimpleDateFormat sdf1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-MM-dd");
		SimpleDateFormat sdf3 = new SimpleDateFormat ( "yyMMdd");
		
		Date date = new Date();
		
		String Wip_LotNo = null;
		int count = 0;
		
		//기존 데이터가 아닌경우 신규 랏번호 생성
		if(wipLotMasterDto.getWip_LotNo() == null) {
			String time2 = sdf2.format(date);
			
			count = wipLotManageDao.wipLotManageListCountDao(time2);
			
			Wip_LotNo = "S"+sdf3.format(date)+String.format("%02d", count+1);
			
			List<DtlDto> dtlProcess = dtlDao.findByCode(32);
			
			//wipLotMaster
			wipLotMasterDto.setWip_LotNo(Wip_LotNo);
			wipLotMasterDto.setWip_Process(dtlProcess.get(0).getCHILD_TBL_NO());
			wipLotMasterDto.setWip_InputDate_P1(sdf1.format(date));	
	
			wipLotManageDao.wipLotMasterInsertDao(wipLotMasterDto);
			
			//wipLotTrans
			WipLotTransDto wipLotTransDto = new WipLotTransDto();
			
			wipLotTransDto.setWip_LotNo(wipLotMasterDto.getWip_LotNo());
			wipLotTransDto.setWip_Process(wipLotMasterDto.getWip_Process());
			wipLotTransDto.setWip_InputDate(wipLotMasterDto.getWip_InputDate_P1());
			
			wipLotManageDao.wipLotTransInsertDao(wipLotTransDto);
		}
		
		//프린터 실행
		//wipLabelPrinter(wipLotMasterDto);
		return wipLotMasterDto;
	}
	/*
	public void wipLabelPrinter(WipLotMasterDto wipLotManageDto) throws Exception {
		String commands = "";
			
		
		// 기본프린터로 설정 되어있는 프린터로 인쇄함
		PrintService pService = PrintServiceLookup.lookupDefaultPrintService();
		String printServiceName = pService.getName();
		log.info(printServiceName);
		
		if (pService != null && Objects.equals(printServiceName, "ZDesigner ZD230-203dpi ZPL")) {
			DocPrintJob job = pService.createPrintJob();
			   
			commands += "^XA\r\n"
					+ "^FO0,128^GFA,15360,15360,00080,:Z64:\r\n"
					+ "eJzt2wEJACAUQ8GPCWxg/5IqdpgIci/AsQKrkiTpx8aKNY/Xc9Maj8fj8Xg8Ho/H4/F4PB6Px+PxeDwej8fj8Xg8Ho/H4/F4PB6Px+PxeLw3XvpvIEl32kiyoHs=:0687\r\n"
					+ "^SEE:UHANGUL.DAT\r\n"
					+ "^CW1,E:KFONT3.FNT^CI26\r\n"
					+ "^FO19,21^GB603,71,71^FS\r\n"
					+ "^FT160,77^A0N,56,69^FB603,1,0^FR^FH\\^FD"+wipLotManageDto.getWip_LotNo()+"^FS\r\n"
					+ "^FT19,132^A1,39,39^FD발행일: "+wipLotManageDto.getWip_InputDate_P1()+"^FS\r\n"
					+ "^FO19,261^GB604,0,1^FS\r\n"
					+ "^FO143,150^GB0,156,1^FS\r\n"
					+ "^FO265,150^GB0,156,1^FS\r\n"
					+ "^FO388,150^GB0,156,1^FS\r\n"
					+ "^FO505,150^GB0,156,1^FS\r\n"
					+ "^FT53,293^A1,32,30^FD밑술^FS\r\n"
					+ "^FT148,293^A1,32,28^FD1단 담금^FS\r\n"
					+ "^FT270,293^A1,32,28^FD2단 담금^FS\r\n"
					+ "^FT400,293^A1,32,30^FD후발효^FS\r\n"
					+ "^FT535,293^A1,32,30^FD숙성^FS\r\n"
					+ "^BY3,3,69^FT53,384^B3N,N,,N,N\r\n"
					+ "^FD"+wipLotManageDto.getWip_LotNo()+"^FS\r\n"
					+ "^XZ";
				
			//System.out.println(commands);
			
			byte[] data = commands.getBytes("EUC-KR");
			   
			DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
	   
			Doc doc = new SimpleDoc(data, flavor, null);
			job.print(doc, null);
		}else {
			throw new Exception("print error");
		}
	}*/
	
	//wipInOutList
	public List<WipLotTransDto> wipInOutList() {		
		return wipLotManageDao.wipInOutListDao();
	}
	
	// wipInputList
	public List<WipLotTransDto> wipInputList(SearchDto searchDto) {
		return wipLotManageDao.wipInputListDao(searchDto);
	}
	
	// wipOutputList
	public List<WipLotTransDto> wipOutputList(SearchDto searchDto) {
		return wipLotManageDao.wipOutputListDao(searchDto);
	}
	
	// wipInOutInsert
	@Transactional(rollbackFor = Exception.class)
	public String wipInOutInsert(String wip_LotNo) {
		
		List<WipLotMasterDto> wipLotMasterDtoList = new ArrayList<WipLotMasterDto>();
		List<DtlDto> dtlDtoList = dtlDao.findByCode(32);
		
		//공정과정중인 재공품 리스트를 가져와서 조건에따라 입출고 시킴
		wipLotMasterDtoList = wipLotManageDao.wipProcessingListDao(dtlDao.findByCode(32).get(5).getCHILD_TBL_NO());
		
		WipLotMasterDto wipLotMasterDto = new WipLotMasterDto(); 
		WipLotTransDto wipLotTransDto = new WipLotTransDto();
		
		String Wip_IntputDate = null;
		String INorOUT = "fail";
				
		for(int i=0;i<wipLotMasterDtoList.size();i++) {
			wipLotMasterDto = wipLotMasterDtoList.get(i);
			if(wipLotMasterDto.getWip_LotNo().equals(wip_LotNo)) {
				String newProcess = Integer.toString(Integer.parseInt(wipLotMasterDto.getWip_Process())+1);
				
				SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				if(wipLotMasterDto.getWip_Process().equals(dtlDtoList.get(0).getCHILD_TBL_NO())) {
					Wip_IntputDate = wipLotMasterDto.getWip_InputDate_P1();
					if(Wip_IntputDate == null) {
						wipLotMasterDto.setWip_InputDate_P1(sdf.format(date));
						INorOUT = "in";
					}else {
						wipLotMasterDto.setWip_OutputDate_P1(sdf.format(date));
						INorOUT = "out";
					}
				}else if(wipLotMasterDto.getWip_Process().equals(dtlDtoList.get(1).getCHILD_TBL_NO())) {
					Wip_IntputDate = wipLotMasterDto.getWip_InputDate_P2();
					if(Wip_IntputDate == null) {
						wipLotMasterDto.setWip_InputDate_P2(sdf.format(date));
						INorOUT = "in";
					}else {
						wipLotMasterDto.setWip_OutputDate_P2(sdf.format(date));
						INorOUT = "out";
					}
				}else if(wipLotMasterDto.getWip_Process().equals(dtlDtoList.get(2).getCHILD_TBL_NO())) {
					Wip_IntputDate = wipLotMasterDto.getWip_InputDate_P3();
					if(Wip_IntputDate == null) {
						wipLotMasterDto.setWip_InputDate_P3(sdf.format(date));
						INorOUT = "in";
					}else {
						wipLotMasterDto.setWip_OutputDate_P3(sdf.format(date));
						INorOUT = "out";
					}
				}else if(wipLotMasterDto.getWip_Process().equals(dtlDtoList.get(3).getCHILD_TBL_NO())) {
					Wip_IntputDate = wipLotMasterDto.getWip_InputDate_P4();
					if(Wip_IntputDate == null) {
						wipLotMasterDto.setWip_InputDate_P4(sdf.format(date));
						INorOUT = "in";
					}else {
						wipLotMasterDto.setWip_OutputDate_P4(sdf.format(date));
						INorOUT = "out";
					}
				}else if(wipLotMasterDto.getWip_Process().equals(dtlDtoList.get(4).getCHILD_TBL_NO())) {
					Wip_IntputDate = wipLotMasterDto.getWip_InputDate_P5();
					if(Wip_IntputDate == null) {
						wipLotMasterDto.setWip_InputDate_P5(sdf.format(date));
						INorOUT = "in";
					}else {
						wipLotMasterDto.setWip_OutputDate_P5(sdf.format(date));
						INorOUT = "out";
					}
				}

				wipLotTransDto.setWip_LotNo(wip_LotNo);
				wipLotTransDto.setWip_Process(wipLotMasterDto.getWip_Process());
				
				if(INorOUT.equals("in")) {
					wipLotManageDao.wipLotMasterUpdateDao(wipLotMasterDto);

					wipLotTransDto.setWip_OutputDate(sdf.format(date));
					wipLotManageDao.wipLotTransUpdateDao(wipLotTransDto);
					
					wipLotTransDto.setWip_InputDate(sdf.format(date));
					wipLotManageDao.wipLotTransInsertDao(wipLotTransDto);

				}else if(INorOUT.equals("out")){
					wipLotMasterDto.setWip_Process(newProcess);
					wipLotManageDao.wipLotMasterUpdateDao(wipLotMasterDto);
					
					wipLotTransDto.setWip_OutputDate(sdf.format(date));
					wipLotManageDao.wipLotTransUpdateDao(wipLotTransDto);			
				}
			}
		}
		return INorOUT;
	}

	//wipLotMasterList
	public List<WipLotMasterDto> wipLotMasterList(SearchDto searchDto) {
		List<WipLotMasterDto> WipLotMasterDtoList = wipLotManageDao.wipLotMasterListDao(searchDto);
		for(int i=0;i<WipLotMasterDtoList.size();i++) {
			setSaveDate(WipLotMasterDtoList.get(i));
		}
		
		return WipLotMasterDtoList;
	}
	
	
	//wipProcessingList
	public List<WipLotMasterDto> wipProcessingList() {
		String endProcessCode = dtlDao.findByCode(32).get(5).getCHILD_TBL_NO();
		List<WipLotMasterDto> WipLotMasterDtoList = wipLotManageDao.wipProcessingListDao(endProcessCode);
		for(int i=0;i<WipLotMasterDtoList.size();i++) {
			setSaveDate(WipLotMasterDtoList.get(i));
		}
		return WipLotMasterDtoList;
	}
	
	//보관기간 set코드
	public void setSaveDate(WipLotMasterDto wipLotMasterDto) {
		String inputDate_P1 = wipLotMasterDto.getWip_InputDate_P1();
		String outputDate_P1 = wipLotMasterDto.getWip_OutputDate_P1();
		if(inputDate_P1 != null) {
			wipLotMasterDto.setWip_SaveDate_P1(saveDateCalc(inputDate_P1, outputDate_P1));
		}
		
		String inputDate_P2 = wipLotMasterDto.getWip_InputDate_P2();
		String outputDate_P2 = wipLotMasterDto.getWip_OutputDate_P2();
		if(inputDate_P2 != null) {
			wipLotMasterDto.setWip_SaveDate_P2(saveDateCalc(inputDate_P2, outputDate_P2));
		}
		
		String inputDate_P3 = wipLotMasterDto.getWip_InputDate_P3();
		String outputDate_P3 = wipLotMasterDto.getWip_OutputDate_P3();
		if(inputDate_P3 != null) {
			wipLotMasterDto.setWip_SaveDate_P3(saveDateCalc(inputDate_P3, outputDate_P3));
		}
		
		String inputDate_P4 = wipLotMasterDto.getWip_InputDate_P4();
		String outputDate_P4 = wipLotMasterDto.getWip_OutputDate_P4();
		if(inputDate_P4 != null) {
			wipLotMasterDto.setWip_SaveDate_P4(saveDateCalc(inputDate_P4, outputDate_P4));
		}
		
		String inputDate_P5 = wipLotMasterDto.getWip_InputDate_P5();
		String outputDate_P5 = wipLotMasterDto.getWip_OutputDate_P5();
		if(inputDate_P5 != null) {
			wipLotMasterDto.setWip_SaveDate_P5(saveDateCalc(inputDate_P5, outputDate_P5));
		}
	}
	
	//보관기간 계산코드
	public String saveDateCalc(String inputDate, String outputDate) {
		DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		LocalDateTime inputDateTime = LocalDateTime.parse(inputDate, DTF);
		LocalDateTime outputDateTime = LocalDateTime.now();
		if(outputDate != null) {
			outputDateTime = LocalDateTime.parse(outputDate, DTF);
		}
		//차이 분
		long totalMinutes = ChronoUnit.MINUTES.between(inputDateTime, outputDateTime);
		//시간
		long hour = totalMinutes/60;
		//분
		long minute = totalMinutes%60;
		//문자
		String HM = "";
		if(hour != 0) {
			HM += hour+"시간 ";
		}
		HM += minute+"분";
		return HM;
	}
	
	//wipInputRollback
	public int wipInputRollback(WipLotTransDto wipLotTransDto) {
		List<DtlDto> dtlDto = dtlDao.findByCode(32);
		//트랜스에서 해당하는 행을 삭제해주고 마스터에서 해당하는 인풋 시간을 지움
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<dtlDto.size();i++) {
						if(wipLotTransDto.getWip_Process().equals(dtlDto.get(i).getCHILD_TBL_NO())) {
							wipLotTransDto.setWip_Process_No(i+"");
						}
					}

					wipLotManageDao.wipLotTransDeleteDao(wipLotTransDto);
					
					wipLotManageDao.wipInputRollbackDao(wipLotTransDto);
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//wipInputRollbackCheck
	public WipLotMasterDto wipOutputRollbackCheck(SearchDto searchDto) {
		WipLotMasterDto wipLotMasterDto = new WipLotMasterDto();
		
		wipLotMasterDto = wipLotManageDao.wipLotMasterListDao(searchDto).get(0);
		
		List<DtlDto> dtlDto = dtlDao.findByCode(32);
		
		String inputTime = null;
		
		if(wipLotMasterDto.getWip_Process().equals(dtlDto.get(1).getCHILD_TBL_NO())) {
			inputTime = wipLotMasterDto.getWip_InputDate_P2();
			wipLotMasterDto.setWip_Process_Name(dtlDto.get(1).getCHILD_TBL_TYPE());     
		}else if(wipLotMasterDto.getWip_Process().equals(dtlDto.get(2).getCHILD_TBL_NO())) {
			inputTime = wipLotMasterDto.getWip_InputDate_P3();
			wipLotMasterDto.setWip_Process_Name(dtlDto.get(2).getCHILD_TBL_TYPE());     
		}else if(wipLotMasterDto.getWip_Process().equals(dtlDto.get(3).getCHILD_TBL_NO())) {
			inputTime = wipLotMasterDto.getWip_InputDate_P4();
			wipLotMasterDto.setWip_Process_Name(dtlDto.get(3).getCHILD_TBL_TYPE());
		}else if(wipLotMasterDto.getWip_Process().equals(dtlDto.get(4).getCHILD_TBL_NO())) {
			inputTime = wipLotMasterDto.getWip_InputDate_P5();
			wipLotMasterDto.setWip_Process_Name(dtlDto.get(4).getCHILD_TBL_TYPE());
		}else {
			inputTime = null;
			wipLotMasterDto.setWip_Process_Name("완료");
		}
		
		if(inputTime == null) {
			return wipLotMasterDto;
		}else {
			return null;
		}
	}
	
	//wipOutputRollback
	public int wipOutputRollback(WipLotTransDto wipLotTransDto) {
		List<DtlDto> dtlDto = dtlDao.findByCode(32);
		
		//트랜스에서 해당하는 행을 업데이트로 시간을 없애주고, 마스터에서 해당하는 아웃풋 시간을 지우고, 공정을 전으로 되돌림
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					String newProcess = null;
					for(int i=0;i<dtlDto.size();i++) {
						if(wipLotTransDto.getWip_Process().equals(dtlDto.get(i).getCHILD_TBL_NO())) {
							newProcess = dtlDto.get(i-1).getCHILD_TBL_NO();
							wipLotTransDto.setWip_Process_No(i-1+"");
						}
					}
					wipLotTransDto.setWip_Process(newProcess);
					wipLotTransDto.setWip_OutputDate(null);
					System.out.println(wipLotTransDto);
					wipLotManageDao.wipLotTransUpdateDao(wipLotTransDto);
					wipLotManageDao.wipOutputRollbackDao(wipLotTransDto);
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
