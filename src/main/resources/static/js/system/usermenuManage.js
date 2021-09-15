var dataList = {
	"data": []
}

var dataList2 = {
	"data": []
}

window.onload = function() {
	tableInit1();
	tableInit2();	
	//SubmenuSelector("1", "13118");
}

function tableInit1() {
	$.ajax({
		method: "GET",
		url: "usermenuManageRest/usermenuManageView1",
		success: function(data) {
			datas = data;

			var table = new Tabulator("#example-table1", {
				height: "calc(100% - 175px)",
				data: datas,
				responsiveLayout: "hide",
				movableColumns: true,
				headerFilterPlaceholder: null,
				rowSelectionChanged: function(data, rows) {
					console.log(data);
					dataList.data = data;
					console.log(dataList);
				},
				columns: [
					{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false, width:40},
					{ title: "그룹명", field: "child_TBL_TYPE", headerHozAlign: "center", hozAlign: "right", width: 100, headerFilter:"input" },
					{ title: "프로그램명", field: "new_TBL_CODE", headerHozAlign: "center", hozAlign: "right", width: 150, headerFilter:"input" }
				],
			});
		}
	});
}

function tableInit2() {
	$.ajax({
		method: "GET",
		url: "usermenuManageRest/usermenuManageView2",
		success: function(data) {
			datas = data;
			console.log(datas);

			var table = new Tabulator("#example-table2", {
				height: "calc(100% - 175px)",
				selectable:1, //make rows selectable
				data: datas,
				responsiveLayout: "hide",
				movableColumns: true,
				headerFilterPlaceholder: null,
				rowSelectionChanged: function(data, rows) {
					dataList2.data = data;
				},
				columns: [
					{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false, width:40},
					{ title: "그룹명", field: "group_Name", headerHozAlign: "center", hozAlign: "right", minWidth: 60 , headerFilter:"input"},
					{ title: "프로그램명", field: "program_Name", headerHozAlign: "center", hozAlign: "right", minWidth: 40 , headerFilter:"input"}
				],
			});
		}
	});
}

function insertModal2() {
	$("#insertYesNo").modal("show");
}

function deleteModal() {
	$("#deleteYesNo").modal("show");
}

function insBtn()
{
	if (dataList.data.length <= 0) {
		alert("추가할 행을 선택한 후에 Add버튼을 눌러 주십시오.")
		return;
	}
	
	console.log(JSON.stringify(dataList));
	$.ajax({
				method : "POST",
				data : null,
				url : "usermenuManageRest/insert?dataList="
						+ encodeURI(JSON.stringify(dataList)),
				success : function(data, testStatus) {
					location.reload();
				}
	});
}

function delBtn(){
	if (dataList2.data.length <= 0) {
		alert("삭제할 행을 선택한 후에 Delete버튼을 눌러 주십시오.")
		return;
	}
	
	$.ajax({
				method : "POST",
				data : null,
				url : "usermenuManageRest/delete?dataList="
						+ encodeURI(JSON.stringify(dataList2)),
				success : function(data, testStatus) {
					location.reload();
				}
	});
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