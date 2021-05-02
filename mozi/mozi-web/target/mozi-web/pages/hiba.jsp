<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:basic-layout-menu title="Bejelentkezés">

    <c:if test="${requestScope.hiba != null}">
        <div class="alert alert-danger" role="alert">
                ${requestScope.hiba}
        </div>
    </c:if>
</t:basic-layout-menu>