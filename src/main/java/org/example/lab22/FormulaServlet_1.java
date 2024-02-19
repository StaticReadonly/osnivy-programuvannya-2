package org.example.lab22;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "formula1Servlet", value = "/formula-1")
public class FormulaServlet_1 extends HttpServlet {

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

        if(c==0){
            response.sendError(400, "Ділення на 0 неможливе");
            return;
        }

        double y = Math.sqrt(Math.abs(Math.sin(a) - 4 * Math.log(b) / Math.pow(c, d))) ;

        Cookie cookie1 = new Cookie("a1", aParam);
        cookie1.setMaxAge(60*60*24*2);
        Cookie cookie2 = new Cookie("b1", bParam);
        cookie2.setMaxAge(60*60*24*2);
        Cookie cookie3 = new Cookie("c1", cParam);
        cookie3.setMaxAge(60*60*24*2);
        Cookie cookie4 = new Cookie("d1", dParam);
        cookie4.setMaxAge(60*60*24*2);
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        response.addCookie(cookie3);
        response.addCookie(cookie4);


        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + "<img alt="err" src="images/equation1.png"/>" + "="+ y+"</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}
