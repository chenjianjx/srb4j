#set($dollar = '$')
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="${package}.webapp.bo.portal.support.BoSessionHelper" %>

<%
    pageContext.setAttribute("alert", BoSessionHelper.removeFlashScopeAlert(session));
%>

<c:if test="${dollar}{alert != null}">
    <div class="alert alert-${dollar}{alert.type} alert-dismissible fade show" role="alert">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
      </button>
      <c:out value="${dollar}{alert.html}" escapeXml="false"/>
    </div>
</c:if>

