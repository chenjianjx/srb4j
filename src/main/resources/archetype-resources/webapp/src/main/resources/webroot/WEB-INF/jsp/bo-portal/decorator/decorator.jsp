<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><decorator:title/></title>

    <!-- Bootstrap core CSS-->
    <link href="/bo-assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="/bo-assets/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Page level plugin CSS-->
    <link href="/bo-assets/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="/bo-assets/css/sb-admin.css" rel="stylesheet">

  </head>


  <body id="page-top">

    <%@ include file="/WEB-INF/jsp/bo-portal/common/topNav.jsp" %>

    <%@ include file="/WEB-INF/jsp/bo-portal/common/flashScopeAlert.jsp" %>

    <div id="wrapper">

      <%@ include file="/WEB-INF/jsp/bo-portal/common/sidebar.jsp" %>

      <div id="content-wrapper">

        <div class="container-fluid">

          <decorator:body />

        </div>
        <!-- /.container-fluid -->

        <%@ include file="/WEB-INF/jsp/bo-portal/common/footer.jsp" %>

      </div>
      <!-- /.content-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
      <i class="fas fa-angle-up"></i>
    </a>


    <!-- Bootstrap core JavaScript-->
    <script src="/bo-assets/vendor/jquery/jquery.min.js"></script>
    <script src="/bo-assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="/bo-assets/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Page level plugin JavaScript-->
    <script src="/bo-assets/vendor/datatables/jquery.dataTables.js"></script>
    <script src="/bo-assets/vendor/datatables/dataTables.bootstrap4.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="/bo-assets/js/sb-admin.js"></script>


    <decorator:getProperty property="page.local_script"/>
  </body>

</html>
