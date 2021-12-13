<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<meta name="mobile-web-app-capable" content="yes">
	<link rel="manifest" href="/json/manifest.json">
	
	<style type="text/css">
	.modal-dialog.modal-fullsize {
	  width: 90%;
	  height: 80%;
	  margin-top: 85px;
	}
	.modal-content.modal-fullsize {
	  height: auto;
	  min-height: 100%;
	  border-radius: 0; 
	}
	
	.ModalTable .tabulator-col-content {
	    background-color:rgb(112,173,70);
	}
	
	.ModalTable .tabulator-col-title{
		color:white;
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

	<div style="width: 100%;height: 100%;position: absolute; border: solid; overflow:hidden; padding: 0px 10px 0px 10px;">
		<table style="width: 100%;">
			<tr>
				<td style="text-align: center;" colspan="2">
					<center>
					<div style="width: 60%; background-color:rgb(112,173,70); margin: 10px; text-align: center; border-radius: 5%;"><strong style="font-size: 40px; color: white;">작업 관리 <br/>Work Management</strong></div> 
					</center>
				</td>
			</tr>
			
			<tr>
				<td style="width: 50%;">
					<table style="width: 100%; background-color: rgb(82,153,217); border-radius: 5%;">
							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									입&nbsp;력
								</td>
								<td style="padding: 10px;">
									<input class="form-control form-control-lg" type="text" id="input" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px; text-align:right;">
								</td>
							</tr>
							
							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									박&nbsp;스&nbsp;LotNo
								</td>
								<td style="padding: 10px;">
									<input readonly="readonly" class="form-control form-control-lg" type="text" id="" style="font-size: 40px; height: 70px; width: 90%; background-color: black; color: white; border:groove; border-color: black;border-width: 1px; text-align:right;">
								</td>
							</tr>
							
							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									생&nbsp;산&nbsp;LotNo
								</td>
								<td style="padding: 10px;">
									<input readonly="readonly" id="workOrder_ONo" class="form-control form-control-lg" type="text" id="" style="font-size: 40px; height: 70px; width: 90%; background-color: black; color: white; border:groove; border-color: black;border-width: 1px; text-align:right;">
								</td>
							</tr>
							
							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									누&nbsp;적&nbsp;생&nbsp;산&nbsp;량
								</td>
								<td style="padding: 10px;">
									<input readonly="readonly" id="sum_qty" class="form-control form-control-lg" value="0" type="text" id="" style="font-size: 40px; height: 70px; width: 90%; background-color: black; color: white; border:groove; border-color: black;border-width: 1px; text-align:right;">
								</td>
							</tr>

							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									품&nbsp;명
								</td>
								<td style="padding: 10px;">
									<input class="form-control form-control-lg" type="text" id="n_len" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px; text-align:right;">
								</td>
							</tr>
					</table>
				</td>
				
				<td style="width: 50%;">
					<table style="width: 100%; background-color: rgb(82,153,217); border-radius: 5%; margin: 10px;">
							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									원&nbsp;자&nbsp;재&nbsp;LotNo
								</td>
								<td style="padding: 10px;">
									<input readonly="readonly" class="form-control form-control-lg" code="one" type="text" id="" style="font-size: 40px; height: 70px; width: 90%; background-color: black; color: white; border:groove; border-color: black;border-width: 1px; text-align:right;">
								</td>
							</tr>
							
							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									원&nbsp;자&nbsp;재&nbsp;LotNo
								</td>
								<td style="padding: 10px;">
									<input readonly="readonly" class="form-control form-control-lg" code="one" type="text" id="" style="font-size: 40px; height: 70px; width: 90%; background-color: black; color: white; border:groove; border-color: black;border-width: 1px; text-align:right;">
								</td>
							</tr>
							
							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									원&nbsp;자&nbsp;재&nbsp;LotNo
								</td>
								<td style="padding: 10px;">
									<input readonly="readonly" class="form-control form-control-lg" code="one" type="text" id="" style="font-size: 40px; height: 70px; width: 90%; background-color: black; color: white; border:groove; border-color: black;border-width: 1px; text-align:right;">
								</td>
							</tr>
							
							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									원&nbsp;자&nbsp;재&nbsp;LotNo
								</td>
								<td style="padding: 10px;">
									<input readonly="readonly" class="form-control form-control-lg" code="one" type="text" id="" style="font-size: 40px; height: 70px; width: 90%; background-color: black; color: white; border:groove; border-color: black;border-width: 1px; text-align:right;">
								</td>
							</tr>

							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									규&nbsp;격
								</td>
								<td style="padding: 10px;">
									<input class="form-control form-control-lg" type="text" id="o_len" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px; text-align:right;">
								</td>
							</tr>
					</table>
				</td>
			</tr>
		</table>
		
		<div style="height: 70%;">
			<div id="WorkOrder_tbl" style="font-size: 40px;"></div>
		</div>
	</div>
	
	<div style="float:none; visibility:hidden;">
			<span><strong style="font-size: 20px;">작업시작일</strong></span> 
			<input id="startDate" class="today" type="date" style="width: 180px; height: 35px; font-size: 20px;"> 
			<span style="text-align: center"><strong>~</strong></span>
			<input id="endDate" class="tomorrow" type="date" style="width: 180px; height: 35px; font-size: 20px;">
			<input id="n_len_code" class="tomorrow" type="text" style="width: 180px; height: 35px; font-size: 20px;">
	</div>
	
	<script src="/js/productionLX/MaterialInput.js"></script>
	
	<style>
		#myFullsizeModal.tabulator { font-size: 60px; }
	</style>
</body>
</html>