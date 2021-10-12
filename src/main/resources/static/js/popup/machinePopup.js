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

var machinePopupTable = new Tabulator("#machinePopupTable", {
	layoutColumnsOnNewData : true,
	keybindings:{
        "navUp" : "38",
		"navDown" : "40"
    }, 
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
		//팝업창 검색란으로 값이 들어감
		$('#Machine_Word').val(row.getData().equipment_INFO_CODE);
	},
	rowDblClick:function(e, row){
		//e - the click event object
		//row - row component
		list_select(row);
	},
	height:"100%",
    columns:[    	
		{title:"", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{title:"설비코드", field:"equipment_INFO_CODE", headerHozAlign:"center"},
		{title:"설비명", field:"equipment_INFO_NAME", headerHozAlign:"center"}]
});

function list_select(row){
	if(urlParams.get('type_value') == 'input'){
		//클래스 이름+tab_value로 데이터를 보낸다.
		$(opener.document).find('.Machine_Code'+urlParams.get('tab_value')).val(row.getData().equipment_INFO_CODE)
		$(opener.document).find('.Machine_Name'+urlParams.get('tab_value')).val(row.getData().equipment_INFO_NAME)
		
	}else if(urlParams.get('type_value') == 'grid'){
		//함수로 데이터를 보낸다.
		window.opener.machine_gridInit(row.getData().equipment_INFO_CODE,row.getData().equipment_INFO_NAME);
	}
	exitfrn();
}

//검색
function search() {
	machinePopupTable.setData("machinePopupSelect",
		{machine_Word:$('#Machine_Word').val(),search_value:urlParams.get('search_value')})
	.then(function(){
		if(machinePopupTable.getDataCount()>0){
			machinePopupTable.getRows()[0].select();
			$("#machinePopupTable").focus();
		}
	});
}

$("#machinePopupTable").keypress(function(e){
	if(e.keyCode == 13){
		list_select(machinePopupTable.getSelectedRows()[0]);
	}
})

$("#Cus_Word").keypress(function(e){
	if(e.keyCode == 13){
		search();
	}
})

$(document).ready(function(){
	// 팝업창이 뜨면 데이터 받음
	$('#Machine_Word').val(urlParams.get('input_value'));
	
	// 팝업이 뜨자마자 바로 검색
	search();
})