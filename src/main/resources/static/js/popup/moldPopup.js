//주소에서 파라미터값을 가져오기 위한 기반
//(input_value,type_value,tab_value,search_value) 값이 들어옴
const url = new URL(window.location.href);
const urlParams = url.searchParams;

// 다른 곳 눌렀을 때 팝업창이 꺼짐
$(window).on("blur", function () {
    exitfrn()
});

$(document).keyup(function(e) {
	if (e.keyCode == 27) {
    	exitfrn()
    }
});

// 팝업 종료
function exitfrn() {
	window.close();
}

var moldPopupTable = new Tabulator("#moldPopupTable", {
	layoutColumnsOnNewData : true,
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
		//팝업창 검색란으로 값이 들어감
		$('#Mold_Word').val(row.getData().mold_INFO_NO);
	},
	rowDblClick:function(e, row){
		//e - the click event object
		//row - row component
		list_select(row)
	},
    height:"100%",
	columns:[
		{title:"", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{title:"금형No", field:"mold_INFO_NO", headerHozAlign:"center"},
		{title:"금형명", field:"mold_INFO_NAME", headerHozAlign:"center"}]
});

function list_select(row){
	if(urlParams.get('type_value') == 'input'){
		//클래스 이름+tab_value로 데이터를 보낸다.
		$(opener.document).find('.Mold_Code'+urlParams.get('tab_value')).val(row.getData().mold_INFO_NO)
		$(opener.document).find('.Mold_Name'+urlParams.get('tab_value')).val(row.getData().mold_INFO_NAME)
		
	}else if(urlParams.get('type_value') == 'grid'){
		//함수로 데이터를 보낸다.
		window.opener.mold_gridInit()
	}
	exitfrn();
}

//검색
function search() {
	moldPopupSelect.setData("moldPopupSelect", {mold_Word:$('#Cus_Word').val()})
	.then(function(){
		if(moldPopupSelect.getDataCount()>0){
			moldPopupSelect.getRows()[0].select();
			$("#moldPopupSelect").focus();
		}
	});
}

$('#searchBtn').click(function(){
	search()
})

$("#moldPopupSelect").keypress(function(e){
	if(e.keyCode == 13){
		list_select(moldPopupSelect.getSelectedRows()[0]);
	}
})

$("#Mold_Word").keypress(function(e){
	if(e.keyCode == 13){
		search();
	}
})

$(document).ready(function(){
	// 팝업창이 뜨면 데이터 받음
	$('#Mold_Word').val(urlParams.get('input_value'));
	
	// 팝업이 뜨자마자 바로 검색
	search();
})