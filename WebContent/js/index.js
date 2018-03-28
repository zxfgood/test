
	window.onload=function(){
		show();
		tick();
		hide();
	
	};
	
		function gomanger() {
			//alert("manger执行了");
			$.ajax({
				type:"GET",
				url:"/compantchat/message/getuser",
				dataType: 'json',
				async:true,
				//contentType: "application/x-www-form-urlencoded; charset=utf-8",
				scriptCharset : 'utf-8',
				success : function(data) {
					var user=data; 
					var role=user.role;
					alert(role);
					if(role=="管理员"){
						window.location.href="/compantchat/manger/mindex";
						}else {
							alert("权限不够");
							//window.location.href="/compantchat/message/messageindex";
						}
		        }
			});
		}
		
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
		/*新建话题 与查看详情*/

		function btnPop_onclick() {
		    var cover = "<div id='Cover' onclick='btnClose_onclick()'></div>";
		    $("body").append(cover);
		    var pop = "<div id='pop_cov'></div>";
		    $("body").append(pop);
		    $("#pop_cov").append("<div id='modlue'>"
		   /* $("#Cover").append("<div id='modlue'>"*/
		    		
		        +"<div class='MyHeader' id='header'>"
		        +" <span >新建话题</span> <button id='cancl' onclick='return btnClose_onclick()'>X</button> </div>"
		        +"<div class='MyContent' >"
		        +" <span class='top_content' >话题内容:</span> <textarea  id='TextValue' placeholder='请输入添加的数据...'></textarea></div>"
		        +"<div class='MyWay'> <div  class='myway1' > <lable >匿名</lable> <image id ='img1' src='../image/02-有话要说-新建弹窗-匿名.png'  ></image>"
		        +"</div>"
		        +" <div class='myway2' ><lable >实名</lable> <image src='/compantchat/image/02-有话要说-新建弹窗-实名.png' id='img2'></image> </div> </div> " +
		        "<div class='MyBtn'> <button id='confirm' onclick='add()'>新建</button> </div>"
		        + "</div>"
		    );
		  //拖动
			var newtopic=document.getElementById("header");
			init(newtopic);
		
		    }
		

		function btnPop_speakDetail(mid){
			//console.log(3333);
			//var mid=mid
		    var cover = "<div id='Cover1' onclick='btnClose_onclick1()'></div>";
		    $("body").append(cover);
		    var pop = "<div id='pop_cov1'></div>";
		    $("body").append(pop);
		    $("#pop_cov1").append("<div id='modlue1'>  " +
		        "<div class='MyHeader1'><span >话题详情</span>" +
		        "        <button id='cancle1'onclick='return btnClose_onclick1()'>X</button>" +
		        "    </div>" +
		        "    <div class='MyContent1' >" +
		        "        <span class='top_content1'>话题内容:</span>" +
		        "        <textarea id='TextValue1' placeholder='请输入添加的数据...'></textarea>" +
		        "    </div>" +
		        "    <div class='MyWay1'>" +
		        "        <div class='myway11'>" +
		        "            <span  >回复内容:</span>" +
		        "            <input type='text'  id='mreply'>" +
		        "        </div>" +
		        "        <div class='myway21' >" +
		        "            <span>话题状态:</span>" +
		        "           <input type='text' id='state'>" +
		        "        </div>" +
		        "    </div>" +
		        "    <div class='MyBtn1'>" +
		        "        <button id='confirm1' onclick='return btnClose_onclick1()' >返回</button>" +
		        "    </div>" +
		        "</div>"
		    );
		    //alert(mid);
		    messageshow(mid);
		}
		//新建话题
		function add() {
			var textvalue=$("#TextValue").val();
			//alert(textvalue);
			//var password=$("#pwd").val();
			$.ajax({
				type:"POST",
				url:'/compantchat/message/addmessage',
				async:true, 
				data:"messagecontent="+ textvalue,
				dataType: 'json',
				 success: function(data) {
					 if(data.result=="sucess"){
					 window.location.href="/compantchat/user/scucessdo";
					 }else{
						 window.location.href="/compantchat/user/faildo";
						 }
				} 
				}
					)
		}
		//查看详情
		    function messageshow(mid) {
		    	//alert(mid);
			$.ajax({
				type:"GET",
				url:'/compantchat/message/messagedetail',
				async:true, 
				data:"mid="+mid,
				dataType: 'json',
				 success: function(data) {
					 alert(data.mcontent);
					 alert(data.mstate);
					 alert(data.mreplay);
					 var mcontent=data.mcontent;
					 var mstate=data.mstate;
					 var mreply=data.mreplay;
					 document.getElementById("TextValue1").innerHTML=mcontent ;
					 document.getElementById("mreply").value=mreply ;
					 document.getElementById("state").value=mstate ;
					 
				} 
				});
		}
					//关闭弹窗的函数
					function btnClose_onclick() {
				$("#Cover").remove();
				$("#pop_cov").remove();
			}
					function btnClose_onclick1() {
						$("#Cover1").remove();
						$("#pop_cov1").remove();
					}
			


		/*总经理回复*/
		function btnPop_reply(mid){
		    var cover = "<div id='Cover2' onclick='btnClose_onclick1()'></div>";
		    $("body").append(cover);
		    var pop = "<div id='pop_cov2'></div>";
		    $("body").append(pop);
		    $("#pop_cov2").append("<div id='modlue2'>  " +
		        "<div class='MyHeader2'><span >话题详情</span>" +
		        "        <button id='cancle2' onclick='return btnClose_onclick2()'>X</button>" +
		        "    </div>" +
		        "    <div class='MyContent2' >" +
		        "        <span class='top_content2'>话题内容:</span>" +
		        "        <textarea id='TextValue2' placeholder='请输入添加的数据...'></textarea>" +
		        "    </div>" +
		        "    <div class='MyWay2'>" +
		        "        <div class='myway12'>" +
		        "            <span  >回复内容:</span>" +
		        "            <input type='text'  id='mreply'>" +
		        "        </div>" +
		        "        <div class='myway22' >" +
		        "            <span>话题状态:</span>" +
		        "           <input type='text' id='state'>" +
		        "        </div>" +
		        "    </div>" +
		        "    <div class='MyBtn2'>" +
		        "        <button id='confirm2' onclick='return btnClose_onclick2("+mid+")' >确定</button>" +
		        "    </div>" +
		        "</div>"
		    );
		    show2(mid);  
		}
		function show2(mid) {
			//alert(mid);
		$.ajax({
			type:"POST",
			url:'/compantchat/message/messagedetail',
			async:true, 
			data:"mid="+mid,
			dataType: 'json',
			 success: function(data) {
				 //alert(data.mcontent);
				 //alert(data.mstate);
				 //alert(data.mreplay);
				 var mcontent=data.mcontent;
				 var mstate=data.mstate;
				 var mreply=data.mreplay;
				 document.getElementById("TextValue2").innerHTML=mcontent ;
				 //document.getElementById("mreply").value=mreply ;
				 document.getElementById("state").value=mstate ; 
			} 
			});
		}
		function reply2(mid) {
			//alert(mid);
			var mreply=$("#mreply").val();
			//alert("mreply:"+mreply);
		$.ajax({
			type:"POST",
			url:'/compantchat/message/replymessage',
			async:true, 
			data:"mid="+mid+"&mreply="+mreply,
			dataType: 'json',
			 success: function(data) {
				
				 window.location.href="/compantchat/message/show";
			} 
			});
		}
		//关闭弹窗的函数
		function btnClose_onclick2(mid) {
		reply2(mid);
		window.location.reload();
		$("#Cover2").remove();
		$("#pop_cov2").remove();
		}

		
		function hide(){
			$("#center_1").click(function(){
				alert("隐藏左菜单");
				$("#in_left").toggle();
				$("#in_right").width("99%");
				$("#center_1").height("0");
				$("#center_2").height("100%");
			});
			$("#center_2").click(function(){
				alert("显示左菜单");
				$("#in_left").toggle();
				$("#in_right").width("84%");
				$("#center_1").height("100%");
				$("#center_2").height("0");
				
			});
	 }
		function init(moveDom){
			var l=0;
			var t=0;
			var x=0;
			var y=0;
			var isover=true;
			var parentDom=moveDom.parentNode;
			moveDom.onmousedown=function(event){
				var e=event||window.event;
				x=e.clientX;
				y=e.clientY;
				l=parentDom.offsetLeft;
				t=parentDom.offsetTop;
				isover=true;
				document.onmousemove=function(event){
					if(isover){
						var e=event||window.event;
						var nowLeft=e.clientX-x+1;
						var nowTop=e.clientY-y+t;
						parentDom.style.left=nowLeft+"px";
						parentDom.style.top=nowTop+"px";	
					}
				};
				document.onmouseup=function(){
					isover=false;
				};
			};
			
		}
		
		
		