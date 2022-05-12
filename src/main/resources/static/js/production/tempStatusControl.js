function equip_stat_change(check){
		alert();
	
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