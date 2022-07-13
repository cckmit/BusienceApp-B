<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.busience.tablet.dto.CrateDto"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.Connection"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
/* 
Date nowTime = new Date();
SimpleDateFormat sf = new SimpleDateFormat("yyyy년 MM월 dd일 a hh:mm:ss");

//document.getElementById("curentTime").innerHTML = sf.format(date);

		myTimer();
		setInterval(myTimer, 1000); */
%>
<!DOCTYPE html>
<html lang="ko">
<head></head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>8 Dang Monitoring System</title>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
<body
	style="background-color: white; max-width: 100%; overflow-x: hidden; overflow-y: hidden;">

	<h1 style="font-weight: bold; font-size: 35px; margin-top: 2%;"
		align="center">작업 모니터링</h1>
	<p id="currentTime"
		style="font-size: 20px; color: rgb(188, 188, 188); margin-right: 2.5%; margin-top: -2%;"
		align="right"></p>
	<div class="row"
		style="display: flex; flex-direction: row; margin-left: 89%;">
		<p id="timer" style="font-size: 15px; margin-right: 3%;"></p>
		<button id="refresher" type="button"
			style="float: right; margin-top: -0.3%; margin-left: 6.7%; position: absolute;"
			class="btn btn-light" onclick="myTimerReset()">↻</button>
	</div>

	<div class="col">
		<div class="row"
			style="display: flex; flex-direction: row; margin-top: 0.5%;">
			<table class="table table-bordered"
				style="width: 43%; font-size: 15px; margin-left: 2.5%;">
				<thead>
					<tr>
						<td style="font-size: 17px; text-align: center;" colspan="7">[
							생 산 중 ]</td>
					</tr>
					<tr class="table-active">
						<th scope="col" style="text-align: center;">호기</th>
						<th scope="col" style="text-align: center;">품명</th>
						<th scope="col" style="text-align: center;">규격</th>
						<th scope="col" style="text-align: center;">재질</th>
						<th scope="col" style="text-align: center;">분류1</th>
						<th scope="col" style="text-align: center;">분류2</th>
						<th scope="col" style="text-align: center;">생산량</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="data" items="${machineList}" varStatus="status">
						<c:choose>
							<c:when test="${status.count < 10}">
								<tr class="table-active"
									id="prodRow_machineCode_M00${status.count}">
									<td style="text-align: center;"><div
											id="machineCode_M00${status.count}">${status.count}</div></td>
									<td style="text-align: center;"><div
											id="itemDesc_M00${status.count}"></div></td>
									<td style="text-align: center;"><div
											id="itemStnd1_M00${status.count}"></div></td>
									<td style="text-align: center;"><div
											id="itemMaterial_M00${status.count}"></div></td>
									<td style="text-align: center;"><div
											id="itemType1_M00${status.count}"></div></td>
									<td style="text-align: center;"><div
											id="itemType2_M00${status.count}"></div></td>
									<td style="text-align: center;"><div
											id="itemProdQty_M00${status.count}"></div></td>
								</tr>
							</c:when>
							<c:when test="${status.count == 10}">
								<tr class="table-active"
									id="prodRow_machineCode_M00${status.count}">
									<td style="text-align: center;"><div
											id="machineCode_M0${status.count}">${status.count}</div></td>
									<td style="text-align: center;"><div
											id="itemDesc_M0${status.count}"></div></td>
									<td style="text-align: center;"><div
											id="itemStnd1_M0${status.count}"></div></td>
									<td style="text-align: center;"><div
											id="itemMaterial_M0${status.count}"></div></td>
									<td style="text-align: center;"><div
											id="itemType1_M0${status.count}"></div></td>
									<td style="text-align: center;"><div
											id="itemType2_M0${status.count}"></div></td>
									<td style="text-align: center;"><div
											id="itemProdQty_M0${status.count}"></div></td>
								</tr>
							</c:when>
						</c:choose>
					</c:forEach>
				</tbody>
			</table>


			<table class="table table-bordered"
				style="width: 50%; height: 55%; font-size: 15px; margin-left: 2%;">
				<thead>
					<tr>
						<td style="font-size: 17px; text-align: center;" colspan="9">[
							포 장 중 ]</td>
					</tr>
					<tr class="table-active">
						<th scope="col" style="text-align: center;">대기수량</th>
						<th scope="col" style="text-align: center;">호기</th>
						<th scope="col" style="text-align: center;">생산량</th>
						<th scope="col" style="text-align: center;">품명</th>
						<th scope="col" style="text-align: center;">규격1</th>
						<th scope="col" style="text-align: center;">규격2</th>
						<th scope="col" style="text-align: center;">재질</th>
						<th scope="col" style="text-align: center;">분류1</th>
						<th scope="col" style="text-align: center;">분류2</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="data" items="${machineList}" varStatus="status">
						<c:choose>
							<c:when test="${status.count > 10 and status.count <= 18}">
								<tr class="table-active"
									id="wrapRow_machineCode_M10${status.count - 10}">
									<td style="text-align: center;"><div
											id="itemWrapRemainQty_M10${status.count - 10}"></div></td>
									<td style="text-align: center;"><div
											id="machineCode_M10${status.count - 10}">${status.count - 10}</div></td>
									<td style="text-align: center;"><div
											id="itemWrapQty_M10${status.count - 10}"></div></td>
									<td style="text-align: center;"><div
											id="itemDesc_M10${status.count - 10}"></div></td>
									<td style="text-align: center;"><div
											id="itemStnd1_M10${status.count - 10}"></div></td>
									<td style="text-align: center;"><div
											id="itemStnd2_M10${status.count - 10}"></div></td>
									<td style="text-align: center;"><div
											id="itemMaterial_M10${status.count - 10}"></div></td>
									<td style="text-align: center;"><div
											id="itemType1_M10${status.count - 10}"></div></td>
									<td style="text-align: center;"><div
											id="itemType2_M10${status.count - 10}"></div></td>
								</tr>
							</c:when>
						</c:choose>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	

	<div class="col">
		<div class="row"
			style="display: flex; flex-direction: row; margin-top: 1%;">
			<table class="table table-bordered"
				style="width: 43%; height: 100%; font-size: 15px; margin-left: 2.5%;">
				<thead>
					<tr>
						<td style="font-size: 17px; text-align: center;" colspan="7">[
							변경된 생산 모델 ]</td>
					</tr>
					<tr class="table-active">
						<th scope="col" style="text-align: center;">호기</th>
						<th scope="col" style="text-align: center;">품명</th>
						<th scope="col" style="text-align: center;">규격</th>
						<th scope="col" style="text-align: center;">재질</th>
						<th scope="col" style="text-align: center;">분류1</th>
						<th scope="col" style="text-align: center;">분류2</th>
						<th scope="col" style="text-align: center;">생산량</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="data" items="${machineList}" varStatus="status">
						<c:choose>
							<c:when test="${status.count < 4}">
								<tr class="table-active"
									id="changedProdItemProdRow_machineCode_${status.count}">
									<td style="text-align: center;"><div
											id="changedProdItemMachineCode_${status.count}">&nbsp;</div></td>
									<td style="text-align: center;"><div
											id="changedProdItemDesc_${status.count}"></div></td>
									<td style="text-align: center;"><div
											id="changedProdItemStnd1_${status.count}"></div></td>
									<td style="text-align: center;"><div
											id="changedProdItemMaterial_${status.count}"></div></td>
									<td style="text-align: center;"><div
											id="changedProdItemType1_${status.count}"></div></td>
									<td style="text-align: center;"><div
											id="changedProdItemType2_${status.count}"></div></td>
									<td style="text-align: center;"><div
											id="changedProdItemProdQty_${status.count}"></div></td>
								</tr>
							</c:when>
						</c:choose>
					</c:forEach>
				</tbody>
			</table>


			<table class="table table-bordered"
				style="width: 50%; height: 100%; font-size: 15px; margin-left: 2%;">
				<thead>
					<tr>
						<td style="font-size: 17px; text-align: center;" colspan="9">[
							변경된 포장 모델 ]</td>
					</tr>
					<tr class="table-active">
						<th scope="col" style="text-align: center;">대기수량</th>
						<th scope="col" style="text-align: center;">호기</th>
						<th scope="col" style="text-align: center;">생산량</th>
						<th scope="col" style="text-align: center;">품명</th>
						<th scope="col" style="text-align: center;">규격1</th>
						<th scope="col" style="text-align: center;">규격2</th>
						<th scope="col" style="text-align: center;">재질</th>
						<th scope="col" style="text-align: center;">분류1</th>
						<th scope="col" style="text-align: center;">분류2</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="data" items="${machineList}" varStatus="status">
						<c:choose>
							<c:when test="${status.count > 10 and status.count <= 13}">
								<tr class="table-active"
									id="changedWrapItemWrapRow_machineCode_${status.count - 10}">
									<td style="text-align: center;"><div
											id="changedWrapItemWrapRemainQty_${status.count - 10}"></div></td>
									<td style="text-align: center;"><div
											id="changedWrapItemMachineCode_${status.count - 10}">&nbsp;</div></td>
									<td style="text-align: center;"><div
											id="changedWrapItemWrapQty_${status.count - 10}"></div></td>
									<td style="text-align: center;"><div
											id="changedWrapItemDesc_${status.count - 10}"></div></td>
									<td style="text-align: center;"><div
											id="changedWrapItemStnd1_${status.count - 10}"></div></td>
									<td style="text-align: center;"><div
											id="changedWrapItemStnd2_${status.count - 10}"></div></td>
									<td style="text-align: center;"><div
											id="changedWrapItemMaterial_${status.count - 10}"></div></td>
									<td style="text-align: center;"><div
											id="changedWrapItemType1_${status.count - 10}"></div></td>
									<td style="text-align: center;"><div
											id="changedWrapItemType2_${status.count - 10}"></div></td>
								</tr>
							</c:when>
						</c:choose>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
<script src="/js/monitoring/productionStatus.js?v=<%=System.currentTimeMillis()%>"></script>