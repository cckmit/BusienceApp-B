setInterval(function() {
	Time_Update();
}, 1000);

function Time_Update() {
	var today = new Date();

	var year = today.getFullYear();
	var month = ('0' + (today.getMonth() + 1)).slice(-2);
	var day = ('0' + today.getDate()).slice(-2);

	var dateString = year + '-' + month + '-' + day;

	var hours = ('0' + today.getHours()).slice(-2);
	var minutes = ('0' + today.getMinutes()).slice(-2);
	var seconds = ('0' + today.getSeconds()).slice(-2);

	var timeString = hours + ':' + minutes + ':' + seconds;

	//console.log(dateString+" "+timeString);

	var time = document.getElementsByClassName("time");

	for (i = 0; i < time.length; i++)
		time[i].innerHTML = dateString + " " + timeString;
}

google.charts.load('current', { 'packages': ['gauge'] });

var chartvalue1 = 0, chartvalue2 = 0, chartvalue3 = 0;

var grid1_flag = true;
function drawChart1() {
	grid1_flag = false;

	Time_Update();

	chart1Data = google.visualization.arrayToDataTable([
		['Label', 'Value'],
		['Temperature', 0],
		['Humidity', 0],
		['Speed', 0]
	]);

	var options = {
		width: 800, height: 520,
		redFrom: 90, redTo: 100,
		yellowFrom: 75, yellowTo: 90,
		minorTicks: 5
	};

	chart1 = new google.visualization.Gauge(document.getElementById('chart_div1'));

	chart1Data.setValue(0, 1, chartvalue1);
	chart1Data.setValue(1, 1, chartvalue2);
	chart1Data.setValue(2, 1, chartvalue3);
	chart1.draw(chart1Data, options);

	$.get("GraphicRest/hogi_time_select?num=M001", function(jqXHR) {
		if (jqXHR[0] > 2) {
			document.getElementById('1_card').style.background = 'red';

			chartvalue1 = 0;
			chartvalue2 = 0;
			chartvalue3 = 0;

			document.getElementById('1_temp').innerHTML = "0°";
			document.getElementById('1_humi').innerHTML = "0°";
			document.getElementById('1_speed').innerHTML = "0m/s";

			//google.charts.setOnLoadCallback(drawChart1);
			document.getElementById('1h').innerHTML = 'OFF';
		}
		else {
			$.get("GraphicRest/hogi_select?num=M001", function(jqXHR) {
				document.getElementById('1_card').style.background = 'green';

				chartvalue1 = 0;
				chartvalue2 = 0;
				chartvalue3 = 0;
				chartvalue1 = jqXHR[0].temp;
				chartvalue2 = jqXHR[0].humi;
				chartvalue3 = jqXHR[0].speed;

				document.getElementById('1_temp').innerHTML = jqXHR[0].temp + "°";
				document.getElementById('1_humi').innerHTML = jqXHR[0].humi + "°";
				document.getElementById('1_speed').innerHTML = jqXHR[0].speed + "m/s";

				//google.charts.setOnLoadCallback(drawChart1);
				document.getElementById('1h').innerHTML = 'ON';
			}, 'json' /* xml, text, script, html */)
				.done(function(jqXHR) {
					//alert("second success" );
				})
		}
	});
}
google.charts.setOnLoadCallback(drawChart1);

var chartvalue12 = 0, chartvalue22 = 0, chartvalue32 = 0;

function drawChart2() {
	chart1Data = google.visualization.arrayToDataTable([
		['Label', 'Value'],
		['Temperature', 0],
		['Humidity', 0],
		['Speed', 0]
	]);

	var options = {
		width: 800, height: 520,
		redFrom: 90, redTo: 100,
		yellowFrom: 75, yellowTo: 90,
		minorTicks: 5
	};

	chart1 = new google.visualization.Gauge(document.getElementById('chart_div2'));

	chart1Data.setValue(0, 1, chartvalue12);
	chart1Data.setValue(1, 1, chartvalue22);
	chart1Data.setValue(2, 1, chartvalue32);
	chart1.draw(chart1Data, options);

	$.get("GraphicRest/hogi_time_select?num=M002", function(jqXHR) {
		if (jqXHR[0] > 2) {
			document.getElementById('2_card').style.background = 'red';

			chartvalue12 = 0;
			chartvalue22 = 0;
			chartvalue32 = 0;

			document.getElementById('2_temp').innerHTML = "0°";
			document.getElementById('2_humi').innerHTML = "0°";
			document.getElementById('2_speed').innerHTML = "0m/s";

			//google.charts.setOnLoadCallback(drawChart2);
			document.getElementById('2h').innerHTML = 'OFF';
		}
		else {
			$.get("GraphicRest/hogi_select?num=M002", function(jqXHR) {
				document.getElementById('2_card').style.background = 'green';

				chartvalue12 = 0;
				chartvalue22 = 0;
				chartvalue32 = 0;
				chartvalue12 = jqXHR[0].temp;
				chartvalue22 = jqXHR[0].humi;
				chartvalue32 = jqXHR[0].speed;

				document.getElementById('2_temp').innerHTML = jqXHR[0].temp + "°";
				document.getElementById('2_humi').innerHTML = jqXHR[0].humi + "°";
				document.getElementById('2_speed').innerHTML = jqXHR[0].speed + "m/s";

				//google.charts.setOnLoadCallback(drawChart2);
				document.getElementById('2h').innerHTML = 'ON';
			}, 'json' /* xml, text, script, html */)
				.done(function(jqXHR) {
					//alert("second success" );
				})
		}
	});
}
google.charts.setOnLoadCallback(drawChart2);

var chartvalue13 = 0, chartvalue23 = 0, chartvalue33 = 0;

function drawChart3() {

	chart1Data = google.visualization.arrayToDataTable([
		['Label', 'Value'],
		['Temperature', 0],
		['Humidity', 0],
		['Speed', 0]
	]);

	var options = {
		width: 800, height: 520,
		redFrom: 90, redTo: 100,
		yellowFrom: 75, yellowTo: 90,
		minorTicks: 5
	};

	chart1 = new google.visualization.Gauge(document.getElementById('chart_div3'));

	chart1Data.setValue(0, 1, chartvalue13);
	chart1Data.setValue(1, 1, chartvalue23);
	chart1Data.setValue(2, 1, chartvalue33);
	chart1.draw(chart1Data, options);

	$.get("GraphicRest/hogi_time_select?num=M003", function(jqXHR) {
		if (jqXHR[0] > 2) {
			document.getElementById('3_card').style.background = 'red';

			chartvalue13 = 0;
			chartvalue23 = 0;
			chartvalue33 = 0;

			document.getElementById('3_temp').innerHTML = "0°";
			document.getElementById('3_humi').innerHTML = "0°";
			document.getElementById('3_speed').innerHTML = "0m/s";

			//google.charts.setOnLoadCallback(drawChart3);
			document.getElementById('3h').innerHTML = 'OFF';
		}
		else {
			$.get("GraphicRest/hogi_select?num=M003", function(jqXHR) {
				document.getElementById('3_card').style.background = 'green';

				chartvalue13 = 0;
				chartvalue23 = 0;
				chartvalue33 = 0;
				chartvalue13 = jqXHR[0].temp;
				chartvalue23 = jqXHR[0].humi;
				chartvalue33 = jqXHR[0].speed;

				document.getElementById('3_temp').innerHTML = jqXHR[0].temp + "°";
				document.getElementById('3_humi').innerHTML = jqXHR[0].humi + "°";
				document.getElementById('3_speed').innerHTML = jqXHR[0].speed + "m/s";

				//google.charts.setOnLoadCallback(drawChart2);
				document.getElementById('3h').innerHTML = 'ON';
			}, 'json' /* xml, text, script, html */)
				.done(function(jqXHR) {
					//alert("second success" );
				})
		}
	});
}
google.charts.setOnLoadCallback(drawChart3);

var chartvalue14 = 0, chartvalue24 = 0, chartvalue34 = 0;

function drawChart4() {

	chart1Data = google.visualization.arrayToDataTable([
		['Label', 'Value'],
		['Temperature', 0],
		['Humidity', 0],
		['Speed', 0]
	]);

	var options = {
		width: 800, height: 520,
		redFrom: 90, redTo: 100,
		yellowFrom: 75, yellowTo: 90,
		minorTicks: 5
	};

	chart1 = new google.visualization.Gauge(document.getElementById('chart_div4'));

	chart1Data.setValue(0, 1, chartvalue14);
	chart1Data.setValue(1, 1, chartvalue24);
	chart1Data.setValue(2, 1, chartvalue34);
	chart1.draw(chart1Data, options);

	$.get("GraphicRest/hogi_time_select?num=M004", function(jqXHR) {
		if (jqXHR[0] > 2) {
			document.getElementById('4_card').style.background = 'red';

			chartvalue14 = 0;
			chartvalue24 = 0;
			chartvalue34 = 0;

			document.getElementById('4_temp').innerHTML = "0°";
			document.getElementById('4_humi').innerHTML = "0°";
			document.getElementById('4_speed').innerHTML = "0m/s";

			//google.charts.setOnLoadCallback(drawChart4);
			document.getElementById('4h').innerHTML = 'OFF';
		}
		else {
			$.get("GraphicRest/hogi_select?num=M004", function(jqXHR) {
				document.getElementById('4_card').style.background = 'green';

				chartvalue14 = 0;
				chartvalue24 = 0;
				chartvalue34 = 0;
				chartvalue14 = jqXHR[0].temp;
				chartvalue24 = jqXHR[0].humi;
				chartvalue34 = jqXHR[0].speed;

				document.getElementById('4_temp').innerHTML = jqXHR[0].temp + "°";
				document.getElementById('4_humi').innerHTML = jqXHR[0].humi + "°";
				document.getElementById('4_speed').innerHTML = jqXHR[0].speed + "m/s";

				//google.charts.setOnLoadCallback(drawChart2);
				document.getElementById('4h').innerHTML = 'ON';
			}, 'json' /* xml, text, script, html */)
				.done(function(jqXHR) {
					//alert("second success" );
				})
		}
	});
}
google.charts.setOnLoadCallback(drawChart4);

setInterval(function() {
	google.charts.setOnLoadCallback(drawChart1);

	google.charts.setOnLoadCallback(drawChart2);

	google.charts.setOnLoadCallback(drawChart3);

	google.charts.setOnLoadCallback(drawChart4);
}, 1000);

setTimeout(function() {
	//window.location.href = './Graphic2';
}, 5000);