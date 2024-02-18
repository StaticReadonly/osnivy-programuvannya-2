package org.example.lab22.vlad;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Collections;

public class ResultsHelper {
    //Parse values into results item string
    public static String convertToString(boolean firstValue, double a, double b, double c, double d, double result){
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
    public static ArrayList<CalcResult> parseResults(String str) throws InvalidKeyException {
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
}
