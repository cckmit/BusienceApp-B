<%@page import="java.net.InetAddress"%>
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
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    
		<style>
			html, body {
				margin: 0;
				height: 100%;
				overflow: hidden;
			}
		</style>
		<link rel="shortcut icon" href="/icon/Fasticon-Database-Data-right.ico" />
	</head>
    <body>
        <div style="margin:10; width: 100%;height: 100%;position: absolute; border: solid;">
			<table style="width: 100%;">
				<tr>
					<td style="text-align: center;" colspan="3">
						<center>
						<div style="width: 60%; background-color:rgb(112,173,70); margin: 10px; text-align: center; border-radius: 5%;"><strong style="font-size: 40px; color: white;">태블릿 작업 지시 <br/>Tablet work Order</strong></div> 
						</center>
					</td>
				</tr>

				<tr>
					<td style="width: 40%;">
						<center>
						<table style="width: 90%; background-color: rgb(82,153,217); border-radius: 5%; margin: 10px;"> 
							<tr>
								<td style="font-size: 50px; color: white; text-align: center; width: 40%;">
									설&nbsp;비
								</td>
								<td rowspan="2" style="padding: 10px;">
									<select class="form-select form-select-lg" id="eqselect" name="eqselectn" style="font-size: 60px; background-color: rgb(164,194,230); width: 90%; border:groove; border-color: black;border-width: 1px;" aria-label=".form-select-lg example">
										<c:forEach var="item" items="${list}">
											<option value="${item.EQUIPMENT_INFO_CODE}">${item.EQUIPMENT_INFO_NAME}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="font-size: 40px; color: white; text-align: center;">
									Machine
								</td>
							</tr>
						</table>
						</center>
					</td>
					<td style="width: 35%;">
						<center>
							<table style="width: 90%; height: 120px; background-color: rgb(82,153,217); border-radius: 5%; margin: 10px;"> 
								<tr>
									<td style="font-size: 50px; color: white; text-align: center; width: 30%;">
										Today
									</td>
									<td style="font-size: 50px; color: white; width: 70%; text-align: right; padding-right: 10px;">
										<fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd" />
									</td>
								</tr>
							</table>
						</center>
					</td>
					<td style="width: 25%; padding-right: 65px;">
						<center>
							<table style="width: 100%; height: 120px; background-color: rgb(82,153,217); border-radius: 5%; margin: 10px;" id="move"> 
								<tr>
									<td style="font-size: 50px; color: white; text-align: center; width: 30%;">
										태블릿 작업관리
									</td>
								</tr>
							</table>
						</center>
					</td>
				</tr>
			</table>

			<div style="float: left;">
				<button type="button" id="SearchBtn" class="btn btn-primary btn-lg" style="font-size:40px; color: black; background-color: rgb(246, 177, 148);margin-left: 45px;margin-right: 200px; visibility: hidden;">작 업 지 시 (Work Order)</button>
			</div>

			<div style="padding: 5px;" class="btn-group-md" role="group" aria-label="Basic radio toggle button group" >
				<c:forEach var="item" items="${list2}">
					<input type="radio" class="btn-check" name="options1" id="${item.CHILD_TBL_NO}" value="${item.CHILD_TBL_NO}" autocomplete="off"/>
					<label class="btn btn-primary" name="labelOptions" style="border-color: rgb(246, 177, 148); background-color: rgb(237, 237, 237); font-size: 40px; color: black;" id="${item.CHILD_TBL_NO}c" for="${item.CHILD_TBL_NO}">${item.CHILD_TBL_TYPE}</label>
				</c:forEach>
			</div> 

        	<input id="startDate" class="today" type="hidden" style="width: 230px; height: 50px; font-size: 30px;"> 
			<input id="endDate" class="tomorrow" type="hidden" style="width: 230px; height: 50px; font-size: 30px;">
			<div id="WorkOrder_tbl" style="height: 100%;padding: 0px 50px 390px 50px;"></div>    
			
        </div>

		<script>
			function move(){
				location.href = "/workOrderStartBB?code=<%out.print(request.getParameter("code"));%>";
			}
		</script>
		<script src="/js/productionLX/workOrderInsertB.js"></script>

		<style>
			.tabulator { font-size: 16px; }
		</style>
    </body>
	
</html>