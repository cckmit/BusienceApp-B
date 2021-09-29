<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">    
        <style>
            html, body {
                margin: 0;
                height: 100%;
                overflow: hidden;
            }
        </style>
    </head>
    <body>
        <div style="width: 100%;height: 100%; position: absolute;">
        	<div class="tabs-wrap">
            	<ul id="navigation">
            		<li class="one selected"><a href="#div1" id="btnradio1" onclick="btnradioClick()">설비명1</a></li>
            		<li class="two"><a id="q" href="#div2" id="btnradio2" onclick="btnradioClick()">설비명2</a></li>
            	</ul>
            	
            	<div id="content" >
            		<ol>
            			<li>
							<div id="div1">
								<div class="btn-group-lg" role="group" aria-label="Basic radio toggle button group" >
					                <input type="radio" class="btn-check" name="options1" id="option1" value="291" autocomplete="off"/>
					                <label class="btn btn-outline-primary" name="labelOptions" for="option1" id="op1">미접수</label>
					              
					                <input type="radio" class="btn-check" name="options1" id="option2" value="292" autocomplete="off"/>
					                <label class="btn btn-outline-primary" name="labelOptions" for="option2">접수완료</label>
					              
					                <input type="radio" class="btn-check" name="options1" id="option3" value="293" autocomplete="off"/>
					                <label class="btn btn-outline-primary" name="labelOptions" for="option3">작업시작</label>
					
					                <input type="radio" class="btn-check" name="options1" id="option4" value="294" autocomplete="off"/>
					                <label class="btn btn-outline-primary" name="labelOptions" for="option4">작업완료</label>
					            </div>
								<div id="WorkOrder_tbl1"></div>
							</div>
						</li>
						<li>
							<div id="div2">
								<div class="btn-group-lg" role="group" aria-label="Basic radio toggle button group" >
					                <input type="radio" class="btn-check" name="options2" id="option1" value="291" autocomplete="off"/>
					                <label class="btn btn-outline-primary" name="labelOptions" for="option1" id="op1">미접수</label>
					              
					                <input type="radio" class="btn-check" name="options2" id="option2" value="292" autocomplete="off"/>
					                <label class="btn btn-outline-primary" name="labelOptions" for="option2">접수완료</label>
					              
					                <input type="radio" class="btn-check" name="options2" id="option3" value="293" autocomplete="off"/>
					                <label class="btn btn-outline-primary" name="labelOptions" for="option3">작업시작</label>
					
					                <input type="radio" class="btn-check" name="options2" id="option4" value="294" autocomplete="off"/>
					                <label class="btn btn-outline-primary" name="labelOptions" for="option4">작업완료</label>
					            </div>
								<div id="WorkOrder_tbl2"></div>
							</div>
						</li>
            		</ol>
            	</div>
            </div>
        </div>

		<script src="${contextPath}/resources/js/tabMenu.js"></script>
        <script>
        	var initData = null;
        	var initRow = null;
        
            var WorkOrder_tbl1 = new Tabulator("#WorkOrder_tbl1", {
                height:"90%",
                layout:"fitColumns",
                placeholder:"No Data Set",
                resizableColumns:false,
                ajaxURL:"WorkOrderTABRest/MI_Search1?WorkOrder_EquipCode=M001",
                rowClick:function(e, row){
                	WorkOrder_tbl1.deselectRow();
        			row.select();
                	
                	radio_select(row.getData().workOrder_WorkStatus);
                	
                	initData = row.getData();
                	
                	initRow = row;
                },
                columns:[
                    { title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center" , width:200 },
                    { title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", width:120 },
                    { title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width:150},
                    { title: "접수일", field: "workOrder_ReceiptTime", align: "right", headerHozAlign: "center", width:160},
                    { title: "작업지시일", field: "workOrder_OrderTime", align: "right", headerHozAlign: "center", width:150},
                    { title: "작업지시완료일", field: "workOrder_CompleteOrderTime", align: "right", headerHozAlign: "center", width:160},
                    { title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center", width:150},
                    { title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center", width:150},
                ],
            });
            
            var WorkOrder_tbl2 = new Tabulator("#WorkOrder_tbl2", {
                height:"90%",
                layout:"fitColumns",
                placeholder:"No Data Set",
                resizableColumns:false,
                ajaxURL:"WorkOrderTABRest/MI_Search1?WorkOrder_EquipCode=M002",
                rowClick:function(e, row){
                	WorkOrder_tbl2.deselectRow();
        			row.select();
                	
                	radio_select(row.getData().workOrder_WorkStatus);
                	
                	initData = row.getData();
                	
                	initRow = row;
                },
                columns:[
                    { title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center" , width:200 },
                    { title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", width:120 },
                    { title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width:150},
                    { title: "접수일", field: "workOrder_ReceiptTime", align: "right", headerHozAlign: "center", width:160},
                    { title: "작업지시일", field: "workOrder_OrderTime", align: "right", headerHozAlign: "center", width:150},
                    { title: "작업지시완료일", field: "workOrder_CompleteOrderTime", align: "right", headerHozAlign: "center", width:160},
                    { title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center", width:150},
                    { title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center", width:150},
                ],
            });
            
            function btnradioClick() {
            	document.getElementsByName("options1").forEach(e=>e.removeAttribute("disabled", ''));
                document.getElementsByName("options1").forEach(e=>e.checked = false);
                document.getElementsByName("options2").forEach(e=>e.removeAttribute("disabled", ''));
                document.getElementsByName("options2").forEach(e=>e.checked = false);
                WorkOrder_tbl1.deselectRow();
                WorkOrder_tbl2.deselectRow();
			} 
            
            $('input[type=radio][name=options1]').change(function() {
            	if(initData == null)
            		return;
            	
                if(initData.workOrder_WorkStatus !== this.getAttribute("value"))
                {
                	// 미접수
                	if(initData.workOrder_WorkStatus === "291")
                	{
                		if(this.getAttribute("value") === "293" || this.getAttribute("value") === "294")
                		{
                			alert("미접수된 데이터는 접수완료를 선택하여 주십시오.");
                			radio_select("291");
                			return;
                		}
                		
                		$.get("workOrderListRest/OrderUpdate?workOrder_ONo="+initData.workOrder_ONo, function(data){
                			//Equip_Select("M001");
                			//console.log(initRow.getData());
                			initRow.update({"workOrder_ReceiptTime":data.workOrder_ReceiptTime,"workOrder_WorkStatus":data.workOrder_WorkStatus});
                			initData = data;
                    	});
                	}
                	// 접수완료
                	else if(initData.workOrder_WorkStatus === "292")
                	{
                		if(this.getAttribute("value") === "294")
                		{
                			alert("접수완료된 데이터는 작업시작을 선택하여 주십시오.");
                			radio_select("292");
                			return;
                		}
                		
                		if(this.getAttribute("value") === "291")
                		{
                			$.get("workOrderListRest/OrderUpdate2?workOrder_ONo="+initData.workOrder_ONo, function(data){
                				initRow.update({"workOrder_ReceiptTime":null,"workOrder_WorkStatus":data.workOrder_WorkStatus});
                				initData = data;
                        	});
                			return;
                		}
                		
                		if(this.getAttribute("value") === "293")
                		{
                			$.get("workListRest/OrderUpdate?workOrder_ONo="+ initData.workOrder_ONo+"&workOrder_EquipCode="+initData.workOrder_EquipCode, function(data){
                				if(data==="OK")
                				{
                					alert("해당 호기에 이미 작업시작이 된 데이터가 존재합니다.");
                					radio_select("292");
                					return;
                				}
                				else
                				{
                					//MI_Search2
                					$.get("WorkOrderTABRest/MI_Search2?workOrder_ONo="+initData.workOrder_ONo, function(data){
                        				initRow.update({"workOrder_StartTime":data.workOrder_StartTime,"workOrder_WorkStatus":data.workOrder_WorkStatus});
                        				initData = data;
                                	});
                				}
                        	});
                		}
                	}
                	// 작업시작
                	else if(initData.workOrder_WorkStatus === "293")
                	{
                		if(this.getAttribute("value") === "291" || this.getAttribute("value") === "292")
                		{
                			alert("작업시작된 데이터는 작업완료를 선택하여 주십시오.");
                			radio_select("293");
                			return;
                		}
                		
                		$.get("workListRest/OrderUpdate2?workOrder_ONo="+ initData.workOrder_ONo+"&workOrder_EquipCode="+initData.workOrder_EquipCode, function(data){
                			//MI_Search2
        					$.get("WorkOrderTABRest/MI_Search2?workOrder_ONo="+initData.workOrder_ONo, function(data){
        						initRow.update({"workOrder_CompleteTime":data.workOrder_CompleteTime,"workOrder_WorkStatus":data.workOrder_WorkStatus});
                				initData = data;
                				document.getElementsByName("options").forEach(e=>e.setAttribute("disabled", ''));
                        	});
                    	});
                	}
                }
            });
            
            $('input[type=radio][name=options2]').change(function() {
            	if(initData == null)
            		return;
            	
                if(initData.workOrder_WorkStatus !== this.getAttribute("value"))
                {
                	// 미접수
                	if(initData.workOrder_WorkStatus === "291")
                	{
                		if(this.getAttribute("value") === "293" || this.getAttribute("value") === "294")
                		{
                			alert("미접수된 데이터는 접수완료를 선택하여 주십시오.");
                			radio_select("291");
                			return;
                		}
                		
                		$.get("workOrderListRest/OrderUpdate?workOrder_ONo="+initData.workOrder_ONo, function(data){
                			//Equip_Select("M001");
                			//console.log(initRow.getData());
                			initRow.update({"workOrder_ReceiptTime":data.workOrder_ReceiptTime,"workOrder_WorkStatus":data.workOrder_WorkStatus});
                			initData = data;
                    	});
                	}
                	// 접수완료
                	else if(initData.workOrder_WorkStatus === "292")
                	{
                		if(this.getAttribute("value") === "294")
                		{
                			alert("접수완료된 데이터는 작업시작을 선택하여 주십시오.");
                			radio_select("292");
                			return;
                		}
                		
                		if(this.getAttribute("value") === "291")
                		{
                			$.get("workOrderListRest/OrderUpdate2?workOrder_ONo="+initData.workOrder_ONo, function(data){
                				initRow.update({"workOrder_ReceiptTime":null,"workOrder_WorkStatus":data.workOrder_WorkStatus});
                				initData = data;
                        	});
                			return;
                		}
                		
                		if(this.getAttribute("value") === "293")
                		{
                			$.get("workListRest/OrderUpdate?workOrder_ONo="+ initData.workOrder_ONo+"&workOrder_EquipCode="+initData.workOrder_EquipCode, function(data){
                				if(data==="OK")
                				{
                					alert("해당 호기에 이미 작업시작이 된 데이터가 존재합니다.");
                					radio_select("292");
                					return;
                				}
                				else
                				{
                					//MI_Search2
                					$.get("WorkOrderTABRest/MI_Search2?workOrder_ONo="+initData.workOrder_ONo, function(data){
                        				initRow.update({"workOrder_StartTime":data.workOrder_StartTime,"workOrder_WorkStatus":data.workOrder_WorkStatus});
                        				initData = data;
                                	});
                				}
                        	});
                		}
                	}
                	// 작업시작
                	else if(initData.workOrder_WorkStatus === "293")
                	{
                		if(this.getAttribute("value") === "291" || this.getAttribute("value") === "292")
                		{
                			alert("작업시작된 데이터는 작업완료를 선택하여 주십시오.");
                			radio_select("293");
                			return;
                		}
                		
                		$.get("workListRest/OrderUpdate2?workOrder_ONo="+ initData.workOrder_ONo+"&workOrder_EquipCode="+initData.workOrder_EquipCode, function(data){
                			//MI_Search2
        					$.get("WorkOrderTABRest/MI_Search2?workOrder_ONo="+initData.workOrder_ONo, function(data){
        						initRow.update({"workOrder_CompleteTime":data.workOrder_CompleteTime,"workOrder_WorkStatus":data.workOrder_WorkStatus});
                				initData = data;
                				document.getElementsByName("options").forEach(e=>e.setAttribute("disabled", ''));
                        	});
                    	});
                	}
                }
            });
            
            function radio_select(value) {
            	for(i=1;i<=2;i++)
            	{
            		document.getElementsByName("options"+i).forEach(e=>e.removeAttribute("disabled", ''));
                	
                	if(value === "291")
                	{
                		document.getElementsByName('options'+i)[0].checked = true;
                		document.getElementsByName('options'+i)[0].focus();
                	}
                	else if(value === "292")
                	{
                		document.getElementsByName('options'+i)[1].checked = true;
                		document.getElementsByName('options'+i)[1].focus();
                	}
                	else if(value === "293")
                	{
                		document.getElementsByName('options'+i)[2].checked = true;
                		document.getElementsByName('options'+i)[2].focus();
                	}
                	else if(value === "294")
                	{
                		document.getElementsByName('options'+i)[3].checked = true;
                		document.getElementsByName('options'+i)[3].focus();
                		document.getElementsByName("options"+i).forEach(e=>e.setAttribute("disabled", ''));
                	}
            	}
			}
        </script>
        
    </body>
</html>