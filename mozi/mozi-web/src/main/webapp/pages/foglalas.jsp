<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<t:basic-layout title="Foglalás">
    <jsp:body>
    <c:set var="x" scope="session" value="1"/>
    <div class="container">
        <div class="text-center shadow-lg m-3">
            <nav class="navbar navbar-dark bg-dark">
            </nav>
                ${requestScope.film}
            <form action="${pageContext.request.contextPath}/VetitesFoglalasController" method="post">
                <div class="form-group">
                <c:forEach var="i" begin="1" end="${requestScope.terem.sorokSzama}">
                    <span class="">${i}. sor</span>
                    <div class="row mt-1">
                        <c:forEach var="j" begin="1" end="${requestScope.terem.oszlopokSzama}">
                            <div class="text-center mr-3 szek col mt-2 mb-3">
                                <label class="form-check-label" for="${x}">${x}</label>
                                <c:set var="contains" value="false" />
                                <c:forEach var="item" items="${requestScope.helyek}">
                                    <c:if test="${item eq x}">
                                        <c:set var="contains" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contains == false}">
                                  <input class="form-check-input" type="checkbox" id="${x}" name="${x}" value="${x}">
                                 </c:if>
                                <c:set var="x" scope="session" value="${x+1}"/>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
                <button id="submit" type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
        </div>
    </div>
    </jsp:body>
</t:basic-layout>