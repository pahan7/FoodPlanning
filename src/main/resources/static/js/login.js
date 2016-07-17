function saveUserName() {
    var mail = document.querySelector('#username').value;
    var name = "";
    for (i=0; i<mail.length; i++){
        if(mail.charAt(i)==='@'){
            break;
        }
        name +=mail.charAt(i);
    }
    console.log(mail);
    console.log(name);
    setCookie(name);
}

function setCookie(cvalue) {
    var d = new Date();
    d.setTime(d.getTime() + (10*24*60*60*1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = userName+"="+cvalue+"; "+expires;
}