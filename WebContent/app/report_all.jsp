<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
		 
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
	<link rel="stylesheet" href="../css/bootstrap.min.css" />
	<link rel="stylesheet" href="../layui/css/layui.css" />
	<link rel="stylesheet" href="../css/font-awesome.min.css" />
	<link href="../css/myAlert.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
			input, option, dd, dl{font-size:12px;}
	</style>

	<script src="../js/jquery-1.11.0.min.js"></script>
	<script src="../layui/layui.all.js"></script>
	<script src="../js/myAlert.js"></script>
	<script src="../js/config.js?1"></script>
	
	<script type="text/javascript">
        var layer = layui.layer,
            element = layui.element,
            laydate = layui.laydate,
            table = layui.table;
            form = layui.form;
        $(document).ready(function () {
        	element.render();
            form.render();
             lay('.J-lay-date-range').each(function(){
                laydate.render({
                    elem: this,
                    max: new Date().getTime(),
                    trigger: 'click',
                    done: function (value, date) {
                         var pElem = this.elem;
                     }
                });
            });
        });

	</script>
	
<script type="text/javascript">
function requestReport(tableId, reportType, projectId, startTime, endTime){
	   //第一个实例
	   table.render({
	     elem: '#'+tableId
	     //,height: 500
	     ,url: './../MonitorReportServlet?requestType=getReport&reportType='+reportType+'&projectId='+projectId+'&startTime='+startTime+'&endTime='+endTime //数据接口
	     ,page: true //开启分页
	     ,cols: [[ //表头
	       {field: 'sReportName', title: '报表(报告)名称', width:'40%'}
	       ,{field: 'dtCreatAt', title: '上传时间', width:'20%', sort: true}
	       ,{field: 'sUserName', title: '创建人', width:'20%'} 
	       ,{field: 'opt', title: '操作', width: '20%', toolbar: "#downloadBar"}
	     ]]
	   });
   }
</script>	
</head>
<body style="height: 560px">  
   <ol class="breadcrumb">
    <li><a href="slopelist.jsp"><i class="fa fa-home"></i> &nbsp;首页</a></li>
    <li ><a href="#">监测简报</a></li>
    <li class="active">日常报表</li>
   </ol>  
  
  <!-- 第一个tab页开始 --> 
  <div class="layui-tab"> 
   <ul class="layui-tab-title"> 
    <li class="layui-this">日常报表</li> 
    <li><a href="report_analysis.jsp">分析报告</a></li>
    <li><a href="report_warning.jsp">预警报告</a></li>
   </ul> 
   <!-- tab内容start --> 
   <div class="layui-tab-content"> 
    <!-- tab页日常报表开始 --> 
    <div class="layui-tab-item layui-show"> 
     <!-- 嵌套tab页start --> 
     <div class="layui-tab layui-tab-brief"> 
      <ul class="layui-tab-title bb-menu">
       <li class="layui-this" reportType="daily_day" tableId="dailyDay">日报表</li>
       <li reportType="daily_week" tableId="dailyWeek">周报表</li>
       <li reportType="daily_month" tableId="dailyMonth">月报表</li>
       <li reportType="daily_year" tableId="dailyYear">年报表</li>
      </ul> 
      <!-- 嵌套tab内容start--> 
      <div class="layui-tab-content" style="height: 100px;"> 
       <!-- 日报表start --> 
       <div class="layui-tab-item layui-show" reportType="daily_day" tableId="dailyDay">
        <%@ include file="report_daily_search.jsp" %>
        <table class="layui-hide" id="dailyDay" lay-filter="reportTable"></table> 
       </div> 
       <!-- 日报表end -->
       <!-- 周报表start --> 
       <div class="layui-tab-item" reportType="daily_week" tableId="dailyWeek">
        <%@ include file="report_daily_search.jsp" %>
        <table class="layui-hide" id="dailyWeek" lay-filter="reportTable"></table> 
       </div> 
       <!-- 周报表end --> 
       <!-- 月报表start --> 
       <div class="layui-tab-item" reportType="daily_month" tableId="dailyMonth">
        <%@ include file="report_daily_search.jsp" %>
        <table class="layui-hide" id="dailyMonth" lay-filter="reportTable"></table> 
       </div> 
       <!-- 月报表end --> 
       <!-- 年报表start --> 
       <div class="layui-tab-item" reportType="daily_year" tableId="dailyYear">
        <%@ include file="report_daily_search.jsp" %>
        <table class="layui-hide" id="dailyYear" lay-filter="reportTable"></table> 
       </div> 
       <!-- 年报表end --> 
      </div> 
      <!-- 嵌套tab内容end--> 
      
     </div> 
     <!-- 嵌套tab页end --> 
    </div> 
    <!-- tab页日常报表结束 --> 
    
    <!-- tab分析报告开始 --> 
       <div class="layui-tab-item" reportType="analysis" tableId="analysis">
        <%@ include file="report_daily_search.jsp" %>
        <table class="layui-hide" id="analysis" lay-filter="reportTable"></table> 
       </div> 
    <!-- tab分析报告结束 --> 
    <!-- tab预警报告开始 --> 
       <div class="layui-tab-item" reportType="warning" tableId="warning">
        <%@ include file="report_daily_search.jsp" %>
        <table class="layui-hide" id="warning" lay-filter="reportTable"></table> 
       </div> 
    <!-- tab预警报告开始 --> 
   </div> 
   <!-- tab内容end --> 
  </div> 
  <!-- tab页结束 -->
<script type="text/javascript">
$(function(){
	Loading("加载中....");
	requestReport('dailyDay', 'daily_day', '', '', '');	
});
$(".bb-menu > li ").click(function () {
    var tableId = $(this).attr("tableId");
    var reportType = $(this).attr("reportType");
    requestReport(tableId,reportType, '', '', '');
})
  //日报表查询按钮开始
  $(".J-dailyReport").click(function(){
	    var tableId = $(this).parents(".layui-tab-item").attr("tableId");
        var reportType = $(this).parents(".layui-tab-item").attr("reportType");
        var projectId = $(this).parents(".compare-container").parent().find(".selectProjectDay").val();
        //var authorId = $(this).parents(".compare-container").find(".selectAuthorDay").val();
        var startTime = $(this).parents(".compare-container").find(".startTime").val();
        var endTime = $(this).parents(".compare-container").find(".endTime").val();
        
        if(startTime!=''){
        	if(endTime==''){
        		$.myAlert("请选择结束时间！");
        		 return false;
        	}
        }
        
        if(startTime==''){
        	if(endTime!=''){
        		$.myAlert("请选择开始时间！");
        		 return false;
        	}
        }
     	requestReport(tableId, reportType, projectId, startTime, endTime);
    }); 

</script>
<script type="text/javascript">
	Loading("加载中....");
	$.post("./../MonitorReportServlet?requestType=getProject",{prjName:""},function(data){
	    var data=eval(data);
		for(var i=0; i< data.length; i++){
		    $(".selectProjectDay").append("<option value=\""+data[i].ID+"\">"+data[i].sProjectAbbr.trim()+"</option>");
		    
		    $("#selectProjectWarning").append("<option value=\""+data[i].ID+"\">"+data[i].sProjectAbbr.trim()+"</option>");
			$("#selectProjectAnalysis").append("<option value=\""+data[i].ID+"\">"+data[i].sProjectAbbr.trim()+"</option>");
			$("#selectProjectYear").append("<option value=\""+data[i].ID+"\">"+data[i].sProjectAbbr.trim()+"</option>");
			
		}
		form.render();
	    LoadingHide();
	});
   
   //=====================
   
	 table.on('tool(reportTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
	  var data = obj.data; //获得当前行数据
	  var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
	  var tr = obj.tr; //获得当前行 tr 的DOM对象
	  var url = data.sURLAddress;
		console.log("a:"+url);	 		
	  if(layEvent === 'view'){ //查看
		viewPDF(url,'0','0');
	  } else if(layEvent === 'downLoad'){ //下载
		ExplodePDF(url);
	  } 
	});
   
//view 报告
  function viewPDF(url,id,e){
    var url="http://bd.hnlzqs.com/picserver/export/"+url;
    var myWindow=window.open(url,'','left=00,top=0,width=1180,height=680,toolbar=no,scrollbars=no,resizable=no,statusbars=no');
  }
  
//下载报告
  function ExplodePDF(url){
	window.location.href="./../DownloadServlet?url="+encodeURIComponent(url);
  }

</script>

<script type="text/html" id="downloadBar">
  <a class="layui-btn layui-btn-xs layui-btn-warm" lay-event="downLoad">下载</a>
  <a class="layui-btn layui-btn-xs" lay-event="view">查看</a>
</script>


</body>
</html>
<script src="../js/footer.js"></script>