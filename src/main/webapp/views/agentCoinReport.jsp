<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Requested List</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }

        .container {
            padding-top: 20px;
        }

        .table thead th {
            background-color: #343a40;
            color: #fff;
            border-color: #454d55;
        }

        .table tbody tr:hover {
            background-color: #f2f2f2;
        }

        .btn-group {
            display: flex;
            gap: 5px;
        }

        .btn {
            border-radius: 3px;
        }

        .btn-success {
            background-color: #28a745;
            border-color: #28a745;
        }

        .btn-success:hover {
            background-color: #218838;
            border-color: #1e7e34;
        }

        .btn-danger {
            background-color: #dc3545;
            border-color: #dc3545;
        }

        .btn-danger:hover {
            background-color: #c82333;
            border-color: #bd2130;
        }

        .btn-dark {
            background-color: #343a40;
            border-color: #343a40;
        }

        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }

        .btn-primary:hover {
            background-color: #0069d9;
            border-color: #0062cc;
        }
    </style>
</head>
<body>
    <div class="container">
        <a href="javascript:history.back()" class="btn btn-secondary back-button">Back</a>
        <table class="table table-bordered table-hover">
            <thead>
                <tr>
                    <th>Serial No.</th>
                    <th>Blood Group</th>
                    <th>End User Coins</th>
                    <th>Rate</th>
                    <th>Agent Coins</th>
                </tr>
            </thead>
            <tbody>
                 <c:forEach var="entry" items="${usersList}" varStatus="loop">
                                <tr>
                                    <%-- Extract blood group and coins array from the map entry --%>
                                    <%-- Ensure to use c:forEach varStatus to access loop.index --%>
                                    <c:set var="bloodGroup" value="${entry.key}" />
                                    <c:set var="coinsAndRate" value="${entry.value}" />
                                    <c:set var="userCoins" value="${coinsAndRate[0]}" />
                                    <c:set var="agentCoins" value="${coinsAndRate[1]}" />
                                    <c:set var="rate" value="${coinsAndRate[2]}" />
                                    <td>${loop.index + 1}</td>
                                    <td>${bloodGroup}</td>
                                    <td>${userCoins}</td>
                                    <td>${rate}</td>
                                    <td>${agentCoins}</td>
                                </tr>
                            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
