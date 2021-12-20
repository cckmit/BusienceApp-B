<%@page import="com.busience.standard.dto.DTL_TBL"%>
<%@page import="com.busience.common.dto.DtlDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
html, body {
	margin: 0;
	height: 100%;
	overflow: hidden;
}
</style>

</head>
<body>
	 
	
	<div class="modal fade" id="myFullsizeModal2" tabindex="-1" role="dialog" aria-labelledby="myFullsizeModalLabel">
	  <div class="modal-dialog modal-fullsize" role="document">
	    <div class="modal-content modal-fullsize" style="height:100%;" >
	       
			<div style="height:10%; padding: 5px; border-bottom: solid;">
				<div class="form-group row">
			 <div class="col-sm-1"></div>
				 <div class="col-sm-5" style="padding-top: 5px;">
					 <input class="form-control form-control-lg" type="text" style="height: 50px; font-size: 35px;" id="Item_Word2">
				 </div>
				 
				 <div class="col-sm-5" style="padding-top: 5px;">
					 <button type="button" class="btn btn-primary btn-lg btn-block" id="popbtn2">검색</button>
				 </div>
			 </div>
		 	</div>

	       <div style="float: left; width: 28%; height:90%; border-color: 'black';overflow-y:scroll; padding: 10px;">
	       	<ul class="list-group">
	       		<li class="list-group-item" style="font-size: 35px; background-color:rgb(112,173,70);color: white;" id="bun2">품목분류</li>
	       	</ul>
	       	
	       	<%
	       		List<DTL_TBL> list2 = (List<DTL_TBL>)request.getAttribute("list2");
	       		
	       		for(int i=0;i<list2.size();i++)
	       		{
	       			if(i==0)
	       			{
	       	%>
	       			<ul class="list-group">
	       				<li class="list-group-item" style="font-size: 35px;" onclick="list_click(this)" id="listtwo"><%=list2.get(i).getCHILD_TBL_TYPE()%></li>
	       			</ul>
	       	<%			
	       			}
	       			else
	       			{
	       	%>
	       			<ul class="list-group">
	       				<li class="list-group-item" style="font-size: 35px;" onclick="list_click(this)"><%=list2.get(i).getCHILD_TBL_TYPE()%></li>
	       			</ul>
	       	<%
	       			}
	       		}
	       	%>
	       </div>
	       
	       <div style="float: left; width: 0.8%; height:90%; border-color: 'black'; border-right-style:solid;">
	       </div>
	       
	       <div style="float: left; width: 1.2%; height:90%; border-color: 'black'; border-right-style:solid; border-right-width:0.5px;">
	       </div>
	       
	       <div style="float: left; width: 70%; height:90%; border-color: 'black'">
	       		<div id="itemManageTable2" class="ModalTable"></div>
	       </div>
	    </div>
	  </div>
	</div> 
	
	<div class="modal fade" id="chModal" tabindex="-1" role="dialog" aria-labelledby="myFullsizeModalLabel">
	  <div class="modal-dialog modal-fullsize" role="document" style="width:30%;">
	    <div class="modal-content modal-fullsize" style="height:100%;" >
	       
	       <div style="float: left; width: 100%; height:100%; border-color: 'black';overflow-y:scroll; padding: 10px;">
	       	<div id="itemManageTable3" class="ModalTable"></div>
	       </div>
	    </div>
	  </div>
	</div>

	<div class="row" style="margin: 5px;">
		<div class="col-md-12" style="border:solid; background-color:rgb(112,173,70); text-align: center; border-radius: 5%;">
			<div style="font-size: 60px; color: white; text-align: center; width: 60%; float: left;" id="t1">자 재 출 고 관 리</div>
			
			<div style="font-size: 60px; color: black; text-align: center; width: 20%; float: left; background-color:red; border-radius: 10%; border: solid;" id="${b1}">원자재</div>
			<div style="font-size: 60px; color: black; text-align: center; width: 20%; float: left; background-color:white; border-radius: 10%; border: solid;" id="${b2}">부자재</div>
		</div>
	</div>
	
	<div class="row" style="margin: 5px;">
		<div class="col-md-6" style="padding:10px; border:solid; font-size: 50px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t4">
						품&nbsp;<font style="color:rgb(82,153,217);">설</font>&nbsp;명
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly type="text" id="pname" style="width: 100%; height: 70%; text-align: right; padding-right: 5px;">
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t5">
						규&nbsp;<font style="color:rgb(82,153,217);">설</font>&nbsp;격
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly type="text" id="sum_qty" style="width: 100%; height: 70%; text-align: right; padding-right: 5px;">
					</td>
				</tr>
			</table>
		</div>
		
		<div class="col-md-6" style="padding:10px; border:solid; font-size: 50px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t4">
						출&nbsp;<font>고</font>&nbsp;처
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<%
							String list3_flag = (String)request.getAttribute("list3_flag");
							List<DtlDto> list3 = (List<DtlDto>)request.getAttribute("list3");
							
							if(list3_flag.equals("on"))
							{
						%>
							<input readonly type="text" id="chr" onclick="chrclick()" style="width: 100%; height: 70%; text-align: center;">
						<%
							}
							else
							{
						%>
							<input readonly type="text" id="chr" value="<%=list3.get(0).getCHILD_TBL_TYPE()%>" style="width: 100%; height: 70%; text-align: center;">
						<%
							}
						%>
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t5">
						수&nbsp;<font style="color:rgb(82,153,217);">설</font>&nbsp;량
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly type="text" id="d_len" style="width: 100%; height: 70%; text-align: right; padding-right: 5px;">
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
								
								<td style="text-align: center; background: rgb(207,213,234); color: black;">
									20Kg
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
	
	<script src="/js/materialLX/matOutputLXTablet.js"></script>
	
	<script type="text/javascript">
	flag = "one";
	document.getElementById("<%=request.getAttribute("b1")%>").onclick = function(){
		flag = "one";
		document.getElementById("<%=request.getAttribute("b1")%>").style.background = "red";
		document.getElementById("<%=request.getAttribute("b2")%>").style.background = "white";
	}

	document.getElementById("<%=request.getAttribute("b2")%>").onclick = function(){
		flag = "bu";
		document.getElementById("<%=request.getAttribute("b1")%>").style.background = "white";
		document.getElementById("<%=request.getAttribute("b2")%>").style.background = "red";
	}
	</script>

	<style>
		#itemManageTable.tabulator { font-size: 60px; }
		
		#itemManageTable2.tabulator { font-size: 60px; }
		
		#itemManageTable3.tabulator { font-size: 40px; }
	</style>
</body>
</html>