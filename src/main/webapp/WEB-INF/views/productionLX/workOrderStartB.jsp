<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div style="margin:10; width: 100%;position: absolute; border: solid; overflow: scroll;">
		<div style="padding: 10px;">
			<span><strong style="font-size: 60px;">설비</strong></span> 
				&nbsp;&nbsp;&nbsp;&nbsp;
        	<select class="form-select form-select-lg" id="eqselect" name="eqselectn" style="font-size: 60px; background: #f5f5f5" aria-label=".form-select-lg example">
	        	<c:forEach var="item" items="${list}">
	        		<option value="${item.EQUIPMENT_INFO_CODE}">${item.EQUIPMENT_INFO_NAME}</option>
	        	</c:forEach>
        	</select>

			<%
				for(int i=0;i<5;i++)
				{
					out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				}
			%>

			<span>
			<button type="button" id="workOrderInsertBBtn" class="btn btn-primary btn-lg" style="font-size:40px;">작업지시 선택</button>
			</span> 
		</div>

		<div style="padding: 10px;">
			
			<div>
				<div style="float: left; width: 30%;">
					<span><strong style="font-size: 60px;">품명</strong></span>

					<br/>

					<select class="form-select" size="10" aria-label="size 10 select example" style="font-size: 30px;" id="pdselect">
						<c:forEach var="item" items="${list1}">
							<option value="${item.PRODUCT_ITEM_CODE}">${item.PRODUCT_ITEM_NAME}</option>
						</c:forEach>
					</select>
				</div>
				<div style="float: left; width: 30%;">
					<span><strong style="font-size: 60px;">길이</strong>
						<input class="form-control form-control-lg" type="text" id="n_len" style="font-size: 60px; height: 80px;">
					</span>

					<br/>
					
					<%
						for(int i=0;i<4;i++)
						{
							if(i < 3)
							{
								for(int j=0;j<3;j++)
								{
									out.println("<span>");
									out.println("<button type='button' class='btn btn-primary btn-lg' style='font-size:30px; margin:5px;' onclick='num_btn(this)'>");
									out.println((j+1)+(3*i));
									out.println("</button>");
									out.println("</span>");
								}
								out.println("<br/>");
							}
						}
					%>
					<span>
						<button type='button' class='btn btn-primary btn-lg' style='font-size:30px; margin:5px;' onclick='num_btn(this)'>
						0
						</button>
						<button type='button' class='btn btn-primary btn-lg' style='font-size:30px; margin:5px;' onclick='num_btn(this)'>
						.
						</button>
						<button type='button' class='btn btn-primary btn-lg' style='font-size:30px; margin:5px;' onclick='num_btn(this)'>
						X
						</button>
						<button type='button' class='btn btn-primary btn-lg' style='font-size:30px; margin:5px;' onclick='num_btn(this)'>
						C
						</button>
						<button type="button" class="btn btn-primary btn-lg" style="font-size:30px;" id="ipBtn">입력</button>
					</span>
				</div>
				<div style="float: left; width: 40%;">
					<span><strong style="font-size: 60px;">총 수량</strong></span>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<span><strong style="font-size: 60px;" id="sum_qty">0</strong></span>
					
					<br/>
					
					<span><strong style="font-size: 60px;">생산 수량</strong></span>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<span><strong style="font-size: 60px;" id="current_qty">0</strong></span>
				</div>
				
			</div>
			
			<div style="float:none; visibility:hidden;">
				<span><strong style="font-size: 20px;">작업시작일</strong></span> 
				<input id="startDate" class="today" type="date" style="width: 180px; height: 35px; font-size: 20px;"> 
				<span style="text-align: center"><strong>~</strong></span>
				<input id="endDate" class="tomorrow" type="date" style="width: 180px; height: 35px; font-size: 20px;">
			</div>
			
			<div style="width: 100%;" id="WorkOrder_tbl"></div>
		</div>
	</div>
	
	<script src="/js/productionLX/workOrderStartB.js"></script>
</body>
</html>