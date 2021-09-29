<%@page import="com.busience.standard.Dto.DTL_TBL"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<!-- MAIN -->
		<div class="soloView">
			<div class="main">
				<div class="top-var">
					<!-- 버튼 -->
					<div class="input-button">
						<img src="images/button/Search.png" onclick="MI_searchBtn1()"/>
						<img src="images/button/Save.png" onclick="MI_saveBtn2()"/>
					</div>
					<!-- 버튼 -->
					<div class="input-box">
						<div>
							<input id="currentDate" type="hidden" disabled value="${SM_Prcs_Date}">
							<span><strong>접수일</strong></span> 
							<input id="startDate" class="today" type="date"> 
							<span style="text-align: center"><strong>~</strong></span> 
							<input id="endDate" class="tomorrow" type="date">
						</div>
						<div>
							<span><strong>제품코드</strong></span>
							<input id="PRODUCT_ITEM_CODE1" class="Item_Code1" type="text" disabled>
							<span><strong>제품명</strong></span>
							<input id="PRODUCT_ITEM_NAME1" class="Item_Name1 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','sales')}">			
						</div>
					</div>
				</div>
				<div id="WorkOrder_tbl"></div>
				
				<form name="iqcInputInspectFrm">
				<div style="height: 180px">
					<div style="float: left; width: 60%;">
						<table style="width: 100%; background-color: #f2f2f2;">
							<tr>
								<td style="width: 50%;">
									<table>
										<tr style="visibility: collapse;">
											<td width="100" style="text-align: right;">
												<strong></strong>
											</td>
											<td width="140"><input id="OQCInspect_Lot_No" type="text" style="width: 100%;" disabled="disabled;"></td>
										</tr>
										
										<tr>
											<td width="100" style="text-align: right;">
												<strong>출하검사번호</strong>
											</td>
											<td width="100"><input id="OQCInspect_OqcNo" type="text" style="width: 100%;" disabled="disabled"></td>
											
											<td width="100" style="text-align: right;">
												<strong>불량수량</strong>
											</td>
											<td width="50"><input id="OQCInspect_DQty" type="number" style="width: 100%;" disabled="disabled"></td>
										</tr>
										
										<tr>
											<td width="100" style="text-align: right;">
												<strong>등록일자</strong>
											</td>
											<td width="100"><input id="OQCInspect_Date" type="text" style="width: 100%;" disabled="disabled"></td>
											
											<td width="100" style="text-align: right;">
												<strong>합격수량</strong>
											</td>
											<td width="50"><input id="OQCInspect_PQty" type="number" style="width: 100%;" disabled="disabled"></td>
											
										</tr>
										
										<tr>
											<td width="100" style="text-align: right;">
												<strong>검사자</strong>
											</td>
											<td width="100">
												<select id="OQCInspect_Worker" style="width: 100%;">
													<c:forEach var="data" items="${inList}">
														<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
													</c:forEach>
												</select>
											</td>
											
											<td width="100" style="text-align: right;">
												<strong>특채수량</strong>
											</td>
											<td width="50"><input id="OQCInspect_SQty" type="number" style="width: 100%;" min="0" onmousedown="SQty_Save()" onmouseup="spec_check()" onkeyup="spec_check()"></td>
											
										</tr>
										
										<tr>
											<td width="100" style="text-align: right;">
												<strong>처리구분</strong>
											</td>
											<td width="100">
												<select id="OQCInspect_Prcsn_Clsfc" style="width: 100%;">
													<c:forEach var="data" items="${rogicList}">
														<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
													</c:forEach>
												</select>
											</td>
											
											<td width="100" style="text-align: right;">
												<strong>샘플수량</strong>
											</td>
											<td width="50"><input id="OQCInspectType_SaQty" type="number" style="width: 100%;" disabled="disabled"></td>
										</tr>
									</table>
								</td>
								<td style="width: 50%;padding-top: 40px;margin-left: 0px;">
									<table>
										<tr>
											<td width="100" style="text-align: right;">
												<strong>제품코드</strong>
											</td>
											<td width="100"><input id="inMat_Code" type="text" style="width: 100%;" disabled="disabled"></td>
											
											<td width="80" style="text-align: right;">
												<strong>제품명</strong>
											</td>
											<td width="120"><input id="inMat_Name" type="text" style="width: 100%;" disabled="disabled"></td>
										</tr>
										
										<tr>
											<td width="100" style="text-align: right;">
												<strong>설비코드</strong>
											</td>
											<td width="100"><input id="inEquip_Code" type="text" style="width: 100%;" disabled="disabled"></td>
											
											<td width="80" style="text-align: right;">
												<strong>설비명</strong>
											</td>
											<td width="120"><input id="inEquip_Name" type="text" style="width: 100%;" disabled="disabled"></td>
										</tr>
										
										<tr style="visibility:hidden ;">
											<td width="100" style="text-align: right;">
												<strong>거래처</strong>
											</td>
											<td width="100"><input id="inMat_Client_Code" type="text" style="width: 100%;" disabled="disabled"></td>
											
											<td width="100" style="text-align: right;">
												<strong>거래처명</strong>
											</td>
											<td width="100"><input id="inMat_Client_Name" type="text" style="width: 100%;" disabled="disabled"></td>
										</tr>
										
										<tr>
											<td width="100" style="text-align: right;">
												<strong>문제점</strong>
											</td>
											
											<td width="50" colspan="3" rowspan="0">
												<textarea id="OQCInspect_Problem" rows="" cols="50" style="resize: none;"></textarea>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						
					</div>
					<div style="float: left; width: 40%;">
						<div id="OQCInspect_tbl2"></div>	
					</div>
				</div>
				</form>
				<div style="float:none;" id="OQCInspect_tbl"></div>	
			</div>
		</div>
		<!-- END MAIN -->
	<script src="/js/qc/oqcOutputInspect.js"></script>
	<script src="/js/tabMenu.js"></script>
	<script src="/js/today.js"></script>
	
	<script type="text/javascript">
			var options = {};
	</script>
	<c:forEach var="data" items="${checkList}">
		<script type="text/javascript">
			options[${data.CHILD_TBL_NO}] = '${data.CHILD_TBL_TYPE}';
		</script>
	</c:forEach>
	
	<%
		List<DTL_TBL> list = (List<DTL_TBL>)request.getAttribute("checkList");
		
		for(int i=0;i<list.size();i++)
			System.out.println(list.get(i).toString());
	%>
	
	<script type="text/javascript">
			var OQCInspect_tbl2 = new Tabulator("#OQCInspect_tbl2", {
				height:"175px",
				//복사하여 엑셀 붙여넣기 가능
				clipboard: true,
				rowDblClick:function(e, row){
			    	row.toggleSelect();
			    },
			    index:"index1",
			    
				columns:[
					{title:"인덱스", field:"index1",visible:false},
					{title:"검사유형", field:"OQCInspectType_Clsfc_Code_Code", visible: false},
					{title:"검사유형", field:"OQCInspectType_Clsfc_Code_Name",headerHozAlign:"center",align:"left",
						headerClick:function(e, column){
						    console.log(OQCInspect_tbl2.getData());
						}
					},
					{title:"입고수량", field:"OQCInspectType_IQty",headerHozAlign:"center",align:"right"},
					{title:"Sample 수량", field:"OQCInspectType_SaQty",headerHozAlign:"center",align:"right",editor:"input",
						cellEdited:function(cell){
							IQCInspectType_SaQty_Check(cell);
						}
					},
					{title:"판정기준", field:"OQCInspectType_CRT",headerHozAlign:"center",align:"right", editor:"select", 
						editorParams:{
						    values:[
						        <%
						        for(int i=0;i<list.size();i++)
						        	out.println("{label:'"+list.get(i).getCHILD_TBL_TYPE()+"',value:'"+list.get(i).getCHILD_TBL_TYPE()+"'},");
						        %>
							]
						}
					},
					{title:"불량수량", field:"OQCInspectType_DQty",headerHozAlign:"center",align:"right", editor:"input",
						cellEdited:function(cell){
							OQCInspectType_DQty_Check(cell);
					    }
					}
				]
			});
			
			function MI_saveBtn2(){
				if(!clickFlag)
				{
					alert("검사 하실 행을 선택한 후에 저장하여 주십시오.");
					return;
				}
				
				if(inupflag=="insert")
				{
					data = {
							//"OQCInspect_Lot_No" : $("#OQCInspect_Lot_No").val(),
							"OQCInspect_OqcNo" : $("#OQCInspect_OqcNo").val(),
							"inMat_Code" : $("#inMat_Code").val(),
							"OQCInspect_DQty" : $("#OQCInspect_DQty").val(),
							"OQCInspect_PQty" : $("#OQCInspect_PQty").val(),
							"OQCInspect_Worker" : $("#OQCInspect_Worker").val(),
							"OQCInspect_SQty" : $("#OQCInspect_SQty").val(),
							"OQCInspectType_SaQty" : $("#OQCInspectType_SaQty").val(),
							"OQCInspect_Prcsn_Clsfc" : $("#OQCInspect_Prcsn_Clsfc").val(),
							"OQCInspectType_SaQty" : $("#OQCInspectType_SaQty").val(),
							"OQCInspect_Problem" : $("#OQCInspect_Problem").val()
						}
						
						$.ajax({
									method : "GET",
									dataType : "json",
									url : "oqcOutputInspectRest/Save1?data="+ encodeURI(JSON.stringify(data)),
									complete : function() {
										$.ajax({
											method : "GET",
											dataType : "json",
											url : "oqcOutputInspectRest/Save2?data="+ encodeURI(JSON.stringify(OQCInspect_tbl2.getData())+"&LotNo="+$("#OQCInspect_Lot_No").val()+"&OQCInspect_OqcNo="+$("#OQCInspect_OqcNo").val()),
											complete : function() {
												alert("입력되었습니다.");
									        	//MI_searchBtn1();
									        	clickFlag = false;
												inputinit();  
												OQCInspect_tbl.replaceData(); 
												MI_searchBtn1();
												
												// 오른쪽 그리드에서 입고수량과 불량수량과 Sample수량을 바꿔주는 로직
												for(i=0;i<OQCInspect_tbl2.getDataCount("active");i++)
													OQCInspect_tbl2.updateRow(i,{
													OQCInspectType_IQty:0,
													OQCInspectType_DQty:0,
													OQCInspectType_SaQty:0,
													OQCInspectType_CRT : <%out.println("'"+list.get(0).getCHILD_TBL_TYPE()+"'");%>
												});
									    	}
										});
							  	}
						});
				}
				else
				{
					data = {
							//"OQCInspect_Lot_No" : $("#OQCInspect_Lot_No").val(),
							"OQCInspect_OqcNo" : $("#OQCInspect_OqcNo").val(),
							"inMat_Code" : $("#inMat_Code").val(),
							"OQCInspect_DQty" : $("#OQCInspect_DQty").val(),
							"OQCInspect_PQty" : $("#OQCInspect_PQty").val(),
							"OQCInspect_Worker" : $("#OQCInspect_Worker").val(),
							"OQCInspect_SQty" : $("#OQCInspect_SQty").val(),
							"OQCInspectType_SaQty" : $("#OQCInspectType_SaQty").val(),
							"OQCInspect_Prcsn_Clsfc" : $("#OQCInspect_Prcsn_Clsfc").val(),
							"OQCInspectType_SaQty" : $("#OQCInspectType_SaQty").val(),
							"OQCInspect_Problem" : $("#OQCInspect_Problem").val()
						}
					
					$.ajax({
							method : "GET",
							dataType : "json",
							url : "oqcOutputInspectRest/Update1?data="+ encodeURI(JSON.stringify(data)),
							complete : function() {
								$.ajax({
									method : "GET",
									dataType : "json",
									url : "oqcOutputInspectRest/Update2?data="+ encodeURI(JSON.stringify(OQCInspect_tbl2.getData())+"&LotNo="+$("#OQCInspect_Lot_No").val()+"&OQCInspect_OqcNo="+$("#OQCInspect_OqcNo").val()),
									complete : function() {
										alert("수정되었습니다.");
							        	MI_searchBtn1();
							        	clickFlag = false;
										inputinit();  
										OQCInspect_tbl.replaceData(); 
										
										// 오른쪽 그리드에서 입고수량과 불량수량과 Sample수량을 바꿔주는 로직
										for(i=0;i<OQCInspect_tbl2.getDataCount("active");i++)
											OQCInspect_tbl2.updateRow(i,{
											OQCInspectType_IQty:0,
											OQCInspectType_DQty:0,
											OQCInspectType_SaQty:0,
											OQCInspectType_CRT : <%out.println("'"+list.get(0).getCHILD_TBL_TYPE()+"'");%>
										});
							    	}
								});
					  		}
					});
				}
			}
			
			i = 0;
	</script>
	
	
	<c:forEach var="data" items="${costList}">
		<script type="text/javascript">
		OQCInspect_tbl2.addRow({
				"index1": i,
				"OQCInspectType_IQty":"0",
				"OQCInspectType_SaQty":"0",
				"OQCInspectType_DQty":"0",
				"OQCInspectType_Clsfc_Code_Code":'${data.CHILD_TBL_NO}',
				"OQCInspectType_Clsfc_Code_Name":'${data.CHILD_TBL_TYPE}',
				"OQCInspectType_CRT" : <%out.println("'"+list.get(0).getCHILD_TBL_TYPE()+"'");%>
			});
			i++;
		</script>
	</c:forEach>
	 
</body>
</html>