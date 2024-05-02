<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Logged In</title>
    <!-- Font Awesome CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 800px;
            margin: 50px auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            display: flex;
            flex-direction: column;
            align-items:center;

        }
        h2 {
            margin-bottom: 20px;
            color: #333;
            align-items: center;
        }
        .user-details {
                    margin-bottom: 20px;
                    padding: 10px;
                    border: 1px solid #ddd;
                    border-radius: 5px;
                    background-color: #f9f9f9;
                    align-self: stretch;
        }
        .user-details div {
                    margin-bottom: 10px;
        }
        label {
                    display: block;
                    font-weight: bold;
                    margin-bottom: 5px;
                    color: #333;
        }

        .dropdown {
            position: relative;
            margin-bottom: 20px;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
            z-index: 1;
            border: 1px solid #ddd;
            border-radius: 5px;
            min-width: 160px;
        }

        .dropdown-content a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
        }

        .dropdown-content a:hover {
            background-color: #f1f1f1;
        }

        .dropdown:hover .dropdown-content {
            display: block;
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

        .add-agent-button:hover {
            background-color: #0056b3;
        }

        .logout-button {
            margin-top: auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
        }
        .button-container {
            display: inline-block; /* Display buttons in one line */
        }

        .add-agent-button {
            margin-right: 10px; /* Add space between buttons */
        }


    </style>
</head>
<body>
    <div class="container">
        <h2>Welcome to Admin Dashboard!!</h2>
        <div class="button-container">
            <a href="/createAgent" class="add-agent-button">Create Agent</a>
            <a href="/usersList" class="add-agent-button">Users List</a>
            <a href="/userBloodRequest" class="add-agent-button">User Request</a>
            <a href="/bloodStock" class="add-agent-button">BloodStock</a>
            <a href="/report" class="add-agent-button">Requests Report</a><br><br><br>
            <a href="/coinReport" class="add-agent-button">Coin Report</a>
            <a href="/logout" class="add-agent-button">Log Out</a>
        </div><br><br>


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

    </div>
</body>
</html>
