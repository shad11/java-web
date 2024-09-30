document.addEventListener('DOMContentLoaded', function () {
    const likeButtons = document.querySelectorAll('.like-btn');
    const likeRemoveButtons = document.querySelectorAll('.like-remove-btn');
    const messageButtons = document.querySelectorAll('.message-btn');

    likeButtons.forEach(button => {
        button.addEventListener('click', function () {
            const userId = this.getAttribute('data-user');
            const userCard = document.getElementById(`user-${userId}`);
            const carouselInner = document.querySelector('.carousel-inner');
            const carousel = document.querySelector('#userCarousel');
            const carouselInstance = bootstrap.Carousel.getInstance(carousel);

            fetch("liked", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({
                    userId
                })
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        // Remove user card from slider upon success
                        const nextItem = userCard.nextElementSibling;

                        carouselInstance.next();
                        userCard.remove();

                        // If there is no next item, manually handle the case where the last item was removed
                        if (!nextItem) {
                            const remainingItems = carouselInner.querySelectorAll('.carousel-item');
                            
                            if (remainingItems.length > 0) {
                                remainingItems[0].classList.add('active');
                            } else {
                                document.querySelector('#userCarousel').style.display = 'none';
                            }
                        }
                    } else {
                        alert(data.msg ? data.msg : "Login failed. Please try again.");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert('Error occurred while liking the user.');
                });
        });
    });

    likeRemoveButtons.forEach(button => {
        button.addEventListener('click', function () {
            const userId = this.getAttribute('data-user-id');
            const userElement = document.getElementById(`user-${userId}`);

            fetch('/liked', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({
                    userId
                })
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // Remove the user from the list
                    userElement.remove();
                } else {
                    alert(data.msg ? data.msg : "Failed to remove user from likes. Please try again.");
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error occurred while removing the user.');
            });
        });
    });

    messageButtons.forEach(button => {
        button.addEventListener('click', function () {
            const userId = this.getAttribute('data-user-id');

            window.location.href = `/messages/${userId}`;
        });
    });

});