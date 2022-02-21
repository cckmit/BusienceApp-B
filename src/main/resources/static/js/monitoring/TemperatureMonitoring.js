//설비 선택박스 선택시 화면 변경
$("#machineCode").on("change", function(){
	location.href = "/TemperatureMonitoring?machineCode="+$("#machineCode").val();
});

//온오프 클릭시 작업지시 생성또는 제거
$("#Temperature_switch").click(function(){
	temperatureSwitch($(this).is(':checked'))	
})

function temperatureSwitch(value){
	console.log(value)
	var datas = {
		Equip_Code : $("#machineCode").val(),
		Equip_Status : !value
	} 
	var ajaxResult = $.ajax({
        method : "get",
		data : datas,
        url : "/updateTemperature",
        success : function(result) {
        }
	})
	return ajaxResult;
}

// 설비정보 가져와서 온오프확인
function selectEquipMonitoring(){
	var datas = {
		machineCode : $("#machineCode").val()
	}
	var ajaxResult = $.ajax({
        method : "get",
		data : datas,
        url : "/selectEquipMonitoring",
        success : function(result) {
        }
    })
	return ajaxResult;
}

function tempChartDatas(value){
	var datas = {
		OrderNo : value
	}
	var ajaxResult = $.ajax({
        method : "get",
		data : datas,
        url : "/tempChartDatas",
        success : function(result) {
			console.log(result)
			if(result != null){
				drawBackgroundColor(result)
			}else{
				alert("오류가 발생했습니다.")
			}
        },
		error : function(){
			$("#chart_div").val("데이터가 없습니다.")
		}
    })
	return ajaxResult;
}
function viewData(){
	$.when(selectEquipMonitoring())
	.then(function(data1){
		console.log(data1[0]);
		$("#Temperature_switch").prop("checked", data1[0].equip_Status);
		document.getElementById("progressb").innerHTML = data1[0].temp+"°C";
		document.getElementById("progressb").setAttribute("aria-valuenow",data1[0].temp);
		document.getElementById("progressb").style.width = data1[0].temp+"%";
		tempChartDatas(data1[0].equip_No)
	})
}

function drawBackgroundColor(jsonData) {
	//작업지시번호를 가져와서
	//해당 지시의 리스트 가져옴
	//처음시간과 끝시간을 파악한후
	//7개표시 할수 있도록 나눔
	
	google.charts.load('current', {'packages':['corechart']});
	google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
		var tempJson = tempJsonData(jsonData)
        var data = google.visualization.arrayToDataTable(tempJson.dblArray);

        var options = {
				hAxis: {
					title: "시간, 평균온도 : "+tempJson.dblArray[1][3]+"°C"
				},
				vAxis: {
					title: "온도",
					maxValue: tempJson.maxTemp+1,
					minValue: tempJson.minTemp-1
				},
				backgroundColor: '#f1f8e9'
			};
			
        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));

        chart.draw(data, options);
      }
}
function tempJsonData(jsonData){
	const dblChartArray = new Array();
	dblChartArray.push(['time', $("#machineCode option:selected").text(), {type: 'number', role: 'annotation'}, '평균온도'])
	
	var avgTemp = 0;
		
	for(let j=0;j<jsonData.length;j++){
		avgTemp += jsonData[j].chartTemp
	}
	var maxTemp = Math.round(avgTemp/jsonData.length*10)/10;
	var minTemp = Math.round(avgTemp/jsonData.length*10)/10;
	
	for(let i=0;i<jsonData.length;i++){

		dblChartArray.push(
			[jsonData[i].chartTime,
			Math.round(jsonData[i].chartTemp*10)/10, Math.round(jsonData[i].chartTemp*10)/10,
			Math.round(avgTemp/jsonData.length*10)/10]
		)
		maxTemp = Math.max(Math.round(jsonData[i].chartTemp*10)/10,maxTemp);
		minTemp = Math.min(Math.round(jsonData[i].chartTemp*10)/10,minTemp);
	}
	return {
		"dblArray" : dblChartArray,
		"maxTemp" : maxTemp,
		"minTemp" : minTemp
	}
}
window.onload = function(){
	viewData();
	setInterval(function(){
		viewData();
	},5000);
}