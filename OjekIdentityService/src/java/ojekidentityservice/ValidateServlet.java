/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekidentityservice;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author VINJERDIM
 */
@WebServlet(name = "ValidateServlet",
        urlPatterns = {"/ValidateLogin", "/ValidateRegister", "/ValidateToken", "/RenewToken", "/DeleteToken"})
public class ValidateServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        String userPath = request.getServletPath();
        //String url = "/WEB-INF/view/login" + userPath + ".jsp";
        switch (userPath) {
            case "/ValidateLogin":
                processLogin(request, response);
                break;
            case "/ValidateRegister":
                processRegister(request, response);
                break;
            case "/ValidateToken":
                processTokenValidation(request, response);
                break;
            case "/RenewToken":
                processTokenRenewal(request, response);
                break;
            case "/DeleteToken":
                processTokenDeletion(request, response);
                break;
            default:
                break;
        }
    }

    private void processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String ip = request.getParameter("ip");
        String user_agent = request.getParameter("user_agent");
        AccountManager accountManagement = new AccountManager();
        int accountID = accountManagement.getAccountID(username, password);
        
        if (accountID != -1) {
            int driver = accountManagement.isDriver(username);
            System.out.println("Hehe");
            String token = accountManagement.createToken(username, accountID,
                    ip, user_agent, driver);
            response.getWriter().print((token != null) ? token : "");
        }
    }

    private void processRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String name = request.getParameter("yourname");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phonenumber");
        String isDriver = request.getParameter("is_driver");
        String ip = request.getParameter("ip");
        String user_agent = request.getParameter("user_agent");
        
        if (!(new AccountManager().isAccountExist(username, email))) {
            if (new AccountManager().addNewAccount(name, username, email, password, phone, isDriver)) {
                AccountManager accountManagement = new AccountManager();
                int accountID = accountManagement.getAccountID(username, password);
                int driver = accountManagement.isDriver(username);
                String token =  accountManagement.createToken(username, 
                        accountID, ip, user_agent, driver);
                response.getWriter().print((token != null) ? token : "");
            } else {
                response.getWriter().print("");
            }
        } else {
            response.getWriter().print("");
        }
    }

    private void processTokenValidation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String stringToken = request.getParameter("token");
        boolean isTokenValid = new AccountManager().isTokenValid(stringToken);
        response.getWriter().print((isTokenValid) ? "OK" : "");
    }

    private void processTokenRenewal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String stringToken = request.getParameter("token");
        String newToken = new AccountManager().renewToken(stringToken);
        response.getWriter().print((newToken != null) ? newToken : "");
    }

    private void processTokenDeletion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String stringToken = request.getParameter("token");
        response.getWriter().print((new AccountManager().deleteToken(stringToken)) ? "OK" : "");
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
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ValidateServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
