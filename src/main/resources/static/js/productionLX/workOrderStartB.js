		var workOrder_ONo = "";
		
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

		function num_btn(e){
			switch(e.innerHTML.trim())
			{
				case "X":
					value = document.getElementById("n_len").value;
					document.getElementById("n_len").value = value.slice(0,-1);
					break;
				case "C":
					document.getElementById("n_len").value = "";
					break;
				default:
					document.getElementById("n_len").value += e.innerHTML.trim();
					break;
			}
		}

		document.getElementById("eqselect").onchange = function(){
			select_program();
		}

		function select_program()
		{
			$.get("workOrderStartBRest/workOrderStartInit?eqselect="+document.getElementById("eqselect").value,function(data){
				if(data.length > 0)
				{
					workOrder_ONo = data[0].workOrder_ONo;

					var obj = document.getElementById("pdselect");
					for(i=0;i<obj.length;i++)
					{
						console.log(obj[i].value);

						if(data[0].workOrder_ItemCode === obj[i].value)
						{
							obj[i].selected = true;
							return;
						}
					}
				}
				else
				{
					var obj = document.getElementById("pdselect");

					for(i=0;i<obj.length;i++)
					{
						obj[i].selected = false;
					}

					workOrder_ONo = "";
				}
			});

			$.get("workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
				WorkOrder_tbl.setData(data);
			});
		}

		document.getElementById("workOrderInsertBBtn").onclick = function(){
			location.href = "/workOrderInsertB";
		}

		document.getElementById("pdselect").onchange = function(){
			if(workOrder_ONo==="")
			{
				$.get("workOrderTABRestXO/MI_Searchi?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&PRODUCTION_PRODUCT_CODE=" + document.getElementById("pdselect").value, function(data) {                 
					
					workOrder_ONo = data[0].workOrder_ONo;

					$.get("workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
						WorkOrder_tbl.setData(data);
					});
				
				});
			}
			else{
				$.get("workOrderTABRestXO/MI_Searche?WorkOrder_EquipCode=" + document.getElementById("eqselect").value+"&PRODUCTION_SERIAL_NUM="+workOrder_ONo, function(data) {            
					$.get("workOrderTABRestXO/MI_Searchi?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&PRODUCTION_PRODUCT_CODE=" + document.getElementById("pdselect").value, function(data) {                 
					
						workOrder_ONo = data[0].workOrder_ONo;

						$.get("workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
							WorkOrder_tbl.setData(data);
						});
					
					});
				});
		}}

		window.onload = function(){
			select_program();
		}

		setInterval(function(){
			$.get("workOrderStartBRest/workOrderSumQty?eqselect=" + document.getElementById("eqselect").value, function(data){
				document.getElementById("sum_qty").innerHTML = data;
			});
		},5000);

		setInterval(function(){
			$.get("workOrderStartBRest/workOrderCurrentQty?eqselect=" + document.getElementById("eqselect").value, function(data){
				document.getElementById("current_qty").innerHTML = data;
			});
		},1000);