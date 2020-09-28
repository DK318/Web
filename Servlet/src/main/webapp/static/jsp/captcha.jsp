<%--
  Created by IntelliJ IDEA.
  User: Home
  Date: 27.09.2020
  Time: 0:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Codeforces</title>
</head>
<body>
    <div class="captcha-img">
        <%
            String pic = (String) request.getSession().getAttribute("captcha-img");
            request.getSession().removeAttribute("captcha-img");
        %>
        <img src="data:image/png;base64,<%=pic%>">
    </div>
    <form action="" method="get">
        <label for="answer-form-text">Enter captcha:</label>
        <input name="answer" id="answer-form-text">
    </form>
</body>
</html>
