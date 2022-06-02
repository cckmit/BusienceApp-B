<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	<div class="soloView1">
		<!-- MAIN -->
		<div class="main">
			<!-- 그리드 생성 장소 -->
			<div class="master-table" style="padding-left: 10px;">
			<table class="tg table table-bordered">
			<tbody>
					<tr>
						<td colspan="3">생산라인</td>
						<td colspan="4"><input type="text" id="proInspectEquipName" readonly></td>
						<td colspan="3">제품명</td>
						<td colspan="4"><input type="text" id="proInspectItemName" readonly></td>
					</tr>
					<tr>
						<td colspan="3">생산일자/검사일자</td>
						<td colspan="4"><input type="text" id="productionDate" readonly> / <input type="text" id="processDate" readonly></td>
						<td colspan="3">검사자</td>
						<td colspan="4">
						<select id="pqcWorkerList" onchange="">
							<c:forEach var="list" items="${processInspectList}">
								<option value="${list.CHILD_TBL_NO}">${list.CHILD_TBL_TYPE}</option>
							</c:forEach>			
						</select>
						</td>
					</tr>
					<tr>
						<td colspan="3">검사기준/검사수량</td>
						<td colspan="4">KS Q ISO 2859-1</td>
						<td colspan="3">시료수</td>
						<td colspan="4"><input type="text" id="processQty">&nbsp;&nbsp;
						<select id="itemColorType">
							<option value="화이트">화이트</option>
							<option value="블랙">블랙</option>
						</select>
						</td>
					</tr>
				<tr>
					<td colspan="3">검사항목</td>
					<td colspan="4">검사기준</td>
					<td colspan="1">X1</td>
					<td colspan="1">X2</td>
					<td colspan="1">X3</td>
					<td colspan="1">X4</td>
					<td colspan="1">X5</td>
					<td colspan="2">결과</td>
				</tr>
				<tr>
					<td colspan="3">성상</td>
					<td colspan="4">마스크 좌우 상단, 우 하단에 글자 등 새김이 있는 (흰색, 검정색)의 2단 세로접이식 본체에 표면이 있고,
					양 측면에 (흰색, 검정색)의 끈이 있는 부직포 마스크
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
					<td rowspan="4" colspan="1">형상</td>
					<td colspan="2">가로</td>
					<td colspan="4">
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
					<td colspan="2">세로</td>
					<td colspan="4">
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
					<td colspan="2">머리끈(좌)</td>
					<td colspan="4">
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
					<td colspan="2">머리끈(우)</td>
					<td colspan="4">
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
					<td colspan="3">이어밴드 접합부 인장강도</td>
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
					<td colspan="14">외관검사</td>
				</tr>
				<tr>
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