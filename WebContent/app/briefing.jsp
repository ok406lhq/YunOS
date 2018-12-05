<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ include file="session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/font-awesome.min.css" />
<link rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" href="../css/app.css" />

 <script src="../js/jquery-1.11.1.min.js"></script>
  <script src="../js/config.js"></script>
     <script src="../js/artDialog.source.js"></script>
      <script src="../js/iframeTools.source.js"></script>
<link rel="stylesheet" href="../js/skins/default.css" />
<body>
	 <ol class="breadcrumb">
    <li><a href="slopelist.jsp"><i class="fa fa-home"></i> &nbsp;首页</a></li>
    <li class="active">项目监测简报</li>
   
</ol>
 <div class="wrapper" >
    <table class="table table-bordered table-hover brieing" style="width:90%; margin-left:5%">
  <caption><font class="prj_title"></font>项目监测简报</caption>
  <thead>
    <tr >
     
      <th  >结构名称</th>
      <th  >目前状态</th>
      <th  >未处理告警</th>
     
      <th >历史告警次数</th>
      
      <th >最新报表</th>
       <th  >其他</th>
       
    </tr>
   
  </thead>
  <tbody>
    
  </tbody>
</table>

 </div>
</body>
</html>
<script type="text/javascript">
var userId = window.localStorage.getItem("userID");
var act=getUrlVars()["act"];

	 $.post("./../WarningInfoServlet",{prjName:"",qType:""},function(data){
          var data=eval("("+data+")");
          
          
        var  ListArr=data;
         var projectListArr=[];
         
         $.each(ListArr,function(index,dataList){
               var AlarmCount=dataList.AlarmCount;
               if(parseInt(act)==0){
                 if(AlarmCount==0){
                    projectListArr.push(dataList);
                 }
               }else if(parseInt(act)==1){
                  if(AlarmCount>0){
                    projectListArr.push(dataList);
                  }
               }else{

                projectListArr.push(dataList);
               }
         })
        var str="";
          var len=projectListArr.length;
        for(var i=0;i<len;i++){

          var ReportURL=projectListArr[i].sName;
          if(!CheckValIsNull(ReportURL)){
             ReportURL="";
          }
          var report="";
         
          /*
          if(!CheckValIsNull(report)){
            var report="ak14.pdf";
            ReportURL="../images/up/ak14.pdf"; //需要删除
          }else{
            report=ReportURL;
          }
          */
          var classStr="";
          if(projectListArr[i].AlarmCount==0){
               classStr="success";
          }else{
              classStr="danger";
          }
          var CurStatus=projectListArr[i].CurStatus;
          var CurStatusStr="正常";
          switch(CurStatus.toLowerCase()){
               case "online":
               CurStatusStr="正常";
               break;
               case "alarm":
               CurStatusStr="预警";
               break;
                case "action":
               CurStatusStr="红色预警";
               break;
               case "offline":
               CurStatusStr="异常";
               break;
               default:
                CurStatusStr="正常";
               break;
          }
          str+="<tr>"+
            
            "<td>"+projectListArr[i].PrjAlais+"</td>"+
            "<td>"+CurStatusStr+"</td>"+
            "<td><a href='warninfo.jsp?prjName="+projectListArr[i].PrjName+"&userId="+userId+"'><span class='badge "+classStr+"'>"+projectListArr[i].AlarmCount+"</span></a></td>"+
            "<td>"+projectListArr[i].ActionCount+"</td>"+
            "<td><a href='javascript:void()' onclick=showreport('"+projectListArr[i].urlAddr+"') >"+ReportURL+"</a></td>"+
            "<td>"+projectListArr[i].unprocSum+"</td>"+
            "<tr>";
            }
        
            
          
         
   $(".brieing").find("tbody").append(str);
       
 ListArr=null;
    })

   function showreport(reproturl){
   //window.showModalDialog(reproturl, null, "dialogWidth: 1180px; height: 680px; dialogTop:0px");
    myWindow=window.open(reproturl,'','left=00,top=0,width=1180,height=680,toolbar=no,scrollbars=no,resizable=no,statusbars=no');

    // art.dialog.open(reproturl, {title: "查看报表:", width: 1180,height:680,lock:true,top:0});
   }
</script>
