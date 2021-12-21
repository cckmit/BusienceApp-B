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

function SubmenuSelector(id, highlight)  {
	try{
		document.getElementById("subPages"+id+"a").setAttribute('class', 'active');
		document.getElementById("subPages"+id+"a").setAttribute('aria-expanded', 'true');
		document.getElementById("subPages"+id).setAttribute('class', 'collapse in');
		document.getElementById("subPages"+id).setAttribute('aria-expanded', 'true');
		document.getElementById(id+highlight).setAttribute('class', 'active');	
	}catch(e){
		console.log("사용자 메뉴에 페이지 없음 : "+e);
	}
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

//공통
function dtlTrueSelect(value){
	var dtl_arr
	
	$.ajax({
		method: "GET",
		async: false,
		url: "dtlTrueSelect",
		data: {"NEW_TBL_CODE" : value},
		success: function(result) {
			dtl_arr = result
		}
	});
	
	return dtl_arr;
}


$(document).ready(function(){
	//input에서 백스페이스 누르면 앞에있는 input 내용 사라짐 
	$('.clearInput').keydown(function(){
		if(event.keyCode==8 || event.keyCode==46) {
			$(this).prev().prev('input').val("");
		}
	});
})