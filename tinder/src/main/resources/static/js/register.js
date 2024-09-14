document.addEventListener('DOMContentLoaded', function () {
    const registerForm = document.getElementById("registerForm");
    const loginBtn = document.getElementById("loginBtn");

    if (registerForm) {
        registerForm.addEventListener("submit", function (event) {
            event.preventDefault();

            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;
            const confirmPassword = document.getElementById("confirmPassword").value;

            if (!email || !password || !confirmPassword) {
                alert("Please fill in all fields.");
                return;
            }

            if (password !== confirmPassword) {
                alert("Passwords do not match.");
                return;
            }

            console.log(email, password);

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
                        alert(data.msg ? data.msg : "Registration failed. Please try again.");
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
