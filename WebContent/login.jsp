<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	function redirectURL(){
		var url = document.getElementById("redirectURL").value;
		window.location.replace(url);
	}
</script>
</head>
<body onload="redirectURL()">
<input type= "hidden" id="redirectURL" name="redirectURL" value = <%=request.getAttribute("AuthURL") %> >
</body>
</html>