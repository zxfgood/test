function logout(){
	$.ajax({
		type:"GET",
		url:"/compantchat/user/logout",
		dataType: 'json',
		async:true,
		scriptCharset : 'utf-8',
	});
	window.location.href="/compantchat/Login.html";
};