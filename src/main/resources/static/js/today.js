var today = new Date();
	
var tomorrow = new Date();
tomorrow = new Date(tomorrow.setDate(tomorrow.getDate()+1));

var lastweek = new Date();
lastweek = new Date(lastweek.setDate(lastweek.getDate()-7));

var endWeek = new Date();
endWeek = new Date(endWeek.setDate(endWeek.getDate()+6));

var nextmonth = new Date();
nextmonth = new Date(nextmonth.setMonth(nextmonth.getMonth()+1));

$(document).ready(function(){
	//오늘
	if($('.today') != null){
		$('.today').attr('value',today.toISOString().substring(0, 10))
	}
	//내일
	if($('.tomorrow') != null){
		$('.tomorrow').attr('value',tomorrow.toISOString().substring(0, 10))
	}
	//7일전
	if($('.lastweek') != null){
		$('.lastweek').attr('value',lastweek.toISOString().substring(0, 10))
	}
	//이번 달
	if($('.this_month') != null){
		$('.this_month').attr('value',today.toISOString().substring(0, 7))
	}
	
	//다음 달
	if($('.next_month') != null){
		$('.next_month').attr('value',nextmonth.toISOString().substring(0, 7))
	}
	
	// 올해
	if($('.this_year') != null){
		$('.this_year').attr('value',today.toISOString().substring(0, 4))
	}
})