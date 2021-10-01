<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="nowDate" class="java.util.Date" />
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
        <div style="margin:10; width: 100%;height: 100%; position: absolute; border: solid;">
        	<select class="form-select form-select-lg" id="eqselect" name="eqselectn" style="width: 180px; height: 35px; font-size: 20px;" aria-label=".form-select-lg example">
        		<c:forEach var="item" items="${list}">
        			<option value="${item.EQUIPMENT_INFO_CODE}">${item.EQUIPMENT_INFO_NAME}</option>
        		</c:forEach>
        	</select>
        	
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	
        		<span><strong>작업지시일</strong></span> 
				<input id="startDate" class="today" type="date"> 
				<span style="text-align: center"><strong>~</strong></span>
				<input id="endDate" class="tomorrow" type="date">
				
				&nbsp;&nbsp;&nbsp;&nbsp;
				
				<span></span>
				<strong style="color: red;">기준일 : <fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd" /></strong>
        	
			<div class="btn-group-lg" role="group" aria-label="Basic radio toggle button group" >
				<c:forEach var="item" items="${list2}">
					<input type="radio" class="btn-check" name="options1" id="${item.CHILD_TBL_NO}" value="${item.CHILD_TBL_NO}" autocomplete="off"/>
				<label class="btn btn-outline-primary border border-secondary" name="labelOptions" style="border: solid;" id="${item.CHILD_TBL_NO}c" for="${item.CHILD_TBL_NO}">${item.CHILD_TBL_TYPE}</label>
				</c:forEach>
			</div>   
			
			<div id="WorkOrder_tbl"></div>     	
        </div>

		
		<script type="text/javascript">
			var initData = null;
	    	var initRow = null;
	    	var workOrder_EquipCode = null;
		
			var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
	            height:"82.5%",
	            layout:"fitColumns",
	            placeholder:"No Data Set",
	            resizableColumns:false,
	            rowClick:function(e, row){
	            	WorkOrder_tbl.deselectRow();
	    			row.select();
	            	
	            	radio_select(row.getData().workOrder_WorkStatus);
	            	initData = row.getData();
	            	workOrder_EquipCode = initData.workOrder_EquipCode;
	            	initRow = row;
	            	
	            	var array = document.getElementsByName("options1");
					array[array.length-1].setAttribute("disabled", '');
	            },
	            columns:[
	            	{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center", width: 160 },
	        		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", width: 100 },
	        		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width: 180},
	        		{ title: "규격", field: "product_INFO_STND_1", headerHozAlign: "center",width: 100},	
	        		{ title: "지시수량", field: "workOrder_PQty", headerHozAlign: "center", align:"right",width: 100},
	        		{ title: "작업지시일", field: "workOrder_OrderTime", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
	        		{ title: "작업지시완료일", field: "workOrder_CompleteOrderTime", align: "right", headerHozAlign: "center", width:160, formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
	        		{ title: "접수일", field: "workOrder_ReceiptTime", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
	        		{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
	        		{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center", width: 160, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}}
	            ],
	        });
			
			document.getElementById("eqselect").onchange = function(e){
				var today = new Date();
				
				var tomorrow = new Date();
				tomorrow = new Date(tomorrow.setDate(tomorrow.getDate()+1));
				
				$('.today').val(today.toISOString().substring(0, 10));
				$('.tomorrow').val(tomorrow.toISOString().substring(0, 10));
				
				document.getElementsByName("options1").forEach(e=>e.removeAttribute("disabled", ''));
                document.getElementsByName("options1").forEach(e=>e.checked = false);
                var array = document.getElementsByName("options1");
				array[array.length-1].setAttribute("disabled", '');
				
				var target = document.getElementById("eqselect");
				workOrder_EquipCode = target.options[target.selectedIndex].value;
				
				var target = document.getElementById("eqselect");
			    $.get("workOrderTABRest/MI_Search1?WorkOrder_EquipCode="+target.options[target.selectedIndex].value, function(data){
			    	WorkOrder_tbl.setData(data);
            	});
            }
			
			var initstartDate = null;
			document.getElementById("startDate").onclick = function(){
				initstartDate = $("#startDate").val();
			}
			
			document.getElementById("startDate").onchange = function(){
				var array = document.getElementsByName("options1");
				array[array.length-1].setAttribute("disabled", '');
				
				startDate = $("#startDate").val();
				endDate = $("#endDate").val();
				
				if(startDate > endDate)
				{
					alert("시작일은 끝일보다 클수 없습니다.");
					$("#startDate").val(initstartDate);
					return;
				}
				
				$.get("workOrderTABRest/MI_Searchd?WorkOrder_EquipCode="+workOrder_EquipCode+"&startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val(), function(data){
			    	WorkOrder_tbl.setData(data);
				});
			}
			
			var initendDate = null;
			document.getElementById("endDate").onclick = function(){
				initendDate = $("#endDate").val();
			}
			
			document.getElementById("endDate").onchange = function(){
				startDate = $("#startDate").val();
				endDate = $("#endDate").val();
				
				if(startDate > endDate)
				{
					alert("시작일은 끝일보다 클수 없습니다.");
					$("#endDate").val(initendDate);
					return;
				}
				
				$.get("workOrderTABRest/MI_Searchd?WorkOrder_EquipCode="+workOrder_EquipCode+"&startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val(), function(data){
			    	WorkOrder_tbl.setData(data);
				});
            }
			
			function radio_select(value) {
				document.getElementsByName("options1").forEach(e=>e.removeAttribute("disabled", ''));
            	
            	if(value === "291")
            	{
            		document.getElementsByName('options1')[0].checked = true;
            		document.getElementsByName('options1')[0].focus();
            	}
            	else if(value === "292")
            	{
            		document.getElementsByName('options1')[1].checked = true;
            		document.getElementsByName('options1')[1].focus();
            	}
            	else if(value === "293")
            	{
            		document.getElementsByName('options1')[2].checked = true;
            		document.getElementsByName('options1')[2].focus();
            	}
            	else if(value === "294")
            	{
            		document.getElementsByName('options1')[3].checked = true;
            		document.getElementsByName('options1')[3].focus();
            		document.getElementsByName("options1").forEach(e=>e.setAttribute("disabled", ''));
            	}
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
                			//alert(initData.workOrder_EquipCode);
                			//alert(workOrder_EquipCode);
                			
                			$.get("workListRest/OrderUpdate?workOrder_ONo="+ initData.workOrder_ONo+"&workOrder_EquipCode="+workOrder_EquipCode, function(data){
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
        					$.get("workOrderTABRest/MI_Search2?workOrder_ONo="+initData.workOrder_ONo, function(data){
        						initRow.update({"workOrder_CompleteTime":data.workOrder_CompleteTime,"workOrder_WorkStatus":data.workOrder_WorkStatus});
                				initData = data;
                				document.getElementsByName("options1").forEach(e=>e.setAttribute("disabled", ''));
                        	});
                    	});
                	}
                }
            });
		
			window.onload = function(){
				var array = document.getElementsByName("options1");
				array[array.length-1].setAttribute("disabled", '');
				
				document.getElementById("295").style.display = "none";
				document.getElementById("295c").style.display = "none";
				
				workOrder_EquipCode = "M001";
				
				$.get("workOrderTABRest/MI_Search1?WorkOrder_EquipCode=M001", function(data){
			    	WorkOrder_tbl.setData(data);
            	});
	        }
		</script>
    </body>
</html>