<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>User List</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="d-flex justify-content-start mb-4">
            <a href="/users" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> All Users
            </a>
        </div>

        <h2 class="text-center">Liked Users</h2>
        <div id="user-list" class="list-group">
            <#list users as user>
            <#if user.imgLink?? && user.imgLink != "">
            <div class="list-group-item d-flex align-items-center justify-content-between" id="user-${user.id}">
                <!-- User Image -->
                <img src="${user.imgLink}" alt="${user.nick}" class="img-fluid rounded" style="width: 100px; height: 100px; object-fit: cover;">

                <!-- User Nick or Email -->
                <h5 class="ms-3">
                    <#if user.nick?? && user.nick != "">
                        ${user.nick}
                    <#else>
                        ${user.email}
                    </#if>
                </h5>

                <!-- Buttons -->
                <div class="ms-auto">
                    <button class="btn btn-danger like-remove-btn" data-user-id="${user.id}">Remove from Likes</button>
                    <button class="btn btn-primary message-btn" data-user-id="${user.id}">Message</button>
                </div>
            </div>
            </#if>
            </#list>
        </div>
    </div>

    <script src="/js/users.js"></script>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
