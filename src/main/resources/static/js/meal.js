function loadMeals() {
    //return fetch('./data/users.json')
    return fetch('http://localhost:8080/cafes')
        .then(function (response) {
            return response.json();
        });
}


function getCookie(userName) {
    var name = userName + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function drawMealList() {
    loadMeals().then(function (cafes) {
        var cafeListTemplate = Handlebars.compile(document.querySelector('#cafe-list').innerHTML);
        var mealsTemplate = Handlebars.compile(document.querySelector('#meals').innerHTML);

        var mealsList = '';
        cafes.forEach(function (cafes) {
            mealsList += mealsTemplate(cafes);
        });

        var mealsList = cafeListTemplate({
            body: mealsList
        });

        var mealsListContainer = document.createElement('div');
        mealsListContainer.innerHTML = mealsList;
        document.body.appendChild(mealsListContainer);
    });
}

drawMealList();
