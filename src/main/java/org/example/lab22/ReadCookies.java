package org.example.lab22;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "readCookies", value = "/cookies")
public class ReadCookies extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {response.setContentType("text/html");


        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Cookies:</h1>");
        for (Cookie c : request.getCookies()) {
            out.println(c.getName() + " " + c.getValue() + "<br/>");
        }
        out.println("</body></html>");
    }

    public void destroy() {
    }
}