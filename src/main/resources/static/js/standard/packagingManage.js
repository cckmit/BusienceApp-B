//커스텀 기능설정
var PM_InputEditor = function(cell, onRendered, success, cancel, editorParams) {
	//cell - 편집 가능한 셀의 셀 구성 요소
	//onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
	//success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
	//cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
	//editorParams - editorParams 열 정의 속성에 전달 된 params 객체

	//create 및 style editor
	var PM_Input = document.createElement("input");

	PM_Input.setAttribute("type", "text");

	//입력 생성 및 스타일 지정
	PM_Input.style.padding = "3px";
	PM_Input.style.width = "100%";
	PM_Input.style.boxSizing = "border-box";

	//편집기의 값을 셀의 현재 값으로 설정
	if (cell.getValue() == undefined) {
		PM_Input.value = "";
	} else {
		PM_Input.value = cell.getValue();
	}

	//에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
	onRendered(function() {
		PM_Input.focus();
		PM_Input.select();
		PM_Input.style.css = "100%";
	});

	//값이 설정되면 업데이트 할 셀 트리거
	function onChange() {
		success(PM_Input.value);
	}

	//바꼈을때 블러됫을때 함수 발동
	PM_Input.addEventListener("change", onChange);
	PM_Input.addEventListener("blur", onChange);

	//키버튼 이벤트
	PM_Input.addEventListener("keydown", function(e) {
		//코드 셀에서 백스페이스를 눌렀을경우 명이 사라지게함
		if (e.keyCode == 8) {
			if (cell.getField() == "packaging_Cus_Code") {
				cell.getRow().update({"packaging_Cus_Name":''})
			}
			if (cell.getField() == "packaging_ItemCode") {
				cell.getRow().update({"packaging_ItemName":''})
			}
		}
		if (e.keyCode == 13) {
			//거래처코드셀 체크
			if (cell.getField() == "packaging_Cus_Code") {
				//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
				$.ajax({
					method: "GET",
					url: "customer_check?Cus_Code=" + PM_Input.value,
					dataType: "json",
					success: function(data) {
						console.log("쿼리실행");
						if (data.length == 1) {
							//검색어와 일치하는값이 있는경우
							cell.getRow().update({
								"packaging_Cus_Code": data[0].cus_Code,
								"packaging_Cus_Name": data[0].cus_Name
							});
						} else {
							//검색어와 일치하는값이 없는경우, 팝업창
							customerPopup(PM_Input.value,'grid','','all');
						}
					}
				})
			}
			//품목코드셀체크
			if (cell.getField() == "packaging_ItemCode") {
				//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
				//쿼리실행
				$.ajax({
					method: "GET",
					url: "product_check?PRODUCT_ITEM_CODE=" + PM_Input.value,
					dataType: "json",
					success: function(data) {
						console.log("쿼리실행");
						if (data.length == 1) {
							//검색어와 일치하는값이 있는경우
							cell.getRow().update({
								"packaging_ItemCode": data[0].product_ITEM_CODE,
								"packaging_ItemName": data[0].product_ITEM_NAME
							})
						} else {
							//검색어와 일치하는값이 없는경우, 팝업창
							itemPopup(PM_Input.value,'grid','','all');
						}
					}
				})
			}
			//비율셀 체크
			if (cell.getField() == "packaging_Rate") {
				cell.getRow().select();
				//사용구분과 거래처구분을 넘긴다.
				cell.nav().next();
				cell.nav().next();
			}
		}
	});
	//반환
	return PM_Input;
};

//팝업창으로부터 특정 파라미터 값으로 데이터를 받는다 
function customer_gridInit(CCode, CName) {
	cellPos.getRow().update({
		"packaging_Cus_Code": CCode,
		"packaging_Cus_Name": CName
	});
	//선택 후 포커스 이동
	cellPos.getElement().focus();
}

//팝업창으로부터 특정 파라미터 값으로 데이터를 받는다 
function item_gridInit(PCode, PName, PSTND_1, PPrice) {
	cellPos.getRow().update({
		"packaging_ItemCode": PCode,
		"packaging_ItemName": PName
	})
	cellPos.getElement().focus();
}

var dtl_arr = dtlSelectList(28);

//셀 위치 저장
var cellPos = null;

var packagingManageTable = new Tabulator("#packagingManageTable",{	
	//페이징
	paginationAddRow : "table",
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "packagingManageRest/PM_Search",
	height: "calc(100% - 175px)",
	selectable:true,
	tabEndNewRow: true,
	keybindings:{
        "navNext" : "13"
    },
	rowClick:function(e,row){
		console.log(row.getElement());
	},
	rowAdded: function(row){
		if(packagingManageTable.getDataCount("selected")!=0){
			row.update(packagingManageTable.getData("selected")[0])
		}else{
			row.update({packaging_Cus_Status : '240',
						packaging_Cus_Name : '',
						packaging_ItemName : '',
						packaging_Min_Order_Qty : 0,
						packaging_Small_Unit : 0,
						packaging_Big_Box : 0,
						packaging_Rate : 0,
						packaging_Use_Status : true})
		}
		row.update({id:0})
		
		//페이지 이동(전체 행 수/페이지당 행 수)
		packagingManageTable.setPage(Math.ceil(packagingManageTable.getDataCount("active") / packagingManageTable.getPageSize()));
	
		//행이 추가되면 첫셀에 포커스
		do {
			setTimeout(function() {
				row.getCell("packaging_Cus_Code").edit();
			}, 100);
		}
		while (row.getData().packaging_Cus_Code === "undefined");
	},
	cellEditing: function(cell) {
		//셀위치 저장하여 포커싱부여
		cellPos = cell;
	},
	columns: [
		{formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false},
		{title: "순번", field: "id", formatter:"rownum", hozAlign: "center", headerHozAlign: "center"},
		{title: "거래처구분", field: "packaging_Cus_Status", headerFilter: true, headerFilterParams:dtl_arr, editor:"select", 
		formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if(dtl_arr[value] != null){
						value = dtl_arr[value];	
					}else{
						value = "";
					}
			    return value;
			},
			editorParams:{values:dtl_arr}},
		{title: "거래처코드", field: "packaging_Cus_Code", headerHozAlign: "center", headerFilter: "input", editor:PM_InputEditor},
		{title: "거래처명", field: "packaging_Cus_Name", headerHozAlign: "center", headerFilter: "input"},
		{title: "품목코드", field: "packaging_ItemCode", headerHozAlign: "center", headerFilter: "input", editor:PM_InputEditor},
		{title: "품목명", field: "packaging_ItemName", headerHozAlign: "center", headerFilter: "input"},
		{title: "라벨 Type", field: "packaging_Label_Type", headerHozAlign: "center", headerFilter: "input", editor:PM_InputEditor},
		{title: "소포장단위수", field: "packaging_Small_Unit", hozAlign: "right", headerHozAlign: "center", headerFilter: "input", editor:PM_InputEditor},
		{title: "대포장BOX수", field: "packaging_Big_Box", hozAlign: "right", headerHozAlign: "center", headerFilter: "input", editor:PM_InputEditor},
		{title: "최소발주량", field: "packaging_Min_Order_Qty", hozAlign: "right", headerHozAlign: "center", headerFilter: "input", editor:PM_InputEditor},
		{title: "비율", field: "packaging_Rate", hozAlign: "right", headerHozAlign: "center", headerFilter: "input", editor:PM_InputEditor,
			formatter:function(cell, formatterParams, onRendered){return cell.getValue()+" %"}},
		{title: "사용구분", field: "packaging_Use_Status", headerHozAlign: "center", headerFilter:"tickCross", hozAlign: "center",
			editor:true, formatter:"tickCross"},
		{title: "수정일자", field: "packaging_Modify_Date", headerHozAlign: "center", headerFilter: true,
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }},
		{title: "수정자", field: "packaging_Modifier", headerHozAlign: "center", headerFilter: true}
	]
});
console.log("변경")
//BIL_Search
function PM_Search(){
	$.ajax({
		method : "GET",
		dataType : "json",
		url : "packagingManageRest/PM_Search",
		success : function(result) {
			console.log(result)
			packagingManageTable.setData(result);
			ResetBtn();
		}
	});
}

$('#PM_SearchBtn').click(function(){
	PM_Search();
})

function PM_New(){
	if(packagingManageTable.getData("selected")[0] != null){
		packagingManageTable.setData([packagingManageTable.getData("selected")[0]]);
		packagingManageTable.selectRow();
		packagingManageTable.addRow();
	}else{
		packagingManageTable.clearData();
		packagingManageTable.addRow();
	}
	UseBtn();
}

$('#PM_NewBtn').click(function(){
	PM_New();
})

function PM_Add(){
	//목록의 구분, 거래처명, 제품명을 검사하여 입력하지 않았을 경우 추가 안됨
	for(i=0;i<packagingManageTable.getDataCount("active");i++){
		rowData = packagingManageTable.getData()[i];
		
		if(rowData.packaging_Cus_Name == '' || rowData.packaging_ItemName == ''){
			alert("작성중인 행이 있습니다.");
			return false;
		}
	}
	packagingManageTable.selectRow();
	packagingManageTable.addRow();
}

$('#PM_AddBtn').click(function(){
	PM_Add();
})

function PM_Save(){
	selectedRow = packagingManageTable.getData("selected");
	rowCount = packagingManageTable.getDataCount("selected");

	if(rowCount==0){
		alert("저장할 선택한 데이터가 없습니다.");
		return false;
	}
	//행추가할때 첫번째행을 복사하므로 첫번째행과
	if(item_Code_Check(rowCount)){
		alert("중복된 품목이 있습니다.");
		return false;
	}
	//목록의 구분, 거래처명, 제품명을 검사하여 입력하지 않았을 경우 저장 안됨
	for(i=0;i<rowCount;i++){
		rowData = selectedRow[i];
		
		if(rowData.packaging_Cus_Name == '' || rowData.packaging_ItemName == ''){
			alert("작성중인 행이 있습니다.");
			return false;
		}
	}
	if(confirm("선택한 행이 저장됩니다. 저장하시겠습니까?")){
		$.ajax({
			method: "post",
			url: "packagingManageRest/PM_Save?data=" + encodeURI(JSON.stringify(selectedRow)),
			beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
			},
			success: function(result) {
				console.log(result);
				if (result == "error") {
					alert("빈칸이 있어서 저장할 수 없습니다.")
				} else {
					PM_Search();
				}
			}
		});	
	}
}

$('#PM_SaveBtn').click(function(){
	PM_Save();
})

function PM_Delete(){
	//테이블에서 선택한 데이터
	selectedData = packagingManageTable.getSelectedData();
	realData = []
	
	if(selectedData.length==0){
		alert("삭제할 선택한 데이터가 없습니다.");
		return false;
	}
	
	// 기존 조회한 데이터는 순번이 1이상, 새로 추가된데이터는 순번이 0
	if(confirm("선택한 행이 삭제됩니다. 삭제하시겠습니까?")){
		//순번이 1인 데이터만 모아서 쿼리를 실행한다.
		for(i=0;i<selectedData.length;i++){
			if(selectedData[i].id != 0){
				realData.push(selectedData[i]);
			}
		}
		//배열에 담은 데이터가 있을경우 쿼리 실행
		if(realData.length != 0){
			$.ajax({
				method: "post",
				url: "packagingManageRest/PM_Delete?data=" + encodeURI(JSON.stringify(realData)),
				success: function(result) {
					console.log(result);
					if (result == "error") {
						alert("삭제 오류")
					}else{
						PM_Search();
					}
				}
			})
		}
		// 행삭제
		packagingManageTable.deleteRow(packagingManageTable.getSelectedRows())
	}
}

$('#PM_DeleteBtn').click(function(){
	PM_Delete();
})

//list에서 같은 품목을 추가할때 경고 알리고 추가안됨
function item_Code_Check(rowCount) {
	cus_Status = packagingManageTable.getColumn("packaging_Cus_Status").getCells();
	cus_Code = packagingManageTable.getColumn("packaging_Cus_Code").getCells();
	itemCode = packagingManageTable.getColumn("packaging_ItemCode").getCells();
	
	//컬럼값을 검색해서 입력값을 포함하는 값이 있으면 선택한다.
	for (i=0;i<rowCount-1;i++) {
		count = 0;
		for(j=i+1;j<rowCount;j++){
			//거래처구분 체크
			if(cus_Status[i].getValue() == cus_Status[j].getValue()){
				count++	
			}
			//거래처코드 체크
			if(cus_Code[i].getValue() == cus_Code[j].getValue()){
				count++	
			}
			//품목코드 체크
			if(itemCode[i].getValue() == itemCode[j].getValue()){
				count++	
			}
			//중복일경우
			if(count==3){
				itemCode[i].getRow().deselect();
				itemCode[j].getRow().deselect();
				return true;
			}
		}
	}
	return false;
}