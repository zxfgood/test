  window.onload=function(){
	 show();
	 tick();
	 hide();

      };
  function tick() {
	    var years,months,days,hours, minutes, seconds;
	    var intYears,intMonths,intDays,intHours, intMinutes, intSeconds;
	    var today;
	    today = new Date(); //系统当前时间
	    intYears = today.getFullYear(); //得到年份,getFullYear()比getYear()更普适
	    intMonths = today.getMonth() + 1; //得到月份，要加1
	    intDays = today.getDate(); //得到日期
	    intHours = today.getHours(); //得到小时
	    intMinutes = today.getMinutes(); //得到分钟
	    intSeconds = today.getSeconds(); //得到秒钟
	    years = intYears + "年";
	    if(intMonths < 10 ){
	        months = "0" + intMonths +"月";
	    } else {
	        months = intMonths +"月";
	    }
	    if(intDays < 10 ){
	        days = "0" + intDays +"日 ";
	    } else {
	        days = intDays + "日  ";
	    }
	    if (intHours == 0) {
	        hours = "00:";
	    } else if (intHours < 10) {
	        hours = "0" + intHours+":";
	    } else {
	        hours = intHours + ":";
	    }
	    if (intMinutes < 10) {
	        minutes = "0"+intMinutes+":";
	    } else {
	        minutes = intMinutes+":";
	    }
	    if (intSeconds < 10) {
	        seconds = "0"+intSeconds+" ";
	    } else {
	        seconds = intSeconds+" ";
	    }
	    timeString = years+months+days+hours+minutes+seconds;
	    Clock.innerHTML = timeString;
	    window.setTimeout("tick();", 1000);
	    
	}
      function deleteuser(uid) {
         // alert("执行了删除")
    	  $.ajax({
  			type:"POST",
  			url:'/compantchat/manger/deleteuser',
  			//async:false, 
  			dataType: 'json',
  			data:"uid="+uid,
  			 success: function() {
  	  			 alert("5455445");
  				 //window.location.href="/compantchat/manger/mindex";
  				location.reload();
  			}
      	}); 	
      	window.location.reload();
	}
      function deleteuser(uid) {
          // alert("执行了删除")
     	  $.ajax({
   			type:"POST",
   			url:'/compantchat/manger/deleteuser',
   			//async:false, 
   			dataType: 'json',
   			data:"uid="+uid,
   			 success: function() {
   	  			 //alert("5455445");
   				 //window.location.href="/compantchat/manger/mindex";
   				location.reload();
   			}
       	}); 	
       	window.location.reload();
 	}
      function selectuser() {
          // alert("执行了删除")
          $("table ").remove("#userlist");
          $("#right_mid").append('<table  id="userlist"><tr class="tr1">'
                  +'<td  class="td1" ><input type="checkbox"></td>'
                  +'<td  class="td2">用户名</td>'
                  +'<td  class="td3" >科室</td>'
                  +'<td  class="td4">密码</td>'
                  +'<td class="td5" >真实姓名</td>'
                  +'<td class="td6" >操作</td>'
                  +'</tr></table>');
          var username=$("#username").val();
          alert("usernaem:"+username);
     	  $.ajax({
   			type:"POST",
   			url:'/compantchat/manger/finduser',
   			//async:false, 
   			dataType: 'json',
   			data:"username="+username,
   			 success: function(data) {
   				var userlist=data;
   			 for(var i=0;i<userlist.length;i++){
				 var user=userlist[i];
				 var uid=user.uid;
				 var username=user.username;
				 alert("ajax:"+username);
				 var dept=user.dept;
				 var password=user.password;
				 var realname=user.realname;
				 var tr ='<tr class="tr">'+'<td  class="td1" ><input type="checkbox"></td>'+'<td class="td2">'+username+'</td>' 
				   +'<td class="td3">'+dept+'</td>'
	    		   +'<td class="td4">'+password+'</td>'+'<td class="td5" >'+realname+'</td>'
	    		   +'<td class="td6"><a onclick= deleteuser("'+uid+'")><img src="../image/05-用户管理-操作-删除2.png"><span>删除用户</span></a></td>'	    		  				   
	    		   +'</tr>';
    			$("#userlist").append(tr);
				 }
   			 }
   			});
       	} 	
      
  	function hide(){
		$("#center_1").click(function(){
			alert("隐藏左菜单");
			$("#in_left").toggle();
			$("#in_right").width("99%");
			$("#center_1").height("0");
			$("#center_2").height("100%");
		})
		$("#center_2").click(function(){
			alert("显示左菜单");
			$("#in_left").toggle();
			$("#in_right").width("84%");
			$("#center_1").height("100%");
			$("#center_2").height("0");
			
		})
}
	