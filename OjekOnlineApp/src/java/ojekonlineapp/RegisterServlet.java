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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nim_13515091
 */
@WebServlet(name = "RegistrasiServlet", urlPatterns = {"/Signup", "/Register"})
public class RegisterServlet extends HttpServlet {

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

        if (userPath.equals("/Signup")) {
            String url = "/WEB-INF/view/signup" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            response.setStatus(response.SC_NOT_FOUND);
        }

        // use RequestDispatcher to forward request internally
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

        if (userPath.equals("/Signup")) {
            String url = "/WEB-INF/view/signup" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (userPath.equals("/Register")) {
            String name = request.getParameter("yourname");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String phone = request.getParameter("phonenumber");
            String isDriver = (request.getParameter("is_driver") == null) ? "0" : "1";
            
            String ipAddress = request.getHeader("X-FORWARDED-FOR");  
            if (ipAddress == null) {  
              ipAddress = request.getRemoteAddr();  
            }
            String user_agent = request.getHeader("user-agent");
            String token = registerUser(name, username, email, password, phone, 
                    isDriver, ipAddress, user_agent);
            
            if (token.length() > 0) {
                response.addCookie(CookieManager.createCookie("accessToken", token, 5));
                response.addCookie(CookieManager.createCookie("refreshToken", token, 10));
                response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                JsonObject jsonToken = null;
                try (JsonReader reader = Json.createReader(new StringReader(token))) {
                    jsonToken = reader.readObject();
                }
                if (jsonToken.getString("isDriver").equals("1")) {
                    response.setHeader("Location", "FindingOrder");
                }
                else response.setHeader("Location", "Order");
            } else {
                response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                response.setHeader("Location", "Signup");
            }
        } else {
            response.setStatus(response.SC_NOT_FOUND);
        }
    }

    private String registerUser(String yourname, String username, String email, 
            String password, String phone, String isDriver, String ip, 
            String user_agent) {
        String parameters = "yourname=" + yourname + "&username=" + username + 
                "&email=" + email + "&password=" + password + "&phonenumber=" + 
                phone + "&is_driver=" + isDriver + "&ip=" + ip + "&user_agent="
                + user_agent;
        StringBuilder result = new StringBuilder();
        try {
            URL validateURL = new URL("http://localhost:8080/OjekIdentityService/ValidateRegister");
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
