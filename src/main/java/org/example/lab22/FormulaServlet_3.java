package org.example.lab22;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "formulaServlet_3", value = "/formula-3")
public class FormulaServlet_3 extends HttpServlet {

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
            response.sendError(400, "Дані введені не коректно");
            return;
        }

        double y = Math.pow((2 * Math.sin(a) + Math.cos(Math.abs(b * Math.sqrt(c)))),d) ;

        Cookie cookie1 = new Cookie("a3", aParam);
        cookie1.setMaxAge(60*60*24*2);
        Cookie cookie2 = new Cookie("b3", bParam);
        cookie2.setMaxAge(60*60*24*2);
        Cookie cookie3 = new Cookie("c3", cParam);
        cookie3.setMaxAge(60*60*24*2);
        Cookie cookie4 = new Cookie("d3", dParam);
        cookie4.setMaxAge(60*60*24*2);
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        response.addCookie(cookie3);
        response.addCookie(cookie4);

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + "<img alt="err" src="images/equation3.png"/>" + "="+ y+"</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}
