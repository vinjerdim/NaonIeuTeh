/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekidentityservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author VINJERDIM
 */
public class DatabaseIdentity {
    private final String host = "jdbc:mysql://localhost:3306/ojek_account";
    private final String username = "root";
    private final String password = "";
    private Connection connection;
    private Statement statement;
    
    public DatabaseIdentity() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
            connection = DriverManager.getConnection(host, username, password);
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseIdentity.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public ResultSet getQueryResult(String sqlQuery) {
        try {
            System.out.println(sqlQuery);
            ResultSet queryResult = statement.executeQuery(sqlQuery);
            return queryResult;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseIdentity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public int getUpdateResult (String sqlQuery) {
        try {
            return statement.executeUpdate(sqlQuery);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseIdentity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
