var keyPressTimeout;
document.getElementById("username").onkeyup = async function() {
    // clearTimeout(keyPressTimeout);
    // keyPressTimeout = setTimeout(function () {
    var response = await fetch("http://localhost:8081/api/user?username=" + document.getElementById("username").value);
    if (response.ok) {
        this.setCustomValidity("Такой пользователь уже зарегистрирован");
    }
    else {
        this.setCustomValidity("");
    }
    // }, 500)
    this.reportValidity();
};