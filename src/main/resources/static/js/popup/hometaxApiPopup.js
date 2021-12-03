$(document).keyup(function(e) {
	if (e.keyCode == 27) {
    	exitfrn();
    }
});

// 팝업 종료
function exitfrn() {
	window.close();
}

//define custom accessor
var nullToEmptyString = function(value, data, type, params, column){
	//value - original value of the cell
	//data - the data for the row
	//type - the type of access occurring  (data|download|clipboard)
	//params - the accessorParams object passed from the column definition
	//column - column component for the column this accessor is bound to

	return value || ""; //return the new value for the cell data.
}

var excelViewerTable = new Tabulator("#excelViewerTable", {
	layoutColumnsOnNewData: true,
	height: "100%",
	ajaxURL:"/hometaxApiPopupSelect",
    ajaxConfig:"get",
    ajaxContentType:"json",
	columns: [
		{ title: "전자(세금)계산서 종류(01:일반, 02:영세율)", field: "tax_Invoice_Type", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "작성일자", field: "tax_Invoice_Date", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급자 등록번호('-' 없이 입력)", field: "supplier_Registration_No", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급자 종사업장번호", field: "supplier_Registration_No_2", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급자 상호", field: "supplier_Company_Name", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급자 성명", field: "supplier_Name", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급자 사업장주소", field: "supplier_Business_Address", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급자 업태", field: "supplier_Business_Type", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급자 종목", field: "supplier_Business_Item", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급자 이메일", field: "supplier_Email", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급받는자 등록번호('-' 없이 입력)", field: "receiver_Registration_No", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급받는자 종사업자번호", field: "receiver_Registration_No_2", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급받는자 상호", field: "receiver_Company_Name", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급받는자 성명", field: "receiver_Name", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급받는자 사업장주소", field: "receiver_Business_Address", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급받는자 업태", field: "receiver_Business_Type", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급받는자 종목", field: "receiver_Business_Item", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급받는자 이메일1", field: "receiver_Email_1", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급받는자 이메일2", field: "receiver_Email_2", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "공급가액", field: "supply_Value", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false}, accessorDownload:nullToEmptyString},
		{ title: "세액", field: "tax_Amount", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "비고", field: "remarks", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "일자1(2자리,작성년월 제외)", field: "date_1", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "품목1", field: "item_1", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "규격1", field: "standard_1", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "수량1", field: "quantity_1", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "단가1", field: "unit_Price_1", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "공급가액1", field: "supply_Value_1", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "세액1", field: "tax_Amount_1", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "품목비고1", field: "item_Remarks_1", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "일자2(2자리,작성년월 제외)", field: "date_2", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "품목2", field: "item_2", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "규격2", field: "standard_2", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "수량2", field: "quantity_2", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "단가2", field: "unit_Price_2", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "공급가액2", field: "supply_Value_2", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "세액2", field: "tax_Amount_2", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "품목비고2", field: "item_Remarks_2", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "일자3(2자리,작성년월 제외)", field: "date_3", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "품목3", field: "item_3", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "규격3", field: "standard_3", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "수량3", field: "quantity_3", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "단가3", field: "unit_Price_3", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "공급가액3", field: "supply_Value_3", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "세액3", field: "tax_Amount_3", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "품목비고3", field: "item_Remarks_3", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "일자4(2자리,작성년월 제외)", field: "date_4", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "품목4", field: "item_4", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "규격4", field: "standard_4", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "수량4", field: "quantity_4", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "단가4", field: "unit_Price_4", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "공급가액4", field: "supply_Value_4", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "세액4", field: "tax_Amount_4", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "품목비고4", field: "item_Remarks_4", headerHozAlign: "center", accessorDownload:nullToEmptyString},
		{ title: "현금", field: "cash", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "수표", field: "accounts_Check", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "어음", field: "note", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "외상미수금", field: "accounts_Receivable", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false }, accessorDownload:nullToEmptyString},
		{ title: "영수(01),청구(02)", field: "classification", headerHozAlign: "center", accessorDownload:nullToEmptyString}
	]
});

//trigger download of data.xlsx file
$("#download-xlsx").click(function(){
	var originData = excelViewerTable.getData();
	for(let i=0;i<5;i++){
		excelViewerTable.addRow({},true);
	}
    excelViewerTable.download("xlsx", "세금 계산서.xlsx", {sheetName:"세금 계산서"});
	
	excelViewerTable.setData(originData);
})
