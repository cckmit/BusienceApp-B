<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="images/button/Search.png"
					onclick="MI_searchBtn1()" />
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<div class="col-sm-12 col-md-12">
					<span><strong>작업시작일</strong></span> <input id="startDate1"
						class="today" type="date"> <span
						style="text-align: center"><strong>~</strong></span> <input
						id="endDate1" class="tomorrow" type="date">
				</div>

				<br>
				<br>

				<div class="col-sm-12 col-md-12">
					<span><strong>제품코드</strong></span> <input id="PRODUCT_ITEM_CODE1"
						class="Item_Code1" type="text" disabled> <span><strong>제품명</strong></span>
					<input id="PRODUCT_ITEM_NAME1" class="Item_Name1 clearInput"
						type="text"> <span><strong>설비코드</strong></span> <input
						id="Machine_Code1" class="Machine_Code1" type="text" disabled>
					<span><strong>설비명</strong></span> <input id="Machine_Name1"
						class="Machine_Name1 clearInput" type="text">
				</div>
			</div>
		</div>
		<div id="WorkOrder_tbl_DayP"
			style="float: left; width: 50%; border-right: solid;"></div>
		<div id="WorkOrder_tbl_DayC"
			style="float: left; width: 18%; border-right: solid;"></div>
		<div id="material" style="height: 530px; float: left; width: 32%;"></div>

		<!-- END MAIN -->

	</div>
</div>

<!-- END WRAPPER -->
<!-- Javascript -->
<script src="/js/productionLX/workdList.js"></script>
<script src="/js/tabMenu.js"></script>

<script>
	/*
	//const labels = [
	    'January',
	    'February',
	    'March',
	    'April',
	    'May',
	    'June',
	];
	
	const data = {
	    labels: labels,
	    datasets: [{
	        label: 'My First dataset',
	        backgroundColor: 'rgb(255, 99, 132)',
	        borderColor: 'rgb(255, 99, 132)',
	        data: [0, 10, 5, 2, 20, 30, 45],
	    }]
	};
	const config = {
	    type: 'line',
	    data,
	    options: {}
	};
	
	var myChart = new Chart(
	    document.getElementById('myChart'),
	    config
	);
	 */

	google.load("visualization", "1", {
		packages : [ "corechart" ]
	});
	//google.charts.load('current', { 'packages': ['bar'] });
	google.setOnLoadCallback(drawChart);

	function drawChart(chartgoogledata) {
		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Day');
		data.addColumn('number', '지시수량');
		//data.addColumn({ type: 'number', role: 'annotation' });
		data.addColumn({
			type : 'string',
			role : 'annotationText'
		});
		//data.addColumn('number', '생산량',{type:'number', role:'annotationText'});
		data.addColumn('number', '생산량', {
			type : 'number',
			role : 'annotation'
		});
		data.addColumn({
			type : 'number',
			role : 'annotation'
		});
		data.addColumn({
			type : 'string',
			role : 'annotationText'
		});
		data.addColumn('number', '누적량');
		data.addColumn({
			type : 'number',
			role : 'annotation'
		});
		data.addColumn({
			type : 'string',
			role : 'annotationText'
		});

		console.log(chartgoogledata);

		if (chartgoogledata != undefined)
			data.addRows(chartgoogledata);
		else {
			data.addRows([ [ '0', 0, '주석 설명', 0, 0, '주석 설명', 0, 0, '주석 설명' ] ]);
		}

		var options = {
			title : '세부작업현황 그래프',
			chartArea : {
				// leave room for y-axis labels
				width : '85%',
				height : '85%'
			},
			legend : {
				position : 'bottom'
			}
		};

		// line
		//var chart = new google.charts.Line(document.getElementById('material'));
		//chart.draw(data, google.charts.Line.convertOptions(options));

		// bar
		//var chart = new google.charts.Bar(document.getElementById('material'));
		//chart.draw(data, google.charts.Bar.convertOptions(options));

		// core
		var chart = new google.visualization.LineChart(document
				.getElementById('material'));
		chart.draw(data, options);
	}
</script>
</body>
</html>