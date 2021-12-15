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
	
</head>
<body>
	
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

	<div id="testModal2" class="modal fade bs-example-modal-lg" style="margin-top:10%;" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg" style="height: 80%;">
	    <div class="modal-content" style="height: 80%;">
	    
	      <div class="modal-body" style="height: 80%;">
			<div id="keyboardzone"></div>
	      </div>
	    </div>
	  </div>
	</div>

	<div style="margin:10; width: 100%;height: 100%;position: absolute; border: solid; overflow:hidden;">
		<table style="width: 100%;">
			<tr>
				<td style="text-align: center;" colspan="3">
					<center>
					<div style="width: 60%; background-color:rgb(112,173,70); margin: 10px; text-align: center; border-radius: 5%;"><strong style="font-size: 40px; color: white;">작업 관리 <br/>Work Management</strong></div> 
					</center>
				</td>
			</tr>

			<tr>
				<td style="width: 50%;">
					<center>
					<table style="width: 90%; background-color: rgb(82,153,217); border-radius: 5%; margin: 10px;"> 
						<tr>
							<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
								설&nbsp;비
							</td>
							<td rowspan="2">
								<select class="form-select form-select-lg" id="eqselect" name="eqselectn" style="font-size: 50px; background-color: rgb(164,194,230); width: 90%; border:groove; border-color: black;border-width: 1px;" aria-label=".form-select-lg example">
									<c:forEach var="item" items="${list}">
										<option value="${item.EQUIPMENT_INFO_CODE}">${item.EQUIPMENT_INFO_NAME}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td style="font-size: 40px; color: white; text-align: center;">
								Machinery
							</td>
						</tr>
						
						<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									누&nbsp;적&nbsp;수&nbsp;량
								</td>
								<td rowspan="2">
									<input readonly class="form-control form-control-lg" type="text" id="sum_qty" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px; text-align:right;">
								</td>
							</tr>
							<tr>
								<td style="font-size: 40px; color: white; text-align: center;">
									Cum Prd Qty
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
									생&nbsp;산&nbsp;수&nbsp;량
								</td>
								<td rowspan="2">
									<input readonly class="form-control form-control-lg" type="text" id="current_qty" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px; text-align:right;">
								</td>
							</tr>
							<tr>
								<td style="font-size: 40px; color: white; text-align: center;">
									Prd Qty
								</td>
							</tr>
							
							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									목&nbsp;표&nbsp;수&nbsp;량
								</td>
								<td rowspan="2">
									<input readonly class="form-control form-control-lg" type="text" id="d_len" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px; text-align:right;">
								</td>
							</tr>
							<tr>
								<td style="font-size: 40px; color: white; text-align: center;">
									Allotted Qty
								</td>
							</tr>
							
						</table>
					</center>
				</td>
			</tr>
			<tr>
				<td style="width: 50%;">
					<center>
						<table style="width: 90%; background-color: rgb(82,153,217); border-radius: 5%;"> 
							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									품&nbsp;명
								</td>
								<td rowspan="2">
									<input class="form-control form-control-lg" type="text" id="n_len" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px;">
								</td>
							</tr>
							<tr>
								<td style="font-size: 40px; color: white; text-align: center;">
									Prd Name
								</td>
							</tr>
						</table>
					</center>
				</td>
				
				<td style="width: 50%;">
					<center>
						<table style="width: 90%; background-color: rgb(82,153,217); border-radius: 5%;">
							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									규&nbsp;격
								</td>
								<td rowspan="2">
									<input class="form-control form-control-lg" type="text" id="o_len" style="font-size: 40px; height: 70px; width: 90%; background-color: rgb(90,155,213); color: black; border:groove; border-color: black;border-width: 1px;">
								</td>
							</tr>
							<tr>
								<td style="font-size: 40px; color: white; text-align: center;">
									Prd Spec.
								</td>
							</tr>
						</table>
					</center>
				</td>
			</tr>
			
			<tr>
				<td style="width: 50%;">
					<center>
					<div style="padding-left: 48px;">
						<table style="width: 60%; background-color: rgb(82,153,217); border-radius: 5%; float: left;"> 
							<tr>
								<td style="font-size: 45px; color: white; text-align: center; width: 40%;">
									작&nbsp;업&nbsp;현&nbsp;황
								</td>
							</tr>
						</table>
						
						<table style="width: 35%; background-color: rgb(246, 177, 148); border-radius: 5%; float: left;" onclick="productCom()"> 
							<tr>
								<td style="font-size: 45px; color: black; text-align: center; width: 40%;">
									작&nbsp;업&nbsp;완&nbsp;료
								</td>
							</tr>
						</table>
					</div>
					
					</center>
				</td>
				
				<td style="width: 50%; visibility:${visibility};">
					<center>
					<table style="width: 90%; background-color: rgb(246, 177, 148); border-radius: 5%; margin: 10px;" onclick="move()"> 
						<tr>
							<td style="font-size: 45px; text-align: center; width: 40%;">
								작업 지시 선택
							</td>
						</tr>
					</table>
					</center>
				</td>
			</tr>
			
		</table>
		
		<div style="padding: 0px 50px 520px 50px; height: 90%;">
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
	
	
	
	<script>
			function move(){
				location.href = "/tablet/workOrderInsertB?code=<%out.print(request.getParameter("code"));%>";
			}
	</script>
	<script src="/js/productionLX/workOrderStartBB.js"></script>

	<style>
		.tabulator { font-size: 16px; }
	</style>

	<script type="text/javascript">
	var keyboardzone = document.getElementById("keyboardzone");
	var input = document.getElementById("Item_Word");

	var keyboard = new customKeyboard(
		keyboardzone/*생성위치 태그*/,
		input/*input을 받을 태그*/, 
        function()/*클릭 했을때*/ {
            console.log("click : ", text);
        },
        function()/*esc 눌렀을때*/ {
            console.log("esc");
        },
        function(e)/*앤터 눌렀을때*/ {
            console.log("앤터 : ", text);
        }, 
        null/*키패드를 모양 값*/
        // {
        //     koNormal : [
        //         ['뒤로','1','2','3','4','5','6','7','8','9','0', 'backspace'],
        //         ['ㅂ', 'ㅈ', 'ㄷ', 'ㄱ', 'ㅅ', 'ㅛ', 'ㅕ', 'ㅑ', 'ㅐ', 'ㅔ'],
        //         ['ㅁ','ㄴ','ㅇ','ㄹ','ㅎ','ㅗ','ㅓ','ㅏ','ㅣ', 'enter'],
        //         ['shift','ㅋ','ㅌ','ㅊ','ㅍ','ㅠ','ㅜ','ㅡ','한/영'],
        //         ["space"]
        //     ], 
        //     koShift : [
        //         ['뒤로','!','@','#','$','%','^','&','*','(',')', 'backspace'],
        //         ['ㅃ', 'ㅉ', 'ㄸ', 'ㄲ', 'ㅆ', 'ㅛ', 'ㅕ', 'ㅑ', 'ㅒ', 'ㅖ'],
        //         ['ㅁ','ㄴ','ㅇ','ㄹ','ㅎ','ㅗ','ㅓ','ㅏ','ㅣ', 'enter'],
        //         ['shift','ㅋ','ㅌ','ㅊ','ㅍ','ㅠ','ㅜ','ㅡ','한/영'],
        //         ["space"]
        //     ],
        //     enNormal : [
        //         ['뒤로','1','2','3','4','5','6','7','8','9','0', 'backspace'],
        //         ['q','w','e','r','t','y','u','i','o','p'],
        //         ['a','s','d','f','g','h','j','k','l','enter'],
        //         ['shift','z','x','c','v','b','n','m','한/영'],
        //         ["space"]
        //     ],
        //     enShift : [
        //         ['뒤로','!','@','#','$','%','^','&','*','(',')', 'backspace'],
        //         ['Q','W','E','R','T','Y','U','I','O','P'],
        //         ['A','S','D','F','G','H','J','K','L','enter'],
        //         ['shift','Z','X','C','V','B','N','M','한/영'],
        //         ["space"]
        //     ]
        // }
    );

	input.addEventListener("click", function() {
        //input 태그를 자신으로 설정
        keyboard.setInput(this);
        //키패드 클릭 이벤트 설정
        keyboard.setClick(function(text) {
            console.log("input을 click한 후 : ", text);
        })
        //앤터 이벤트 설정
        keyboard.setEnter(function(e) {
            menu3searching(e)
        });
    })
</script>
	
</body>
</html>