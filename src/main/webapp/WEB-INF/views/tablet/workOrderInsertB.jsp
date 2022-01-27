<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="nowDate" class="java.util.Date" />

<style>
html, body {
	overflow: hidden;
}

.workActive{
	background-color: red !important;
}
</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

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
		<div  id="itemPopupTableModal" style="font-size: 30px;"></div>
      </div>
    </div>
  </div>
</div>

<div class="row" style="margin: 5px;">
	<div class="col-md-12" style="border:solid; text-align: center; background-color:rgb(112,173,70); text-align: center; border-radius: 5%;">
		<div style="font-size: 60px; color: white; text-align: center; width: 70%; float: left;" id="t1">작업 관리</div>

		<div style="font-size: 60px; color: black; text-align: center; width: 15%; float: left; background-color:red; border-radius: 10%; border: solid;" id="kor" onclick="lang_convert(this)">한글</div>
		<div style="font-size: 60px; color: black; text-align: center; width: 15%; float: left; background-color:white; border-radius: 10%; border: solid;" id="eng" onclick="lang_convert(this)">ENG</div>
	</div>
</div>

<div class="row" style="margin: 5px;">
	<div class="col-md-5" style="padding:10px; border:solid; font-size: 45px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
		<table style="width: 100%;"> 
			<tr>
				<td style="color: white; text-align: center; width: 40%;" id="t2">
					설&nbsp;&nbsp;&nbsp;비
				</td>
				<td style="color: black; text-align: center; width: 60%;">
					<select id="eqselect" name="eqselectn" style="width: 100%;">
						<c:forEach var="data" items="${machineList}">
							<c:choose>	
								<c:when test="${data.EQUIPMENT_INFO_CODE == machineCode}">
									<option value="${data.EQUIPMENT_INFO_CODE}" selected>${data.EQUIPMENT_INFO_NAME}</option>
								</c:when>									
								<c:otherwise>									
									<option value="${data.EQUIPMENT_INFO_CODE}">${data.EQUIPMENT_INFO_NAME}</option>									
								</c:otherwise>									
							</c:choose>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="col-md-4" style="padding:10px; border:solid; font-size: 45px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
		<table style="width: 100%;"> 
			<tr>
				<td style="color: white; text-align: center; width: 40%;">
					Today
				</td>
				<td style="color: white; text-align: center; width: 60%;">
					<fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd" />
				</td>
			</tr>
		</table>
	</div>
	
	<div class="col-md-3" style="padding:10px; border:solid; font-size: 45px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%; cursor: pointer;" onclick="move()">
		<font style="color: white;" id="t3">작업관리</font>
	</div>
</div>

<div class="row" style="margin: 5px;">
	<div class="col-md-12">
		<div style="text-align: center;" class="btn-group-md" role="group" aria-label="Basic radio toggle button group" >
			
			<input type="radio" class="btn-check" name="options1Name" id="" value="" autocomplete="off"/>
			<label class="btn btn-primary" name="labelOptions" style="background-color:rgb(112,173,70); color:white; font-size: 45px; margin-right: 5px; height: 70px;" id="t4" for="">수정</label>
			
			<input type="radio" class="btn-check" name="options22" value="" autocomplete="off"/>
			<label class="btn btn-primary" name="labelOptions2" style="visibility:hidden; font-size: 45px; border-color: rgb(246, 177, 148); background-color: rgb(237, 237, 237); color:black; margin-right: 5px; height: 70px;" id="t66">전체</label>
			
			<c:forEach var="item" items="${list2}" end="3">
				<input type="radio" class="btn-check modifyRadioBtn" name="options1" id="${item.CHILD_TBL_NO}" value="${item.CHILD_TBL_NO}" autocomplete="off"/>
				<label class="btn btn-primary" name="labelOptions" style="font-size: 45px;border-color: rgb(246, 177, 148); background-color: rgb(237, 237, 237); color: black; margin-right: 5px; height: 70px;" id="${item.CHILD_TBL_NO}c" for="${item.CHILD_TBL_NO}">${item.CHILD_TBL_TYPE}</label>
			</c:forEach>
		</div> 
	</div>
</div>

<div class="row" style="margin: 5px;">
	<div class="col-md-12">
		<div style="text-align: center;" class="btn-group-md" role="group" aria-label="Basic radio toggle button group" >
			
			<input type="radio" class="btn-check" name="options2Name" id="g1s" value="" autocomplete="off" checked/>
			<label class="btn btn-primary" name="labelOptions2" style="font-size: 45px; background-color:rgb(112,173,70); color:white; margin-right: 5px; height: 70px;" id="t5" for="g1s">조회</label>
			
			<input type="radio" class="btn-check" name="options2" id="alls" value="" autocomplete="off"/>
			<label class="btn btn-primary selectRadioBtn workActive" name="labelOptions2" style="font-size: 45px; border-color: rgb(246, 177, 148); background-color: rgb(237, 237, 237); color:black; margin-right: 5px; height: 70px;" id="alls" for="alls">전체</label>
			
			<c:forEach var="item" items="${list2}" end="3">
				<input type="radio" class="btn-check" name="options2" value="" autocomplete="off"/>
				<label class="btn btn-primary selectRadioBtn" name="labelOptions2" style="font-size: 45px; border-color: rgb(246, 177, 148); background-color: rgb(237, 237, 237); color: black; margin-right: 5px; height: 70px;" id="${item.CHILD_TBL_NO}s" for="${item.CHILD_TBL_NO}s">${item.CHILD_TBL_TYPE}</label>
			</c:forEach>
		</div> 
	</div>
</div>	

<div class="row" style="margin: 5px;" id="ko">
	<div class="col-md-12">
		<div id="WorkOrder_tbl" style="font-size: 18px;"></div>    
	</div>
</div>

<input id="startDate" class="today" type="hidden" style="width: 230px; height: 50px; font-size: 30px;"> 
		<input id="endDate" class="tomorrow" type="hidden" style="width: 230px; height: 50px; font-size: 30px;">

<script src="/js/tablet/workOrderInsertB.js"></script>
