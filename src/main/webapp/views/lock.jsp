<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Message</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 20px;
    }
    .container {
        max-width: 600px;
        margin: 0 auto;
        background-color: #fff;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    h1 {
        color: #333;
    }
    .message {
        margin-top: 20px;
        padding: 15px;
        background-color: #dff0d8;
        border: 1px solid #c3e6cb;
        border-radius: 3px;
        color: #155724;
    }
</style>
</head>
<body>
    <div class="container">
        <h1>Message</h1>
        <div class="message">
            <h1>You are Locked</h1>
            <p>You exceed maximum limit of login with wrong attempt so you are blocked</p>
            <a href="/">Back to Login</a>
        </div>
    </div>
</body>
</html>
