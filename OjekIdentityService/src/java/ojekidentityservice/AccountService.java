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
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author VINJERDIM
 */
@WebService(serviceName = "AccountService")
public class AccountService {

    /**
     * Get account data
     */
    @WebMethod(operationName = "getAccountByID")
    public String getAccountByID(@WebParam(name = "accountID") int accountID) {
        //TODO write your implementation code here:
        JsonObject account = Json.createObjectBuilder(JsonValue.EMPTY_JSON_OBJECT).build();
        try {
            String query = String.format("SELECT * FROM account WHERE account_id = '%1$s'", accountID);
            ResultSet result = new DatabaseIdentity().getQueryResult(query);
            if (result.first()) {
                String picturePath = result.getString("picture_path");
                account = Json.createObjectBuilder(account)
                        .add("fullname", result.getString("full_name"))
                        .add("username", result.getString("username"))
                        .add("email", result.getString("email"))
                        .add("phone", result.getString("phone"))
                        .add("isDriver", result.getString("is_driver"))
                        .add("picturePath", (picturePath == null) ? "" : picturePath)
                        .build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return account.toString();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "updateAccount")
    public Boolean updateAccount(@WebParam(name = "accountID") int accountID, @WebParam(name = "fullname") String fullname, @WebParam(name = "phone") String phone, @WebParam(name = "isDriver") int isDriver) {
        //TODO write your implementation code here:
        String query = String.format("UPDATE account SET full_name = '%1$s', phone = '%2$s', is_driver = '%3$s' "
                + "WHERE account_id='%4$s'", fullname, phone, isDriver, accountID);
        return new DatabaseIdentity().getUpdateResult(query) > 0;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAllDriver")
    public String getAllDriver() {
        //TODO write your implementation code here:
        JsonArray drivers = Json.createArrayBuilder(JsonValue.EMPTY_JSON_ARRAY).build();
        try {
            String query = String.format("SELECT * FROM account WHERE is_driver = '1'");
            ResultSet result = new DatabaseIdentity().getQueryResult(query);
            while (result.next()) {
                String picturePath = result.getString("picture_path");
                JsonObject account = Json.createObjectBuilder()
                        .add("driverID", result.getString("account_id"))
                        .add("fullname", result.getString("full_name"))
                        .add("username", result.getString("username"))
                        .add("picturePath", (picturePath == null) ? "" : picturePath)
                        .build();
                drivers = Json.createArrayBuilder(drivers).add(account).build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return drivers.toString();
    }
}
