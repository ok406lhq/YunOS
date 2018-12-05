$.post("./../PointInfo",{},function(data){
	 	 var data=eval("("+data+")");

	 	 //////解析项目类型信息
	 	 var prjTypeData=data[0].PrjInfo;
	 	    var prjtypeshtmltr8="";
	 	   $.each(prjTypeData,function(index,prjtype){
	 	   	  var prjtypestr=prjtype.prjType;
	 	   	  if($.trim(prjtypestr)==$.trim(prjType)){
	 	   	                 var prjtypeshtmltr="<tr><td class='p1'>项目类型：</td><td>"+prjtypestr+"</td></tr>";
	 	   	                             prjtypeshtmltr+="<tr><td class='p1'>项目地址：</td><td>"+prjtype.prjAddr+"</td></tr>"+
	 	   	                              "<tr><td class='p1'>结构概述：</td><td>"+prjtype.JieGouDes+"</td></tr>"+
	 	   	                              "<tr><td class='p1'>主要监测内容：</td><td>"+prjtype.JianceNeirong+"</td></tr>"+
	 	   	                              "<tr><td class='p1'>检测开始时间：</td><td>"+prjtype.startTime+"</td></tr>"+
	 	   	                              "<tr><td class='p1'>状态描述：</td><td>"+prjtype.StatusDes+"</td></tr>"+
	 	   	                              "<tr><td class='p1'>标段：</td><td>"+prjtype.BiaoDuan+"</td></tr>";
	 	   	                         
	 	   	                              $(".prjinfo tbody").append(prjtypeshtmltr);
	 	   	                              
	 	   	  	return false;
 
	 	   	  }

	 	   })
	 	   //////解析点位信息
	 	   var colors=["green","blue","yello","violet","red"];
	 	   var ptStatus=data[1].ptStatus;
	 	  
	 	   var sensorTypeArr=[]; //定义一个sensorType的数组，用来装载ensorType;
	 	   if(ptStatus.length>0){
	 		
	 	   	$.each(ptStatus,function(index,Pt){
	 	   		 sensorTypeArr.push(Pt.sensorType);
	 	   	})
	 	   sensorTypeArr=uniqueArray(sensorTypeArr);//去除重复的元素。
		 	    for(var i=0; i<sensorTypeArr.length;i++){ 
		 	    	var tableHtmlStr="";
		 	    	      tableHtmlStr+='<div class="pro_data_table_list">'+
		         	                   '<table class="table table-bordered table-hover surface">'+
						               '<caption>'+sensorTypeArr[i]+'监测</caption>'+
						                '<thead>'+
							            '<tr>'+
								        '<th>设备名</th>'+
								        '<th>测站名</th>'+
								        '<th>ref1值</th>'+
								        '<th>ref2值</th>'+
								        '<th>ref3值</th>';
								         //'<th>状态描述</th>'+
								       tableHtmlStr+= '<th>状态</th>'+
							            '</tr>'+
						                '</thead>'+
						                '<tbody>';
					
                         $.each(ptStatus,function(index,Pt){
				 	   		 if(sensorTypeArr[i]==Pt.sensorType){

                                 tableHtmlStr+='<tr>'+
								                '<td><i class="fa fa-circle '+colors[i]+'"></i> '+Pt.SensorName+'</td>'+
								                 '<td>'+Pt.staName+'</td>'+
								                 '<td>'+Pt.ref1+'</td>'+
								                 '<td>'+Pt.ref2+'</td>'+
								                '<td>'+Pt.ref3+'</td>';
								                //'<td>'+Pt.StatusDes+'</td>';
								                if(Pt.Status=="Online"){
								                	tableHtmlStr+='<td><button type="button" class="btn btn-success btn-sm">正常</button></td>';
								                    }else{
                                                     tableHtmlStr+='<td><button type="button" class="btn btn-danger btn-sm">不正常</button></td>';
								                    }
								           
							             tableHtmlStr+='</tr>';
				 	   		 }
				 	   	});
                        
                         tableHtmlStr+'</tbody></table></div>';
                         $(".pointdatalist").append(tableHtmlStr);
		 	    }
	 	   }

	 })
function shwgraphic(sensorname){
	var SensorHref="detail.html?sensorname="+sensorname;
	 	 art.dialog.open(SensorHref, {title: "点位:"+sensorname, width: 1180,height:620,lock:true});
}
	 