/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekonlineapp;

import java.io.IOException;
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
import ojekonlineservice.ProfileService_Service;

/**
 *
 * @author VINJERDIM
 */
@WebServlet(name = "PrefLocTabel", urlPatterns = {"/PrefLocTabel"})
public class PrefLocTabel extends HttpServlet {

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
        Cookie refreshToken = CookieManager.getCookieByName(request, "refreshToken");
        int accountID = Integer.parseInt(CookieManager.getJsonValue("accountID", refreshToken));
        displayTabel(request, response, accountID);
    }

    private void displayTabel(HttpServletRequest request, HttpServletResponse response, int accountID)
            throws ServletException, IOException {
        String stringPrefLoc = getPrefLoc(accountID);
        JsonArray location;
        try (JsonReader reader = Json.createReader(new StringReader(stringPrefLoc))) {
            location = reader.readArray();
        }
        int i = 1;
        String row
                = "<tr class=\"rows\">"
                + "<td id=\"count\">%1$s</td>"
                + "<td><input type='hidden' value='%2$s' name=\"location\"/>"
                + "<span id='location'>%2$s</span>"
                + "<input type='hidden' value='%3$s'/></td>"
                + "<td>"
                + "<input type=\"button\" name=\"check-button\" id=\"check-button\" onclick=\"updatePrefLoc(this)\" value=\"Save\"/>"
                + "<input type=\"button\" name=\"edit-button\" id=\"edit-button\" onclick=\"changeToInput(this)\" value=\"Edit\"/>"
                + "<input type=\"button\" name=\"remove-button\" id=\"remove-button\" onclick=\"deletePrefLoc(this)\" value=\"Delete\"/>"
                + "</td>"
                + "</tr>";
        String responseString = "";
        if (!location.isEmpty()) {
            for (JsonValue x : location) {
                JsonObject loc = x.asJsonObject();
                responseString = responseString + String.format(row,
                        i, loc.getString("location"), loc.getString("prefID"));
                i++;
            }
            response.getWriter().println(responseString);
        } else {
            response.getWriter().println("");
        }
    }

    private String getAccount(int accountID) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekonlineservice.ProfileService port = service.getProfileServicePort();
        return port.getAccount(accountID);
    }

    private String getPrefLoc(int accountID) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekonlineservice.ProfileService port = service.getProfileServicePort();
        return port.getPrefLoc(accountID);
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
