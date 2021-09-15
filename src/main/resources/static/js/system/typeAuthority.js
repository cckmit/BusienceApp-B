var table = null;
var table2 = null;

// DB의 데이터를 변수에 담아서 화면에 뿌여줄 용도
var datas = "";

// 선택한 행을 변수에 담아서 더블클릭한 행에 색깔을 칠하는 변수
var element = null;

// 행을 더블클릭하여서 해당 행의 인덱스를 저장하는 변수
var rowindex = 0;

var element = null;
var dataList = {
	"data": []
}

window.onload = function() {
	$.ajax({
			method: "GET",
			url: "dtl_tbl_select?NEW_TBL_CODE=1",
			success: function(data) {
				datas = data;

				table = new Tabulator(
					"#example-table1",
					{
						layout: "fitColumns",
						height: "calc(100% - 175px)",
						selectable:1, //make rows selectable
						rowClick: function(e, row) {
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

							//document.getElementById("modifyinitbtn").parentNode.removeAttribute("disabled");	
							$("#modifyinitbtn").css(
								{
									"opacity" : "1"
								}
							);
							//document.getElementById("modifyinitbtn").onclick = modifyModalShow;

							console.log("typeAuthorityRest/view1?RIGHTS_USER_TYPE=" + row.getData().child_TBL_NUM);

							$.ajax({
								method: "POST",
								data: null,
								url: "typeAuthorityRest/view1?RIGHTS_USER_TYPE=" + row.getData().child_TBL_NUM,
								success: function(data, testStatus) {
									datas = data;
									console.log(row.getData().child_TBL_NO);
									table2 = new Tabulator("#example-table2", {
										layout: "fitColumns",
										pagination: "local",
										paginationSize: 20,
										height: "calc(100% - 175px)",
										rowSelectionChanged: function(data, rows) {
											dataList.data = data;
											console.log(dataList.data);
										},
										rowDblClick: function(e, row) {
											//e - the click event object
											//row - row component
											console.log(row.getIndex());
											console.log(row.getData().id);
										},
										rowUpdated: function(row) {
											alert("수정");
										},
										data: datas,
										columns: [
											{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false, width:40},
											{ title: "코드", field: "rights_PROGRAM_CODE", hozAlign: "right", width: 70 },
											{ title: "프로그램 명", field: "child_TBL_TYPE", width: 200 },
											{ title: "사용유무", field: "rights_MGMT_USE_STATUS", width: 100, headerHozAlign: "center", hozAlign: "center", formatter: "tickCross", editor: "select", editorParams: { values: { "true": "사용", "false": "미사용" } } }
										],
									});
								}
							});
						},
						rowDblClick: function(e, row) {
							row.select();
						},
						data: datas, //assign data to table
						columns: [ //Define Table Columns
							{ title: "사용자타입", field: "child_TBL_NUM", hozAlign: "right", width: 120 },
							{ title: "타입명", field: "child_TBL_TYPE", width: 120 }
						],
					});

			}
		});

	//SubmenuSelector("1", "13101");
}

function modifyModalShow() {
	$("#modifyYesNo").modal("show");
}

function modBtn() {
	if (dataList.data.length <= 0) {
		alert("수정할 행을 선택한 후에 수정을 시도하여 주십시오.")
		return;
	}

	alert("수정 완료 하였습니다.");

	hiddenInput("permissionFrm", "modifier","admin");
	hiddenInput("permissionFrm", "dataList", JSON.stringify(dataList));
	frmSubmit("permissionFrm", "post", "typeAuthority/update");
}

function frmSubmit(frmid, method, action) {
	frm = document.getElementById(frmid);
	frm.method = method;
	frm.action = action;
	frm.submit();
}

function hiddenInput(frmid, name, value) {
	frm = document.getElementById(frmid);
	var hidden = document.createElement("input");
	hidden.setAttribute("type", "hidden");
	hidden.setAttribute("name", name);
	hidden.setAttribute("value", value);
	frm.appendChild(hidden);
}