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
	window.close();
}

var customerPopupTable = new Tabulator("#customerPopupTable", {
	layoutColumnsOnNewData : true,
	keybindings:{
        "navUp" : "38",
		"navDown" : "40"
    },
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
		//팝업창 검색란으로 값이 들어감
		$('#Cus_Word').val(row.getData().cus_Code);
	},
	rowDblClick:function(e, row){
		//e - the click event object
		//row - row component
		list_select(row)
	},
	height:"100%",
	columns:[
		{title:"", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{title:"거래처코드", field:"cus_Code", headerHozAlign:"center"},
		{title:"거래처명", field:"cus_Name", headerHozAlign:"center"}]
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
	customerPopupTable.setData("customerPopupSelect",
		{cus_Word:$('#Cus_Word').val(),search_value:urlParams.get('search_value')})
	.then(function(){
		if(customerPopupTable.getDataCount()>0){
			customerPopupTable.getRows()[0].select();
			$("#customerPopupTable").focus();
		}
	});
}

$('#searchBtn').click(function(){
	search()
})

$("#customerPopupTable").keypress(function(e){
	if(e.keyCode == 13){
		list_select(customerPopupTable.getSelectedRows()[0]);
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