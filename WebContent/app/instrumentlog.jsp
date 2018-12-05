<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="session.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../css/bootstrap.min.css"/>
<link rel="stylesheet" href="../css/font-awesome.min.css"/>
<link rel="stylesheet" href="../css/main.css"/>
<link rel="stylesheet" href="../css/warnset.css"/>
<script src="../js/jquery.min.js"></script>
<script src="../js/config.js"></script>
<script src="../js/artDialog.source.js"></script>
<script src="../js/iframeTools.source.js"></script>
<script src="../js/lhgcalendar.min.js"></script>
<link href="../css/lhgcalendar.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="../css/kkpager.css"/>
<script src="../js/kkpager.min.js"></script>
<link rel="stylesheet" href="../js/skins/default.css"/>
<body>
<ol class="breadcrumb">
    <li><a href="slopelist.jsp"><i class="fa fa-home"></i> &nbsp;首页</a></li>
    <li><a href="#">系统管理</a></li>
    <li class="active">设备日志</li>

</ol>
<%
    if (StringUtil.isIn("del", role)) {
        out.print("<input type='hidden' class='delrole' value='1'>");
    }
%>
<style type="text/css">
    .systemlogTable select, input[type='text'] {
        border: 1px #ddd solid;
        width: 160px;
        height: 32px;
        padding: 2px
    }

    .systemlogTable input[type='checkbox'] {
        width: 16px;
        height: 16px;
        border: 1px #ddd solid;
    }

    .wrapper {
        padding: 10px;
        margin: 0px
    }

    .warinfo_parma {
        width: 100%;
        height: 46px;
        margin-bottom: 10px;
        background: #f0fafc;
        padding-top: 6px;
        text-indent: 10px
    }

    .warinfo_parma * {
        vertical-align: middle;
    }

    .warinfo_parma label {
        padding-top: 4px
    }

    .warinfo_parma label, button, select {
        float: left;
        margin-right: 10px
    }

    /* 新增样式  */
    .warinfo_parma .activeval, .warinfo_parma .structure_type, .warinfo_parma .project_name {
        height: 32px;
        padding: 2px;
        /*border: 1px #ddd solid;*/
        float: left;
    }

</style>
<div class="wrapper" style="margin-top: -15px">
    <div class="warinfo_parma" style="width: 96%;margin-left: 2%">
        <label>项目：</label>
        <select class="project_name" id="project" name="project">
        </select>
        <label>站点：</label>
        <select class="project_name" id="siteid" name="siteid">
        </select>
        <%--<input id="siteid" class="project_name" type='text' name='siteid' value="">--%>
        <button class="btn btn-info btn-sm querydatas" style="margin-left: 20px"> &nbsp;&nbsp;查询&nbsp;&nbsp;</button>
    </div>
    <div style="width: 96%;margin-left: 2%">
        <table class="table table-bordered systemlogTable">
            <thead>
            <tr>
                <th width="20%">项目名称</th>
                <th width="10%">站点名称</th>
                <th width="10%">站点</th>
                <%--<th width="10%">回复命令</th>--%>
                <%--<th width="10%">命令回复间隔</th>--%>
                <%--<th width="10%">最后命令发送时间</th>--%>
                <th width="15%">DTU注册时间</th>
                <th width="7%">最后操作</th>
                <th width="15%">最后更新时间</th>
            </tr>
            </thead>
            <tbody class="datalist">


            </tbody>
        </table>
        <div id="kkpager"></div>
    </div>


</div>
</body>
</html>

<script type="text/javascript">
    $(function () {
        var pageSize = 10;
        var totalPage;
        var totalRecords;
        var page = getUrlVars()["page"];
        if (!page) {
            page = 1;
        }
        var projectid = getUrlVars()["projectid"];
        if (!CheckValIsNull(projectid)) {
            projectid = ""
        }
        var siteid = getUrlVars()["siteid"];
        if (!CheckValIsNull(siteid)) {
            siteid = ""
        }
        projectList();
        QueryMoniteSitelist();
        querySiteList(siteid,projectid);
        $(".querydatas").bind("click", function () {
            var projectid = $.trim($("#project").find("option:selected").val());
            var siteid = $.trim($("#siteid").find("option:selected").val());
            window.location.href = "?siteid=" + siteid+"&projectid="+projectid;
        })
        function querySiteList(siteid,projectid) {
            $.post('./../moniteSiteServlet',{"oper": "select","projectid":projectid},function (data) {
                $("#siteid").html("");
                $("#siteid").append('<option value="">全部</option>');
                $.each(data,function (index,obj) {
                    if(siteid == obj.nSiteID){
                        $("#siteid").append('<option value="'+obj.nSiteID+'" selected>'+obj.sDtuName+'</option>');
                    }else{
                        $("#siteid").append('<option value="'+obj.nSiteID+'">'+obj.sDtuName+'</option>');
                    }
                })
            },'json')
        }
        $("#project").bind('change',function () {
              querySiteList(siteid,$(this).val());
        })
        function projectList() {
            $.post('./../moniteSiteServlet',{"oper": "selectProject"},function (data) {
                $("#project").append('<option value="">全部</option>');
                $.each(data,function (index,obj) {
                    if(projectid == obj.ID){
                        $("#project").append('<option value="'+obj.ID+'" selected>'+obj.sProjectAbbr+'</option>');
                    }else{
                        $("#project").append('<option value="'+obj.ID+'">'+obj.sProjectAbbr+'</option>');
                    }
                })
            },'json')
        }

        function QueryMoniteSitelist() {
            tip("正在查询数据.......")
            $.post("./../moniteSiteServlet", {
                "oper": "Query",
                "page": page,
                "pageSize": pageSize,
                "siteid": siteid,
                "projectid": projectid
            }, function (data) {
                totalRecords = data.totalRecord;
                var dataList = data.data;
                var dataStr = "";
                var delRoleEnable = "";
                var delRole = $(".delrole").val();
                if (delRole != 1) {
                    delRoleEnable = "disabled";
                }
                if (dataList.length > 0) {
                    $.each(dataList, function (index, list) {


                        dataStr += "<tr class='list" + list.nSiteID + "'>" +
                            "<td>" + list.sProjectAbbr + "</td>" +
                            "<td>" + list.sDtuName + "</td>" +
                            "<td>" + list.nSiteID + "</td>" +
                            // "<td>"+list.sSendCommand+"</td>"+
                            // "<td>"+list.nSendMinuteInterval+"</td>"+
                            // "<td>"+list.dtSendDateTime+"</td>"+
                            "<td>" + list.dtLoginDateTime + "</td>" +
                            "<td>" + list.cnSLastAction + "</td>" +
                            "<td>" + list.dtLastDateTime + "</td>" +
                            "</tr>";
                    })
                    //dataStr+="<tr ><td colspan=5 style='text-align:right'><input type='checkbox' name='selall'>&nbsp;&nbsp;<button class='btn btn-primary btn-xs checkall'> 全选</button>&nbsp;&nbsp;<button class='btn btn-warning btn-xs systemDel' "+delRoleEnable+"> 删除</button></td></tr>";
                    $(".datalist").append(dataStr);


                } else {
                    $(".datalist").append("<tr><td colspan=5 align='center'>无查询数据。</td></tr>")
                }
                tip_close();
                totalPage = Math.ceil(totalRecords / pageSize);
                kkpager.generPageHtml({
                    pno: page,
                    total: totalPage,
                    totalRecords: totalRecords,
                    hrefFormer: 'instrumentlog',
                    hrefLatter: '.jsp',
                    getLink: function (n) {
                        return this.hrefFormer + this.hrefLatter + "?page=" + n + "&siteid=" + siteid+"&projectid="+projectid;
                    },
                    lang: {
                        firstPageText: '首页',
                        lastPageText: '末尾',
                        prePageText: '上页',
                        nextPageText: '下页',
                        totalPageBeforeText: '总共',
                        totalPageAfterText: '页',
                        totalRecordsAfterText: '条记录',
                        gopageBeforeText: '跳转',
                        ButtonOkText: 'OK',
                        gopageAfterText: '页',
                        buttonTipBeforeText: 'OK',
                        buttonTipAfterText: '页'
                    }
                });

            })
        }
    });
</script>