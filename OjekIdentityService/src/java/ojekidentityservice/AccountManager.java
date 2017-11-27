/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekidentityservice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import static ojekidentityservice.Token.generateString;

/**
 *
 * @author VINJERDIM
 */
public class AccountManager {

    public boolean isTokenValid(String stringToken) {
        JsonObject jsonToken = Token.parseStringToken(stringToken);
        if (jsonToken != null) {
            try {
                String query = String.format("SELECT COUNT(*) FROM account_token "
                        + "WHERE account_id = '%1$s' AND token = '%2$s'",
                        jsonToken.getString("accountID"), jsonToken.getString("token"));
                ResultSet result = new DatabaseIdentity().getQueryResult(query);
                if (result.first()) {
                    return (result.getInt(1) == 1);
                }
                return false;
            } catch (SQLException ex) {
                Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public int getAccountID(String username, String password) {
        try {
            String query = String.format("SELECT account_id FROM account "
                    + "WHERE username = '%1$s' AND password = '%2$s'", username, password);
            ResultSet result = new DatabaseIdentity().getQueryResult(query);
            if (result.first()) {
                return result.getInt(1);
            }
            return -1;
        } catch (SQLException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int isDriver(String username) {
        try {
            String query = String.format("SELECT is_driver FROM account "
                    + "WHERE username = '%1$s'", username);
            ResultSet result = new DatabaseIdentity().getQueryResult(query);
            if (result.first()) {
                return result.getInt(1);
            }
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public boolean isAccountExist(String username, String email) {
        try {
            String query = String.format("SELECT * FROM account "
                    + "WHERE username = '%1$s' OR email = '%2$s'", username, email);
            ResultSet result = new DatabaseIdentity().getQueryResult(query);
            return (result.first());
        } catch (SQLException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean addNewAccount(String name, String username, String email, String password, String phone, String isDriver) {
        String sql = String.format("INSERT INTO account (full_name, username, email, password, phone, is_driver) "
                + "VALUES ('%1$s', '%2$s', '%3$s', '%4$s', '%5$s', '%6$s')",
                name, username, email, password, phone, isDriver);
        return new DatabaseIdentity().getUpdateResult(sql) > 0;
    }

    public String createToken(String username, int accountID, String ip,
            String user_agent, int isDriver) throws SQLException {
        JsonObject token = Token.generateToken(isDriver, username, accountID);
        String checkquery = String.format("SELECT * FROM account_token "
                + "WHERE account_id = '%1$s'", accountID);
        ResultSet result = new DatabaseIdentity().getQueryResult(checkquery);

        if (result.first()) {
            String tokenSQL = result.getString("token");
            String ipSQL = result.getString("ip");
            String user_agentSQL = result.getString("user_agent");
            if (tokenSQL == null) {
                String query = String.format("UPDATE account_token SET token = '%1$s', "
                        + "refresh_token = '%1$s', ip = '%2$s', user_agent = '%3$s'"
                        + "WHERE account_id = '%4$s'", token.getString("token"), ip,
                        user_agent, accountID);
                if (new DatabaseIdentity().getUpdateResult(query) > 0) {
                    return token.toString();
                }
                return null;
            } else {
                if (ipSQL.equals(ip) && user_agentSQL.equals(user_agent)) {
                    JsonObject oldToken = Json.createObjectBuilder()
                            .add("isDriver", Integer.toString(isDriver))
                            .add("accountID", Integer.toString(accountID))
                            .add("username", username)
                            .add("token", tokenSQL)
                            .build();
                    return oldToken.toString();
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public String renewToken(String stringToken) {
        try {
            JsonObject refreshToken = Token.parseStringToken(stringToken);
            String query = String.format("SELECT * FROM account_token "
                    + "WHERE account_id = '%1$s' AND refresh_token = '%2$s'",
                    refreshToken.getString("accountID"), refreshToken.getString("token"));
            ResultSet result = new DatabaseIdentity().getQueryResult(query);

            if (result.first()) {
                JsonObject newToken = Token.generateToken(isDriver(refreshToken.getString("isDriver")),
                        refreshToken.getString("username"),
                        Integer.parseInt(refreshToken.getString("accountID")));
                query = String.format("UPDATE account_token SET token = '%1$s' WHERE account_id = '%2$s'",
                        newToken.getString("token"), newToken.getString("accountID"));
                if (new DatabaseIdentity().getUpdateResult(query) > 0) {
                    return newToken.toString();
                }
                return null;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean deleteToken(String stringToken) {
        JsonObject jsonToken = Token.parseStringToken(stringToken);
        String query = String.format("UPDATE account_token SET token = NULL, "
                + "refresh_token = NULL, ip = NULL, user_agent = NULL "
                + "WHERE account_id = '%1$s'", jsonToken.getString("accountID"));
        return new DatabaseIdentity().getUpdateResult(query) > 0;
    }
}
