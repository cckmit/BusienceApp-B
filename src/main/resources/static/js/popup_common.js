//거래처 팝업창 	(입력값,input or grid, 탭기능이 있을떄 1부터 없으면 '',검색조건제약(in,out,all))
function customerPopup(input_value,type_value,tab_value,search_value){
	//창의 주소
	var url = "customerPopup?input_value="+input_value
							+"&type_value="+type_value
							+"&tab_value="+tab_value
							+"&search_value="+search_value;
	//창의 이름
	var name = "customerPopup";
	//창의 css	
	var option = "width = 500, height = 500, top = 100, left = 200, location = no"
	
	openWin = window.open(url, name, option);
}
//제품팝업창		(입력값,input or grid, 탭기능이 있을떄 1부터 없으면 '',검색조건제약(all,material,sales,dtl_tbl 숫자코드))
function itemPopup(input_value,type_value,tab_value,search_value) {
	//제품명 팝업
	localStorage.setItem('PRODUCT_ITEM_NAME', input_value);
	//창의 주소
	var url = "itemPopup?input_value="+input_value
								+"&type_value="+type_value
								+"&tab_value="+tab_value
								+"&search_value="+search_value;
	//창의 이름
	var name = "itemPopup";
	//창의 css
	var option = "width = 1000, height = 600, top = 100, left = 200, location = no"
	openWin = window.open(url, name, option);
}

//설비 팝업창 			(입력값,input or grid, 탭기능이 있을떄 1부터 없으면 '')
function machinePopup(input_value,type_value,tab_value,search_value){
	//창의 주소
	var url = "machinePopup?input_value="+input_value
							+"&type_value="+type_value
							+"&tab_value="+tab_value
							+"&search_value="+search_value;
	//창의 이름
	var name = "machinePopup";
	//창의 css	
	var option = "width = 500, height = 500, top = 100, left = 200, location = no"
	
	openWin = window.open(url, name, option);
}

//설비 팝업창 			(입력값,input or grid, 탭기능이 있을떄 1부터 없으면 '')
function defectPopup(input_value,type_value,tab_value){
	//창의 주소
	var url = "defectPopup?input_value="+input_value
							+"&type_value="+type_value
							+"&tab_value="+tab_value;
	//창의 이름
	var name = "defectPopup";
	//창의 css	
	var option = "width = 500, height = 500, top = 100, left = 200, location = no"
	
	openWin = window.open(url, name, option);
}

//금형 팝업창 			(입력값,input or grid, 탭기능이 있을떄 1부터 없으면 '')
function moldPopup(input_value,type_value,tab_value){
	//창의 주소
	var url = "moldPopup?input_value="+input_value
						+"&type_value="+type_value
						+"&tab_value="+tab_value;
	//창의 이름
	var name = "moldPopup";
	//창의 css	
	var option = "width = 500, height = 500, top = 100, left = 200, location = no"
	
	openWin = window.open(url, name, option);
}

//엑셀 업로드 팝업창 		(해당 년월)
function hometaxApiPopup(ym){
	//창의 주소
	var url = "hometaxApiPopup?ym="+ym;
	//창의 이름
	var name = "hometaxApiPopup";
	//창의 css	
	var option = "width = 1400, height = 700, top = 150, left = 250, location = no"
	
	openWin = window.open(url, name, option);
}

//엑셀 업로드 팝업창 		(해당 년월)
function paldangPackagingPopup(){
	//창의 주소
	var url = "paldangPackagingPopup";
	//창의 이름
	var name = "paldangPackagingPopup";
	//창의 css	
	var option = "width = 1000, height = 500, top = 100, left = 200, location = no"
	
	openWin = window.open(url, name, option);
}