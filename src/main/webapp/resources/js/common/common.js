/**
 * 
 */
function changeVerifyCode(img){
	img.src="../Kaptcha?"+Math.floor(Math.random() * 100);
}


function getQueryString(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	alert(r);
	if(r != null){
		var a = decodeURIComponent(r[2]);
	
		return a;
	}
	return '';
	
}