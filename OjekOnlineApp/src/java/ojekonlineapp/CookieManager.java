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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author VINJERDIM
 */
public class CookieManager {

    public static boolean isSessionValid(HttpServletRequest request, HttpServletResponse response) {
        Cookie accessToken = CookieManager.getCookieByName(request, "accessToken");
        Cookie refreshToken = CookieManager.getCookieByName(request, "refreshToken");
        if (accessToken != null) {
            return isTokenValid(accessToken);
        } else if (refreshToken != null) {
            return false;   
        } 
        return false;
    }

    public static String getJsonValue(String key, Cookie cookie) {
        JsonObject result;
        try (JsonReader reader = Json.createReader(new StringReader(cookie.getValue()))) {
            result = reader.readObject();
        }
        return result.getString(key);
    }

    public static int getCurrentAccountID(HttpServletRequest request) {
        Cookie accessToken = getCookieByName(request, "accessToken");
        Cookie refreshToken = getCookieByName(request, "refreshToken");
        if (accessToken != null) {
            return Integer.parseInt(getJsonValue("accountID", accessToken));
        } else if (refreshToken != null) {
            return Integer.parseInt(getJsonValue("accountID", refreshToken));
        }
        return -1;
    }

    public static String getCurrentUsername(HttpServletRequest request) {
        Cookie accessToken = getCookieByName(request, "accessToken");
        Cookie refreshToken = getCookieByName(request, "refreshToken");
        if (accessToken != null) {
            return getJsonValue("username", accessToken);
        } else if (refreshToken != null) {
            return getJsonValue("username", refreshToken);
        }
        return null;
    }

    public static Cookie createCookie(String name, String value, int ageInMinute) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(ageInMinute * 60);
        cookie.setHttpOnly(true);
        return cookie;
    }

    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Cookie result = null;
        if (request.getCookies() != null) {
            for (Cookie x : request.getCookies()) {
                if (x.getName().equals(name)) {
                    return x;
                }
            }
        }
        return result;
    }

    public static boolean isTokenValid(Cookie cookie) {
        return requestTokenProcessing(cookie, "ValidateToken").length() > 0;
    }

    public static void deleteToken(Cookie cookie) {
        requestTokenProcessing(cookie, "DeleteToken");
    }

    public static String renewToken(Cookie cookie) {
        String result = requestTokenProcessing(cookie, "RenewToken");
        if (result.length() > 0) {
            return result;
        }
        return "";
    }

    private static String requestTokenProcessing(Cookie cookie, String process) {
        String parameters = "token=" + cookie.getValue();
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
