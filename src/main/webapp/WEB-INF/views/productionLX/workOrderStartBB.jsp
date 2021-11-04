<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<style>
		html, body {
			margin: 0;
			height: 100%;
			overflow: hidden;
		}
	</style>
</head>
<body>
	<div id="testModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	    
	      <div class="modal-header">
			<div class="form-group row">
				<label for="staticEmail" class="col-sm-2 col-form-label"><strong style="font-size: 40px;">검색</strong></label>
				<div class="col-sm-5" style="padding-top: 5px;">
					<input class="form-control form-control-lg" type="text" style="height: 50px; font-size: 35px;" id="Item_Word">
				</div>
			</div>
			
	      </div>
	      
	      <div class="modal-body" style="height: 400px;">
			<div  id="itemPopupTableModal"></div>
	      </div>
	    </div>
	  </div>
	</div>

	<div style="margin:10; width: 100%;height: 100%;position: absolute; border: solid;">
		<table style="width: 100%;">
			<tr>
				<td style="text-align: center;" colspan="3">
					<center>
					<div style="width: 60%; background-color:rgb(112,173,70); margin: 10px; text-align: center; border-radius: 5%;"><strong style="font-size: 40px; color: white;">태블릿 작업 관리 (Tablet work management)</strong></div> 
					</center>
				</td>
			</tr>

			<tr>
				<td style="width: 50%;">
					<center>
					<table style="width: 90%; background-color: rgb(82,153,217); border-radius: 5%; margin: 10px;"> 
						<tr>
							<td style="font-size: 50px; color: white; text-align: center; width: 40%;">
								설&nbsp;비
							</td>
							<td rowspan="2">
								<select class="form-select form-select-lg" id="eqselect" name="eqselectn" style="font-size: 60px; background-color: rgb(164,194,230); width: 90%; border:groove; border-color: black;border-width: 1px;" aria-label=".form-select-lg example">
									<c:forEach var="item" items="${list}">
										<option value="${item.EQUIPMENT_INFO_CODE}">${item.EQUIPMENT_INFO_NAME}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td style="font-size: 25px; color: white; text-align: center;">
								Mathine
							</td>
						</tr>
					</table>
					</center>
				</td>
				<td style="width: 50%;">
					<center>
					<table style="width: 90%; background-color: rgb(82,153,217); border-radius: 5%;" onclick="move()"> 
						<tr>
							<td style="font-size: 50px; color: white; text-align: center;">
								작업 지시 선택
							</td>
						</tr>
						<tr>
							<td style="font-size: 25px; color: white; text-align: center;">
								Choose a work instruction
							</td>
						</tr>
					</table>
					</center>
				</td>
			</tr>
			<tr>
				<td style="width: 50%;">
					<center>
						<table style="width: 90%; background-color: rgb(82,153,217); border-radius: 5%;"> 
							<tr>
								<td style="font-size: 50px; color: white; text-align: center; width: 40%;">
									품&nbsp;명
								</td>
								<td rowspan="2">
									<input class="form-control form-control-lg" type="text" id="n_len" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px;">
								</td>
							</tr>
							<tr>
								<td style="font-size: 25px; color: white; text-align: center;">
									Product name
								</td>
							</tr>

							<tr>
								<td style="font-size: 50px; color: white; text-align: center; width: 40%;">
									규&nbsp;격
								</td>
								<td rowspan="2">
									<input class="form-control form-control-lg" type="text" id="o_len" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px;">
								</td>
							</tr>
							<tr>
								<td style="font-size: 25px; color: white; text-align: center;">
									Product specifications
								</td>
							</tr>
						</table>
					</center>
				</td>
				<td style="width: 50%;">
					<center>
						<table style="width: 90%; height: 100%; background-color: rgb(82,153,217); border-radius: 5%; padding: 100px;"> 
							<tr>
								<td style="font-size: 50px; color: white; text-align: center; width: 40%;">
									총&nbsp;생&nbsp;산&nbsp;수&nbsp;량
								</td>
								<td rowspan="2">
									<input class="form-control form-control-lg" type="text" id="sum_qty" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px;">
								</td>
							</tr>
							<tr>
								<td style="font-size: 25px; color: white; text-align: center;">
									Product name
								</td>
							</tr>

							<tr>
								<td style="font-size: 50px; color: white; text-align: center; width: 40%;">
									생&nbsp;산&nbsp;수&nbsp;량
								</td>
								<td rowspan="2">
									<input class="form-control form-control-lg" type="text" id="current_qty" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px;">
								</td>
							</tr>
							<tr>
								<td style="font-size: 25px; color: white; text-align: center;">
									Product specifications
								</td>
							</tr>
						</table>
					</center>
				</td>
			</tr>
			<tr>
				<td style="width: 50%;">
					<center>
					<table style="width: 100%; border-radius: 5%; margin: 10px;"> 
						<tr>
							<td style="font-size: 50px; color: white; text-align: center; width: 32%;">
								<button type="button" id="workOrderInsertBBtn" class="btn btn-primary btn-lg" style="font-size:40px; color: black; background-color: rgb(246, 177, 148);">작 업 현 황</button>
							</td>
							<td rowspan="2">
							</td>
						</tr>
					</table>
					</center>
				</td>
				<td style="width: 50%;">
				</td>
			</tr>
		</table>

		<div style="padding: 0px 50px 520px 50px;" id="WorkOrder_tbl"></div>
		<div style="float:none; visibility:hidden;">
			<span><strong style="font-size: 20px;">작업시작일</strong></span> 
			<input id="startDate" class="today" type="date" style="width: 180px; height: 35px; font-size: 20px;"> 
			<span style="text-align: center"><strong>~</strong></span>
			<input id="endDate" class="tomorrow" type="date" style="width: 180px; height: 35px; font-size: 20px;">
			<input id="n_len_code" class="tomorrow" type="text" style="width: 180px; height: 35px; font-size: 20px;">
		</div>
	</div>
	
	<script>
			function move(){
				location.href = "/workOrderInsertB?code=<%out.print(request.getParameter("code"));%>";
			}
	</script>
	<script src="/js/productionLX/workOrderStartBB.js"></script>
</body>
</html>