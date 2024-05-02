<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blood Donation Form</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 400px;
            margin: 50px auto;
            background-color: #fff;
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

        input[type="text"], select{
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        input[type="submit"],button {
            background-color: #3498db;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #2980b9;
        }
        #bloodGroupContainer {
            display: none;
        }

    </style>
</head>
<body>

    <div class="container">
        <button onclick="goBack()" style="position: absolute; top: 20px; left: 20px;">Back</button>

        <div class="header">
            <h1>Blood Donation Form</h1>
        </div>
            <h3>${message}</h3>
            <form action="submitBloodForm" method="post">
            <label for="type">Type:</label>
            <select name="type" id="type">
            <option value="" disabled selected>Select Type</option>
                <option value="receiver">Receiver</option>
                <option value="donor">Donor</option>
            </select>
            <!-- Container for blood group select -->
            <div id="bloodGroupContainer">
                <label for="bloodGroup">Blood Group:</label>
                <select id="bloodGroup" name="bloodGroup" >
                    <option value="" disabled selected>Select Blood Group</option>
                    <option value="A+">A+</option>
                    <option value="A-">A-</option>
                    <option value="B+">B+</option>
                    <option value="B-">B-</option>
                    <option value="AB+">AB+</option>
                    <option value="AB-">AB-</option>
                    <option value="O+">O+</option>
                    <option value="O-">O-</option>
                </select>
            </div>
            <label for="quantity">Quantity: in ml</label>
            <input type="text" id="quantity" name="quantity" placeholder="Enter Quantity in ml">

            <input type="submit" value="Submit Request">
        </form>
    </div>

    <script>
        // Function to show or hide blood group select based on type selection
        document.getElementById('type').addEventListener('change', function() {
            var bloodGroupContainer = document.getElementById('bloodGroupContainer');
            bloodGroupContainer.style.display = this.value === 'receiver' ? 'block' : 'none';
        });
        function goBack() {
                window.history.back();
        }
    </script>
</body>