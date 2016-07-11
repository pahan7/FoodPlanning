function handleSubmit(){

    var userName = document.user.name.value,




    fetch('http://localhost:8080/users',{
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json'
        },
        method:"POST",
        body: JSON.stringify(payload)
    }).then(function() {
        var userList = document.querySelector(".user-list");
    });
}