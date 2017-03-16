<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>INDEX.........</title>
</head>
<body>INDEX.........
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
/* $.post("find",{page:3,rows:5},function(result){
	result=JSON.parse(result);
	for(var i in result){
		alert(result[i].name);
	}
}) */
$.getJSON("find",{page:1,rows:5},function(result){
	for(var i in result){
		alert("name:"+result[i].name+"\n"+"age:"+result[i].age);
	}
})
</script>
</html>