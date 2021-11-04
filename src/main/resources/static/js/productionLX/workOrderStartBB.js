		var workOrder_ONo = "";
		
		var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
			//페이징
			pagination: "local",
			paginationSize: 5,
			paginationAddRow: "table",
			height:"100%",
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
				{ title: "작업시작일", field: "workOrder_StartTime2", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
				{ title: "작업완료일", field: "workOrder_CompleteTime2", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } }
			],
		});

		var itemPopupTableModal = new Tabulator("#itemPopupTableModal", {
			//페이징
			pagination: "local",
			paginationSize: 8,
			paginationAddRow: "table",
			height:"100%",
			rowTap:function(e, row){
				
				if(confirm("작업지시를 추가하시겠습니까?"))
				{
					// 품목코드
					document.getElementById("n_len_code").value = row.getData().product_ITEM_CODE;
					// 품목명
					document.getElementById("n_len").value = row.getData().product_ITEM_NAME;
					// 규격
					document.getElementById("o_len").value = row.getData().product_INFO_STND_1;

					
					if(workOrder_ONo==="")
					{
						$.get("workOrderTABRestXO/MI_Searchi?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&PRODUCTION_PRODUCT_CODE=" + document.getElementById("n_len_code").value, function(data) {                 
							
							workOrder_ONo = data[0].workOrder_ONo;

							$.get("workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
								WorkOrder_tbl.setData(data);
							});
						
						});
					}
					else{
						$.get("workOrderTABRestXO/MI_Searche?WorkOrder_EquipCode=" + document.getElementById("eqselect").value+"&PRODUCTION_SERIAL_NUM="+workOrder_ONo, function(data) {            
							$.get("workOrderTABRestXO/MI_Searchi?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&PRODUCTION_PRODUCT_CODE=" + document.getElementById("n_len_code").value, function(data) {                 
							
								workOrder_ONo = data[0].workOrder_ONo;
		
								$.get("workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
									WorkOrder_tbl.setData(data);
								});
							
							});
						});
					}
				}
				else
				{
					itemPopupTableModal.deselectRow();
				}
			},
			rowFormatter:function(row){
				var element = row.getElement(),
				data = row.getData(),
				width = element.offsetWidth,
				rowTable, cellContents;

				//clear current row data
				while(element.firstChild) element.removeChild(element.firstChild);

				//define a table layout structure and set width of row
				rowTable = document.createElement("table")
				rowTable.style.width = (width - 18) + "px";
				rowTable.style.border = "solid";
				rowTable.style.width = "100%";

				rowTabletr = document.createElement("tr");

				//add image on left of row
				cellContents = "<td></td>";

				console.log(data);
				//add row data on right hand side
				//cellContents += "<td><div><strong style='font-size: 200px;'>Type:</strong> " + data.type + "</div><div><strong>Age:</strong> " + data.age + "</div><div><strong>Rind:</strong> " + data.rind + "</div><div><strong>Colour:</strong> " + data.color + "</div></td>"
				cellContents += "<td>";

				cellContents += "<div><strong style='font-size: 25px;'>제품이름 : "+data.product_ITEM_NAME+" / ";
				cellContents += "규격 : "+data.product_INFO_STND_1+"</strong></div>";

				cellContents += "</td>";

				rowTabletr.innerHTML = cellContents;

				rowTable.appendChild(rowTabletr);

				//append newly formatted contents to the row
				element.append(rowTable);
			},
			columns:[ 
			{title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center", visible:false},
			{title:"품목코드", field:"product_ITEM_CODE", headerHozAlign:"center", visible:false},
			{title:"품목이름", field:"product_ITEM_NAME", headerHozAlign:"center", visible:false},
			{title:"규격", field:"product_INFO_STND_1", headerHozAlign:"center", visible:false}
			]
		});

		document.getElementById("eqselect").onchange = function(){
			select_program();
		}

		function select_program()
		{
			$.get("workOrderStartBRest/workOrderStartInit?eqselect="+document.getElementById("eqselect").value,function(data){
				
				if(data.length > 0)
				{
					document.getElementById("n_len_code").value = data[0].workOrder_ItemCode;
					document.getElementById("n_len").value = data[0].workOrder_ItemName;
					document.getElementById("o_len").value = data[0].product_INFO_STND_1;
				}

			});

			$.get("workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
				WorkOrder_tbl.setData(data);
			});

			$.get("workOrderStartBRest/workOrderStartInit?eqselect="+document.getElementById("eqselect").value,function(data){
				if(data.length > 0)
				{
					workOrder_ONo = data[0].workOrder_ONo;
				}
				else
				{
					workOrder_ONo = "";
				}
			});
		}

		document.getElementById("workOrderInsertBBtn").onclick = function(){
			move();
		}

		window.onload = function(){
			select_program();
		}

		setInterval(function(){
			$.get("workOrderStartBRest/workOrderSumQty?eqselect=" + document.getElementById("eqselect").value, function(data){
				document.getElementById("sum_qty").value = data;
			});
		},5000);

		setInterval(function(){
			$.get("workOrderStartBRest/workOrderCurrentQty?eqselect=" + document.getElementById("eqselect").value, function(data){
				document.getElementById("current_qty").value = data;
			});
		},1000);

		var flag = "";

		document.getElementById("n_len").onkeydown = function(){
			if(event.keyCode==13)
			{
				event.preventDefault();
				$('#testModal').modal("show");

				document.getElementById("Item_Word").value = document.getElementById("n_len").value;

				search();

				flag = "v";
			}
		}

		document.getElementById("o_len").onkeydown = function(){
			if(event.keyCode==13)
			{
				event.preventDefault();
				$('#testModal').modal("show");

				document.getElementById("Item_Word").value = document.getElementById("o_len").value;

				search2();

				flag = "n";
			}
		}

		document.getElementById("Item_Word").onkeydown = function(){
			if(event.keyCode==13)
			{
				event.preventDefault();
				$('#testModal').modal("show");
				search2();

				if(flag=="v")
					search();
				else
					search2();
			}
		}

		//검색
		function search(){
			itemPopupTableModal.setData("itemPopupSelect",
				{item_Word:$('#Item_Word').val(),search_value:"sales"})
			.then(function(){
			});

			itemPopupTableModal.redraw(true);
		}

		function search2(){
			itemPopupTableModal.setData("itemPopupSelect2",
				{item_Word:$('#Item_Word').val(),search_value:"sales"})
			.then(function(){
			});

			itemPopupTableModal.redraw(true);
		}