<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="app/session.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>北斗+安全监测云平台</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link rel="shortcut icon" href="images/ico.png"/>
    <link rel="stylesheet" href="css/main.css"/>
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/font-awesome.min.css"/>
    <link rel="stylesheet" href="css/index.css"/>
    <link rel="stylesheet" href="css/user.css"/>
    <link rel="stylesheet" href="layui/css/layui.css"/>
    <script src="js/jquery.min.js"></script>

    <script src="js/config.js"></script>
    <script src="layui/layui.all.js"></script>

    <link rel="stylesheet" type="text/css" href="css/jquery.dialogbox.css">

    <link href="" rel="stylesheet">
    <style type="text/css">
        .tx {
            border: 1px #ddd solid;
            border-radius: 4px;
            height: 26px;
            padding: 2px;
            width: 80%;
            margin-left: 5px;
        }

        .product-buyer-name {
            max-width: 180px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        .sub > li {
            display: none
        }
    </style>
</head>
<body style="background:linear-gradient(180deg,#3e526b,#314358)">
<div id="header" style="margin-top:8px">
    <div class="logo">
    	<img id="iLoginLogo" style="float: left;margin-left:15px" src="images/logo_login.png" width="120" height="46">
    	<h2 id="sWebName" style="padding-left:15px; padding-top: 20px; font-size: 18px;"></h2>
    	<!-- <img src="images/logo_login.png" width="324" height="46"> -->
    </div>
    <div id="user-nav" class="navbar">
        <ul class="nav btn-group">
            <li class="btn btn-inverse userinfo_btn" id="userinfo_btn">
                <i class="fa fa-user"></i> <span class="text" id="text"></span>

            </li>
            <li class="btn btn-inverse" id="warningstrs">

                <i class="fa fa-comment"></i>
                <span class="text">消息</span>
                <span class="label label-important dataln">0</span>
                <b class="caret"></b>


            </li>
            <!-- <li class="btn btn-inverse backstage">
                    <span class="text">跳转后台</span>
            </li> -->

            <li class="btn btn-inverse logout">
                <i class="fa fa-reply"></i> <span class="text">退出</span>
            </li>

        </ul>
    </div>

</div>
<ul class="logdata-menu"></ul>
<DIV id="wrapper">
    <!-- 侧边 菜单栏 开始 -->
    <div id="sidebar">

        <ul class="menulist">

            <li class="submenu" id="project">
                <a href="app/slopelist.jsp" target="right"><i class="fa fa-file-text-o"></i> <span>结构信息</span></a>
            </li>


            <li class="submenu">
                <!-- <a href="app/briefing.jsp" target="right"><i class="fa fa-external-link-square"></i> <span>报表管理</span></a> -->
                <a href="javascript:void(0);"><i class="fa fa-external-link-square"></i> <span>监测简报</span></a>
                <ul style="display:none">
                    <!--                    <li><a href="app/briefing.jsp?act=0" target="right"> 监测简报</a></li>
                                            <li><a href="app/briefing.jsp?act=1" target="right"> 预警结构</a></li> -->
                    <!-- <li><a href="app/warninfo.jsp" target="right"> 预警信息</a></li> -->
                    <!--  <li><a href="app/reportform_daily.jsp" target="right"> 日常报表</a></li>-->

                    <li><a href="app/report_all.jsp" target="right"> 日常报表</a></li>

                    <li><a href="app/report_analysis.jsp" target="right"> 分析报告</a></li>
                    <li class="end"><a href="app/report_warning.jsp" target="right"> 预警报告</a></li>

                </ul>
            </li>
            <li class="submenu">
                <a href="javascript:void(0);"><i class="fa fa-bar-chart"></i> <span>数据分析</span></a>
                <ul style="display:none">
                    <li class="end"><a href="app/compare.jsp" target="right"> 数据对比</a></li>
                    <!--<li class="end"><a href="app/Databelinked.jsp" target="right"> 数据关联</a></li>-->
                </ul>
            </li>
            <li class="submenu">
                <a href="javascript:void(0);"><i class="fa fa-bullhorn"></i> <span>预警管理</span></a>
                <ul style="display:none">
                    <li><a href="app/warninfo.jsp" target="right"> 预警信息</a></li>
                    <li><a href="app/warnval.jsp" target="right"> 设备预警值</a></li>
                    <li class="end"><a href="app/warnlist.jsp" target="right"> 接警人配置</a></li>
                    <!--<li ><a href="app/warninterface.jsp" target="right"> 预警接口</a></li> -->

                </ul>
            </li>
            <li class="submenu">
                <a href="javascript:void(0);"><i class="fa fa-gear"></i> <span>系统管理</span></a>
                <ul style="display:none">
                    <li><a href="app/systemlog.jsp" target="right"> 系统日志</a></li>
                    <li><a href="app/instrumentlog.jsp" target="right"> 设备日志</a></li>
                    <li class="end"><a href="app/usermanager.jsp" target="right"> 用户设置</a></li>

                </ul>
            </li>

            <li id="config" class="submenu"><a href="./logout"><i class="fa fa-reply-all"></i> <span>退出</span></a></li>

        </ul>
        <div class="hrefPointer"><i class="fa fa-caret-left"></i></div>

    </div>
    <div class="panMove"><i class="fa fa-caret-left"></i></div>
    <!-- 侧边 菜单栏 结束 -->
    <div id="content">
        <iframe frameBorder="0" id="right" name="right" src="app/slopelist.jsp"
                style="max-height: 585px;VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1;">
        </iframe>
    </div>
</DIV>
<div id="btn-dialogBox"></div>
</body>

<script src="http://www.jq22.com/jquery/1.11.1/jquery.min.js"></script>
<script src="js/jquery.dialogBox.js"></script>
<script type="text/javascript">
    $(function () {
        warningDialog();
    })
    var t;

    function warningDialog() {
        $.post('warningDialogServlet', {type: 'select'}, function (data) {
            layui.layer.closeAll();
            if (data != null) {
                // clearTimeout(t);
                var index = layui.layer.open({
                    type: 1
                    , title: data.sAlertLevel
                    , offset: 'rb'
                    , content: '<div style="padding: 20px 30px;">' + data.sAlertContent + '</div>'
                    , btnAlign: 'c' //按钮居中
                    , btn: '忽略全部(' + data.total + ')'
                    , anim: 2
                    , shade: 0 //不显示遮罩
                    , yes: function () {
                        $.post('warningDialogServlet', {type: 'updateAll', id: data.ID}, function () {
                        })
                        layui.layer.close(index);
                        // t = setTimeout('warningDialog()',5000);
                    }
                    , cancel: function () {
                        $.post('warningDialogServlet', {type: 'update', id: data.ID}, function () {
                        })
                        // t = setTimeout('warningDialog()',5000);
                    }
                });
            }
            t = setTimeout('warningDialog()', 5000);
        }, 'json')
    }

    $("body").on('click', 'ul.sub > a', function () {
        $(this).siblings('li').toggle();
    })
    var pwd = window.localStorage.getItem("pwd");
    $(".hrefPointer").css({"right": "0px", "top": "18px"});
    $(".backstage").click(function () {
        window.location.href = "http://58.20.17.214:8080/bdsimpms/public/admin/login/index.jsp";
    });
    $(".panMove").bind("click", function () {
        if ($("#sidebar").is(":hidden")) {

            $("#sidebar").show().css({"width": "15%"});
            $(".panMove .fa").removeClass("fa-caret-right").addClass("fa-caret-left");
            $("#content").css({"width": "85%"});
            $(".panMove").css({"left": "15%"});
        } else {
            $("#sidebar").hide();
            $("#content").css({"width": "100%"});
            $(".panMove .fa").removeClass("fa-caret-left").addClass("fa-caret-right");
            $(".panMove").css({"left": "0px"});
        }
    });

    $(".logout").bind("click", function () {
        window.location.href = "./logout";
    })
    $.post("./myprojects", {prjName: ""}, function (data) {
        var data = eval("(" + data + ")");
        var myproject = [];
        var projectListArr = data[0].PrjInfo;
        var prjTypeArr = [];
        for (var i in projectListArr) {
            prjTypeArr.push(projectListArr[i].prjType);
        }

        prjTypeArr = uniqueArray(prjTypeArr).reverse();

        var s = prjTypeArr.length;
        for (var i = 0; i < s; i++) {
            var menus = "";
            menus += "<ul class='sub'>" +
                "<a href='app/slopelist.jsp?prj=" + encodeURI(prjTypeArr[i]) + "' target='right'><dd class='secondsub'> " + prjTypeArr[i] + "</dd></a>";
            $.each(projectListArr, function (index, prj) {

                if (prjTypeArr[i] == prj.prjType) {


                    var tip = "";
                    if (isWarning(prj.statusVal)) {
                        tip = " <font class='waringsim' style='color:#fe0202;background:#fff;padding:1px 6px 1px 6px;border-radius:50%' >!</font>";
                    }
                    menus += "<li class='in product-buyer-name'><a href='app/prjlist.jsp?prjName=" + prj.prjID + "&prjType=" + encodeURI(prjTypeArr[i]) + "&prjAlais=" + encodeURI(prj.prjAlais) + "' target='right'>" + prj.prjAlais + tip + "</a></li>";
                    myproject[i] = prj.prjAlais;
                }
            })
            menus += "</ul>";
            $("#project").append(menus);
            $(".submenu .sub").eq(s - 1).css({
                "background-image": "url(images/line_s.png)",
                "background-repeat": "no-repeat"
            });
            $("li.in:last").css({"background-image": "url(images/treeline_end.png)", "background-repeat": "repeat-y"});
        }
        setCookie('myproject', myproject);
        $("#sidebar a").each(function (index, el) {
            $(this).bind("click", function () {

                var h = ($(this).position().top);
                $(".hrefPointer").css({"top": h + "px"});
            });
        })


    })


    var data1;
    $(function () {
        $.post("./WarningInfoServlet", {qType: "C"}, function (data) {
            var data = eval("(" + data + ")");
            var l = data.count;
            $(".dataln").html(l);
        });
        /* $.post("./WarningInfoServlet",{qType:"G"},function(result){
               var data=eval("("+result+")");
               var l=data.length;

               $(".dataln").html(l);
               if(l>0){
                 var str="";
                 str+="<li><dd class='time'>预警时间</dd><dd class='prj'>结构</dd><dd class='type'>类型</dd><dd class='sensor'>传感器</dd><dd class='waring'>预警信息</dd></li>";
                 $.each(data,function(index,datalist){
                   str+="<li><dd class='time'>"+datalist.waringTime+
                   "</dd><dd class='prj'>"+datalist.PrjAlias+
                   "</dd><dd class='type'>"+datalist.waringType+
                   "</dd><dd class='sensor'>"+datalist.wSensor+
                   "</dd><dd class='waring'>"+datalist.waringMsg+"</dd></li>";
                 })

                 $(".logdata-menu").append(str);
               }
          }); */
             
        	 $.ajax({
     			url : "./LogoServlet",
     			type : "post",
     			data : {type: "logo"},
     			dataType : "json",
     			success:function(data){
     				 var data=eval(data);
     				$("#sWebName").html(data[0].sWebName);
     			}
     		});

         
        $.post("./UserInfo", {oper: "userinfo"}, function (data) {
            if (CheckValIsNull(data)) {
                data1 = data;
                var text = document.getElementById("text");
                text.innerText = "欢迎你，" + data1.name;
                window.localStorage.setItem("userID", data1.userID);
                
                
            }
        })

        $("#warningstrs").bind("click", function () {
            window.open("app/warninfo.jsp", "right")
        });
        /* $("#warningstrs").hover(function(){
          $(".logdata-menu").toggle();
          $(".logdata-menu li:last").css({"border-bottom":"none"})
        },function(){
          $(".logdata-menu").toggle();
          $(".logdata-menu li:last").css({"border-bottom":"none"})
        }); */
        var name;
        $(".userinfo_btn").hover(function () {
            var adddate = data1.adddate;
            adddate = CheckValIsNull(adddate) ? adddate : "";
            var role = data1.role;
            var roleStr = "查看";
            var roleArr = [];
            if (CheckValIsNull(role)) {
                var userroleArr = role.split("|");
            }
            for (var i = 0; i < userroleArr.length; i++) {
                for (var r in roleCH) {
                    if (r == userroleArr[i]) {
                        roleArr[i] = roleCH[r];
                    }
                }
            }
            roleStr = roleArr.join(",");
            setCookie("roleStr", roleStr);
            var str = "<ul class='userinfo'>";
            name = "<%=username %>";
            str += "<li><label>用户：</label>" + name + "</li>" +
                "<li><label>权限：</label>" + roleStr + "</li>" +
                "<li><label>部门：</label>" + data1.department + "</li>" +
                "<li><label>加入时间：</label>" + adddate + "</li>" +

                "<li><label>登录次数：</label>" + data1.loginnum + "</li>" +
                "<li><label>最近登录：</label>" + data1.lastlogindata + "</li>" +
                "</ul>";
            var u_l = $(".userinfo_btn").offset().left;

            var u_h = $(".userinfo_btn").offset().top + 42;

            $("body").append(str);
            $(".userinfo").css({"left": u_l + "px", "top": u_h + "px"});
            adddate = null;
        }, function () {
            $(".userinfo").remove();
        });
        $('#userinfo_btn').click(function () {
            console.log(JSON.stringify(data1));
            var htmlStr = "<ul class='adduser' style='width: 360px;'>";
            htmlStr += "</li><li><label>&emsp;姓&emsp;名 :&emsp;</label><input type='text' value='" + data1.name + "' class='t_username tx'></li>" +
                "<li><label>&emsp;旧密码 :&emsp;</label><input type='password' class='t_password_old tx'></li>" +
                "<li><label>&emsp;新密码 :&emsp;</label><input type='password' class='t_password_new tx'></li>" +
                "<li><label>确认密码 :&emsp;</label><input type='password' class='t_password_again tx'></li>" +
                "<li><label>&emsp;职&emsp;位 :&emsp;</label><input type='text' value='" + data1.position + "' class='t_position tx'></li>" +
                "<li><label>&emsp;手&emsp;机 :&emsp;</label><input type='text' value='" + data1.mobile + "' class='t_mobile tx'></li>" +
                "<li><label>&emsp;邮&emsp;箱 :&emsp;</label><input type='text' value='" + data1.email + "' class='t_email tx'></li>" +
                "<li><label>&emsp;部&emsp;门 :&emsp;</label><input type='text' value='" + data1.department + "' class='t_department tx'></li>" +
                "<li><label>&emsp;地&emsp;址 :&emsp;</label><input type='text' value='" + data1.address + "' class='t_address tx'></li>" +
                "</ul>";

            $('#btn-dialogBox').dialogBox({
                hasClose: true,
                hasBtn: true,
                confirmValue: '保存',
                confirm: function () {
                    var username = $(".t_username").val();
                    var old_pwd = $(".t_password_old").val();
                    var new_pwd = $(".t_password_new").val();
                    var again_pwd = $(".t_password_again").val();
                    var position = $(".t_position").val();
                    var tel = $(".t_mobile").val();
                    var email = $(".t_email").val();
                    var department = $(".t_department").val();
                    var address = $(".t_address").val();
                    if (username == "" || position == "" || tel == "" || email == "" || department == "" || address == "") {
                        alert('请将表格填充完整');
                    } else if (old_pwd != "") {
                        if (new_pwd == "" || again_pwd == "") {
                            alert('请将密码填充完整');
                        } else if (old_pwd != pwd) {
                            alert('旧密码不正确');
                        } else if (new_pwd != again_pwd) {
                            alert('确认密码不正确');
                        } else {
                            var json = {
                                "oper": "edit",
                                "userid": data1.userID,
                                "username": username,
                                "eTelNum": tel,
                                "email": email,
                                "department": department,
                                "address": address,
                                "position": position,
                                "password": new_pwd
                            };
                            updata_user(json);
                        }
                    } else {
                        var json = {
                            "oper": "edit",
                            "userid": data1.userID,
                            "username": username,
                            "eTelNum": tel,
                            "email": email,
                            "department": department,
                            "address": address,
                            "position": position
                        };
                        updata_user(json);
                    }


                },
                title: '编辑资料',
                content: htmlStr
            });
        });
    });

    function updata_user(json) {
        $.post("./UserInfo", json, function (data) {
            if (data == "ok") {
                location.reload();
            } else {
                alert("更改失败");
            }
        });
    }

    //初始话加载权限
    $(function () {
        if ($(".userinfo").length > 0) {
            $(".userinfo").remove();
        } else {
            $.post("./UserInfo", {oper: "userinfo"}, function (data) {
                if (CheckValIsNull(data)) {
                    var adddate = data.adddate;
                    adddate = CheckValIsNull(adddate) ? adddate : "";
                    var role = data.role;
                    var roleStr = "查看";
                    var roleArr = [];
                    if (CheckValIsNull(role)) {
                        var userroleArr = role.split("|");
                    }

                    for (var i = 0; i < userroleArr.length; i++) {
                        for (var r in roleCH) {
                            if (r == userroleArr[i]) {
                                roleArr[i] = roleCH[r];
                            }
                        }
                    }
                    roleStr = roleArr.join(",");
                    setCookie("roleStr", roleStr);
                }
            })
        }
    });

    //默认左侧菜单收起
    $(".submenu a").bind("click", function () {
        if ($(this).next().css("display") == "none") {
            $(this).nextAll().show();
        } else {
            $(this).nextAll().hide();
        }
    })
    //根据浏览器可视区域高度，设置主页面右侧iframe框架高度
    var content_iframe_height = $(window).height() - $("#header").height();
    $("#right").css("max-height", content_iframe_height);
    $("#right").css("min-height", content_iframe_height);
</script>
</html>