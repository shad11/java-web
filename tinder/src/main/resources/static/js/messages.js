document.addEventListener('DOMContentLoaded', function () {
    // Send message
    document.getElementById("messageForm").addEventListener("submit", function(event) {
        event.preventDefault();
        const message = document.getElementById("messageInput").value;
        const receiverId = document.querySelector('#receiver-id').value;

        if (message.trim() === '') {
            return;
        }

        fetch(`/messages/${receiverId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({ message }),
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                document.getElementById("messageInput").value = "";

                const chatContainer = document.getElementById("chat");
                const newMessage = document.createElement("div");

                newMessage.classList.add("message", "sender");
                newMessage.innerHTML = "<p>" + message + "</p>";

                chatContainer.appendChild(newMessage);

                chatContainer.scrollTop = chatContainer.scrollHeight;
            } else {
                alert(data.msg ? data.msg : "Помилка при відправці повідомлення.");
            }
        })
        .catch(error => console.error('Error sending message:', error));
    });
});
