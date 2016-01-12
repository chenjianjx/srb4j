<h2>Please login here</h2>


<p><font color="red">${err.errorCode}</font><p>
<p><font color="red">${err.userErrMsg}</font><p>


<form action="${pageContext.request.contextPath}/bo/portal/login" method="post">
	
	<ul>
		<li>Email: <input name="email" value="bbsadmin@nonexist.com"/> </li>
		<li>Password: <input name="password" type="password" value="abc123456"/> </li>		
		<li><input type="submit" value="Login"/></li>
	</ul>

</form>