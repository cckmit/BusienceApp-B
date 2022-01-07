<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
body{
	background: white;
}
.board-green{
	background: rgb(112, 173, 71);
	border: 1px solid rgb(80, 126, 50);
	color: white;
}
.board-orange{
	background: linear-gradient( to bottom, rgb(247, 188, 162), rgb(247, 169, 136));
	border: 1px solid rgb(237, 125, 49);
}
.board-grayblue{
	background: linear-gradient( to bottom, rgb(167, 182, 223), rgb(136, 159, 215));
	border: 1px solid rgb(68, 114, 196);
}
.board-blue{
	background: rgb(68,114,196);
	border: 1px solid rgb(47, 82, 143);
	color: white;
}
.board-gray{
	border: 1px solid white;
	background: rgb(207, 213, 234);
}
.board-sky{
	background: rgb(0, 176, 240);
	border: 1px solid rgb(237, 125, 49);
}
.mid1{
	height: 32vh;
}
.mid1>div{
	height: 85%;
	width: 48%;	
	float: left;
	border-radius: 4vh;
	margin: 1%;
	padding: 1%;
}
.m1-top{
	margin: auto;
	height: 60%;
	width: 90%;
	display: flex;
}
.m1-top .m1-input div{
	cursor: pointer;
}
.m1-bottom{
	margin: auto;
	height: 40%;
	width: 90%;
	display: flex;
}
.m1-label{
	width: 40%;
	margin: auto;
	text-align: center;
	font-size: 4.5vh;
}
.m1-input{
	width: 60%;
	padding: 2%;
	font-size: 3vh;
}
.m1-input>div{
	padding: 2.5%;
}
.m1-input div.selected{
	background: rgb(68,114,196);
	color: white;
	cursor: default;
}
.board-btn{
	height: 100%;
	width: 100%;
	border-radius: 0.6vh;
    font-size: 3vh;
}
.board-title{
	border-radius: 1vh;
	text-align: center;
}
.board-title span{
	font-size: 3vh;
    top: 15%;
    position: relative;
}
table{
	width: 100%;
	border-spacing: 2px;
	border-collapse: separate;
	table-layout: fixed;
	font-size: 3vh;
}
th{
	background: rgb(68,114,196);
	color: white;
	text-align: center;
}
td{
	background: rgb(207,213,234);
	text-align: center;
}

.plus-minus:hover{
	cursor: pointer;
	color: gray;
}
.plus-minus:active{
	color: white;
}
</style>

<div class="content" style="height: 100vh; width: 75vw; margin:auto;">
	<div class="top" style="height: 8vh; margin-bottom: 1vh">
		<div class="title board-green" style="height: 80%; width: 60%; margin:auto; top: 10%; position:relative; text-align: center; border-radius: 1vh;">
			<span style="top: 10%; position:relative; font-size: 3.5vh">자재 출고 관리</span>
		</div>
	</div>
	<div class="mid1">
		<div class="left board-grayblue">
			<div class="m1-top">
				<div class="m1-label">
					<span>품 명</span>
				</div>
				<div id="boardItem" class="m1-input">
					<div  class="board-gray selected" style="height: 50%;">
						<span>1. 유기농쌀</span>
					</div>
					<div class="board-gray" style="height: 50%;">
						<span>2. 무기농쌀</span>
					</div>	
				</div>
			</div>
			<div class="m1-bottom">
				<div class="m1-label">
					<span>규 격</span>
				</div>
			<div class="m1-input">
				<div id="boardStandard" class="board-blue" style="height: 100%; display: flex; justify-content: space-around; align-items: center;">
					<span>20kg</span>
				</div>
			</div>
			</div>
		</div>
		<div class="right board-grayblue">
			<div class="m1-top">
				<div class="m1-label">
					<span>출고처</span>
				</div>
				<div id="boardOutput" class="m1-input">
					<div  class="board-gray selected" style="height: 50%;">
						<span>1. 전처리실</span>
					</div>
					<div class="board-gray" style="height: 50%;">
						<span>2. 퍼핑실</span>
					</div>	
				</div>
			</div>
			<div class="m1-bottom">
				<div class="m1-label">
					<span>수 량</span>
				</div>
				<div class="m1-input">
					<div id="boardQuantity" class="board-blue" style="height: 100%; text-align: center; display: flex; justify-content: space-around; align-items: center;">
						<span class="plus-minus"><i class="fa fa-minus"></i></span>
						<span id="bq-value">0</span>
						<span class="plus-minus"><i class="fa fa-plus"></i></span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="mid2" style="height: 20vh">
		<div style="height: 30%; width: 100%; padding: 2px; display: flex; justify-content: space-between;">
			<div class="board-title board-orange" style="height: 100%; width: 25%;">
				<span>선택사항</span>
			</div>
			<div style="height: 100%; width: 15%;">
				<button id="MOB_SaveBtn" class="btn board-btn board-sky" style="padding: 3%;">저장</button>
			</div>
		</div>
		<div class="" style="height: 70%; width: 100%;">
			<table>
				<tr>
					<th>품 명</th>
					<th>규 격</th>
					<th>출고처</th>
					<th>출고 수량</th>
					<th>날 짜</th>
				</tr>
				<tr>
					<td id="boardItemCopy">유기농쌀</td>
					<td id="boardStandardCopy">20kg</td>
					<td id="boardOutputCopy">전처리실</td>
					<td id="boardQuantityCopy">0</td>
					<td id="boardNowtime"></td>
				</tr>
			</table>
		</div>
	</div>
	<div style="height: 39vh;">
		<div style="height: 15%; width: 100%; padding: 2px;">
			<div class="board-title board-orange" style="height: 100%; width: 25%;">
				<span>출고현황 (오늘)</span>
			</div>
		</div>
		<div id="matOutputLXBoardTable" style="width: 100%; font-size: 16px"></div>
	</div>
</div>

<script>
var matOutputLXBoardTable = new Tabulator("#matOutputLXBoardTable", {
	height:"85%",
	resizableColumns: false,
	columns: [
		{title:"순번", field:"id", headerHozAlign: "center",  hozAlign: "center"},
		{title:"출고일자", field:"outMat_Date", headerHozAlign:"center", hozAlign:"left", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
	 	{title:"출고구분", field:"outMat_Send_Clsfc_Name", headerHozAlign:"center", hozAlign:"left"},
	 	{title:"부서명", field:"outMat_Dept_Name", headerHozAlign:"center", hozAlign:"left"},
		{title:"수취인", field:"outMat_Consignee_Name", headerHozAlign:"center", hozAlign:"left"},
	 	{title:"품목코드", field:"outMat_Code", headerHozAlign:"center", hozAlign:"left"},
	 	{title:"품명", field:"outMat_Name", headerHozAlign:"center",hozAlign:"left"},
	 	{title:"규격", field:"outMat_STND_1", headerHozAlign:"center",hozAlign:"left"},
	 	{title:"단위", field:"outMat_UNIT", headerHozAlign:"center", hozAlign:"left"},
	 	{title:"수량", field:"outMat_Qty", headerHozAlign:"center", hozAlign:"right"},
	 	{title:"등록자", field:"outMat_Modifier", headerHozAlign:"center", hozAlign:"left"},
	 	{title:"등록일자", field:"outMat_dInsert_Time", headerHozAlign:"center", hozAlign:"left", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}}
	],
});

function MOB_Search(){
	var data = {
			startDate : today,
			endDate : tomorrow,
			outMat_Code : "",
			outMat_Send_Clsfc_Name : "all",
			outMat_Dept_Name : "all"
		}
	
	matOutputLXBoardTable.setData("/matOutputReportLXRest/MO_ListView",{data: JSON.stringify(data)});
}
$(document).ready(function(){
	MOB_Search();
})

$(".m1-top .m1-input div").click(function(){
	if(!$(this).hasClass("selected")){
		$(this).parent().children().removeClass("selected");
		$(this).addClass("selected");
		var textValue = $(this).children().text()
		
		$("#"+$(this).parent().attr('id')+"Copy").text(textValue.substr(2,textValue.length));
	}
})

$("#boardQuantity .plus-minus").click(function(){
	var bqValue = $(this).parent().children("#bq-value");
	
	if($(this).children().hasClass("fa-plus")){
		bqValue.text(Number(bqValue.text())+1);
	}else if($(this).children().hasClass("fa-minus")){
		bqValue.text(bqValue.text()-1);
	}
	$("#"+$(this).parent().attr('id')+"Copy").text(bqValue.text());
})

$("#boardNowtime").text(today.toISOString().substring(0, 10));

$("#MOB_SaveBtn").click(function(){
	var data = {
			
	}
})

</script>