			var OQCInspect_tbl = new Tabulator("#OQCInspect_tbl", {
				height:"calc(100% - 175px)",
				//복사하여 엑셀 붙여넣기 가능
				clipboard: true,
				rowDblClick:function(e, row){
					OQCInspect_tbl.deselectRow();
					row.select();
			    },
			    
				columns:[
					{title:"순번", field:"num",headerHozAlign:"center",align:"center"},
					{title:"LotNo", field:"oqcinspect_Lot_No",headerHozAlign:"center",align:"left",headerFilter:true,visible:false},
					{title:"출하검사번호", field:"oqcinspect_OqcInNo",headerHozAlign:"center",align:"left",headerFilter:true},
					{title:"등록일자", field:"oqcinspect_Date",headerHozAlign:"center",align:"right",headerFilter:true},
					{title:"거래처", field:"cus_Code",headerHozAlign:"center",align:"left",visible:false,headerFilter:true},
					{title:"거래처명", field:"cus_Name",headerHozAlign:"center",align:"left",visible:false,headerFilter:true},
					{title:"제품코드", field:"product_ITEM_CODE",headerHozAlign:"center",align:"left",headerFilter:true},
					{title:"제품명", field:"product_ITEM_NAME",headerHozAlign:"center",align:"left",headerFilter:true},
					{title:"규격", field:"product_INFO_STND_1",headerHozAlign:"center",align:"left",headerFilter:true},
					{title:"검사자", field:"oqcinspect_Worker_Name",headerHozAlign:"center",align:"left",headerFilter:true},
					{title:"처리구분", field:"oqcinspect_Prcsn_Clsfc_Name",headerHozAlign:"center",align:"left",headerFilter:true},
					{title:"입고수량", field:"oqcinspectType_RQty",headerHozAlign:"center",align:"right",headerFilter:true},
					{title:"불량수량", field:"oqcinspect_DQty",headerHozAlign:"center",align:"right",headerFilter:true},
					{title:"합격수량", field:"oqcinspect_PQty",headerHozAlign:"center",align:"right",headerFilter:true},
					{title:"특채수량", field:"oqcinspect_SQty",headerHozAlign:"center",align:"right",headerFilter:true},
					{title:"Sample수량", field:"oqcinspect_SaQty",headerHozAlign:"center",align:"right",headerFilter:true},
					{title:"문제점", field:"oqcinspect_Problem",headerHozAlign:"center",align:"right",headerFilter:true},
					{title:"비고", field:"remark",headerHozAlign:"center",align:"right",headerFilter:true}
				]
			});
			
			function MI_searchBtn1(){
				data = {
					startDate : $("#startDate").val(),
					endDate : $("#endDate").val()
					,OQCInspect_Prcsn_Clsfc : $("#OQCInspect_Prcsn_Clsfc1").val()
				}
				
				$.ajax({
					method : "GET",
					dataType : "json",
					url : "oqcOutputListRest/Search?data="+ encodeURI(JSON.stringify(data)),
					success : function(data) {
						//console.log("MI");
						console.log(data);
						OQCInspect_tbl.setData(data);
					}
				});
			}
			