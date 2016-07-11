
function loadCafe() {
    return fetch('./data/cafeAndMeals.json')
        .then(function(response) {
            return response.json();
        });
}

function drawCafeList() {
    loadCafe().then(function(cafes) {
        var cafeListTemplate = Handlebars.compile(document.querySelector('#cafe-list').innerHTML);
        var cafeTemplate = Handlebars.compile(document.querySelector('#cafe').innerHTML);

        var cafeList = '';
        var mealList='';

        cafes.forEach(function(cafe) {
            cafeList += cafeTemplate(cafe);
        }).forEach(function(meal){
            mealList += cafeTemplate(meal)
        });

        var cafeList = cafeListTemplate({
            body: cafeList
        });

        var cafeListContainer = document.createElement('div');
        cafeListContainer.innerHTML = cafeList;
        document.body.appendChild(cafeListContainer);
    });
}

drawCafeList();