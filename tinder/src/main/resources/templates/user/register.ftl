<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Register</h2>
        <form id="registerForm">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required><br><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br><br>

            <label for="confirmPassword">Confirm Password:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required><br><br>

            <button type="submit">Register</button>
            <button type="button" id="loginBtn">Back to Login</button>
        </form>
    </div>
    
    <script src="/js/register.js"></script>
</body>
</html>
