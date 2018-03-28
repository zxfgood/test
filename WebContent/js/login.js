
	$(document).ready(function() {
		$("#btn_login").click(function() {
			var username=$("#name").val();
			var password=$("#pwd").val();
			$.ajax({
				type:"POST",
				url:'/compantchat/user/login',
				async:true, 
				data:"username="+username+"&password="+password,
				dataType: 'json',
				 success: function(data) {
					 //alert(data);
					 //alert(data.username);
					// alert(data.password);
					 //alert(data.result);
					 var name=data.username;
					 var password=data.username;
					 if(data.result=="sucess"){
					 window.location.href="/compantchat/user/scucessdo";
					 }else{
						 window.location.href="/compantchat/user/faildo";
						 }
				} 
				});
		});
	});
