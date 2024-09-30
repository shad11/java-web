document.addEventListener('DOMContentLoaded', function () {
    const registerForm = document.getElementById("registerForm");
    const loginBtn = document.getElementById("loginBtn");
    const errorMessages = document.getElementById("errorMessages");

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    // Minimum 8 characters, at least one letter and one number
    const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

    const showError = function (message) {
        errorMessages.innerHTML = message;
        errorMessages.classList.remove('d-none');
    }

    if (registerForm) {
        registerForm.addEventListener("submit", function (event) {
            event.preventDefault();

            // Clear previous errors
            errorMessages.innerHTML = '';
            errorMessages.classList.add('d-none');

            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;
            const confirmPassword = document.getElementById("confirmPassword").value;

            if (!email || !emailPattern.test(email)) {
                showError("Please enter a valid email address.");
                return;
            }

            if (!password || !confirmPassword || !passwordPattern.test(password) || !passwordPattern.test(confirmPassword)) {
                showError("Password must be at least 8 characters long and contain both letters and numbers.");
                return;
            }

            if (password !== confirmPassword) {
                showError("Passwords do not match.");
                return;
            }

            fetch("/register", {
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
                            alert("Registration successful!");
                        }
                    } else {
                        showError(data.msg ? data.msg : "Registration failed. Please try again.");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    showError("An error occurred. Please try again.");
                });
        });
    }

    if (loginBtn) {
        loginBtn.addEventListener("click", function () {
            window.location.href = "/login";
        });
    }
});
