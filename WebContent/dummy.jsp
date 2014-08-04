<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" >
function getURLParameter(name) {
	  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
	}
function getVerifier(){
	var token = getURLParameter('oauth_verifier');
	document.getElementById("verifier").value = token;
	document.getElementById("tokenForm").submit();
}
</script>
</head>
<body onload="getVerifier()">
<form method="post" action="verifyToken" id="tokenForm">
<input type="hidden" name = "verifier" id="verifier">
</form>
</body>
</html>