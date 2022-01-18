<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
html, body {
	overflow: hidden;
}
@keyframes blink-effect {
	50% {
		opacity: 0;
	}
}
.complete{
	animation: blink-effect 3s step-end infinite;
}
</style>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <font style="font-size: 40px;">에러 발생</font>
      </div>
      <div class="modal-body" style="font-size: 30px;">
        작업완료 버튼을 누르세요.
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-bs-dismiss="modal" style="font-size: 40px; border: solid;" onclick="modalClose()">Close</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="font-size: 40px; border: solid;" onclick="workOrderComplete()">작업완료</button>
      </div>
    </div>
  </div>
</div>

<div class="row" style="margin: 5px;">
	<div class="col-md-12" style="border:solid; background-color:rgb(112,173,70); text-align: center; border-radius: 5%;">
		<div style="font-size: 60px; color: white; text-align: center; width: 100%; float: left;" id="t1">작업 관리</div>
		<input style="display: none" id="eqselect" value="M002">
	</div>
</div>

<div class="row" style="margin: 5px;">
	<div class="col-md-6" style="padding:10px; border:solid; font-size: 50px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
		 <div style="float: left; width: 30%; color: white; text-align: center;" id="t6">
		 	품&nbsp;명
		 </div>
		 <div style="float: left; width: 70%;">
		 	<input readonly="readonly" type="text" id="n_len" style="width: 100%; height: 70%;">
		 </div>
	</div>
	<div class="col-md-6" style="padding:10px; border:solid; font-size: 50px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
		<div style="float: left; width: 30%; color: white; text-align: center;" id="t7">
		 	규&nbsp;격
		 </div>
		 <div style="float: left; width: 70%;">
		 	<input readonly="readonly" type="text" id="o_len" style="width: 100%; height: 70%; text-align:right;">
		 </div>
	</div>
</div>

<div class="row" style="margin: 5px;">
	<div class="col-md-4" style="padding:10px; border:solid; font-size: 45px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
		 <div style="float: left; width: 50%; color: white; text-align: center;" id="t6">
		 	설비1 수량
		 </div>
		 <div style="float: left; width: 50%;">
		 	<input readonly="readonly" type="text" style="width: 100%; height: 70%; text-align:right;" id="M001_count">
		 </div>
	</div>
	<div class="col-md-4" style="padding:10px; border:solid; font-size: 45px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
		<div style="float: left; width: 50%; color: white; text-align: center;" id="t7">
		 	설비2 수량
		 </div>
		 <div style="float: left; width: 50%;">
		 	<input readonly="readonly" type="text" style="width: 100%; height: 70%; text-align:right;" id="M002_count">
		 </div>
	</div>
	<div class="col-md-4" style="padding:10px; border:solid; font-size: 45px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
		<div style="float: left; width: 30%; color: white; text-align: center;" id="t7">
		 	차이
		 </div>
		 <div style="float: left; width: 70%;">
		 	<input readonly="readonly" type="text" style="width: 100%; height: 70%; text-align:right;" id="gap_count">
		 </div>
	</div>
</div>

<div class="row" style="margin: 5px;">
	<div class="col-md-6" style="padding: 6.5px; border:solid; font-size: 40px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
		<font style="color: white;" id="t8">NONE</font>
	</div>
	<div id="WOS_CompleteBtn" class="col-md-6" style="border:solid; font-size: 50px; text-align: center; background-color: rgb(246, 177, 148); border-radius: 5%; cursor: pointer">
		<font style="color: black;" id="t9">작&nbsp;업&nbsp;완&nbsp;료</font>
	</div>
</div>

<div class="row" style="margin: 5px; height: 180px; padding-bottom: 5px;" id="ko">
	<div class="col-md-12">
		<div id="WorkOrder_tbl" style="font-size: 30px;"></div>
	</div>
</div>

<div id="complete_message" class="complete none" style="position: absolute; z-index: 999; top: 30%; left: 40%; height: 10vh; width: 40vh; color: black; background-color:red;
			display: flex; align-items: center; justify-content: center; font-size: 3vh; border: solid 2px; border-radius: 15px;">
	<span>작업완료를 눌러주세요.</span>
</div>

<div style="float:none; visibility:hidden;">
			<span><strong style="font-size: 20px;">작업시작일</strong></span> 
			<input id="startDate" class="today" type="date" style="width: 180px; height: 35px; font-size: 20px;"> 
			<span style="text-align: center"><strong>~</strong></span>
			<input id="endDate" class="tomorrow" type="date" style="width: 180px; height: 35px; font-size: 20px;">
			<input id="n_len_code" class="tomorrow" type="text" style="width: 180px; height: 35px; font-size: 20px;">
</div>

<script src="/js/tablet/workOrderSeiyon.js?v=<%=System.currentTimeMillis() %>"></script>