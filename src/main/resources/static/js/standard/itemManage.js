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

// 행을 더블클릭했는지 안했는지 아는 변수
		var item_FLAG = false;
		
		
function updatedeleteView() {
	if (!item_FLAG) {
		alert("행을 선택한 후에 수정 및 삭제를 시도하여 주십시오.");
		return;
	}

	$("#updateModal").modal("show");
	//modviewBtn();
}

function select_init(input,value){
	for (i = 0; i < input.length; i++){
		if(input.options[i].value == value)
			input.options[i].selected = true;
	}
}

window.onload = function() {
	$
		.ajax({
			method: "GET",
			url: "itemManageRest/itemManageRestSelect",
			success: function(data) {
				datas = data;
				console.log(datas);
				var itemManageTable = new Tabulator(
					"#itemManageTable",
					{
						//페이징
						layout: "fitColumns",
						pagination: "local",
						paginationSize: 20,
						headerFilterPlaceholder: null,
						height: "calc(100% - 175px)",
						rowClick:function(e, row){
						item_FLAG = true;
						row.select();
						if (element == null) {
								element = row.getElement();
								element.style.color = "red";
							}
							else {
								element.style.color = "black";
								row.getElement().style.color = "red";
								element = row.getElement();
							}

							rowindex = row.getIndex();
							
							// 더블클릭할때 데이터를 저장
							product_BUSINESS_PLACE_NAME = row.getData().product_BUSINESS_PLACE_NAME;
							product_ITEM_CODE = row.getData().product_ITEM_CODE;
							product_OLD_ITEM_CODE = row.getData().product_OLD_ITEM_CODE;
							product_ITEM_NAME = row.getData().product_ITEM_NAME;
							product_INFO_STND_1 = row.getData().product_INFO_STND_1;
							product_INFO_STND_2 = row.getData().product_INFO_STND_2;
							product_UNIT_NAME = row.getData().product_UNIT_NAME;
							product_MATERIAL_NAME = row.getData().product_MATERIAL_NAME;
							product_MTRL_CLSFC_NAME = row.getData().product_MTRL_CLSFC_NAME;
							product_ITEM_CLSFC_1_NAME = row.getData().product_ITEM_CLSFC_1_NAME;
							product_ITEM_CLSFC_2_NAME = row.getData().product_ITEM_CLSFC_2_NAME;
							product_SUBSID_MATL_MGMT = row.getData().product_SUBSID_MATL_MGMT;
							product_ITEM_STTS_NAME = row.getData().product_ITEM_STTS_NAME;
							product_BASIC_WAREHOUSE_NAME = row.getData().product_BASIC_WAREHOUSE_NAME;
							product_SFTY_STOCK = row.getData().product_SFTY_STOCK;
							product_BUYER = row.getData().product_BUYER;
							product_WRHSN_INSPC = row.getData().product_WRHSN_INSPC;
							product_USE_STATUS = row.getData().product_USE_STATUS;
							product_MODIFY_D = row.getData().product_MODIFY_D;
							product_MODIFIER = row.getData().product_MODIFIER;
							
							console.log(row.getData());
							
							document.getElementById("update_item_CODE").value = product_ITEM_CODE;
							document.getElementById("update_item_NAME").value = product_ITEM_NAME;
							
							//document.getElementById("update_item_COMPANY").options[Number(row.getData().product_BUSINESS_PLACE)].selected = true;
							select_init(document.getElementById("update_item_COMPANY"),Number(row.getData().product_BUSINESS_PLACE));
							
							document.getElementById("update_olditem_CODE").value = product_OLD_ITEM_CODE;
							document.getElementById("update_item_STND1").value = product_INFO_STND_1;
							document.getElementById("update_item_STND2").value = product_INFO_STND_2;
							
							//document.getElementById("update_item_UNIT").options[Number(row
								//.getData().product_UNIT) - 1].selected = true;
							select_init(document.getElementById("update_item_UNIT"),Number(row.getData().product_UNIT));
								
							//document.getElementById("update_item_MAT").options[Number(row
								//.getData().product_MATERIAL) - 1].selected = true;
							select_init(document.getElementById("update_item_MAT"),Number(row.getData().product_MATERIAL));
							
							//document.getElementById("update_item_mat_CLSFC").options[Number(row
								//.getData().product_MTRL_CLSFC) - 1].selected = true;
							select_init(document.getElementById("update_item_mat_CLSFC"),Number(row.getData().product_MTRL_CLSFC));
								
							//document.getElementById("update_item_CLSFC1").options[Number(row
								//.getData().product_ITEM_CLSFC_1) - 1].selected = true;
							select_init(document.getElementById("update_item_CLSFC1"),Number(row.getData().product_ITEM_CLSFC_1));
								
							//document.getElementById("update_item_CLSFC2").options[Number(row
								//.getData().product_ITEM_CLSFC_2) - 1].selected = true;
							select_init(document.getElementById("update_item_CLSFC2"),Number(row.getData().product_ITEM_CLSFC_2));
								
							//document.getElementById("update_item_STATUS").options[Number(row
								//.getData().product_ITEM_STTS) - 1].selected = true;
							select_init(document.getElementById("update_item_STATUS"),Number(row.getData().product_ITEM_STTS));
								
							//document.getElementById("update_item_WAREHOUSE").options[Number(row
								//.getData().product_BASIC_WAREHOUSE) - 1].selected = true;
							select_init(document.getElementById("update_item_WAREHOUSE"),Number(row.getData().product_BASIC_WAREHOUSE));
								
							document.getElementById("update_item_stfy_STOCK").value = product_SFTY_STOCK;
							document.getElementById("update_item_BUYER").value = "";

							if (row.getData().product_USE_STATUS == "true")
								document.getElementById("update_item_USE_STATUS").checked = true;
							else
								document.getElementById("update_item_USE_STATUS").checked = false;

							if (row.getData().product_SUBSID_MATL_MGMT == "true")
								document.getElementById("update_item_subsid_mat_MGMT").checked = true;
							else
								document.getElementById("update_item_subsid_mat_MGMT").checked = false;

							if (row.getData().product_WRHSN_INSPC == "true")
								document.getElementById("update_item_WRHSN").checked = true;
							else
								document.getElementById("update_item_WRHSN").checked = false;
							
    					}, 
						rowDblClick: function(e, row) {
							item_FLAG = true;
							row.select();
							if (element == null) {
								element = row.getElement();
								element.style.color = "red";
							}
							else {
								element.style.color = "black";
								row.getElement().style.color = "red";
								element = row.getElement();
							}

							rowindex = row.getIndex();

							console.log(row.getData());

							// 더블클릭할때 데이터를 저장
							product_BUSINESS_PLACE_NAME = row.getData().product_BUSINESS_PLACE_NAME;
							product_ITEM_CODE = row.getData().product_ITEM_CODE;
							product_OLD_ITEM_CODE = row.getData().product_OLD_ITEM_CODE;
							product_ITEM_NAME = row.getData().product_ITEM_NAME;
							product_INFO_STND_1 = row.getData().product_INFO_STND_1;
							product_INFO_STND_2 = row.getData().product_INFO_STND_2;
							product_UNIT_NAME = row.getData().product_UNIT_NAME;
							product_MATERIAL_NAME = row.getData().product_MATERIAL_NAME;
							product_MTRL_CLSFC_NAME = row.getData().product_MTRL_CLSFC_NAME;
							product_ITEM_CLSFC_1_NAME = row.getData().product_ITEM_CLSFC_1_NAME;
							product_ITEM_CLSFC_2_NAME = row.getData().product_ITEM_CLSFC_2_NAME;
							product_SUBSID_MATL_MGMT = row.getData().product_SUBSID_MATL_MGMT;
							product_ITEM_STTS_NAME = row.getData().product_ITEM_STTS_NAME;
							product_BASIC_WAREHOUSE_NAME = row.getData().product_BASIC_WAREHOUSE_NAME;
							product_SFTY_STOCK = row.getData().product_SFTY_STOCK;
							product_BUYER = row.getData().product_BUYER;
							product_WRHSN_INSPC = row.getData().product_WRHSN_INSPC;
							product_USE_STATUS = row.getData().product_USE_STATUS;
							product_MODIFY_D = row.getData().product_MODIFY_D;
							product_MODIFIER = row.getData().product_MODIFIER;

							//행에 색변경
							if (element != null) {
								element.style.background = "white";
							}
							row.getElement().style.background = "#78EAFF";
							element = row.getElement();

							defect_FLAG = true;

							$("#updateModal").modal("show");

							document.getElementById("update_item_CODE").value = product_ITEM_CODE;
							document.getElementById("update_item_NAME").value = product_ITEM_NAME;
							
							var product_BUSINESS_PLACE = row.getData().product_BUSINESS_PLACE
							var product_BUSINESS_PLACE_Last = String(product_BUSINESS_PLACE).charAt(product_BUSINESS_PLACE.length-1)-1;
							
							$("#update_item_COMPANY option:eq("+product_BUSINESS_PLACE_Last+")").prop("selected", true);
							
							
							
							document.getElementById("update_olditem_CODE").value = product_OLD_ITEM_CODE;
							document.getElementById("update_item_STND1").value = product_INFO_STND_1;
							document.getElementById("update_item_STND2").value = product_INFO_STND_2;
							
							var product_UNIT = row.getData().product_UNIT
							var product_UNIT_Last = String(product_UNIT).charAt(product_UNIT.length-1)-1;
							
							$("#update_item_UNIT option:eq("+product_UNIT_Last+")").prop("selected", true);
							
							var product_MATERIAL = row.getData().product_MATERIAL
							var product_MATERIAL_Last = String(product_MATERIAL).charAt(product_MATERIAL.length-1)-1;
							
							$("#update_item_MAT option:eq("+product_MATERIAL_Last+")").prop("selected", true);
							
							var product_MTRL_CLSFC = row.getData().product_MTRL_CLSFC
							var product_MTRL_CLSFC_Last = String(product_MTRL_CLSFC).charAt(product_MTRL_CLSFC.length-1)-1;
							
							$("#update_item_mat_CLSFC option:eq("+product_MTRL_CLSFC_Last+")").prop("selected", true);
							
							var product_ITEM_CLSFC_1 = row.getData().product_ITEM_CLSFC_1
							var product_ITEM_CLSFC_1_Last = String(product_ITEM_CLSFC_1).charAt(product_ITEM_CLSFC_1.length-1)-1;
							
							$("#update_item_CLSFC1 option:eq("+product_ITEM_CLSFC_1_Last+")").prop("selected", true);
							
							var product_ITEM_CLSFC_2 = row.getData().product_ITEM_CLSFC_2
							var product_ITEM_CLSFC_2_Last = String(product_ITEM_CLSFC_2).charAt(product_ITEM_CLSFC_2.length-1)-1;
							
							$("#update_item_CLSFC2 option:eq("+product_ITEM_CLSFC_2_Last+")").prop("selected", true);
							
							var product_ITEM_STTS = row.getData().product_ITEM_STTS
							var product_ITEM_STTS_Last = String(product_ITEM_STTS).charAt(product_ITEM_STTS.length-1)-1;
							
							$("#update_item_STATUS option:eq("+product_ITEM_STTS_Last+")").prop("selected", true);
							
							var product_BASIC_WAREHOUSE = row.getData().product_BASIC_WAREHOUSE
							var product_BASIC_WAREHOUSE_Last = String(product_BASIC_WAREHOUSE).charAt(product_BASIC_WAREHOUSE.length-1)-1;
							
							$("#update_item_WAREHOUSE option:eq("+product_BASIC_WAREHOUSE_Last+")").prop("selected", true);
							
							document.getElementById("update_item_stfy_STOCK").value = product_SFTY_STOCK;
							document.getElementById("update_item_BUYER").value = product_BUYER;

							if (row.getData().product_USE_STATUS == "true")
								document.getElementById("update_item_USE_STATUS").checked = true;
							else
								document.getElementById("update_item_USE_STATUS").checked = false;

							if (row.getData().product_SUBSID_MATL_MGMT == "true")
								document.getElementById("update_item_subsid_mat_MGMT").checked = true;
							else
								document.getElementById("update_item_subsid_mat_MGMT").checked = false;

							if (row.getData().product_WRHSN_INSPC == "true")
								document.getElementById("update_item_WRHSN").checked = true;
							else
								document.getElementById("update_item_WRHSN").checked = false;
						},
						data: datas, //assign data to table
						selectable:1, 
						columns: [{
							title: "번호",
							field: "id",
							hozAlign: "center",
							headerHozAlign: "center",
							width: 60
						}, {
							title: "사업장",
							field: "product_BUSINESS_PLACE_NAME",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 90
						}, {
							title: "품목코드",
							field: "product_ITEM_CODE",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 90
						}, {
							title: "구품목코드",
							field: "product_OLD_ITEM_CODE",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 100
						}, {
							title: "품명",
							field: "product_ITEM_NAME",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 150
						}, {
							title: "규격1",
							field: "product_INFO_STND_1",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 70
						}, {
							title: "규격2",
							field: "product_INFO_STND_2",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 70
						}, {
							title: "단위",
							field: "product_UNIT_NAME",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 60
						}, {
							title: "재질",
							field: "product_MATERIAL_NAME",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 60
						}, {
							title: "자재분류",
							field: "product_MTRL_CLSFC_NAME",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 85
						}, {
							title: "품목분류1",
							field: "product_ITEM_CLSFC_1_NAME",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 95
						}, {
							title: "품목분류2",
							field: "product_ITEM_CLSFC_2_NAME",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 100
						}, {
							title: "부자재관리",
							field: "product_SUBSID_MATL_MGMT",
							headerHozAlign: "center",
							width: 100,
							headerHozAlign: "center",
							hozAlign: "center",
							formatter: "tickCross",
							headerFilter: true,
							headerFilterParams: {
								values: {
									"true": "사용",
									"false": "미사용"
								}
							},
							editor: "select",
							editorParams: {
								values: {}
							}
						}, {
							title: "품목상태",
							field: "product_ITEM_STTS_NAME",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 85
						}, {
							title: "기본창고",
							field: "product_BASIC_WAREHOUSE_NAME",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 100
						}, {
							title: "보관구역",
							field: "product_SAVE_AREA_NAME",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 100
						}, {
							title: "안전재고",
							field: "product_SFTY_STOCK",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 85
						}, {
							title: "구매담당자",
							field: "product_BUYER",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 100
						}, {
							title: "입고검사",
							field: "product_WRHSN_INSPC",
							headerHozAlign: "center",
							width: 85,
							headerHozAlign: "center",
							hozAlign: "center",
							formatter: "tickCross",
							headerFilter: true,
							headerFilterParams: {
								values: {
									"true": "사용",
									"false": "미사용"
								}
							},
							editor: "select",
							editorParams: {
								values: {}
							}
						}, {
							title: "수정일자",
							field: "product_MODIFY_D",
							headerHozAlign: "center",
							hozAlign: "right",
							formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"},
							headerFilter: "input",
							width: 126
						}, {
							title: "수정자",
							field: "product_MODIFIER",
							headerHozAlign: "center",
							headerFilter: "input",
							width: 85
						}, {
							title: "사용유무",
							field: "product_USE_STATUS",
							headerHozAlign: "center",
							width: 85,
							headerHozAlign: "center",
							hozAlign: "center",
							formatter: "tickCross",
							headerFilter: true,
							headerFilterParams: {
								values: {
									"true": "사용",
									"false": "미사용"
								}
							},
							editor: "select",
							editorParams: {
								values: {}
							}
						}],
					});
			}
		});

	SubmenuSelector("2", "13211");
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

	// DB까지 지우고 나면 화면을 새로고침
	location.reload();
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
	

	location.reload();
}

// 입력버튼을 클릭을 할때 모달창을 여는 이벤트
$("#custom-btn-default").click(function() {
	$("#insertModal").modal("show");
});

// 입력모달창이 열릴때 가장 상단의 input에 포커스를 주는 이벤트
$("#insertModal").on("shown.bs.modal", function() {
	$("#insert_item_CODE").focus();
});

// 수정모달창이 열릴때 가장 상단의 input에 포커스를 주는 이벤트
$("#updateModal").on("shown.bs.modal", function() {
	$("#update_olditem_CODE").focus();
});

// 저장할지 말지 여부를 묻는 모달창 열기
function insertModal() {
	$("#insertYesNo").modal("show");
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

				location.reload();
			}
		}
	});
}



// 입력 모달창에서 취소버튼을 누를때 입력 모달창 데이터를 초기화
function insResetBtn() {
	document.getElementById("insert_item_CODE").value = "";
	document.getElementById("insert_item_NAME").value = "";
	document.getElementById("insert_item_COMPANY").options[0].selected = true;
	document.getElementById("insert_olditem_CODE").value = "";
	document.getElementById("insert_item_STND1").value = "";
	document.getElementById("insert_item_STND2").value = "";
	document.getElementById("insert_item_UNIT").options[0].selected = true;
	document.getElementById("insert_item_MAT").options[0].selected = true;
	document.getElementById("insert_item_mat_CLSFC").options[0].selected = true;
	document.getElementById("insert_item_CLSFC1").options[0].selected = true;
	document.getElementById("insert_item_CLSFC2").options[0].selected = true;
	document.getElementById("insert_item_subsid_mat_MGMT").checked = false;
	document.getElementById("insert_item_STATUS").options[0].selected = true;
	document.getElementById("insert_item_WAREHOUSE").options[0].selected = true;
	document.getElementById("insert_item_stfy_STOCK").value = 0;
	document.getElementById("insert_item_min_ORDERS").value = 0;
	document.getElementById("insert_item_max_ORDERS").value = 0;
	document.getElementById("insert_item_BUYER").value = "";
	document.getElementById("insert_item_WRHSN").checked = false;
	document.getElementById("insert_item_USE_STATUS").checked = false;
}

