<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<input type="checkbox" style="width: 100px;" onclick="checkchange()">
	
	<script type="text/javascript">
		var check = false;
		
		function checkchange(){
			check = !check;
		} 
		
		function randomNum(min, max){
			var randNum = Math.floor(Math.random()*(max-min+1)) + min;
			return randNum;
		}

		setInterval(function() {
	    	
			console.log(check);
			
			if(check)
			{
				var rand = randomNum(0, 100);
				
				$.get("/temperatureMonitoringRestController/tablet/temperature_Insert?equip=m001&value="+rand,function(data){
				});
			}
	    }, 1000);
	</script>
</body>
</html>