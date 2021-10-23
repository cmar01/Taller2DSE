/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.model;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Proxy;
import sv.edu.udb.www.entities.Libros;

/**
 *
 * @author ReyAlex
 */
public class LibrosModel {

    SessionFactory factory = HibernateUtil.getSessionFactory();

    public int insertarLibro(Libros lib) {
        Session ses = factory.openSession();
        try {
            Transaction tran = ses.beginTransaction();
            ses.save(lib);
            tran.commit();
            ses.close();
            return 1;
        } catch (Exception ex) {
            ses.close();
            ex.printStackTrace();
            return 0;
        }
    }

    public List<Libros> listarLibros() {
        Session ses = factory.openSession();
        try {
            Query consulta = ses.createQuery("select l FROM Libros l");
            List<Libros> lista = consulta.list();
            ses.close();
            return lista;
        } catch (Exception ex) {
            ses.close();
            ex.printStackTrace();
            return null;
        }
    }

    public Libros obtenerLibro(String codigo) {
        Session ses = factory.openSession();
        try {
            Libros lib = (Libros) ses.get(Libros.class, codigo);
            ses.close();
            return lib;
        } catch (Exception e) {
            e.printStackTrace();
            ses.close();
            return null;
        }
    }

    public int modificarLibro(Libros lib) {
        Session ses = factory.openSession();
        try {
            Transaction tran = ses.beginTransaction();
            ses.update(lib);
            tran.commit();
            ses.close();
            return 1;
        } catch (Exception ex) {
            ses.close();
            ex.printStackTrace();
            return 0;
        }
    }

    public int eliminarLibro(String id) {
        int filasAfectadas = 0;
        Session ses = factory.openSession();
        try {
            Transaction tran = ses.beginTransaction();
            Libros libros = (Libros) ses.get(Libros.class, id);
            if (libros != null) {
                ses.delete(libros);
                tran.commit();
                filasAfectadas = 1;
            }
            ses.close();
            return filasAfectadas;
        } catch (Exception ex) {
            ex.printStackTrace();
            ses.close();
            return filasAfectadas;
        }
    }

}
