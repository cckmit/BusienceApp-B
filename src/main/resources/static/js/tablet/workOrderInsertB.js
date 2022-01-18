var initData = null;

var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
	height: "95%",
	placeholder: "No Data Set",
	resizableColumns: false,
	layoutColumnsOnNewData : true,
	rowClick: function(e, row) {
		WorkOrder_tbl.deselectRow();
		row.select();

		radio_select(row.getData().workOrder_WorkStatus);
		initData = row.getData();
	},
	columns: [
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center"},
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center", visible: false},
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center"},
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center"},
		{ title: "규격", field: "workOrder_Item_STND_1", headerHozAlign: "center"},
		{ title: "지시수량", field: "workOrder_PQty", headerHozAlign: "center", align: "right", visible:false },
		{ title: "작업지시일", field: "workOrder_OrderTime", align: "right", headerHozAlign: "center", width: 155,
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm"}},
		{ title: "접수일", field: "workOrder_ReceiptTime", align: "right", headerHozAlign: "center", width: 155,
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm"}},
		{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center", width: 155,
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm"}},
		{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center", width: 155,
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm"}}
	],
});

//라디오버튼 기능
function radio_select(value) {
	document.getElementsByName("options1").forEach(e => e.removeAttribute("disabled", ''));

	document.getElementsByName('options1')[0].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";
	document.getElementsByName('options1')[1].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";
	document.getElementsByName('options1')[2].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";

	if (value === "242") {
		document.getElementsByName('options1')[0].checked = true;
		document.getElementsByName('options1')[0].focus();
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

//라디오버튼 수정 기능
$('input[type=radio][name=options1]').change(function() {

	if (initData == null)
		return;

	if (initData.workOrder_WorkStatus !== this.getAttribute("value")) {
		// 미접수 -> 접수완료
		if (initData.workOrder_WorkStatus === "242") {
			if(this.getAttribute("value") === "243"){
				var datas = {
					WorkOrder_ONo : WorkOrder_tbl.getData("selected")[0].workOrder_ONo,
					WorkOrder_EquipCode : $("#eqselect").val(),
					WorkOrder_WorkStatus_Name : "Y"
				}
				
				$.ajax({
					method : "get",
					url : "../workOrderTABRestXO/workOrderUpdate",
					data : datas,
					success : function(data) {
						if (data) {
							WorkOrder_tbl.getRows("selected")[0].update(
								{workOrder_ReceiptTime: today.toISOString().substring(0, 10)}
							)
								
						}else{
						}
					}
				});
			}else{
				alert($("#"+$(this).val()+"c").text()+"을(를) 선택 할 수 없습니다.")
			}
		}
		
		// 접수완료 -> 작업시작
		else if (initData.workOrder_WorkStatus === "243") {
			if(this.getAttribute("value") === "244"){
				var datas = {
					WorkOrder_ONo : WorkOrder_tbl.getData("selected")[0].workOrder_ONo,
					WorkOrder_EquipCode : $("#eqselect").val(),
					WorkOrder_WorkStatus_Name : "S"
				}
				
				$.ajax({
					method : "get",
					url : "../workOrderTABRestXO/workOrderUpdate",
					data : datas,
					success : function(data) {
						if (data) {
							WorkOrder_tbl.getRows("selected")[0].update(
								{workOrder_StartTime: today.toISOString().substring(0, 10)}
							)
						}else{							
							alert("작업 중인 데이터가 있습니다.");
						}
					}
				});
			}else{
				alert($("#"+$(this).val()+"c").text()+"을(를) 선택 할 수 없습니다.");
			}
		}
		// 작업시작 -> 작업완료
		else if (initData.workOrder_WorkStatus === "244") {
			if(this.getAttribute("value") === "245"){
				var datas = {
					WorkOrder_ONo : WorkOrder_tbl.getData("selected")[0].workOrder_ONo,
					WorkOrder_EquipCode : $("#eqselect").val(),
					WorkOrder_PQty : WorkOrder_tbl.getData("selected")[0].workOrder_PQty,
					WorkOrder_WorkStatus_Name : "E"
				}
				
				$.ajax({
					method : "get",
					url : "../workOrderTABRestXO/workOrderUpdate",
					data : datas,
					success : function(data) {
						if (data) {
							WorkOrder_tbl.getRows("selected")[0].update(
								{workOrder_CompleteTime: today.toISOString().substring(0, 10)}
							)
						}else{
						}
					}
				});
			}else{
				alert($("#"+$(this).val()+"c").text()+"을(를) 선택 할 수 없습니다.");
			}
		}
	}
});

//라디오버튼 조회기능
function option2click(n){
	document.getElementsByName('options2')[0].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";
	document.getElementsByName('options2')[1].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";
	document.getElementsByName('options2')[2].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";
	document.getElementsByName('options2')[3].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";
	document.getElementsByName('options2')[4].nextElementSibling.style.backgroundColor = "rgb(237, 237, 237)";
	n.style.backgroundColor = "red";

	id = n.getAttribute("for").slice(0, -1);
	
	WOTS_Search(id);
}

//해당설비 완료리스트 이번달치
function WOTS_Search(workStatus){
	var datas = {
			machineCode : $("#eqselect").val(),
			startDate : today.toISOString().substring(0, 7)+"-01",
			endDate : nextmonth.toISOString().substring(0, 7)+"-01",
			statusCodeArr : ""
		}
	if(workStatus == "all"){
		datas.statusCodeArr = ["242","243","244"]
	}else{
		datas.statusCodeArr = [workStatus]
	}
	
	WorkOrder_tbl.setData("../workOrderTABRestXO/WOT_Search",datas)
	.then(function(){
	})
}

window.onload = function() {
	document.getElementById("ko").style.height = window.innerHeight - document.getElementById("ko").offsetTop + "px";
	document.getElementById("WorkOrder_tbl").style.height = window.innerHeight - document.getElementById("ko").offsetTop - 10 + "px";

	document.getElementsByName('options2')[0].nextElementSibling.style.backgroundColor = "red";

	WOTS_Search("all")
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
				document.getElementById("t66").style.marginLeft = "6px";
				
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

function move(){
	location.href = "/tablet/workOrderTablet?machineCode="+$("#eqselect").val();
}

//설비 선택박스 선택시 화면 변경
$("#eqselect").on("change", function(){
	location.href = "/tablet/workOrderInsertB?machineCode="+$("#eqselect").val();
});