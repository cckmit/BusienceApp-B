var initData = null;
var initRow = null;
var workOrder_EquipCode = null;

var WorkOrder_tbl1 = new Tabulator("#WorkOrder_tbl1", {
	height: "95%",
	placeholder: "No Data Set",
	resizableColumns: false,
	rowClick: function(e, row) {
		WorkOrder_tbl1.deselectRow();
		row.select();

		radio_select(row.getData().workOrder_WorkStatus);
		initData = row.getData();
		workOrder_EquipCode = initData.workOrder_EquipCode;
		initRow = row;

		//var array = document.getElementsByName("options1");
		//array[array.length - 1].setAttribute("disabled", '');
	},
	columns: [
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center"},
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center"},
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center"},
		{ title: "규격", field: "product_INFO_STND_1", headerHozAlign: "center"},
		{ title: "지시수량", field: "workOrder_PQty", headerHozAlign: "center", align: "right", width: 100, visible:false },
		{ title: "작업지시일", field: "workOrder_OrderTime", align: "right", headerHozAlign: "center"},
		{ title: "작업지시완료일", field: "workOrder_CompleteOrderTime", align: "right", headerHozAlign: "center"},
		{ title: "접수일", field: "workOrder_ReceiptTime", align: "right", headerHozAlign: "center"},
		{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center", width: 140},
		{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center", width: 140}
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

	$.get("../workOrderTABRestXO/MI_Searchd2?WorkOrder_EquipCode=M001" + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
		WorkOrder_tbl1.setData(data);
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

	$.get("../workOrderTABRest/MI_Searchd?WorkOrder_EquipCode=" + workOrder_EquipCode + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
		WorkOrder_tbl1.setData(data);
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

	$.get("../workOrderTABRestXO/MI_Searchd2?WorkOrder_EquipCode=M001" + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
		WorkOrder_tbl1.setData(data);
	});
}

function radio_select(value) {
	document.getElementsByName("options1").forEach(e => e.removeAttribute("disabled", ''));

	document.getElementsByName('options1')[0].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";
	document.getElementsByName('options1')[1].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";
	document.getElementsByName('options1')[2].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";

	if (value === "242") {
		document.getElementsByName('options1')[0].checked = true;
		document.getElementsByName('options1')[0].focus();
		document.getElementsByName('options1')[0].style.color = "red";
		document.getElementsByName('options1')[0].nextElementSibling.style.backgroundColor = "red";
	}
	else if (value === "243") {
		document.getElementsByName('options1')[1].checked = true;
		document.getElementsByName('options1')[1].focus();
		document.getElementsByName('options1')[1].nextElementSibling.style.backgroundColor = "red";
	}
	else if (value === "244") {
		document.getElementsByName('options1')[2].checked = true;
		document.getElementsByName('options1')[2].focus();
		document.getElementsByName('options1')[2].nextElementSibling.style.backgroundColor = "red";
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
			if (this.getAttribute("value") === "245") {
				alert("미접수된 데이터는 작업완료를 선택 할 수 없습니다.");
				radio_select("242");
				return;
			}
			
			if (this.getAttribute("value") === "244") {
				$.get("../workOrderListRest/OrderUpdate?workOrder_ONo=" + initData.workOrder_ONo + "&workOrder_EquipCode=" + workOrder_EquipCode + "&Start=t", function(data) {
					if (data === "OK") {
						alert("해당 호기에 이미 작업시작이 된 데이터가 존재합니다.");
						radio_select("242");
						return;
					}
					else {
						//MI_Search2
						location.href = "/tablet/workOrderStartBB?code="+document.getElementById("eqselect").value;
					}
				});
			}
			
			if (this.getAttribute("value") === "243"){
				$.get("../workOrderListRest/OrderUpdate?workOrder_ONo=" + initData.workOrder_ONo, function(data) {
					//Equip_Select("M001");
					//console.log(initRow.getData());
					initRow.update({ "workOrder_ReceiptTime": data.workOrder_ReceiptTime, "workOrder_WorkStatus": data.workOrder_WorkStatus });
					initData = data;
				});
			}
		}
		// 접수완료
		else if (initData.workOrder_WorkStatus === "243") {
			if (this.getAttribute("value") === "245") {
				alert("접수완료된 데이터는 작업시작을 선택하여 주십시오.");
				radio_select("243");
				return;
			}

			if (this.getAttribute("value") === "242") {
				$.get("../workOrderListRest/OrderUpdate2?workOrder_ONo=" + initData.workOrder_ONo, function(data) {
					initRow.update({ "workOrder_ReceiptTime": null, "workOrder_WorkStatus": data.workOrder_WorkStatus });
					initData = data;
				});
				return;
			}

			if (this.getAttribute("value") === "244") {
				//alert(initData.workOrder_EquipCode);
				//alert(workOrder_EquipCode);
				
				var datas = {
				WorkOrder_ONo : initData.workOrder_ONo,
				WorkOrder_EquipCode : workOrder_EquipCode,
				WorkOrder_WorkStatus : '244'
				}
				$.ajax({
					method : "get",
					url : "../workOrderTABRestXO/MI_Searche",
					data : datas,
					success : function(data) {
						if (data) {
							location.href = "/tablet/workOrderStartBB?code="+document.getElementById("eqselect").value;
						}else{
							alert("해당 호기에 이미 작업시작이 된 데이터가 존재합니다.");
							radio_select("243");
							return;
						}
					}
				});
			/*
				$.get("../workOrderListRest/OrderUpdate?workOrder_ONo=" + initData.workOrder_ONo + "&workOrder_EquipCode=" + workOrder_EquipCode, function(data) {
					if (data === "OK") {
						alert("해당 호기에 이미 작업시작이 된 데이터가 존재합니다.");
						radio_select("243");
						return;
					}
					else {
						//MI_Search2
						location.href = "/tablet/workOrderStartBB?code="+document.getElementById("eqselect").value;

						
						$.get("WorkOrderTABRest/MI_Search2?workOrder_ONo=" + initData.workOrder_ONo, function(data) {
							initRow.update({ "workOrder_StartTime": data.workOrder_StartTime, "workOrder_WorkStatus": data.workOrder_WorkStatus });
							initData = data;

							location.href = "/workOrderStartBB?code="+data.workOrder_EquipCode;
						});
						
					}
				});*/
			}
		}
		// 작업시작
		else if (initData.workOrder_WorkStatus === "244") {

			if (this.getAttribute("value") === "242" || this.getAttribute("value") === "243") {
				alert("작업시작된 데이터는 작업완료를 선택하여 주십시오.");
				radio_select("244");
				return;
			}

			$.get("../workOrderListRest/OrderUpdate2?workOrder_ONo=" + initData.workOrder_ONo + "&workOrder_EquipCode=" + initData.workOrder_EquipCode, function(data) {
				//MI_Search2
				$.get("../workOrderTABRest/MI_Search2?workOrder_ONo=" + initData.workOrder_ONo, function(data) {
					initRow.update({ "workOrder_CompleteTime": data.workOrder_CompleteTime, "workOrder_WorkStatus": data.workOrder_WorkStatus });
					initData = data;
					document.getElementsByName("options1").forEach(e => e.setAttribute("disabled", ''));
					
					$.get("../workOrderTABRestXO/MI_Searchd2?WorkOrder_EquipCode="+document.getElementById("eqselect").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
						WorkOrder_tbl1.setData(data);
					});
				});
			});
		}
	}
});

var totaldata = null;
var viewdata = [];

function option2click(n){
	document.getElementsByName('options2')[0].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";
	document.getElementsByName('options2')[1].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";
	document.getElementsByName('options2')[2].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";
	document.getElementsByName('options2')[3].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";
	document.getElementsByName('options2')[4].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";
	n.style.backgroundColor = "red";

	id = n.getAttribute("for").slice(0, -1);

	if(id === "all")
	{
		WorkOrder_tbl1.setData(totaldata);
	}
	else if(id === "245")
	{
		//WorkOrder_EquipCode
		$.get("../workOrderTABRestXO/MI_End_Search?WorkOrder_EquipCode="+document.getElementById("eqselect").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
			WorkOrder_tbl1.setData(data);
		});
	}
	else
	{
		viewdata = [];
	
		for(i=0;i<totaldata.length;i++)
		{
			if(totaldata[i].workOrder_WorkStatus === id)
			{
				viewdata.push(totaldata[i]);
			}
		}
		
		WorkOrder_tbl1.setData(viewdata);
	}
}

window.onload = function() {
	document.getElementById("ko").style.height = window.innerHeight - document.getElementById("ko").offsetTop + "px";
	document.getElementById("WorkOrder_tbl1").style.height = window.innerHeight - document.getElementById("ko").offsetTop - 10 + "px";

	workOrder_EquipCode = "M001";

	document.getElementsByName('options2')[0].nextElementSibling.style.backgroundColor = "red";

	$.get("../workOrderTABRestXO/MI_Searchd2?WorkOrder_EquipCode="+document.getElementById("eqselect").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
		totaldata = data;
		WorkOrder_tbl1.setData(data);
	});
}

		function lang_convert(n){
			if(n.id==="kor")
			{
				document.getElementById("kor").style.background = "red";
				document.getElementById("eng").style.background = "white";

				document.getElementById("t1").innerHTML = "작업 관리";
				document.getElementById("t2").innerHTML = "설&nbsp;&nbsp;&nbsp;비";
				document.getElementById("t3").innerHTML = "작업관리";
				
				document.getElementById("t4").innerHTML = "수정";
				document.getElementById("t4").style.fontSize = "45px";
				document.getElementById("242c").innerHTML = "미접수";
				document.getElementById("243c").innerHTML = "접수완료";
				document.getElementById("244c").innerHTML = "작업시작";
				document.getElementById("245c").innerHTML = "작업완료";
				
				for(i=242;i<246;i++)
				{
					document.getElementById(i+"c").style.fontSize = "45px";
				}
				
				document.getElementById("t5").innerHTML = "조회";
				document.getElementById("t5").style.fontSize = "45px";
				
				document.getElementById("t6").innerHTML = "전체";
				document.getElementById("t6").style.fontSize = "45px";

				document.getElementById("t66").style.width = "";
				document.getElementById("t66").style.marginLeft = "0px";
				document.getElementById("t66").innerHTML = "전체";
				document.getElementById("t66").style.fontSize = "45px";
				
				document.getElementById("242s").innerHTML = "미접수";
				document.getElementById("243s").innerHTML = "접수완료";
				document.getElementById("244s").innerHTML = "작업시작";
				document.getElementById("245s").innerHTML = "작업완료";
				
				for(i=242;i<246;i++)
				{
					document.getElementById(i+"s").style.fontSize = "45px";
				}

				for(i=2;i<4;i++)
				{
					document.getElementById("t"+i).style.fontSize = "45px";
				}
			}
			else if(n.id==="eng")
			{
				document.getElementById("kor").style.background = "white";
				document.getElementById("eng").style.background = "red";

				document.getElementById("t1").innerHTML = "Work Order";
				document.getElementById("t2").innerHTML = "Machinery";
				document.getElementById("t3").innerHTML = "Work Order";
				
				document.getElementById("t4").innerHTML = "Modify";
				document.getElementById("t4").style.fontSize = "35px";
				document.getElementById("242c").innerHTML = "Not accepted";
				document.getElementById("243c").innerHTML = "Accepted";
				document.getElementById("244c").innerHTML = "Start time of work";
				document.getElementById("245c").innerHTML = "End time of work";
				
				for(i=242;i<246;i++)
				{
					document.getElementById(i+"c").style.fontSize = "35px";
				}
				
				document.getElementById("t5").innerHTML = "Select&nbsp;";
				document.getElementById("t5").style.fontSize = "35px";
				
				document.getElementById("t6").innerHTML = "ALL";
				document.getElementById("t6").style.fontSize = "35px";
				
				document.getElementById("t66").innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				//document.getElementById("t66").style.width = "4%";
				document.getElementById("t66").style.marginLeft = "6px";
				//document.getElementById("t66").style.fontSize = "35px";
				
				document.getElementById("242s").innerHTML = "Not accepted";
				document.getElementById("243s").innerHTML = "Accepted";
				document.getElementById("244s").innerHTML = "Start time of work";
				document.getElementById("245s").innerHTML = "End time of work";
				
				for(i=242;i<246;i++)
				{
					document.getElementById(i+"s").style.fontSize = "35px";
				}

				for(i=2;i<4;i++)
				{
					document.getElementById("t"+i).style.fontSize = "40px";
				}
			}
		}