package org.example.lab22.vlad;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Base64;

public class CookieHelper {
    //Set results cookie in response
    public static void setCookie(HttpServletResponse response, String value, String path){
        Cookie cookie = new Cookie(Defaults._resultsKey, Base64.getEncoder().encodeToString(value.getBytes()));
        cookie.setMaxAge(60 * 60 * 24 * 2);
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    //Set results cookie for deletion
    public static void deleteCookie(HttpServletResponse response, String path){
        Cookie deleteCookie = new Cookie(Defaults._resultsKey, "");
        deleteCookie.setMaxAge(0);
        deleteCookie.setHttpOnly(true);
        deleteCookie.setPath(path);
        response.addCookie(deleteCookie);
    }

    //Get results from cookie and set in attribute
    public static Cookie getCookieData(HttpServletRequest request) throws InvalidKeyException{
        for (Cookie cookie : request.getCookies()){
            if (cookie.getName().equals(Defaults._resultsKey)){
                String resultsString = new String(Base64.getDecoder().decode(cookie.getValue().getBytes()));
                ArrayList<CalcResult> results = ResultsHelper.parseResults(resultsString);
                request.setAttribute(Defaults._resultsKey, results);
                return cookie;
            }
        }
        return null;
    }
}
