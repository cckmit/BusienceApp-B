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
			url: "userManageRest/userManageRestSelect",
			success: function(data) {
				datas = data;

				table = new Tabulator("#example-table1",{
						layout: "fitColumns",
						height: "calc(100% - 175px)",
						selectable:1, //make rows selectable
						headerFilterPlaceholder: null,
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
								
							$("#modifyinitbtn").css(
								{
									"opacity" : "1"
								}
							);
							document.getElementById("modifyinitbtn").onclick = modifyModalShow;

							$.ajax({
								method: "GET",
								data: null,
								url: "menuManageRest/view?MENU_USER_CODE=" + row.getData().user_CODE,
								success: function(data, testStatus) {
									datas = data;

									table2 = new Tabulator("#example-table2", {
										layout: "fitColumns",
										rowSelectionChanged: function(data, rows) {
											dataList.data = data;
											console.log(dataList.data);
										},
										rowUpdated: function(row) {
											alert("수정");
										},
										height: "calc(100% - 175px)",
										data: datas,
										columns: [
											{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false, width:40},
											{ title: "코드", field: "menu_PROGRAM_CODE", hozAlign: "right", width: 70, headerFilter:"input" },
											{ title: "프로그램 명", field: "menu_PROGRAM_NAME", hozAlign: "left", headerHozAlign: "center",headerFilter:"input" }
											, {
												title: "읽기",
												field: "menu_READ_USE_STATUS",
												headerHozAlign: "center",
												width: 70,
												hozAlign: "center",
												formatter: "tickCross",
												editor: "select",
												editorParams: { values: { "true": "사용", "false": "미사용" } }
											}
											, {
												title: "쓰기",
												field: "menu_WRITE_USE_STATUS",
												headerHozAlign: "center",
												width: 70,
												hozAlign: "center",
												formatter: "tickCross",
												editor: "select",
												editorParams: { values: { "true": "사용", "false": "미사용" } }
											}
											, {
												title: "삭제",
												field: "menu_DEL_USE_STATUS",
												headerHozAlign: "center",
												width: 70,
												hozAlign: "center",
												formatter: "tickCross",
												editor: "select",
												editorParams: { values: { "true": "사용", "false": "미사용" } }
											}
											, {
												title: "사용유무",
												field: "menu_MGMT_USE_STATU",
												headerHozAlign: "center",
												width: 100,
												hozAlign: "center",
												formatter: "tickCross",
												editor: "select",
												editorParams: { values: { "true": "사용", "false": "미사용" } }
											}

										],
									});
								}
							});
						},
						rowDblClick: function(e, row) {
							row.select();
						},
						height:"calc(100% - 175px)", // set height of table (in CSS or here), this enables the Virtual DOM and improves render speed dramatically (can be any valid css height value)
						data: datas, //assign data to table
						columns: [ //Define Table Columns
							{
								title: "사용자 코드",
								field: "user_CODE",
								headerHozAlign: "center",
								width: 120,
								headerFilter:"input"
							},
							{
								title: "사용자 명",
								field: "user_NAME",
								headerHozAlign: "center",
								width: 110,
								headerFilter:"input"
							},
							{
								title: "부서",
								field: "dept_NAME",
								headerHozAlign: "center",
								width: 100,
								headerFilter:"input"
							},
							{
								title: "타입",
								field: "user_TYPE_NAME",
								hozAlign: "right",
								headerHozAlign: "center",
								width: 100,
								headerFilter:"input"
							}, {
								title: "사용유무",
								field: "user_USE_STATUS",
								headerHozAlign: "center",
								width: 110,
								headerHozAlign: "center",
								hozAlign: "center",
								formatter: "tickCross",
								editor: "select",
								editorParams: { values: {} }
							}, {
								title: "수정일자",
								field: "user_MODIFY_D",
								headerHozAlign: "center",
								hozAlign: "right",
								formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"},
								width: 150
							}, {
								title: "수정자",
								field: "user_MODIFIER",
								headerHozAlign: "center",
							}
						],
					});
			}
		});
	//SubmenuSelector("1", "13106");
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

	hiddenInput("permissionFrm", "modifier","test");
	hiddenInput("permissionFrm", "dataList", JSON.stringify(dataList));
	frmSubmit("permissionFrm", "post", "menuManage/update");
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