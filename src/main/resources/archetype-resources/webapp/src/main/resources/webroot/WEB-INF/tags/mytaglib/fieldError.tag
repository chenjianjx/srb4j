<%@ tag body-content="empty" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="field"    rtexprvalue="true"  required="true" type="java.lang.String"  description="field name" %>

                <c:if test="${not empty it.err.fieldUserErrors && not empty it.err.fieldUserErrors[field]}">
                    <div class="invalid-feedback-show">
                        <c:out value="${it.err.fieldUserErrors[field]}"/>
                    </div>
                </c:if>