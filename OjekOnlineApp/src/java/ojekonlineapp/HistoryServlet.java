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
import javax.xml.ws.WebServiceRef;
import ojekonlineservice.HistoryService_Service;

/**
 *
 * @author nim_13515091
 */
@WebServlet(name = "HistoryServlet", urlPatterns = {"/History","/HideHistory"})
public class HistoryServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/OjekOnlineService/HistoryService.wsdl")
    private HistoryService_Service service;

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        if (!CookieManager.isSessionValid(request, response)) {
            response.sendRedirect("Logout");
        }
        String userPath = request.getServletPath();

        if (userPath.equals("/History")) {
            // use RequestDispatcher to forward request internally
            String url = "/WEB-INF/view/history" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            String transactionID = request.getParameter("id");
            String isDriver = request.getParameter("driver");
            Boolean result = updateHideStatus(transactionID, isDriver);
            response.sendRedirect("History");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        if (!CookieManager.isSessionValid(request, response)) {
            response.sendRedirect("Logout");
        }
        String userPath = request.getServletPath();

        // if addToCart action is called
        if (userPath.equals("/History")) {
            // TODO: Implement add product to cart action
            
        // if updateCart action is called
        } 

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view/history" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Boolean updateHideStatus(java.lang.String transactionID, java.lang.String isDriver) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekonlineservice.HistoryService port = service.getHistoryServicePort();
        return port.updateHideStatus(transactionID, isDriver);
    }

}
