/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekonlineapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import ojekonlineservice.ProfileService_Service;

/**
 *
 * @author User
 */
@WebServlet(name = "EditProfileServlet", urlPatterns = {"/ThinProfile"})
public class EditProfileBefore extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/OjekOnlineService/ProfileService.wsdl")
    private ProfileService_Service service;

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
        String choice = request.getParameter("choice");
        int accountID = CookieManager.getCurrentAccountID(request);
        JsonObject account;
        try (JsonReader reader = Json.createReader(new StringReader(getAccount(accountID)))) {
            account = reader.readObject();
        }
        switch (choice) {
            case "1":
                response.getWriter().println("<input class='ep-textarea' type='text' name='yourname' "
                        + "value='"+ account.getString("fullname") +"'>");
                break;
            case "2":
                response.getWriter().println("<input class='ep-textarea' type='text' name='yourphone' "
                        + "value='"+ account.getString("phone") +"'>");
                break;
            case "3":
                if (account.getString("isDriver").equals("0")) {
                    response.getWriter().println("<input type='checkbox' name='isdriver'>");
                } else {
                    response.getWriter().println("<input type='checkbox' name='isdriver' checked='checked'>");
                }
                break;
            default:
                break;
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

    private String getAccount(int accountID) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekonlineservice.ProfileService port = service.getProfileServicePort();
        return port.getAccount(accountID);
    }

}
