<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>



<jsp:include page="/FilmLeirasController?filmId=${param.filmId}"/>

<t:basic-layout-menu title="${requestScope.film.cim}">

<div class="container">
    <div class="row shadow-lg rounded m-3">
        <div class="col-sm text-center mt-3 mb-2">
            <h1 class="mb-2">${requestScope.film.cim}</h1>
            <img src="${requestScope.film.boritoUrl}"
                 class="card-img-top rounded mx-auto d-block mb-2" style="width: 500px; height: 650px; ">
        </div>
        <div class="col-sm mt-3">
            <h6 class="mt-3">Rendező ${requestScope.film.rendező}</h6>
            <h6>Hossz ${requestScope.film.hossz} perc</h6>
            <h6>Korhatár ${requestScope.film.korhatar}</h6>
            <h6>Szereplők</h6>
            <ul>
            <c:forEach var="item" items="${requestScope.film.szereplok}">
                <li>${item.nev}</li>
            </c:forEach>
            </ul>
            <h6>Leírás</h6>
            <span>${requestScope.film.leiras}</span>
        </div>
    </div>
    <div class="row shadow-lg rounded m-3 text-center">
        <h1>Vetítések</h1>
        <c:if test="${sessionScope.felhasznalo.felhasznalonev == null}">
            <h3>A vetítések megtekítéséhez be kell jelentkezni!</h3>
            <a class="nav-link" href="${pageContext.request.contextPath}/pages/bejelentkezes.jsp">Bejelentkezés</a>
        </c:if>
        <c:if test="${sessionScope.felhasznalo.felhasznalonev != null}">
            ${requestScope.vetitesek}
            <table class="text-center">
                <tr>
                    <th>Dátum</th>
                    <th>Óra</th>
                    <th>Terem</th>
                    <th>Ár</th>
                </tr>
                <c:forEach var="item" items="${requestScope.vetites}">
                    <tr>
                        <td>${item.datum}</td>
                        <td>${item.ido}</td>
                        <td>${item.terem}</td>
                        <td>${item.ar}</td>
                        <td>  <a href="../VetitesFoglalasController?vetitesId=${item.id}&film=${requestScope.film.cim}"><button class="btn btn-success btn-sm">Foglal</button></a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
    <c:if test="${requestScope.film.elozetesUrl != null}">
    <div class="row shadow-lg rounded m-3 h-75">

        <iframe src="${requestScope.film.elozetesUrl}">
        </iframe>
    </div>
    </c:if>
    </div>




</t:basic-layout-menu>

