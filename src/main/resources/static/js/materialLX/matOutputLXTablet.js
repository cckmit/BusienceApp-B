		var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
			height:"85%",
			placeholder: "No Data Set",
			resizableColumns: false,
			rowClick: function(e, row) {
			},
			columns: [
				{title:"순번", field:"id", headerHozAlign: "center",  hozAlign: "center", width:70},
				{title:"출고일자", field:"outMat_Date", headerHozAlign:"center", hozAlign:"left", width:150, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
			 	{title:"출고구분", field:"outMat_Send_Clsfc_Name", headerHozAlign:"center", hozAlign:"left", width:100},
			 	{title:"부서명", field:"outMat_Dept_Name", headerHozAlign:"center", hozAlign:"left", width:100},
				{title:"수취인", field:"outMat_Consignee_Name", headerHozAlign:"center", hozAlign:"left", width:100},
			 	{title:"품목코드", field:"outMat_Code", headerHozAlign:"center", hozAlign:"left", width:100},
			 	{title:"품명", field:"outMat_Name", headerHozAlign:"center",hozAlign:"left", width:170},
			 	{title:"규격", field:"outMat_STND_1", headerHozAlign:"center",hozAlign:"left", width:120},
			 	{title:"단위", field:"outMat_UNIT", headerHozAlign:"center", hozAlign:"left", width:100},
			 	{title:"수량", field:"outMat_Qty", headerHozAlign:"center", hozAlign:"right", width:100},
			 	{title:"등록자", field:"outMat_Modifier", headerHozAlign:"center", hozAlign:"left", width:100},
			 	{title:"등록일자", field:"outMat_dInsert_Time", headerHozAlign:"center", hozAlign:"left", width:140, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}}
			],
		});

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
				url : "../matOutputReportLXRest/MO_ListView?data="+ encodeURI(JSON.stringify(data)),
				success : function(data) {
					console.log(data);
					WorkOrder_tbl.setData(data);
				}
			});
		}

		window.onload = function(){
			search();
		}

		function search2(){
			itemPopupTableModal.setData("itemPopupSelect2",
				{item_Word:$('#Item_Word').val(),search_value:"sales"})
			.then(function(){
			});

			itemPopupTableModal.redraw(true);
		}