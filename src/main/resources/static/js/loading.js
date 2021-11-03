$( document ).ready(function(){
	const spinner = '<div class="spinner-bg"><div class="spinner"><div class="lds-ring">'
    	+'<div></div><div></div><div></div><div></div></div></div></div>'
    $('body').append(spinner);
})
.ajaxStart(function() {
 console.log('ajax start');
      $('body>.spinner-bg').show();
})
.ajaxStop(function() {
 console.log('ajax end');     
       $('body>.spinner-bg').hide();
});