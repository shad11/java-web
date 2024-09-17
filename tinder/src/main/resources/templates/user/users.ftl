<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>User List Slider</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        /* Custom styles for carousel */
        .carousel-control-prev, .carousel-control-next {
            width: 5%;
        }
        .carousel-control-prev-icon, .carousel-control-next-icon {
            background-color: blue;
            border-radius: 50%;
        }
        .carousel-inner img {
            width: 100%;
            height: 400px;
            object-fit: cover;
        }
        .card {
            width: 20rem;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">User List</h2>
        <!-- Carousel Slider -->
        <div id="userCarousel" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <!-- Declare a flag for the first active item -->
                <#assign isFirstItem = true>

                <#list users as user>
                <!-- Check if imgLink is not empty -->
                <#if user.imgLink?? && user.imgLink != "">
                <div class="carousel-item <#if isFirstItem>active<#assign isFirstItem=false></#if>" id="user-${user.id}">
                    <div class="d-flex justify-content-center">
                        <div class="card" style="width: 18rem;">
                            <img src="${user.imgLink}" class="card-img-top" alt="${user.nick}">
                            <div class="card-body text-center">
                                <!-- Show nick if available, else show email -->
                                <h5 class="card-title">
                                    <#if user.nick?? && user.nick != "">
                                        ${user.nick}
                                    <#else>
                                        ${user.email}
                                    </#if>
                                </h5>
                                
                                <button class="btn btn-primary like-btn" data-user="${user.id}">Like</button>
                            </div>
                        </div>
                    </div>
                </div>
                </#if>
                </#list>
            </div>
            <!-- Carousel controls -->
            <button class="carousel-control-prev" type="button" data-bs-target="#userCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#userCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>

    <script src="/js/users.js"></script>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
