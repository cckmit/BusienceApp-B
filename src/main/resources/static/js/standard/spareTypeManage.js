/**
 * 
 */
var table = null;

// DB의 데이터를 변수에 담아서 화면에 뿌여줄 용도
var datas = "";

// 선택한 행을 변수에 담아서 더블클릭한 행에 색깔을 칠하는 변수
var element = null;

// 행을 더블클릭하여서 해당 행의 인덱스를 저장하는 변수
var rowindex = 0;

var selectRow = null;
//셀위치저장
var cellPos = null;

function nextFocus(next) {
	if (event.keyCode == 13) {
		console.log(next);
		$('#' + next).focus();
	}
}

// 행을 더블클릭했는지 안했는지 아는 변수
var item_FLAG = false;

var SP_MFEditor = function(cell, onRendered, success, cancel, editorParams, row) {
	//cell - 편집 가능한 셀의 셀 구성 요소
	//onRendered - 에디터가 렌더링 되었을 때 호출 할 함수
	//success - 성공적을 업데이트 된 값을 tabulator에 전달하기 위해 호출되는 함수
	//cancel - 편집을 중단하고 일반 셀로 돌아 가기 위해 호출하는 함수
	//editorParams - editorParams 열 정의 속성에 전달 된 params 객체

	//create 및 style editor
	var SP_modify = document.createElement("input");

	SP_modify.setAttribute("type", "text");

	//입력 생성 및 스타일 지정
	SP_modify.style.padding = "3px";
	SP_modify.style.width = "100%";
	SP_modify.style.boxSizing = "border-box";

	//편집기의 값을 셀의 현재 값으로 설정
	if (cell.getValue() == undefined) {
		SP_modify.value = "";
	} else {
		SP_modify.value = cell.getValue();
	}

	//에디터가 선택되면 선택 상자에 포커스 설정 (타임 아웃은 편집기를 DOM에 추가 할 수 있음)
	onRendered(function() {
		SP_modify.focus();
		SP_modify.select();
		SP_modify.style.css = "100%";
	});

	//값이 설정되면 업데이트 할 셀 트리거
	function onChange() {
		success(SP_modify.value);
	}

	//바꼈을때 블러됫을때 함수 발동
	SP_modify.addEventListener("change", onChange);
	SP_modify.addEventListener("blur", onChange);

	//키버튼 이벤트
	SP_modify.addEventListener("keydown", function(e) {

		if (e.keyCode == 13) {
			if (cell.getField() == "component_Use_Status") {
				$('#comPicture').focus();
			}
			// 사용기한 체크
			if (cell.getField() == "component_Use_Status") {

				// 사용기한 체크해서 잘못되었으면 반응 안함
				if (cell.getRow().getData().component_Use_Date.length != "10") {
					alert("사용기한을 잘못 입력하였습니다.");
				}
			}
			// 사진 체크
			if (cell.getField() == "component_Picture") {

				// 내용이 있을 경우 팝업 창에서 파일 수정

				// 내용이 없을 경우 팝업 창에서 파일 등록
			}
			cell.nav().next();
		}
	});

	return SP_modify;
};


// 행을 더블클릭했는지 안했는지 아는 변수
var item_FLAG = false;


function select_init(input, value) {
	for (i = 0; i < input.length; i++) {
		if (input.options[i].value == value)
			input.options[i].selected = true;
	}
}

var editCheck = function(cell) {
	//cell - the cell component for the editable cell
	//get row data
	var data = cell.getRow().getData();
	return data.component_Producer;
}

var spareTypeManageTable = null;
var Component_Producer = null;

window.onload = function() {

	spareTypeSelect()

}

function spareTypeSelect() {

	data = {
		spareType_itemCode: $("#PRODUCT_ITEM_CODE1").val()
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		async: false,
		url: "spareTypeRest/squareTypeManageRestSelect?data=" + encodeURI(JSON.stringify(data)),
		success: function(data) {
			datas = data;
			console.log(datas);
			spareTypeManageTable = new Tabulator(
				"#spareTypeManageTable",
				{
					//페이징
					layout: "fitColumns",
					pagination: "local",
					paginationSize: 20,
					headerFilterPlaceholder: null,
					height: "calc(100% - 175px)",
					rowClick: function(e, row) {
						item_FLAG = true;
						row.getTable().deselectRow();
						row.select();

						// 더블클릭할 때 데이터 저장
						component_Code = row.getData().component_Code;
						component_Producer = row.getData().component_Producer;
						component_Model = row.getData().component_Model;
						component_Use_Date = row.getData().component_Use_Date;
						component_Picture = row.getData().component_Picture;
						component_Info_Remark = row.getData().component_Info_Remark;

					}, rowDblClick: function(e, row) {
						row.getTable().deselectRow();
						row.select();

						setTimeout(function() {
							row.getCell("component_Producer").edit()
								;
						}, 100);


						//row.getElement().focus();
					}, cellEditing: function(cell) {
						//셀위치 저장하여 포커싱부여
						cellPos = cell;
					},
					data: datas, //assign data to table
					selectable: 1,
					columns: [{
						title: "번호",
						field: "id",
						hozAlign: "center",
						headerHozAlign: "center",
						width: 60
					}, {
						title: "부품코드",
						field: "component_Code",
						headerHozAlign: "center",
						headerFilter: "input",
						width: 90
					}, {
						title: "부품명",
						field: "component_Name",
						headerHozAlign: "center",
						headerFilter: "input",
						width: 90
					}, {
						title: "규격",
						field: "component_Standard",
						headerHozAlign: "center",
						headerFilter: "input",
						width: 100
					}, {
						title: "제작사",
						field: "component_Producer",
						headerHozAlign: "center",
						headerFilter: "input",
						editor: SP_MFEditor,
						width: 150
					}, {
						title: "모델",
						field: "component_Model",
						headerHozAlign: "center",
						headerFilter: "input",
						editor: SP_MFEditor,
						width: 100
					}, {
						title: "구입처",
						field: "component_Cus_Name",
						headerHozAlign: "center",
						headerFilter: "input",
						width: 100
					}, {
						title: "최신구입단가",
						field: "component_Latest_Unit_Price",
						headerHozAlign: "center",
						headerFilter: "input",
						width: 130
					}, {
						title: "사용기한",
						field: "component_Use_Date",
						headerHozAlign: "center",
						headerFilter: "input",
						editor: SP_MFEditor,
						width: 100
					}, {
						title: "사용유무",
						field: "component_Use_Status",
						headerHozAlign: "center",
						headerFilter: "input",
						hozAlign: "center",
						formatter: "tickCross",
						width: 85,
						headerFilterParams: {
							initial: "true",
							values: {
								"true": "사용",
								"false": "미사용"
							}
						},
						editor: SP_MFEditor,
						editor: "select",
						editorParams: {
							values: { "true": "사용", "false": "미사용" }
						}

					}, {
						title: "최근입고일자",
						field: "component_Latest_InMat_Date",
						headerHozAlign: "center",
						headerFilter: "input",
						formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" },
						width: 135
					}, {
						title: "최근출고일자",
						field: "component_Latest_OutMat_Date",
						headerHozAlign: "center",
						headerFilter: "input",
						formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" },
						width: 135
					}, {
						title: "사진",
						field: "component_Picture",
						headerHozAlign: "center",
						hozAlign: "center",
						formatter: more_details,
						visible: false,
						width: 150
					}, {
						title: "비고",
						field: "component_Info_Remark",
						headerHozAlign: "center",
						headerFilter: "input",
						editor: SP_MFEditor,
						width: 100
					}],
				});
		}
	});
}
// 사진등록 세부보기 버튼
var more_details = function(cell, formatterParams, onRendered) { //plain text value
	value = cell.getValue();
	if (value == null || value == 0) {
		value = ' <button class="spareImgInsertBtn" style="height: 21px; font-size: 12px" onclick="insert()">등록</button> '
	} else {
		value += ' <button class="spareImgUpdateBtn" style="height: 21px; font-size: 12px" onclick="imgUpdate()">수정</button> '
	}
	return value;
};

function spareTypeUpdate() {

	console.log("datas");

	selectedRow = spareTypeManageTable.getData("selected")[0];

	datas = {
		component_Producer: selectedRow.component_Producer,
		component_Model: selectedRow.component_Model,
		component_Use_Date: selectedRow.component_Use_Date,
		component_Picture: selectedRow.component_Picture,
		component_Info_Remark: selectedRow.component_Info_Remark,
		component_Code: selectedRow.component_Code
	};

	console.log(datas);

	$.ajax({
		method: "get",
		data: datas,
		url: "spareTypeRest/squareTypeManageRestUpdate?data="
			+ encodeURI(JSON.stringify(datas)),
		success: function(data, testStatus) {
			console.log(data);
			spareTypeManageTable.setData(data);
			alert("저장 완료 하였습니다.");
			location.reload();
		}
	});

}

// 이미지를 눌렀을 때 
$('#img-box').click(function() {
	console.log('fileadd');
	$("input[name=uploadFile]").click();
});

// 파일명 나오게
function loadFile(input) {
	var file = input.files[0];

	var name = document.getElementById('holder');
	name.textContent = file.name;
}

// 사진 전송
$('#insertModalInitbtn').on('click', function() {
	var form = $('#formSubmit');
	selectedRow = spareTypeManageTable.getData("selected")[0];

	// formdata 생성
	var formData = new FormData(form[0]);

	formData.append("file", $("input[name=uploadFile]")[0].files[0]);

	$.ajax({
		method: "POST",
		url: "spareTypeRest/spareImgUpload?data=" + encodeURI(JSON.stringify(selectedRow)),
		data: formData,
		processData: false,
		contentType: false,
		success: function(data) {
			console.log('jQeury ajax form submit success');
		},
		error: function(data) {
			console.log('jQeury ajax form submit error');
		}
	})
});

// 사진 update
function imgUpdate() {

	var form = $('#updateformSubmit');
	var formData = new FormData(form[0]);

	var imgItem = $("#updateFile").val;

	console.log("imgItem = " + imgItem);
	// formdata 생성

	formData.append("file", $("input[name=updateFile]")[0].files[0]);

	$.ajax({
		method: "POST",
		url: "spareTypeRest/spareImgUpdate?data=" + encodeURI(JSON.stringify(imgItem)),
		data: formData,
		dataType: "json",
		async: false,
		processData: false,
		contentType: false,
		success: function(data) {
			console.log('jQeury ajax form submit success');
		},
		error: function(data) {
			console.log('jQeury ajax form submit error');
		}
	});
}

/*$("input[name='uploadFile']").change(function(e){
	
	e.preventDefault();
	
	var file = this.uploadFile[0];
	reader = new FileReader();
	
	reader.onload = function(event) {
		var img = new Image();
		img.src = event.target.result;
		if(img.width > 700 || img.height > 500) {
			img.width = 700;
			img.height = 500;
		}
		
		$('#holder').empty();
		$('#holder').append(img);
	};
	
	reader.readAsDataURL(file);
	
	return false;
});*/



/*var addEventListener = null;
var openModel = document.querySelector('.spareImgInsertBtn');
var modal = document.querySelector('#spareImginsertModal');

openModel.addEventListener('click',() => {
	modal.style.display = 'block';
});*/
function insert() {
	console.log("insert");
	$("#spareImginsertModal").modal("show");
}
console.log($(".spareImgInsertBtn").value);
// 입력버튼을 클릭을 할때 모달창을 여는 이벤트
$(".spareImgInsertBtn").click(function() {
	console.log("모달창 클릭됐니?");
	$("#spareImginsertModal").modal("show");
});

// 수정버튼을 클릭 할 때 모달창 여는 이벤트
$('.spareImgUpdateBtn').click(function() {
	console.log("수정,삭제");
	$("#spareImgUpdateModal").modal("show");
});

// 수정모달창이 열릴때 가장 상단의 input에 포커스를 주는 이벤트
$("#updateModal").on("shown.bs.modal", function() {
	$("#update_olditem_CODE").focus();
});

// 저장할지 말지 여부를 묻는 모달창 열기
function insertModal() {
	$("#insertYesNo").modal("show");
}


