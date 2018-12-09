#set( $capitalizedRootArtifactId = "$rootArtifactId.substring(0,1).toUpperCase()$rootArtifactId.substring(1)" )
#set($dollar = '$')
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


        <!-- Sticky Footer -->
        <footer class="sticky-footer">
          <div class="container my-auto">
            <div class="copyright text-center my-auto">
              <jsp:useBean id="now" class="java.util.Date" />
              <fmt:formatDate var="year" value="${dollar}{now}" pattern="yyyy" />
              <span>Copyright @ ${capitalizedRootArtifactId} ${dollar}{year}</span>
            </div>
          </div>
        </footer>