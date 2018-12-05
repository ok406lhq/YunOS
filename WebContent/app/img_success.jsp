<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript"> 
	var str='<%=request.getAttribute("img_path") %>';
	parent.finish_upload(str); 
</script>

