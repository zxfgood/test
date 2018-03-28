  var pageSize=10;
   var count;//总共多少记录
   var role=" ";
   function show(){

     $.ajax({
			type:"GET",
			url:"/compantchat/message/getuser",
			dataType: 'json',
			async:true,
			success : function(data) {  
				role=data.role;
				alert("role:"+role);
				document.getElementById("helloname").innerHTML=data.realname ;
				document.getElementById("role").innerHTML=data.role ;				
	        }
		});
   };
   
   $(function(){ 
	   	InitTable(0);  //初始化显示第一页
	 	$.ajax({
	   	 type: "POST",  
         dataType: "json",  
         url: '/compantchat/manger/getalluser',      //提交到一般处理程序请求数据    
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
	  alert(pageIndex);
      $.ajax({
          type: "POST",  
          dataType: "json",  
          url: '/compantchat/manger/getalluser',      //提交到一般处理程序请求数据    
          data: "pageIndex=" + (pageIndex+1) ,          //提交两个参数：pageIndex(页面索引)，pageSize(显示条数)     
          success: function(data) {
        	  count=data.totalPage; //共多少条记录  
              var  now= data.currentpage; //当前页  
              var total=Math.ceil(count/pageSize) ;//总页数
              $('.count').text(count);   //共多少条记录  
              $('.now').text(now); //当前页  
              $('.total').text(total);//总页数

              $("table").remove("#userlist");
              $("#right_mid").append('<table  id="userlist"><tr class="tr1">'
                      +'<td  class="td1" ><input type="checkbox"></td>'
                      +'<td  class="td2">用户名</td>'
                      +'<td  class="td3" >科室</td>'
                      +'<td  class="td4">权限</td>'
                      +'<td class="td5" >真实姓名</td>'
                      +'<td class="td6" >操作</td>'
                      +'</tr></table>');
              var userlist=data.userlist;			
				 for(var i=0;i<userlist.length;i++){
					 var user=userlist[i];
					 var uid=user.uid;
					 var username=user.username;
					 var dept=user.dept;
					 var userrole=user.role;
					 var realname=user.realname;
					 
					 var j=i%2;
					 if(j==0){
						 var tr ='<tr class="tr" style="background-color:#FFFFFF">'+'<td  class="td1" ><input type="checkbox"></td>'+'<td class="td2">'+username+'</td>' 
						   +'<td class="td3">'+dept+'</td>'
			    		   +'<td class="td4">'+userrole+'</td>'+'<td class="td5" >'+realname+'</td>'
			    		   +'<td class="td6"><a onclick= deleteuser("'+uid+'")><img src="../image/05-用户管理-操作-删除2.png"><span>删除用户</span></a></td>'	    		  				   
			    		   +'</tr>';
					 }
					 else{
						 var tr ='<tr class="tr" style="background-color:#F4F4F4">'+'<td  class="td1" ><input type="checkbox"></td>'+'<td class="td2">'+username+'</td>' 
						   +'<td class="td3">'+dept+'</td>'
			    		   +'<td class="td4">'+userrole+'</td>'+'<td class="td5" >'+realname+'</td>'
			    		   +'<td class="td6"><a onclick= deleteuser("'+uid+'")><img src="../image/05-用户管理-操作-删除2.png"><span>删除用户</span></a></td>'	    		  				   
			    		   +'</tr>';
					 }
					 
	    			$("#userlist").append(tr); 
					 }
          } , error: function () {
        	  alert("请求超时，请重试！");
          	  } 
      }); 
  }
