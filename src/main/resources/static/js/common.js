//숫자앞에 0을 추가하는 코드
function pluszero(n, length) {
	var zero = '';
	n = n.toString();
	if (n.length < length) {
		for (var i = 0; i < length - n.length; i++) {
			zero += '0';
			}
		}
	return zero + n;
}

function TableSetData(table,jsondata)
{
	table.clearSort();	
	table.setData(jsondata);
}

function UseBtn() {
	//버튼 활성화
	if ($('.BtnStatus').hasClass('unUseBtn')) {
		$('.BtnStatus').removeClass('unUseBtn');
	}
}

function ResetBtn() {
	//버튼 비활성화
	if (!$('.BtnStatus').hasClass('unUseBtn')) {
		$('.BtnStatus').addClass('unUseBtn');
	}
}

function fromRowToJson(row, valueList){
	
	var jsonData = new Object();
	valueList.forEach(function(item,index,arr2){
		jsonData[item] = row.getData()[item]
	})
	return jsonData;
}

function fromInputToJson(valueList){
	
	var jsonData = new Object();
	valueList.forEach(function(item,index,arr2){
		//타입이 checkbox일 경우
			if($('#'+item).attr("type") == "checkbox"){
				jsonData[item] = $("#"+item).is(":checked");
			}else{
				//그외
				jsonData[item] = $("#"+item).val();
			}
		
	})
	return jsonData;
}

//json데이터 modal로 데이터 넣기
function modalInputBox(json){
	for (var key in json){
		if($('#'+key)){
			//타입이 checkbox일 경우
			if($('#'+key).attr("type") == "checkbox"){
				if(json[key] == "true"){
					$('#'+key).prop("checked", true)	
				}else{
					$('#'+key).prop("checked", false)
				}
			}else{
				//그외
				$('#'+key).val(json[key]);
			}
		}
	}
}

//공통
function dtlSelectList(value){
	var dtl_arr = new Object();
	
	$.ajax({
		method : "GET",
		async: false,
		url : "dtlTrueSelect",
		data : {"NEW_TBL_CODE" : value},
		success : function(datas) {
			for(i=0;i<datas.length;i++){
				dtl_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
			}
		}
	});
	
	return dtl_arr;
}

function excel_download(table){
	table.download("xlsx", "data.xlsx", {sheetName : "Sheet1"});
}
function multi_Excel_download(table, jsonSheetData){
	table.download("xlsx", "data.xlsx", {sheets:jsonSheetData});
}

$(document).ready(function(){
	toastr.options = {
	  "positionClass": "toast-top-center",
	}
	//input에서 백스페이스 누르면 앞에있는 input 내용 사라짐 
	$('.clearInput').keydown(function(){
		if(event.keyCode==8 || event.keyCode==46) {
			$(this).prev().prev('input').val("");
		}
	});
})