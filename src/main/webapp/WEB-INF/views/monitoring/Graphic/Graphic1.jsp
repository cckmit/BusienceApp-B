<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!doctype html>
<html lang="en">

<head>
	<title>Dashboard | Klorofil - Free Bootstrap Dashboard Template</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

	<style type="text/css">
	html, body {
	    margin: 0;
	    height: 100%;
	    overflow: hidden;
	}
	</style>

</head>

<body>
	
	<div class="row h-50" style="padding: 10px;">
	
		<%
			for(int i=0;i<2;i++)
			{
		%>
		<div class="col-lg-6">
		
			<div class="card border-dark h-100" id="<%=i+1%>_card">
			  <div class="card-header bg-transparent border-dark">
			  	<table style="width: 100%;">
			  		<tr>
			  			<td style="width: 20%;"><h1><%=i+1%>호기</h1></td>
			  			<td style="text-align: right; width: 20%;"><h1 id="<%=i+1%>h">ON</h1></td>
			  			<td class="time" style="text-align: right; font-size: 40px; width: 40%;"></td>
			  		</tr>
			  	</table>
			  </div>
			  <div class="card-body">
			    <h5 class="card-title">
			    	<table style="width: 88%;">
			    		<tr>
			    			<td style="width: 33%;">
			    				<p style="font-size: 40px; text-align: center;" id="<%=i+1%>_temp">0°</p>
			    			</td>
			    			<td style="width: 33%;">
			    				<p style="font-size: 40px; text-align: center;" id="<%=i+1%>_humi">0°</p>
			    			</td>
			    			<td style="width: 33%;">
			    				<p style="font-size: 40px; text-align: center;" id="<%=i+1%>_speed">0m/s</p>
			    			</td>
			    		</tr>
			    	</table>
			    </h5>
			    <p class="card-text">
			    	<div id="chart_div<%=i+1%>" style="width: 400px; height: 120px; text-align: center;"></div>
			    </p>
			    
			  </div>
			</div>
			
		</div>
		<%
			}
		%>
	
	</div>
	
	<div class="row h-50" style="padding: 10px;">
	
		<%
			for(int i=2;i<4;i++)
			{
		%>
		<div class="col-lg-6">
		
			<div class="card border-dark h-100" id="<%=i+1%>_card">
			  <div class="card-header bg-transparent border-dark">
			  	
			  	<table style="width: 100%;">
			  		<tr>
			  			<td style="width: 20%;"><h1><%=i+1%>호기</h1></td>
			  			<td style="text-align: right; width: 20%;"><h1 id="<%=i+1%>h">ON</h1></td>
			  			<td class="time" style="text-align: right; font-size: 40px; width: 40%;"></td>
			  		</tr>
			  	</table>
			  </div>
			  <div class="card-body">
			    <h5 class="card-title">
			    	<table style="width: 88%;">
			    		<tr>
			    			<td style="width: 33%;">
			    				<p style="font-size: 40px; text-align: center;" id="<%=i+1%>_temp">0°</p>
			    			</td>
			    			<td style="width: 33%;">
			    				<p style="font-size: 40px; text-align: center;" id="<%=i+1%>_humi">0°</p>
			    			</td>
			    			<td style="width: 33%;">
			    				<p style="font-size: 40px; text-align: center;" id="<%=i+1%>_speed">0m/s</p>
			    			</td>
			    		</tr>
			    	</table>
			    </h5>
			    <p class="card-text">
			    	<div id="chart_div<%=i+1%>" style="width: 400px; height: 120px; text-align: center;"></div>
			    </p>
			  </div>
			</div>
			
		</div>
		<%
			}
		%>
	
	</div>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script src="${contextPath}/resources/assets/vendor/jquery/jquery.min.js"></script>
	
   <script type="text/javascript">
	  setInterval(function(){
		  Time_Update();
	  },1000);
	  
	  function Time_Update(){
		  var today = new Date();

		  var year = today.getFullYear();
		  var month = ('0' + (today.getMonth() + 1)).slice(-2);
		  var day = ('0' + today.getDate()).slice(-2);

		  var dateString = year + '-' + month  + '-' + day;
		  
		  var hours = ('0' + today.getHours()).slice(-2); 
		  var minutes = ('0' + today.getMinutes()).slice(-2);
		  var seconds = ('0' + today.getSeconds()).slice(-2); 

		  var timeString = hours + ':' + minutes  + ':' + seconds;
		  
		  //console.log(dateString+" "+timeString);
		  
		  var time = document.getElementsByClassName("time");
		  
		  for(i=0;i<time.length;i++)
			  time[i].innerHTML = dateString+" "+timeString;
	  }
   
      google.charts.load('current', {'packages':['gauge']});
      
	  var chartvalue1 = 0,chartvalue2=0,chartvalue3=0;
      
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
	          yellowFrom:75, yellowTo: 90,
	          minorTicks: 5
	        };
	
	        chart1 = new google.visualization.Gauge(document.getElementById('chart_div1'));
	
	        chart1Data.setValue(0, 1, chartvalue1);
	        chart1Data.setValue(1, 1, chartvalue2);
	        chart1Data.setValue(2, 1, chartvalue3);
	        chart1.draw(chart1Data, options);
        
	        $.get("GraphicRest/hogi_time_select?num=M001",function(jqXHR) {
	  		  if(jqXHR[0]>2)
	  		  {
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
	  		  else
	  		  {
	  			  $.get("GraphicRest/hogi_select?num=M001",function(jqXHR) {
	  				  document.getElementById('1_card').style.background = 'green';  
	  				  
	  	    		    chartvalue1 = 0;
	  	    		    chartvalue2 = 0;
	  	    		    chartvalue3 = 0;
	  	    		    chartvalue1 = jqXHR[0].temp;
	  	    	    	chartvalue2 = jqXHR[0].humi;
	  	    	    	chartvalue3 = jqXHR[0].speed;
	  	    	    	
	  	    	    	document.getElementById('1_temp').innerHTML = jqXHR[0].temp+"°";
	  	    	    	document.getElementById('1_humi').innerHTML = jqXHR[0].humi+"°";
	  	    	    	document.getElementById('1_speed').innerHTML = jqXHR[0].speed+"m/s";
	  	    	    	
	  	    	    	//google.charts.setOnLoadCallback(drawChart1);
	  	    	    	document.getElementById('1h').innerHTML = 'ON';
	  	    	  },'json' /* xml, text, script, html */)
	  	    	  .done(function(jqXHR) {
	  	    		//alert("second success" );
	  	    	  })
	  		  }
	  	  });
      }
      google.charts.setOnLoadCallback(drawChart1);
      
	  var chartvalue12 = 0,chartvalue22=0,chartvalue32=0;
      
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
	          yellowFrom:75, yellowTo: 90,
	          minorTicks: 5
	        };
	
	        chart1 = new google.visualization.Gauge(document.getElementById('chart_div2'));
	
	        chart1Data.setValue(0, 1, chartvalue12);
	        chart1Data.setValue(1, 1, chartvalue22);
	        chart1Data.setValue(2, 1, chartvalue32);
	        chart1.draw(chart1Data, options);
        
	        $.get("GraphicRest/hogi_time_select?num=M002",function(jqXHR) {
	  		  if(jqXHR[0]>2)
	  		  {
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
	  		  else
	  		  {
	  			  $.get("GraphicRest/hogi_select?num=M002",function(jqXHR) {
	  				  	document.getElementById('2_card').style.background = 'green';  
	  				  
	  	    		    chartvalue12 = 0;
	  	    		    chartvalue22 = 0;
	  	    		    chartvalue32 = 0;
	  	    		    chartvalue12 = jqXHR[0].temp;
	  	    	    	chartvalue22 = jqXHR[0].humi;
	  	    	    	chartvalue32 = jqXHR[0].speed;
	  	    	    	
	  	    	    	document.getElementById('2_temp').innerHTML = jqXHR[0].temp+"°";
	  	    	    	document.getElementById('2_humi').innerHTML = jqXHR[0].humi+"°";
	  	    	    	document.getElementById('2_speed').innerHTML = jqXHR[0].speed+"m/s";
	  	    	    	
	  	    	    	//google.charts.setOnLoadCallback(drawChart2);
	  	    	    	document.getElementById('2h').innerHTML = 'ON';
	  	    	  },'json' /* xml, text, script, html */)
	  	    	  .done(function(jqXHR) {
	  	    		//alert("second success" );
	  	    	  })
	  		  }
	  	  });
      }
      google.charts.setOnLoadCallback(drawChart2);
      
	var chartvalue13 = 0,chartvalue23=0,chartvalue33=0;
      
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
          yellowFrom:75, yellowTo: 90,
          minorTicks: 5
        };

        chart1 = new google.visualization.Gauge(document.getElementById('chart_div3'));

        chart1Data.setValue(0, 1, chartvalue13);
        chart1Data.setValue(1, 1, chartvalue23);
        chart1Data.setValue(2, 1, chartvalue33);
        chart1.draw(chart1Data, options);
        
        	$.get("GraphicRest/hogi_time_select?num=M003",function(jqXHR) {
	  		  if(jqXHR[0]>2)
	  		  {
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
	  		  else
	  		  {
	  			  $.get("GraphicRest/hogi_select?num=M003",function(jqXHR) {
	  				  	document.getElementById('3_card').style.background = 'green';  
	  				  
	  	    		    chartvalue13 = 0;
	  	    		    chartvalue23 = 0;
	  	    		    chartvalue33 = 0;
	  	    		    chartvalue13 = jqXHR[0].temp;
	  	    	    	chartvalue23 = jqXHR[0].humi;
	  	    	    	chartvalue33 = jqXHR[0].speed;
	  	    	    	
	  	    	    	document.getElementById('3_temp').innerHTML = jqXHR[0].temp+"°";
	  	    	    	document.getElementById('3_humi').innerHTML = jqXHR[0].humi+"°";
	  	    	    	document.getElementById('3_speed').innerHTML = jqXHR[0].speed+"m/s";
	  	    	    	
	  	    	    	//google.charts.setOnLoadCallback(drawChart2);
	  	    	    	document.getElementById('3h').innerHTML = 'ON';
	  	    	  },'json' /* xml, text, script, html */)
	  	    	  .done(function(jqXHR) {
	  	    		//alert("second success" );
	  	    	  })
	  		  }
	  	  });
      }
      google.charts.setOnLoadCallback(drawChart3);
      
	var chartvalue14 = 0,chartvalue24=0,chartvalue34=0;
      
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
          yellowFrom:75, yellowTo: 90,
          minorTicks: 5
        };

        chart1 = new google.visualization.Gauge(document.getElementById('chart_div4'));

        chart1Data.setValue(0, 1, chartvalue14);
        chart1Data.setValue(1, 1, chartvalue24);
        chart1Data.setValue(2, 1, chartvalue34);
        chart1.draw(chart1Data, options);
        
        	$.get("GraphicRest/hogi_time_select?num=M004",function(jqXHR) {
	  		  if(jqXHR[0]>2)
	  		  {
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
	  		  else
	  		  {
	  			  $.get("GraphicRest/hogi_select?num=M004",function(jqXHR) {
	  				  	document.getElementById('4_card').style.background = 'green';  
	  				  
	  	    		    chartvalue14 = 0;
	  	    		    chartvalue24 = 0;
	  	    		    chartvalue34 = 0;
	  	    		    chartvalue14 = jqXHR[0].temp;
	  	    	    	chartvalue24 = jqXHR[0].humi;
	  	    	    	chartvalue34 = jqXHR[0].speed;
	  	    	    	
	  	    	    	document.getElementById('4_temp').innerHTML = jqXHR[0].temp+"°";
	  	    	    	document.getElementById('4_humi').innerHTML = jqXHR[0].humi+"°";
	  	    	    	document.getElementById('4_speed').innerHTML = jqXHR[0].speed+"m/s";
	  	    	    	
	  	    	    	//google.charts.setOnLoadCallback(drawChart2);
	  	    	    	document.getElementById('4h').innerHTML = 'ON';
	  	    	  },'json' /* xml, text, script, html */)
	  	    	  .done(function(jqXHR) {
	  	    		//alert("second success" );
	  	    	  })
	  		  }
	  	  });
      }
      google.charts.setOnLoadCallback(drawChart4);
      
      setInterval(function(){
        google.charts.setOnLoadCallback(drawChart1);
      	  
      	google.charts.setOnLoadCallback(drawChart2);
      	  
      	google.charts.setOnLoadCallback(drawChart3);
      	
      	google.charts.setOnLoadCallback(drawChart4);
      },1000);
      
      setTimeout(function() {
    	  //window.location.href = './Graphic2';
      }, 5000);
    </script>
</body> 

</html>