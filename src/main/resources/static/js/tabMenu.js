$(document).ready(function() {

	//처음 페이지가 실행됬을때 보여주는 값
	list = $(this).index();
	$('#content > ol > li').hide();
	$('#content > ol > li').eq(list).show();

	//버튼을 클릭했을때 일치하는 페이지를 띄운다.
	$("ul#navigation li").click(function() {

		list = $(this).index();
		$("ul#navigation li").removeClass("selected");
		$(this).addClass("selected");

		//나머지 감주고 보여줌
		$('#content > ol > li').hide();
        $("#content > ol > li").removeClass("current");

		$('#content > ol > li').eq(list).show();
		$('#content > ol > li').eq(list).addClass("current");
		
		return false;
	});
	//페이지가 완료되면 클릭이벤트 발동 
	$(".selected").trigger("click");
});