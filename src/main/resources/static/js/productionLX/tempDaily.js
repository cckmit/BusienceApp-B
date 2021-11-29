dds = [];

function Search() {
	if ($('#startDate').val().length < 10) {
		alert("시작일은 반드시 입력하여 주십시오.");
		return;
	}
3
	if ($('#endDate').val().length < 10) {
		alert("끝일은 반드시 입력하여 주십시오.");
		return;
	}

	var jsonData = {
		startDate : $('#startDate').val(),
		endDate : $('#endDate').val(),
		EQUIPMENT_INFO_CODE : $('#EQUIPMENT_INFO_CODE').val(),
		EQUIPMENT_INFO_NAME : $('#EQUIPMENT_INFO_NAME').val()
	}

	$.get( "/tempDailyRest/History_Daily_List", jsonData )
	.done(function( data ) {
		console.log(data);

		proMachineTable.setData(data);

		/*
		dds = [];

		data.forEach(function(element){
			var dat = new Date(element.temp_Time);
				var ddsv = [];

				var year = dat.getFullYear();
				var month = dat.getMonth();
				var date = dat.getDate();
				var time = dat.getTime();

				var hours = ('0' + dat.getHours()).slice(-2); 
				var minutes = ('0' + dat.getMinutes()).slice(-2);

				ddsv.push(year+"-"+month+"-"+date);

				value = parseInt(element.temp_Value);

				ddsv.push(value);
				ddsv.push(element.temp_Value);
				dds.push(ddsv);
		})

		// Load the Visualization API and the corechart package.
		google.charts.load('current', {'packages':['corechart']});

		// Set a callback to run when the Google Visualization API is loaded.
		google.charts.setOnLoadCallback(drawBackgroundColor);
		*/
	});

}


$('#SearchBtn').click(function(){
	Search();
})

$('#EQUIPMENT_INFO_NAME').keypress(function(e){
	if(e.keyCode==13) {
		var value = $(this).val()
		//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
		$.ajax({
			method: "GET",
			url: "product_check?PRODUCT_ITEM_CODE=" + value,
			dataType: "json",
			success: function(data) {
				if (data.length == 1) {
					//검색어와 일치하는값이 있는경우
					$('#EQUIPMENT_INFO_CODE').val(data[0].equipment_INFO_CODE)
					$('#EQUIPMENT_INFO_NAME').val(data[0].equipment_INFO_NAME)
				} else {
					//검색어와 일치하는값이 없는경우, 팝업창
					machinePopup(value,'input','','sales')
				}
			}
		})
	}
})

var proMachineTable = new Tabulator("#proMachineTable", {
	//페이징
	pagination:"local",
	paginationSize:20,
	layoutColumnsOnNewData : true,
    //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().production_WorkOrder_ONo == "Sub Total"){
            row.getElement().style.backgroundColor = "#D8D8D8";
            }
    },
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"temp_No", headerHozAlign:"center"},
 	{title:"시작일자", field:"startTime", headerHozAlign:"center"},
	{title:"종료일자", field:"endTime", headerHozAlign:"center"},
	{title:"온도", field:"temp_Value", headerHozAlign:"center", align: "right"}
 	],
});

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