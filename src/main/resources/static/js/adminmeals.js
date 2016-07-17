function handleSubmit(event){

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/cafe/{{id}}/meals',
        data: JSON.stringify({
            "id": document.meal.id.value,
            "car": document.meal.car.value
        }),
        contentType: 'application/json',
        dataType: 'json',
        statusCode: {
            201: function () {
                var mealsList = document.querySelector(".meals-list");
                if (mealsList) {
                    document.body.removeChild(mealsList.parentNode)
                }
                drawOfferList();
            }
        }
    });
    event.preventDefault();
}
