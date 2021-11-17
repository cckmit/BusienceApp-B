<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
html, body {
    margin: 0;
    height: 100%;
    overflow: hidden;
}
</style>

<style>
.progress-bar{
	background-color: #B0DEFC;
}
</style>

<script type="text/javascript" src="https://cdn.fusioncharts.com/fusioncharts/latest/fusioncharts.js"></script>
	<script type="text/javascript" src="https://cdn.fusioncharts.com/fusioncharts/latest/themes/fusioncharts.theme.fusion.js"></script>
	<script type="text/javascript">
		FusionCharts.ready(function(){
			var chartObj = new FusionCharts({
			    type: 'thermometer',
			    renderAt: 'chart-container',
			    width: '100%',
			    height: '82.5%',
			    dataFormat: 'json',
			    dataSource: {
			        "chart": {
			            "caption": "설비명1 온도",
			            "subcaption": "설비명1의 현재 온도",
			            "lowerLimit": ${Min_Value},
						"upperLimit": ${Max_Value},
			
			            "decimals": "1",
			            "numberSuffix": "°C",
			            "showhovereffect": "1",
			            "thmFillColor": "#008ee4",
			            "showGaugeBorder": "1",
			            "gaugeBorderColor": "#008ee4",
			            "gaugeBorderThickness": "2",
			            "gaugeBorderAlpha": "30",
			            "thmOriginX": "100",
			            "chartBottomMargin": "20",
			            "valueFontColor": "#000000",
			            "theme": "fusion"
			        },
			        "value": "-6",
			        //All annotations are grouped under this element
			        "annotations": {
			            "showbelow": "0",
			            "groups": [{
			                //Each group needs a unique ID
			                "id": "indicator",
			                "items": [
			                    //Showing Annotation
			                    {
			                        "id": "background",
			                        //Rectangle item
			                        "type": "rectangle",
			                        "alpha": "50",
			                        "fillColor": "#AABBCC",
			                        "x": "$gaugeEndX-40",
			                        "tox": "$gaugeEndX",
			                        "y": "$gaugeEndY+54",
			                        "toy": "$gaugeEndY+72"
			                    }
			                ]
			            }]
			
			        },
			    },
			    "events": {
				        "rendered": function(evt, arg) {
				            evt.sender.dataUpdate = setInterval(function() {
				            	//debugger;
				            	
				            	// 현재 온도 값을 저장하는 코드
				            	
				                var value,
				                    prevTemp = evt.sender.getData(),
				                    mainTemp = (Math.random() * 10) * (-1),
				                    diff = Math.abs(prevTemp - mainTemp);
				
				                diff = diff > 1 ? (Math.random() * 1) : diff;
				                if (mainTemp > prevTemp) {
				                    value = prevTemp + diff;
				                } else {
				                    value = prevTemp - diff;
				                }
				
				                //console.log(value);
				                
								$.get("/temperatureMonitoringRestController/temperature_Current",function(data){
									evt.sender.feedData("&value="+data);
								});
				
				            }, 3000);
				            evt.sender.updateAnnotation = function(evtObj, argObj) {
				            	
				            	// 색상을 바꿔주는 코드
				            	
				                var code,
				                    chartObj = evtObj.sender,
				                    val = chartObj.getData(),
				                    annotations = chartObj.annotations;
				
				                if (val >= -4.5) {
				                    code = "#00FF00";
				                } else if (val < -4.5 && val > -6) {
				                    code = "#ff9900";
				                } else {
				                    code = "#ff0000";
				                }
				                annotations.update("background", {
				                    "fillColor": code
				                });
				            };
				        },
				        'renderComplete': function(evt, arg) {
				            evt.sender.updateAnnotation(evt, arg);
				        },
				        'realtimeUpdateComplete': function(evt, arg) {
				            evt.sender.updateAnnotation(evt, arg);
				        },
				        'disposed': function(evt, arg) {
				            clearInterval(evt.sender.dataUpdate);
				        }
				    }
				}
			);
			chartObj.render();
		});
		
		
		setInterval(function() {
        	//debugger;
        	
			$.get("/temperatureMonitoringRestController/temperature_Current",function(data){
				document.getElementById("progressb").innerHTML = data+"°";
				document.getElementById("progressb").setAttribute("aria-valuenow",data);
				document.getElementById("progressb").style.width = data+"%";
			});

        }, 3000);
	</script>
	
	<script type="text/javascript">
		setInterval(function() {
	    	
			$.get("workOrderStartBRest/workOrderStartInit?eqselect=m001",function(data){
				
				//console.log(data);

				if(data.length>0)
				{
					document.getElementById("workOrder_ONo").innerHTML = "작업지시번호 : "+data[0].workOrder_ONo;
				}
				else
				{
					document.getElementById("workOrder_ONo").innerHTML = "작업지시번호 : ";
				}
				
			});
	
	    }, 3000);

		/*
		setInterval(function() {
	    	var rand = Math.floor(Math.random() * ${Max_Value}) + ${Min_Value};
			rand += 0.5;
			
			
			$.get("/temperatureMonitoringRestController/temperature_Insert?equip=m001&value="+rand,function(data){
			});
			
	
	    }, 60000);
		*/
		var dds = [];

		setInterval(function() {

			$.get("/temperatureMonitoringRestController/temperature_Array",function(data){
				console.log(data);

				var categorys = [];
				var datas = [];
				dds = [];

				data.forEach(function(element){
					//console.log(element); // 0 1 2 3 4 5 6 7 8 9 10

					var category = {};
					category.label = element.temp_Time;
					categorys.push(category);

					var das = {};
					das.value = element.temp_Value;
					datas.push(das);

					var ddsv = [];

					//debugger;

					var dat = new Date(element.temp_Time);
					var year = dat.getFullYear();
					var month = dat.getMonth();
					var date = dat.getDate();
					var time = dat.getTime();

					var hours = ('0' + dat.getHours()).slice(-2); 
					var minutes = ('0' + dat.getMinutes()).slice(-2);

					ddsv.push(year+"-"+month+"-"+date+" "+hours+":"+minutes);
					ddsv.push(parseInt(element.temp_Value));
					ddsv.push(element.temp_Value);
					dds.push(ddsv);
				});

				console.log("음");
				console.log(dds);

				google.charts.load('current', {packages: ['corechart', 'line']});
				google.charts.setOnLoadCallback(drawBackgroundColor);

				FusionCharts.ready(function(){
					var chartObj = new FusionCharts({
						type: 'scrollline2d',
						dataFormat: 'json',
						renderAt: 'chart-container2',
						width: '90%',
						height: '85%',
						dataSource: {
							"chart": {
								"theme": "fusion",
								"caption": "온도 그래프",
								"subCaption": "",
								"xAxisName": "Time",
			            		"yAxisName": "Value",
								"numberPrefix": "",
								"lineThickness": "3",
								"flatScrollBars": "1",
								"scrollheight": "10",
								"numVisiblePlot": "12",
								"showHoverEffect": "1"
							},
							"categories": [{
								"category": categorys
							}],
							"dataset": [{
								"data": datas
							}]
						}
					});
					chartObj.render();
				});

				
			});
	    	
			

	    }, 10000);

		function drawBackgroundColor() {
			var data = new google.visualization.DataTable();
			data.addColumn('string', 'X');
			data.addColumn('number', '1호기 온도');
			data.addColumn({type:'string', role:'annotation'});

			data.addRows(dds);

			var options = {
				hAxis: {
				title: '시간'
				},
				vAxis: {
				title: '온도'
				},
				backgroundColor: '#f1f8e9'
			};

			var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
			chart.draw(data, options);
		}
	</script>
	
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	</head>
	<body>
		<div style="height: 10%; text-align: center;">
			<div class="page-header">
			  <h1>온도 모니터링<small id="workOrder_ONo">작업지시번호 : 20211115-A01-01</small></h1>
			</div>
		</div>
		
		<div style="height: 10%; text-align: center; padding:0px 100px 0px 100px;">
			<h1>현재 온도</h1>
			<div class="progress" style="height:100%; border: solid;">
			  <div id="progressb" class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="${Min_Value}" aria-valuemax="${Max_Value}" style="color:black; width: 60%; padding-top: 40px; font-size: 110px;">
			    60°
			  </div>
			</div>
		</div>

		<br/><br/>

		<div style="height: 80%; text-align: center; padding:0px 100px 0px 100px;">
			<h1>온도 그래프</h1>
			<div id="chart_div" style="height: 75%; border: solid;">잠시만 기다려주세요.</div>
		</div>
			
		
		<div style="height: 80%; display:none;">
			<div style="float: left; width: 30%; padding-left: 200px;">
				<div id="chart-container">FusionCharts XT will load here!</div>
			</div>
			
			<div style="float: left; width: 70%;">
				<div id="chart-container2">FusionCharts XT will load here!</div>
			</div>
		</div>
	</body>
</html>