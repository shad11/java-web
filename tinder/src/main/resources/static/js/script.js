document.addEventListener('DOMContentLoaded', (event) => {
    const loginForm = document.querySelector('.login-container form');

    loginForm.addEventListener('submit', (event) => {
        event.preventDefault();
        alert('Submit button clicked');
    });
});