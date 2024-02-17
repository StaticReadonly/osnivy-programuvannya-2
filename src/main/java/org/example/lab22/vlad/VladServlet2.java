package org.example.lab22.vlad;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;

@WebServlet(name = "vladServlet2", value = "/vlad-form2")
public class VladServlet2 extends HttpServlet {
    private Cookie _cookie = null;
    public void init() {

    }

    //GET handler
    public void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ServletException
    {
        try{
            getCookieData(request);
        }catch (InvalidKeyException exc){
            setInvalidKeyError(request);
            deleteCookie(response);
        }

        forwardToJspFile(request, response);
    }

    //POST handler
    public void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ServletException
    {
        String _a = request.getParameter(Defaults._parameterA);
        String _b = request.getParameter(Defaults._parameterB);
        String _c = request.getParameter(Defaults._parameterC);
        String _d = request.getParameter(Defaults._parameterD);

        try{
            getCookieData(request);

            double a = Double.parseDouble(_a);
            double b = Double.parseDouble(_b);
            double c = Double.parseDouble(_c);
            double d = Double.parseDouble(_d);

            double result = (Math.exp(a) + 3 * Math.log10(c) * Math.abs(Math.atan(d))) / (Math.pow(b, 1 / c));

            if (Double.isNaN(result)){
                setZeroDivisionError(request);
                forwardToJspFile(request, response);
                return;
            }

            if (Double.isInfinite(result)){
                setZeroLogError(request);
                forwardToJspFile(request, response);
                return;
            }

            ArrayList<CalcResult> results = (ArrayList<CalcResult>)request.getAttribute(Defaults._resultsKey);

            if (results != null){
                String cookieVal = new String(Base64.getDecoder().decode(_cookie.getValue()))
                        + addValues(false, a, b, c, d, result);

                CalcResult newValue = new CalcResult();
                newValue.a = a;
                newValue.b = b;
                newValue.c = c;
                newValue.d = d;
                newValue.result = result;
                results.add(0, newValue);

                setCookie(response, cookieVal);

                forwardToJspFile(request, response);
                return;
            }

            String cookieVal = addValues(true, a, b, c, d, result);

            results = parseResults(cookieVal);
            request.setAttribute(Defaults._resultsKey, results);

            setCookie(response, cookieVal);
        }catch (NumberFormatException exc) {
            setInvalidRequestDataError(request);
        }catch(InvalidKeyException exc){
            setInvalidKeyError(request);
            deleteCookie(response);
        }

        forwardToJspFile(request, response);
    }

    //Forward request to jsp file
    private void forwardToJspFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        request.getRequestDispatcher("vlad-form2.jsp").forward(request, response);
    }

    //Set results cookie in response
    private void setCookie(HttpServletResponse response, String value){
        Cookie cookie = new Cookie(Defaults._resultsKey, Base64.getEncoder().encodeToString(value.getBytes()));
        cookie.setMaxAge(60 * 60 * 24 * 2);
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        cookie.setPath("/lab2_2_war_exploded/vlad-form2");
        response.addCookie(cookie);
    }

    //Set results cookie for deletion
    private void deleteCookie(HttpServletResponse response){
        Cookie deleteCookie = new Cookie(Defaults._resultsKey, "");
        deleteCookie.setMaxAge(0);
        deleteCookie.setHttpOnly(true);
        deleteCookie.setPath("/lab2_2_war_exploded/vlad-form2");
        response.addCookie(deleteCookie);
    }

    //Get results from cookie and set in attribute
    private void getCookieData(HttpServletRequest request) throws InvalidKeyException{
        for (Cookie cookie : request.getCookies()){
            if (cookie.getName().equals(Defaults._resultsKey)){
                _cookie = cookie;
                String resultsString = new String(Base64.getDecoder().decode(cookie.getValue().getBytes()));
                ArrayList<CalcResult> results = parseResults(resultsString);
                request.setAttribute(Defaults._resultsKey, results);
                break;
            }
        }
    }

    //Parse values into results item string
    private String addValues(boolean firstValue, double a, double b, double c, double d, double result){
        StringBuilder value = new StringBuilder();

        if (!firstValue)
            value.append(";");

        value.append(Defaults._parameterA)
             .append("=")
             .append(a)
             .append(",")
             .append(Defaults._parameterB)
             .append("=")
             .append(b)
             .append(",")
             .append(Defaults._parameterC)
             .append("=")
             .append(c)
             .append(",")
             .append(Defaults._parameterD)
             .append("=")
             .append(d)
             .append(",")
             .append(Defaults._parameterRes)
             .append("=")
             .append(result);

        return value.toString();
    }

    //Parse string to array of results
    private ArrayList<CalcResult> parseResults(String str) throws InvalidKeyException{
        ArrayList<CalcResult> result = new ArrayList<>();

        for(String str1 : str.split(";")){
            String[] str2 = str1.split(",");

            CalcResult newResult = new CalcResult();

            for(String str3 : str2){
                String[] keyValue = str3.split("=");
                switch (keyValue[0]){
                    case Defaults._parameterA:{
                        newResult.a = Double.parseDouble(keyValue[1]);
                        break;
                    }
                    case Defaults._parameterB:{
                        newResult.b = Double.parseDouble(keyValue[1]);
                        break;
                    }
                    case Defaults._parameterC:{
                        newResult.c = Double.parseDouble(keyValue[1]);
                        break;
                    }
                    case Defaults._parameterD:{
                        newResult.d = Double.parseDouble(keyValue[1]);
                        break;
                    }
                    case Defaults._parameterRes:{
                        newResult.result = Double.parseDouble(keyValue[1]);
                        break;
                    }
                    default: throw new InvalidKeyException();
                }
            }

            result.add(newResult);
        }

        Collections.reverse(result);
        return result;
    }

    private void setZeroDivisionError(HttpServletRequest request){
        request.setAttribute(Defaults.Errors._errorParameter, Defaults.Errors._zeroDivisionError);
    }

    private void setInvalidRequestDataError(HttpServletRequest request){
        request.setAttribute(Defaults.Errors._errorParameter, Defaults.Errors._invalidFormData);
    }

    private void setInvalidKeyError(HttpServletRequest request){
        request.setAttribute(Defaults.Errors._errorParameter, Defaults.Errors._invalidKey);
    }

    private void setZeroLogError(HttpServletRequest request){
        request.setAttribute(Defaults.Errors._errorParameter, Defaults.Errors._logFromZero);
    }

    public void destroy() {
    }
}