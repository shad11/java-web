<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Message Dialog</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .message-container {
            max-width: 600px;
            margin: 20px auto;
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 10px;
            height: 400px;
            overflow-y: auto;
        }
        .message {
            margin-bottom: 10px;
            padding: 10px;
            border-radius: 20px;
            max-width: 75%;
            word-wrap: break-word;
        }
        .message.sender {
            background-color: #007bff;
            color: white;
            margin-left: auto;
            text-align: right;
        }
        .message.receiver {
            background-color: #e9ecef;
            color: black;
        }
        .input-area {
            max-width: 600px;
            margin: 0 auto;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="message-container" id="chat">
        <!-- Loop through the messages list and display each message -->
        <#list messages as message>
            <#if message.senderId == userId>
                <div class="message sender">
                    <p>${message.message}</p>
                </div>
            <#else>
                <div class="message receiver">
                    <p>${message.message}</p>
                </div>
            </#if>
        </#list>
    </div>

    <!-- Message input area -->
    <div class="input-area mt-3">
        <form id="messageForm">
            <div class="input-group">
                <input type="text" class="form-control" id="messageInput" placeholder="Type a message..." required>
                <button class="btn btn-primary" type="submit">Send</button>

                <input type="hidden" id="receiver-id" value="${receiverId}">
            </div>
        </form>

        <div class="mt-3">
            <a href="/liked" class="btn btn-secondary">Go to Liked Users</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="/js/messages.js"></script>

</body>
</html>