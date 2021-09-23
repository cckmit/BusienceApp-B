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

var defectPopupTable = new Tabulator("#defectPopupTable", {
	layoutColumnsOnNewData : true,
	keybindings:{
        "navUp" : "38",
		"navDown" : "40"
    },
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
		//팝업창 검색란으로 값이 들어감
		$('#Defect_Word').val(row.getData().defect_CODE);
    },
    rowDblClick:function(e, row){
		//e - the click event object
		//row - row component
		list_select(row)
    },
	height:"100%",
 	columns:[ 
 	{title:"", field:"rownum", formatter:"rownum", hozAlign:"center"},
	{title:"불량코드", field:"defect_CODE", headerHozAlign:"center"},
	{title:"불량이름", field:"defect_NAME", headerHozAlign:"center"}]
});

function list_select(row){
	if(urlParams.get('type_value') == 'input'){
		//클래스 이름+tab_value로 데이터를 보낸다.
		$(opener.document).find('.Defect_Code'+urlParams.get('tab_value')).val(row.getData().defect_CODE)
		$(opener.document).find('.Defect_Name'+urlParams.get('tab_value')).val(row.getData().defect_NAME)
		
	}else if(urlParams.get('type_value') == 'grid'){
		//코드 네임
		window.opener.defect_gridInit();
	}	
	exitfrn()
}

//검색
function search(){
	defectPopupTable.setData("defectPopupSelect",
		{defect_Word:$('#Defect_Word').val()})
	.then(function(){
		if(defectPopupTable.getDataCount()>0){
			defectPopupTable.getRows()[0].select();
			$("#defectPopupTable").focus();
		}
	});
}

$('#searchBtn').click(function(){
	search()
})
$("#defectPopupTable").keypress(function(e){
	if(e.keyCode == 13){
		list_select(defectPopupTable.getSelectedRows()[0]);
	}
})

$("#Defect_Word").keypress(function(e){
	if(e.keyCode == 13){
		search();
	}
})

$(document).ready(function(){
	// 팝업창이 뜨면 데이터 받음
	$('#Defect_Word').val(urlParams.get('input_value'));
	
	// 팝업이 뜨자마자 바로 검색
	search();
})