<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Editar libro</title>
        <jsp:include page="/cabecera.jsp"/>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>
    </head>
    <body>
        <jsp:include page="/menu.jsp"/>
        <div class="container">
            <div class="row">
                <h3>Nuevo libro</h3>
            </div>
            <div class="row">
                <div class=" col-md-7">
                    <c:if test="${ not empty requestScope.listaErrores}">
                        <div class="alert alert-danger">
                            <ul>
                                <c:forEach items="${requestScope.listaErrores}" var="error">
                                    <li>${error}</li>
                                    </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                    <form role="form" action="${pageContext.request.contextPath}/libros.do" method="POST">
                        <input type="hidden" name="op" value="editar"/>
                        <div class="well well-sm"><strong><span class="glyphicon glyphicon-asterisk"></span>Campos requeridos</strong></div>
                        <div class="form-group">
                            <label for="codigo">Codigo del libro:</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="codigo" id="codigo" readonly="true" placeholder="Ingresa el codigo del libro" value="${libro.codigo}">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="nombre">Nombre del libro:</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="nombre" id="nombre"  placeholder="Ingresa el nombre del libro" value="${libro.nombre}">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="nombre">Autor:</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="autor" id="nombre"  placeholder="Ingresa el nombre del autor" value="${libro.autor}">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="nombre">Editorial:</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="editorial" id="nombre"  placeholder="Ingresa la editorial" value="${libro.editorial}">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="precio">Precio:</label>
                            <div class="input-group">
                                <input type="number" step="0.01" class="form-control" id="precio" name="precio"  placeholder="Ingresa el precio del libro" value="${libro.precio}">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="existencias">Existencias:</label>
                            <div class="input-group">
                                <input type="number" class="form-control" id="existencias" name="existencias"  placeholder="Ingresa las existencias del libro" value="${libro.existencias}">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="nombre">Genero:</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="genero" id="nombre"  placeholder="Ingresa el genero" value="${libro.genero}">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                            </div>
                        </div>
                        <input type="submit" class="btn btn-info" value="Guardar" name="Guardar">
                        <a class="btn btn-danger" href="${pageContext.request.contextPath}/libros.do?op=listar">Cancelar</a>
                    </form>
                </div>
            </div>  
        </div>
        <jsp:include page="/pie.jsp"/>
    </body>
</html>



