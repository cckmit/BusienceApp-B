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
        	<div style="padding: 5px;">
        	
        		<span><strong style="font-size: 20px;">설비</strong></span> 
        		<select type="text" id="eqselect" style="width: 180px; height: 35px; font-size: 20px; background: #f5f5f5" aria-label=".form-select-lg example">
	        		<c:forEach var="item" items="${list}">
	        			<option value="${item.EQUIPMENT_INFO_CODE}">${item.EQUIPMENT_INFO_NAME}</option>
	        		</c:forEach>
        		</select>
        		
        		&nbsp;&nbsp;&nbsp;
        		
        		<span><strong style="font-size: 20px;">제품</strong></span> 
        		<select type="text" id="itselect" style="width: 200px; height: 35px; font-size: 20px; background: #f5f5f5" aria-label=".form-select-lg example">
	        		<c:forEach var="item" items="${list2}">
	        			<option value="${item.PRODUCT_ITEM_CODE}">${item.PRODUCT_ITEM_NAME}</option>
	        		</c:forEach>
        		</select>
        	
				<span></span>
				<strong style="color: red; font-size: 20px;">기준일 : <fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd" /></strong>
        	</div>
        	
        	
			<div style="padding: 5px;" class="btn-group-lg" role="group" aria-label="Basic radio toggle button group" >

				<span><strong style="font-size: 20px;">작업시작일</strong></span> 
				<input id="startDate" class="today" type="date" style="width: 180px; height: 35px; font-size: 20px;"> 
				<span style="text-align: center"><strong>~</strong></span>
				<input id="endDate" class="tomorrow" type="date" style="width: 180px; height: 35px; font-size: 20px;">
				
				&nbsp;&nbsp;&nbsp;&nbsp;

				<input type="radio" class="btn-check" name="options1" id="s" value="시작" autocomplete="off"/>
				<label class="btn btn-outline-primary border border-secondary" name="labelOptions" style="border: solid;" id="s">시작</label>

				<input type="radio" class="btn-check" name="options1" id="e" value="종료" autocomplete="off"/>
				<label class="btn btn-outline-primary border border-secondary" name="labelOptions" style="border: solid;" id="e">종료</label>

				<br/>

				<span><strong style="font-size: 20px;">시작중인 작업지시번호 :  </strong></span>
				<span><strong style="font-size: 20px;" id="csnum"></strong></span>
			</div>   
			
			<div id="WorkOrder_tbl"></div>     	
        </div>

		
		<script src="/js/productionLX/work_order_TABXO.js"></script>
    </body>
</html>