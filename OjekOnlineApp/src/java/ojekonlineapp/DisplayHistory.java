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
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import ojekonlineservice.HistoryService_Service;

/**
 *
 * @author VINJERDIM
 */
@WebServlet(name = "DisplayHistory", urlPatterns = {"/DisplayHistory"})
public class DisplayHistory extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/OjekOnlineService/HistoryService.wsdl")
    private HistoryService_Service service;

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
        int accountID = CookieManager.getCurrentAccountID(request);
        String choice = request.getParameter("choice");
        if (choice.equals("0")) {
            displayHistoryPassenger(request, response, accountID);
        } else if (choice.equals("1")) {
            displayHistoryDriver(request, response, accountID);
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

    private void displayHistoryDriver(HttpServletRequest request, HttpServletResponse response, int accountID)
            throws ServletException, IOException {
        String stringDriverHistory = getHistoryDriver(accountID);
        if (stringDriverHistory.length() > 0) {
            JsonArray driverHistory;
            try (JsonReader reader = Json.createReader(new StringReader(stringDriverHistory))) {
                driverHistory = reader.readArray();
            }
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<ol class=\"history-list driver-list\">");
            if (!driverHistory.isEmpty()) {
                for (JsonValue x : driverHistory) {
                    JsonObject history = x.asJsonObject();
                    out.println("<li class=\"list-text-his\">");
                    out.println("<div class=\"history-pic-div\">");
                    out.println("<img class=\"profile-pic-his\" src=\"" + history.getString("picturePath") + "\" alt=\"\">");
                    out.println("</div>");
                    out.println("<span class=\"hide\">");
                    out.println("<a href=\"HideHistory?id=" + history.getString("transactionID") + "&driver=1\">HIDE</a>");
                    out.println("</span>");
                    out.println("<div class=\"his-text\">");
                    out.println("<div class=\"date-his\">" + history.getString("date") + "</div>");
                    out.println("<div><b>" + history.getString("fullname") + "</b></div>");
                    out.println("<div>" + history.getString("pick_location") + " - " + history.getString("dest_location") + "</div>");
                    out.println("<div> gave <span>" + history.getString("stars") + "</span> stars for this order<div>");
                    out.println("<div><div>And left comment:");
                    out.println("<div class=\"comment\">" + history.getString("review") + "</div>");
                    out.println(" </div></div></div></div></li>");   
                }
            } else {
                out.println("No transaction displayed");
            }
            out.println("</ol>");
        }
    }
    
    private void displayHistoryPassenger(HttpServletRequest request, HttpServletResponse response, int accountID)
            throws ServletException, IOException {
        String stringPassengerHistory = getHistoryPassenger(accountID);
        if (stringPassengerHistory.length() > 0) {
            JsonArray passengerHistory;
            try (JsonReader reader = Json.createReader(new StringReader(stringPassengerHistory))) {
                passengerHistory = reader.readArray();
            }
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<ol class=\"history-list order-list\">");
            if (!passengerHistory.isEmpty()) {
                for (JsonValue x : passengerHistory) {
                    JsonObject history = x.asJsonObject();
                    out.println("<li class=\"list-text-his\">");
                    out.println("<div class=\"history-pic-div\">");
                    out.println("<img class=\"profile-pic-his\" src=\"" + history.getString("picturePath") + "\" alt=\"\">");
                    out.println("</div>");
                    out.println("<span class=\"hide\">");
                    out.println("<a href=\"HideHistory?id=" + history.getString("transactionID") + "&driver=0\">HIDE</a>");
                    out.println("</span>");
                    out.println("<div class=\"his-text\">");
                    out.println("<div class=\"date-his\">" + history.getString("date") + "</div>");
                    out.println("<div class=\"name-his\"><b>" + history.getString("fullname") + "</b></div>");
                    out.println("<div>" + history.getString("pick_location") + " - " + history.getString("dest_location") + "</div>");
                    out.println("<div>You rated : " + history.getString("stars"));
                    out.println("<div><div>You commented:");
                    out.println("<div class=\"comment\">" + history.getString("review") + "</div>");
                    out.println(" </div></div></div></div></li>");   
                }
            } else {
                out.println("No transaction displayed");
            }
            out.println("</ol>");
        }
    }

    private String getHistoryDriver(int driverID) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekonlineservice.HistoryService port = service.getHistoryServicePort();
        return port.getHistoryDriver(driverID);
    }

    private String getHistoryPassenger(int passengerID) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekonlineservice.HistoryService port = service.getHistoryServicePort();
        return port.getHistoryPassenger(passengerID);
    }

}
