package org.example.lab22;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "formulaServlet_2", value = "/formula-2")
public class FormulaServlet_2 extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {response.setContentType("text/html");

        String aParam = request.getParameter("a");
        String bParam = request.getParameter("b");
        String cParam = request.getParameter("c");
        String dParam = request.getParameter("d");

        double a ;
        double b ;
        double c ;
        double d;

        try {
            a = Double.parseDouble(aParam);
            b = Double.parseDouble(bParam);
            c = Double.parseDouble(cParam);
            d = Double.parseDouble(dParam);
        }catch(NumberFormatException e){
            response.sendError(400,"Дані введені не коректно");
            return;
        }

        if(b==0){
            response.sendError(400, "Ділення на 0 неможливе");
            return;
        }
        if(c==0){
            response.sendError(400, "Не коректно введені дані");
            return;
        }

        double y = ((Math.pow(Math.E,a) + 3 * Math.log10(c)) / Math.sqrt(Math.pow(b,c))) * Math.abs(Math.atan(d)) ;

        Cookie cookie1 = new Cookie("a2", aParam);
        cookie1.setMaxAge(60*60*24*2);
        Cookie cookie2 = new Cookie("b2", bParam);
        cookie2.setMaxAge(60*60*24*2);
        Cookie cookie3 = new Cookie("c2", cParam);
        cookie3.setMaxAge(60*60*24*2);
        Cookie cookie4 = new Cookie("d2", dParam);
        cookie4.setMaxAge(60*60*24*2);
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        response.addCookie(cookie3);
        response.addCookie(cookie4);

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + "<img alt="err" src="images/equation2.png"/>" + "="+ y+"</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}
