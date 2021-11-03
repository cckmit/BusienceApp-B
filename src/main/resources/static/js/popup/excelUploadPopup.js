//주소에서 파라미터값을 가져오기 위한 기반
//(input_value,type_value,tab_value,search_value) 값이 들어옴
const url = new URL(window.location.href);
const urlParams = url.searchParams;

// 다른 곳 눌렀을 때 팝업창이 꺼짐
$(window).on("blur", function () {
    exitfrn();
});

$(document).keyup(function(e) {
	if (e.keyCode == 27) {
    	exitfrn()
    }
});

// 팝업 종료
function exitfrn() {
	//window.close();
}

var excelViewerTable = new Tabulator("#excelViewerTable", {
	layoutColumnsOnNewData: true,
	//Sub Total 색상
	height: "calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "전자(세금)계산서 종류(01:일반, 02:영세율)", field: "sales_tax_bill_kind", headerHozAlign: "center", hozAlign: "center" },
		{ title: "작성일자", field: "sales_tax_bill_Date", headerHozAlign: "center", hozAlign: "center" },
		{ title: "공급자 등록번호("-" 없이 입력)", field: "supplier_Regist_Num_1", headerHozAlign: "center", hozAlign: "center", formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "공급자 종사업장번호", field: "supplier_Regist_Num_2", headerHozAlign: "center" },
		{ title: "공급자 상호", field: "supplier_Regist_Title", headerHozAlign: "center" },
		{ title: "공급자 성명", field: "supplier_Regist_Name", headerHozAlign: "center" },
		{ title: "공급자 사업장주소", field: "supplier_Regist_Adr", headerHozAlign: "center" },
		{ title: "공급자 업태", field: "supplier_Regist_Business", headerHozAlign: "center" },
		{ title: "공급자 종목", field: "supplier_Regist_Event", headerHozAlign: "center", hozAlign: "right" },
		{ title: "공급자 이메일", field: "supplier_Regist_Email", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "공급받는자 등록번호("-" 없이 입력)", field: "supplier_Recipent_Num_1", headerHozAlign: "center" },
		{ title: "공급받는자 종사업자번호", field: "supplier_Recipent_Num2", headerHozAlign: "center" },
		{ title: "공급받는자 상호", field: "supplier_Recipent_Title", headerHozAlign: "center" },
		{ title: "공급받는자 성명", field: "supplier_Recipent_Name", headerHozAlign: "center", hozAlign: "right" },
		{ title: "공급받는자 사업장주소", field: "supplier_Recipent_Adr", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "공급받는자 업태", field: "supplier_Recipent_Business", headerHozAlign: "center" },
		{ title: "공급받는자 종목", field: "supplier_Recipent_Event", headerHozAlign: "center" },
		{ title: "공급받는자 이메일1", field: "supplier_Recipent_Email_1", headerHozAlign: "center" },
		{ title: "공급받는자 이메일2", field: "supplier_Recipent_Email_2", headerHozAlign: "center", hozAlign: "right" },
		{ title: "공급가액", field: "supply_Value", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "세액", field: "tax_Amount", headerHozAlign: "center" },
		{ title: "비고", field: "remarks", headerHozAlign: "center" },
		{ title: "일자1(2자리,작성년월 제외)", field: "date_1", headerHozAlign: "center" },
		{ title: "품목1", field: "item_1", headerHozAlign: "center", hozAlign: "right" },
		{ title: "규격1", field: "standard_1", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "수량1", field: "qty_1", headerHozAlign: "center" },
		{ title: "단가1", field: "unit_Price_1", headerHozAlign: "center" },
		{ title: "공급가액1", field: "supply_Value_1", headerHozAlign: "center" },
		{ title: "세액1", field: "tax_Amount_1", headerHozAlign: "center", hozAlign: "right" },
		{ title: "품목비고1", field: "item_Remarks_1", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "일자2(2자리,작성년월 제외)", field: "date_2", headerHozAlign: "center" },
		{ title: "품목2", field: "item_2", headerHozAlign: "center", hozAlign: "right" },
		{ title: "규격2", field: "standard_2", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "수량2", field: "qty_2", headerHozAlign: "center" },
		{ title: "단가2", field: "unit_Price_2", headerHozAlign: "center" },
		{ title: "공급가액2", field: "supply_Value_2", headerHozAlign: "center" },
		{ title: "세액2", field: "tax_Amount_2", headerHozAlign: "center", hozAlign: "right" },
		{ title: "품목비고2", field: "item_Remarks_2", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "일자3(2자리,작성년월 제외)", field: "date_3", headerHozAlign: "center" },
		{ title: "품목3", field: "item_3", headerHozAlign: "center", hozAlign: "right" },
		{ title: "규격3", field: "standard_3", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "수량3", field: "qty_3", headerHozAlign: "center" },
		{ title: "단가3", field: "unit_Price_3", headerHozAlign: "center" },
		{ title: "공급가액3", field: "supply_Value_3", headerHozAlign: "center" },
		{ title: "세액3", field: "tax_Amount_3", headerHozAlign: "center", hozAlign: "right" },
		{ title: "품목비고3", field: "item_Remarks_3", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "일자4(2자리,작성년월 제외)", field: "date_4", headerHozAlign: "center" },
		{ title: "품목4", field: "item_4", headerHozAlign: "center", hozAlign: "right" },
		{ title: "규격4", field: "standard_4", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "수량4", field: "qty_4", headerHozAlign: "center" },
		{ title: "단가4", field: "unit_Price_4", headerHozAlign: "center" },
		{ title: "공급가액4", field: "supply_Value_4", headerHozAlign: "center" },
		{ title: "세액4", field: "tax_Amount_4", headerHozAlign: "center", hozAlign: "right" },
		{ title: "품목비고4", field: "item_Remarks_4", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "현금", field: "cash", headerHozAlign: "center" },
		{ title: "수표", field: "check", headerHozAlign: "center" },
		{ title: "어음", field: "note", headerHozAlign: "center" },
		{ title: "외상미수금", field: "accounts_Receivable", headerHozAlign: "center", hozAlign: "right" },
		{ title: "영수(01),청구(02)", field: "classification", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } }
	],
});

function list_select(row){
	if(urlParams.get('type_value') == 'input'){
		//클래스 이름+tab_value로 데이터를 보낸다.
		$(opener.document).find('.Client_Code'+urlParams.get('tab_value')).val(row.getData().cus_Code)
		$(opener.document).find('.Client_Name'+urlParams.get('tab_value')).val(row.getData().cus_Name)
		
	}else if(urlParams.get('type_value') == 'grid'){
		//코드 네임
		window.opener.customer_gridInit(row.getData().cus_Code,row.getData().cus_Name);
	}	
	exitfrn()
}

function search(){
	excelViewerTable.setData("customerPopupSelect",
		{cus_Word:$('#Cus_Word').val(),search_value:urlParams.get('search_value')})

}

$('#searchBtn').click(function(){
	search()
})

$("#excelViewerTable").keypress(function(e){
	if(e.keyCode == 13){
		list_select(excelViewerTable.getSelectedRows()[0]);
	}
})

$("#Cus_Word").keypress(function(e){
	if(e.keyCode == 13){
		search();
	}
})

$(document).ready(function(){
	// 팝업창이 뜨면 데이터 받음
	$('#Cus_Word').val(urlParams.get('input_value'));
	
	// 팝업이 뜨자마자 바로 검색
	search();
})