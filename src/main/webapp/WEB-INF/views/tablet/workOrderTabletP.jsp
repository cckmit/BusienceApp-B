<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<input type="hidden" id="eqcode" value="<%=request.getParameter("eqcode")%>"/>
	
	<div id="testModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	    
	      <div class="modal-header">
			<div class="form-group row">
				<div class="col-sm-1"></div>
				<div class="col-sm-5" style="padding-top: 5px;">
					<input class="form-control form-control-lg" type="text" style="height: 50px; font-size: 35px;" id="Item_Word" />
				</div>
				
				<div class="col-sm-5" style="padding-top: 5px;">
					<button type="button" class="btn btn-primary btn-lg btn-block" id="popbtn">검색</button>
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
		<div class="col-md-12" style="border:solid; background-color:rgb(112,173,70); text-align: center; border-radius: 5%;">
			<div style="font-size: 60px; color: black; text-align: center; width: 15%; float: left; background-color:red; border-radius: 10%; border: solid;" id="t1"><%=request.getParameter("eqcode")%></div>
		
			<div style="font-size: 60px; color: white; text-align: center; width: 70%; float: left;" id="t1">작업 관리</div>	
		</div>
	</div>
	
	<div class="row" style="margin: 5px;">
		<div class="col-md-6" style="padding:10px; border:solid; font-size: 50px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t4">
						입&nbsp;력
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input class="form-control form-control-lg" type="text" id="input" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px; text-align:right;">
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t5">
						박&nbsp;스&nbsp;Lot
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly="readonly" class="form-control form-control-lg" type="text" id="" style="font-size: 40px; height: 70px; width: 90%; background-color: black; color: white; border:groove; border-color: black;border-width: 1px; text-align:right;">
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t5">
						생&nbsp;산&nbsp;Lot
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly="readonly" id="workOrder_ONo" class="form-control form-control-lg" type="text" id="" style="font-size: 40px; height: 70px; width: 90%; background-color: black; color: white; border:groove; border-color: black;border-width: 1px; text-align:right;">
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t5">
						누적생산량
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly="readonly" id="sum_qty" class="form-control form-control-lg" value="0" type="text" id="" style="font-size: 40px; height: 70px; width: 90%; background-color: black; color: white; border:groove; border-color: black;border-width: 1px; text-align:right;">
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t5">
						품&nbsp;명
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input class="form-control form-control-lg" type="text" id="n_len" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px; text-align:right;">
					</td>
				</tr>
			</table>
		</div>
		
		<div class="col-md-6" style="padding:10px; border:solid; font-size: 50px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t4">
						원&nbsp;자&nbsp;재&nbsp;Lot
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly="readonly" class="form-control form-control-lg" code="one" type="text" id="" style="font-size: 40px; height: 70px; width: 90%; background-color: black; color: white; border:groove; border-color: black;border-width: 1px; text-align:right;">
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t5">
						원&nbsp;자&nbsp;재&nbsp;Lot
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly="readonly" class="form-control form-control-lg" code="one" type="text" id="" style="font-size: 40px; height: 70px; width: 90%; background-color: black; color: white; border:groove; border-color: black;border-width: 1px; text-align:right;">
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t5">
						원&nbsp;자&nbsp;재&nbsp;Lot
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly="readonly" class="form-control form-control-lg" code="one" type="text" id="" style="font-size: 40px; height: 70px; width: 90%; background-color: black; color: white; border:groove; border-color: black;border-width: 1px; text-align:right;">
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t5">
						원&nbsp;자&nbsp;재&nbsp;Lot
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly="readonly" class="form-control form-control-lg" code="one" type="text" id="" style="font-size: 40px; height: 70px; width: 90%; background-color: black; color: white; border:groove; border-color: black;border-width: 1px; text-align:right;">
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t5">
						규&nbsp;격
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input class="form-control form-control-lg" type="text" id="o_len" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px; text-align:right;">
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="row" style="margin: 5px;" >
		<div class="col-md-12" id="ko" style="padding:10px; font-size: 50px; text-align: center; border-radius: 5%; ">
			<div id="WorkOrder_tbl" style="font-size: 40px; border: solid;"></div>
		</div>
	</div>
	
	<div style="float:none; visibility:hidden;">
			<span><strong style="font-size: 20px;">작업시작일</strong></span> 
			<input id="startDate" class="today" type="date" style="width: 180px; height: 35px; font-size: 20px;"> 
			<span style="text-align: center"><strong>~</strong></span>
			<input id="endDate" class="tomorrow" type="date" style="width: 180px; height: 35px; font-size: 20px;">
			<input id="n_len_code" class="tomorrow" type="text" style="width: 180px; height: 35px; font-size: 20px;">
	</div>
	
	<script src="/js/tablet/workOrderTabletP.js"></script>
	
	<style>
		#myFullsizeModal.tabulator { font-size: 60px; }
	</style>
</body>
</html>