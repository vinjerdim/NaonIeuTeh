/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ojekonlineservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 *
 * @author nim_13515091
 */
public class TokenOjek {
   
    public static boolean isTokenValid(String token) {
        return requestToken(token,"ValidateToken").length() > 0;
    }
    
    private static String requestToken(String token, String process) {
        String parameters = "token=" + token;
        StringBuilder result = new StringBuilder();
        try {
            URL validateURL = new URL("http://localhost:8080/OjekIdentityService/" + process);
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
