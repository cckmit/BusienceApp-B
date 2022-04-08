<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<meta charset="UTF-8">
<style>
html, body {
	margin: 0;
	height: 100%;
	overflow: hidden;
	background: white;
}
.tabulator{ background-color:red; }

#header { padding-top: 2px; border: solid; font-size: 2vw; text-align: center; background-color: rgb(82, 153, 217); border-radius: 15px; }
#barcode { width: 65%; font-size: 2.5vw; color:white; text-align: left; }
#barcodeInput { width: 70%; background-color:white; color:black; height: 7vh; text-align: right; margin-top: 1%; border-radius: 10px; }
#start { border-style: solid; border-color: rgb(0, 150, 0); border-width: 1%; border-radius: 15px; height: 100%; color: rgb(0, 150, 0); text-align: center; font-size: 3vw; margin-left: 8%; }
#wait { border-style: solid; border-color: rgb(0, 0, 200); border-width: 1%; border-radius: 15px; height: 100%; color: rgb(0, 0, 200); text-align: center; font-size: 3vw; }
#success { border-style: solid; border-color: rgb(220, 0, 0); border-width: 1%; border-radius: 15px; height: 100%; color: rgb(220, 0, 0); text-align: center; font-size: 3vw; }

#main { border: solid; font-size: 2vw; height: 50vh; text-align: center; background-color: rgb(82, 153, 217); border-radius: 15px; }
#mainTable { width: 100%; height: 25%; }
#mainInfo { color: white; text-align: center; width: 30%; }
#mainTd { color: black; text-align: center; width: 70%; }
#mainInput{ width: 90%; height: 60%; color: black; border: groove; border-color: black; border-width: 1px; text-align: right; font-size: 1.5vw; }

#main2 { border: solid; font-size: 2vw; text-align: center; background-color: rgb(82, 153, 217); border-radius: 15px; padding-bottom: 1%; }
#mainInfo2 { width: 100%; height: 17%; }
#mainInfo2Td { color: white; text-align: center; width: 33%; }
#mainTable2 { width: 100%; height: 17%; margin-left: 2%; font-size: 2vw; }
#mainTd2 { text-align: center; width: 33%; }
#mainLotInput { font-size: 1.8vw; width: 90%; height: 80%; background-color: black; color: white; border: groove; border-color: black; border-width: 1px; text-align: right; }
#mainInput2 { width: 90%; height: 80%; color: white; border: groove; border-color: black; border-width: 1px; text-align: right; }

#end { border:solid; background: rgb(90, 155, 213); border-radius: 15px; height: 39vh; font-size: 3vw; text-align: center; }
</style>
<title>비지언스 MES</title>
</head>
<body>
	<div class="container-fluid">
			<div class="row" id="header">
				<div id="barcode" class="col-sm-6 col-md-8">
					&nbsp;바코드 입력 &nbsp;
					<input type="text" id="barcodeInput">
				</div>
				<div id="start" class="col-sm-1 col-md-1">시작</div>
				<div id="wait" class="col-sm-1 col-md-1">대기</div>
				<div id="success" class="col-sm-1 col-md-1">완료</div>
			</div>

			<div class="row">
				<div class="col-md-4" id="main">
					<table id="mainTable">
						<tr>
							<td id="mainInfo">
								설비명
							</td>
							<td id="mainTd">
								<input id="mainInput" readonly="readonly" class="form-control form-control-lg" type="text" name="EqInfoName">
							</td>
						</tr>
					</table>

					<table id="mainTable">
						<tr>
							<td id="mainInfo">
								모델
							</td>
							<td id="mainTd">
								<input id="mainInput" readonly="readonly" class="form-control form-control-lg" type="text" name="ProItemName">
							</td>
						</tr>
					</table>

					<table id="mainTable">
						<tr>
							<td id="mainInfo">
								규격
							</td>
							<td id="mainTd">
								<input id="mainInput" readonly="readonly" class="form-control form-control-lg" type="text" name="proInfoSTND">
							</td>
						</tr>
					</table>

					<table id="mainTable">
						<tr>
							<td id="mainInfo">
								사이즈
							</td>
							<td id="mainTd">
								<input id="mainInput" readonly="readonly" class="form-control form-control-lg" type="text">
							</td>
						</tr>
					</table>
				</div>
				
				<div class="col-md-8" id="main2">
					<table id="mainInfo2">
						<tr>
							<td id="mainInfo2Td">
								원&nbsp;자&nbsp;재&nbsp;Lot
							</td>
							<td id="mainInfo2Td">
								원&nbsp;자&nbsp;재&nbsp;명
							</td>
							<td id="mainInfo2Td">
								수&nbsp;량
							</td>
						</tr>
					</table>

					<table id="mainTable2">
						<tr>
							<td id="mainTd2">
								<input id="mainLotInput" readonly="readonly" class="form-control form-control-lg" code="one" type="text">
							</td>
							<td id="mainTd2">
								<input id="mainInput2" readonly="readonly" class="form-control form-control-lg" type="text">
							</td>
							<td id="mainTd2">
								<input id="mainInput2" readonly="readonly" class="form-control form-control-lg" type="text">
							</td>
						</tr>
					</table>

					<table id="mainTable2">
						<tr>
							<td id="mainTd2">
								<input id="mainLotInput" readonly="readonly" class="form-control form-control-lg" code="one" type="text">
							</td>
							<td id="mainTd2">
								<input id="mainInput2" readonly="readonly" class="form-control form-control-lg" type="text">
							</td>
							<td id="mainTd2">
								<input id="mainInput2" readonly="readonly" class="form-control form-control-lg" type="text">
							</td>
						</tr>
					</table>

					<table id="mainTable2">
						<tr>
							<td id="mainTd2">
								<input id="mainLotInput" readonly="readonly" class="form-control form-control-lg" code="one" type="text">							</td>
							<td id="mainTd2">
								<input id="mainInput2" readonly="readonly" class="form-control form-control-lg" type="text">
							</td>
							<td id="mainTd2">
								<input id="mainInput2" readonly="readonly" class="form-control form-control-lg" type="text">
							</td>
						</tr>
					</table>

					<table id="mainTable2">
						<tr>
							<td id="mainTd2">
								<input id="mainLotInput" readonly="readonly" class="form-control form-control-lg" code="one" type="text">
							</td>
							<td id="mainTd2">
								<input id="mainInput2" readonly="readonly" class="form-control form-control-lg" type="text">
							</td>
							<td id="mainTd2">
								<input id="mainInput2" readonly="readonly" class="form-control form-control-lg" type="text">
							</td>
						</tr>
					</table>
					
					<table id="mainTable2">
						<tr>
							<td id="mainTd2">
								<input id="mainLotInput" readonly="readonly" class="form-control form-control-lg" code="one" type="text">
							</td>
							<td id="mainTd2">
								<input id="mainInput2" readonly="readonly" class="form-control form-control-lg" type="text">
							</td>
							<td id="mainTd2">
								<input id="mainInput2" readonly="readonly" class="form-control form-control-lg" type="text">
							</td>
						</tr>
					</table>
				</div>
			</div>
		<div class="row" id="end">
		</div>
	</div>
	<script src="/js/tablet/maskProductionTablet.js"></script>
</body>
</html>