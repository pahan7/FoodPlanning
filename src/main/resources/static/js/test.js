function loadUsers() {
    return $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/users',
        dataType: 'json',
        statusCode: {
            200: function (data) {
                return data;
            }
        }
    });

}

function usersList() {
    loadUsers().then(function (users) {
        var usersListTemplate = Handlebars.compile(document.querySelector('#users-list').innerHTML);
        var usersTemplate = Handlebars.compile(document.querySelector('#users').innerHTML);

        var usersList = '';
        requests.forEach(function (users) {
            usersList += usersTemplate(users);
        });

        var usersList = usersListTemplate({
            body: usersList
        });

        var usersListContainer = document.createElement('div');
        usersListContainer.innerHTML = usersList;
        document.body.appendChild(usersListContainer);
    });
}

function handleSubmit(event) {

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/users/requests',
        data: JSON.stringify({
            "id": document.request.id.value,
            "radius": document.request.radius.value
        }),
        contentType: 'application/json',
        dataType: 'json',
        statusCode: {
            201: function () {
                var requestList = document.querySelector(".request-list");
                if (requestList) {
                    document.body.removeChild(requestList.parentNode)
                }
                usersList();
            }
        }
    });
    event.preventDefault();
}

function removeElement(event, id) {
    console.log(id);
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/requests/' + id,
        statusCode: {
            200: function () {
                var requestList = document.querySelector(".request-list");
                document.body.removeChild(requestList.parentNode)
                usersList();
            }
        }
    });
    event.preventDefault();
}

usersList();
