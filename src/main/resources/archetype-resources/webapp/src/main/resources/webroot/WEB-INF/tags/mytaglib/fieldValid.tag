<%@ tag body-content="empty" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>


<%@ attribute name="field"    rtexprvalue="true"  required="true" type="java.lang.String"  description="field name" %>

                <c:if test="${not empty it.err.fieldUserErrors && not empty it.err.fieldUserErrors[field]}">
                    <c:out value="is-invalid"/>
                </c:if>