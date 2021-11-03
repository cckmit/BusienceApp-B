workOrder_EquipCode = "M001";

var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
	height: "82.5%",
	layout: "fitColumns",
	placeholder: "No Data Set",
	resizableColumns: false,
	rowClick: function(e, row) {
	},
	columns: [
		{ title: "작업지시번호", field: "workOrder_ONo", headerHozAlign: "center", width: 160 },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", width: 100 },
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width: 180 },
		{ title: "규격", field: "product_INFO_STND_1", headerHozAlign: "center", width: 100 },
		{ title: "목표량", field: "workOrder_PQty", headerHozAlign: "center", width: 100, align: "right",
			formatter:function(cell, formatterParams, onRendered){
				return cell.getValue() == null ? cell.getValue() :  cell.getValue().slice(0,-2); //return the contents of the cell;
			},
			visible:false
		},
		{ title: "생산량", field: "workOrder_RQty", headerHozAlign: "center", width: 100, align: "right",
			formatter:function(cell, formatterParams, onRendered){
				return cell.getValue() == null ? cell.getValue() :  cell.getValue().slice(0,-2); //return the contents of the cell;
			}
		},
		{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } }
	],
});

var initstartDate = null;
document.getElementById("startDate").onclick = function() {
	initstartDate = $("#startDate").val();
}

document.getElementById("startDate").onchange = function() {
	startDate = $("#startDate").val();
	endDate = $("#endDate").val();

	if (startDate > endDate) {
		alert("시작일은 끝일보다 클수 없습니다.");
		$("#startDate").val(initstartDate);
		return;
	}

	$.get("workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + workOrder_EquipCode + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
		WorkOrder_tbl.setData(data);
		btnCheck();
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

	$.get("workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + workOrder_EquipCode + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
		WorkOrder_tbl.setData(data);
		btnCheck();
	});
}

document.getElementById("eqselect").onchange = function(e) {
	var target = document.getElementById("eqselect");
	workOrder_EquipCode = target.options[target.selectedIndex].value;
	
	var array = document.getElementsByName("options1");
	array[0].checked = false;
	array[1].checked = false;
	
	$.get("workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + workOrder_EquipCode + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
		WorkOrder_tbl.setData(data);
		
		btnCheck();
	});
}

var visible = false;

function btnCheck(){
 	$.get("workOrderTABRestXO/MI_Searchc?WorkOrder_EquipCode=" + workOrder_EquipCode + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {            
                             
               var array = document.getElementsByName("options1");
               if(data.length==1)
               {
               		document.getElementById("csnum").innerText = data[0].workOrder_ONo;
					array[0].setAttribute("disabled", '');
					array[1].removeAttribute("disabled");
					
					visible = true;
               }
               else
               {
               		document.getElementById("csnum").innerText = "";
               		array[1].setAttribute("disabled", '');
					array[0].removeAttribute("disabled");
					
					visible = false;
               }
 	});
 }
 
document.getElementById("s").onclick = function() {

	PRODUCT_ITEM_CODE = document.getElementById("itselect").options[document.getElementById("itselect").selectedIndex].value;
	
	$.get("workOrderTABRestXO/MI_Searchi?WorkOrder_EquipCode=" + workOrder_EquipCode + "&PRODUCTION_PRODUCT_CODE=" + PRODUCT_ITEM_CODE, function(data) {            
        $.get("workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + workOrder_EquipCode + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
			WorkOrder_tbl.setData(data);
			btnCheck();
		});     
 	});
	
}

document.getElementById("e").onclick = function() {
	$.get("workOrderTABRestXO/MI_Searche?WorkOrder_EquipCode=" + workOrder_EquipCode+"&PRODUCTION_SERIAL_NUM="+document.getElementById("csnum").innerText, function(data) {            
		$.get("workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + workOrder_EquipCode + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
			WorkOrder_tbl.setData(data);
			btnCheck();
		});
 	});
}

$('#PRODUCT_ITEM_NAME').keypress(function(e){
	if(e.keyCode==13) {
		var value = $(this).val()
		//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
		$.ajax({
			method: "GET",
			url: "product_check?PRODUCT_ITEM_CODE=" + value,
			dataType: "json",
			success: function(data) {
				if (data.length == 1) {
					//검색어와 일치하는값이 있는경우
					$('#PRODUCT_ITEM_CODE').val(data[0].product_ITEM_CODE)
					$('#PRODUCT_ITEM_NAME').val(data[0].product_ITEM_NAME)
				} else {
					//검색어와 일치하는값이 없는경우, 팝업창
					itemPopup(value,'input','','sales');
				}
			}
		})
	}
})

window.onload = function(){
 	$.get("workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + workOrder_EquipCode + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
		console.log(data);
		
		WorkOrder_tbl.setData(data);
		
		btnCheck();
	});
}

