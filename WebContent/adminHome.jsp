<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Smart Recruiter</title>
<script>
function addjob()
{
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
	        xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
	        xmlhttp=new ActiveXObject('Microsoft.XMLHTTP');
	}
	
	xmlhttp.onreadystatechange=function()
	{
	        if (xmlhttp.readyState==4 && xmlhttp.status==200)
	{
	        document.getElementById('content').innerHTML=xmlhttp.responseText;
	}
	}
	xmlhttp.open('GET','addJob.jsp',true);
	xmlhttp.send();
}

function interviewreport()
{
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
	        xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
	        xmlhttp=new ActiveXObject('Microsoft.XMLHTTP');
	}
	
	xmlhttp.onreadystatechange=function()
	{
	        if (xmlhttp.readyState==4 && xmlhttp.status==200)
	{
	        document.getElementById('content').innerHTML=xmlhttp.responseText;
	}
	}
	xmlhttp.open('GET','interviewReport.jsp',true);
	xmlhttp.send();
}


function load(position){
	if (window.XMLHttpRequest) {
		// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else{
		// code for IE6, IE5
		xmlhttp=new ActiveXObject('Microsoft.XMLHTTP');
	}
	xmlhttp.onreadystatechange=function(){
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
	        document.getElementById('content').innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open('GET','getCompletedTests?job_id='+position.value,true);
	xmlhttp.send();
}
</script>
</head>
<jsp:include page="header.jsp" />
<body onLoad="addjob()">
	<div id="navigation" style="height: 200px; width: 200px; float: left;">
		<ul style="list-style-type:none">
			<li onclick="addjob()"><a href="javascript:void(0)">Add A Job</a></li>
			<li onclick="interviewreport()"><a href="javascript:void(0)">Interview Reports</a></li>
			<li><a href="logout">Logout</a></li>
		</ul>
	</div>
	<div id="content"></div>
</body>
</html>