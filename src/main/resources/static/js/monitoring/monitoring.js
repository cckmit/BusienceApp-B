function getMachine(){
	var ajaxResult = $.ajax({
		method: "GET",
		url: "machineManageRest/machineManageSelect",
	})
	return ajaxResult;
}

function setClock(){
	let nowTime = new Date();
	
	let year = nowTime.getFullYear(); // 년도
	
	let month = pluszero(nowTime.getMonth() + 1, 2);  // 월
	let day = pluszero(nowTime.getDate(), 2);  // 일
	
	let date = year + '-' + month + '-' + day;
	
	let hours = pluszero(nowTime.getHours(), 2); // 시
	let minutes = pluszero(nowTime.getMinutes(), 2);  // 분
	let seconds = pluszero(nowTime.getSeconds(), 2);  // 초
	
	let time = hours + ':' + minutes + ':' + seconds;
	document.getElementById("curentTime").innerHTML = date+" "+time
}

$(document).ready(function(){
	
	$.when(getMachine())
	.then(function(data){
		tagAdd(data)	
	})
	.then(function(){
		
		setClock();
		setInterval(setClock, 1000);
		setInterval(replaceData, 60000);
	})
})