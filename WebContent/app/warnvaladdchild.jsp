<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/font-awesome.min.css" />
<link rel="stylesheet" href="../css/main.css" />
<link rel="stylesheet" href="../css/user.css" />
<script src="../js/jquery-3.3.1.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/jquery.validate.min.js"></script>
  <script src="../js/config.js"></script>
<script src="../js/artDialog.source.js"></script>
    <script src="../js/iframeTools.source.js"></script>
  <link rel="stylesheet" href="../js/skins/default.css" />
  <style>
  	a{
  		text-decoration: none;}
  	.control{
  		width: 70%;
  	}
  	.panel-body .table thead tr th{
  		text-align: center;
  	}
  	.panel-body .table tbody tr td{
  		text-align: center;
  	}
  	#rainGaugecontent{
  	 vertical-align:middle;}
  	.wrapper{padding: 10px; margin: 0px}
  	.warinfo_parma{width: 100%; height: 46px; margin-bottom: 10px; background: #f0fafc; padding-top: 6px; text-indent: 10px}
	.warinfo_parma *{vertical-align: middle;}
	.warinfo_parma label{padding-top: 4px}
	.warinfo_parma label,button,select{float: left; margin-right: 10px}
	/* 新增样式  */
	.warinfo_parma .activeval,.warinfo_parma .structure_type,.warinfo_parma .sen_name{width: 100px; height: 32px; padding: 2px; border: 1px #ddd solid;float: left;}
  	.error{color:red;}
  </style>
  <body>
  <center id="gnsscontent">
	<br/>
	<form id="fazhiAdd" >
	<div class="col-md-12 column control">
		<div class="warinfo_parma">
			<label>设备名:</label>
			<select class="form-control-static structure_type" id="siteselect">
			</select>
			
		</div>
	</div>
	<div class="col-md-4 column control">
		<div class="tabbable" id="tabs-701946">
			<ul class="nav nav-tabs">
				<li>
					 <a class="btn btn-danger" href="#panel-780135" data-toggle="tab">一级预警</a>
				</li>
				<li>
					 <a class="btn btn-warning" href="#panel-174429" data-toggle="tab" >二级预警</a>
				</li>
				<li>
					 <a class="btn btn-info" href="#panel-174430" data-toggle="tab">三级预警</a>
				</li>
				<li>
					 <a class="btn btn-primary" href="#panel-174431" data-toggle="tab">四级预警</a>
				</li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="panel-780135">
					<div class="column">
						<div class="panel panel-danger">
							<div class="panel-body" id="level1warning">
								<table class="table table-condensed table-hover" align="center">
									<thead>
										<tr class="gnss_update">
											<th>累计变化量</th>
											<th>位移速率</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>
												<div class="form-group">
													 <label for="totalxy1" class="raingauge_update">△XY（水平变化量）</label><input type="text" class="form-control-static" name="totalxy1" id="totalxy1" />
												</div>
											</td>
											<td>
												<div class="form-group gnss_update">
													 <label for="todayxy1">△XY（水平变化量）</label><input type="text" class="form-control-static" name="todayxy1" id="todayxy1" />
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="form-group gnss_update">
													 <label for="totalz1">△Z（垂直变化量）</label><input type="text" class="form-control-static" name="totalz1" id="totalz1" />
												</div>
											</td>
											<td>
												<div class="form-group gnss_update">
													 <label for="todayz1">△Z（垂直变化量）</label><input type="text" class="form-control-static" name="todayz1" id="todayz1" />
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="tab-pane" id="panel-174429">
					<div class="column">
						<div class="panel panel-warning">
							<div class="panel-body" id="level2warning">
								<table class="table table-condensed table-hover" align="center">
									<thead>
										<tr class="gnss_update">
											<th>累计变化量</th>
											<th>位移速率</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>
												<div class="form-group">
													 <label for="totalxy2" class="raingauge_update">△XY（水平变化量）</label><input type="text" class="form-control-static" name="totalxy2" id="totalxy2" />
												</div>
											</td>
											<td>
												<div class="form-group gnss_update">
													 <label for="todayxy2">△XY（水平变化量）</label><input type="text" class="form-control-static" name="todayxy2" id="todayxy2" />
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="form-group gnss_update">
													 <label for="totalz2">△Z（垂直变化量）</label><input type="text" class="form-control-static" name="totalz2" id="totalz2" />
												</div>
											</td>
											<td>
												<div class="form-group gnss_update">
													 <label for="todayz2">△Z（垂直变化量）</label><input type="text" class="form-control-static" name="todayz2" id="todayz2" />
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="tab-pane" id="panel-174430">
					<div class="column">
						<div class="panel panel-info">
							<div class="panel-body" id="level3warning">
								<table class="table table-condensed table-hover" align="center">
									<thead>
										<tr class="gnss_update">
											<th>累计变化量</th>
											<th>位移速率</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>
												<div class="form-group">
													 <label for="totalxy3" class="raingauge_update">△XY（水平变化量）</label><input type="text" class="form-control-static" name="totalxy3" id="totalxy3" />
												</div>
											</td>
											<td>
												<div class="form-group gnss_update">
													 <label for="todayxy3">△XY（水平变化量）</label><input type="text" class="form-control-static" id="todayxy3" name="todayxy3" />
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="form-group gnss_update">
													 <label for="totalz3">△Z（垂直变化量）</label><input type="text" class="form-control-static" name="totalz3" id="totalz3" />
												</div>
											</td>
											<td>
												<div class="form-group gnss_update">
													 <label for="todayz3">△Z（垂直变化量）</label><input type="text" class="form-control-static" name="todayz3" id="todayz3" />
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="tab-pane" id="panel-174431">
					<div class="column">
						<div class="panel panel-primary">
							<div class="panel-body" id="level4warning">
								<table class="table table-condensed table-hover" align="center">
									<thead>
										<tr class="gnss_update">
											<th>累计变化量</th>
											<th>位移速率</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>
												<div class="form-group">
													 <label for="totalxy4" class="raingauge_update">△XY（水平变化量）</label><input type="text" class="form-control-static" name="totalxy4" id="totalxy4" />
												</div>
											</td>
											<td>
												<div class="form-group gnss_update">
													 <label for="todayxy4">△XY（水平变化量）</label><input type="text" class="form-control-static" name="todayxy4" id="todayxy4" />
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="form-group gnss_update">
													 <label for="totalz4">△Z（垂直变化量）</label><input type="text" class="form-control-static" name="totalz4" id="totalz4" />
												</div>
											</td>
											<td>
												<div class="form-group gnss_update">
													 <label for="todayz4">△Z（垂直变化量）</label><input type="text" class="form-control-static" name="todayz4" id="todayz4" />
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="column">
					<button type="submit" class="btn btn-block btn-success btn-lg" id="submit">保存</button>
				</div>
			</div>
		</div>
	</div>
	</form>
  </center>	
  
  </body>
  </html>

  <script language="javascript">
  
  var nprojectID = getUrlVars()["ID"];
  var abbr = getUrlVars()["abbr"];
  var type = getUrlVars()["type"];
  var fazhi = getUrlVars()["fazhi"];
  //console.log(id);
  if(type=="GNSS"){
	  $("#gnsscontent").removeClass("hidden");
	  var fzArr = fazhi.split("|");//逐级阀值数组
	  for(var i=0;i<fzArr.length;i++){
		  var fzCoordinates = fzArr[i].split(";");//阀值坐标
		  var id = i+1;
		  $("#totalxy"+id).val(fzCoordinates[0]);
		  $("#totalz"+id).val(fzCoordinates[1]);
		  $("#todayxy"+id).val(fzCoordinates[2]);
		  $("#todayz"+id).val(fzCoordinates[3]);
	  }
  }else{
		  $(".raingauge_update").html("雨量/小时");
		  $(".gnss_update").addClass("hidden");
	  $("#gnsscontent").removeClass("hidden");
	  var fzArr = fazhi.split("|");//逐级阀值数组
	  for(var i=0;i<fzArr.length;i++){
		  var id = i+1;
		  $("#totalxy"+id).val(fzArr[i]);
	  }
	  //$("#rainGaugecontent").removeClass("hidden");
  }
	  
	  $(function(){
		  $.post("./../WarningStrategyServlet",{"type":"site","nprojectID":nprojectID,"sSensorType":type},function(data){
			  //alert(nprojectID+"====="+type);
			  var data = eval(data);
			  $("#siteselect").html("");
			  var htmlStr = "";
			  if(data.length>0){
				  $.each(data,function(index,result){
					  htmlStr += "<option value='"+result.nSensorID+"'>"+result.sSiteName+"</option>";
				  });
			  }else{
				  htmlStr += "<option value='未查询到数据'>未查询到数据</option>";
			  }
			  $("#siteselect").append(htmlStr);
		  });
		  
	  });
	  
	  
	  
	  $(document).ready(function() {
			// validate signup form on keyup and submit
			var validator = $("#fazhiAdd").validate({
				
				submitHandler: function(form) {//通过之后回调
					$("#submit").attr("disabled","disabled");
					  var siteselect = $("#siteselect").val();
					  var fazhi1 = "";
					  var fazhi2 = "";
					  var fazhi3 = "";
					  var fazhi4 = "";
					  
					  if(type == "RainGauge"){
						  fazhi1 = $("#totalxy1").val();
						  fazhi2 = $("#totalxy2").val();
						  fazhi3 = $("#totalxy3").val();
						  fazhi4 = $("#totalxy4").val();
					  }else{
						  fazhi1 = $("#totalxy1").val()+";"+$("#totalz1").val()+";"+$("#todayxy1").val()+";"+$("#todayz1").val();
						  fazhi2 = $("#totalxy2").val()+";"+$("#totalz2").val()+";"+$("#todayxy2").val()+";"+$("#todayz2").val();
						  fazhi3 = $("#totalxy3").val()+";"+$("#totalz3").val()+";"+$("#todayxy3").val()+";"+$("#todayz3").val();
						  fazhi4 = $("#totalxy4").val()+";"+$("#totalz4").val()+";"+$("#todayxy4").val()+";"+$("#todayz4").val();
					  }
					  var data = {type:"addchild",proselect:nprojectID,senselect:type,siteselect:siteselect,dTopLevelValue:fazhi1,dMediumLevelValue:fazhi2,dLowLevelValue:fazhi3,dMicroLevelValue:fazhi4};
					  
					  $.post("./../WarningStrategyServlet",data,function(data){
						  var data = eval(data);
						  if(data.msg=="success"){
								tip('添加成功!...',true);
								setTimeout(function(){
					            		window.parent.location.reload();
					           },1000);
						  }else if(data.msg=="cunzai"){
							  tip('添加设备已存在！...',true);
								setTimeout(function(){
					            		window.parent.location.reload();
					           },1000);
						  }else{
							  art.dialog.alert("数据添加失败！检查设备数据是否存在");
						  }
					  });
					  
				},
				invalidHandler: function(form, validator) {//初始化
					return false;
				},
				
				rules: {
					totalxy1:{
						required: true,
						number:true
					},
					totalz1: {
						required: true,
						number:true
					},
					todayxy1: {
						required: true,
						number:true
					},
					todayz1: {
						required: true,
						number:true
					},
					
					totalxy2:{
						required: true,
						number:true
					},
					totalz2: {
						required: true,
						number:true
					},
					todayxy2: {
						required: true,
						number:true
					},
					todayz2: {
						required: true,
						number:true
					},
					
					totalxy3:{
						required: true,
						number:true
					},
					totalz3: {
						required: true,
						number:true
					},
					todayxy3: {
						required: true,
						number:true
					},
					todayz3: {
						required: true,
						number:true
					},
					
					totalxy4:{
						required: true,
						number:true
					},
					totalz4: {
						required: true,
						number:true
					},
					todayxy4: {
						required: true,
						number:true
					},
					todayz4: {
						required: true,
						number:true
					},

				},
				errorPlacement: function(error, element) {
				// Append error within linked label
				$( element )
					.closest( "form" )
						.find( "label[for='" + element.attr( "id" ) + "']" )
							.append(error);
				},
				errorElement: "span",
				messages: {
					totalxy1:{
						required: "*",
						number:"数字"
					},
					totalz1: {
						required: "*",
						number:"数字"
					},
					todayxy1: {
						required: "*",
						number:"数字"
					},
					todayz1: {
						required: "*",
						number:"数字"
					},
					
					totalxy2:{
						required: "*",
						number:"数字"
					},
					totalz2: {
						required: "*",
						number:"数字"
					},
					todayxy2: {
						required: "*",
						number:"数字"
					},
					todayz2: {
						required: "*",
						number:"数字"
					},
					
					totalxy3:{
						required: "*",
						number:"数字"
					},
					totalz3: {
						required: "*",
						number:"数字"
					},
					todayxy3: {
						required: "*",
						number:"数字"
					},
					todayz3: {
						required: "*",
						number:"数字"
					},
					
					totalxy4:{
						required: "*",
						number:"数字"
					},
					totalz4: {
						required: "*",
						number:"数字"
					},
					todayxy4: {
						required: "*",
						number:"数字"
					},
					todayz4: {
						required: "*",
						number:"数字"
					},
					
					
				},
				success: function(label) {
					// set &nbsp; as text for IE
					label.addClass("valid").html("<img src='./../images/yes.png' width='12' height='12' />");
				},
			});
		});
	  
	  
	  
	  function fazhiAdd(){
		  
	  }
 </script>
