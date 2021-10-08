var table = null;

// DB의 데이터를 변수에 담아서 화면에 뿌여줄 용도
var datas = "";

// 선택한 행을 변수에 담아서 더블클릭한 행에 색깔을 칠하는 변수
var element = null;

// 행을 더블클릭하여서 해당 행의 인덱스를 저장하는 변수
var rowindex = 0;

// 행을 더블클릭하여서 해당 행의 데이터를 저장했다가 화면에서 뿌려주는 변수
var product_BUSINESS_PLACE_NAME = null;
var product_ITEM_CODE = null;
var product_OLD_ITEM_CODE = null;
var product_ITEM_NAME = null;
var product_INFO_STND_1 = null;
var product_INFO_STND_2 = null;
var product_UNIT_NAME = null;
var product_MATERIAL_NAME = null;
var product_MTRL_CLSFC_NAME = null;
var product_ITEM_CLSFC_1_NAME = null;
var product_ITEM_CLSFC_2_NAME = null;
var product_SUBSID_MATL_MGMT = null;
var product_ITEM_STTS_NAME = null;
var product_BASIC_WAREHOUSE_NAME = null;
var product_SFTY_STOCK = null;
var product_BUYER = null;
var product_WRHSN_INSPC = null;
var product_USE_STATUS = null;
var product_MODIFY_D = null;
var product_MODIFIER = null;


function nextFocus(next) {
	if (event.keyCode == 13) {
		console.log(next);
		$('#' + next).focus();
	}
}

function select_init(input, value) {
	for (i = 0; i < input.length; i++) {
		if (input.options[i].value == value)
			input.options[i].selected = true;
	}
}

var itemManageTable = new Tabulator("#itemManageTable",	{
		//페이징
		pagination: "local",
		paginationSize: 20,
		layoutColumnsOnNewData : true,
		headerFilterPlaceholder: null,
		height: "calc(100% - 175px)",
		ajaxURL:"itemManageRest/itemManageSelect",
	    ajaxConfig:"get",
	    ajaxContentType:"json",
		rowClick: function(e, row) {
			row.getTable().deselectRow();
			row.select();

		},
		rowDblClick: function(e, row) {
			//모달창 띄움
			modifyModalShow();
		},
		rowSelected:function(row){
	    	var jsonData = fromRowToJson(row);
			console.log(jsonData);
			modalInputBox(jsonData);
	    },
		columns: [
			{ title: "순번",	field: "rownum", hozAlign: "center", formatter:"rownum"},
			{ title: "사업장", field: "product_BUSINESS_PLACE_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "품목코드", field: "product_ITEM_CODE", headerHozAlign: "center", headerFilter: "input"},
			{ title: "구품목코드", field: "product_OLD_ITEM_CODE", headerHozAlign: "center", headerFilter: "input"},
			{ title: "품명", field: "product_ITEM_NAME",	headerHozAlign: "center", headerFilter: "input"},
			{ title: "규격1", field: "product_INFO_STND_1", headerHozAlign: "center", headerFilter: "input"},
			{ title: "규격2", field: "product_INFO_STND_2", headerHozAlign: "center", headerFilter: "input"},
			{ title: "단위", field: "product_UNIT_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "재질", field: "product_MATERIAL_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "자재분류", field: "product_MTRL_CLSFC_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "품목분류1", field: "product_ITEM_CLSFC_1_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "품목분류2", field: "product_ITEM_CLSFC_2_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "부자재관리", field: "product_SUBSID_MATL_MGMT", headerHozAlign: "center",hozAlign: "center",
				formatter: "tickCross", headerFilter: true, headerFilterParams: { values: {	"true": "사용", "false": "미사용"}}},
			{ title: "품목상태", field: "product_ITEM_STTS_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "기본창고", field: "product_BASIC_WAREHOUSE_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "보관구역", field: "product_SAVE_AREA_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "안전재고", field: "product_SFTY_STOCK", headerHozAlign: "center", headerFilter: "input"},
			{ title: "구매담당자", field: "product_BUYER", headerHozAlign: "center", headerFilter: "input"},
			{ title: "입고검사", field: "product_WRHSN_INSPC", headerHozAlign: "center", hozAlign: "center",
				formatter: "tickCross", headerFilter: true,	headerFilterParams: { values: { "true": "사용", "false": "미사용"}}},
			{ title: "사용유무", field: "product_USE_STATUS", headerHozAlign: "center", hozAlign: "center",
				formatter: "tickCross", headerFilter: true, headerFilterParams: { values: {	"true": "사용", "false": "미사용"}}},
			{ title: "수정일자", field: "product_MODIFY_D", headerHozAlign: "center", hozAlign: "right",  headerFilter: "input",
				formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }},
			{ title: "수정자", field: "product_MODIFIER", headerHozAlign: "center", headerFilter: "input"}
			]
	});

function fromRowToJson(row){
	console.log(row)
	var pickValue = ["product_BUSINESS_PLACE_NAME", "product_ITEM_CODE", "product_OLD_ITEM_CODE",
					"product_ITEM_NAME", "product_INFO_STND_1", "product_INFO_STND_2",
					"product_UNIT_NAME", "product_MATERIAL_NAME", "product_MTRL_CLSFC_NAME",
					"product_ITEM_CLSFC_1_NAME", "product_ITEM_CLSFC_2_NAME", "product_SUBSID_MATL_MGMT",
					"product_ITEM_STTS_NAME", "product_BASIC_WAREHOUSE_NAME", "product_SAVE_AREA_NAME",
					"product_SFTY_STOCK", "product_BUYER", "product_WRHSN_INSPC", "product_USE_STATUS",
					"product_MODIFY_D",	"product_MODIFIER"];
	var jsonData = new Object();
	pickValue.forEach(function(item,index,arr2){
		jsonData[item] = row.getData()[item]
	})
	return jsonData;
}

// 삭제 기능을 수행하는 함수
function delBtn() {
	// 실제로 DB에서 선택한 행의 데이터를 지운다.
	$.ajax({
		method: "POST",
		data: null,
		url: "itemManageRest/itemManageDelete?PRODUCT_ITEM_CODE=" + product_ITEM_CODE,
		success: function(data, testStatus) {
		}
	});
}

function modBtn() {
	datas = {
		PRODUCT_ITEM_CODE: document.getElementById("update_item_CODE").value,
		PRODUCT_ITEM_NAME: document.getElementById("update_item_NAME").value,
		PRODUCT_BUSINESS_PLACE: $("#update_item_COMPANY option:selected").val(),
		PRODUCT_OLD_ITEM_CODE: document.getElementById("update_olditem_CODE").value,
		PRODUCT_INFO_STND_1: document.getElementById("update_item_STND1").value,
		PRODUCT_INFO_STND_2: document.getElementById("update_item_STND2").value,
		PRODUCT_UNIT: $("#update_item_UNIT option:selected").val(),
		PRODUCT_MATERIAL: $("#update_item_MAT option:selected").val(),
		PRODUCT_MTRL_CLSFC: $("#update_item_mat_CLSFC option:selected").val(),
		PRODUCT_MTRL_CLSFC_NAME: product_MTRL_CLSFC_NAME,
		PRODUCT_ITEM_CLSFC_1: $("#update_item_CLSFC1 option:selected").val(),
		PRODUCT_ITEM_CLSFC_2: $("#update_item_CLSFC2 option:selected").val(),
		PRODUCT_SUBSID_MATL_MGMT: document.getElementById("update_item_subsid_mat_MGMT").checked,
		PRODUCT_ITEM_STTS: $("#update_item_STATUS option:selected").val(),
		PRODUCT_BASIC_WAREHOUSE: $("#update_item_WAREHOUSE option:selected").val(),
		PRODUCT_SFTY_STOCK: document.getElementById("update_item_stfy_STOCK").value,
		PRODUCT_BUYER: document.getElementById("update_item_BUYER").value,
		PRODUCT_WRHSN_INSPC: document.getElementById("update_item_WRHSN").checked,
		PRODUCT_USE_STATUS: document.getElementById("update_item_USE_STATUS").checked
	};

	console.log(datas);

	$.ajax({
		method: "POST",
		data: datas,
		url: "itemManageRest/itemManageUpdate?data="
			+ encodeURI(JSON.stringify(datas)),
		success: function(data, testStatus) {
		}
	});
}

// 입력버튼을 클릭을 할때 모달창을 여는 이벤트
$("#itemADDBtn").click(function() {
	registerModalShow();
});

function registerModalShow(){
	/*
	$('.modify').addClass('none');
	
	if ($('.insert').hasClass('none')) {
		$('.insert').removeClass('none');
	}	*/
	$("#itemRegisterModal").find('form')[0].reset();
	
	$("#itemRegisterModal").modal("show").on("shown.bs.modal", function () {
		$("#insert_item_CODE").focus();
	});
}

// update버튼을 클릭을 할때 모달창을 여는 이벤트
$("#itemUpdateBtn").click(function() {
	modifyModalShow();
});

// delete버튼을 클릭을 할때 모달창을 여는 이벤트
$("#itemDeleteBtn").click(function() {
	modifyModalShow();
});


function modifyModalShow(){
	var selectedRow = itemManageTable.getData("selected");
	
	if(selectedRow.length == 0){
		alert("수정할 행을 선택하세요.");
		return false;
	}
	/*
	$('.insert').addClass('none');
	
	if ($('.modify').hasClass('none')) {
		$('.modify').removeClass('none');
	}*/
	
	$("#itemModifyModal").modal("show").on("shown.bs.modal", function () {
		$("#update_olditem_CODE").focus();
	});
}

function insBtn() {

	// 최소발주
	$('#insert_item_min_ORDERS').val("0");
	// 안전재고
	$('#insert_item_stfy_STOCK').val("0");
	// 최대발주
	$('#insert_item_max_ORDERS').val("0");

	var itemCode = document.getElementById("insert_item_CODE").value;

	//alert(itemCode);

	if (itemCode == "") {
		alert("품목코드는 반드시 입력하셔야 합니다.");
		document.getElementById("insert_item_CODE").focus();
		return;
	}

	if (itemCode.length > 10) {
		alert("품목코드는 10글자 이하로 입력하여주세요.");
		document.getElementById("insert_item_CODE").focus();
		return;
	}

	datas = {
		PRODUCT_ITEM_CODE: itemCode,
		PRODUCT_ITEM_NAME: document.getElementById("insert_item_NAME").value,
		PRODUCT_BUSINESS_PLACE: $("#insert_item_COMPANY option:selected").val(),
		PRODUCT_OLD_ITEM_CODE: document.getElementById("insert_olditem_CODE").value,
		PRODUCT_INFO_STND_1: document.getElementById("insert_item_STND1").value,
		PRODUCT_INFO_STND_2: document.getElementById("insert_item_STND2").value,
		PRODUCT_UNIT: $("#insert_item_UNIT option:selected").val(),
		PRODUCT_MATERIAL: $("#insert_item_MAT option:selected").val(),
		PRODUCT_MTRL_CLSFC: $("#insert_item_mat_CLSFC option:selected").val(),
		PRODUCT_MTRL_CLSFC_NAME: product_MTRL_CLSFC_NAME,
		PRODUCT_ITEM_CLSFC_1: $("#insert_item_CLSFC1 option:selected").val(),
		PRODUCT_ITEM_CLSFC_2: $("#insert_item_CLSFC2 option:selected").val(),
		PRODUCT_SUBSID_MATL_MGMT: document.getElementById("insert_item_subsid_mat_MGMT").checked,
		PRODUCT_ITEM_STTS: $("#insert_item_STATUS option:selected").val(),
		PRODUCT_BASIC_WAREHOUSE: $("#insert_item_WAREHOUSE option:selected").val(),
		PRODUCT_SFTY_STOCK: document.getElementById("insert_item_stfy_STOCK").value,
		PRODUCT_BUYER: document.getElementById("insert_item_BUYER").value,
		PRODUCT_WRHSN_INSPC: document.getElementById("insert_item_WRHSN").checked,
		PRODUCT_USE_STATUS: document.getElementById("insert_item_USE_STATUS").checked
	};

	$.ajax({
		method: "POST",
		data: datas,
		url: "itemManageRest/itemManageInsert?data="
			+ encodeURI(JSON.stringify(datas)),
		success: function(data, testStatus) {
			if (data == "Overlap") {
				alert("중복코드를 입력하셨습니다. 다른 코드를 입력해주세요.");
				document.getElementById("insert_item_CODE").focus();
			}
			else if (data == "Success") {
				alert("저장 완료 하였습니다.");

			}
		}
	});
}