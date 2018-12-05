<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ include file="session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/font-awesome.min.css" />
<link rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" href="../css/warnset.css" />

 <script src="../js/jquery.min.js"></script>
  <script src="../js/config.js"></script>
 <script src="../js/artDialog.source.js"></script>
    <script src="../js/iframeTools.source.js"></script>
      <script src="../js/lhgcalendar.min.js"></script>
  <link href="../css/lhgcalendar.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="../css/kkpager.css" />
    <script src="../js/kkpager.min.js"></script>
     <link rel="stylesheet" href="../js/skins/default.css" />
<body>
	 <ol class="breadcrumb">
    <li><a href="slopelist.jsp"><i class="fa fa-home"></i> &nbsp;首页</a></li>
    <li ><a href="#">系统管理</a></li>
    <li class="active">系统日志</li>
   
</ol>
<%
if(StringUtil.isIn("del",role)){out.print("<input type='hidden' class='delrole' value='1'>");}
%>
<style type="text/css">
	.systemlogTable select,input[type='text']{border: 1px #ddd solid; width: 160px;height: 32px; padding: 2px}
    .systemlogTable input[type='checkbox']{width: 16px; height: 16px; border:1px #ddd solid;}
	
	.wrapper{padding: 10px; margin: 0px}
  	.warinfo_parma{width: 100%; height: 46px; margin-bottom: 10px; background: #f0fafc; padding-top: 6px; text-indent: 10px}
	.warinfo_parma *{vertical-align: middle;}
	.warinfo_parma label{padding-top: 4px}
	.warinfo_parma label,button,select{float: left; margin-right: 10px}
	/* 新增样式  */
	.warinfo_parma .activeval,.warinfo_parma .structure_type,.warinfo_parma .project_name{width: 120px; height: 32px; padding: 2px; border: 1px #ddd solid;float: left;}
	  	
</style>
<div class="wrapper"  style="margin-top: -15px">

  <div class="warinfo_parma" style="width: 96%;margin-left: 2%">
  	
  	<label>用户：</label>
    <select class="userName project_name">
      <option value="0">全部</option>
    </select>
	<label>&nbsp;&nbsp;&nbsp;时间区间：</label>
    <input type="text" value=" " class="starttime project_name" name="starttime" > 
    <label> 至 </label>
    <input type="text" value=" " class="endtime project_name" name="endtime" >&nbsp;&nbsp;&nbsp;
    <button class="btn btn-info btn-sm querydatas" style="margin-left: 20px"> &nbsp;&nbsp;查询&nbsp;&nbsp;</button>
  </div>
  <div style="width: 96%;margin-left: 2%">

	<table class="table table-bordered systemlogTable">
		<thead>
			<tr>
				<th width="15%">用户</th>
				<th width="10%">操作</th>
				<th width="55%">描述</th>
				<th width="20%">日期</th>
			</tr>
		</thead>
		<tbody class="datalist">
		</tbody>
	</table>
	<div id="kkpager" ></div>
  </div>
</div>
</body>
</html>

<script type="text/javascript">
  var pageSize=10;
  var totalPage ;
  var totalRecords;
  var page = getUrlVars()["page"];
  if(!page){
    page = 1;
  }
  var weekago=getRecentweekDays();
  var starttime=getUrlVars()["starttime"];
  var user=getUrlVars()["user"];
  var endtime=getUrlVars()["endtime"];
 if(!CheckValIsNull(starttime)){
          $(".starttime").val(weekago);
           starttime=weekago;
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
   if(!CheckValIsNull(user)){user="0"}
	function QuerySystemLogolist(starttime,endtime,userid){
		tip("正在查询数据.......");
		$.post("./../SystemlogoServlet",{"oper":"Query","page":page,"pageSize":pageSize,"starttime":starttime,"endtime":endtime,"userid":userid},function(data){
     
			 totalRecords=data.totalRecord;
            var dataList=data.data;
            var dataStr="";
             var delRoleEnable="";
          var delRole=$(".delrole").val();
          if(delRole!=1){
            delRoleEnable="disabled";
          }
            if(dataList.length>0){
            	$.each(dataList,function(index,list){
                     dataStr+="<tr class='list"+list.id+"'>"+
						"<td>"+list.username+"</td>"+
						"<td>"+list.active+"</td>"+
						"<td>"+list.descript+"</td>"+
						"<td>"+list.adddate+"</td>"+
					"</tr>";
            	})
                //dataStr+="<tr ><td colspan=5 style='text-align:right'><input type='checkbox' name='selall'>&nbsp;&nbsp;<button class='btn btn-primary btn-xs checkall'> 全选</button>&nbsp;&nbsp;<button class='btn btn-warning btn-xs systemDel' "+delRoleEnable+"> 删除</button></td></tr>";
                   $(".datalist").append(dataStr);
                    $(".checkall").bind("click",function(){
                       var c= $("input[name='selall']").prop("checked");
                      if(!c){
                        $("input[name='systemval']").prop("checked", true);
                        $("input[name='selall']").prop("checked",true);
                      }else{
                         $("input[name='systemval']").prop("checked", false);
                         $("input[name='selall']").prop("checked",false);
                      }
                    })
            }else{
            	$(".datalist").append("<tr><td colspan=5 align='center'>无查询数据。</td></tr>")
            }
            tip_close();
	        totalPage=Math.ceil(totalRecords/pageSize);
	        kkpager.generPageHtml({
	        pno : page,total : totalPage,totalRecords : totalRecords,hrefFormer : 'systemlog',hrefLatter : '.jsp',
	        getLink : function(n){
	          return this.hrefFormer + this.hrefLatter + "?page="+n+"&starttime="+starttime+"&endtime="+endtime+"&user="+user;
	        },
	        lang : {
	          firstPageText : '首页',lastPageText : '末尾',prePageText : '上页',nextPageText : '下页',totalPageBeforeText : '总共',totalPageAfterText :'页',totalRecordsAfterText : '条记录',gopageBeforeText : '跳转',ButtonOkText : 'OK',gopageAfterText : '页',buttonTipBeforeText : 'OK',buttonTipAfterText :'页'
	        }});

		})
	}

	function QueryUserlist(){
		$.post("./../UserInfo",{"oper":"QueryAll"},function(data){
		      var userStr="";
		       $.each(data, function(index,resultArr){
		       var sel="";
		         if(resultArr.ID==user){sel="selected";}

		         userStr+="<option value='"+resultArr.ID+"' "+sel+">"+resultArr.sUserName+"</option>";
		       	
		       })
		         $(".userName").append(userStr);
	     });
	};
	
$(function(){
	
	QueryUserlist();
	QuerySystemLogolist(starttime,endtime,user);
	$(".querydatas").bind("click",function(){
		var user=$(".userName").val();
		var starttime=$(".starttime").val();
		var endtime=$(".endtime").val();
		window.location.href="?starttime="+starttime+"&endtime="+endtime+"&user="+user
	})
})
</script>