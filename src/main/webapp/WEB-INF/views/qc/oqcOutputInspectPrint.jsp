<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	<div class="soloView1">
		<!-- MAIN -->
		<div class="main">
			<!-- 그리드 생성 장소 -->
			<div class="master-table" style="padding-left: 10px;">
			<table class="tg table table-bordered">
		<!-- 		<tr>
						<td colspan="3">문서번호</td>
						<td colspan="4"><input type="text" id="proInspectEquipName" readonly></td>
						<td colspan="3">고&nbsp;&nbsp;객&nbsp;&nbsp;사</td>
						<td colspan="4"><input type="text" id="proInspectItemName" readonly></td>
						<td colspan="3">검&nbsp;&nbsp;사&nbsp;&nbsp;자</td>
						<td colspan="4"><input type="text" id="proInspectItemName" readonly></td>
					</tr>
					<tr>
						<td colspan="3">품&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</td>
						<td colspan="4"><input type="text" id="productionDate" readonly></td>
						<td colspan="3">출고수량</td>
						<td colspan="4"><input type="text" id="productionDate" readonly></td>
						<td colspan="3">규&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;격</td>
						<td colspan="4"><input type="text" id="productionDate" readonly></td>
					</tr>
					<tr>
						<td colspan="3">검사일자</td>
						<td colspan="4"><input type="text" id="processQty"></td>
						<td colspan="3">검사수량</td>
						<td colspan="4"><input type="text" id="productionDate" readonly></td>
						<td colspan="3">검사기준</td>
						<td colspan="4">KS Q ISO 2859-1</td>
					</tr> -->
			<tbody>
				<tr>
					<td colspan="3" style="border: 2px; width: 40px;">NO</td>
					<td colspan="3">검사항목</td>
					<td colspan="2">검사기준</td>
					<td colspan="1">X1</td>
					<td colspan="1">X2</td>
					<td colspan="1">X3</td>
					<td colspan="1">X4</td>
					<td colspan="1">X5</td>
					<td colspan="1">X1</td>
					<td colspan="1">X2</td>
					<td colspan="1">X3</td>
					<td colspan="1">X4</td>
					<td colspan="1">X5</td>
					<td colspan="2">결과</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">1</td>
					<td colspan="3">성상</td>
					<td colspan="2">마스크 좌우 상단
					<input type="hidden" id="stnd1" style="width: 20%">
					<input type="hidden" id="stnd2" style="width: 20%">
					</td>
					<td colspan="1"><input type="text" id="value1"></td>
					<td colspan="1"><input type="text" id="value2"></td>
					<td colspan="1"><input type="text" id="value3"></td>
					<td colspan="1"><input type="text" id="value4"></td>
					<td colspan="1"><input type="text" id="value5"></td>
					<td colspan="2">
						<select id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">2</td>
					<td rowspan="4" colspan="1">형상</td>
					<td colspan="2">가로</td>
					<td colspan="2">
						<input type="text" id="stnd1" style="width: 20%">
						±
						<input type="text" id="stnd2" style="width: 20%">
					</td>
					<td colspan="1"><input type="text" id="value1"></td>
					<td colspan="1"><input type="text" id="value2"></td>
					<td colspan="1"><input type="text" id="value3"></td>
					<td colspan="1"><input type="text" id="value4"></td>
					<td colspan="1"><input type="text" id="value5"></td>
					<td colspan="2">
						<select id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">3</td>
					<td colspan="2">세로</td>
					<td colspan="2">
						<input type="text" id="stnd1" style="width: 20%">
						±
						<input type="text" id="stnd2" style="width: 20%">
					</td>
					<td colspan="1"><input type="text" id="value1"></td>
					<td colspan="1"><input type="text" id="value2"></td>
					<td colspan="1"><input type="text" id="value3"></td>
					<td colspan="1"><input type="text" id="value4"></td>
					<td colspan="1"><input type="text" id="value5"></td>
					<td colspan="2">
						<select id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">4</td>
					<td colspan="2">머리끈(좌)</td>
					<td colspan="2">
						<input type="text" id="stnd1" style="width: 20%">
						±
						<input type="text" id="stnd2" style="width: 20%">
					</td>
					<td colspan="1"><input type="text" id="value1"></td>
					<td colspan="1"><input type="text" id="value2"></td>
					<td colspan="1"><input type="text" id="value3"></td>
					<td colspan="1"><input type="text" id="value4"></td>
					<td colspan="1"><input type="text" id="value5"></td>
					<td colspan="2">
						<select id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">5</td>
					<td colspan="2">머리끈(우)</td>
					<td colspan="2">
						<input type="text" id="stnd1" style="width: 20%">
						±
						<input type="text" id="stnd2" style="width: 20%">
					</td>
					<td colspan="1"><input type="text" id="value1"></td>
					<td colspan="1"><input type="text" id="value2"></td>
					<td colspan="1"><input type="text" id="value3"></td>
					<td colspan="1"><input type="text" id="value4"></td>
					<td colspan="1"><input type="text" id="value5"></td>
					<td colspan="2">
						<select id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">6</td>
					<td colspan="3">순도시험</td>
					<td colspan="9">
					공인기관 시험성적서 참조
					</td>
					<td colspan="2">
						<select id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">7</td>
					<td colspan="3">분진포집효율</td>
					<td colspan="9">
					공인기관 시험성적서 참조
					</td>
					<td colspan="2">
						<select id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">8</td>
					<td colspan="3">안면부 흡기저항</td>
					<td colspan="9">
					공인기관 시험성적서 참조
					</td>
					<td colspan="2">
						<select id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">9</td>
					<td colspan="3">이어밴드</td>
					<td colspan="9">
					공인기관 시험성적서 참조
					</td>
					<td colspan="2">
						<select id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">10</td>
					<td colspan="14">외관검사</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">11</td>
					<td colspan="3">패키지상태</td>
					<td colspan="6">
					밀봉상태 및 인쇄상태 확인
					</td>
					<td colspan="3"><input type="text"></td>
					<td colspan="2">
						<select id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">12</td>
					<td colspan="3">박스상태</td>
					<td colspan="6">
					테이핑 및 박스포장 상태 확인
					</td>
					<td colspan="3"><input type="text"></td>
					<td colspan="2">
						<select id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">13</td>
					<td colspan="3">포장단위</td>
					<td colspan="6">
					보건용, 비말용
					</td>
					<td colspan="3"><input type="text"></td>
					<td colspan="2">
						<select id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">14</td>
					<td colspan="3">포장단위</td>
					<td colspan="6">
					보건용, 비말용
					</td>
					<td colspan="3"><input type="text"></td>
					<td colspan="2">
						<select id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">15</td>
					<td colspan="3">포장단위</td>
					<td colspan="6">
					보건용, 비말용
					</td>
					<td colspan="3"><input type="text"></td>
					<td colspan="2">
						<select id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="border: 2px; width: 30px;">16</td>
					<td colspan="3">포장단위</td>
					<td colspan="6">
					보건용, 비말용
					</td>
					<td colspan="3"><input type="text"></td>
					<td colspan="2">
						<select id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
		</div>
		</div>
		<!-- END MAIN -->
	</div>

<!-- END WRAPPER -->
<!-- Javascript -->