package org.example.lab22.vlad;

import jakarta.servlet.http.HttpServletRequest;

public class ErrorHelper {
    public static void setZeroDivisionError(HttpServletRequest request){
        request.setAttribute(Defaults.Errors._errorParameter, Defaults.Errors._zeroDivisionError);
    }

    public static void setInvalidRequestDataError(HttpServletRequest request){
        request.setAttribute(Defaults.Errors._errorParameter, Defaults.Errors._invalidFormData);
    }

    public static void setInvalidKeyError(HttpServletRequest request){
        request.setAttribute(Defaults.Errors._errorParameter, Defaults.Errors._invalidKey);
    }

    public static void setZeroLogError(HttpServletRequest request){
        request.setAttribute(Defaults.Errors._errorParameter, Defaults.Errors._logFromZero);
    }
}
