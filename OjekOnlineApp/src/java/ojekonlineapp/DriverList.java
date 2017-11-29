/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekonlineapp;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import ojekonlineservice.OrderService_Service;

/**
 *
 * @author VINJERDIM
 */
@WebServlet(name = "DriverList", urlPatterns = {"/ShowDriverList"})
public class DriverList extends HttpServlet {

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
        int accountID = CookieManager.getCurrentAccountID(request);
        showDriverList(request, response, accountID);
    }

    private void showDriverList(HttpServletRequest request, HttpServletResponse response,
            int currentUserID) throws ServletException, IOException {
        String preferredDriver = request.getParameter("pref-driver");
        String pickLoc = request.getParameter("pick-point");
        String destLoc = request.getParameter("destination");

        JsonObject result;
        try (JsonReader reader = Json.createReader(
                new StringReader(getDrivers(preferredDriver, currentUserID, destLoc)))) {
            result = reader.readObject();
        }
        response.getWriter().println(result.toString());
        /*
        String preferenceData = "";
        String otherData = "";

        if (result != null) {
            JsonArray preference = result.getJsonArray("preference");
            JsonArray others = result.getJsonArray("others");
            String listItem
                    = "<form method=\"POST\" action=\"ChatDriver\">"
                    + "<div class=\"driver-content\">"
                    + "<img src=\"\" alt=\"no image\" class =\"driver-pic\">"
                    + "<div class=\"driver-name-disp\" >"
                    + "%1$s</div>"
                    + "<div class=\"driver-rating-disp\" >"
                    + "★ %2$.2f <span class=\"vote-disp\">(%3$s votes)</span></div>"
                    + "<input type=\"hidden\" name=\"driver_id\" value=\"%4$s\">"
                    + "<input type=\"hidden\" name=\"pick_loc\" value=\"%5$s\">"
                    + "<input type=\"hidden\" name=\"dest_loc\" value=\"%6$s\">"
                    + "<input type=\"hidden\" name=\"driverUsername\" value=\"%7$s\">"
                    + "<input type=\"hidden\" name=\"fullname\" value=\"%1$s\">"
                    + "<input type=\"submit\" value=\"I Choose You\" class=\"accept-button select-driver-btn\">"
                    + "</div>"
                    + "</form>";

            if (!preference.isEmpty()) {
                for (JsonValue x : preference) {
                    JsonObject driver = x.asJsonObject();
                    preferenceData = preferenceData + String.format(listItem,
                            driver.getString("fullname"), Double.valueOf(driver.getString("rating")),
                            driver.getInt("vote"), driver.getString("driverID"),
                            pickLoc, destLoc, driver.getString("username"));
                }
            } else {
                preferenceData = "<div class=\"driver-not-found\">Nothing to display :(</div>";
            }

            if (!others.isEmpty()) {
                for (JsonValue x : others) {
                    JsonObject driver = x.asJsonObject();
                    otherData = otherData + String.format(listItem,
                            driver.getString("fullname"), Double.valueOf(driver.getString("rating")),
                            driver.getInt("vote"), driver.getString("driverID"),
                            pickLoc, destLoc, driver.getString("username"));
                }
            } else {
                otherData = "<div class=\"driver-not-found\">Nothing to display :(</div>";
            }
        } else {
            preferenceData = "<div class=\"driver-not-found\">Nothing to display :(</div>";
            otherData = "<div class=\"driver-not-found\">Nothing to display :(</div>";
        }
        String responseString
                = "<div id=\"pref-driver\" class=\"select-driver\">"
                + "<div class= \"selectdriver-header\">"
                + "Preferred Drivers:%1$s"
                + "</div></div>"
                + "<div id=\"other-driver\" class=\"select-driver\">"
                + "<div class=\"selectdriver-header\">"
                + "Other Drivers:%2$s"
                + "</div></div>";
        responseString = String.format(responseString, preferenceData, otherData);
        response.getWriter().println(responseString);
        */
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

    private String getDrivers(java.lang.String preferredDriver, int currentUserID, java.lang.String prefloc) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekonlineservice.OrderService port = service.getOrderServicePort();
        return port.getDrivers(preferredDriver, currentUserID, prefloc);
    }
}