document.addEventListener('DOMContentLoaded', function () {
    const registerForm = document.getElementById("registerForm");
    const loginBtn = document.getElementById("loginBtn");

    if (registerForm) {
        registerForm.addEventListener("submit", function (event) {
            event.preventDefault();

            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;
            const confirmPassword = document.getElementById("confirmPassword").value;

            if (!username || !password || !confirmPassword) {
                alert("Please fill in all fields.");
                return;
            }

            if (password !== confirmPassword) {
                alert("Passwords do not match.");
                return;
            }

            fetch("/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ username, password })
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert("Registration successful! Please login to continue.");
                        window.location.href = "/login";
                    } else {
                        alert("Registration failed. Please try again.");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert("An error occurred. Please try again.");
                });
        });
    }

    if (loginBtn) {
        loginBtn.addEventListener("click", function () {
            // Simulate navigation to the login page
            window.location.href = "/login";
        });
    }
});
