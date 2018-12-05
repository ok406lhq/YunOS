<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ include file="session.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

</head>
 <!-- Bootstrap core CSS -->
   

    <!-- Custom styles for this template -->
    <link href="../css/main.css" rel="stylesheet">
    <link href="../css/crop_main.css" rel="stylesheet">
    <link href="../css/croppic.css" rel="stylesheet">

<body>
    <div class="ddf" style="width: 760px; margin:60px auto;">
				
				<div id="cropContainerMinimal"></div>
			</div>
			
		<div style="width: 760px;  margin: -20px auto">
		 <button class="imgsave"> 保存图片</button>
		 </div>
		 <input type="hidden" class="cropimgurl" value="">	
</body>
 <script src="../js/jquery-1.11.0.min.js"></script> 
   <script src="../js/config.js"></script>

	 	<script src="../js/croppic.js"></script>
   
    <script type="text/javascript">
    	var croppicContaineroutputMinimal = {
				uploadUrl:'./../img_crop_to_file',
				cropUrl:'./../img_crop', 
				modal:false,
				doubleZoomControls:false,
			    rotateControls: false,
				loaderHtml:'<div class="loader bubblingG"><span id="bubblingG_1"></span><span id="bubblingG_2"></span><span id="bubblingG_3"></span></div> ',
				onBeforeImgUpload: function(){ console.log('onBeforeImgUpload') },
				onAfterImgUpload: function(){ console.log('onAfterImgUpload') },
				onImgDrag: function(){ console.log('onImgDrag') },
				onImgZoom: function(){ console.log('onImgZoom') },
				onBeforeImgCrop: function(){ console.log('onBeforeImgCrop') },
				onAfterImgCrop:function(data){ $(".cropimgurl").val(data.url); $(".imgsave").show() },
				onReset:function(){ console.log('onReset') },
				onError:function(errormessage){ console.log('onError:'+errormessage) }
		}
		var cropContaineroutput = new Croppic('cropContainerMinimal', croppicContaineroutputMinimal);
		 var prjType=getCookie("prjType");
       var prjAlias=getCookie("prjAlias");
       var prjName=getCookie("prjName");
  $(".imgsave").bind("click",function(){
  	var cropimgurl=$(".cropimgurl").val();
  	 $.post("./../Project",{"op":"cropimgurl","prjName":prjName,"cropimgurl":cropimgurl},function(e){

  	 	if(e=="1"){
  	 		tip('图片保存成功!...',true);
				 setTimeout(function(){
            window.parent.location.reload();
               
                  
           },500);
  	 	}
  	 })

  })
  var project_image=getCookie("proimag");

  if(CheckValIsNull(project_image)){
  	$(".ddf").css({"background-image":"url("+project_image+")"})
  }
    </script>
</html>
</body>