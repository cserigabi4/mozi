<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:basic-layout-menu title="Bejelentkezés">

    <c:if test="${requestScope.hiba != null}">
        <div class="alert alert-danger" role="alert">
                ${requestScope.hiba}
        </div>
    </c:if>

    <div class="container w-25">
        <div class="text-center rounded shadow-lg m-3">
            <h1 class="mb-3">Bejelentkezés</h1>
            <form action="${pageContext.request.contextPath}/BejelentkezesController" method="post">
                <div class="form-group">
                    <label for="felhasznalonev">Felhasználónév</label>
                    <input required name="felhasznalonev" type="text" class="form-control"
                           placeholder="Felhasználónév"/>
                </div>
                <div class="form-group mt-2">
                    <label for="jelszo">Jelszó</label>
                    <input required name="jelszo" type="password" class="form-control"
                           placeholder="Jelszó"/>
                </div>
                <button id="submit" type="submit" class="btn btn-success mt-3">Bejelentkezés</button>
            </form>
        </div>
    </div>

    <div class="container w-25 mt-5">
        <div class="text-center rounded shadow-lg m-3">
            <h1 class="mb-3">Regisztrálás</h1>
            <form action="../RegisztralasController" method="post">
                <div class="form-group">
                    <label for="nev">Teljes Név</label>
                    <input required name="nev" type="text" class="form-control" id="nev"
                           placeholder="Teljes Név"/>
                </div>
                <div class="form-group">
                    <label for="felhasznalonev">Felhasználónév</label>
                    <input required name="felhasznalonev" type="text" class="form-control" id="felhasznalonev"
                           placeholder="Felhasználónév"/>
                </div>
                <div class="form-group">
                    <label for="jelszo">Jelszó</label>
                    <input required name="jelszo" type="password" class="form-control" id="jelszo"
                           placeholder="Jelszó"/>
                </div>
                <button id="" type="submit" class="btn btn-success mt-3">Regisztrálás</button>
            </form>
        </div>
    </div>

</t:basic-layout-menu>