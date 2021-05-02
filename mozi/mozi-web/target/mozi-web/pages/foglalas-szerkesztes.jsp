<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<t:basic-layout-menu title="Foglalas Lista">
    <div class="container">
        <div class="row shadow-lg rounded m-3">
            <c:if test="${requestScope.hiba != null}">
            <div class="alert alert-danger" role="alert">
                    ${requestScope.hiba}
            </div>
            </c:if>
            <table class="text-center">
                <tr>
                    <th>Film</th>
                    <th>Dátum</th>
                    <th>Óra</th>
                    <th>Helyek</th>
                    <th>Ár</th>
                    <th>Művelet</th>
                </tr>
                <c:forEach var="item" items="${requestScope.foglalasok}">
                    <tr>
                        <td>${item.film.cim}</td>
                        <td>${item.vetites.datum}</td>
                        <td>${item.vetites.ido}</td>
                        <td>${item.helyek}</td>
                        <td>${item.ar}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/VetitesFoglalasController?vetitesId=${item.vetites.id}&film=${requestScope.film.cim}&foglalasId=${item.id}"><button class="btn btn-success btn-sm">Szerkeszt</button></a>
                            <a href="${pageContext.request.contextPath}/FoglalasTorlesController?foglalasId=${item.id}&vetitesId=${item.vetites.id}"><button class="btn btn-danger btn-sm">Töröl</button></a>
                        </td>

                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</t:basic-layout-menu>
