		var workOrder_ONo = "";
		var workOrder_Remark = "";
		
		var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
			height:"100%",
			placeholder: "No Data Set",
			resizableColumns: false,
			rowClick: function(e, row) {
			},
			columns: [
				{ title: "작업지시번호", field: "workOrder_ONo", headerHozAlign: "center"},
				{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center"},
				{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center"},
				{ title: "규격", field: "product_INFO_STND_1", headerHozAlign: "center"},
				{ title: "목표량", field: "workOrder_PQty", headerHozAlign: "center", width: 100, align: "right",
					formatter:function(cell, formatterParams, onRendered){
						return cell.getValue() == null ? cell.getValue() :  cell.getValue().slice(0,-2); //return the contents of the cell;
					},
					visible:false
				},
				{ title: "생산량", field: "workOrder_RQty", headerHozAlign: "center", align: "right",
					formatter:"money", formatterParams: {precision: false}
				},
				{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center"},
				{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center"},
				{ title: "특이사항", field: "workOrder_Remark", align: "right", headerHozAlign: "center",visible:false}
			],
		});
		
		function productCom(){
			if(workOrder_Remark != "AUTO")
			{
				$.get("../workOrderTABRestXO/MI_Searche?WorkOrder_WorkStatus=243&WorkOrder_EquipCode=" + document.getElementById("eqselect").value+"&PRODUCTION_SERIAL_NUM="+workOrder_ONo, function(data) {            
					select_program();
					workOrder_ONo = "";
				});
			}
			else
			{
				$.get("../workOrderTABRestXO/MI_Searche?WorkOrder_WorkStatus=245&WorkOrder_EquipCode=" + document.getElementById("eqselect").value+"&PRODUCTION_SERIAL_NUM="+workOrder_ONo, function(data) {            
					select_program();	
					workOrder_ONo = "";		
				});
			}
			
		}

		var itemPopupTableModal = new Tabulator("#itemPopupTableModal", {
			layout:"fitDataStretch",
			height:"100%",
			resizableColumns: false,
			rowDblClick:function(e, row){
				
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
						$.get("../workOrderTABRestXO/MI_Searchi?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&PRODUCTION_PRODUCT_CODE=" + document.getElementById("n_len_code").value, function(data) {                 
							
							workOrder_ONo = data[0].workOrder_ONo;

							$.get("../workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
								WorkOrder_tbl.setData(data);
								select_program();
								$('#testModal').modal("hide");
							});
						
						});
					}
					else{
						

						if(workOrder_Remark != "AUTO")
						{
							$.get("../workOrderTABRestXO/MI_Searche?WorkOrder_WorkStatus=243&WorkOrder_EquipCode=" + document.getElementById("eqselect").value+"&PRODUCTION_SERIAL_NUM="+workOrder_ONo, function(data) {            
								$.get("../workOrderTABRestXO/MI_Searchi?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&PRODUCTION_PRODUCT_CODE=" + document.getElementById("n_len_code").value, function(data) {                 
								
									workOrder_ONo = data[0].workOrder_ONo;
									workOrder_Remark = data[0].workOrder_Remark;
			
									$.get("../workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
										WorkOrder_tbl.setData(data);
										select_program();
										$('#testModal').modal("hide");
									});
								
								});
							});
						}
						else
						{
							$.get("../workOrderTABRestXO/MI_Searche?WorkOrder_WorkStatus=245&WorkOrder_EquipCode=" + document.getElementById("eqselect").value+"&PRODUCTION_SERIAL_NUM="+workOrder_ONo, function(data) {            
								$.get("../workOrderTABRestXO/MI_Searchi?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&PRODUCTION_PRODUCT_CODE=" + document.getElementById("n_len_code").value, function(data) {                 
								
									workOrder_ONo = data[0].workOrder_ONo;
									workOrder_Remark = data[0].workOrder_Remark;
			
									$.get("../workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
										WorkOrder_tbl.setData(data);
										select_program();
										$('#testModal').modal("hide");
									});
								
								});
							});
						}
					}
				}
				else
				{
					itemPopupTableModal.deselectRow();
				}
			},
			/* 
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
			*/
			columns:[ 
			{title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center", visible:true},
			{title:"규격", field:"product_INFO_STND_1", headerHozAlign:"center", visible:true},
			{title:"품목코드", field:"product_ITEM_CODE", headerHozAlign:"center", visible:true},
			{title:"품목이름", field:"product_ITEM_NAME", headerHozAlign:"center", visible:true}
			
			]
		});

		document.getElementById("eqselect").onchange = function(){
			select_program();
		}

		function select_program()
		{
			$.get("../workOrderStartBRest/workOrderStartInit?eqselect="+document.getElementById("eqselect").value,function(data){
				
				console.log(data);

				if(data.length > 0)
				{
					document.getElementById("n_len_code").value = data[0].workOrder_ItemCode;
					document.getElementById("n_len").value = data[0].workOrder_ItemName;
					document.getElementById("o_len").value = data[0].product_INFO_STND_1;
					document.getElementById("d_len").value = parseInt(data[0].workOrder_PQty);

					workOrder_ONo = data[0].workOrder_ONo;
					workOrder_Remark = data[0].workOrder_Remark;
					document.getElementById("t8").innerHTML = workOrder_ONo;
				}
				else
				{
					workOrder_ONo = "";
					workOrder_Remark = "";

					document.getElementById("n_len_code").value = "";
					document.getElementById("n_len").value = "";
					document.getElementById("o_len").value = "";
					document.getElementById("d_len").value = "0";
				}

			});

			$.get("../workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + document.getElementById("eqselect").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
				
				console.log(data);
				
				WorkOrder_tbl.setData(data);
			});

			/* 
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
			*/
		}

		window.onload = function(){
			workStatMoni();
			document.getElementById("ko").style.height = window.innerHeight - document.getElementById("ko").offsetTop + "px";
			document.getElementById("WorkOrder_tbl").style.height = window.innerHeight - document.getElementById("ko").offsetTop - 10 + "px";
			select_program();
		}

		setInterval(function(){
			$.get("../workOrderStartBRest/workOrderSumQty?eqselect=" + document.getElementById("eqselect").value, function(data){
				document.getElementById("sum_qty").value = data+document.getElementById("current_qty").value;
				
				select_program();
			});
		},10000);

		setInterval(function(){
			$.get("../workOrderStartBRest/workOrderCurrentQty?eqselect=" + document.getElementById("eqselect").value, function(data){
				document.getElementById("current_qty").value = data;
			});
		},1000);

		var flag = "";

		document.getElementById("n_len").onclick = function(){
			$('#testModal').modal("show");

			document.getElementById("n_len").value = "";
			document.getElementById("o_len").value = "";

			document.getElementById("Item_Word").value = "";

			search();

			flag = "v";
		}

		document.getElementById("o_len").onclick = function(){
			$('#testModal').modal("show");

			document.getElementById("n_len").value = "";
			document.getElementById("o_len").value = "";

			document.getElementById("Item_Word").value = "";

			search();

			flag = "n";
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
		
		function lang_convert(n){
			if(n.id==="kor")
			{
				document.getElementById("kor").style.background = "red";
				document.getElementById("eng").style.background = "white";

				document.getElementById("t1").innerHTML = "작업 관리";
				document.getElementById("t2").innerHTML = "설&nbsp;&nbsp;&nbsp;비";
				document.getElementById("t3").innerHTML = "누&nbsp;적&nbsp;수&nbsp;량";
				document.getElementById("t4").innerHTML = "생&nbsp;산&nbsp;수&nbsp;량";
				document.getElementById("t5").innerHTML = "목&nbsp;표&nbsp;수&nbsp;량";
				document.getElementById("t6").innerHTML = "품&nbsp;명";
				document.getElementById("t7").innerHTML = "규&nbsp;격";
				document.getElementById("t9").innerHTML = "작&nbsp;업&nbsp;완&nbsp;료";
				document.getElementById("t10").innerHTML = "작업 지시 선택";

				for(i=2;i<11;i++)
				{
					document.getElementById("t"+i).style.fontSize = "50px";

					if(i==6|| i==7)
					document.getElementById("t"+i).style.paddingTop = "0px";
				}
				
				document.getElementById("t8").style.fontSize = "40px";
			}
			else if(n.id==="eng")
			{
				document.getElementById("kor").style.background = "white";
				document.getElementById("eng").style.background = "red";

				document.getElementById("t1").innerHTML = "Work Management";
				document.getElementById("t2").innerHTML = "Machinery";
				document.getElementById("t3").innerHTML = "Cum Prd Qty";
				document.getElementById("t4").innerHTML = "Prd Qty";
				document.getElementById("t5").innerHTML = "Allotted Qty";
				document.getElementById("t6").innerHTML = "Prd Name";
				document.getElementById("t7").innerHTML = "Prd Spec.";
				document.getElementById("t9").innerHTML = "Work Complete";
				document.getElementById("t10").innerHTML = "Work Select";

				for(i=2;i<11;i++)
				{
					document.getElementById("t"+i).style.fontSize = "35px";

					if(i==6|| i==7)
					document.getElementById("t"+i).style.paddingTop = "5px";
				}
				
				document.getElementById("t8").style.fontSize = "40px";
			}
		}

		flash = false;
		setInterval(function(){
			workStatMoni();
		},1000);

		function workStatMoni(){
			if(document.getElementById("n_len").value === "")
			{
				document.getElementById("t8").innerHTML = "NONE";
				document.getElementById("tp").style.color = "rgb(112,173,70)";
				document.getElementById("tp").style.background = "rgb(112,173,70)";
			}
			else
			{
				flash = !flash;
				document.getElementById("tp").style.color = "black";
				if(flash)
					document.getElementById("tp").style.background = "red";
				else
					document.getElementById("tp").style.background = "white";
			}
		}