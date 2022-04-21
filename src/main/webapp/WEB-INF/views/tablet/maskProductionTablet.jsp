<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.container-bs{
	display: grid;
	grid-template-columns: 1fr 1fr;
	grid-template-rows: 10vh 50vh 10vh 30vh;
	grid-template-areas:
		"header header"
		"main-a main-b"
		"main-c main-c"
		"footer footer";
	height: 100vh;
}
.global-header{
	grid-area: header;
	display: flex;
	justify-content: space-around;
    align-items: center;
    padding: 5px;
}
.global-header #machineName{
	font-size: 3vh;
	color: rgb(68,114,196);
}
.global-header .title{
	border-radius: 20px;
	height: 100%;
	width: 50%;
	display: flex;
	justify-content: space-around;
    align-items: center;
    
    font-size: 4vh;
	color: white;
	border: solid #333;
	background-color: rgb(112,173,70);
}
.main-a{
	grid-area: main-a;
    padding: 5px;
}
.main-b{
	grid-area: main-b;
    padding: 5px;
}
.main-c{
	grid-area: main-c;
    padding: 5px;
}
.main-box{
	border-radius: 20px;
	border: solid #333;
	background-color: rgb(82,153,217);
    height: 100%;
    width: 100%;
    
    display: grid;
	grid-template-rows: repeat(4, 1fr);
}
.main-box .item{
	display: flex;
	justify-content: space-around;
    align-items: center;
	
	margin: 5px;
	font-size: 3.5vh;
}
.main-box .item label{
	width: 35%;
	text-align-last: justify;
}
.main-box .item input{
	width: 50%;
}

#itemTable{
	font-size: 2vh;
}
.global-footer{
	grid-area: footer;
    padding: 5px;
}
.footer-box{
	border-radius: 20px;
	background-color: rgb(207,213,234);
    height: 100%;
    width: 100%;
}
</style>
<div class="container-bs">
	<header class="global-header">
		<div>
			<input id="machineCode" type="hidden" value="${machineInfo.EQUIPMENT_INFO_CODE}">
			<span id="machineName">${machineInfo.EQUIPMENT_INFO_NAME}</span>
		</div>
		<div class="title">
			<span>작업관리 (마스크 생산)</span>
		</div>
		<div>
			<button>시작</button>
			<button>대기</button>
			<button>완료</button>
			<button id="fullScreenBtn">전체화면</button>
		</div>
	</header>
	<div class="main-a">
		<div class="main-box">
			<div class="item">
				<label for="barcodeInput">바코드 입력</label>
				<input id="barcodeInput">
			</div>
			<div class="item">
				<label for="box-LotNo">박스 LOTNO</label>
				<input id="box-LotNo">
			</div>
			<div class="item">
				<label for="production-LotNo">생산 LOTNO</label>
				<input id="production-LotNo">
			</div>
			<div class="item">
				<label for="production-Qty">생 산 량</label>
				<input id="production-Qty">
			</div>
		</div>
	</div>
	<div class="main-b">
		<div class="main-box">
			<div class="item">
				<label for="item-LotNo1">원자재 LOTNO 1</label>
				<input id="item-LotNo1">
			</div>
			<div class="item">
				<label for="item-LotNo2">원자재 LOTNO 2</label>
				<input id="item-LotNo2">
			</div>
			<div class="item">
				<label for="item-LotNo3">원자재 LOTNO 3</label>
				<input id="item-LotNo3">
			</div>
			<div class="item">
				<label for="item-LotNo4">원자재 LOTNO 4</label>
				<input id="item-LotNo4">
			</div>
		</div>
	</div>
	<div class="main-c">
		<div id="itemTable"></div>
	</div>
	<footer class="global-footer">
		<div class="footer-box"></div>
	</footer>
</div>
<script src="/js/tablet/maskProductionTablet.js?v=<%=System.currentTimeMillis() %>"></script>