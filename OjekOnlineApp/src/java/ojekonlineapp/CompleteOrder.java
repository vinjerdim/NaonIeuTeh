/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekonlineapp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author VINJERDIM
 */
@WebServlet(name = "CompleteOrder", urlPatterns = {"/ShowCompleteOrder"})
public class CompleteOrder extends HttpServlet {

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
        String driverID = request.getParameter("driver_id");
        String fullname = request.getParameter("fullname");
        String username = request.getParameter("driverUsername");
        String pickLoc = request.getParameter("pick_loc");
        String destLoc = request.getParameter("dest_loc");
        
        String responseString = 
                "<div class=\"completeorder-container\">"
                + "<input type=\"hidden\" name=\"pick_loc\" value=\"%1$s\">"
                + "<input type=\"hidden\" name=\"driver_id\" value=\"%2$s\">"
                + "<input type=\"hidden\" name=\"dest_loc\" value=\"%3$s\">"
                + "<img class=\"driver-completeorder-pic\" src=\"\" alt=\"No Image\">"
                + "<div class=\"driver-name-disp\">%4$s</div>"
                + "<div class=\"driver-fullname-disp\">%5$s</div>";
        responseString = String.format(responseString, pickLoc, driverID, destLoc, username, fullname);
        response.getWriter().println(responseString);
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
