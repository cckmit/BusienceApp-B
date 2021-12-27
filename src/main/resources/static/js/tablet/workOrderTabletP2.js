		var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
			height:"40%",
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
	
		window.onload = function(){
			document.getElementById("ko").style.height = window.innerHeight - document.getElementById("ko").offsetTop + "px";
			document.getElementById("WorkOrder_tbl").style.height = window.innerHeight - document.getElementById("ko").offsetTop - 20 + "px";
		}
		
		oldVal = "";
		
		$("#input").on("propertychange change keydown paste input", function() {

			var currentVal = $(this).val();

			if(currentVal == oldVal) {
				return;
			}
			oldVal = currentVal;
			
			if(oldVal.substring(0, 1) === "K" || oldVal.substring(0, 1) === "k")
			{
				if(document.getElementById("eqcode").value == "" || document.getElementById("eqcode").value == "null")
				{
					alert("잘못된 URL에 접속하셨습니다.");
					document.getElementById("input").value = "";
					document.getElementById("input").focus();
					return;
				}
				
				data = {
					eqcode : document.getElementById("eqcode").value
				};
				
				$.get("/tablet/workOrderTabletPRest2/WorkOrder_ONo_export",data, function(data2) {                 
					console.log(data2);						
				});
			}
			else
			{
				document.getElementById("input").value = "";
				alert("박스 바코드를 입력하여 주십시오.");
			}

		});