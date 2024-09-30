<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Page Not Found</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body, html {
            height: 100%;
        }
        .error-container {
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            text-align: center;
        }
        .error-code {
            font-size: 8rem;
            font-weight: bold;
            color: #dc3545; /* Bootstrap danger color */
        }
        .error-message {
            font-size: 1.5rem;
            margin-bottom: 20px;
        }
        .btn-home {
            margin-top: 20px;
        }
    </style>
</head>
<body>

<div class="container error-container">
    <div>
        <div class="error-code">404</div>
        <div class="error-message">
            <#if message?? && message != "">
                ${message}
            <#else>
                Oops! The page you're looking for can't be found.
            </#if>
        </div>
        <a href="/" class="btn btn-primary btn-home">Go Back to Home</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>