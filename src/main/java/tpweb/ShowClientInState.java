/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import simplejdbc.CustomerEntity;
import simplejdbc.DAO;
import simplejdbc.DataSourceFactory;

/**
 *
 * @author pedago
 */
public class ShowClientInState extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShowClientInState</title>");
            out.println("</head>");
            out.println("<body>");

            try {
                String val = request.getParameter("state");
                if (val == null) {
                    throw new Exception("La paramètre state n'a pas été transmis");
                }
                String state = String.valueOf(val);
                
                DAO dao = new DAO(DataSourceFactory.getDataSource());
                List<CustomerEntity> customerStates = dao.customersInState(state);
                
                if (customerStates == null) {
                    throw new Exception("Etat inconnu");
                }
                out.printf("<table border=1>");
                out.printf("<tr> <th>id</th> <th>Name</th> <th>Adress</th> </tr>");
                for (CustomerEntity c : customerStates) {
                    out.printf("<tr> <td>%d</td> <td>%s</td> <td>%s</td> </tr>",
                    c.getCustomerId(),
                    c.getName(),
                    c.getAddressLine1()
                    );

                }
                out.println("</table>");
                out.println("</body>");
                out.println("</html>");

            } catch (Exception ex) {
                Logger.getLogger("servlet").log(Level.SEVERE, "Erreur de traitement", ex);
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

}
