var initData = null;
var initRow = null;
var workOrder_EquipCode = null;

var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
	//페이징
	pagination: "local",
	paginationSize: 10,
	paginationAddRow: "table",
	height: "95%",
	layout: "fitColumns",
	placeholder: "No Data Set",
	resizableColumns: false,
	rowClick: function(e, row) {
		WorkOrder_tbl.deselectRow();
		row.select();

		radio_select(row.getData().workOrder_WorkStatus);
		initData = row.getData();
		workOrder_EquipCode = initData.workOrder_EquipCode;
		initRow = row;

		//var array = document.getElementsByName("options1");
		//array[array.length - 1].setAttribute("disabled", '');
	},
	columns: [
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center", width: 180 },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", width: 100 },
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width: 180 },
		{ title: "규격", field: "product_INFO_STND_1", headerHozAlign: "center", width: 100 },
		{ title: "지시수량", field: "workOrder_PQty", headerHozAlign: "center", align: "right", width: 100, visible:false },
		{ title: "작업지시일", field: "workOrder_OrderTime", align: "right", headerHozAlign: "center", width: 160},
		{ title: "작업지시완료일", field: "workOrder_CompleteOrderTime", align: "right", headerHozAlign: "center", width: 160},
		{ title: "접수일", field: "workOrder_ReceiptTime", align: "right", headerHozAlign: "center", width: 160},
		{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center", width: 160},
		{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center", width: 160}
	],
});

document.getElementById("eqselect").onchange = function(e) {
	var today = new Date();

	var tomorrow = new Date();
	tomorrow = new Date(tomorrow.setDate(tomorrow.getDate() + 1));

	$('.today').val(today.toISOString().substring(0, 10));
	$('.tomorrow').val(tomorrow.toISOString().substring(0, 10));

	document.getElementsByName("options1").forEach(e => e.removeAttribute("disabled", ''));
	document.getElementsByName("options1").forEach(e => e.checked = false);
	//var array = document.getElementsByName("options1");
	//array[array.length - 1].setAttribute("disabled", '');

	var target = document.getElementById("eqselect");
	workOrder_EquipCode = target.options[target.selectedIndex].value;

	$.get("workOrderTABRestXO/MI_Searchd2?WorkOrder_EquipCode=M001" + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
		WorkOrder_tbl.setData(data);
	});
}

var initstartDate = null;
document.getElementById("startDate").onclick = function() {
	initstartDate = $("#startDate").val();
}

document.getElementById("startDate").onchange = function() {
	var array = document.getElementsByName("options1");
	array[array.length - 1].setAttribute("disabled", '');

	startDate = $("#startDate").val();
	endDate = $("#endDate").val();

	if (startDate > endDate) {
		alert("시작일은 끝일보다 클수 없습니다.");
		$("#startDate").val(initstartDate);
		return;
	}

	$.get("workOrderTABRest/MI_Searchd?WorkOrder_EquipCode=" + workOrder_EquipCode + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
		WorkOrder_tbl.setData(data);
	});
}

var initendDate = null;
document.getElementById("endDate").onclick = function() {
	initendDate = $("#endDate").val();
}

document.getElementById("endDate").onchange = function() {
	startDate = $("#startDate").val();
	endDate = $("#endDate").val();

	if (startDate > endDate) {
		alert("시작일은 끝일보다 클수 없습니다.");
		$("#endDate").val(initendDate);
		return;
	}

	$.get("workOrderTABRestXO/MI_Searchd2?WorkOrder_EquipCode=M001" + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
		WorkOrder_tbl.setData(data);
	});
}

function radio_select(value) {
	document.getElementsByName("options1").forEach(e => e.removeAttribute("disabled", ''));

	if (value === "242") {
		document.getElementsByName('options1')[0].checked = true;
		document.getElementsByName('options1')[0].focus();
	}
	else if (value === "243") {
		document.getElementsByName('options1')[1].checked = true;
		document.getElementsByName('options1')[1].focus();
	}
	else if (value === "244") {
		document.getElementsByName('options1')[2].checked = true;
		document.getElementsByName('options1')[2].focus();
	}
	else if (value === "245") {
		document.getElementsByName('options1')[3].checked = true;
		document.getElementsByName('options1')[3].focus();
		document.getElementsByName("options1").forEach(e => e.setAttribute("disabled", ''));
	}
}

$('input[type=radio][name=options1]').change(function() {
	if (initData == null)
		return;

	if (initData.workOrder_WorkStatus !== this.getAttribute("value")) {
		// 미접수
		if (initData.workOrder_WorkStatus === "242") {
			if (this.getAttribute("value") === "245" || this.getAttribute("value") === "244") {
				alert("미접수된 데이터는 접수완료를 선택하여 주십시오.");
				radio_select("242");
				return;
			}

			$.get("workOrderListRest/OrderUpdate?workOrder_ONo=" + initData.workOrder_ONo, function(data) {
				//Equip_Select("M001");
				//console.log(initRow.getData());
				initRow.update({ "workOrder_ReceiptTime": data.workOrder_ReceiptTime, "workOrder_WorkStatus": data.workOrder_WorkStatus });
				initData = data;
			});
		}
		// 접수완료
		else if (initData.workOrder_WorkStatus === "243") {
			if (this.getAttribute("value") === "245") {
				alert("접수완료된 데이터는 작업시작을 선택하여 주십시오.");
				radio_select("243");
				return;
			}

			if (this.getAttribute("value") === "242") {
				$.get("workOrderListRest/OrderUpdate2?workOrder_ONo=" + initData.workOrder_ONo, function(data) {
					initRow.update({ "workOrder_ReceiptTime": null, "workOrder_WorkStatus": data.workOrder_WorkStatus });
					initData = data;
				});
				return;
			}

			if (this.getAttribute("value") === "244") {
				//alert(initData.workOrder_EquipCode);
				//alert(workOrder_EquipCode);

				$.get("workListRest/OrderUpdate?workOrder_ONo=" + initData.workOrder_ONo + "&workOrder_EquipCode=" + workOrder_EquipCode, function(data) {
					if (data === "OK") {
						alert("해당 호기에 이미 작업시작이 된 데이터가 존재합니다.");
						radio_select("243");
						return;
					}
					else {
						//MI_Search2
						location.href = "/workOrderStartBB?code="+document.getElementById("eqselect").value;

						/* 
						$.get("WorkOrderTABRest/MI_Search2?workOrder_ONo=" + initData.workOrder_ONo, function(data) {
							initRow.update({ "workOrder_StartTime": data.workOrder_StartTime, "workOrder_WorkStatus": data.workOrder_WorkStatus });
							initData = data;

							location.href = "/workOrderStartBB?code="+data.workOrder_EquipCode;
						});
						*/
					}
				});
			}
		}
		// 작업시작
		else if (initData.workOrder_WorkStatus === "244") {

			if (this.getAttribute("value") === "242" || this.getAttribute("value") === "243") {
				alert("작업시작된 데이터는 작업완료를 선택하여 주십시오.");
				radio_select("244");
				return;
			}

			$.get("workListRest/OrderUpdate2?workOrder_ONo=" + initData.workOrder_ONo + "&workOrder_EquipCode=" + initData.workOrder_EquipCode, function(data) {
				//MI_Search2
				$.get("workOrderTABRest/MI_Search2?workOrder_ONo=" + initData.workOrder_ONo, function(data) {
					initRow.update({ "workOrder_CompleteTime": data.workOrder_CompleteTime, "workOrder_WorkStatus": data.workOrder_WorkStatus });
					initData = data;
					document.getElementsByName("options1").forEach(e => e.setAttribute("disabled", ''));
					
					$.get("workOrderTABRestXO/MI_Searchd2?WorkOrder_EquipCode="+document.getElementById("eqselect").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
						WorkOrder_tbl.setData(data);
					});
				});
			});
		}
	}
});

window.onload = function() {
	//var array = document.getElementsByName("options1");
	//array[array.length - 1].setAttribute("disabled", '');

	//document.getElementById("246").style.display = "none";
	//document.getElementById("246c").style.display = "none";

	workOrder_EquipCode = "M001";

	/* 
	$.get("workOrderTABRest/MI_Search1?WorkOrder_EquipCode=M001", function(data) {
		WorkOrder_tbl.setData(data);
	});
	*/

	$.get("workOrderTABRestXO/MI_Searchd2?WorkOrder_EquipCode="+document.getElementById("eqselect").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
		WorkOrder_tbl.setData(data);
	});
}

document.getElementById("move").onclick = function(){
	move();
}