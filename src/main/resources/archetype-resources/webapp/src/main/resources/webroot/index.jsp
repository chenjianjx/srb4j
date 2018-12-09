#set( $capitalizedRootArtifactId = "$rootArtifactId.substring(0,1).toUpperCase()$rootArtifactId.substring(1)" )
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${capitalizedRootArtifactId} Homepage</title>
</head>

<body>

    <h1>${capitalizedRootArtifactId} RESTFul Backend</h1>

<div>
    <ul>
        <li><a href="/fo-rest-doc">Frontend-oriented RESTFul API Doc</a></li>
    </ul>
    <ul>
        <li><a href="/fo/rest/swagger.json">Swagger JSON</a></li>
    </ul>


</div>
</body>

</html>