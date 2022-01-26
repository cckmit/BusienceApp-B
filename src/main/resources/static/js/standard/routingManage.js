//matInputSub 커스텀 기능설정
var RM_InputEditor = function(cell, onRendered, success, cancel, editorParams){
    //cell - 편집 가능한 셀의 셀 구성 요소
    //onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
    //success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
    //cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
    //editorParams - editorParams 열 정의 속성에 전달 된 params 객체

    //create 및 style editor
    var RM_input = document.createElement("input");

    RM_input.setAttribute("type", "text");

    //입력 생성 및 스타일 지정
    RM_input.style.padding = "3px";
    RM_input.style.width = "100%";
    RM_input.style.boxSizing = "border-box";

    //편집기의 값을 셀의 현재 값으로 설정
	if(cell.getValue() == undefined){
		RM_input.value = "";
	}else{
		RM_input.value = cell.getValue();
	}

    //에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
    onRendered(function(){
        RM_input.focus();
		RM_input.select();
        RM_input.style.css = "100%";
    });

    //값이 설정되면 업데이트 할 셀 트리거
    function onChange(){
        success(RM_input.value);
    }
    
	//바꼈을때 블러됫을때 함수 발동
    RM_input.addEventListener("change", onChange);
    RM_input.addEventListener("blur", onChange);

    //키버튼 이벤트
    RM_input.addEventListener("keydown", function (e) {
		//품목코드 팝업창
		if(e.keyCode == 13){
			cell.nav().next();
		}
    });
    //반환
    return RM_input;
};


var routingManageTable = new Tabulator("#routingManageTable",	{
	headerFilterPlaceholder: null,
	layoutColumnsOnNewData : true,
	height: "calc(100% - 175px)",
	ajaxURL:"routingManageRest/routingManageSelect",
    ajaxConfig:"get",
    ajaxContentType:"json",
	rowClick: function(e, row) {
		row.getTable().deselectRow();
		row.select();
	},
	columns: [
		{ title: "순번",	field: "rownum", hozAlign: "center", formatter:"rownum"},
		{ title: "품목분류", field: "item_Clsfc_1", headerHozAlign: "center"},
		{ title: "품목분류명", field: "item_Clsfc_1_Name", headerHozAlign: "center", editor: RM_InputEditor},
		{ title: "공정", headerHozAlign:"center",
			
			columns:[
				{ title: "1", field: "routing_1", headerHozAlign: "center", editor: RM_InputEditor},
				{ title: "2", field: "routing_2", headerHozAlign: "center", editor: RM_InputEditor},
				{ title: "3", field: "routing_3", headerHozAlign: "center", editor: RM_InputEditor},
				{ title: "4", field: "routing_4", headerHozAlign: "center", editor: RM_InputEditor},
				{ title: "5", field: "routing_5", headerHozAlign: "center", editor: RM_InputEditor}
			]
		},
		{ title: "사용여부", field: "use_Status", headerHozAlign: "center", hozAlign: "center",
			formatter: "tickCross", editor:'select',  editorParams:{true : "사용", false : "미사용"}},
	]
});

$("#routingUpdateBtn").click(function(){
	routingUpdate();
})

function routingUpdate(){
	var datas = routingManageTable.getData();
	
	$.ajax({
		method: "post",
		url: "routingManageRest/routingManageUpdate",
		data : JSON.stringify(datas),
		contentType:'application/json',
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data) {
				alert("저장 되었습니다.");
				routingManageTable.replaceData();
				
			}else{
				alert("오류가 발생했습니다.");
			}
		}
	});
}
