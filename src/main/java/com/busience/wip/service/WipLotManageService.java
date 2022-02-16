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
import com.busience.qc.dto.DefectDto;
import com.busience.standard.dao.ItemDao;
import com.busience.standard.dao.RoutingDao;
import com.busience.standard.dto.ItemDto;
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
	ItemDao itemDao;
	
	@Autowired
	RoutingDao routingDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;

	//Lot 목록
	public List<WipLotTransDto> wipLotManageList(SearchDto searchDto) {
		return wipLotManageDao.wipLotManageListDao(searchDto);
	}
	
	//lot 생존 조건 목록
	public List<WipLotTransDto> wipLotTransList(SearchDto searchDto) {
		return wipLotManageDao.wipLotTransListDao(searchDto);
	}
	
	//해당lot 최근 데이터
	public List<WipLotTransDto> wipLastData(SearchDto searchDto) {
		return wipLotManageDao.wipLastDataDao(searchDto);
	}
	
	//완제품 목록
	public List<ItemDto> selectItemList() {
		List<DtlDto> dtlDtoList = new ArrayList<DtlDto>();
		dtlDtoList = dtlDao.findByCode(5);
		
		String materialClsfc = "";
		for(int i=0;i<dtlDtoList.size();i++) {
			if("완제품".equals(dtlDtoList.get(i).getCHILD_TBL_TYPE()))
			materialClsfc = dtlDtoList.get(i).getCHILD_TBL_NO();
		}
		return itemDao.selectMaterialClsfc(materialClsfc);
	}
	
	//라벨 프린터
	@Transactional(rollbackFor = Exception.class)
	public WipLotTransDto wipLabelPrint(ItemDto itemDto){
				
		SimpleDateFormat sdf1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf3 = new SimpleDateFormat ( "yyMMdd");
		
		Date date = new Date();
								
		//오늘 생성한 랏번호 확인
		int count = wipLotManageDao.wipLotManageListCountDao();
		
		//랏번호 생성
		String Wip_LotNo = sdf3.format(date)+String.format("%03d", count+1);
		
		//랏마스터에 저장
		wipLotMasterInsert(Wip_LotNo, 1);
		
		//wipLotTrans
		WipLotTransDto wipLotTransDto = new WipLotTransDto();
		
		wipLotTransDto.setWip_Prefix(itemDto.getPRODUCT_OLD_ITEM_CODE());
		wipLotTransDto.setWip_LotNo(Wip_LotNo);
		wipLotTransDto.setWip_Process_Type(itemDto.getPRODUCT_ITEM_CLSFC_1());
		wipLotTransDto.setWip_Process_No(1);
		wipLotTransDto.setWip_InputDate(sdf1.format(date));
		
		wipLotManageDao.wipLotTransInsertDao(wipLotTransDto);
		
		//프린터 실행
		//wipLabelPrinter(wipLotTransDto);
		return wipLotTransDto;
	}
	
	private void wipLotMasterInsert(String wip_LotNo, int status) {
		WipLotMasterDto wipLotMasterDto = new WipLotMasterDto();
		
		//wipLotMaster
		wipLotMasterDto.setWip_LotNo(wip_LotNo);
		wipLotMasterDto.setWip_Status(status);
		wipLotManageDao.wipLotMasterInsertDao(wipLotMasterDto);
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
		List<WipLotTransDto> WipLotTransDtoList = new ArrayList<WipLotTransDto>();
		WipLotTransDtoList = wipLotManageDao.wipInOutListDao();
		for(int i=0;i<WipLotTransDtoList.size();i++) {
			String inputDate = WipLotTransDtoList.get(i).getWip_InputDate();
			String outputDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

			WipLotTransDtoList.get(i).setWip_SaveTime(saveDateCalc(inputDate, outputDate));
		}
		
		return WipLotTransDtoList;
	}
	
	// wipInputList
	public List<WipLotTransDto> wipInputList(SearchDto searchDto) {
		List<WipLotTransDto> WipLotTransDtoList = wipLotManageDao.wipInputListDao(searchDto);
		
		for(int i=0;i<WipLotTransDtoList.size();i++) {
			String Wip_FullLotNo = WipLotTransDtoList.get(i).getWip_Prefix()+WipLotTransDtoList.get(i).getWip_LotNo();
			WipLotTransDtoList.get(i).setWip_FullLotNo(Wip_FullLotNo);
		}
		
		return WipLotTransDtoList;
	}
	
	// wipOutputList
	public List<WipLotTransDto> wipOutputList(SearchDto searchDto) {
		List<WipLotTransDto> WipLotTransDtoList = wipLotManageDao.wipOutputListDao(searchDto);
		
		for(int i=0;i<WipLotTransDtoList.size();i++) {
			String Wip_FullLotNo = WipLotTransDtoList.get(i).getWip_Prefix()+WipLotTransDtoList.get(i).getWip_LotNo();
			WipLotTransDtoList.get(i).setWip_FullLotNo(Wip_FullLotNo);
		}
		return WipLotTransDtoList;
	}
	
	// wipInOutInsert
	@Transactional(rollbackFor = Exception.class)
	public String wipInOutInsert(String wip_LotNo) {
		//입고 또는 출고
		//확인한 데이터만 들어오므로 해당 랏번호의 마지막 데이터를 찾아 검증만 하면됨다
		//마지막데이터의 출고시간이 있으면 입고
		//이때 숫자가 5면 그만해야됨
		//마지막데이터의 출고시간이 없으면 출고
		
		WipLotTransDto wipLotTransDto = wipLotManageDao.wipLastDataDao(wip_LotNo);
		
		String outputDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		String INorOUT = "fail";
		
		if(wipLotTransDto.getWip_OutputDate() != null) {
			//입고처리
			if(wipLotTransDto.getWip_Process_No() == 5) {
				//입고처리하면 안됨
				return INorOUT;
			}else {
				INorOUT = "in";
				wipLotTransDto.setWip_Process_No(wipLotTransDto.getWip_Process_No()+1);
				
				
				wipLotManageDao.wipLotTransInsertDao(wipLotTransDto);
			}
		}else {
			if(wipLotTransDto.getWip_Process_No() == 5) {
				//wipLotMaster
				wipLotMasterInsert(wipLotTransDto.getWip_LotNo(), 2);
			}
			//출고처리
			wipLotTransDto.setWip_OutputDate(outputDate);
			INorOUT = "out";
			wipLotManageDao.wipLotTransUpdateDao(wipLotTransDto);
		}
		return INorOUT;
	}

	//wipLotMasterList
	public List<WipLotTransDto> wipLotMasterList(WipLotTransDto wipLotTransDto) {
		List<WipLotTransDto> WipLotTransDtoList = wipLotManageDao.wipLotMasterListDao(wipLotTransDto);
		for(int i=0;i<WipLotTransDtoList.size();i++) {
			String Wip_FullLotNo = WipLotTransDtoList.get(i).getWip_Prefix()+WipLotTransDtoList.get(i).getWip_LotNo();
			WipLotTransDtoList.get(i).setWip_FullLotNo(Wip_FullLotNo);
			
			setSaveDate(WipLotTransDtoList.get(i));
		}
		
		return WipLotTransDtoList;
	}
	
	
	//wipProcessingList
	public List<WipLotTransDto> wipProcessingList(WipLotTransDto wipLotTransDto) {
		List<WipLotTransDto> WipLotTransDtoList = wipLotManageDao.wipProcessingListDao(wipLotTransDto);
		for(int i=0;i<WipLotTransDtoList.size();i++) {
			String Wip_FullLotNo = WipLotTransDtoList.get(i).getWip_Prefix()+WipLotTransDtoList.get(i).getWip_LotNo();
			WipLotTransDtoList.get(i).setWip_FullLotNo(Wip_FullLotNo);
			
			setSaveDate(WipLotTransDtoList.get(i));
		}
		return WipLotTransDtoList;
	}
	
	//보관기간 set코드
	public void setSaveDate(WipLotTransDto wipLotTransDto) {
		String inputDate_P1 = wipLotTransDto.getWip_InputDate_P1();
		String outputDate_P1 = wipLotTransDto.getWip_OutputDate_P1();
		if(inputDate_P1 != null) {
			wipLotTransDto.setWip_SaveDate_P1(saveDateCalc(inputDate_P1, outputDate_P1));
		}
		
		String inputDate_P2 = wipLotTransDto.getWip_InputDate_P2();
		String outputDate_P2 = wipLotTransDto.getWip_OutputDate_P2();
		if(inputDate_P2 != null) {
			wipLotTransDto.setWip_SaveDate_P2(saveDateCalc(inputDate_P2, outputDate_P2));
		}
		
		String inputDate_P3 = wipLotTransDto.getWip_InputDate_P3();
		String outputDate_P3 = wipLotTransDto.getWip_OutputDate_P3();
		if(inputDate_P3 != null) {
			wipLotTransDto.setWip_SaveDate_P3(saveDateCalc(inputDate_P3, outputDate_P3));
		}
		
		String inputDate_P4 = wipLotTransDto.getWip_InputDate_P4();
		String outputDate_P4 = wipLotTransDto.getWip_OutputDate_P4();
		if(inputDate_P4 != null) {
			wipLotTransDto.setWip_SaveDate_P4(saveDateCalc(inputDate_P4, outputDate_P4));
		}
		
		String inputDate_P5 = wipLotTransDto.getWip_InputDate_P5();
		String outputDate_P5 = wipLotTransDto.getWip_OutputDate_P5();
		if(inputDate_P5 != null) {
			wipLotTransDto.setWip_SaveDate_P5(saveDateCalc(inputDate_P5, outputDate_P5));
		}
	}
		
	//wipInputRollback
	public int wipInputRollback(WipLotTransDto wipLotTransDto) {
		//트랜스에서 해당하는 행을 삭제
		return wipLotManageDao.wipLotTransDeleteDao(wipLotTransDto);
	}
		
	//wipOutputRollback
	public int wipOutputRollback(WipLotTransDto wipLotTransDto) {
		//트랜스에서 해당하는 행을 업데이트로 시간을 null값으로 업데이트
		wipLotTransDto.setWip_OutputDate(null);
		
		return wipLotManageDao.wipLotTransUpdateDao(wipLotTransDto);			
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
		//일
		long day = totalMinutes/(60*24);
		//시간
		long hour = totalMinutes%(60*24)/60;
		//분
		long minute = totalMinutes%60;
		//문자
		String DHM = "";
		if(day != 0) {
			DHM += day+"일 ";
		}
		if(hour != 0) {
			DHM += hour+"시간 ";
		}
		DHM += minute+"분";
		return DHM;
	}
	
	//wipDefectSelect
	public List<DefectDto> wipDefectSelect(String wip_LotNo) {
		return wipLotManageDao.wipDefectSelectDao(wip_LotNo);
	}
	
	//wipDefectInsert
	public int wipDefectInsert(List<DefectDto> defectDtoList) {
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<defectDtoList.size();i++) {
						System.out.println(defectDtoList);
						//wipLotMaster
						wipLotMasterInsert(defectDtoList.get(i).getDefect_LotNo(), 3);
						
						wipLotManageDao.wipDefectInsertDao(defectDtoList.get(i));
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
