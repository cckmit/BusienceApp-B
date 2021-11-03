
var table = null;

// DB의 데이터를 변수에 담아서 화면에 뿌여줄 용도
var datas = "";

// 선택한 행을 변수에 담아서 더블클릭한 행에 색깔을 칠하는 변수
var element = null;

// 행을 더블클릭하여서 해당 행의 인덱스를 저장하는 변수
var rowindex = 0;

// 행을 더블클릭하여서 해당 행의 데이터를 저장했다가 화면에서 뿌려주는 변수
var cus_Code = null;
var cus_Name = null;
var cus_Status = null;
var cus_Clsfc = null;
var cus_Rprsn = null;
var cus_Mng = null;
var cus_Co = null;
var cus_Rprsn_PhNr = null;
var cus_Mng_PhNr = null;
var cus_Mng_Email = null;
var cus_Adr = null;
var cus_Rgstr_Nr = null;
var cus_Pymn_Date = null;
var cus_Co_EstYr = null;

// 행을 더블클릭했는지 안했는지 아는 변수
var customer_FLAG = false;

function updatedeleteView() {
	if (!customer_FLAG) {
		alert("행을 선택한 후에 수정 및 삭제를 시도하여 주십시오.");
		return false;
	}

	$("#customerModifyModal").modal("show");

	document.getElementById("update_cus_Code").value = cus_Code;
	document.getElementById("update_cus_Name").value = cus_Name;
	$("#update_cus_Status option:eq(" + cus_Status + ")").prop("selected", true);
	document.getElementById("update_cus_Rprsn").value = cus_Rprsn;
	document.getElementById("update_cus_Mng").value = cus_Mng;
	document.getElementById("update_cus_Co").value = cus_Co;
	document.getElementById("update_cus_Rprsn_PhNr").value = cus_Rprsn_PhNr;
	document.getElementById("update_cus_Mng_PhNr").value = cus_Mng_PhNr;
	document.getElementById("update_cus_Mng_Email").value = cus_Mng_Email;
	document.getElementById("update_cus_Adr").value = cus_Adr;
	document.getElementById("update_cus_Rgstr_Nr").value = cus_Rgstr_Nr;
	$('#update_cus_Pymn_Date').val(cus_Pymn_Date).prop("selected", true);
	$('#update_cus_Clsfc').val(cus_Clsfc).prop("selected", true);
	console.log(cus_Clsfc);
	document.getElementById("update_cus_Co_EstYr").value = cus_Co_EstYr;
}

window.onload = function() {
	$
		.ajax({
			method: "GET",
			url: "customerManageRest/view",
			success: function(data) {
				datas = data;

				table = new Tabulator("#example-table", {
					//페이징
					layout: "fitColumns",
					pagination: "local",
					paginationSize: 20,
					height: "calc(100% - 175px)",
					headerFilterPlaceholder: null,
					selectable: 1, //make rows selectable
					rowClick: function(e, row) {
						// 클릭한 행의 색상을 칠해준다.
						if (element == null) {
							element = row.getElement();
							element.style.color = "red";
							element.style.fontWeight = "bold";
						}
						else {
							element.style.color = "black";
							element.style.fontWeight = "normal";
							row.getElement().style.color = "red";
							row.getElement().style.fontWeight = "bold";
							element = row.getElement();
						}

						rowindex = row.getIndex();

						document.getElementById("update_cus_Code").value = row.getData().cus_Code;
						cus_Code = row.getData().cus_Code;
						document.getElementById("update_cus_Name").value = row.getData().cus_Name;
						cus_Name = row.getData().cus_Name;

						status = row.getData().cus_Status - 1;
						$("#update_cus_Status option:eq(" + status + ")").prop("selected", true);
						cus_Status = status;

						document.getElementById("update_cus_Rprsn").value = row.getData().cus_Rprsn;
						cus_Rprsn = row.getData().cus_Rprsn;
						document.getElementById("update_cus_Mng").value = row.getData().cus_Mng;
						cus_Mng = row.getData().cus_Mng;
						document.getElementById("update_cus_Co").value = row.getData().cus_Co;
						cus_Co = row.getData().cus_Co;
						document.getElementById("update_cus_Rprsn_PhNr").value = row.getData().cus_Rprsn_PhNr;
						cus_Rprsn_PhNr = row.getData().cus_Rprsn_PhNr;
						document.getElementById("update_cus_Mng_PhNr").value = row.getData().cus_Mng_PhNr;
						cus_Mng_PhNr = row.getData().cus_Mng_PhNr;
						document.getElementById("update_cus_Mng_Email").value = row.getData().cus_Mng_Email;
						cus_Mng_Email = row.getData().cus_Mng_Email;
						document.getElementById("update_cus_Adr").value = row.getData().cus_Adr;
						cus_Adr = row.getData().cus_Adr;
						document.getElementById("update_cus_Rgstr_Nr").value = row.getData().cus_Rgstr_Nr;
						cus_Rgstr_Nr = row.getData().cus_Rgstr_Nr;

						//$("#update_cus_Pymn_Date option:eq(0)").prop("selected", true);
						$('#update_cus_Pymn_Date').val(row.getData().cus_Pymn_Date).prop("selected", true);
						cus_Pymn_Date = row.getData().cus_Pymn_Date;
						$('#update_cus_Clsfc').val(row.getData().cus_Clsfc).prop("selected", true);
						cus_Clsfc = row.getData().cus_Clsfc;
						document.getElementById("update_cus_Co_EstYr").value = row.getData().cus_Co_EstYr;
						cus_Co_EstYr = row.getData().cus_Co_EstYr;

						customer_FLAG = true;
					},
					rowDblClick: function(e, row) {
						row.select();

						// 클릭한 행의 색상을 칠해준다.
						if (element == null) {
							element = row.getElement();
							element.style.color = "red";
							element.style.fontWeight = "bold";
						}
						else {
							element.style.color = "black";
							element.style.fontWeight = "normal";
							row.getElement().style.color = "red";
							row.getElement().style.fontWeight = "bold";
							element = row.getElement();
						}

						rowindex = row.getIndex();

						console.log(row.getData());

						document.getElementById("update_cus_Code").value = row.getData().cus_Code;
						cus_Code = row.getData().cus_Code;
						document.getElementById("update_cus_Name").value = row.getData().cus_Name;
						cus_Name = row.getData().cus_Name;

						status = row.getData().cus_Status - 1;
						$("#update_cus_Status option:eq(" + status + ")").prop("selected", true);
						cus_Status = status;

						document.getElementById("update_cus_Rprsn").value = row.getData().cus_Rprsn;
						cus_Rprsn = row.getData().cus_Rprsn;
						document.getElementById("update_cus_Mng").value = row.getData().cus_Mng;
						cus_Mng = row.getData().cus_Mng;
						document.getElementById("update_cus_Co").value = row.getData().cus_Co;
						cus_Co = row.getData().cus_Co;
						document.getElementById("update_cus_Rprsn_PhNr").value = row.getData().cus_Rprsn_PhNr;
						cus_Rprsn_PhNr = row.getData().cus_Rprsn_PhNr;
						document.getElementById("update_cus_Mng_PhNr").value = row.getData().cus_Mng_PhNr;
						cus_Mng_PhNr = row.getData().cus_Mng_PhNr;
						document.getElementById("update_cus_Mng_Email").value = row.getData().cus_Mng_Email;
						cus_Mng_Email = row.getData().cus_Mng_Email;
						document.getElementById("update_cus_Adr").value = row.getData().cus_Adr;
						cus_Adr = row.getData().cus_Adr;
						document.getElementById("update_cus_Rgstr_Nr").value = row.getData().cus_Rgstr_Nr;
						cus_Rgstr_Nr = row.getData().cus_Rgstr_Nr;

						$('#update_cus_Pymn_Date').val(row.getData().cus_Pymn_Date).prop("selected", true);
						cus_Pymn_Date = row.getData().cus_Pymn_Date;
						$('#update_cus_Clsfc').val(row.getData().cus_Clsfc).prop("selected", true);
						cus_Clsfc = row.getData().cus_Clsfc;
						document.getElementById("update_cus_Co_EstYr").value = row.getData().cus_Co_EstYr;
						cus_Co_EstYr = row.getData().cus_Co_EstYr;

						customer_FLAG = true;

						$("#customerModifyModal").modal("show");
					},
					data: datas, //assign data to table
					columns: [ //Define Table Columns
						{
							title: "거래처코드",
							field: "cus_Code",
							headerHozAlign: "center",
							width: 100
						}, {
							title: "거래처명",
							field: "cus_Name",
							width: 150,
							headerHozAlign: "center",
							headerFilter: "input"
						}, {
							title: "상태",
							field: "cus_Status_Name",
							width: 85,
							headerHozAlign: "center",
							headerFilter: "input"
						}, {
							title: "분류",
							field: "cus_Clsfc",
							width: 85,
							headerHozAlign: "center",
							headerFilter: "input"
						}, {
							title: "대표자명",
							field: "cus_Rprsn",
							width: 85,
							headerHozAlign: "center",
							headerFilter: "input"
						}, {
							title: "담당자명",
							field: "cus_Mng",
							width: 85,
							headerHozAlign: "center",
							headerFilter: "input"
						}, {
							title: "회사명",
							field: "cus_Co",
							width: 120,
							headerHozAlign: "center",
							headerFilter: "input"
						}, {
							title: "회사설립연도",
							field: "cus_Co_EstYr",
							width: 100,
							headerHozAlign: "center",
							hozAlign: "right",
							headerFilter: "input"
						}, {
							title: "대표전화번호",
							field: "cus_Rprsn_PhNr",
							width: 110,
							headerHozAlign: "center",
							hozAlign: "right",
							headerFilter: "input"
						}, {
							title: "담당자 전화번호",
							field: "cus_Mng_PhNr",
							width: 130,
							headerHozAlign: "center",
							hozAlign: "right",
							headerFilter: "input"
						}, {
							title: "담당자 이메일",
							field: "cus_Mng_Email",
							width: 200,
							headerHozAlign: "center",
							headerFilter: "input"
						}, {
							title: "주소",
							field: "cus_Adr",
							width: 140,
							headerHozAlign: "center",
							headerFilter: "input"
						}, {
							title: "결제일",
							field: "cus_Pymn_Date",
							width: 75,
							headerHozAlign: "center",
							headerFilter: "input"
						}, {
							title: "사업자등록번호",
							field: "cus_Rgstr_Nr",
							width: 125,
							headerHozAlign: "center",
							headerFilter: "input"
						}

					],
				});
			}
		});

	//SubmenuSelector("2", "13221");
}

// 삭제 기능을 수행하는 함수
function delBtn() {
	// 실제로 DB에서 선택한 행의 데이터를 지운다.
	$.ajax({
		method: "POST",
		data: null,
		url: "customerManageRest/delete?Cus_Code="
			+ cus_Code,
		success: function(data, testStatus) {
		}
	});

	// DB까지 지우고 나면 화면을 새로고침
	location.reload();
}

function modBtn() {
	datas = {
		Cus_Code: document.getElementById("update_cus_Code").value,
		Cus_Name: document.getElementById("update_cus_Name").value,
		Cus_Status: document.getElementById("update_cus_Status").value,
		Cus_Clsfc: document.getElementById("update_cus_Clsfc").value,
		Cus_Rprsn: document.getElementById("update_cus_Rprsn").value,
		Cus_Mng: document.getElementById("update_cus_Mng").value,
		Cus_Co: document.getElementById("update_cus_Co").value,
		Cus_Co_EstYr: document.getElementById("update_cus_Co_EstYr").value,
		Cus_Rprsn_PhNr: document.getElementById("update_cus_Rprsn_PhNr").value,
		Cus_Mng_PhNr: document.getElementById("update_cus_Mng_PhNr").value,
		Cus_Mng_Email: document.getElementById("update_cus_Mng_Email").value,
		Cus_Adr: document.getElementById("update_cus_Adr").value,
		Cus_Pymn_Date: document.getElementById("update_cus_Pymn_Date").value,
		Cus_Rgstr_Nr: document.getElementById("update_cus_Rgstr_Nr").value
	};

	$.ajax({
		method: "POST",
		data: datas,
		url: "customerManageRest/update?data="
			+ encodeURI(JSON.stringify(datas)),
		success: function(data, testStatus) {
		}
	});

	//location.reload();
}

// 수정 모달창에서 취소버튼을 누를때 수정 모달창 데이터를 초기화
function modResetBtn() {
	document.getElementById("update_cus_Code").value = "";
	document.getElementById("update_cus_Name").value = "";
	$("#update_cus_Status option:eq(0)").prop("selected", true);
	document.getElementById("update_cus_Rprsn").value = "";
	document.getElementById("update_cus_Mng").value = "";
	document.getElementById("update_cus_Co").value = "";
	document.getElementById("update_cus_Rprsn_PhNr").value = "";
	document.getElementById("update_cus_Mng_PhNr").value = "";
	document.getElementById("update_cus_Mng_Email").value = "";
	document.getElementById("update_cus_Adr").value = "";
	document.getElementById("update_cus_Rgstr_Nr").value = "";
	$("#update_cus_Pymn_Date option:eq(0)").prop("selected", true);
	$("#update_cus_Clsfc option:eq(0)").prop("selected", true);
	document.getElementById("update_cus_Co_EstYr").value = "";
}

// 입력버튼을 클릭을 할때 모달창을 여는 이벤트
$("#registerModal").click(function() {
	$("#customerRegisterModal").modal("show");
});

// 수정버튼을 클릭을 할때 모달창을 여는 이벤트
if (customer_FLAG) {
	$("#modifyModal").click(function() {
		$("#customerModifyModal").modal("show");
	});
}

// 입력모달창이 열릴때 가장 상단의 input에 포커스를 주는 이벤트
$("#customerRegisterModal").on("shown.bs.modal", function() {
	$("#insert_cus_Code").focus();
});

// 수정모달창이 열릴때 가장 상단의 input에 포커스를 주는 이벤트
$("#customerModifyModal").on("shown.bs.modal", function() {
	$("#update_cus_Name").focus();
});

// 저장할지 말지 여부를 묻는 모달창 열기
function insertModal() {
	$("#insertYesNo").modal("show");
}

function insBtn() {
	var ccode = document.getElementById("insert_cus_Code").value;

	alert(ccode);

	if (ccode == "") {
		alert("거래처코드는 반드시 입력하셔야 합니다.");
		document.getElementById("insert_cus_Code").focus();
		return;
	}

	datas = {
		cus_Code: document.getElementById("insert_cus_Code").value,
		cus_Name: document.getElementById("insert_cus_Name").value,
		cus_Status: document.getElementById("insert_cus_Status").value,
		cus_Clsfc: document.getElementById("insert_cus_Clsfc").value,
		cus_Rprsn: document.getElementById("insert_cus_Rprsn").value,
		cus_Mng: document.getElementById("insert_cus_Mng").value,
		cus_Co: document.getElementById("insert_cus_Co").value,
		cus_Co_EstYr: document.getElementById("insert_cus_Co_EstYr").value,
		cus_Rprsn_PhNr: document.getElementById("insert_cus_Rprsn_PhNr").value,
		cus_Mng_PhNr: document.getElementById("insert_cus_Mng_PhNr").value,
		cus_Mng_Email: document.getElementById("insert_cus_Mng_Email").value,
		cus_Adr: document.getElementById("insert_cus_Adr").value,
		cus_Pymn_Date: document.getElementById("insert_cus_Pymn_Date").value,
		cus_Rgstr_Nr: document.getElementById("insert_cus_Rgstr_Nr").value
	};

	console.log(datas);
	$.ajax({
		method: "POST",
		data: datas,
		url: "customerManageRest/insert?data="
			+ encodeURI(JSON.stringify(datas)),
		success: function(data, testStatus) {
			if (data == "Overlap")
				alert("중복코드를 입력하셨습니다. 다른 코드를 입력해주세요.");
			else if (data == "Success") {
				//alert("저장 완료 하였습니다.");

				location.reload();
			}
		}
	});
}

// 입력 모달창에서 취소버튼을 누를때 입력 모달창 데이터를 초기화
function insResetBtn() {
	document.getElementById("insert_cus_Code").value = "";
	document.getElementById("insert_cus_Name").value = "";
	$("#insert_cus_Status option:eq(0)").prop("selected", true);
	document.getElementById("insert_cus_Rprsn").value = "";
	document.getElementById("insert_cus_Mng").value = "";
	document.getElementById("insert_cus_Co").value = "";
	document.getElementById("insert_cus_Rprsn_PhNr").value = "";
	document.getElementById("insert_cus_Mng_PhNr").value = "";
	document.getElementById("insert_cus_Mng_Email").value = "";
	document.getElementById("insert_cus_Adr").value = "";
	document.getElementById("insert_cus_Rgstr_Nr").value = "";
	$("#insert_cus_Pymn_Date option:eq(0)").prop("selected", true);
	$("#insert_cus_Clsfc option:eq(0)").prop("selected", true);
	document.getElementById("insert_cus_Co_EstYr").value = "";
}