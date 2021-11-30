<%@page import="com.busience.standard.dto.Equip_Monitoring_TBL"%>
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

<style>
body{
}
input[type="checkbox"]{
	width: 130px;
	height: 48px;
	display:block;
	background-image:url(../images/button/Back1.png);
	-webkit-appearance:none;
	-webkit-transition:1s;
	padding:3px 4px 3px 4px;
}
input[type="checkbox"]:after{
	content:'';
	display:block;
	position:relative;
	top:0;
	left:0;
	width: 60px;
	height: 44px;
	border-radius: 8px; /* from vector shape */
	background-image:url(../images/button/Knob.png);
	color: #f9f3b6;
}
input[type="checkbox"]:checked {
    	padding-left: 67px;
    	padding-right: 0;
	background-image:url(../images/button/Back2.png);
}
input[type="checkbox"]:hover {
	opacity:1;
}

</style>

<script type="text/javascript" src="https://cdn.fusioncharts.com/fusioncharts/latest/fusioncharts.js"></script>
	<script type="text/javascript" src="https://cdn.fusioncharts.com/fusioncharts/latest/themes/fusioncharts.theme.fusion.js"></script>
	<script type="text/javascript">
		
		
		setInterval(function() {
        	//debugger;
        	
			$.get("/temperatureMonitoringRestController/temperature_Current",function(data){
				
				
				if(data == "NONE")
				{
					document.getElementById("progressb").innerHTML = data;
					document.getElementById("progressb").setAttribute("aria-valuenow",0);
					document.getElementById("progressb").style.width = 0+"%";
				}
				else
				{
					document.getElementById("progressb").innerHTML = data+"°";
					document.getElementById("progressb").setAttribute("aria-valuenow",data);
					document.getElementById("progressb").style.width = data+"%";
				}
				
			});

        }, 3000);
	</script>
	
	<script type="text/javascript">
	
		/*
		setInterval(function() {
	    	var rand = Math.floor(Math.random() * 0) + 94;
			rand += 0.5;
			
			
			$.get("/temperatureMonitoringRestController/tablet/temperature_Insert?equip=m001&value="+rand,function(data){
			});
			
	
	    }, 1000);
		*/
		
	
		
		var dds = [];

		setInterval(function() {

			/*
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
					//ddsv.push(parseInt(element.temp_Value));

					value = parseInt(element.temp_Value);

					ddsv.push(value);
					ddsv.push(element.temp_Value);
					dds.push(ddsv);
				});

				console.log("음");
				console.log(dds);

				google.charts.load('current', {packages: ['corechart', 'line']});
				google.charts.setOnLoadCallback(drawBackgroundColor);				
			});
			*/
			
			$.get("/temperatureMonitoringRestController/temperature_Equip_No_Select",function(vno){
				if(vno=="NONE")
				{
					dds = [];
					
					// Load the Visualization API and the corechart package.
					google.charts.load('current', {'packages':['corechart']});
			
					// Set a callback to run when the Google Visualization API is loaded.
					google.charts.setOnLoadCallback(drawBackgroundColor);
				}
				else
				{
					$.get( "/tempDailyRest/History_DetailView?Temp_No="+vno.split("/")[1],function(data){
			    		console.log(data);
			    		
			    		dds = [];
			    		
			    		for(i=0;i<data.length;i++)
			    		{
			    			var ddsv = [];
			    			
							ddsv.push(data[i].startTime);

							value = parseInt(data[i].temp_Value);

							vvalue = parseInt(vno.split("/")[0]);
							
							ddsv.push(vvalue);
							
							ddsv.push(value);
							ddsv.push(value);
							
							htitle = "시간 , 평균온도 : "+vvalue;
							console.log(htitle);
							
							dds.push(ddsv);
			    		}
			    		
			    		// Load the Visualization API and the corechart package.
						google.charts.load('current', {'packages':['corechart']});
				
						// Set a callback to run when the Google Visualization API is loaded.
						google.charts.setOnLoadCallback(drawBackgroundColor);
			    	});
				}
			});
			

	    }, 10000);

		var vminValue=0,vmaxValue=0;
		var htitle = "온도";
		
		function drawBackgroundColor() {
			var data = new google.visualization.DataTable();
			data.addColumn('string', 'X');
			
			data.addColumn('number', '1호기 평균 온도');
			
			data.addColumn('number', '1호기 온도');
			data.addColumn({type:'number', role:'annotation'});
			

			data.addRows(dds);

			var options = {
				hAxis: {
				title: htitle
				},
				vAxis: {
				title: "온도"
				,maxValue:vmaxValue,minValue:vminValue
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
			  <h1>온도 모니터링</h1>
			  
			  <%
			  		Equip_Monitoring_TBL data = (Equip_Monitoring_TBL)request.getAttribute("data");	
			  
			  		if(data.getEquip_Status().equals("true"))
			  		{
			 %>
			 			<div class="sample">
			 				<center>
						  	<div class="checker">
						        	<input type="checkbox" id="<%=data.getEquip_Code()%>" checked="checked" onclick="equip_stat_change(this)"/>
						  	</div>
						  	</center>
						</div>
			<%			
			  		}
			  		else
			  		{
			  %>
			  			<div class="sample">
			  				<center>
						  	<div class="checker">
						        	<input type="checkbox" id="<%=data.getEquip_Code()%>" onclick="equip_stat_change(this)"/>
						  	</div>
						  	</center>
						</div>
			  <%
			  		}
			  %>
			</div>
		</div>
		
		<div style="height: 7%; text-align: center; padding:0px 100px 0px 100px;">
			<h1>현재 온도</h1>
			<div class="progress" style="height:100%; border: solid;">
			  <div id="progressb" class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="${Min_Value}" aria-valuemax="${Max_Value}" style="color:black; width: 0%; padding-top: 20px; font-size: 70px;">
			    0°
			  </div>
			</div>
		</div>

		<br/><br/>

		<div style="height: 70%; text-align: center; padding:0px 100px 0px 100px;">
			<h1>온도 그래프</h1>
			<div id="chart_div" style="height: 75%; border: solid;">잠시만 기다려주세요.</div>
		</div>

		<script type="text/javascript">
		function equip_stat_change(check){
			data = {
				id : check.id,
				checked : check.checked
			};

			if(check.checked)
			{
				$.get("../tempStatusControlRest/tempStatusOnChange",data,function(){});
			}
			else
			{
				$.get("../tempStatusControlRest/tempStatusOffChange",data,function(){});
			}
		}
		</script>
	</body>
</html>