document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById("loginForm");
    const registerBtn = document.getElementById("registerBtn");
    const errorMessages = document.getElementById("errorMessages");

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    // Minimum 8 characters, at least one letter and one number
    const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

    const showError = function (message) {
        errorMessages.innerHTML = message;
        errorMessages.classList.remove('d-none');
    }

    if (loginForm) {
        loginForm.addEventListener("submit", function (event) {
            event.preventDefault();

            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;

            // Clear previous errors
            errorMessages.innerHTML = '';
            errorMessages.classList.add('d-none');

            if (!email || !emailPattern.test(email)) {
                showError("Please enter a valid email address.");
                return;
            }

            if (!password || !passwordPattern.test(password)) {
                showError("Password must be at least 8 characters long and contain both letters and numbers.");
                return;
            }

            fetch("/login", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({
                    email,
                    password
                })
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        if (data.redirect) {
                            window.location.href = data.redirect;
                        } else {
                            alert("Login successful!");
                        }
                    } else {
                        showError(data.msg ? data.msg : "Login failed. Please try again.");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    showError("An error occurred. Please try again.");
                });
        });
    }

    if (registerBtn) {
        registerBtn.addEventListener("click", function () {
            window.location.href = "/register";
        });
    }
});
