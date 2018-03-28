   var pageSize=10;
   var count;//总共多少记录
   var role="";
   var orderNum=0;
   function show(){

     $.ajax({
			type:"GET",
			url:"/compantchat/message/getuser",
			dataType: 'json',
			async:true,
			success : function(data) {  
				role=data.role;
				document.getElementById("helloname").innerHTML=data.realname ;
				document.getElementById("role").innerHTML=data.role ;
				if(role=="管理员"){
					//window.location.href="/compantchat/manger/mindex";
					$("#left_ul").append('<li class="left_item" id="userrole">'+
			                 '<a href="/compantchat/manger/mindex" id="item1"> <img  class="item" src="/compantchat/image/02-有话要说-菜单栏-用户管理.png"><span style="color:#FFFAFA">用户管理</span></a>'          
			                +'</li>');
					}
	        }
		});
   };
   $(function(){ 
	   	InitTable(0);  //初始化显示第一页
	 	$.ajax({
	   	 type: "POST",  
         dataType: "json",  
         url: '/compantchat/complaint/show',      //提交到一般处理程序请求数据    
         //data: "pageIndex=" + (now) + "&pageSize=" + pageSize,          //提交两个参数：pageIndex(页面索引)，pageSize(显示条数)                   
         success: function(data) {
        	 count = data.totalPage; //共多少条记录  
        	 $('#Pagination').pagination(count,{  
    	    	 callback:PageCallback,//为翻页调用次函数
    	         prev_text: '上一页',       //上一页按钮里text
    	         next_text: '下一页',       //下一页按钮里text
    	         items_per_page:pageSize,  //每页显示条数
    	         num_display_entries: 2,    //连续分页主体部分显示的分页条目数
    	         num_edge_entries: 2,       //两侧显示的首尾分页的条目数 
    	         current_page: 0,   //当前页索引
    	     });
         }
	   	});
	   	    
   });   
   function PageCallback(index, jq) {             
       InitTable(index);  
   } 
  //请求数据   
  function InitTable(pageIndex) { 
		var resultlist=null;
		var rid=0;
	  alert(pageIndex);
      $.ajax({
          type: "POST",  
          dataType: "json",  
          url: '/compantchat/complaint/show',      //提交到一般处理程序请求数据    
          data: "pageIndex=" + (pageIndex+1) ,          //提交两个参数：pageIndex(页面索引)，pageSize(显示条数)     
          success: function(data) {
        	  count=data.totalPage; //共多少条记录  
              var  now= data.currentpage; //当前页  
              var total=Math.ceil(count/pageSize) ;//总页数
              $('.count').text(count);   //共多少条记录  
              $('.now').text(now); //当前页  
              $('.total').text(total);//总页数
              
              $("table ").remove("#complaintlist");
              $("#right_mid").append('<table  id="complaintlist"><tr class="tr1">'
                      +'<td  class="td1" ><input type="checkbox"></td>'
                      +'<td  class="td2">序号</td>'
                      +'<td  class="td3" >内容</td>'
                      +'<td  class="td6" >时间</td>'
                      +'<td  class="td4" >状态</td>'
                      +'<td  class="td5">操作</td>'
                      +'</tr></table>');
              orderNum=pageIndex*10;
              var complaintlist=data.complaintlist;
     		 for(var i=0;i<complaintlist.length;i++){
     			 var complant=complaintlist[i];
          		var cid=complant.cid;
          		var cnum=complant.cnumber;
          		var ccon=complant.ccontent;
          		var csta=complant.cstate;
          		orderNum=orderNum+1;
          		var j=i%2;
          		if(role=="管理员"){
          			if(j==0){
    	    			var tr='<tr class="tr" style="background-color:#FFFFFF">'+'<td class="td1"><input type="checkbox"></td>'+'<td class="td2">'+orderNum+'</td>'   	     	
    	     		      +'<td class="td3">'+ccon+'</td>'+'<td>'+cnum+'</td>'
    	     		      +'<td class="td4">'+csta+'</td>'
    	     		      +'<td class="td5">'+'<a href="#"onclick=btnPop_speakDetail("'+cid+'")><img src=/compantchat/image/02-有话要说-操作-查看.png><span>查看详情</span></a>'
    	     		      +'<a href="#"onclick=btnPop_reply("'+cid+'")><img src=/compantchat/image/02-有话要说-操作-回复.png><span>回复投诉 </span></a>'
    	     		      +'</td>'
    	     		      +'</tr>';
    	    			
    	    		}else{
    	    			var tr='<tr class="tr"style="background-color:#F4F4F4">'+'<td class="td1"><input type="checkbox"></td>'+'<td class="td2">'+orderNum+'</td>'   	     	
  	     		      +'<td class="td3">'+ccon+'</td>'+'<td>'+cnum+'</td>'
  	     		      +'<td class="td4">'+csta+'</td>'
  	     		      +'<td class="td5">'+'<a href="#"onclick=btnPop_speakDetail("'+cid+'")><img src=/compantchat/image/02-有话要说-操作-查看.png><span>查看详情</span></a>'
  	     		      +'<a href="#"onclick=btnPop_reply("'+cid+'")><img src=/compantchat/image/02-有话要说-操作-回复.png><span>回复投诉</span></a>'
  	     		      +'</td>'
  	     		      +'</tr>';
    	    		}
    	    	}
    	    	else{
    	    		if(j==0){
    	    			var tr='<tr class="tr" style="background-color:#FFFFFF">'+'<td class="td1"><input type="checkbox"></td>'+'<td class="td2">'+orderNum+'</td>'   	     	
    	     		      +'<td class="td3">'+ccon+'</td>'+'<td>'+cnum+'</td>'
    	     		      +'<td class="td4">'+csta+'</td>'
    	     		      +'<td class="td5">'+'<a href="#"onclick=btnPop_speakDetail("'+cid+'")><img src=/compantchat/image/02-有话要说-操作-查看.png><span>查看详情</span></a>'
    	     		      +'</td>'
    	     		      +'</tr>';
    	    			
    	    		}else{
    	    			var tr='<tr class="tr"style="background-color:#F4F4F4">'+'<td class="td1"><input type="checkbox"></td>'+'<td class="td2">'+orderNum+'</td>'   	     	
  	     		      +'<td class="td3">'+ccon+'</td>'+'<td>'+cnum+'</td>'
  	     		      +'<td class="td4">'+csta+'</td>'
  	     		      +'<td class="td5">'+'<a href="#"onclick=btnPop_speakDetail("'+cid+'")><img src=/compantchat/image/02-有话要说-操作-查看.png><span>查看详情</span></a>'
  	     		      +'</td>'
  	     		      +'</tr>';
    	    		}
    	    			
    	    		}
          	
   		      $("#complaintlist").append(tr);
     		 }
          } , error: function () {
        	  alert("请求超时，请重试！");
          	  } 
      }); 
  }

      
           


    
