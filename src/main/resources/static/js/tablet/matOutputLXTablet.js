var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
	height:"75%",
	placeholder: "No Data Set",
	resizableColumns: false,
	rowClick: function(e, row) {
	},
	columns: [
		{title:"순번", field:"id", headerHozAlign: "center",  hozAlign: "center"},
		{title:"출고일자", field:"outMat_Date", headerHozAlign:"center", hozAlign:"left", width:150, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm"},hozAlign:"right",width:360},
		 {title:"출고구분", field:"outMat_Send_Clsfc_Name", headerHozAlign:"center", hozAlign:"left"},
		 {title:"부서명", field:"outMat_Dept_Name", headerHozAlign:"center", hozAlign:"left", width:100,visible:false},
		{title:"수취인", field:"outMat_Consignee_Name", headerHozAlign:"center", hozAlign:"left", width:100,visible:false},
		 {title:"품목코드", field:"outMat_Code", headerHozAlign:"center", hozAlign:"left"},
		 {title:"품명", field:"outMat_Name", headerHozAlign:"center",hozAlign:"left"},
		 {title:"규격", field:"outMat_STND_1", headerHozAlign:"center",hozAlign:"left"},
		 {title:"단위", field:"outMat_UNIT", headerHozAlign:"center", hozAlign:"left"},
		 {title:"수량", field:"outMat_Qty", headerHozAlign:"center", hozAlign:"right"},
		 {title:"등록자", field:"outMat_Modifier", headerHozAlign:"center", hozAlign:"left",visible:false},
		 {title:"등록일자", field:"outMat_dInsert_Time", headerHozAlign:"center", hozAlign:"left", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"},hozAlign:"right",width:180,visible:false}
	],
});

function popbtnSelect(data,table)
{
	$.get("/tablet/matOutputLXTabletRest/Out_Clsfc",data,function(data2){
		console.log(data2);

		var list = document.getElementsByClassName("list-group-item");

		for(i=0;i<list.length;i++)
		{
			if(data2.product_ITEM_CLSFC_1.trim() === list[i].innerHTML.trim())
			{
				list_click(list[i]);

				setTimeout(function(){
					for(i=0;i<table.getRows().length;i++)
					{
						//console.log(itemManageTable.getRows()[i].getData());

						if(data2.product_ITEM_NAME.trim() === table.getRows()[i].getData().product_ITEM_NAME)
						{
							table.deselectRow();
							table.getRows()[i].select();
							console.log(table.getRows()[i].getData());
							break;
						}
					}
				},500);

				break;
			}
		}
	});
}

document.getElementById("popbtn").onclick = function(){
	data = {
		PRODUCT_ITEM_CLSFC : 'PRODUCT_ITEM_CLSFC_1',
		PRODUCT_ITEM_NAME : document.getElementById("Item_Word").value
	}

	popbtnSelect(data,itemManageTable);
}

document.getElementById("Item_Word").onkeydown = function(){
	if(event.keyCode==13)
	{
		event.preventDefault();
		data = {
			PRODUCT_ITEM_CLSFC : 'PRODUCT_ITEM_CLSFC_1',
			PRODUCT_ITEM_NAME : document.getElementById("Item_Word").value
		}
	
		popbtnSelect(data,itemManageTable);
	}
}

document.getElementById("popbtn2").onclick = function(){
	data = {
		PRODUCT_ITEM_CLSFC : 'PRODUCT_ITEM_CLSFC_2',
		PRODUCT_ITEM_NAME : document.getElementById("Item_Word2").value
	}

	popbtnSelect(data,itemManageTable2);
}

document.getElementById("Item_Word2").onkeydown = function(){
	if(event.keyCode==13)
	{
		event.preventDefault();
		data = {
			PRODUCT_ITEM_CLSFC : 'PRODUCT_ITEM_CLSFC_2',
			PRODUCT_ITEM_NAME : document.getElementById("Item_Word2").value
		}
	
		popbtnSelect(data,itemManageTable2);
	}
}

//검색
function search(){
	data = {
		startDate : $("#matOutputList_startDate").val(),
		endDate : $("#matOutputList_endDate").val(),
		outMat_Code : "",
		outMat_Send_Clsfc_Name : "all",
		outMat_Dept_Name : "all"
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "../matOutputReportLXRest/tablet/MO_ListView?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			WorkOrder_tbl.setData(data);
		}
	});
}

function qtyselectFun()
{
	data = {
		code : document.getElementById("pdselect2").getAttribute("code")
	};

	$.get("/tablet/matOutputLXTabletRest/Current_Save",data,function(idata){
		document.getElementById("current_qty").innerHTML = parseInt(idata);
	});
}

$(document).ready(function() { 
	var today = new Date();
	var year = today.getFullYear();
	var month = ('0' + (today.getMonth() + 1)).slice(-2);
	var day = ('0' + today.getDate()).slice(-2);
	document.getElementById("today").innerHTML = year + '-' + month  + '-' + day;
	
	document.getElementById("ko").style.height = window.innerHeight - document.getElementById("ko").offsetTop - 150 + "px";
			document.getElementById("WorkOrder_tbl").style.height = window.innerHeight - document.getElementById("ko").offsetTop - 150 + "px";
	
	MO_ListViewSearchBtn();
});

document.getElementById("d_len").onchange = function(){
	document.getElementById("d_len2").innerHTML = document.getElementById("d_len").value;
}

document.getElementById("d_len").onclick = function(){
	//CommandRun.run("/Users/Oliver/Desktop/keyboardViewer", []);
	//CommandRun.run("C:\\WINDOWS\\system32\\osk.exe", []);

	//alert("OK");
}

document.getElementById("okbtn").onclick = function(){
	data = {
		pdcode : document.getElementById("pname").getAttribute("code"),
		dtcode : document.getElementById("chr").getAttribute("code"),
		qty : document.getElementById("d_len").value
	};

	if(data.qty == 0)
	{
		alert("수량을 입력하시오.");
		return;
	}
	
	console.log("OK");
	
	if(data.pdcode == '' || data.dtcode == '')
	{
		alert("값이 제대로 입력 안되었습니다.");
		return;
	}
	
	if(data.pdcode == null || data.dtcode == null)
	{
		alert("값이 제대로 입력 안되었습니다.");
		return;
	}

	var cqty = parseInt(document.getElementById("current_qty").innerHTML);
	if(data.qty > cqty)
	{
		alert("재고가 부족합니다.");
		return;
	}

	$.get("/tablet/matOutputLXTabletRest/MOM_Save",data,function(){
		location.reload();
	});
}

document.getElementById("cancelbtn").onclick = function(){
	location.reload();
}

function init_list_click(){
	var list = document.getElementsByClassName("list-group-item");

	//document.getElementById("").style.borderColor = "red";

	for(i=0;i<list.length;i++)
	{
		if(list[i].id=="bun1" || list[i].id=="bun2")
			continue;
	
		list[i].style.borderColor = "black";
		list[i].style.color = "black";
		list[i].style.background = "white";
	}
	
	if(flag === "one"){
		document.getElementById("listone").style.borderColor = "red";
		document.getElementById("listone").style.borderColor = "red";
		document.getElementById("listone").style.color = "white";
		document.getElementById("listone").style.background = "#4472C4";
	} 
	else if(flag === "bu")
	{
		document.getElementById("listtwo").style.borderColor = "red";
		document.getElementById("listtwo").style.borderColor = "red";
		document.getElementById("listtwo").style.color = "white";
		document.getElementById("listtwo").style.background = "#4472C4";
	}
	
	list_select();
}

function list_click(o){
	var list = document.getElementsByClassName("list-group-item");

	//document.getElementById("").style.borderColor = "red";

	for(i=1;i<list.length;i++)
	{
		if(list[i].id=="bun1" || list[i].id=="bun2")
			continue;
	
		list[i].style.borderColor = "black";
		list[i].style.color = "black";
		list[i].style.background = "white";
	}

	o.style.borderColor = "red";
	o.style.color = "white";
	o.style.background = "#4472C4";
	
	list_select();
}

function list_select(){
	
	var list = document.getElementsByClassName("list-group-item");

	var item = null;
	
	for(i=0;i<list.length;i++)
	{
		if(list[i].style.borderColor == "red")
		{
			item = list[i];
		}
	}
	
	console.log(item.innerHTML);
	
	if(flag === "one"){
		data = {
			CLSFC : 'PRODUCT_ITEM_CLSFC_1',
			value : item.innerHTML
		};
	
		$.get("/tablet/matOutputLXTabletRest/Current_List",data,function(data){
			console.log(data);
			itemManageTable.setData(data);
		});
	}
	else if(flag === "bu")
	{
		data = {
			CLSFC : 'PRODUCT_ITEM_CLSFC_2',
			value : item.innerHTML
		};
	
		$.get("/tablet/matOutputLXTabletRest/Current_List",data,function(data){
			console.log(data);
			itemManageTable2.setData(data);
		});
	}
}

var itemManageTable = new Tabulator("#itemManageTable",	{
		layout:"fitDataStretch",
		//페이징
		pagination:"local",
		paginationSize:7,
		layoutColumnsOnNewData : true,
		headerFilterPlaceholder: null,
		height: "100%",
		rowClick: function(e, row) {
			row.getTable().deselectRow();
			row.select();
		},
		rowTap: function(e, row) {
			row.getTable().deselectRow();
			row.select();
		},
		rowDblClick: function(e, row) {
			console.log(row.getData());
			document.getElementById("pname").value = row.getData().product_ITEM_NAME;
			document.getElementById("pname").setAttribute("code",row.getData().product_ITEM_CODE);
			
			document.getElementById("pdselect2").value = row.getData().product_ITEM_NAME;
			document.getElementById("pdselect2").setAttribute("code",row.getData().product_ITEM_CODE);

			document.getElementById("gu").value = row.getData().product_INFO_STND_1;
			document.getElementById("gu2").innerHTML = row.getData().product_INFO_STND_1;
			$('#myFullsizeModal').modal("hide");
			

			$.get("/tablet/matOutputLXTabletRest/Current_Save?code="+row.getData().product_ITEM_CODE,function(data){
				document.getElementById("current_qty").innerHTML = parseInt(data);
			});
		},
		rowDblClick: function(e, row) {
			console.log(row.getData());
			document.getElementById("pname").value = row.getData().product_ITEM_NAME;
			document.getElementById("pname").setAttribute("code",row.getData().product_ITEM_CODE);
			
			document.getElementById("pdselect2").innerHTML = row.getData().product_ITEM_NAME;
			document.getElementById("pdselect2").setAttribute("code",row.getData().product_ITEM_CODE);

			document.getElementById("gu").value = row.getData().product_INFO_STND_1;
			document.getElementById("gu2").innerHTML = row.getData().product_INFO_STND_1;
			$('#myFullsizeModal').modal("hide");
			

			$.get("/tablet/matOutputLXTabletRest/Current_Save?code="+row.getData().product_ITEM_CODE,function(data){
				document.getElementById("current_qty").innerHTML = parseInt(data);
			});
		},
		columns: [
			{ title: "규격", field: "product_INFO_STND_1", headerSort:false, headerHozAlign:"center",width:350},
			{ title: "제품이름", field: "product_ITEM_NAME", headerSort:false, headerHozAlign:"center"}
		]
});

var itemManageTable2 = new Tabulator("#itemManageTable2",	{
		layout:"fitDataStretch",
		//페이징
		pagination:"local",
		paginationSize:7,
		layoutColumnsOnNewData : true,
		headerFilterPlaceholder: null,
		height: "100%",
		rowClick: function(e, row) {
			row.getTable().deselectRow();
			row.select();
		},
		rowTap: function(e, row) {
			row.getTable().deselectRow();
			row.select();
		},
		rowDblClick: function(e, row) {
			console.log(row.getData());
			document.getElementById("pname").value = row.getData().product_ITEM_NAME;
			document.getElementById("pname").setAttribute("code",row.getData().product_ITEM_CODE);

			document.getElementById("gu").value = row.getData().product_INFO_STND_1;
			document.getElementById("gu2").value = row.getData().product_INFO_STND_1;
			$('#myFullsizeModal2').modal("hide");
			

			$.get("/tablet/matOutputLXTabletRest/Current_Save?code="+row.getData().product_ITEM_CODE,function(data){
				document.getElementById("current_qty").innerHTML = parseInt(data);
			});
		},
		rowDblClick: function(e, row) {
			console.log(row.getData());
			document.getElementById("pname").value = row.getData().product_ITEM_NAME;
			document.getElementById("pname").setAttribute("code",row.getData().product_ITEM_CODE);

			document.getElementById("gu").value = row.getData().product_INFO_STND_1;
			document.getElementById("gu2").value = row.getData().product_INFO_STND_1;
			$('#myFullsizeModal2').modal("hide");
			

			$.get("/tablet/matOutputLXTabletRest/Current_Save?code="+row.getData().product_ITEM_CODE,function(data){
				document.getElementById("current_qty").innerHTML = parseInt(data);
			});
		},
		columns: [
			{ title: "규격", field: "product_INFO_STND_1", headerSort:false, headerHozAlign:"center",width:350},
			{ title: "제품이름", field: "product_ITEM_NAME", headerSort:false, headerHozAlign:"center"}
		]
});

var itemManageTable3 = new Tabulator("#itemManageTable3",	{
		ajaxURL:"/tablet/matOutputLXTabletRest/Out_List",
		layout:"fitDataStretch",
		//페이징
		layoutColumnsOnNewData : true,
		headerFilterPlaceholder: null,
		height: "100%",
		rowClick: function(e, row) {
			row.getTable().deselectRow();
			row.select();
		},
		rowTap: function(e, row) {
			row.getTable().deselectRow();
			row.select();
		},
		rowDblClick: function(e, row) {
			document.getElementById("chr").value = row.getData().child_TBL_TYPE;
			document.getElementById("chr").innerHTML = row.getData().child_TBL_TYPE;
			document.getElementById("chr").setAttribute("code",row.getData().child_TBL_NO);
			
			document.getElementById("dtselect2").innerHTML = row.getData().child_TBL_TYPE;
			$('#chModal').modal("hide");
		},
		rowDblClick: function(e, row) {
			document.getElementById("chr").value = row.getData().child_TBL_TYPE;
			document.getElementById("chr").innerHTML = row.getData().child_TBL_TYPE;
			document.getElementById("chr").setAttribute("code",row.getData().child_TBL_NO);
			
			document.getElementById("dtselect2").innerHTML = row.getData().child_TBL_TYPE;
			$('#chModal').modal("hide");
		},
		columns: [
			{ title: "출고처", field: "child_TBL_TYPE", headerSort:false, headerHozAlign:"center"}
		]
});

document.getElementById("pname").onclick = function(){
	/*
			if(flag === "one")
			{
				init_list_click();
				$('#myFullsizeModal').modal("show");
			}
			else if(flag === "bu")
			{
				init_list_click();
				$('#myFullsizeModal2').modal("show");
			}
			
			
			
			var array = document.getElementsByClassName("modal-dialog modal-fullsize");

			for(i=0;i<array.length;i++)
			{
				array[i].scrollTop = 0;
			}
	*/
	if(flag === "one")
	{
		data = {
			PRODUCT_MTRL_CLSFC : 'A'
		};
	
		$.get("/tablet/matOutputLXTabletRest/Current_List",data,function(data){
			itemPopupTableModal.setData(data);
		});
	}
	else
	{
		data = {
			PRODUCT_MTRL_CLSFC : 'B'
		};
	
		$.get("/tablet/matOutputLXTabletRest/Current_List",data,function(data){
			itemPopupTableModal.setData(data);
		});
	}
	
	$('#testModal').modal("show");
}

document.getElementById("gu").onclick = function(){
			/*
			if(flag === "one")
			{
				init_list_click();
				$('#myFullsizeModal').modal("show");
			}
			else if(flag === "bu")
			{
				init_list_click();
				$('#myFullsizeModal2').modal("show");
			}
			
			var array = document.getElementsByClassName("modal-dialog modal-fullsize");

			for(i=0;i<array.length;i++)
			{
				array[i].scrollTop = 0;
			}
			*/
	
	if(flag === "one")
	{
		data = {
			PRODUCT_MTRL_CLSFC : 'A'
		};
	
		$.get("/tablet/matOutputLXTabletRest/Current_List",data,function(data){
			itemPopupTableModal.setData(data);
		});
	}
	else
	{
		data = {
			PRODUCT_MTRL_CLSFC : 'B'
		};
	
		$.get("/tablet/matOutputLXTabletRest/Current_List",data,function(data){
			itemPopupTableModal.setData(data);
		});
	}
			
	$('#testModal').modal("show");
}


function search(){
	data = null;

	if(flag === "one")
	{
		data = {
			PRODUCT_MTRL_CLSFC : 'A'
		};
	}
	else
	{
		data = {
			PRODUCT_MTRL_CLSFC : 'B'
		};
	}
	
	data.value = document.getElementById("Item_Word").value;
	
	$.get("/tablet/matOutputLXTabletRest/Current_List",data,function(data){
			itemPopupTableModal.setData(data);
	});
}

var itemPopupTableModal = new Tabulator("#itemPopupTableModal", {
	layout:"fitDataStretch",
	height:"100%",
	resizableColumns: false,
	rowDblClick:function(e, row){
				
	},
	columns:[ 
	{title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center", visible:true},
	{title:"규격", field:"product_INFO_STND_1", headerHozAlign:"center", visible:true,width:150},
	{title:"품목코드", field:"product_ITEM_CODE", headerHozAlign:"center", visible:true},
	{title:"품목이름", field:"product_ITEM_NAME", headerHozAlign:"center", visible:true}		
	]
});

function chrclick(){
	$('#chModal').modal("show");
}

function MO_ListViewSearchBtn(){
	data = {
		startDate : $("#matOutputList_startDate").val(),
		endDate : $("#matOutputList_endDate").val(),
		outMat_Code : null,
		outMat_Send_Clsfc_Name : 'all',
		outMat_Dept_Name : 'all'
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "../matOutputReportLXRest/tablet/MO_ListView?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			WorkOrder_tbl.setData(data);
		}
	});
}