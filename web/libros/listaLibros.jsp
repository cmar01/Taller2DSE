<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lista de libros</title>
        <jsp:include page="/cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="/menu.jsp"/>
        <div class="container">
            <div class="row">
                <h3>Lista de libros</h3>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <a class="btn btn-primary btn-md" href="${pageContext.request.contextPath}/libros.do?op=nuevo">Nuevo libro</a>
                    <br><br>
                    <table class="table table-striped table-bordered table-hover" id="tabla">
                        <thead>
                            <tr>
                                <th>Codigo</th>
                                <th>Nombre</th>
                                <th>Existencias</th>
                                <th>Precio</th>
                                <th>Editorial</th>
                                <th>Autor</th>
                                <th>Genero</th>
                                <th>Operaciones</th>
                            </tr>
                        </thead>
                        <tbody>         
                            <c:forEach items="${requestScope.listaLibros}" var="libros">                                                   
                                <tr>
                                    <td>${libros.codigo}</td>
                                    <td>${libros.nombre}</td>
                                    <td>${libros.existencias}</td>
                                    <td>${libros.precio}</td>                                   
                                    <td>${libros.editorial}</td>
                                    <td>${libros.autor}</td>                       
                                    <td>${libros.genero}</td>
                                    <td>
                                        <a class="btn btn-success" href="javascript:void(0)" onclick="detalles('${libros.codigo}')"><span class="glyphicon glyphicon-book"></span> Detalles</a>
                                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/libros.do?op=obtener&id=${libros.codigo}">
                                            <span class="glyphicon glyphicon-edit"></span> Editar</a>
                                        <a  class="btn btn-danger" href="javascript:eliminar('${libros.codigo}')">
                                            <span class="glyphicon glyphicon-trash"></span> Eliminar</a>
                                    </td>
                                </tr>
                            </c:forEach>                                             
                        </tbody>
                    </table>
                </div>

            </div>                    
        </div> 

        <!-- Bootstrap modal -->
        <div class="modal fade" id="modal" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h3 class="titulo-modal"></h3>
                    </div>
                    <div class="modal-body form">
                        <ul class="list-group">
                            <li class="list-group-item"><b>Nombre del libro: </b> <span id="nombre"></span></li>
                            <li class="list-group-item"> <b>Precio: </b> $<span id="precio"></span></li>
                            <li class="list-group-item"> <b>Existencias: </b> <span id="existencias"></span></li>
                            <li class="list-group-item"> <b>Autor: </b> <span id="autor"></span></li>
                            <li class="list-group-item"> <b>Editorial: </b> <span id="editorial"></span></li>
                            <li class="list-group-item"> <b>Genero: </b> <span id="genero"></span></li>
                        </ul>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <!-- End Bootstrap modal -->

        <script>
            $(document).ready(function () {
                $('#tabla').DataTable();
            });

            function detalles(id) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/libros.do?op=detalles&id=" + id,
                    type: "GET",
                    dataType: 'JSON',
                    success: function (datos) {
                        $('#nombre').text(datos.nombre);
                        $('#precio').text(datos.precio);
                        $('#existencias').text(datos.existencias);
                        $('#autor').text(datos.autor);
                        $('#editorial').text(datos.editorial);
                        $('#genero').text(datos.genero);
                        $('#modal').modal('show');
                        $('.titulo-modal').text(datos.nombre);
                    }
                });
            }

            function eliminar(id) {
                alertify.confirm("Realmente desea eliminar este Libro?", function (e) {
                    if (e) {
                        location.href = "libros.do?op=eliminar&id=" + id;
                    }
                });
            }
        </script>
        <jsp:include page="/pie.jsp"/>
    </body>
</html>


