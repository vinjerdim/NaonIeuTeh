/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekonlineapp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import ojekonlineservice.EditPrefService_Service;

/**
 *
 * @author User
 */
@WebServlet(name = "EditPreference", urlPatterns = {"/AddPreference", "/UpdatePreference", "/DeletePreference"})
public class EditPreference extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/OjekOnlineService/EditPrefService.wsdl")
    private EditPrefService_Service service;

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
        if (!CookieManager.isSessionValid(request, response)) {
            response.sendRedirect("Logout");
        }
        
        String path = request.getServletPath();
        int accountID = CookieManager.getCurrentAccountID(request);
        
        if (path.equals("/AddPreference")) {
            addPrefLoc(request, response, accountID);
        } else if (path.equals("/UpdatePreference")) {
            int prefID = Integer.parseInt(request.getParameter("id"));
            String location = request.getParameter("location");
            updatePreference(prefID, location);
        } else if (path.equals("/DeletePreference")) {
            int prefID = Integer.parseInt(request.getParameter("id"));
            deletePreference(prefID);
        }
        response.sendRedirect("EditPrefLoc");
    }

    private void addPrefLoc(HttpServletRequest request, HttpServletResponse response, int accountID)
            throws ServletException, IOException {
        String pref = request.getParameter("location");
        if (pref != null && !pref.trim().equals("")) {
            addPrefLoc(accountID, pref);
        }
    }

    private void addPrefLoc(int accountID, String loc) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekonlineservice.EditPrefService port = service.getEditPrefServicePort();
        port.addPreference(accountID, loc);
    } // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

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

    private Boolean updatePreference(int prefID, java.lang.String location) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekonlineservice.EditPrefService port = service.getEditPrefServicePort();
        return port.updatePreference(prefID, location);
    }

    private Boolean deletePreference(int prefID) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekonlineservice.EditPrefService port = service.getEditPrefServicePort();
        return port.deletePreference(prefID);
    }
}
