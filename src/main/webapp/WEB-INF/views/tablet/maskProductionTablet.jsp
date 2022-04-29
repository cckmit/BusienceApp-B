<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.container-bs{
	display: grid;
	grid-template-columns: 2fr 3fr;
	grid-template-rows: 10vh 10vh 40vh 15vh 25vh;
	grid-template-areas:
		"header header"
		"main-a main-c"
		"main-b main-c"
		"main-d main-d"
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
	font-weight: bold;
	font-size: 3vh;
	color: rgb(68,114,196);
	cursor: pointer;
}
.global-header #fullScreenBtn{
	font-weight: bold;
	font-size: 3vh;
	color: rgb(51,13,97);
	border: solid rgb(51,13,97);
	border-radius: 10px;
	cursor: pointer;
}
.global-header .title{
	height: 100%;
	width: 50%;
	display: flex;
	justify-content: space-around;
    align-items: center;
    
    font-size: 4vh;
	color: white;
	background-color: rgb(112,173,70);
}
.main-a{
	grid-area: main-a;
    padding: 5px;
}
.main-a .main-box{
	background-color: rgb(68,114,196);
}
.main-a .main-box label{
	color: white;
}
.main-b{
	grid-area: main-b;
    padding: 5px;
}
.main-b .main-box{
	grid-template-rows: repeat(4, 1fr);
}
.main-c{
	grid-area: main-c;
    padding: 5px;
}
.main-c .main-box{
	grid-template-rows: 1fr 2fr 2fr 2fr 2fr;
}
.main-c .main-box .item span{
	text-align: center;
}
.main-c .main-box .item .LotNo_Name{
	width: 30%;
}
.main-c .main-box .item .LotNo{
	width: 40%;
}
.main-c .main-box .item .LotNo_Qty{
	width: 20%;
}
.main-d{
	grid-area: main-d;
    padding: 5px;
}
.main-box{
	background-color: rgb(82,153,217);
    height: 100%;
    width: 100%;
    
	display: grid;
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
	width: 60%;
}

.global-footer{
	grid-area: footer;
    padding: 5px;
}
.footer-box{
	background-color: rgb(207,213,234);
    height: 100%;
    width: 100%;
    display: flex;
    justify-content: space-around;
}

.tablet-border{
	border-radius: 20px;
	border: solid #333;
}

.tablet-Table{
	font-size: 2vh;
}
</style>
<div class="container-bs">
	<header class="global-header">
		<div>
			<input id="machineCode" type="hidden" value="${machineInfo.EQUIPMENT_INFO_CODE}">
			<span id="machineName">${machineInfo.EQUIPMENT_INFO_NAME}</span>
		</div>
		<div class="title tablet-border">
			<span>작업관리 (마스크 생산)</span>
		</div>
		<div>
			<span id="fullScreenBtn">전체화면</span>
		</div>
	</header>
	<div class="main-a">
		<div class="main-box tablet-border">
			<div class="item">
				<label for="barcodeInput">바코드 입력</label>
				<input id="barcodeInput" autofocus>
			</div>
		</div>
	</div>
	<div class="main-b">
		<div class="main-box tablet-border">
			<div class="item">
				<label for="crate-LotNo">상자 LOTNO</label>
				<input id="crate-LotNo" disabled>
				<input type="hidden" id="crateCode">
			</div>
			<div class="item">
				<label for="crate-Qty">상자 내 생산량</label>
				<input id="crate-Qty" readonly>
			</div>
			<div class="item">
				<label for="production-ID">자재 식별 코드</label>
				<input id="production-ID" disabled>
			</div>
			<div class="item">
				<label for="production-Qty">생 산 량</label>
				<input id="production-Qty" readonly>
			</div>
		</div>
	</div>
	<div class="main-c">
		<div class="main-box tablet-border">
			<div class="item">
				<span class="LotNo_Name">원자재 명</span>
				<span class="LotNo">원자재 LOTNO</span>
				<span class="LotNo_Qty">수량</span>
			</div>
			<div class="item">
				<input type="hidden" class="LotNo_Code">
				<input class="LotNo_Name" disabled>
				<input class="LotNo" readonly>
				<input class="LotNo_Qty" disabled>
			</div>
			<div class="item">
				<input type="hidden" class="LotNo_Code">
				<input class="LotNo_Name" disabled>
				<input class="LotNo" readonly>
				<input class="LotNo_Qty" disabled>
			</div>
			<div class="item">
				<input type="hidden" class="LotNo_Code">
				<input class="LotNo_Name" disabled>
				<input class="LotNo" readonly>
				<input class="LotNo_Qty" disabled>
			</div>
			<div class="item">
				<input type="hidden" class="LotNo_Code">
				<input class="LotNo_Name" disabled>
				<input class="LotNo" readonly>
				<input class="LotNo_Qty" disabled>
			</div>
		</div>
	</div>
	<div class="main-d">
		<div id="itemTable" class="tablet-border tablet-Table"></div>
	</div>
	<footer class="global-footer">
		<div class="footer-box tablet-border">
			<div>
				<div id="crateTable" class="tablet-Table"></div>
			</div>
			<div>
				<div id="rawMaterialTable" class="tablet-Table"></div>
			</div>
		</div>
	</footer>
</div>
<script src="/js/tablet/maskProductionTablet.js?v=<%=System.currentTimeMillis() %>"></script>