#set($dollar = '$')
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

          <h1>The Dashboard</h1>
          <hr>
          <p>Welcome, <c:out value="${dollar}{sessionStaffUser.username}"/></p>