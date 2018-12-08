<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>

    <c:choose>
        <c:when test="${it.users != null && not empty it.users}">
              <div class="card mb-3">

                <div class="card-header">
                  <i class="fas fa-table"></i>
                  Front Users</div>

                <div class="card-body">
                  <div class="table-responsive">
                    <table class="table table-bordered" id="userListTable" width="100%" cellspacing="0">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Principal</th>
                                <th>Source</th>
                                <th>Email</th>
                                <th>Email Verified</th>
                            </tr>
                        </thead>
                        <tbody>
                             <c:forEach items="${it.users}" var="user">
                                 <tr>
                                     <td>
                                         <c:out value="${user.id}"/>
                                     </td>
                                     <td>
                                         <c:out value="${user.principal}"/>
                                     </td>
                                     <td>
                                         <c:out value="${user.source}"/>
                                     </td>
                                     <td>
                                         <c:out value="${user.email}"/>
                                     </td>
                                     <td>
                                         <c:out value="${user.emailVerified? 'Y': 'N'}"/>
                                     </td>
                                 </tr>
                             </c:forEach>
                        </tbody>
                    </table>
                  </div>
                </div>

              </div>



        </c:when>

        <c:otherwise>
            <div>There are no front users yet</div>
        </c:otherwise>

    </c:choose>

</body>


<!-- for sitemesh to pick up -->
<content tag="local_script">
            <script>
                $(document).ready(function() {
                  $('#userListTable').DataTable();
                });
            </script>
</content>


