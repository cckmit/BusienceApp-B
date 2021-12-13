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
	<div class="modal fade" id="myFullsizeModal" tabindex="-1" role="dialog" aria-labelledby="myFullsizeModalLabel">
	  <div class="modal-dialog modal-fullsize" role="document">
	    <div class="modal-content modal-fullsize"  style="height:100%;" >
	    
	    	<div style="height:10%; padding: 10px; border-bottom: solid;">
		       	<div class="form-group row">
				<label for="staticEmail" class="col-sm-2 col-form-label"><strong style="font-size: 40px;">검색</strong></label>
				<div class="col-sm-5" style="padding-top: 5px;">
					<input class="form-control form-control-lg" type="text" style="height: 50px; font-size: 35px;" id="Item_Word">
				</div>
				</div>
	    	</div>
	       
	       <div style="float: left; width: 28%; height:90%; border-color: 'black';overflow-y:scroll; padding: 10px;">
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
	       				<li class="list-group-item" style="font-size: 35px;" onclick="list_click(this)" id="listone"><%=list1.get(i).getCHILD_TBL_TYPE()%></li>
	       			</ul>
	       	<%			
	       			}
	       			else
	       			{
	       	%>
	       			<ul class="list-group">
	       				<li class="list-group-item" style="font-size: 35px;" onclick="list_click(this)"><%=list1.get(i).getCHILD_TBL_TYPE()%></li>
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
	       		<div id="itemManageTable" class="ModalTable"></div>
	       </div>
	    </div>
	  </div>
	</div> 
	
	<div class="modal fade" id="myFullsizeModal2" tabindex="-1" role="dialog" aria-labelledby="myFullsizeModalLabel">
	  <div class="modal-dialog modal-fullsize" role="document">
	    <div class="modal-content modal-fullsize" style="height:100%;" >
	       
	       <div style="float: left; width: 28%; height:100%; border-color: 'black';overflow-y:scroll; padding: 10px;">
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
	       
	       <div style="float: left; width: 0.8%; height:100%; border-color: 'black'; border-right-style:solid;">
	       </div>
	       
	       <div style="float: left; width: 1.2%; height:100%; border-color: 'black'; border-right-style:solid; border-right-width:0.5px;">
	       </div>
	       
	       <div style="float: left; width: 70%; height:100%; border-color: 'black'">
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

	<div style="width: 100%;height: 100%;position: absolute; border: solid; overflow:hidden;">
		<table style="width: 100%;">
			<tr>
				<td style="text-align: center;" colspan="3">
					<table style="width: 100%; margin: 10px;">
						<tr>
							<td style="width: 50%;">
								<center>
								<div style="width: 90%; background-color:rgb(112,173,70); text-align: center; border-radius: 5%;"><strong style="font-size: 40px; color: white;">자재출고관리</strong></div> 
								</center>
							</td>
							<td style="width: 15%; ">
								<center>
								<div style="width: 100%; border-style: solid; border-color:black; background-color:red; text-align: center; border-radius: 5%;" id="${b1}"><strong style="font-size: 40px; color: black;">원자재</strong></div> 
								</center>
							</td>
							<td style="width: 5%;">
								<center>
								<div style="width: 100%; background-color:rgb(112,173,70); text-align: center; border-radius: 5%;"><strong style="font-size: 40px; color: white;"></strong></div> 
								</center>
							</td>
							<td style="width: 15%;">
								<center>
								<div style="width: 100%; border-style: solid; border-color:black; background-color:white; text-align: center; border-radius: 5%;" id="${b2}"><strong style="font-size: 40px; color: black;">부자재</strong></div> 
								</center>
							</td>
							<td style="width: 3%;">
								<center>
								<div style="width: 100%; background-color:rgb(112,173,70); text-align: center; border-radius: 5%;"><strong style="font-size: 40px; color: white;"></strong></div> 
								</center>
							</td>
						</tr>
					</table>

				</td>
			</tr>

			<tr>
				<td style="width: 50%;">
					<center>
					<table style="width: 90%; background-color: rgb(82,153,217); border-radius: 5%; margin: 10px;"> 
						<tr>
							<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
								품&nbsp;명
							</td>
							<td rowspan="2" style="padding: 10px;">
								<input readonly value="" class="form-control form-control-lg" type="text" id="pname" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px; text-align:center;">
							</td>
						</tr>
						<tr>
							<td style="font-size: 40px; color: white; text-align: center;">
								
							</td>
						</tr>
						
						<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									규&nbsp;격
								</td>
								<td rowspan="2" style="padding: 10px;">
									<input readonly value="" class="form-control form-control-lg" type="text" id="sum_qty" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px; text-align:center;">
								</td>
							</tr>
							<tr>
								<td style="font-size: 40px; color: white; text-align: center;">
									
								</td>
							</tr>
					</table>
					</center>
				</td>
				<td style="width: 50%;">
					<center>
						<table style="width: 90%; height: 100%; background-color: rgb(82,153,217); border-radius: 5%; padding: 100px;"> 
							
							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									출&nbsp;고&nbsp;처
								</td>
								<td rowspan="2" style="padding: 10px;">
									<%
										String list3_flag = (String)request.getAttribute("list3_flag");
										List<DtlDto> list3 = (List<DtlDto>)request.getAttribute("list3");
									
										if(list3_flag.equals("on"))
										{
									%>
										<input id="chr" onclick="chrclick()" class="form-control form-control-lg" type="text" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px; text-align:center;">	
									<%		
										}
										else
										{
									%>
										<input id="chr" class="form-control form-control-lg" type="text" value="<%=list3.get(0).getCHILD_TBL_TYPE()%>" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px; text-align:center;">
									<%		
										}
									%>
								</td>
							</tr>
							<tr>
								<td style="font-size: 40px; color: white; text-align: center;">
									
								</td>
							</tr>
							
							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									수&nbsp;량
								</td>
								<td rowspan="2" style="padding: 10px;">
									<input class="form-control form-control-lg" type="number" id="d_len" value="1" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px; text-align:center;" min="1">
								</td>
							</tr>
							<tr>
								<td style="font-size: 40px; color: white; text-align: center;">
									
								</td>
							</tr>
							
						</table>
					</center>
				</td>
			</tr>

			<tr>
				<td style="width: 50%;">
					<center>
						<table style="width: 90%; background-color: rgb(246, 177, 148); border-radius: 5%; margin: 10px;"> 
							<tr>
								<td style="font-size: 45px; text-align: center; width: 40%;">
									선&nbsp;택&nbsp;사&nbsp;항
								</td>
							</tr>
						</table>
					</center>
				</td>

				<td style="width: 50%;">
					<center>
						<table style="width: 90%; background-color: rgb(82,153,217); border-radius: 5%; margin: 10px;"> 
							<tr>
								<td style="font-size: 45px; text-align: center; width: 10%; background-color: #F3F5F8;">
									
								</td>

								<td style="font-size: 45px; text-align: center; width: 40%; color: white; border-radius: 5%;" id="okbtn">
									확&nbsp;인
								</td>

								<td style="font-size: 45px; text-align: center; width: 10%; background-color: #F3F5F8;">
									
								</td>

								<td style="font-size: 45px; text-align: center; width: 40%; color: white; border-radius: 5%;" id="cancelbtn">
									취&nbsp;소
								</td>
							</tr>
						</table>
					</center>
				</td>
			</tr>
			
			<tr>
				<td colspan="2" style="width: 100%; font-size: 45px; padding-left: 45px; padding-right: 45px;">
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
								<td style="text-align: center; background: rgb(207,213,234);" id="pdselect2">
									
								</td>
								
								<td style="text-align: center; background: rgb(207,213,234);" id="gu">
									
								</td>
								
								<td style="text-align: center; background: rgb(207,213,234);" id="chr2">
									<%
										if(list3_flag.equals("on"))
										{
									%>
										<%=list3.get(0).getCHILD_TBL_TYPE()%>
									<%		
										}
									%>
								</td>
								
								<td style="text-align: center; background: rgb(207,213,234);" id="d_len2">
									1
								</td>
								
								<td style="text-align: center; background: rgb(207,213,234);" id="today">
									
								</td>
								
								<td style="text-align: center; background: rgb(207,213,234);" id="current_qty">
									0
								</td>
								
								
								
								<%
										if(!list3_flag.equals("on"))
										{
								%>
										<td style="text-align: center; background: rgb(207,213,234);" id="chr2" code="<%=list3.get(0).getCHILD_TBL_NO()%>">
											<%=list3.get(0).getCHILD_TBL_TYPE()%>
										</td>
								<%		
										}
										else
										{
								%>
										<td style="text-align: center; background: rgb(207,213,234);" id="chr2" code="">
											
										</td>
								<%
										}
								%>
							</tr>
						</thead>
					</table>
				</td>
			</tr>
			
			<tr>
				<td style="width: 50%;">
					<center>
					<table style="width: 90%; background-color: rgb(82,153,217); border-radius: 5%; margin: 10px;"> 
						<tr>
							<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
								출&nbsp;고&nbsp;현&nbsp;황&nbsp;(오늘)
							</td>
						</tr>
					</table>
					</center>
				</td>
				
				<td style="width: 50%; visibility:${visibility};">
					<center>
					<table style="width: 90%; background-color: rgb(246, 177, 148); border-radius: 5%; margin: 10px;" onclick="move()"> 
						<tr>
							<td style="font-size: 45px; text-align: center; width: 40%;">
								
							</td>
						</tr>
					</table>
					</center>
				</td>
			</tr>
			
		</table>
		
		<div style="padding: 0px 50px 520px 50px; height: 100%;">
			<div id="WorkOrder_tbl" style="font-size: 40px;"></div>
		</div>
		

		<div style="float:none; visibility:hidden;">
			<span><strong style="font-size: 20px;">작업시작일</strong></span> 
			<input id="startDate" class="today" type="date" style="width: 180px; height: 35px; font-size: 20px;"> 
			<span style="text-align: center"><strong>~</strong></span>
			<input id="endDate" class="tomorrow" type="date" style="width: 180px; height: 35px; font-size: 20px;">
			<input id="n_len_code" class="tomorrow" type="text" style="width: 180px; height: 35px; font-size: 20px;">
		</div>
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