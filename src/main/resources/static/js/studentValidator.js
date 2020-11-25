var keyPressTimeout;
$("#student").on("change keyup", async function() {
    // clearTimeout(keyPressTimeout);
    // keyPressTimeout = setTimeout(function () {
    let username = $(this).val();
    let response = await fetch("/api/student?username=" + username);
    if (response.ok) {
        this.setCustomValidity("");
        $("#courses").empty();
        let student = await response.json();
        for (let course of student["courses"]) {
            let opt = document.createElement("option");
            opt.value = course["id"];
            opt.innerHTML = course["title"];
            document.getElementById("courses").appendChild(opt);
        }
        document.getElementById("courses").disabled = false;
    }
    else {
        this.setCustomValidity("Ученик не найден.");
        $("#courses").empty();
        document.getElementById("courses").disabled = true;
    }
    this.reportValidity();
    // }, 500)
});