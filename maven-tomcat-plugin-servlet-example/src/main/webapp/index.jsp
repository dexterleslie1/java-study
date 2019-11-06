<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<html>
<head><title>测试首页</title></head>
<body>
  <%
    Date currentTime = new Date();
  %>
  <h2>系统当前时间：<%=currentTime%>
  <a href="<%= request.getRequestURI() %>"><h3>刷新</h3></a>
</body>
</html>