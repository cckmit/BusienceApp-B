	var defectPerformance = new Tabulator("#defectPerformance", {
			height: "45%",
			//복사하여 엑셀 붙여넣기 가능
			clipboard: true,
			rowClick:function(e, row){
			},
			rowDblClick:function(e, row){
		    },
		    
			columns:[
				{ title: "작업지시 번호", field: "defect_USE_STATUS", headerHozAlign: "center",visible: false },
				{ title: "불량 코드", field: "defect_CODE", headerHozAlign: "center" },
				{ title: "불량 이름", field: "defect_NAME", headerHozAlign: "center" },
				{ title: "불량 수량", field: "defect_ABR", headerHozAlign: "center",editor:"input"
					, formatter:function(cell, formatterParams, onRendered){
				    //cell - the cell component
				    //formatterParams - parameters set for the column
				    //onRendered - function to call when the formatter has been rendered
				    
				    return isNaN(parseInt(cell.getValue())) ? 0 : parseInt(cell.getValue()); //return the contents of the cell;
					}
					,cellClick:function(e, cell){
						
						var value = String(cell.getValue());
						
						if(value != "null" && value.split('.').length > 0)
							cell.getRow().update({"defect_ABR":value.split('.')[0]});
						else
						{
							cell.getRow().update({"defect_ABR":value == "null"?0:value});
						}
						
						cell.getRow().getCell("defect_ABR").edit();
						
				    }
					,cellEditCancelled:function(cell){
				    }
					,cellEdited(cell){
				    }
				},
			]
		});
		
		var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
			height: "45%",
			//복사하여 엑셀 붙여넣기 가능
			clipboard: true,
			rowClick:function(e, row){
				if (e.detail == 1){
					$.get("../defectPerformanceMRest/DEFECT_INFO_TBL_Load?oqcinspect_OqcInNo=" + row.getData().oqcinspect_OqcInNo, function(data) {
						WorkOrder_tbl.deselectRow();
						row.select();
						
						defectPerformance.setData(data);
						
						drawArray = [];
					  	drawArray.push([ '불량 개수', '불량 개수', ]);
					  	
					  	defect_USE_STATUS = "";	
					  	defect_USE_STATUS = data[0].defect_USE_STATUS;
					  	
					  	gita_defect_ABR = 0;
					  	gita_defect_ABR_value = "기타 - ";
					  	
						for(i=0;i<data.length;i++)
						{
							if(i<=4)
							{
								drawArray.push([ String(data[i].defect_NAME), data[i].defect_ABR == "null"?0:parseInt(data[i].defect_ABR) ]);
							}
							else
							{
								gita_defect_ABR += data[i].defect_ABR == "null"?0:parseInt(data[i].defect_ABR);
								
								//gita_defect_ABR_value += " " + data[i].defect_NAME + " 개수:" + data[i].defect_ABR == "null"?0:parseInt(data[i].defect_ABR);
								
								num = data[i].defect_ABR == "null" ?0:parseInt(data[i].defect_ABR);
								
								if(isNaN(num))
									num = 0;
								
								gita_defect_ABR_value += " " + String(data[i].defect_NAME) + " 개수: "+ num + " , ";
							}
						}
						
						drawArray.push([ "기타", gita_defect_ABR ]);
						
						var data = google.visualization.arrayToDataTable(drawArray);
		
					    var options = {
					    	title: '작업 지시 번호 : '+defect_USE_STATUS+" 제품 이름 : "+row.getData().product_ITEM_NAME,
					        chartArea: {width: '50%'},
					        hAxis: {
					          title: gita_defect_ABR_value,
					          minValue: 0
					        },
					        vAxis: {
					        }
					    };
					    
					    var view = new google.visualization.DataView(data);
						view.setColumns([ 0, 1, {
							calc : "stringify",
							sourceColumn : 1,
							type : "string",
							role : "annotation"
						} ]);
					
					    var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
					
					    chart.draw(view, options);
					});
				}
			},
			rowDblClick:function(e, row){
		    },
		    
			columns:[
				{ title: "작업지시No", field: "oqcinspect_OqcInNo", headerHozAlign: "center", width: 160 },
				{ title: "제품이름", field: "product_ITEM_NAME", headerHozAlign: "center", width: 180},
				{ title: "설비이름", field: "cus_Name", headerHozAlign: "center", width: 180 },
				{ title: "규격", field: "product_INFO_STND_1", headerHozAlign: "center"},		
				{ title: "상태", field: "oqcinspect_Prcsn_Clsfc_Name", headerHozAlign: "center"},	
				{ title: "작업완료일", field: "oqcinspect_Date", headerHozAlign: "center"},	
				{ title: "지시수량", field: "oqcinspect_PQty", headerHozAlign: "center", align:"right",formatter:function(cell, formatterParams, onRendered){
				    //cell - the cell component
				    //formatterParams - parameters set for the column
				    //onRendered - function to call when the formatter has been rendered
				    
				    return isNaN(parseInt(cell.getValue())) ? 0 : parseInt(cell.getValue()); //return the contents of the cell;
				}},
				{ title: "생산수량", field: "oqcinspect_SaQty", headerHozAlign: "center", align:"right",formatter:function(cell, formatterParams, onRendered){
				    //cell - the cell component
				    //formatterParams - parameters set for the column
				    //onRendered - function to call when the formatter has been rendered

				    return isNaN(parseInt(cell.getValue())) ? 0 : parseInt(cell.getValue()); //return the contents of the cell;
				}},
				{ title: "불량수량", field: "oqcinspect_DQty", headerHozAlign: "center", align:"right", formatter:function(cell, formatterParams, onRendered){
				    //cell - the cell component
				    //formatterParams - parameters set for the column
				    //onRendered - function to call when the formatter has been rendered
				    
				    return isNaN(parseInt(cell.getValue())) ? 0 : parseInt(cell.getValue()); //return the contents of the cell;
				}
				,cellClick:function(e, cell){
					
			    }
				,cellEdited:function(cell){
			    }
				,cellEditCancelled:function(cell){
			    }},
			]
		});
		
		function MI_searchBtn1(){
			datas = {
					startDate : $("#startDate").val(),
					endDate : $("#endDate").val()
			}
			
			console.log("defectInsertRest/Search?data=" + encodeURI(JSON.stringify(datas)));
			
			$.get("../defectInsertRest/Search?data=" + encodeURI(JSON.stringify(datas)), function(data) {
				WorkOrder_tbl.setData(data);
			});
		}
		
		function MI_saveBtn1(){
			if(defectPerformance.getRows().length>0)
			{
				sum_defect_ABR = 0;
				defectPerformance.getData().forEach(function(em){
					sum_defect_ABR += isNaN(parseInt(em.defect_ABR)) ? 0 : parseInt(em.defect_ABR);
				});
				
				oqcinspect_SaQty = WorkOrder_tbl.getSelectedRows()[0].getCell("oqcinspect_SaQty").getValue();
				
				if(sum_defect_ABR > oqcinspect_SaQty)
				{
					alert("불량수량은 생산수량보다 클 수 없습니다.");
					return;
				}
				
				$.get("../defectInsertRest/Upsert?data=" + encodeURI(JSON.stringify(defectPerformance.getData())), function(data) {
					alert("저장되었습니다.");
					
					WorkOrder_tbl.getSelectedRows()[0].update({"oqcinspect_DQty":sum_defect_ABR});
				});
			}
		}
		
		// ---------------------------------------------------------------------------
		
		google.charts.load('current', {packages: ['corechart', 'bar']});
		google.charts.setOnLoadCallback(drawBasic);
		
		function drawBasic() {
		
			  $.get("../defectPerformanceMRest/Defect_Export_Init", function(data) {
			  		drawArray = [];
			  		drawArray.push([ '불량 개수', '불량 개수', ]);
			  
					data.forEach(function(element){
					    drawArray.push([ String(element.defect_NAME), 0 ]);
					});
					
					var data = google.visualization.arrayToDataTable(drawArray);
		
				    var options = {
				        chartArea: {width: '50%'},
				        hAxis: {
				          minValue: 0
				        },
				        vAxis: {
				        }
				    };
				
				    var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
				
				    chart.draw(data, options);
			  });
		
		      
		}
		
		// ---------------------------------------------------------------------------
		
		window.onload = function(){
             MI_searchBtn1();
        }