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

$(document).ready(function(){
	//input에서 백스페이스 누르면 앞에있는 input 내용 사라짐 
	$('.clearInput').keydown(function(){
		if(event.keyCode==8 || event.keyCode==46) {
			$(this).prev().prev('input').val("");
		}
	});
})