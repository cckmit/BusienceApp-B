<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<style>
.tabulator {
	font-size: 13px;
}

html, body {
	margin: 0;
	height: 100%;
	overflow: hidden;
}

#totalDiv {
	margin: 0;
	height: 100%;
	overflow: hidden;
}

td {
 border: 1px solid black;
}

</style>
<link rel="stylesheet"
	href="/webjars/tabulator/dist/css/tabulator_simple.min.css">
</head>
<body>
	<div class="soloView1">
		<!-- MAIN -->
		<div class="main">
			<!-- 그리드 생성 장소 -->
			<div class="master-table" style="padding-left: 10px;">
				<table class="table table-bordered" style="padding-left: 10px; height: 100px; width: 870px; font-size: 12px;">
					<tr>
						<td colspan="2">품명</td>
						<td><input type="text"></td>
						<td colspan="2">검사일자</td>
						<td colspan="2"><input type="text"></td>
						
					</tr>
					<tr>
						<td colspan="2">수량</td>
						<td><input type="text"></td>
						<td colspan="2">검사자</td>
						<td colspan="2"><input type="text"></td>
					</tr>
					<tr>
						<td colspan="2">검사기준</td>
						<td><input type="text"></td>
						<td colspan="2">공급업체</td>
						<td colspan="2"><input type="text"></td>
					</tr>
					<tr>
						<td colspan="2" style="width: 100px; font-size: 13px;" width="80"
							class="col1"></td>
						<td colspan="4" class="col2"><textarea readonly
								id="processworkOrderText" name="workOrderText" rows="4"
								cols="94" style="resize: none;"></textarea></td>
					</tr>
				</table>
				<table class="table table-bordered"
					style="padding-left: 10px; margin-top: -17px; height: 250px; width: 870px; font-size: 12px; text-align: center;">
					<tr>
						<td rowspan="2" style="border: 1px;">번호</td>
						<td rowspan="2">검사항목</td>
						<td rowspan="2">검사규격</td>
						<td colspan="5">측정 DATA</td>
						<td rowspan="2">판정</td>
					</tr>
					<tr>
						<td>X1</td>
						<td>X2</td>
						<td>X3</td>
						<td>X4</td>
						<td>X5</td>
					</tr>
					<tr>
						<td style="border: 2px; 5width: 30px;">1</td>
						<td style="width: 100px;">성상</td>
						<td style="width: 300px;">청격하고 자극성이 없으며 이물이 함유되어 있지 않고 섬유의
							탈락이 거의 없는 흰색의 포로서 &nbsp;&nbsp;&nbsp; 냄새는 없다.</td>
						<td style="width: 40px;"><input style="width: 60px;" type="text"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text"></td>
						<td style="width: 50px;"><select></select></td>
					</tr>
					<tr>
						<td style="border: 1px;">2</td>
						<td>색소</td>
						<td>부직포의 순도시험 중 1)색소에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 400px;" type="text"></td>
					</tr>
					<tr>
						<td style="border: 1px;">3</td>
						<td>산 또는 알칼리</td>
						<td>부직포의 순도시험 중 2)산 또는 알칼리에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 400px;" type="text"></td>
					</tr>
					<tr>
						<td style="border: 1px;">4</td>
						<td>형광증백제</td>
						<td>부직포의 순도시험 중 3)형광증백제에 따라 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 시험한다.</td>
						<td colspan="5"><input style="width: 400px;" type="text"></td>
					</tr>
					<tr>
						<td style="border: 1px;">5</td>
						<td>회분</td>
						<td>부직포의 회분에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 400px;" type="text"></td>
					</tr>
					<tr>
						<td style="border: 1px;">6</td>
						<td>포름알데히드</td>
						<td>부직포의 포름알데히드에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 400px;" type="text"></td>
					</tr>
					<tr>
						<td style="border: 1px;">7</td>
						<td>강도</td>
						<td>부직포의 강도에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 400px;" type="text"></td>
					</tr>
					<tr>
						<td style="border: 1px;">8</td>
						<td>치수(A)</td>
						<td>230 ±2mm</td>
						<td colspan="5"><input style="width: 400px;" type="text"></td>
					</tr>
					<tr>
						<td style="border: 1px;">9</td>
						<td>중량</td>
						<td>40g ±4g</td>
						<td colspan="5"><input style="width: 400px;" type="text"></td>
					</tr>
					<tr>
						<td style="border: 1px;">10</td>
						<td>두께</td>
						<td>1.0 ±0.1mm</td>
						<td colspan="5"><input style="width: 400px;" type="text"></td>
					</tr>
				</table>
			</div>
		</div>
		<!-- END MAIN -->
	</div>
</body>

<!-- END WRAPPER -->
<!-- Javascript -->