<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

	<head>
    <title>Administrate Portfolio</title>
	</head>

	<body>
		
		<ul>
     <li><a href="<s:url action='AddImage'/>">Add image to portfolio.</a></li>	
     <li><a href="<s:url action='RemoveImage'/>">Remove an image from portfolio.</a></li>	
       <li><a href="<s:url action='UpdateAccountForm'/>">Update your Account</a></li>	
 
 	  </ul> 	
 	  
 	  Testing Redirected QueryString Parameter: param2 = <s:property value="#parameters.param2"/>
	  
		
	</body>
	
</html>
