<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<div class="compare-container">
	<blockquote class="layui-elem-quote  mlr-18 mb-8">
		<div class="layui-form">
			<div class="layui-inline">
				<select class="selectProjectDay" lay-filter="selectProjectDay">
					<option value="">请选择结构物</option>
				</select>
			</div>
		 	<div class="layui-inline">
				<select class="selectWarning" lay-filter="selectWarning">
					<option value="">请选择预警等级</option>
					<option value="一级预警">一级预警</option>
					<option value="二级预警">二级预警</option>
					<option value="三级预警">三级预警</option>
					<option value="四级预警">四级预警</option>
				</select>
			</div>
		 	
			<div class="layui-inline">
				<input type="text" style="width: 200px;"  placeholder="开始时间" autocomplete="off" class="startTime layui-input J-lay-date-range">
			</div>
			<div class="layui-inline">
				<input type="text" style="width: 200px;"  placeholder="结束时间" autocomplete="off" class="endTime layui-input J-lay-date-range">
			</div>
			<div class="layui-inline">
				<button class="layui-btn J-dailyReport">查询</button>
			</div>
		</div>
	</blockquote>
</div>  