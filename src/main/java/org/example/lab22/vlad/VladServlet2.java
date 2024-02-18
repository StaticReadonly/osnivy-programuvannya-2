package org.example.lab22.vlad;

import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "vladServlet2", value = "/vlad-form2")
public class VladServlet2 extends AbstractVladServlet {
    public VladServlet2(){
        _formFile = "vlad-form2.jsp";
        _path = "/lab2_2_war_exploded/vlad-form2";
    }

    @Override
    public double calculate(double a, double b, double c, double d) {
        return (Math.exp(a) + 3 * Math.log10(c) * Math.abs(Math.atan(d))) / (Math.pow(b, 1 / c));
    }
}