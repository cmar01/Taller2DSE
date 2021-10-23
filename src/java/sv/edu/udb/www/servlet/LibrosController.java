/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import sv.edu.udb.www.entities.Libros;
import sv.edu.udb.www.model.LibrosModel;
import sv.edu.udb.www.utilidades.IngresoDatos;

/**
 *
 * @author Usuario
 */
@WebServlet(name = "LibrosController", urlPatterns = {"/libros.do"})
public class LibrosController extends HttpServlet {

    LibrosModel modelo = new LibrosModel();
    Libros libro;
    ArrayList<String> listaErrores = new ArrayList<String>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String operacion = request.getParameter("op");
            switch (operacion) {
                case "nuevo":
                    nuevo(request, response);
                    break;
                case "insertar":
                    insertar(request, response);
                    break;
                case "listar":
                    listar(request, response);
                    break;
                case "detalles":
                    detalles(request, response);
                    break;
                case "obtener":
                    obtenerLibro(request, response);
                    break;
                case "editar":
                    editar(request, response);
                    break;
                case "eliminar":
                    eliminar(request, response);
                    break;
            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void nuevo(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/libros/nuevoLibro.jsp").forward(request, response);
        } catch (IOException | ServletException ex) {
            ex.printStackTrace();
        }
    }

    private void insertar(HttpServletRequest request, HttpServletResponse response) {
        try {
            listaErrores.clear();
            libro = new Libros();
            libro.setCodigo(request.getParameter("codigo"));
            libro.setNombre(request.getParameter("nombre"));
            libro.setAutor(request.getParameter("autor"));
            libro.setEditorial(request.getParameter("editorial"));
            libro.setGenero(request.getParameter("genero"));

            if (!IngresoDatos.esCodigoLibro(libro.getCodigo())) {
                listaErrores.add("El codigo del libro debe tener el formato LIB######");
            }
            if (!IngresoDatos.esCadena(libro.getNombre())) {
                listaErrores.add("Debes ingresar el nombre del libro");
            }
            if (!IngresoDatos.esCadena(libro.getAutor())) {
                listaErrores.add("Debes ingresar el nombre del autor");
            }
            if (!IngresoDatos.esCadena(libro.getEditorial())) {
                listaErrores.add("Debes ingresar el nombre de la editorial");
            }
            if (!IngresoDatos.esEnteroPositivo(request.getParameter("existencias"))) {
                listaErrores.add("Las existencias deben ser mayor a 0");
            } else {
                libro.setExistencias(Integer.parseInt(request.getParameter("existencias")));
            }
            if (!IngresoDatos.esCadena(libro.getGenero())) {
                listaErrores.add("Debes ingresar el genero del libro");
            }
            if (!IngresoDatos.esDecimalPositivo(request.getParameter("precio"))) {
                listaErrores.add("Las existencias deben ser mayor a 0");
            } else {
                libro.setPrecio(request.getParameter("precio"));
            }

            if (listaErrores.size() > 0) {
                request.setAttribute("listaErrores", listaErrores);
                request.setAttribute("libro", libro);
                nuevo(request, response);
            } else {
                if (modelo.insertarLibro(libro) > 0) {
                    request.getSession().setAttribute("exito", "Libro registrado exitosamente");
                    response.sendRedirect(request.getContextPath() + "/libros.do?op=listar");
                } else {
                    //insercion fallida
                    request.getSession().setAttribute("fracaso", "No se pudo insertar el libro");
                    response.sendRedirect(request.getContextPath() + "/libros.do?op=nuevo");
                    //request.getRequestDispatcher("/editoriales.do?op=nuevo").forward(request, response);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("listaLibros", modelo.listarLibros());

            request.getRequestDispatcher("/libros/listaLibros.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace();
        }
    }

    private void detalles(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String codigo = request.getParameter("id");
            libro = modelo.obtenerLibro(codigo);

            JSONObject json = new JSONObject();
            json.put("nombre", libro.getNombre());
            json.put("autor", libro.getAutor());
            json.put("editorial", libro.getEditorial());
            json.put("precio", libro.getPrecio());
            json.put("existencias", libro.getExistencias());
            json.put("genero", libro.getGenero());
            json.put("codigo", codigo);
            out.print(json);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void obtenerLibro(HttpServletRequest request, HttpServletResponse response) {

        try {
            String codigo = request.getParameter("id");
            libro = modelo.obtenerLibro(codigo);
            //verificamos que editorial no sea nulo
            if (libro != null) {
                request.setAttribute("libro", libro);
                request.getRequestDispatcher("/libros/editarLibro.jsp").forward(request, response);

            } else {
                response.sendRedirect(request.getContextPath() + "/error404.jsp");
            }
        } catch (IOException | ServletException ex) {
            ex.printStackTrace();
        }
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) {
        try {
            listaErrores.clear();
            libro = new Libros();
            libro.setCodigo(request.getParameter("codigo"));
            libro.setNombre(request.getParameter("nombre"));
            libro.setAutor(request.getParameter("autor"));
            libro.setEditorial(request.getParameter("editorial"));
            libro.setGenero(request.getParameter("genero"));


            if (!IngresoDatos.esCodigoLibro(libro.getCodigo())) {
                listaErrores.add("El codigo del libro debe tener el formato LIB######");
            }
            if (!IngresoDatos.esCadena(libro.getNombre())) {
                listaErrores.add("Debes ingresar el nombre del libro");
            }
            if (!IngresoDatos.esCadena(libro.getAutor())) {
                listaErrores.add("Debes seleccionar el autor");
            }
            if (!IngresoDatos.esCadena(libro.getEditorial())) {
                listaErrores.add("Debes seleccionar el editorial");
            }
            if (!IngresoDatos.esEnteroPositivo(request.getParameter("existencias"))) {
                listaErrores.add("Las existencias deben ser mayor a 0");
            } else {
                libro.setExistencias(Integer.parseInt(request.getParameter("existencias")));
            }
            if (!IngresoDatos.esCadena(libro.getGenero())) {
                listaErrores.add("Debes ingresar el genero del libro");
            }
            if (!IngresoDatos.esDecimalPositivo(request.getParameter("precio"))) {
                listaErrores.add("Las existencias deben ser mayor a 0");
            } else {
                libro.setPrecio(request.getParameter("precio"));
            }

            if (listaErrores.size() > 0) {
                request.setAttribute("listaErrores", listaErrores);
                request.setAttribute("libro", libro);
                request.getRequestDispatcher("/libros/editarLibro.jsp").forward(request, response);
            } else {
                if (modelo.modificarLibro(libro) > 0) {
                    request.getSession().setAttribute("exito", "Libro modificado exitosamente");
                    response.sendRedirect(request.getContextPath() + "/libros.do?op=listar");
                } else {
                    //insercion fallida
                    request.getSession().setAttribute("fracaso", "No se pudo modificar el libro");
                    response.sendRedirect(request.getContextPath() + "/libros.do?op=listar");
                    //request.getRequestDispatcher("/editoriales.do?op=nuevo").forward(request, response);
                }
            }
        } catch (IOException | ServletException ex) {
            ex.printStackTrace();
        }
    }
    
        private void eliminar(HttpServletRequest request, HttpServletResponse response) {

        try {
            String codigo = request.getParameter("id");
            int fBorradas = modelo.eliminarLibro(codigo);
            if (fBorradas > 0) {
                request.getSession().setAttribute("exito", "Libro eliminado exitosamente");
                request.getRequestDispatcher("/libros.do?op=listar").forward(request, response);
            } else {
                request.getSession().setAttribute("fracaso", "Libro no pudo ser eliminado");
                request.getRequestDispatcher("/libros.do?op=listar").forward(request, response);
            }

        } catch (ServletException | IOException ex) {
            ex.printStackTrace();
        }

    }

}
