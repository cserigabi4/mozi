<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<jsp:include page="/FilmController"/>
<t:basic-layout-menu title="Kezdőlap">
        <div class="mt-5 container">
                <div class="row">
                    <c:forEach var="item" items="${requestScope.filmLista}">
                    <div class="col-sm text-center shadow-lg rounded m-3">
                        <img src="${item.boritoUrl}"
                             class="card-img-top rounded mx-auto d-block" style="width: 250px; height: 300px; ">
                        <div class="card-body mx-auto">
                            <h5 class="card-title text-center">${item.cim}</h5>
                            <a href="${pageContext.request.contextPath}/pages/film.jsp?filmId=${item.id}" class="btn btn-secondary mx-auto">Részletek</a>
                        </div>
                    </div>
                        </c:forEach>
                </div>
        </div>
</t:basic-layout-menu>
