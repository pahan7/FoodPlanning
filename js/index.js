function handleSubmit(){

    var userName = document.user.name.value;

    console.log(userName);


    fetch('http://localhost:8080/users',{
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json'
        },
        method:"POST",
        body: JSON.stringify(userName)
    });

}