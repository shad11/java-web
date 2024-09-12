document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById("loginForm");
    const registerBtn = document.getElementById("registerBtn");

    if (loginForm) {
        loginForm.addEventListener("submit", function (event) {
            event.preventDefault();

            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;

            if (username && password) {
                fetch("/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({ username, password })
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert("Login successful!");
                    } else {
                        alert("Login failed. Please try again.");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert("An error occurred. Please try again.");
                });
            } else {
                alert("Please enter valid credentials.");
            }
        });
    }

    if (registerBtn) {
        registerBtn.addEventListener("click", function () {
            // Simulate navigation to the register page
            window.location.href = "/register";
        });
    }
});
