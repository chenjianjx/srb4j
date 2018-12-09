<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="my"   tagdir="/WEB-INF/tags/mytaglib"%>
<!DOCTYPE html>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Login</title>

    <!-- Bootstrap core CSS-->
    <link href="/bo-assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="/bo-assets/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Custom styles for this template-->
    <link href="/bo-assets/css/sb-admin.css" rel="stylesheet">

  </head>

  <body class="bg-dark">

    <div class="container">
      <div class="card card-login mx-auto mt-5">
        <div class="card-header">Srb4jfullsample Back Office</div>
        <div class="card-body">

          <form action="/bo/portal/login" method="post" class="needs-validation" novalidate>
            <div class="form-group">
                <%@ include file="/WEB-INF/jsp/bo-portal/common/form-errors.jsp" %>
            </div>

            <div class="form-group">
              <div class="form-label-group">

                <input type="text" id="username" name="username" value="${it.username}"
                    class="form-control <my:fieldValid field='username'/>" placeholder="Username" required="required" autofocus="autofocus">
                <label for="username">Username</label>
                <my:fieldError field="username"/>
              </div>
            </div>
            <div class="form-group">
              <div class="form-label-group">

                <input type="password" name="password" type="password" value="${it.password}"
                    id="inputPassword" class="form-control <my:fieldValid field='password'/>" required="required"  placeholder="Password" >
                <label for="inputPassword">Password</label>
                <my:fieldError field="password"/>
              </div>
            </div>
            <input class="btn btn-primary btn-block" value="Login" type="submit"/>
          </form>
        </div>
      </div>
    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="/bo-assets/vendor/jquery/jquery.min.js"></script>
    <script src="/bo-assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="/bo-assets/vendor/jquery-easing/jquery.easing.min.js"></script>

  </body>

</html>
