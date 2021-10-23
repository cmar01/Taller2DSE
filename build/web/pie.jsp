<%-- 
    Document   : pie
    Created on : 06-05-2017, 02:21:28 PM
    Author     : Usuario
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script>
    <c:if test="${not empty exito}">
    alertify.success('${exito}');
        <c:set var="exito" scope="session" value=""/>
    </c:if>
    <c:if test="${not empty fracaso}">
    alertify.error('${fracaso}');
        <c:set var="fracaso" scope="session" value=""/>
    </c:if>
</script>