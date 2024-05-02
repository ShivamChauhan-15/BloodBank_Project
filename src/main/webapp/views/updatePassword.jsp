<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Signup Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 400px;
            margin: 50px auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        }

        h1,h2{
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
            color: #333;
        }

        input[type="text"],
        input[type="password"],
        input[type="date"],
        input[type="email"],
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

    </style>
</head>
<body>
<h1>Welcome to Blood Bank</h1>
    <div class="container">

        <h2>Update Password</h2>
        <form action="updatePassword" method="post" id="signupForm">

            <label for="username">Username:</label>
            <input readonly type="text" id="username" name="username" value="${userName}" >


            <label for="currentPassword">Current Password:</label>
            <input type="password" id="password" name="currentPassword" required>

            <label for="password">New Password:</label>
            <input type="password" id="password" name="password" required>

            <input type="submit" value="Update">
            <c:if test="${not empty message}">
                <h2 style='color:red'>${message}</h2>
            </c:if>

        </form>
    </div>


    </script>
</body>
</html>
