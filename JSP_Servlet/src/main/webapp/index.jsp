<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Trang Chủ</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background: url('assets/hinh-anh-hello.jpg') no-repeat center center fixed;
            background-size: cover;
        }
        .container {
            margin-top: 100px;
            background: rgba(255, 255, 255, 0.8);
            padding: 20px;
            border-radius: 10px;
        }
    </style>
</head>
<body>

<div class="container text-center">
    <h1>Chào mừng đến với Website JSP Servlet</h1>

    <%
        String username = (String) session.getAttribute("username");
        if (username == null) {
    %>
    <p>Bạn chưa đăng nhập.</p>
    <a href="login.jsp" class="btn btn-primary">Đăng nhập</a>
    <% } else { %>
    <p>Xin chào, <strong><%= username %></strong>!</p>
    <a href="LogoutServlet" class="btn btn-danger">Đăng xuất</a>
    <% } %>

</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
