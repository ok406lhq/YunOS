function getsensorNames(sensorType,PrjName,sensor){

    $.post("./../PointAll",{oper:"SensorTypePoints",SensorType:sensorType,PrjName:PrjName},function(data){
    	 
         //var data=eval("("+data+")");
    	var list = document.getElementById("sensornamelist");
         var data=data;
          if(data.length>0){
             var selectStr="<select>";
             $.each(data,function(i,datalist){
              var selected="";
              if(sensorName.toLowerCase()==(datalist.SensorName.toLowerCase())){

                 selected="selected";
              }
              selectStr+="<option value='"+datalist.SensorID+"' "+selected+">"+datalist.staName+"</option>";
             })
             selectStr+="</select>";
             $(".sensornamelist").html(selectStr);
             list.value = sensor;
          }
    })
  }

//function UploadPointImages(sensorName){
	//  var myDate=new Date();

   //   var time=myDate.getTime();
     
      //   var up = $('#upload').Huploadify({
    	  //   auto:false,
      //   fileTypeExts:'*.*',
      //   multi:false,
      //    formData:{"time":time,"type":"point","sensorname":sensorName},
      //   fileSizeLimit:99999999999,
      //    showUploadedPercent:true,
      //   showUploadedSize:true,
      //    removeTimeout:9999999,
      //    uploader:'./../UploadProcessorServlet?prjName='+PrjName+'&time='+time+'&type=point&sensorname='+sensorName,
      //   onUploadStart:function(file){
    	  //     console.log(file.name+'开始上传');
        //   },
      //  onInit:function(obj){
    	  //    //console.log('初始化');
    	  //    //console.log(obj);
    	  //  },
      //   onUploadComplete:function(file,res){
    	 
    	  //          var fileName=time+file.name.substring(file.name.lastIndexOf("."),file.name.length);
            
             // $("#SenosrImage").attr("src","./../uploadfiles/"+fileName); 
              //           $("#SenosrImage").attr("src",res);
              //          $("#SenosrImage").css({"width":"230px"});
             // window.location.reload();
              //     console.log(file.name+'上传完成');
        //     $(".uploadify-queue-item").hide();
       
        //   },
      //   onCancel:function(file){
    	  //      console.log(file.name+'删除成功');
    	  //    },
      //    onClearQueue:function(queueItemCount){
    	  //       console.log('有'+queueItemCount+'个文件被删除了');
        //     },
      //     onDestroy:function(){
    	  //       console.log('destroyed!');
        //      },
      //      onSelect:function(file){
      //       console.log(file.name+'加入上传队列');
        //     },
      //      onQueueComplete:function(queueData){
    	  //    	
    	  //        console.log('队列中的文件全部上传完成',queueData);
        //      }
      //   });
//}

  function getsensorimages(sensorName){

  $.post("./../PointAll",{"oper":"ImgUrlBySensorName","SensorName":sensorName},function(data){
   var data=eval("("+data+")");

    var imageUrl=data[0].imgURL;
    if(CheckValIsNull(imageUrl)){
       $("#SenosrImage").attr("src",imageUrl); 
       $("#SenosrImage").css({"width":"170px"});
    }else{
    	// $("#SenosrImage").attr("src","");
    	// $("#SenosrImage").css({"width":"170px"});
    }
    var descript=data[0].Demo;
    if(!CheckValIsNull(descript)){descript="请在“测点预警值”一栏更新测点描述内容。"}
    $("#descript").html(descript);

     /*var isY=  data[0].isY;
   
     if(isY=="true"){

       $("input[name='isY']").attr("checked",true);
       $(".chartY").attr("disabled",false);
       $(".chartY").val(data[0].chartY);
        
     }else{
       $(".chartY").attr("disabled",true);

     }*/
  })
}
//$(function(){
//	$(".sensornamelist").bind("change",function(){
//  	var sName=$(this).val();
//  	sensorName=sName;
//  	$("#upload").html("");
////  	UploadPointImages(sName);
//  	getsensorimages();
//  })
//
//
//})

$(function(){
	$("#sensornamelist").bind("change", function() {
		staName = $("#sensornamelist").find("option:selected").text();
		sensorName=$("#sensornamelist").find("option:selected").val();
		getsensorimages($(this).val());
		querydata();
		queryThreshold();
	});
})  

function extend(target, source,n) {
	
        for (var obj in source) {
        	if(n==0){
        		 target[obj+"_"] = source[obj];
        	}else{
        		target[obj+"_"]="";
        	}
           
        }
        return target;
    }

//导出表格表头中文映射
var chineseLaberMapping={st:"数据时间",dn:"北方向(mm)",de:"东方向(mm)",du:"垂直方向(mm)",dRealRain:"雨量(mm)",dtCreateDT:"数据时间",Sigma:"平面(mm)",dNorth:"北方向(mm/d)",dEast:"东方向(mm/d)",dUp:"垂直方向(mm/d)",sigma:"平面(mm/d)",sDatetime:"数据时间",azimuth:"方位角",dL:"单次沉降量(mm)"};

function JSONToCSVConvertor(JSONData, ReportTitle, ShowLabel) {
    //If JSONData is not an object then JSON.parse will parse the JSON string in an Object
    var arrData = typeof JSONData != 'object' ? JSON.parse(JSONData) : JSONData;
    var CSV = '';
    //Set Report title in first row or line

   // CSV += ReportTitle + '\r\n\n';

    //This condition will generate the Label/Header
    if (ShowLabel) {
        var row = "";
        //This loop will extract the label from 1st index of on array
        for (var index in arrData[0]) {
        	console.log(index);
            //Now convert each value to string and comma-seprated
        	var th=index.toString().replace(/_/,'');
        	if(row.indexOf(th)>=0){
        	}else{
        		row += index + ',';
        	}   
        }
        row = row.slice(0, -1);
        var englishLabels=row.split(",");
        for (var i = 0; i < englishLabels.length; i++) {
        	if(chineseLaberMapping[englishLabels[i]]){
        		console.log(englishLabels[i]);
        		console.log(englishLabels[i]+"--"+chineseLaberMapping[englishLabels[i]]);
        		englishLabels[i]=chineseLaberMapping[englishLabels[i]];
        	}
		}
        row = englishLabels.join(",");
        //append Label row with line break
        CSV += row + '\r\n';
    }
    //1st loop is to extract each row
    for (var i = 0; i < arrData.length; i++) {
        var row = "";

        //2nd loop will extract each column and convert it in string comma-seprated
        for (var index in arrData[i]) {
        	if(index.toString().indexOf("_")>=0){
        		break;
        	}
            row += '"' + arrData[i][index] + '",';
        }
        row.slice(0, row.length - 1);
        //add a line break after each row
        CSV += row + '\r\n';
    }

    if (CSV == '') {
        alert("Invalid data");
        return;
    }

    //Generate a file name
    var fileName = "";
    //this will remove the blank-spaces from the title and replace it with an underscore
    fileName += ReportTitle.replace(/ /g, "_");

    //Initialize file format you want csv or xls
    var uri = 'data:text/csv;charset=utf-8,\uFEFF' + encodeURI(CSV);

    // Now the little tricky part.
    // you can use either>> window.open(uri);
    // but this will not work in some browsers
    // or you will not get the correct file extension    

    //this trick will generate a temp <a /> tag
    var link = document.createElement("a");
    link.href = uri;

    //set the visibility hidden so it will not effect on your web-layout
    link.style = "visibility:hidden";
    link.download = fileName + ".csv";

    //this part will append the anchor tag and remove it after automatic click
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}
 
//测量机器人 与  拉线位移计
function JSONToCSV(JSONData, ReportTitle, ShowLabel) {
    //If JSONData is not an object then JSON.parse will parse the JSON string in an Object
    var arrData = typeof JSONData != 'object' ? JSON.parse(JSONData) : JSONData;
    var CSV = '';
    //Set Report title in first row or line

   // CSV += ReportTitle + '\r\n\n';

    //This condition will generate the Label/Header
    if (ShowLabel) {
        var row = "";
        //This loop will extract the label from 1st index of on array
        console.log("--"+arrData[0]);
        for (var index in arrData[0]) {
        	console.log(index+"=======");
            //Now convert each value to string and comma-seprated
        	index=index.substring(1,index.length);
        	var th=index.toString().replace(/_/,'');
        	if(row.indexOf(th)>=0){
        	}else{
        		row += index+ ',';
        	}   
        }
        row = row.slice(0, -1);
        //append Label row with line break
        CSV += row + '\r\n';
    }
    //1st loop is to extract each row
    for (var i = 0; i < arrData.length; i++) {
        var row = "";

        //2nd loop will extract each column and convert it in string comma-seprated
        for (var index in arrData[i]) {
        	if(index.toString().indexOf("_")>=0){
        		break;
        	}
            row += '"' + arrData[i][index] + '",';
        }
        row.slice(0, row.length - 1);
        //add a line break after each row
        CSV += row + '\r\n';
    }

    if (CSV == '') {
        alert("Invalid data");
        return;
    }

    //Generate a file name
    var fileName = "";
    //this will remove the blank-spaces from the title and replace it with an underscore
    fileName += ReportTitle.replace(/ /g, "_");

    //Initialize file format you want csv or xls
    var uri = 'data:text/csv;charset=utf-8,\uFEFF' + encodeURI(CSV);

    // Now the little tricky part.
    // you can use either>> window.open(uri);
    // but this will not work in some browsers
    // or you will not get the correct file extension    

    //this trick will generate a temp <a /> tag
    var link = document.createElement("a");
    link.href = uri;

    //set the visibility hidden so it will not effect on your web-layout
    link.style = "visibility:hidden";
    link.download = fileName + ".csv";

    //this part will append the anchor tag and remove it after automatic click
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

