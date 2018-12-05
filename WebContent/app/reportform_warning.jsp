<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ include file="session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/font-awesome.min.css" />
<link rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" href="../css/app.css" />
  <link href="../css/lhgcalendar.css" rel="stylesheet" type="text/css" />
 <script src="../js/jquery-1.11.1.min.js"></script>
  <script src="../js/config.js"></script>
    <script src="../js/lhgcalendar.min.js"></script>
      <script src="../js/artDialog.source.js"></script>
      <link rel="stylesheet" href="../js/skins/default.css" />
<body>
	 <ol class="breadcrumb">
    <li><a href="slopelist.jsp"><i class="fa fa-home"></i> &nbsp;首页</a></li>
    <li ><a href="#">监测简报</a></li>
    <li class="active">预警报告</li>
   
</ol>
<input type="hidden" class="reportform">
 <div class="wrapper" >
      <div class="report_btn">
       <!-- <div class="btn-group report_from_nav"  >
              <button type="button" class="btn btn-default btn-info" alt="">全部</button>
              <button type="button" class="btn btn-default" alt="深度位移">深度位移监测</button>
              <button type="button" class="btn btn-default" alt="雨量">雨量监测</button>
              <button type="button" class="btn btn-default" alt="GNSS">GNSS位移监测</button>
              <button type="button" class="btn btn-default" alt="超声测距">超声测距监测</button>
              <button type="button" class="btn btn-default" alt="地下水位">地下水位监测</button>
              <button type="button" class="btn btn-default" alt="位移计">裂缝监测</button>
               </div>
               
             <div class="create">
             	 <button type="button" class="btn btn-primary btn-info instantReport"><i class="fa fa-plus"></i> &nbsp;&nbsp;生成即时报表&nbsp;</button>&nbsp;&nbsp;&nbsp;&nbsp;
             	 <button type="button" class="btn btn-primary btn-warning timingReport"><i class="fa fa-plus"></i> &nbsp;&nbsp;生成择时报表&nbsp;</button>
             </div>
        </div> -->
       <div class="reprot_pram alert alert-info">
       	  <label>项目名称：&nbsp;</label>
       	   <select class="prjName">
             <option id="allPrj" value="">全部</option>
           </select>
       	  
       	   <label>&nbsp;&nbsp;&nbsp;时间区间：</label>
       	   <input type="text" class="starttime" name="starttime">
       	   <label>&nbsp;&nbsp;至&nbsp;&nbsp;</label>
       	   <input type="text" class="endtime" name="endtime">

            <!-- <input type="checkbox"  name="show_alert_report" id="p1">
            <label for="p1">&nbsp;&nbsp;仅显示预警报表</label>
            <input type="checkbox"  name="datedesc"  id="p2">
            <label for="p2">&nbsp;&nbsp;逆时间循序</label> -->
          
       	   <button class="btn btn-info report_create"><i class="fa fa-plus"></i> &nbsp;查 询</button>
         
         <span class="reprot_pram_desc">
         	 
         </span>

       </div>
    <table class="table table-bordered table-hover reprot_table">
  
  <thead>
    <tr >
      <th>报告名</th> 
      <th>所属项目</th>
      <th>创建时间</th>
      <th>创建人</th>
      <!-- <th>报表类型</th> -->
      <th>报告内容开始时间</th>
      <th>报告内容截止时间</th>
       <th>报告文件地址</th>
      <th>阅读次数</th>
      <th>操作</th>
 </tr>
    
  </thead>
  <tbody>
    
  </tbody>
</table>


 </div>

 
</body>
</html>
<div id="instantReport_prama" class="instantReport_prama" >
    <p><label>项目名称：</label> <select class="instantReport_prama_prjName"><option value="all">全部</option></select></p>
     <p><label>时间区间：</label><select class="instantReport_prama_data">
        <option value="d">最近一天</option>
        <option value="w">最近一周</option>
        <option value="1m">最近1月</option>
        <option value="2m">最近2月</option>
        <option value="3m">最近3月</option>
        <option value="6m">最近半年</option>
        <option value="y">1年</option>
        <option value="all">全部</option>
     </select></p>
      <p><button class="submit_instantReport_prama btn btn-info" onclick="immediatesubmit();">生成即时报表</button></p>
 </div>

 <div id="timingReport_prama" class="timingReport_prama" >
  
    <p><label>项目名称：</label> <select class="timingReport_prama_prjName"><option value="0">全部</option></select></p>
     <p><label>开始时间：</label>
       <input type="text" class="timingReport_prama_starttime" >
       </p>
       <p><label>结束时间：</label>
       <input type="text" class="timingReport_prama_endtime" />
       </p>
        <script type="text/javascript">


        $(".timingReport_prama_starttime").calendar();
        $(".timingReport_prama_endtime").calendar();

           
      </script>
      <p><button class="submit_instantReport_prama btn btn-info" onclick="Choosetime();">生成择时报表</button></p>
     
 </div>
<script type="text/javascript">
var starttime=getUrlVars()["starttime"];
var endtime=getUrlVars()["endtime"];
var date=new Date();
var lastYearYestdy=getLastYearYestdy(date);
if(!CheckValIsNull(starttime)){
          $(".starttime").val(lastYearYestdy);
           starttime=lastYearYestdy;
     }else{
         $(".starttime").val(starttime);
     }
     if(!CheckValIsNull(endtime)){
        $(".endtime").val(curDateTime());
          endtime=curDateTime();
     }else{
          $(".endtime").val(endtime);
     }
	$(".starttime").calendar();
    $(".endtime").calendar(); 
   
    var prjName=getUrlVars()["prjName"];
 var prjNameGroup=[];
 //$.post("./../ProjectInfo",{prjName:""},function(data)
 $.post("./../myprojects",{prjName:""},function(data){
  var data=eval("("+data+")");
  var prjNameGroupList=data[0].PrjInfo;
  
        var prjNameStr;
        if(prjNameGroupList.length>0){
            var all = "";
         $.each(prjNameGroupList,function(index,prjNameGroup){


            var sel="";
             if(prjNameGroup.prjName==prjName){
               sel="selected"
             }
             if(index<prjNameGroupList.length-1){
                 all = all + prjNameGroup.prjID + ",";
             }else{
            	 all = all + prjNameGroup.prjID;
             }
             prjNameStr+="<option value='"+prjNameGroup.prjID+"' "+sel+">"+prjNameGroup.prjAlais+"</option>";
          
       })
       	 document.getElementById("allPrj").value = all;
         $(".prjName").append(prjNameStr);
         $(".instantReport_prama_prjName").append(prjNameStr);
         $(".timingReport_prama_prjName").append(prjNameStr);
    	 QueryReportData(all,starttime,endtime,"","0","0");
       }
 })

   var reportform=$(".reportform").val();

function QueryReportData(prjName,starttime,endtime,reportform,showalertreport,datadesc){
	tip("正在查询数据.......");
  $.post("./../ReportInfo",{"prjName":prjName,"starttime":starttime,"endtime":endtime,"sReportType":"warning"},function(data){
          tip_close();
          var data=eval("("+data+")");
           var projectListArr=[];
         projectListArr=data;
        
         var len=projectListArr.length;
        for(var i=0;i<len;i++){
         str="<tr style='text-align: center;'>"+
            "<td ><span style='vertical-align: middle'>"+projectListArr[i].sname+"</span></td>"+
            "<td>"+projectListArr[i].sprjname+"</td>"+
            "<td>"+projectListArr[i].screatetime+"</td>"+
            "<td>"+projectListArr[i].author+"</td>"+
            /* "<td>"+"预警报表"+"</td>"+ */
            "<td>"+projectListArr[i].sstarttime+"</td>"+
            "<td>"+projectListArr[i].sendtime+"</td>"+
              "<td>"+projectListArr[i].url+"</td>"+
            "<td>"+projectListArr[i].read+"</td>"+
            "<td><button class='btn btn-info btn-sm' onclick=viewPDF('"+projectListArr[i].url+"',"+projectListArr[i].ID+",this) ><i class='fa fa-plus' ></i> 查看</button><button class='btn btn-success btn-sm' onclick=ExplodePDF('"+projectListArr[i].url+"')><i class='fa fa-plus'></i> 导出</button></td>"+
            "</tr>"+
         "<tr>";
   $(".reprot_table").find("tbody").append(str);
          }
          if(len==0){
             $(".reprot_table").find("tbody").append("<tr><td colspan='9' align='center'><h4> 无报表数据。</h4></td></tr>");
          }

    })
}


  $(".instantReport").bind("click",function(){
            art.dialog({content: document.getElementById('instantReport_prama'),
            title:'生成即时报表',
            width:420,height:280,top:20,lock:true
        });
  })

  $(".timingReport").bind("click",function(){
   // $(".date_selector").css({"left":"20px","top":"-120px"})
       var date=new Date();
        var getLastmd=getLastMonthYestdy(date);
        $(".timingReport_prama_starttime").val(getLastmd);
        $(".timingReport_prama_endtime").val(curDateTime());
        
          
        
        
        art.dialog({content: document.getElementById('timingReport_prama'),
            title:'生成择时报表',
            width:420,height:280,top:20,lock:true
        });
       
           $(".timingReport_prama_starttime").bind("click",function(){
         
              $(".date_selector").css({"left":"20px","top":"145px"})
           })
          $(".timingReport_prama_endtime").bind("click",function(){
         
              $(".date_selector").css({"left":"20px","top":"215px"})
           })
    
  })
  function ExplodePDF(url){
	  window.location.href="./../DownloadServlet?url="+encodeURIComponent(url);
    
  }

  function viewPDF(url,id,e){

    $.post("./../ReportInfo",{"oper":"addViewNum","id":id},function(data){
		if(data=="1"){
			curReadTimes=$(e).parent().prev().html(); 
			$(e).parent().prev().html(curReadTimes*1+1);
		}
    })  
      //var url="./../images/up/ak14.pdf";
   var url="http://bd.hnlzqs.com/picserver/export"+url;
    var myWindow=window.open(url,'','left=00,top=0,width=1180,height=680,toolbar=no,scrollbars=no,resizable=no,statusbars=no');
  }
 $(".report_from_nav button").each(function(index,element){
                     $(this).bind("click",function(){
                      $(".report_from_nav button").removeClass('btn-info');
                       $(this).addClass('btn-info');
                       var val=$(this).attr("alt");
                       $(".reportform").val(val);
                     })
                 })
  $(".report_create").bind("click",function(){
           var reportform=$(".reportform").val(); 
           var starttime=$(".starttime").val();
           var endtime=$(".endtime").val();
           var prjName=$(".prjName").val();
           var showalertreport= $("input[name='show_alert_report']").is(':checked');
               showalertreport=showalertreport?"1":"0";
           var datadesc= $("input[name='datedesc']").is(':checked');
               datadesc=datadesc?"1":"0";
               $(".reprot_table").find("tbody").html("");

              QueryReportData(prjName,starttime,endtime,reportform,showalertreport,datadesc);

     })
//生成即时报表
 function immediatesubmit(){
	 var start_time;
	 var end_time;
	 var prjName=$('.instantReport_prama_prjName').val();
	 var date=$('.instantReport_prama_data').val();
	 var thisdate=new Date();
	 var year=thisdate.getFullYear();//获取年份
	 var month=thisdate.getMonth()+1;//获取月份 因为JavaScript的月份是0-11所以后面要加 1
	 var day=thisdate.getDate();//获取今天是多少号
	 if(date=='d'){
		 //最近一天
		 start_time=year+"-"+month+"-"+(day-1);
		 end_time=year+"-"+month+"-"+day;
		 alert("start_time:"+start_time+"\nend_time:"+end_time);
		 submitdata(prjName,start_time,end_time);
	 }else if(date=='w'){
		 //最近一周
		 start_time=year+"-"+month+"-"+(day-7);
		 end_time=year+"-"+month+"-"+day;
		 alert("start_time:"+start_time+"\nend_time:"+end_time);
		 submitdata(prjName,start_time,end_time);
	 }else if(date=='1m'){
		 //最近一个月
		 start_time=year+"-"+(month-1)+"-"+day;
		 end_time=year+"-"+month+"-"+day;
		 alert("start_time:"+start_time+"\nend_time:"+end_time);
		 submitdata(prjName,start_time,end_time);
	 }else if(date=='2m'){
		 //最近两个月
		 start_time=year+"-"+(month-2)+"-"+day;
		 end_time=year+"-"+month+"-"+day;
		 alert("start_time:"+start_time+"\nend_time:"+end_time);
		 submitdata(prjName,start_time,end_time);
	 }else if(date=='3m'){
		 //最近三个月
		 start_time=year+"-"+(month-3)+"-"+day;
		 end_time=year+"-"+month+"-"+day;
		 alert("start_time:"+start_time+"\nend_time:"+end_time);
		 submitdata(prjName,start_time,end_time);
	 }else if(date=='6m'){
		 //最近半年
		 start_time=year+"-"+(month-6)+"-"+day;
		 end_time=year+"-"+month+"-"+day;
		 alert("start_time:"+start_time+"\nend_time:"+end_time);
		 submitdata(prjName,start_time,end_time);
	 }else if(date=='y'){
		 //最近一年
		 start_time=(year-1)+"-"+month+"-"+day;
		 end_time=year+"-"+month+"-"+day;
		 alert("start_time:"+start_time+"\nend_time:"+end_time);
		 submitdata(prjName,start_time,end_time);
	 }else{
		 //全部时间
	 }
 }
 //生成择时报表
 function Choosetime(){
	 var prjName=$('.timingReport_prama_prjName').val();
	 var start_time=$('.timingReport_prama_starttime').val();
	 var end_time=$('.timingReport_prama_endtime').val();
	 submitdata(prjName,start_time,end_time);
 }
 //提交数据
 function submitdata(prjName,start_time,end_time){
	 var data={
			'prjName': prjName,
			'start_time': start_time,
			'end_time': end_time
	 };
	 $.ajax({
		type: 'post',
		url: '../ReportUrl',
		data: data,
		success: function(s){
			if(s=='false'){
				alert('erroe生成失败');
			}else{
				alert("提交成功");
			}
		},
		error:function(){
            alert("erroe生成失败");
        }
	});
	 var list = art.dialog.list;
	 for (var i in list) {
	     list[i].close();
	 };
 }
</script>

