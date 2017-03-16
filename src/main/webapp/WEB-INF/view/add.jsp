<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/user/add"
		method="post">
		name:<input name="user.name" /><br /> age :<input name="user.age"
			onkeyup='this.value=this.value.replace(/\D/gi,"")'
			onkeydown='this.value=this.value.replace(/\D/gi,"")'><br />
		money:<input onkeyup="checkNum(this)" onkeydown="checkNum(this)" /> <input
			type="submit" value="增加" />
	</form>
</body>
<script type="text/javascript">


	function checkNum(element){
		var value=element.value;
		if(value==0){
			return;
		}
		if(value=="."){
			element.value="0.";
			return;
		}
		if(value.substring(value.length-1)=="."){
			if(value.indexOf(".")==value.lastIndexOf(".")){
				return;
			}
			else{
				element.value=value.substring(0,value.length-1);
				return;
			}
		}
		
		if(value.substring(0,1)=="0"&&value.length==2){
			element.value=value.substring(1);
			return;
		}
		
	var reg=
		/^(?!0+(?:\.0+)?$)\d+(?:\.\d{1,2})?$/;
	if(!reg.test(value)){
		element.value=value.substring(0,value.length-1);
	}
	
	
	
	}

</script>

</html>