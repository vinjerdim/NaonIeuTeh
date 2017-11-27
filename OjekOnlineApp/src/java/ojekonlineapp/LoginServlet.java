/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekonlineapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nim_13515091
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/Login", "/ValidateLogin", "/Logout"})
public class LoginServlet extends HttpServlet {

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

        String userPath = request.getServletPath();
        String url = "/WEB-INF/view/login" + userPath + ".jsp";

        if (userPath.equals("/Login")) {
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (IOException | ServletException ex) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            response.getWriter().print(request.getMethod());
        } else if (userPath.equals("/Logout")) {
            processLogout(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
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

        String userPath = request.getServletPath();

        if (userPath.equals("/ValidateLogin")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            String ipAddress = request.getHeader("X-FORWARDED-FOR");  
            if (ipAddress == null) {  
              ipAddress = request.getRemoteAddr();  
            }
            String user_agent = request.getHeader("user-agent");
            String token = getAccessToken(username, password, ipAddress, 
                    user_agent);

            if (token.length() > 0) {
                response.addCookie(CookieManager.createCookie("accessToken", token, 15));
                response.addCookie(CookieManager.createCookie("refreshToken", token, 30));
                response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                JsonObject jsonToken = null;
                try (JsonReader reader = Json.createReader(new StringReader(token))) {
                    jsonToken = reader.readObject();
                }
                System.out.println(jsonToken.toString());
                if (jsonToken.getString("isDriver").equals("1")) {
                    response.setHeader("Location", "FindingOrder");
                }
                else response.setHeader("Location", "Order");
            } else {
                response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                response.setHeader("Location", "Login");
            }
        } else if (userPath.equals("/Logout")) {
            processLogout(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", "Login");
        }
    }

    private void processLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie accessToken = CookieManager.getCookieByName(request, "accessToken");
        Cookie refreshToken = CookieManager.getCookieByName(request, "refreshToken");
        if (accessToken != null) {
            CookieManager.deleteToken(accessToken);
        }
        if (refreshToken != null) {
            CookieManager.deleteToken(refreshToken);
        }
        response.sendRedirect("Login");
    }

    private String getAccessToken(String username, String password, String ip, 
            String user_agent) {
        String parameters = "username=" + username + "&password=" + password
                + "&ip=" + ip + "&user_agent=" + user_agent;
        StringBuilder result = new StringBuilder();
        try {
            URL validateURL = new URL("http://localhost:8080/OjekIdentityService/ValidateLogin");
            HttpURLConnection connection = (HttpURLConnection) validateURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(parameters.getBytes(Charset.defaultCharset()));
                os.flush();
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (IOException e) {
            return "";
        }
        return result.toString();
    }
}
