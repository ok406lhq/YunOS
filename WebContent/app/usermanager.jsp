<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="session.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户设置</title>
</head>
<link rel="stylesheet" href="../css/bootstrap.min.css"/>
<link rel="stylesheet" href="../css/font-awesome.min.css"/>
<link rel="stylesheet" href="../css/main.css"/>
<link rel="stylesheet" href="../css/user.css"/>
<script src="../js/jquery-1.11.1.min.js"></script>
<script src="../js/config.js"></script>
<script src="../js/artDialog.source.js"></script>
<script src="../js/jquery.validate.min.js"></script>
<script src="../js/iframeTools.source.js"></script>
<link rel="stylesheet" href="../js/skins/default.css"/>
<style>
    .wrapper {
        padding: 10px;
        margin: 0px
    }

    .warinfo_parma {
        width: 100%;
        height: 40px;
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
        width: 120px;
        height: 32px;
        padding: 2px;
        border: 1px #ddd solid;
        float: left;
    }

    .opener {
        text-align: center;
    }

    .error {
        color: red;
    }
</style>
<body>
<ol class="breadcrumb">
    <li><a href="slopelist.jsp"><i class="fa fa-home"></i> &nbsp;首页</a></li>
    <li>系统管理</li>
    <li class="active">用户设置</li>

</ol>
<div class="wrapper">

    <table class="table table-bordered">
        <tr>
            <th scope="col" class="info">基本信息</th>

        </tr>
        <tr>
            <!-- 基本信息 -->
            <td>
                <div class="row clearfix">
                    <div class="col-md-3 column"></div>
                    <div class="col-md-6 column">
                        <form id="updateUser" class="form-horizontal" role="form">
                            <div class="form-group ">
                                <div class="col-sm-9">
                                    <input type="text" class="form-control hidden" id="userid"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="sUserName" class="col-sm-3">姓名:</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="sUserName" name="sUserName"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="sPosition" class="col-sm-3">职位:</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="sPosition" name="sPosition"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="sPassWord" class="col-sm-3">密码:</label>
                                <div class="col-sm-9">
                                    <input type="password" class="form-control" id="sPassWord"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="sTel" class="col-sm-3">手机:</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="sTel" name="sTel"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="sEMail" class="col-sm-3">邮箱:</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="sEMail" name="sEMail"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="sPartMent" class="col-sm-3">部门:</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="sPartMent" name="sPartMent"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="sAddress" class="col-sm-3">地址:</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="sAddress" name="sAddress"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-5 col-sm-10">
                                    <button type="submit" class="btn btn-info" id="btn_save">&nbsp;&nbsp;保&nbsp;&nbsp;存&nbsp;&nbsp;</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-3 column"></div>
                </div>
            </td>
        </tr>
    </table>
</div>
</body>
</html>

<script language="javascript">
    $(function () {
        tip("正在查询数据.......")
        $.post("./../UserInfo", {oper: "userinfo"}, function (data) {
            if (CheckValIsNull(data)) {
                var data = eval(data);
                $("#userid").val(data.userID);
                $.post("./../UserServlet", {"type": "QID", "ID": data.userID}, function (data) {
                    var data = eval(data);
                    $("#sUserName").val(data.sUserName);
                    $("#sPosition").val(data.sPosition);
                    $("#sTel").val(data.sTel);
                    $("#sEMail").val(data.sEMail);
                    $("#sPartMent").val(data.sPartMent);
                    $("#sAddress").val(data.sAddress);
                });
            }
            tip_close();
        })
    });


    $(document).ready(function () {
        // validate signup form on keyup and submit
        var validator = $("#updateUser").validate({

            submitHandler: function (form) {//通过之后回调
                var userid = $("#userid").val();
                var sUserName = $("#sUserName").val();
                var sPosition = $("#sPosition").val();
                var sTel = $("#sTel").val();
                var sEMail = $("#sEMail").val();
                var sPartMent = $("#sPartMent").val();
                var sAddress = $("#sAddress").val();
                var sPassWord = $("#sPassWord").val();

                var data = {
                    "type": "U",
                    ID: userid,
                    sUserName: sUserName,
                    sPosition: sPosition,
                    sTel: sTel,
                    sEMail: sEMail,
                    sPartMent: sPartMent,
                    sAddress: sAddress,
                    sPassWord: sPassWord,

                };
                $.post("./../UserServlet", data, function (data) {
                    var data = eval(data);
                    if (data.msg == "success") {
                        tip('保存成功!...', true);
                        $("#btn_save").attr("disabled", "disabled");
                        setTimeout(function () {
                            location.reload();
                        }, 2000);
                    } else {
                        tip('保存失败!...', true);
                    }
                });
            },
            invalidHandler: function (form, validator) {//初始化
                return false;
            },

            rules: {
                sUserName: {
                    required: true,
                },
                sPosition: {
                    required: true,
                },
                sTel: {
                    required: true,
                    digits:true,
                    rangelength:[11,11]
                },
                sEMail: {
                    required: true,
                    email:true
                },
                sPartMent: {
                    required: true,
                },
                sAddress: {
                    required: true,
                },


            },
            errorPlacement: function (error, element) {
                // Append error within linked label
                $(element)
                    .closest("form")
                    .find("label[for='" + element.attr("id") + "']").next()
                    .append(error);
            },
            errorElement: "span",
            messages: {
                sUserName: {
                    required: "必填字段",
                },
                sPosition: {
                    required: "必填字段",
                },
                sTel: {
                    required: "必填字段",
                    digits: "请输入正确的手机格式",
                    rangelength: "请输入正确的手机格式"
                },
                sEMail: {
                    required: "必填字段",
                    email:"请输入正确的邮箱格式"
                },
                sPartMent: {
                    required: "必填字段",
                },
                sAddress: {
                    required: "必填字段",
                },

            },
            success: function (label) {
                // set &nbsp; as text for IE
                // label.addClass("valid").html("<img src='./../images/yes.png' width='12' height='12' />");
            },
        });
    });


    function submitSave() {


    }


</script>