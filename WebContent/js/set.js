function setPassword(){

	    var cover = "<div id='Cover1' onclick='btnClose_onclick1()'></div>";
	    $("body").append(cover);
	    var pop = "<div id='pop_cov1'></div>";
	    $("body").append(pop);
	    $("#pop_cov1").append("<div id='modlue1'>  " +
	        "<div class='sHeader'><span >设置</span>" +
	        "        <button id='scancle'onclick='return btnClose_onclick1()'>X</button>" +
	        "</div>" +"<div style='height:10%'> </div>"+
	        "        <div class='sname'>" +
	        "            <span  >账号:</span>" +
	        "            <input type='text'  id='username5'>" +
	        "        </div>" +
	        "        <div class='spassword' >" +
	        "            <span>更改密码:</span>" +
	        "           <input type='text' id='state' placeholder='请输入新密码'>" +
	        "        </div>" +
	        "        <div class='sspassword' >" +
	        "            <span>确认密码:</span>" +
	        "           <input type='text' id='state1'placeholder='请确认密码'>" +
	        "        </div>" +
	        "    <div class='MyBtn1'>" +
	        "        <button id='confirm1' onclick='updatepwd()'>确定</button>" +
	        "    </div>" +
	        "</div>"
	    );
		  setusername();
		}
function setusername(){

    $.ajax({
			type:"GET",
			url:"/compantchat/message/getuser",
			dataType: 'json',
			async:true,
			success : function(data) {
				document.getElementById("username5").value=data.username;
				alert("username:"+data.username);
			}
		});
}
function updatepwd(){
	var pwd=$("#state").val();
	var pwd2=$("#state1").val();
	alert("pwd:"+pwd);
	if(pwd!==null||pwd!==undefined||pwd!==''){
	if(pwd==pwd2){
		//var uid=null;
	$.ajax({
		type:"POST",
		url:"/compantchat/user/updatepwd",
		async:true, 
		data:"updatepwd="+pwd,
		dataType: 'json',
			
	});
	}else{
		alert("两次密码不一致请重新输入");
	}
	}else{
		alert("密码不能为空");
	}
	window.location.href="/compantchat/Login.html";
}

function btnClose_onclick1() {
	$("#Cover1").remove();
	$("#pop_cov1").remove();
}
