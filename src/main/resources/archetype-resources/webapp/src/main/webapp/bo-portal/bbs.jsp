<h2>The BBS</h2>

<h3>Your User ID is ${sessionBoUserId}</h3>


<p>
	<font color="red">${postListResult.err.errorCode}</font>
<p>
<p>
	<font color="red">${postListResult.err.userErrMsg}</font>
<p>

<h3>The Posts</h3>

<textarea readonly="true" cols="100" rows="30">${postListResult.data}</textarea>

