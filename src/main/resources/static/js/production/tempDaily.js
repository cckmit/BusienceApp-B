
function Search() {
	proMachineTable.setData("/tempDailyRest/tempListSelect",
		{startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		machineCode: $("#machineCode").val(),
		condition: $("#condition").val()})
	.then(function(){
		proMachineTable.setColumns(customColumns($("#condition").val()))
	})
}


$('#SearchBtn').click(function(){
	Search();
})

var proMachineTable = new Tabulator("#tempDailyTable", {
	layoutColumnsOnNewData : true,
	height:"calc(100% - 175px)",
 	columns:customColumns($("#condition").val())
});

function customColumns(value){
	console.log(value)
	var list = [
	{title:"순번", field:"rownum", formatter:"rownum", align: "center"},
	{title:"월", field:"temp_Monthly", headerHozAlign:"center", align: "right",
		formatter: function(cell){return cell.getValue()+"월"}}
 	]
	if(value == "daily"){
		list.push(
			{title:"일", field:"temp_Daily", headerHozAlign:"center", align: "right",
				formatter: function(cell){return cell.getValue()+"일"}}
		)	
	}else if(value == "hourly"){
		list.push(
			{title:"일", field:"temp_Daily", headerHozAlign:"center", align: "right",
				formatter: function(cell){return cell.getValue()+"일"}},
			{title:"시간", field:"temp_Hourly", headerHozAlign:"center", align: "right",
				formatter: function(cell){return cell.getValue()+"시"}}
		)	
	}

	list.push(
	 	{title:"장소", field:"temp_EquipName", headerHozAlign:"center"},
		{title:"평균온도", field:"temp_Value", headerHozAlign:"center", align: "right"}
	)
	return list;
}
