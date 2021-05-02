<%@ tag description="Basic Page template" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="header" fragment="true" %>
<%@ attribute name="footer" fragment="true" %>
<%@ attribute name="title" required="false" type="java.lang.String" %>

<t:basic-layout title="${title}">
    <jsp:attribute name="header">
        <jsp:invoke fragment="header" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <jsp:invoke fragment="footer" />
    </jsp:attribute>

    <jsp:body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav w-100">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/index.jsp">Filmek</a>
                    </li>
                    <c:if test="${sessionScope.felhasznalo.felhasznalonev == null}">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/pages/bejelentkezes.jsp">Bejelentkezés</a></li>
                    </c:if>
                    <c:if test="${sessionScope.felhasznalo.felhasznalonev != null}">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/FoglalasSzerkesztesController">Foglalasok</a>
                    </li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/KijelentkezesController">Kijelentkezés</a></li>
                    </c:if>
                </ul>

            </div>
        </nav>
        <jsp:doBody/>
    </jsp:body>
</t:basic-layout>