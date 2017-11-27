/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekonlineservice;

import java.io.StringReader;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 *
 * @author nim_13515091
 */
public class QueryOjekController {

    /**
     *
     * @param driverID
     * @return
     */
    public static JsonArray getHistoryDriver(int driverID) {
        JsonArray resultJson = Json.createArrayBuilder(JsonValue.EMPTY_JSON_ARRAY).build();
        try {
            String query = String.format("SELECT * FROM transaction "
                    + "WHERE driver_id = '%1$s' AND hide_from_driver = '0'", driverID);
            ResultSet result = new DatabaseOjekOnline().getQueryResult(query);
            while (result.next()) {
                JsonObject passenger;
                String stringPassenger = getAccountByID(result.getInt("passenger_id"));
                try (JsonReader reader = Json.createReader(new StringReader(stringPassenger))) {
                    passenger = reader.readObject();
                }
                JsonObject history = Json.createObjectBuilder()
                        .add("transactionID", result.getString("transaction_id"))
                        .add("fullname", passenger.getString("fullname"))
                        .add("picturePath", passenger.getString("picturePath"))
                        .add("pick_location", result.getString("pick_location"))
                        .add("dest_location", result.getString("dest_location"))
                        .add("date", result.getString("date"))
                        .add("stars", result.getString("stars"))
                        .add("review", result.getString("review"))
                        .build();
                resultJson = Json.createArrayBuilder(resultJson).add(history).build();
            }
            return resultJson;
        } catch (SQLException ex) {
            Logger.getLogger(QueryOjekController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultJson;
    }

    /**
     *
     * @param passengerID
     * @return
     */
    public static JsonArray getHistoryPassenger(int passengerID) {
        JsonArray resultJson = Json.createArrayBuilder(JsonValue.EMPTY_JSON_ARRAY).build();
        try {
            String query = String.format("SELECT * FROM transaction "
                    + "WHERE passenger_id = '%1$s' AND hide_from_passenger = '0'", passengerID);
            ResultSet result = new DatabaseOjekOnline().getQueryResult(query);

            while (result.next()) {
                JsonObject driver;
                String stringDriver = getAccountByID(result.getInt("driver_id"));
                try (JsonReader reader = Json.createReader(new StringReader(stringDriver))) {
                    driver = reader.readObject();
                }
                JsonObject history = Json.createObjectBuilder()
                        .add("transactionID", result.getString("transaction_id"))
                        .add("fullname", driver.getString("fullname"))
                        .add("picturePath", driver.getString("picturePath"))
                        .add("pick_location", result.getString("pick_location"))
                        .add("dest_location", result.getString("dest_location"))
                        .add("date", result.getString("date"))
                        .add("stars", result.getString("stars"))
                        .add("review", result.getString("review"))
                        .build();
                resultJson = Json.createArrayBuilder(resultJson).add(history).build();
            }
            return resultJson;
        } catch (SQLException ex) {
            Logger.getLogger(QueryOjekController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultJson;
    }

    public static JsonArray getPreferredLoc(int accountID) {
        JsonArray resultJson = Json.createArrayBuilder(JsonValue.EMPTY_JSON_ARRAY).build();
        try {
            String query = String.format("SELECT * FROM pref_location WHERE account_id = '%1$s'", accountID);
            ResultSet result = new DatabaseOjekOnline().getQueryResult(query);

            while (result.next()) {
                JsonObject location = Json.createObjectBuilder()
                        .add("prefID", result.getString("entry_id"))
                        .add("location", result.getString("location"))
                        .build();
                resultJson = Json.createArrayBuilder(resultJson).add(location).build();
            }
            return resultJson;
        } catch (SQLException ex) {
            Logger.getLogger(QueryOjekController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultJson;
    }

    public static boolean havePrefLoc(int accountID, String location) {
        try {
            String query = String.format("SELECT * FROM pref_location "
                    + "WHERE account_id = '%1$s' AND location = '%2$s'", accountID, location);
            ResultSet result = new DatabaseOjekOnline().getQueryResult(query);
            return result.first();
        } catch (SQLException ex) {
            Logger.getLogger(QueryOjekController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static JsonObject getRatingVote(int accountID) {
        JsonObject resultJson = Json.createObjectBuilder(JsonValue.EMPTY_JSON_OBJECT).build();
        try {
            String query = String.format("SELECT AVG(stars) as rating, COUNT(*) as vote FROM transaction "
                    + "WHERE driver_id = '%1$s'", accountID);
            ResultSet result = new DatabaseOjekOnline().getQueryResult(query);

            if (result.first()) {
                resultJson = Json.createObjectBuilder(resultJson)
                        .add("rating", Float.toString(result.getFloat("rating")))
                        .add("vote", result.getInt("vote"))
                        .build();
            }
            return resultJson;
        } catch (SQLException ex) {
            Logger.getLogger(QueryOjekController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultJson;
    }

    public static JsonObject getDriverRatingVote(String preferredDriver, int currentUserID, String prefloc) {
        JsonObject allDrivers = Json.createObjectBuilder(JsonValue.EMPTY_JSON_OBJECT).build();

        JsonArray result;
        try (JsonReader reader = Json.createReader(new StringReader(getAllDriver()))) {
            result = reader.readArray();
        }

        JsonArray preference = Json.createArrayBuilder(JsonValue.EMPTY_JSON_ARRAY).build();
        JsonArray others = Json.createArrayBuilder(JsonValue.EMPTY_JSON_ARRAY).build();
        for (JsonValue x : result) {
            JsonObject driver = x.asJsonObject();
            JsonObject ratingVote = getRatingVote(Integer.parseInt(driver.getString("driverID")));
            driver = Json.createObjectBuilder(driver)
                    .add("rating", ratingVote.getString("rating"))
                    .add("vote", ratingVote.getInt("vote"))
                    .build();

            if (havePrefLoc(Integer.parseInt(driver.getString("driverID")), prefloc)) {
                if (currentUserID != Integer.parseInt(driver.getString("driverID"))) {
                    if (preferredDriver != null && !preferredDriver.equals("")
                            && driver.getString("fullname").contains(preferredDriver)) {
                        preference = Json.createArrayBuilder(preference).add(driver).build();
                    } else {
                        others = Json.createArrayBuilder(others).add(driver).build();
                    }
                }
            }
        }
        allDrivers = Json.createObjectBuilder()
                .add("preference", Json.createArrayBuilder(preference))
                .add("others", Json.createArrayBuilder(others))
                .build();
        return allDrivers;
    }

    /**
     *
     * @param order
     * @return
     */
    public static boolean insertTransaction(String passengerID, String driverID, String pickLoc,
            String destLoc, String stars, String review) {
        Date now = new Date(System.currentTimeMillis());
        String query = String.format("INSERT INTO transaction "
                + "VALUES(NULL, '%1$s', '%2$s', '%3$s', '%4$s', '%5$s', '%6$s', '%7$s', '0', '0')",
                passengerID, driverID, pickLoc, destLoc, now.toString(), stars, review);
        return new DatabaseOjekOnline().getUpdateResult(query) > 0;
    }

    public static boolean updateHideStatus(String transactionID, String isDriver) {
        boolean statusDriver = Integer.parseInt(isDriver) > 0;
        String query;
        if (statusDriver) {
            query = String.format("UPDATE transaction SET hide_from_driver = '1' "
                    + "WHERE transaction_id = '%1$s'", transactionID);
        } else {
            query = String.format("UPDATE transaction SET hide_from_passenger = '1' "
                    + "WHERE transaction_id = '%1$s'", transactionID);
        }
        return new DatabaseOjekOnline().getUpdateResult(query) > 0;
    }

    private static String getAccountByID(int accountID) {
        ojekidentityservice.AccountService_Service service = new ojekidentityservice.AccountService_Service();
        ojekidentityservice.AccountService port = service.getAccountServicePort();
        return port.getAccountByID(accountID);
    }

    private static String getAllDriver() {
        ojekidentityservice.AccountService_Service service = new ojekidentityservice.AccountService_Service();
        ojekidentityservice.AccountService port = service.getAccountServicePort();
        return port.getAllDriver();
    }

    static boolean addPrefLoc(int accountID, String loc) {
        String query;
        query = String.format("INSERT INTO pref_location (account_id, location) "
                + "VALUES ('%1$s', '%2$s')", accountID, loc);
        return new DatabaseOjekOnline().getUpdateResult(query) > 0;
    }

    static boolean updatePrefLoc(int prefID, String loc) {
        String query;
        query = String.format("UPDATE pref_location SET location = '%1$s' "
                + "WHERE entry_id = '%2$s'", loc, prefID);
        return new DatabaseOjekOnline().getUpdateResult(query) > 0;
    }

    static boolean deletePrefLoc(int prefID) {
        String query;
        query = String.format("DELETE FROM pref_location WHERE entry_id = '%1$s'", prefID);
        return new DatabaseOjekOnline().getUpdateResult(query) > 0;
    }

    public static String getAllHistory() throws SQLException {
        JsonArray resultJson = Json.createArrayBuilder(JsonValue.EMPTY_JSON_ARRAY).build();
        try {
            String query = String.format("SELECT * FROM transaction");
            ResultSet result = new DatabaseOjekOnline().getQueryResult(query);

            while (result.next()) {
                JsonObject location = Json.createObjectBuilder()
                        .add("transactionID", result.getString("transaction_id"))
                        .add("passenger_id", result.getString("passenger_id"))
                        .add("driver_id", result.getString("driver_id"))
                        .add("pick_location", result.getString("pick_location"))
                        .add("dest_location", result.getString("dest_location"))
                        .add("date", result.getString("date"))
                        .add("rating", result.getString("stars"))
                        .add("review", result.getString("review"))
                        .build();
                resultJson = Json.createArrayBuilder(resultJson).add(location).build();
            }
            return resultJson.toString();
        } catch (SQLException ex) {
            Logger.getLogger(QueryOjekController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultJson.toString();
    }

    static JsonArray getSelectedDriverAcc(String dest) {
        JsonArray result;
        try (JsonReader reader = Json.createReader(new StringReader(getAllDriver()))) {
            result = reader.readArray();
        }
        JsonArray driverRatingVote = Json.createArrayBuilder(JsonValue.EMPTY_JSON_ARRAY).build();
        for (JsonValue x : result) {
            JsonObject driver = x.asJsonObject();
            JsonObject ratingVote = getRatingVote(Integer.parseInt(driver.getString("driverID")));
            String dump = getAccountByID(Integer.parseInt(driver.getString("driverID")));
            JsonObject account;
            try (JsonReader reader = Json.createReader(new StringReader(dump))) {
                account = reader.readObject();
            }
            JsonArray loc = getPreferredLoc(Integer.parseInt(driver.getString("driverID")));
            boolean check = true;
            for (JsonValue y : loc) {
                JsonObject z = y.asJsonObject();
                if (!Objects.equals(z.getString("location"), dest)) {
                    check = false;
                }
            }
            if (check) {
                driver = Json.createObjectBuilder(driver)
                        .add("fullname", account.getString("full_name"))
                        .add("username", account.getString("username"))
                        .add("email", account.getString("email"))
                        .add("phone", account.getString("phone"))
                        .add("isDriver", account.getString("is_driver"))
                        .add("picturePath", account.getString("picturePath"))
                        .add("driver_id", Integer.parseInt(driver.getString("driverID")))
                        .add("rating", ratingVote.get("rating"))
                        .add("vote", ratingVote.getInt("vote"))
                        .build();
                driverRatingVote = Json.createArrayBuilder(driverRatingVote).add(driver).build();
            }
        }
        return driverRatingVote;
    }

    public static JsonObject getSpesificDriver(int accountID) {
        JsonObject account;
        try (JsonReader reader = Json.createReader(new StringReader(getAccountByID(accountID)))) {
            account = reader.readObject();
        }
        JsonObject resultJson = Json.createObjectBuilder(JsonValue.EMPTY_JSON_OBJECT).build();
        try {
            String query = String.format("SELECT AVG(stars) as rating, COUNT(*) as vote FROM transaction "
                    + "WHERE driver_id = '%1$s'", accountID);
            ResultSet result = new DatabaseOjekOnline().getQueryResult(query);

            if (result.first()) {
                resultJson = Json.createObjectBuilder(resultJson)
                        .add("fullname", account.getString("full_name"))
                        .add("username", account.getString("username"))
                        .add("email", account.getString("email"))
                        .add("phone", account.getString("phone"))
                        .add("isDriver", account.getString("is_driver"))
                        .add("picturePath", account.getString("picturePath"))
                        .add("rating", result.getFloat("rating"))
                        .add("vote", result.getInt("vote"))
                        .build();
            }
            return resultJson;
        } catch (SQLException ex) {
            Logger.getLogger(QueryOjekController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultJson;
    }
}
