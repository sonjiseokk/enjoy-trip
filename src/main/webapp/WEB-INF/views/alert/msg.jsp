<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script>
		window.onload = function() {			
			alert("${msg}");
			window.location.href = '${root}/enjoyTrip/index.jsp';
		}
	</script>
</body>
</html>
