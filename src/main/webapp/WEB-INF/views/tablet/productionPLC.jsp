<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="/css/tablet/productionPLC.css?v=<%=System.currentTimeMillis() %>">

<div class="content">
	<div class="top">
		<div class="title board-green">
			<span>작업 관리</span>
			<div style="background-color:white; color: black; text-align: center; width: 20%; float: left; border: solid;" id="tp">작업중</div>

			<div style="color: black; text-align: center; width: 15%; float: left; background-color:red; border-radius: 10%; border: solid;" id="kor" onclick="lang_convert(this)">한글</div>
			<div style="color: black; text-align: center; width: 15%; float: left; background-color:white; border-radius: 10%; border: solid;" id="eng" onclick="lang_convert(this)">ENG</div>
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