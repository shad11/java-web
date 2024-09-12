<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login</title>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body>
    <div class="login-container">
        <h2>Login</h2>
        <form id="loginForm">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required><br><br>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br><br>
            
            <button type="submit">Login</button>
            <button type="button" id="registerBtn">Register</button>
        </form>
    </div>

    <script src="/js/login.js"></script>
</body>
</html>