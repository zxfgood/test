function btnPop_onclick() {
    var cover = "<div id='Cover' onclick='btnClose_onclick()'></div>";
    $("body").append(cover);
    var pop = "<div id='pop_cov'></div>";
    $("body").append(pop);
    $("#pop_cov").append("<div id='modlue'>"
        +"<div class='MyHeader' > <span >新建话题</span> <button id='cancle'  onclick='return btnClose_onclick()'>X</button> </div>"
        +"<div class='MyContent' >"
        +"<form id= 'uploadForm' enctype='multipart/form-data'>"
        +" <span class='top_content'>文件名称:</span> <input type='file' id='filename' name='file'>"
        + "<image src='../image/05-用户管理-操作-上传.png' onclick=' return upfilename()' ></image> </div>"
        +"<div class='MyWay'> <p>温馨提示：1.请正确填写用户的用户名（域账号）、密码、科室真实姓名</p> <p>2.上传文件统一格式为xls</p> <p>3.最新上传证书说明将覆盖所有旧版证书说明</p> </div>"
        +"<div class='MyBtn'> <button id='newtopic' onclick='doUpload()'>新增</button> <button id='cancle1' onclick='return btnClose_onclick()'>取消</button> </div>"
        +'</form>'
        + "</div>"
    );
    }
//关闭弹窗的函数
function btnClose_onclick() {
    $("#Cover").remove();
    $("#pop_cov").remove();
}
function upfilename() {
    var filename =document.getElementById("filename");
    filename.click();
}
function doUpload() {  
    var formData = new FormData($( "#uploadForm" )[0]);
  // alert("执行了函数");
    $.ajax({  
         url: '/compantchat/manger/upload',  
         type: 'POST',  
         data: formData,  
         //async: false,  
         cache: false,  
         //contentType: false,  
         processData: false,  
         success: function (returndata) {  
            // alert(returndata);
//             var filename=returndata.result;
//             alert(filename);
//             var str=filename.substr(filename.length-4,filename.length-1);
//             //alert(str);
//             if(str==".xls"){
//            	// alert(filename);
//            	 sendfile(filename);
//            	 btnClose_onclick();
//            	 window.location.reload();
//                 }else{
//                     alert("文件不符合格式");
//                	 //window.location.href="scucess2";
//                     }
             
         },  
         error: function (returndata) {  
             alert("出错了");  
         }  
    }); 
}
function sendfile(filname) {
	   $.ajax({  
	         url: '/compantchat/manger/file',  
	         type: 'POST',  
	         dataType: 'JSON',
	         data:"filename="+filname,
	         success: function () {
	        	// window.location.href="scucess2";
	         },  
	    });
}
function btnPop_newuser() {
    var cover = "<div id='cover3' onclick='close3()'></div>";
    $("body").append(cover);
    var pop = "<div id='pop_newuser'></div>";
    $("body").append(pop);
    $("#pop_newuser").append("<div  id ='ncontain'>"+
    "<div id='ntop'><span>新增用户</span><button id='cancle' onclick=' return close3()'>X</button></div>"
    +"<div id='nmid'>" +
		"<div class='part'><span>账号</span><input  type='text'></div>"+
	    "<div class='part'><span>密码</span><input  type='text'></div>"+
	    "<div class='part'><span>科室</span><input  type='text'></div>"+
	    "<div class='part'><span>权限</span><input  type='text'></div>"+
	    "<div class='part'><span>姓名</span><input  type='text'></div>"+
	    "<div id='nbottom'><button>确定</button></div>"
	    +"</div>");
    }
function close3() {
    $("#cover3").remove();
    $("#pop_newuser").remove();

}