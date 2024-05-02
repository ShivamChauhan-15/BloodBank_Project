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
        <h2 class="text-center mb-4">Blood Stock Table</h2>
        <table class="table table-bordered table-hover">
            <thead>
                <tr>
                    <th>BloodGroup</th>
                    <th>Available Units</th>
                    <th>Last Update</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${availableBloodList}" var="bloodStock" varStatus="loop">
                    <tr>
                        <td>${bloodStock.bloodGroup}</td>
                        <td>${bloodStock.availableUnit}</td>
                        <td>${bloodStock.lastUpdate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>