		var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
			height:"51%",
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
		
		document.getElementById("input").focus();

		oldVal = '';

		$("#input").on("propertychange change keydown paste input", function() {

			var currentVal = $(this).val();

			if(currentVal == oldVal) {
				return;
			}
			oldVal = currentVal;

			if(oldVal.substring(0, 1)=="M" || oldVal.substring(0, 1)=="m")
			{
				var arrays = document.getElementsByClassName("form-control form-control-lg");

				for(i=0;i<arrays.length;i++)
				{
					if(arrays[i].getAttribute("code")=="one" && arrays[i].value=="")
					{
						arrays[i].value = oldVal;
						document.getElementById("input").value = "";
						return;
					}
				}
				
				alert("원자재가 이미 다 입력되었습니다.");
				document.getElementById("input").value = "";
				return;
			}
			else if(oldVal.substring(0, 1)=="K" || oldVal.substring(0, 1)=="k")
			{
				console.log(oldVal.substring(0, 1));
				
				k = 4;

				var arrays = document.getElementsByClassName("form-control form-control-lg");

				for(i=0;i<arrays.length;i++)
				{
					if(arrays[i].getAttribute("code")=="one" && arrays[i].value!="")
					{
						--k;
					}
				}

				if(k==4)
				{
					alert("원자재가 입력되지 않았습니다.");
					document.getElementById("input").value = "";
					document.getElementById("input").focus();
					return;
				}
				
				if(document.getElementById("n_len").getAttribute("pcode") == "" || document.getElementById("n_len").getAttribute("pcode") == null)
				{
					alert("품목코드가 선택되지 않았습니다.");
					document.getElementById("input").value = "";
					document.getElementById("input").focus();
					return;
				}

				if(document.getElementById("eqcode").value == "" || document.getElementById("eqcode").value == "null")
				{
					alert("잘못된 URL에 접속하셨습니다.");
					document.getElementById("input").value = "";
					document.getElementById("input").focus();
					return;
				}
				
				// -------------------------
				//if(confirm("작업지시를 추가하시겠습니까?"))
				//{
					if(workOrder_ONo==="")
					{
						$.get("../workOrderTABRestXO/MI_Searchi?WorkOrder_EquipCode=" + document.getElementById("eqcode").value + "&PRODUCTION_PRODUCT_CODE=" + document.getElementById("n_len").getAttribute("pcode"), function(data) {                 							
							
							
							
							document.getElementById("workOrder_ONo").value = data[0].workOrder_ONo;
							//select_program();
							
							var arrays = document.getElementsByClassName("form-control form-control-lg");

							for(i=0;i<arrays.length;i++)
							{
								if(arrays[i].getAttribute("code")=="one" && arrays[i].value!="")
								{
									$.get("../MaterialInputRest/MaterialInput_Insert?Product_LotNo=" + document.getElementById("workOrder_ONo").value + "&Material_LotNo=" + arrays[i].value, function(data) {                 
									
									});
								}
							}
							
							select_program();
						});
					}
					
					else{
						if(workOrder_Remark != "AUTO")
						{
							$.get("../workOrderTABRestXO/MI_Searche?WorkOrder_WorkStatus=243&WorkOrder_EquipCode=" + document.getElementById("eqcode").value+"&PRODUCTION_SERIAL_NUM="+workOrder_ONo, function(data) {            
								$.get("../workOrderTABRestXO/MI_Searchi?WorkOrder_EquipCode=" + document.getElementById("eqcode").value + "&PRODUCTION_PRODUCT_CODE=" + document.getElementById("n_len").getAttribute("pcode"), function(data) {                 
								
									document.getElementById("workOrder_ONo").value = data[0].workOrder_ONo;
									//select_program();
									var arrays = document.getElementsByClassName("form-control form-control-lg");

									for(i=0;i<arrays.length;i++)
									{
										if(arrays[i].getAttribute("code")=="one" && arrays[i].value!="")
										{
											$.get("../MaterialInputRest/MaterialInput_Insert?Product_LotNo=" + document.getElementById("workOrder_ONo").value + "&Material_LotNo=" + arrays[i].value, function(data) {                 
											
											});
										}
									}
								});
							});
						}
						else
						{
							$.get("../workOrderTABRestXO/MI_Searche?WorkOrder_WorkStatus=245&WorkOrder_EquipCode=" + document.getElementById("eqcode").value+"&PRODUCTION_SERIAL_NUM="+workOrder_ONo, function(data) {            
								$.get("../workOrderTABRestXO/MI_Searchi?WorkOrder_EquipCode=" + document.getElementById("eqcode").value + "&PRODUCTION_PRODUCT_CODE=" + document.getElementById("n_len").getAttribute("pcode"), function(data) {                 
								
									document.getElementById("workOrder_ONo").value = data[0].workOrder_ONo;
									//select_program();
									var arrays = document.getElementsByClassName("form-control form-control-lg");

									for(i=0;i<arrays.length;i++)
									{
										if(arrays[i].getAttribute("code")=="one" && arrays[i].value!="")
										{
											$.get("../MaterialInputRest/MaterialInput_Insert?Product_LotNo=" + document.getElementById("workOrder_ONo").value + "&Material_LotNo=" + arrays[i].value, function(data) {                 
											
											});
										}
									}
								});
							});
						}
					}
					
					
					setTimeout(function(){
						location.reload();
					},500);
					
					
					var arrays = document.getElementsByClassName("form-control form-control-lg");
				//}
				// -------------------------
			}
			
		});
		
		document.getElementById("n_len").onclick = function(){
			$('#testModal').modal("show");
			document.getElementById("Item_Word").value = "";
			document.getElementById("n_len").value = "";
			document.getElementById("o_len").value = "";
			document.getElementById("n_len").setAttribute("pcode","");
			document.getElementById("Item_Word").value = ' ';
			flag = "v";
				
			search();
			
			//$("#itemPopupTableModal").tabulator("redraw");
			document.getElementById("Item_Word").focus();
		}
		
		document.getElementById("o_len").onclick = function(){
			$('#testModal').modal("show");
			document.getElementById("Item_Word").value = "";
			document.getElementById("n_len").value = "";
			document.getElementById("o_len").value = "";
			document.getElementById("n_len").setAttribute("pcode","");
			document.getElementById("Item_Word").value = ' ';
			flag = "s";
				
			search2();
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

		document.getElementById("popbtn").onclick = function(){
			if(flag=="v")
					search();
				else
					search2();
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
			console.log($('#Item_Word').val());
			console.log(document.getElementById("Item_Word").value);

			itemPopupTableModal.setData("itemPopupSelect2",
				{item_Word:$('#Item_Word').val(),search_value:"sales"})
			.then(function(){
			});

			itemPopupTableModal.redraw(true);
		}
		
		var itemPopupTableModal = new Tabulator("#itemPopupTableModal", {
			layout:"fitDataStretch",
			height:"100%",
			rowTap:function(e, row){
				itemPopupTableModal.deselectRow();

				document.getElementById("n_len").value = row.getData().product_ITEM_NAME;
				document.getElementById("o_len").value = row.getData().product_INFO_STND_1;
				document.getElementById("n_len").setAttribute("pcode",row.getData().product_ITEM_CODE);
				$('#testModal').modal("hide");
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
		
		function select_program()
		{
			$.get("../workOrderStartBRest/workOrderStartInit?eqselect="+document.getElementById("eqcode").value,function(data){
				
				console.log(data);

				if(data.length > 0)
				{
					workOrder_ONo = data[0].workOrder_ONo;
					workOrder_Remark = data[0].workOrder_Remark;
					
					document.getElementById("workOrder_ONo").value = workOrder_ONo;
					document.getElementById("n_len").value = data[0].workOrder_ItemName;
					document.getElementById("n_len").setAttribute("pcode",data[0].workOrder_ItemCode);
					document.getElementById("o_len").value = data[0].product_INFO_STND_1;
				}
				else
				{
					workOrder_ONo = "";
					workOrder_Remark = "";

					document.getElementById("workOrder_ONo").value = workOrder_ONo;
					document.getElementById("n_len").value = "";
					document.getElementById("n_len").setAttribute("pcode","");
					document.getElementById("o_len").value = "";
				}
				
				document.getElementById("input").value = "";
				
				$.get("../workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=" + document.getElementById("eqcode").value + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
					WorkOrder_tbl.setData(data);
				});
				
			});
		}
		
		var workOrder_ONo = "";

		$(document).ready(function() {
			select_program();
		});

		setInterval(function(){
			var id = $(':focus').attr('id');

			if(id !== "Item_Word")
				document.getElementById("input").focus();
		},1000);
		
		/* 
		setInterval(function(){
			$.get("../workOrderStartBRest/workOrderSumQty?eqselect=" + document.getElementById("eqcode").value, function(data){
				document.getElementById("sum_qty").value = data;
				
			});
		},10000);
		*/