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
        input[type="submit"] ,select{
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }
        select{
            background-color:white;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        button {
            background-color: #3498db;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

    </style>
</head>
<body>
<h1>Welcome to Blood Bank</h1>

    <div class="container">

        <h2>User Signup</h2>
        <form action="register" method="post" id="signupForm">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>

           <label for="name">Name:</label>
           <input type="text" id="name" name="name" >

           <label for="dob">DOB:</label>
           <input type="date" id="dob" name="dob" max ="2024-03-01" required>

            <label for="address">Address:</label>
            <input type="text" id="address" name="address"  required>

            <label for="bloodGroup">Blood Group:</label>
                            <select id="bloodGroup" name="bloodGroup">
                                <option disabled selected>Select Blood Group</option>
                                <option value="A+">A+</option>
                                <option value="A-">A-</option>
                                <option value="B+">B+</option>
                                <option value="B-">B-</option>
                                <option value="AB+">AB+</option>
                                <option value="AB-">AB-</option>
                                <option value="O+">O+</option>
                                <option value="O-">O-</option>
                            </select>

             <c:choose>
                 <c:when test="${role eq 'agent'}">
                    <input type="hidden" id="password" name="password" value="abc">

                 </c:when>
                 <c:otherwise>
                     <label for="password">Password:</label>
                     <input type="password" id="password" name="password" required>
                 </c:otherwise>
             </c:choose>

            <input type="submit" value="Sign Up">
            <div th:if="${message}">
            <strong style="color: blue;">${message}</strong>
            <h3>If you have already an account? Please Login!</h3>
            <a href="/">Login</a>
            </div>
        </form>
    </div>

</body>
</html>
