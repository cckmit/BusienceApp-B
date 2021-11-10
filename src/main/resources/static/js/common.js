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

//브라우저 강제종료시 로그아웃 되게 설정
window.onbeforeunload = function(e){
	closePage(e)
}
// 윈도우 창을 닫을 때 로그아웃 처리
function closePage( event ){
	if( event.clientY < 0 ){
		$.ajax({
			method: "post",
			url: "/logout",
			beforeSend: function (xhr) {
	           var header = $("meta[name='_csrf_header']").attr("content");
	           var token = $("meta[name='_csrf']").attr("content");
	           xhr.setRequestHeader(header, token);
			}
		});
	}
	
	document.onkeydown = function(event) {
		// 새로고침 방지 ( Ctrl+R, Ctrl+N, F5 )
	    if ( event.ctrlKey == true && ( event.keyCode == 78 || event.keyCode == 82 ) || event.keyCode == 116) {
	         event.keyCode = 0;
	         event.cancelBubble = true;
	         event.returnValue = false;
	    } 
	
	    // 창 닫기( Alt+F4 ) 방지 
	    if ( event.keyCode == 115) { // F4 눌렀을 시
	      // 로그아웃 처리
	    } 
	
	    // 윈도우 창이 닫힐 경우
	    if (event.keyCode == 505) { 
	       alert(document.body.onBeforeUnload);
		}
	}
}
$(document).ready(function(){
	//input에서 백스페이스 누르면 앞에있는 input 내용 사라짐 
	$('.clearInput').keydown(function(){
		if(event.keyCode==8 || event.keyCode==46) {
			$(this).prev().prev('input').val("");
		}
	});
})