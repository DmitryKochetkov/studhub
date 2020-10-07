var keyPressTimeout;
$('#student').on('change keyup', async function() {
    // clearTimeout(keyPressTimeout);
    // keyPressTimeout = setTimeout(function () {
    var response = await fetch('http://localhost:8081/api/user/' + $(this).val());
    if (response.ok) {
        this.setCustomValidity('');
        $('#courses').empty();
        let data = await response.json();
        if (data['student']) {
            for (let course of data['courses']) {
                var opt = document.createElement('option');
                opt.value = course['id'];
                opt.innerHTML = course['title'];
                document.getElementById('courses').appendChild(opt);
            }
            document.getElementById('courses').disabled = false;
        }
        else {
            this.setCustomValidity('Данный пользователь не является учеником.');
            $('#courses').empty();
            document.getElementById('courses').disabled = true;
        }
    }
    else {
        this.setCustomValidity('Пользователь не найден.');
        $('#courses').empty();
        document.getElementById('courses').disabled = true;
    }
    this.reportValidity();
    // }, 500)
});