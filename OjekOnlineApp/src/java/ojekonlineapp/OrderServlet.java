/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekonlineapp;

import java.io.IOException;
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
import ojekidentityservice.AccountService_Service;
import ojekonlineservice.OrderService_Service;

/**
 *
 * @author nim_13515091
 */
@WebServlet(name = "OrderServlet", urlPatterns = {"/Order", "/SelectDriver", 
    "/CompleteOrder", "/ChatDriver", "/AddTransaction", "/FindingOrder", 
    "/WaitingForOrder", "/GotAnOrder", "/OrderTransition"})
public class OrderServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/OjekIdentityService/AccountService.wsdl")
    private AccountService_Service service_1;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/OjekOnlineService/OrderService.wsdl")
    private OrderService_Service service;

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
        String userPath = request.getServletPath();
        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view/order" + userPath + ".jsp";

        // if addToCart action is called
        if (userPath.equals("/Order") || userPath.equals("/SelectDriver") 
                || userPath.equals("/ChatDriver") 
                || userPath.equals("/CompleteOrder")
                || userPath.equals("/FindingOrder")
                || userPath.equals("/WaitingForOrder")
                || userPath.equals("/GotAnOrder")) {
            request.getRequestDispatcher(url).forward(request, response);
        } else if (userPath.equals("/AddTransaction")) {
            finishOrder(request, response);
        } else if (userPath.equals("/OrderTransition")) {
            orderTransition(request, response);
        }
    }

    
    private void orderTransition(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int accountID = CookieManager.getCurrentAccountID(request);
        String jsonID = getAccountByID(accountID);
        JsonObject result;
        try (JsonReader reader = Json.createReader(new StringReader(jsonID))) {
            result = reader.readObject();
        }
        if (!result.getString("isDriver").equals("1")) {
            response.sendRedirect("Order");
        } else {
            response.sendRedirect("FindingOrder");
        }
    }
    
    private void finishOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String passengerID = Integer.toString(CookieManager.getCurrentAccountID(request));
        String driverID = request.getParameter("driver_id");
        String pickLoc = request.getParameter("pick_loc");
        String destLoc = request.getParameter("dest_loc");
        String stars = request.getParameter("rating");
        String review = request.getParameter("comment");
        if (addTransaction(passengerID, driverID, pickLoc, destLoc, stars, review)) {
            response.sendRedirect("History");
        } else {
            response.sendRedirect("Order");
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

    private Boolean addTransaction(java.lang.String passengerID, java.lang.String driverID, java.lang.String pickLoc,
            java.lang.String destLoc, java.lang.String stars, java.lang.String review) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekonlineservice.OrderService port = service.getOrderServicePort();
        return port.addTransaction(passengerID, driverID, pickLoc, destLoc, stars, review);
    }

    private String getAccountByID(int accountID) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekidentityservice.AccountService port = service_1.getAccountServicePort();
        return port.getAccountByID(accountID);
    }
}
