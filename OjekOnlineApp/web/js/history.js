
function tabActive(tag) {
    var tabUser = document.getElementById('user-his');
    var tabDriver = document.getElementById('driver-his');
    var order = document.getElementsByClassName('order-list');
    var driver = document.getElementsByClassName('driver-list');
    
    if (tag === 'user') {    
        order[0].style.display = 'block';
        driver[0].style.display = 'none';
    } else {
        order[0].style.display = 'none';
        driver[0].style.display = 'block';
    }
}

function hideThis(user) {
    updateHide(user);
    console.log(user[0]);
}

function updateHide(history)
{
    var data = "user=" + history[0] + "&driver=" + history[1] + "&date=" + history[4];
    var xhr;
    if (window.XMLHttpRequest) {
        xhr = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xhr.open("GET", "/history/update", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(data);
}