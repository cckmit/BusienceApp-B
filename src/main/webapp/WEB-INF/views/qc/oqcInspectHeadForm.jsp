<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	<div class="soloView1" style="height: 130px !important;">
		<!-- MAIN -->
		<div class="main">
			<!-- 그리드 생성 장소 -->
			<div class="master-table" style="padding-left: 2px;">
			<table class="table table-bordered">
			<tbody>
				<tr>
					<td colspan="3" class="formHeader">문서번호</td>
					<td colspan="4" class="formHeader"><input type="text" id="oqcDocumentNo" value="QC211230-04" readonly></td>
					<td colspan="3" class="formHeader">고&nbsp;&nbsp;객&nbsp;&nbsp;사</td>
					<td colspan="4" class="formHeader"><input type="text" id="oqcCusName" readonly></td>
					<td colspan="3" class="formHeader">검&nbsp;&nbsp;사&nbsp;&nbsp;자</td>
					<td colspan="4" class="formHeader">
						<select id="oqcWorkerList" onchange="">
							<c:forEach var="list" items="${oqcInspectWorkerList}">
								<option value="${list.CHILD_TBL_NO}">${list.CHILD_TBL_TYPE}</option>
							</c:forEach>			
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3" class="formHeader">품&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</td>
					<td colspan="4" class="formHeader"><input type="text" id="oqcItemName" readonly></td>
					<td colspan="3" class="formHeader">출고수량</td>
					<td colspan="4" class="formHeader"><input type="text" id="oqcOutMatQty" readonly>&nbsp;EA</td>
					<td colspan="3" class="formHeader">규&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;격</td>
					<td colspan="4" class="formHeader"><input type="text" id="oqcItemSTND_1" readonly></td>
				</tr>
				<tr>
					<td colspan="3" class="formHeader">검사일자</td>
					<td colspan="4" class="formHeader"><input type="text" id="oqcDate" readonly></td>
					<td colspan="3" class="formHeader">검사수량</td>
					<td colspan="4" class="formHeader"><input type="text" id="oqcQty">&nbsp;EA</td>
					<td colspan="3" class="formHeader">검사기준</td>
					<td colspan="4" class="formHeader">KS Q ISO 2859-1</td>
				</tr>
			 </tbody>
			</table>
		</div>
		</div>
		<!-- END MAIN -->
	</div>

<!-- END WRAPPER -->
<!-- Javascript -->