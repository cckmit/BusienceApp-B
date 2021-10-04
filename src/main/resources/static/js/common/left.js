 $(".sidebar-busience .sidebar-menu").on("click","a", function() {
    $(".sidebar-busience li a").removeClass("active");
    $(this).addClass("active");
});
console.log("위임")
var parents_sidebar_menu
var child_sidebar_menu

$.ajax({
	method: "GET",
	url: "parentMenuSelect",
	success: function(data) {
		parents_sidebar_menu = data
		
		$.ajax({
			method: "GET",
			url: "childMenuSelect",
			success: function(data) {
				child_sidebar_menu = data
				console.log(parents_sidebar_menu);
				console.log(child_sidebar_menu);
				$(".sidebar-busience .sidebar-menu").append(
					
					dynamic_sidebar_menu(parents_sidebar_menu, child_sidebar_menu)
				)
			}
		});
	}
});

function dynamic_sidebar_menu(parents, child){
	var tag = ""
	for(let i=0;i<parents.length;i++){
		tag += '<li>'
				+'<a href="#subPages"'+parents[i].child_TBL_NUM+' data-toggle="collapse" class="collapsed"><span>'+parents[i].child_TBL_TYPE+'</span> <i class="icon-submenu lnr lnr-chevron-left"></i></a>'
					+'<ul id="subPages"'+parents[i].child_TBL_NUM+' class="collapse">'
					
		for(let j=0;j<child.length;j++){
			let menu_num = child[j].child_TBL_RMARK.split('/')
			if(menu_num[1] == parents[i].child_TBL_NUM){
				tag += '<li><a href="/'+menu_num[0]+'"><span>'+child[j].child_TBL_TYPE+'</span></a></li>'
			}
		}
						
		tag +="</ul>"
			+"</li>"
	}
	return tag
}