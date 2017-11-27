/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekidentityservice;

import java.io.StringReader;
import java.util.Random;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author VINJERDIM
 */
public class Token {
    public static JsonObject parseStringToken(String token) {
        JsonObject result = null;
        try (JsonReader reader = Json.createReader(new StringReader(token))) {
            result = reader.readObject();
        }
        return result;
    }
    
    public static JsonObject generateToken(int isDriver, String username, 
            int accountID) {
        System.out.println("is driver: "+Integer.toString(isDriver));
        JsonObject token = Json.createObjectBuilder()
                .add("isDriver", Integer.toString(isDriver))
                .add("accountID", Integer.toString(accountID))
                .add("username", username)
                .add("token", generateString(20))
                .build();
        return token;
    }
    
    static String generateString(int tokenLength) {
        String charSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        while (stringBuilder.length() < tokenLength) {
            int index = (int) (random.nextFloat() * charSet.length());
            stringBuilder.append(charSet.charAt(index));
        }
        String result = stringBuilder.toString();
        return result;
    }
}
