function monitoring_status(machineCode){
	var status = {
		layoutColumnsOnNewData : true,
	    ajaxLoader:false,
	    ajaxURL:"workOrderTABRestXO/WOT_Search",
	    ajaxConfig:"get",
	    ajaxContentType:"json",
	    ajaxParams:{
	    	machineCode : machineCode,
	    	startDate : today.toISOString().substring(0, 10),
	    	endDate : tomorrow.toISOString().substring(0, 10),
	    	statusCodeArr : "245"
	    },
	    columns:[
	        { title:"작업지시번호", field:"workOrder_ONo", visible:false,},
	        { title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", headerSort:false},
	        { title: "생산", field: "workOrder_RQty", headerHozAlign: "center", align: "right",
				formatter:"money", formatterParams: {precision: false}, headerSort:false, bottomCalc:"sum"},
			{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center", headerSort:false,
				formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm" }},
			{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center", headerSort:false,
				formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm" }}
	    ]
	}
	return status;
}

var tables = []
var machineCodeArr = []

function replaceData(){
	tables.forEach(function(table){
	    table.replaceData();
	})
}

function tagAdd(data){
	var tag = ""
	var viewCount
	var minNumber
	
	//설비가 4대 이하면 4칸만, 5개 이상이면 8칸만 생성
	if(data.length < 5){
		viewCount = 4
		minNumber = Math.min(data.length, viewCount)
		for(let j=0;j<minNumber;j++){
			tag += '<div class="col-md-6 monitor_wrap">'
				+ '<h1>'+data[j].equipment_INFO_NAME+'</h1>'
				+ '<div id="proMonitoring_'+data[j].equipment_INFO_CODE+'" class="monitor"></div>'
				+ '</div>'
		}
		for(let j=minNumber;j<viewCount;j++){
			tag += '<div class="col-md-6 monitor_wrap">'
				+ '<h1>'+(j+1)+'호기</h1>'
				+ '<div id="proMonitoring_M00'+(j+1)+'" class="monitor"></div>'
				+ '</div>'
		}
	}else{
		viewCount = 8
		minNumber = Math.min(data.length, viewCount)
		for(let j=0;j<minNumber;j++){
			tag += '<div class="col-md-3 monitor_wrap">'
				+ '<h1>'+data[j].equipment_INFO_NAME+'</h1>'
				+ '<div id="proMonitoring_'+data[j].equipment_INFO_CODE+'" class="monitor"></div>'
				+ '</div>'
		}
		for(let j=minNumber;j<viewCount;j++){
			tag += '<div class="col-md-3 monitor_wrap">'
				+ '<h1>'+(j+1)+'호기</h1>'
				+ '<div id="proMonitoring_M00'+(j+1)+'" class="monitor"></div>'
				+ '</div>'
		}
	}
	
	$("#monitor_content").append(tag);	
		
	//데이터가 있는 설비명 수집
	for(let i=0;i<minNumber;i++){
		machineCodeArr.push(data[i].equipment_INFO_CODE)
	}
	
	//수집한 설비명을 기준으로 테이블 생성
	for(let k=0;k<machineCodeArr.length;k++){
			var table = new Tabulator("#proMonitoring_"+machineCodeArr[k], monitoring_status(machineCodeArr[k]));
			tables.push(table);
	}
	//더미테이블 생성
	for(let k=minNumber;k<viewCount;k++){
		new Tabulator("#proMonitoring_M00"+(k+1), monitoring_status(""));
	}
}