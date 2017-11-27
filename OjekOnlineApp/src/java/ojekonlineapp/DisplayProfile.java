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
@WebServlet(name = "DisplayProfile", urlPatterns = {"/DisplayProfile"})
public class DisplayProfile extends HttpServlet {

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
        if (!CookieManager.isSessionValid(request, response)) {
            response.sendRedirect("Logout");
        }
        int accountID = CookieManager.getCurrentAccountID(request);
        displayProfile(request, response, accountID);
    }

    private void displayProfile(HttpServletRequest request, HttpServletResponse response, int accountID)
            throws ServletException, IOException {
        String stringAccount = getAccount(accountID);
        if (stringAccount.length() > 0) {
            JsonObject account;
            try (JsonReader reader = Json.createReader(new StringReader(stringAccount))) {
                account = reader.readObject();
            }
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<div id='profile-pic-div'>");
            response.getWriter().println("<img class='profile-picture' src='' alt='No Image'/></div>");
            response.getWriter().println("<div id='info-text'><ol id='list-info'>");
            response.getWriter().println("<li><b>" + account.getString("username") + "</b></li>");
            response.getWriter().println("<li>" + account.getString("fullname") + "</li>");
            if (account.getString("isDriver").equals("1")) {
                String stringRatingVote = getRatingVote(accountID);
                JsonObject ratingVote;
                try (JsonReader reader = Json.createReader(new StringReader(stringRatingVote))) {
                    ratingVote = reader.readObject();
                }
                response.getWriter().println("<li>Driver | ★ " + ratingVote.get("rating").toString()
                        + " (" + ratingVote.getInt("vote") + " votes)</li>");
            } else {
                response.getWriter().println("<li>Non-Driver</li>");
            }
            response.getWriter().println("<li>" + account.getString("email") + "</li>");
            response.getWriter().println("<li>" + account.getString("phone") + "</li></ol></div></div>");
            if (account.getString("isDriver").equals("1")) {
                displayPrefLoc(request, response, accountID);
            }
        }
    }

    private void displayPrefLoc(HttpServletRequest request, HttpServletResponse response, int accountID)
            throws ServletException, IOException {
        String stringPrefLoc = getPrefLoc(accountID);
        JsonArray location;
        try (JsonReader reader = Json.createReader(new StringReader(stringPrefLoc))) {
            location = reader.readArray();
        }
        response.getWriter().println("<div><div class='subtitle-cont'>");
        response.getWriter().println("<div id='prefer-loc-title' class='subtitle-profile'>PREFERRED LOCATIONS</div>");
        response.getWriter().println("<a href='EditPrefLoc'><div id='edit-pen-div'>"
                + "<img class='edit-pen' src='' alt='EDIT'></div></a></div>");
        if (!location.isEmpty()) {
            for (JsonValue x : location) {
                response.getWriter().println("<div id='info-pref-text'>"
                        + "<span class='loc-list'>" + x.asJsonObject().getString("location")
                        + "</span></div>");
            }
        } else {
            response.getWriter().println("No preferred location");
        }
        response.getWriter().println("</div>");
    }

    private String getAccount(int accountID) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekonlineservice.ProfileService port = service.getProfileServicePort();
        return port.getAccount(accountID);
    }

    private String getRatingVote(int accountID) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ojekonlineservice.ProfileService port = service.getProfileServicePort();
        return port.getRatingVote(accountID);
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
