package de.patkab;

import de.patkab.shoppinglist.model.ShoppingListEntry;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/test2")
public class TestServlet2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    // within a servlet, a user transaction must be started manually
    @Resource
    private UserTransaction ut;

    public void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String error = null;
        try {
            //EntityManagerFactory emf = Persistence.createEntityManagerFactory("shoppinglist-jpa");
            //EntityManager em = emf.createEntityManager();
            ShoppingListEntry entry = new ShoppingListEntry();
            entry.setName("testentry");
            ut.begin();
            em.persist(entry);
            ut.commit();
        } catch (Exception e) {
            e.printStackTrace();
            error = e.getMessage();
        }

        final PrintWriter writer =
                response.getWriter();
        response.setContentType(
                "text/html;charset=UTF-8");
        writer.println("<!DOCTYPE html>");
        writer.println("<html><body>");
        writer.println("Connected: " + em.isOpen());

        if (error != null) {
            writer.println("<BR>Error: " + error);
        } else {
            writer.println("<BR>Success!");
        }

        writer.println(
                "<BR>Test finished!</body></html>");
    }
}
