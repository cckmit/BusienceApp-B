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

		
		<script src="/js/productionLX/work_order_TAB.js"></script>
    </body>
</html>