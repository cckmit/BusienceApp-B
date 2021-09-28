			// 위에 그리드를 클릭하였는지 안하였는지 검사한다.
			var clickFlag = false;
			// InMat_tbl의 index를 저장한다.
			index = 0;
			// 인서트할지 업데이트를 할지 정해주는 조건값
			var inupflag = "";
			
			//var Sales_InMat_tbl = new Tabulator("#Sales_InMat_tbl", {
			var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
				height:"calc(60% - 175px)",
				//복사하여 엑셀 붙여넣기 가능
				clipboard: true,
				layout:"fitDataStretch",
				rowClick:function(e, row){
				},
				rowDblClick:function(e, row){
			    	//row.toggleSelect();
			    	
			    	clickFlag = true;
			    	
			    	// 인풋태그안에 있는 값들을 클리어함
			    	inputinit();
				
					// 더블클릭한 행을 선택한다.
					WorkOrder_tbl.deselectRow();
					row.select();
					
					// 아래 테이블은 셀렉트를 중지한다
					OQCInspect_tbl2.deselectRow();
					inupflag = "insert";
					
					console.log(row.getData());
					
					// LotNo
					// $("#OQCInspect_Lot_No").val(row.getData().inMat_Lot_No);
					
					var today = new Date();
					
					var montht = today.getMonth()+1;
					var month = "";
					if(montht <= 9)
						month = "0"+montht;
					else
						month = montht;
					
					var datet = today.getDate();
					var date = "";
					if(date <= 9)
						date = "0"+datet;
					else
						date = datet;
					
					// 출하검사번호
					// $("#OQCInspect_OqcNo").val(today.getFullYear() + "" + month + "" + date+"-"+row.getData().inMat_Code+"-");
					$("#OQCInspect_OqcNo").val(row.getData().workOrder_ONo);
					
					// 제품
					$("#inMat_Code").val(row.getData().workOrder_ItemCode);
					$("#inMat_Name").val(row.getData().workOrder_ItemName);
					
					// 설비
					$("#inEquip_Code").val(row.getData().workOrder_EquipCode);
					$("#inEquip_Name").val(row.getData().workOrder_EquipName);
					
					// 거래
					$("#inMat_Client_Code").val(row.getData().inMat_Client_Code);
					$("#inMat_Client_Name").val(row.getData().inMat_Client_Name);
					
					// 합격
					$("#OQCInspect_PQty").val(row.getData().workOrder_RQty);
					
					//id="IQCInspectType_IQty1"
					
					console.log(OQCInspect_tbl2.getDataCount("active"));
					
					// 오른쪽 그리드에서 입고수량과 불량수량과 Sample수량을 바꿔주는 로직
					for(i=0;i<OQCInspect_tbl2.getDataCount("active");i++)
						OQCInspect_tbl2.updateRow(i,{OQCInspectType_IQty:row.getData().workOrder_RQty,OQCInspectType_DQty:0,OQCInspectType_SaQty:0});
					
					index = row.getData().index1;
					
					OQCInspect_tbl.deselectRow();
			    },
			    index:"index1",
			    
				columns:[
					{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center", width: 160 },
					{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", width: 100 },
					{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width: 180},
					{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center", width: 100 },
					{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center", width: 180 },
					{ title: "규격", field: "product_INFO_STND_1", headerHozAlign: "center"},		
					{ title: "생산수량", field: "workOrder_RQty", headerHozAlign: "center", align:"right"},
					{ title: "작업지시일", field: "workOrder_OrderTime", align: "right", headerHozAlign: "center", width: 160},
					{ title: "작업지시종료일", field: "workOrder_CompleteOrderTime", align: "right", headerHozAlign: "center", width: 160},
					{ title: "접수일", field: "workOrder_ReceiptTime", align: "right", headerHozAlign: "center", width: 160},
					{ title: "접수여부", field: "workOrder_WorkStatusName", headerHozAlign: "center",align:"center"},
					{ title: "특이사항", field: "workOrder_Remark", headerHozAlign: "center" }
				]
			});
			
			var OQCInspect_tbl = new Tabulator("#OQCInspect_tbl", {
				height:"calc(39.5% - 175px)",
				//복사하여 엑셀 붙여넣기 가능
				clipboard: true,
				rowDblClick:function(e, row){
					console.log(row.getData());
					
					clickFlag = true;
				
					OQCInspect_tbl.deselectRow();
					row.select();
					
					// LotNo
					$("#OQCInspect_Lot_No").val(row.getData().oqcinspect_Lot_No);
					
					// 등록일자
					var today = new Date();
					document.getElementById('OQCInspect_Date').value = today.toISOString().substring(0, 10);
					
					//alert(row.getData().oqcinspect_OqcInNo);
					
					// 출고검사번호
					$("#OQCInspect_OqcNo").val(row.getData().oqcinspect_OqcInNo);
					
					// 품목
					$("#inMat_Code").val(row.getData().product_ITEM_CODE);
					$("#inMat_Name").val(row.getData().product_ITEM_NAME);
					
					// 거래
					$("#inMat_Client_Code").val(row.getData().cus_Code);
					$("#inMat_Client_Name").val(row.getData().cus_Name);
					
					// 문제점
					$("#OQCInspect_Problem").val(row.getData().oqcinspect_Problem);
					
					// 검사자
					var OQCInspect_Worker = document.getElementById("OQCInspect_Worker");
					for(i=0;i<OQCInspect_Worker.length;i++)
					{
						if(OQCInspect_Worker.options[i].value==row.getData().oqcinspect_Worker)
							OQCInspect_Worker.options[i].selected = true;
					}
					
					// 처리구분
					var OQCInspect_Prcsn_Clsfc = document.getElementById("OQCInspect_Prcsn_Clsfc");
					for(i=0;i<OQCInspect_Prcsn_Clsfc.length;i++)
					{
						if(OQCInspect_Prcsn_Clsfc.options[i].value==row.getData().oqcinspect_Prcsn_Clsfc)
							OQCInspect_Prcsn_Clsfc.options[i].selected = true;
					}
					
					// 불량
					$("#OQCInspect_DQty").val(row.getData().oqcinspect_DQty);
					// 합격
					$("#OQCInspect_PQty").val(row.getData().oqcinspect_PQty);
					// 특채
					$("#OQCInspect_SQty").val(row.getData().oqcinspect_SQty);
					// 샘플
					$("#OQCInspectType_SaQty").val(row.getData().oqcinspect_SaQty);
					
					OQCInspect_tbl2.clearData();
					
					console.log(row.getData().iqcinspect_Lot_No);
					
					$.ajax({
					method : "GET",
					dataType : "json",
					url : "oqcOutputInspectRest/Search3?OQCInspect_OqcNo="+ row.getData().oqcinspect_OqcInNo,
						success : function(data) {
							//console.log("MI");
							console.log(data);
							OQCInspect_tbl2.setData(data);
						}
					});
					
					inupflag = "update";
					clickFlag = true;
			    	// 아래 테이블은 셀렉트를 중지한다
					WorkOrder_tbl.deselectRow();
			    },
			    ajaxConfig : "get",
				ajaxContentType:"json",
				ajaxURL : "oqcOutputInspectRest/Search2",
			    
				columns:[
					{title:"LotNo", field:"oqcinspect_Lot_No",headerHozAlign:"center",align:"left",
						headerClick:function(e, column){
						    console.log(OQCInspect_tbl.getData());
						},visible:false
					},
					{title:"출고검사번호", field:"oqcinspect_OqcInNo",headerHozAlign:"center",align:"left",
					headerClick:function(e, column){
						    console.log(OQCInspect_tbl.getData());
						}
					},
					{title:"등록일자", field:"oqcinspect_Date",headerHozAlign:"center",align:"right"},
					{title:"거래처", field:"cus_Code",headerHozAlign:"center",align:"left",visible:false},
					{title:"거래처명", field:"cus_Name",headerHozAlign:"center",align:"left",visible:false},
					{title:"제품코드", field:"product_ITEM_CODE",headerHozAlign:"center",align:"left"},
					{title:"제품명", field:"product_ITEM_NAME",headerHozAlign:"center",align:"left"},
					{title:"규격", field:"product_INFO_STND_1",headerHozAlign:"center",align:"left"},
					{title:"검사자", field:"oqcinspect_Worker_Name",headerHozAlign:"center",align:"left"},
					{title:"처리구분", field:"oqcinspect_Prcsn_Clsfc_Name",headerHozAlign:"center",align:"left"},
					{title:"입고수량", field:"oqcinspectType_IQty",headerHozAlign:"center",align:"right", formatter:"money", formatterParams: {precision: false}},
					{title:"불량수량", field:"oqcinspect_DQty",headerHozAlign:"center",align:"right", formatter:"money", formatterParams: {precision: false}},
					{title:"합격수량", field:"oqcinspect_PQty",headerHozAlign:"center",align:"right", formatter:"money", formatterParams: {precision: false}},
					{title:"특채수량", field:"oqcinspect_SQty",headerHozAlign:"center",align:"right", formatter:"money", formatterParams: {precision: false}},
					{title:"Sample수량", field:"oqcinspect_SaQty",headerHozAlign:"center",align:"right", formatter:"money", formatterParams: {precision: false}},
					{title:"문제점", field:"oqcinspect_Problem",headerHozAlign:"center",align:"right"},
					{title:"비고", field:"remark",headerHozAlign:"center",align:"right"}
				]
			});
			
			function MI_searchBtn1(){
				data = {
					startDate : $("#startDate").val(),
					endDate : $("#endDate").val(),
					PRODUCT_ITEM_CODE: $("#PRODUCT_ITEM_CODE1").val(),
					WorkOrder_Check: "E"
				}
				
				$.ajax({
					method : "GET",
					dataType : "json",
					url : "oqcOutputInspectRest/Search?data="+ encodeURI(JSON.stringify(data)),
					success : function(data) {
						//console.log("MI");
						console.log(data);
						WorkOrder_tbl.setData(data);
					}
				});
			}
			
			function MI_searchBtn2(){
				$.ajax({
					method : "GET",
					dataType : "json",
					url : "oqcOutputInspectRest/Search2",
					success : function(data) {
						console.log(data);
						OQCInspect_tbl.setData(data);
					}
				});
			}
			
			function inputinit()
			{
				$("#OQCInspect_Lot_No").val("");
				$("#OQCInspect_OqcNo").val("");
				$("#oQCInspect_INo").val("");
				$("#inMat_Code").val("");
				$("#inMat_Name").val("");
				$("#inEquip_Code").val("");
				$("#inEquip_Name").val("");
				$("#inMat_Client_Code").val("");
				$("#inMat_Client_Name").val("");
				var today = new Date();
				document.getElementById('OQCInspect_Date').value = today.toISOString().substring(0, 10);
				$("#OQCInspect_DQty").val(0);
				$("#OQCInspect_PQty").val(0);
				$("#OQCInspect_SQty").val(0);
				$("#OQCInspectType_SaQty").val(0);
				$("#OQCInspect_Problem").val("");
				
				// 처리구분
				var OQCInspect_Prcsn_Clsfc = document.getElementById("OQCInspect_Prcsn_Clsfc");
				OQCInspect_Prcsn_Clsfc[0].selected = true;
				
				// 검사자
				var OQCInspect_Worker = document.getElementById("OQCInspect_Worker");
				OQCInspect_Worker[0].selected = true;
			}
			
			inputinit();

			function IQCInspectType_SaQty_Check(cell) {
				var cellValue = cell.getValue();
				
				if(cellValue=="")
				{
					cell.restoreOldValue();
					return;
				}
				
				if(isNaN(cellValue))
				{
					alert("Sample수량은 숫자로 입력하여 주십시오.");
					cell.restoreOldValue();
					return;
				}
				
				var cellValue2 =  parseInt(cellValue);
				
				if(cellValue2 < 0)
				{
					alert("Sample수량은 양수로 입력하여 주십시오.");
					cell.restoreOldValue();
					return;
				}
				
				// 입고수량보다 Sample수량이 많을 경우
				OQCInspectType_SaQty_Sum = OQCInspectType_SaQty_Sum_Fun();
				if(OQCInspect_tbl2.getRows()[0].getData().OQCInspectType_IQty < OQCInspectType_SaQty_Sum)
				{
					alert("Sample수량이 입고수량보다 많습니다.");
					cell.restoreOldValue();
					return;
				}
				
				$("#OQCInspectType_SaQty").val(OQCInspectType_SaQty_Sum);
			}
			
			function OQCInspectType_DQty_Check(cell)
			{
				var cellValue = cell.getValue();
				
				if(cellValue=="")
				{
					cell.restoreOldValue();
					return;
				}
				
				if(isNaN(cellValue))
				{
					alert("불량수량은 숫자로 입력하여 주십시오.");
					cell.restoreOldValue();
					return;
				}
				
				var cellValue2 =  parseInt(cellValue);
				
				if(cellValue2 < 0)
				{
					alert("불량수량은 양수로 입력하여 주십시오.");
					cell.restoreOldValue();
					return;
				}
				
				// 입고수량보다 불량수량이 많을 경우
				OQCInspectType_DQty_Sum = OQCInspectType_DQty_Sum_Fun();
				if(OQCInspect_tbl2.getRows()[0].getData().OQCInspectType_IQty < OQCInspectType_DQty_Sum)
				{
					alert("불량수량이 입고수량보다 많습니다.");
					cell.restoreOldValue();
					return;
				}
				
				OQCInspect_Prcsn_Clsfc_Change("DQty",1);
				DataCheck();
			}
			
			// 처리구분을 바꿔주는 로직
			function OQCInspect_Prcsn_Clsfc_Change(flag,max)
			{
				// 처리구분
				var OQCInspect_Prcsn_Clsfc = document.getElementById("OQCInspect_Prcsn_Clsfc");
				
				if(flag=="DQty")
				{
					OQCInspectType_DQty_Sum = OQCInspectType_DQty_Sum_Fun();
					
					// 불량수량이 0이상이면 처리구분은 Fail로
					if(OQCInspectType_DQty_Sum > 0)
						OQCInspect_Prcsn_Clsfc[max].selected = true;
					else
					{
						var OQCInspect_SQty = parseInt($("#OQCInspect_SQty").val());
						
						if(OQCInspect_SQty > 0)
							OQCInspect_Prcsn_Clsfc[2].selected = true;
						else
							OQCInspect_Prcsn_Clsfc[0].selected = true;
					}
				}
				else if(flag=="SQty")
				{
					var OQCInspect_SQty = parseInt($("#OQCInspect_SQty").val());
					
					if(OQCInspect_SQty > 0)
						OQCInspect_Prcsn_Clsfc[max].selected = true;
					else
					{
						OQCInspectType_DQty_Sum = OQCInspectType_DQty_Sum_Fun();
						
						if(OQCInspectType_DQty_Sum > 0)
							OQCInspect_Prcsn_Clsfc[1].selected = true;
						else
							OQCInspect_Prcsn_Clsfc[0].selected = true;
					}
				}
			}
			
			// 사용자가 입력한 불량수량 합계를 구하는 펑션
			function OQCInspectType_DQty_Sum_Fun(){
				OQCInspectType_DQty_Sum = 0;
				for(i=0;i<OQCInspect_tbl2.getRows().length;i++)
				{
					data = OQCInspect_tbl2.getRows()[i].getData();
					
					OQCInspectType_DQty = parseInt(data.OQCInspectType_DQty);
					OQCInspectType_DQty_Sum += OQCInspectType_DQty;
				}
				
				return OQCInspectType_DQty_Sum;
			}
			
			// 사용자가 입력한 Sample수량을 구하는 펑션
			function OQCInspectType_SaQty_Sum_Fun(){
				OQCInspectType_SaQty_Sum = 0;
				for(i=0;i<OQCInspect_tbl2.getRows().length;i++)
				{
					data = OQCInspect_tbl2.getRows()[i].getData();
					
					OQCInspectType_SaQty = parseInt(data.OQCInspectType_SaQty);
					OQCInspectType_SaQty_Sum += OQCInspectType_SaQty;
				}
				
				return OQCInspectType_SaQty_Sum;
			}
			
			function DataCheck()
			{
				// 사용자가 입력한 불량수량합
				OQCInspectType_DQty_Sum = OQCInspectType_DQty_Sum_Fun();
				// 사용자가 입력한 특채수량
				var OQCInspect_SQty = parseInt($("#OQCInspect_SQty").val());
				// 입고수량
				OQCInspectType_IQty = OQCInspect_tbl2.getRows()[0].getData().OQCInspectType_IQty;
				
				$.ajax({
					method : "GET",
					dataType : "json",
					url : "oqcOutputInspectRest/check_defect_num",
					success : function(data) {
						console.log(data);
						
						if(data==1)
						{
							if(OQCInspect_SQty == 0)
							{
								// 불량수량에 입고수량을 그대로 넣어준다.
								$("#OQCInspect_DQty").val(OQCInspectType_IQty);
								// 합격수량은 0이다.
								$("#OQCInspect_PQty").val(0);
							}
							else
							{
								// 입고수량에서 사용자가 입력한 특채수량을 뺀값을 오른쪽 불량수량에 입력해준다.
								$("#OQCInspect_DQty").val(OQCInspectType_IQty-OQCInspect_SQty);
								// 합격수량에 특채수량을 넣어준다.
								$("#OQCInspect_PQty").val(OQCInspect_SQty);
							}
						}
						else
						{
							if(OQCInspect_SQty == 0)
							{
								// 불량수량에 사용자가 입력한 모든 불량수량 합을 넣어준다.
								$("#OQCInspect_DQty").val(OQCInspectType_DQty_Sum);
								// 입고수량에서 불량수량을 빼고 넣는다.
								$("#OQCInspect_PQty").val(OQCInspectType_IQty-OQCInspectType_DQty_Sum);
							}
							else
							{
								// 특채수량보다 불량수량이 크거나 같을때
								if(OQCInspectType_DQty_Sum>=OQCInspect_SQty)
								{
									// 불량수량에 사용자가 입력한 모든 불량수량 합을 넣어준다.
									$("#OQCInspect_DQty").val(OQCInspectType_DQty_Sum);
									// 불량수량에서 특채수량을 뺀다.
									DQty = OQCInspectType_DQty_Sum - OQCInspect_SQty;
									// 입고수량에서 불량수량에서 특채수량을 뺀 값을 다시 뺀다.
									TQty = OQCInspectType_IQty - DQty;
									$("#OQCInspect_PQty").val(TQty);
								}
								// 불량수량보다 특채수량이 작을때
								else if(OQCInspectType_DQty_Sum<OQCInspect_SQty)
								{
									
								}
							}
						}
					}
				});
			}
			
			OQCInspect_SQty_Before = 0;
			function spec_check(){
				// 특채수량은 불량수량보다 클수 없다.
				// 불량수량
				OQCInspect_DQty = parseInt($("#OQCInspect_DQty").val());
				// 특채수량
				OQCInspect_SQty = parseInt($("#OQCInspect_SQty").val());
				
				// 특채수량은 불량수량보다 클수 없다.
				if(OQCInspect_SQty>=OQCInspect_DQty)
				{
					alert("특채수량이 불량수량보다 클 수 없습니다.");
					$("#OQCInspect_SQty").val(OQCInspect_SQty_Before);
					return;
				}
			
				OQCInspect_Prcsn_Clsfc_Change("SQty",2);
				DataCheck();
			}
			
			function SQty_Save(){
				OQCInspect_SQty_Before = parseInt($("#OQCInspect_SQty").val());
			}