<%@page import="com.busience.common.dto.DtlDto"%>
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
	<meta name="mobile-web-app-capable" content="yes">
	<link rel="manifest" href="/json/manifest.json">
	
	
	<style>
	html, body {
		margin: 0;
		height: 100%;
		overflow: hidden;
	}

	.modal-dialog {
	    overflow-y: initial !important
	}
	
	.modal-body {
	    height: 250px;
	    overflow-y: auto;
	}
	</style>
</head>
<body>
	<div id="testModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	    
	      <div class="modal-header">
			<div class="form-group row">
				<div class="col-sm-6" style="padding-top: 5px;">
					<input class="form-control form-control-lg" type="text" style="height: 50px; font-size: 35px;" id="Item_Word">
				</div>
				<div class="col-sm-6" style="padding-top: 5px;">
					<button type="button" class="btn btn-primary btn-lg" style="width: 100%;" onclick="search()">검색</button>
				</div>
			</div>
			
	      </div>
	      
	      <div class="modal-body" style="height: 400px;">
			<div  id="itemPopupTableModal" style="font-size: 30px;"></div>
	      </div>
	    </div>
	  </div>
	</div>

	<div class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" id="myFullsizeModal"> 
	    <div class="modal-dialog" style=" max-height:100%; width: 95%; height: 100%;" > 
	        <div class="modal-content" style="height: 95%;"> 
	            <div class="modal-header" > 
		                <div class="modal-title">
			                <div class="form-group row">
							<div class="col-sm-1"></div>
								<div class="col-sm-5" style="padding-top: 5px;">
									<input class="form-control form-control-lg" type="text" style="height: 50px; font-size: 35px;" id="Item_Word">
								</div>
								
								<div class="col-sm-5" style="padding-top: 5px;">
									<button type="button" class="btn btn-primary btn-lg btn-block" id="popbtn">검색</button>
								</div>
							</div>
				    	</div>
	                </h3> 
	            </div> 
	            <div class="modal-body" style="height: 80%;">
	            	<div style="width: 30%; float:left;">
	            	<ul class="list-group">
			       		<li class="list-group-item" style="font-size: 35px; background-color:rgb(112,173,70);color: white;" id="bun1">품목분류</li>
			       	</ul>
	            	<%
			       		List<DTL_TBL> list1 = (List<DTL_TBL>)request.getAttribute("list1");
			       		
			       		for(int i=0;i<list1.size();i++)
			       		{
			       			if(i==0)
			       			{
			       	%>
			       			<ul class="list-group">
			       				<li class="list-group-item" style="font-size: 25px;" onclick="list_click(this)" id="listone"><%=list1.get(i).getCHILD_TBL_TYPE()%></li>
			       			</ul>
			       	<%			
			       			}
			       			else
			       			{
			       	%>
			       			<ul class="list-group">
			       				<li class="list-group-item" style="font-size: 25px;" onclick="list_click(this)"><%=list1.get(i).getCHILD_TBL_TYPE()%></li>
			       			</ul>
			       	<%
			       			}
			       		}
			       	%>
			       	</div>
			       	
			       	<div style="width: 70%; float:left;">
			       		<div id="itemManageTable" class="ModalTable"></div>
			       	</div>
	          	</div> 
	        </div> 
	    </div> 
	</div>
	
	<div class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" id="myFullsizeModal2"> 
	    <div class="modal-dialog" style=" max-height:100%; width: 95%; height: 100%;" > 
	        <div class="modal-content" style="height: 95%;"> 
	            <div class="modal-header" > 
		                <div class="modal-title">
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
	                </h3> 
	            </div> 
	            <div class="modal-body" style="height: 80%;">
	            	<div style="width: 30%; float:left;">
	            	<ul class="list-group">
			       		<li class="list-group-item" style="font-size: 35px; background-color:rgb(112,173,70);color: white;" id="bun1">품목분류</li>
			       	</ul>
	            	<%
			       		List<DTL_TBL> list2 = (List<DTL_TBL>)request.getAttribute("list2");
			       		
			       		for(int i=0;i<list2.size();i++)
			       		{
			       			if(i==0)
			       			{
			       	%>
			       			<ul class="list-group">
			       				<li class="list-group-item" style="font-size: 25px;" onclick="list_click(this)" id="listtwo"><%=list2.get(i).getCHILD_TBL_TYPE()%></li>
			       			</ul>
			       	<%			
			       			}
			       			else
			       			{
			       	%>
			       			<ul class="list-group">
			       				<li class="list-group-item" style="font-size: 25px;" onclick="list_click(this)" id="listtwo"><%=list2.get(i).getCHILD_TBL_TYPE()%></li>
			       			</ul>
			       	<%
			       			}
			       		}
			       	%>
			       	</div>
			       	
			       	<div style="width: 70%; float:left;">
			       		<div id="itemManageTable2" class="ModalTable"></div>
			       	</div>
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
			<div style="font-size: 60px; color: white; text-align: center; width: 70%; float: left;" id="t1">자재출고관리</div>

			<div style="font-size: 56px; color: black; text-align: center; width: 15%; float: left; background-color:red; border-radius: 10%; border: solid;" id="${b1}">원자재</div>
			<div style="font-size: 56px; color: black; text-align: center; width: 15%; float: left; background-color:white; border-radius: 10%; border: solid;" id="${b2}">부자재</div>
		</div>
	</div>
	
	<div class="row" style="margin: 5px;">
		<div class="col-md-6" style="padding:10px; border:solid; font-size: 50px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t3">
						품&nbsp;명
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly type="text" id="pname" style="width: 100%; height: 70%; text-align: right; padding-right: 5px;">
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t4">
						규&nbsp;격
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly type="text" id="gu" style="width: 100%; height: 70%; text-align: right; padding-right: 5px;">
					</td>
				</tr>
			</table>
		</div>
		
		<div class="col-md-6" style="padding:10px; border:solid; font-size: 50px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t4">
						출&nbsp;고&nbsp;처
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<%
							String list3_flag = (String)request.getAttribute("list3_flag");
							List<DtlDto> list3 = (List<DtlDto>)request.getAttribute("list3");
							
							if(list3_flag.equals("on"))
							{
						%>
							<input readonly type="text" id="chr" onclick="chrclick()" style="width: 100%; height: 70%; text-align: right; padding-right: 5px;">
						<%
							}
							else
							{
						%>
							<input readonly type="text" id="chr" style="width: 100%; height: 70%; text-align: right; padding-right: 5px;">
						<%
							}
						%>
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t5">
						수&nbsp;량
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input type="number" id="d_len" value="1" min="1" style="width: 100%; height: 70%; text-align: right; padding-right: 5px;">
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
								
								<td style="text-align: center; background: rgb(207,213,234); color: black;" id="gu2">
									
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

	<script src="/js/tablet/matOutputLXTablet.js?v=<%=System.currentTimeMillis() %>"></script>
	
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
		#itemManageTable.tabulator { font-size: 30px; }
		
		#itemManageTable2.tabulator { font-size: 30px; }
		
		#itemManageTable3.tabulator { font-size: 40px; }
	</style>
</body>
</html>