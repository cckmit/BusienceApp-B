package com.busience.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dao.HometaxApiDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.HometaxApiDto;
import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dto.Sales_OutMat_tbl;
import com.busience.standard.dao.CustomerDao;
import com.busience.standard.dao.ItemDao;
import com.busience.standard.dto.CustomerDto;

@Service
public class HometaxApiService {
	
	@Autowired
	HometaxApiDao hometaxApiDao;
	
	@Autowired
	DtlDao dtlDao;
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	ItemDao itemdao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	// 데이터 저장
	public int hometaxApiDataSave(SearchDto searchDto) {
		
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					//테이블 내용 삭제
					hometaxApiDao.deleteHometaxApiDao();
					
					List<Sales_OutMat_tbl> DetailList = new ArrayList<Sales_OutMat_tbl>();
					List<DtlDto> myCompany = new ArrayList<DtlDto>();
					List<DtlDto> hometaxOption = new ArrayList<DtlDto>();
					
					CustomerDto customerDto = new CustomerDto();
					
					String customer = null;
					String dateTypeChange = null;
					int Quantity = 0;
					int price = 0;
					int taxAmount = 0;
					double taxRate = 0;
					
					//세금계산서 옵션
					hometaxOption = dtlDao.findByCode(40);
					//세금계산서 종류 (01:일반, 02:영세율)
					String tax_Invoice_Type = hometaxOption.get(0).getCHILD_TBL_RMARK();
					//단가 세금형태 (01:포함, 02:미포함)
					String taxType = hometaxOption.get(1).getCHILD_TBL_RMARK();
					//세금계산서 분류 (01:영수, 02:청구)
					String classification = hometaxOption.get(2).getCHILD_TBL_RMARK();
					
					if(tax_Invoice_Type.equals("01")) {
						if(taxType.equals("01")) {
							taxRate = 1/11.0;
						}else {
							taxRate = 1/10.0;
						}
					}
					
					//거래한 거래처리스트
					String[] customerList = searchDto.getClientCodeArr();
					
					for(int i=0;i<customerList.length;i++) {
						HometaxApiDto hometaxApiDto = new HometaxApiDto();
						
						//세금계산서 종류
						hometaxApiDto.setTax_Invoice_Type(tax_Invoice_Type);
						
						//거래처
						customer = customerList[i];
						searchDto.setClientCode(customer);
						customerDto = customerDao.selectCustomerDao(customer);
						
						//우리회사
						myCompany = dtlDao.findByCode(13);
						//공급자 등록번호
						hometaxApiDto.setSupplier_Registration_No(myCompany.get(0).getCHILD_TBL_RMARK().replaceAll("-", ""));
						//공급자 상호
						hometaxApiDto.setSupplier_Company_Name(myCompany.get(1).getCHILD_TBL_RMARK());
						//공급자 성명
						hometaxApiDto.setSupplier_Name(myCompany.get(2).getCHILD_TBL_RMARK());
						//공급자 사업장주소
						hometaxApiDto.setSupplier_Business_Address(myCompany.get(3).getCHILD_TBL_RMARK());
						//공급자 이메일
						hometaxApiDto.setSupplier_Email(myCompany.get(5).getCHILD_TBL_RMARK());
						
						//공급받는자 등록번호
						hometaxApiDto.setReceiver_Registration_No(customerDto.getCus_Rgstr_Nr().replaceAll("-", ""));
						//공급받는자 상호
						hometaxApiDto.setReceiver_Company_Name(customerDto.getCus_Co());
						//공급받는자 성명
						hometaxApiDto.setReceiver_Name(customerDto.getCus_Mng());
						//공급받는자 사업장주소
						hometaxApiDto.setReceiver_Business_Address(customerDto.getCus_Adr());
						//공급받는자 이메일
						hometaxApiDto.setReceiver_Email_1(customerDto.getCus_Mng_Email());

						//거래처별 거래내역						
						DetailList = hometaxApiDao.transactionDetailListDao(searchDto);
						
						//4개까지 있을경우 합계행 +1 하나하나 대입
						
						//공급가액
						price = DetailList.get(DetailList.size()-1).getSales_OutMat_Price();
						taxAmount = (int) (price*taxRate);
						hometaxApiDto.setSupply_Value(price - taxAmount);
						//세액
						hometaxApiDto.setTax_Amount(taxAmount);
						
						//일자1
						dateTypeChange = DetailList.get(0).getSales_OutMat_Date().substring(8,10);
						hometaxApiDto.setDate_1(dateTypeChange);
						//품목1
						hometaxApiDto.setItem_1(DetailList.get(0).getSales_OutMat_Name());
						//규격1
						hometaxApiDto.setStandard_1(DetailList.get(0).getSales_OutMat_STND_1());
						//수량1
						hometaxApiDto.setQuantity_1(DetailList.get(0).getSales_OutMat_Qty());
						//단가1
						hometaxApiDto.setUnit_Price_1(DetailList.get(0).getSales_OutMat_Unit_Price());
						//공급가액1
						price = DetailList.get(0).getSales_OutMat_Price();
						taxAmount = (int) (price*taxRate);
						hometaxApiDto.setSupply_Value_1(price - taxAmount);
						//세액1
						hometaxApiDto.setTax_Amount_1(taxAmount);
						
						if(DetailList.size() >= 3) {
							//일자2
							dateTypeChange = DetailList.get(1).getSales_OutMat_Date().substring(8,10);
							hometaxApiDto.setDate_2(dateTypeChange);
							//품목2
							hometaxApiDto.setItem_2(DetailList.get(1).getSales_OutMat_Name());
							//규격2
							hometaxApiDto.setStandard_2(DetailList.get(1).getSales_OutMat_STND_1());
							//수량2
							hometaxApiDto.setQuantity_2(DetailList.get(1).getSales_OutMat_Qty());
							//단가2
							hometaxApiDto.setUnit_Price_2(DetailList.get(1).getSales_OutMat_Unit_Price());
							//공급가액2
							price = DetailList.get(1).getSales_OutMat_Price();
							taxAmount = (int) (price*taxRate);
							//세액2
							hometaxApiDto.setSupply_Value_2(price - taxAmount);
							hometaxApiDto.setTax_Amount_2(taxAmount);
						}
						if(DetailList.size() >= 4) {
							//일자3
							dateTypeChange = DetailList.get(2).getSales_OutMat_Date().substring(8,10);
							hometaxApiDto.setDate_3(dateTypeChange);
							//품목3
							hometaxApiDto.setItem_3(DetailList.get(2).getSales_OutMat_Name());
							//규격3
							hometaxApiDto.setStandard_3(DetailList.get(2).getSales_OutMat_STND_1());
							//수량3
							hometaxApiDto.setQuantity_3(DetailList.get(2).getSales_OutMat_Qty());
							//단가3
							hometaxApiDto.setUnit_Price_3(DetailList.get(2).getSales_OutMat_Unit_Price());
							//공급가액
							price = DetailList.get(2).getSales_OutMat_Price();
							taxAmount = (int) (price*taxRate);
							hometaxApiDto.setSupply_Value_3(price - taxAmount);
							//세액
							hometaxApiDto.setTax_Amount_3(taxAmount);
						}

						//4개 이상 있을경우 4번째 이후는 합산
						if(DetailList.size()<6) {
							if(DetailList.size() >= 5) {
								//일자4
								dateTypeChange = DetailList.get(3).getSales_OutMat_Date().substring(8,10);
								hometaxApiDto.setDate_4(dateTypeChange);
								//품목4
								hometaxApiDto.setItem_4(DetailList.get(3).getSales_OutMat_Name());
								//규격4
								hometaxApiDto.setStandard_4(DetailList.get(3).getSales_OutMat_STND_1());
								//수량4
								hometaxApiDto.setQuantity_4(DetailList.get(3).getSales_OutMat_Qty());
								//단가4
								hometaxApiDto.setUnit_Price_4(DetailList.get(3).getSales_OutMat_Unit_Price());
								//공급가액4
								price = DetailList.get(3).getSales_OutMat_Price();
								taxAmount = (int) (price*taxRate);
								hometaxApiDto.setSupply_Value_4(price - taxAmount);
								//세액4
								hometaxApiDto.setTax_Amount_4(taxAmount);
							}
						}else {
							//일자4
							dateTypeChange = DetailList.get(3).getSales_OutMat_Date().substring(8,10);
							hometaxApiDto.setDate_4(dateTypeChange);
							//품목4
							hometaxApiDto.setItem_4("기타");
							//여러품목이므로 규격 X
							
							for(int j=3;j<DetailList.size()-1;j++) {
								//수량4 합산
								Quantity += DetailList.get(j).getSales_OutMat_Qty();
								//공급가액4 합산
								price += DetailList.get(j).getSales_OutMat_Price();
							}
							//수량4
							hometaxApiDto.setQuantity_4(Quantity);
							//단가4
							hometaxApiDto.setUnit_Price_4(price/Quantity);							
							//공급가액4
							taxAmount = (int) (price*taxRate);
							hometaxApiDto.setSupply_Value_4(price - taxAmount);
							//세액
							hometaxApiDto.setTax_Amount_4(taxAmount);
						}						

						//분류
						hometaxApiDto.setClassification(classification);
						
						//거래처별로 저장
						hometaxApiDao.insertTaxInvoiceDao(hometaxApiDto);
					}
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public List<HometaxApiDto> hometaxApiDataSearch(){
		return hometaxApiDao.selectTaxInvoiceDao();
	};
}
