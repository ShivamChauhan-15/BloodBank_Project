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
        <h2 class="text-center mb-4">User Requested List</h2>
         <form method="GET" action="/userBloodRequest">
                            <!-- Filtering options -->
                                <select id="filterBy" name="filterBy">
                                <option value="select" >FilterBy</option>
                                <option value="byStatus">By Status</option>
                                <option value="byDate">By Date</option>
                                <option value="byUsername">By Username</option>
                                </select>
                                <input type="text" id="username" name="input" placeholder="Enter..." style="display: none;">
                                <input type="date" id="startDate" name="startDate" placeholder="Start Date" style="display: none;">
                                <input type="date" id="endDate" name="endDate" placeholder="End Date" style="display: none;">
                                <!-- Status select -->
                                                    <select id="statusSelect" name="status" style="display: none;">
                                                        <option value="select">Select Status</option>
                                                        <option value="approved">Accepted</option>
                                                        <option value="rejected">Rejected</option>
                                                        <option value="pending">Pending</option>
                                                    </select>
                                <button type="submit">Filter</button>
                            </form>
        <table class="table table-bordered table-hover">
            <thead>
                <tr>
                    <th>Request Id</th>
                    <th>CreatedBy</th>
                    <th>Agent</th>
                    <th>Blood Group</th>
                    <th>Quantity</th>
                    <th>Type</th>
                    <th>Created At</th>
                    <th>DOB</th>
                    <th>Age</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestedUserList}" var="bloodStock" varStatus="loop">
                    <tr>
                        <td>${bloodStock.id}</td>
                        <td>${bloodStock.createdBy}</td>
                        <td>${bloodStock.agent}</td>
                        <td>${bloodStock.bloodGroup}</td>
                        <td>${bloodStock.quantity}</td>
                        <td>${bloodStock.type.toUpperCase()}</td>
                        <td>${bloodStock.createdAt}</td>
                        <td>${bloodStock.dob}</td>
                        <td>${bloodStock.age}</td>
                        <td>${bloodStock.status}</td>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
        <!-- Font Awesome JS -->
                   <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>
                    <script>
                           document.getElementById("filterBy").addEventListener("change", function() {
                               var selectedOption = this.value;
                               var usernameInput = document.getElementById("username");
                               var startDateInput = document.getElementById("startDate");
                               var endDateInput = document.getElementById("endDate");
                               var statusInput = document.getElementById("statusSelect");

                               if (selectedOption === "byUsername") {
                                   usernameInput.style.display = "inline-block";
                                   startDateInput.style.display = "none";
                                   endDateInput.style.display = "none";
                                   statusInput.style.display="none";
                               } else if (selectedOption === "byDate") {
                                   startDateInput.style.display = "inline-block";
                                   endDateInput.style.display = "inline-block";
                                   usernameInput.style.display = "none";
                                   statusInput.style.display="none";
                               }
                               else if(selectedOption=="byStatus"){
                                   startDateInput.style.display = "none";
                                   endDateInput.style.display = "none";
                                   usernameInput.style.display = "none";
                                   statusInput.style.display="inline-block";
                               }
                                else {
                                   startDateInput.style.display = "none";
                                   endDateInput.style.display = "none";
                                   usernameInput.style.display = "none";
                                   statusInput.style.display="none";
                               }
                           });
           </script>
</body>
</html>