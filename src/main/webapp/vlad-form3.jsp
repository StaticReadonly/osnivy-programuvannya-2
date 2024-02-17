<%@ page import="java.util.ArrayList" %>
<%@ page import="org.example.lab22.vlad.CalcResult" %>
<%@ page import="org.example.lab22.vlad.Defaults" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String error = (String)request.getAttribute(Defaults.Errors._errorParameter);
    ArrayList<CalcResult> results = (ArrayList<CalcResult>)request.getAttribute(Defaults._resultsKey);
%>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link rel="stylesheet" href="css/vlad-form.css">
</head>
<body>
    <div class="container">
        <form method="post">
            <div class="form-field">
                <span>a:</span>
                <input type="text" step="0.01" name="a"/>
            </div>
            <div class="form-field">
                <span>b:</span>
                <input type="text" step="0.01" name="b"/>
            </div>
            <div class="form-field">
                <span>c:</span>
                <input type="text" step="0.01" name="c"/>
            </div>
            <div class="form-field">
                <span>d:</span>
                <input type="text" step="0.01" name="d"/>
            </div>
            <% if (error != null){ %>
                <div class="error-message form-field"><%out.print(error);%></div>
            <%}%>
            <br/>
            <div>
                <input type="submit" value="Calculate">
            </div>
        </form>
        <div class="results-container">
            <div class="results-title">Results:</div>
            <%
                if (results != null){
                    for(CalcResult res : results){
            %>
                <div class="results-item">
                    <div>a: <%out.print(res.a);%></div>
                    <div>b: <%out.print(res.b);%></div>
                    <div>c: <%out.print(res.c);%></div>
                    <div>d: <%out.print(res.d);%></div>
                    <div>result: <%out.print(res.result);%></div>
                </div>
            <%}}%>
        </div>
    </div>
    <div class="image-container" style="background-image: url('images/equation3.png');">

    </div>
</body>
</html>