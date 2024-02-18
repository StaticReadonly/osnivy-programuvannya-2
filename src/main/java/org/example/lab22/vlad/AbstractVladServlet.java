package org.example.lab22.vlad;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Base64;

public abstract class AbstractVladServlet extends HttpServlet{
    private Cookie _cookie = null;
    protected String _path = "";
    protected String _formFile = "";

    public void init() {

    }

    //Calculate
    public abstract double calculate(double a, double b, double c, double d);

    //Forward request to jsp file
    public void forwardToJspFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher(_formFile).forward(request, response);
    }

    //GET handler
    public void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ServletException
    {
        try{
            _cookie = CookieHelper.getCookieData(request);
        }catch (InvalidKeyException exc){
            ErrorHelper.setInvalidKeyError(request);
            CookieHelper.deleteCookie(response, _path);
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
            _cookie = CookieHelper.getCookieData(request);

            double a = Double.parseDouble(_a);
            double b = Double.parseDouble(_b);
            double c = Double.parseDouble(_c);
            double d = Double.parseDouble(_d);

            double result = calculate(a, b, c, d);

            if (Double.isNaN(result)){
                ErrorHelper.setZeroDivisionError(request);
                forwardToJspFile(request, response);
                return;
            }

            if (Double.isInfinite(result)){
                ErrorHelper.setZeroLogError(request);
                forwardToJspFile(request, response);
                return;
            }

            ArrayList<CalcResult> results = (ArrayList<CalcResult>)request.getAttribute(Defaults._resultsKey);

            if (results != null && _cookie != null){
                String cookieVal = new String(Base64.getDecoder().decode(_cookie.getValue()))
                        + ResultsHelper.convertToString(false, a, b, c, d, result);

                CalcResult newValue = new CalcResult();
                newValue.a = a;
                newValue.b = b;
                newValue.c = c;
                newValue.d = d;
                newValue.result = result;
                results.add(0, newValue);

                CookieHelper.setCookie(response, cookieVal, _path);

                forwardToJspFile(request, response);
                return;
            }

            String cookieVal = ResultsHelper.convertToString(true, a, b, c, d, result);

            results = ResultsHelper.parseResults(cookieVal);
            request.setAttribute(Defaults._resultsKey, results);

            CookieHelper.setCookie(response, cookieVal, _path);
        }catch (NumberFormatException exc) {
            ErrorHelper.setInvalidRequestDataError(request);
        }catch(InvalidKeyException exc){
            ErrorHelper.setInvalidKeyError(request);
            CookieHelper.deleteCookie(response, _path);
        }

        forwardToJspFile(request, response);
    }

    public void destroy() {
    }
}
