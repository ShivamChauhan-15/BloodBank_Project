 <%@ page isELIgnored="false" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>End User Logged In</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 600px;
            margin: 50px auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        }

        h2 {
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

        .user-details {
            margin-bottom: 20px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
        }

        .user-details div {
            margin-bottom: 10px;
        }

        .back-button {
            text-align: center;
            margin-top: 20px;
        }

        .back-button a {
            text-decoration: none;
            font-weight: bold;
            color: #333;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border-radius: 5px;
            display: inline-block;
        }

        .back-button a:hover {
            background-color: #45a049;
        }
        .add-agent-button {
                    text-decoration: none;
                    font-weight: bold;
                    color: #fff;
                    padding: 10px 20px;
                    background-color: #007bff;
                    border: none;
                    border-radius: 5px;
                    transition: background-color 0.3s;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Welcome to ${dto.username} Dashboard!!</h2>
        <div class="back-button">
            <a href="/userRequest">Donate/Receive Blood</a>
            <a href="/userBloodRequest" class="add-agent-button">User Request</a>
        </div>
        <br>
        <div class="user-details">
            <div>
                <label>Name:</label>
                <div>${dto.name}</div>
            </div>
            <div>
                <label>Username:</label>
                <div>${dto.username}</div>
            </div>
            <div>
                <label>Created On:</label>
                <div>${dto.createdOn}</div>
            </div>
            <div>
                <label>Created By:</label>
                <div>${dto.createdBy}</div>
            </div>
            <div>
                <label>DOB:</label>
                <div>${dto.dob}</div>
            </div>
            <div>
                <label>Blood Group:</label>
                <div>${dto.bloodGroup}</div>
            </div>
            <div>
                <label>Total Coins:</label>
                <div>${dto.coins}</div>
            </div>
        </div>

        <div class="back-button">
            <a href="logout">Log Out</a>
        </div>
    </div>
</body>
</html>
