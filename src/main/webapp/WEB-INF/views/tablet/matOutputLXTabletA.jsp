<%@page import="com.busience.standard.dto.DTL_TBL"%>
<%@page import="com.busience.productionLX.dto.PRODUCTION_INFO_TBL"%>
<%@page import="java.util.List"%>
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
		<div class="row" style="margin: 5px;">
			<div class="col-md-12" style="border:solid; background-color:rgb(112,173,70); text-align: center; border-radius: 5%; font-size: 60px; color: white; text-align: center;">
				자 재 출 고 관 리
			</div>
		</div>
		
		<div class="row" style="margin: 5px;">
		<div class="col-md-6" style="padding:10px; border:solid; font-size: 40px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t2">
						품&nbsp;<font style="color:rgb(82,153,217);">설</font>&nbsp;명
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<select id="pdselect" name="eqselectn" class="form-select" size="2" style="width: 100%; height: 120px;">
							<%
								List<PRODUCTION_INFO_TBL> list = (List<PRODUCTION_INFO_TBL>)request.getAttribute("product_list");
								for(int i=0;i<list.size();i++)
								{
							%>
									<option value="<%=list.get(i).getPRODUCTION_PRODUCT_CODE()%>" stnd="<%=list.get(i).getPRODUCT_INFO_STND_1()%>"><%=list.get(i).getPRODUCTION_EQUIPMENT_INFO_NAME()%></option>
							<%
								}
							%>	
						</select>
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t3">
						규&nbsp;<font style="color:rgb(82,153,217);">설</font>&nbsp;격
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly type="text" id="sum_qty" style="width: 100%; height: 70%; text-align: right; padding-right: 5px;">
					</td>
				</tr>
			</table>
		</div>
		
		<div class="col-md-6" style="padding:10px; border:solid; font-size: 40px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t2">
						출&nbsp;<font>고</font>&nbsp;처
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<select id="dtselect" name="eqselectn" class="form-select" size="2" style="width: 100%; height: 120px;">
							<%
								List<DTL_TBL> list2 = (List<DTL_TBL>)request.getAttribute("dtl_list");
											
								for(int i=0;i<list2.size();i++)
								{
									out.println("<option value='"+list2.get(i).getCHILD_TBL_NO()+"'>"+list2.get(i).getCHILD_TBL_TYPE()+"</option>");
								}
							%>
						</select>
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t5">
						수&nbsp;<font style="color:rgb(82,153,217);">설</font>&nbsp;량
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input type="number" id="d_len" value="1" min="1" style="text-align:center; width: 100%; height: 70%; padding-right: 5px;">
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	
	<div class="row" style="margin: 5px;">
		<div class="col-md-6" style="border:solid; font-size: 40px; text-align: center; background-color: rgb(246, 177, 148); border-radius: 5%;">
			선&nbsp;택&nbsp;사&nbsp;항
		</div>
		<div class="col-md-3" id="okbtn" style="border:solid; font-size: 40px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
			확&nbsp;<font style="color:rgb(82,153,217);">설</font>&nbsp;<font style="color:rgb(82,153,217);">설</font>&nbsp;인
		</div>
		<div class="col-md-3" id="cancelbtn" style="border:solid; font-size: 40px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
			취&nbsp;<font style="color:rgb(82,153,217);">설</font>&nbsp;<font style="color:rgb(82,153,217);">설</font>&nbsp;소
		</div>
	</div>
	
	<div class="col-md-12" style="border:solid; text-align: center; border-radius: 5%; font-size: 40px; color: white; text-align: center;">
			<table style="width: 100%; border-spacing: 2px; border-collapse: separate;">
						<thead>
							<tr>
								<th style="text-align: center; background: rgb(68,114,196); color: white;">
								품&nbsp;명
								</th>
								<th style="text-align: center; background: rgb(68,114,196); color: white;">
									규&nbsp;격
								</th>
								<th style="text-align: center; background: rgb(68,114,196); color: white;">
									출&nbsp;고&nbsp;처
								</th>
								<th style="text-align: center; background: rgb(68,114,196); color: white;">
									출&nbsp;고&nbsp;수&nbsp;량
								</th>
								<th style="text-align: center; background: rgb(68,114,196); color: white;">
									날&nbsp;짜
								</th>
								<th style="text-align: center; background: rgb(68,114,196); color: white;">
									현&nbsp;재&nbsp;재&nbsp;고
								</th>
							</tr>
							
							<tr>
								<td style="text-align: center; background: rgb(207,213,234); color: black;" id="pdselect2">
									
								</td>
								
								<td style="text-align: center; background: rgb(207,213,234); color: black;" id="stnd">
									
								</td>
								
								<td style="text-align: center; background: rgb(207,213,234); color: black;" id="dtselect2">
									
								</td>
								
								<td style="text-align: center; background: rgb(207,213,234); color: black;" id="d_len2">
									1
								</td>
								
								<td style="text-align: center; background: rgb(207,213,234); color: black;" id="today">
									
								</td>
								
								<td style="text-align: center; background: rgb(207,213,234); color: black;" id="current_qty">
									0
								</td>
							</tr>
						</thead>
		</table>
	</div>
	
	<div class="row" style="margin: 5px;" id="ko">
		<div class="col-md-12" style="border:solid; text-align: center; border-radius: 5%; font-size: 40px; color: white; text-align: center;">
			<div id="WorkOrder_tbl" style="font-size: 30px;"></div>
		</div>
	</div>
	
	<div style="float:none; visibility:hidden;">
			<span><strong style="font-size: 20px;">작업시작일</strong></span> 
			<input id="startDate" class="today" type="date" style="width: 180px; height: 35px; font-size: 20px;"> 
			<span style="text-align: center"><strong>~</strong></span>
			<input id="endDate" class="tomorrow" type="date" style="width: 180px; height: 35px; font-size: 20px;">
			<input id="n_len_code" class="tomorrow" type="text" style="width: 180px; height: 35px; font-size: 20px;">
	</div>
	
	<div style="display: none;">
		<span><strong>출고일</strong></span> 
		<input id="matOutputList_startDate" class="today" type="date"> 
		<span style="text-align: center"><strong>~</strong></span> 
		<input id="matOutputList_endDate" class="tomorrow" type="date">
	</div>
	
	<script src="/js/tablet/matOutputLXTabletA.js"></script>

	<style>
		.tabulator { font-size: 16px; }
	</style>
</body>
</html>