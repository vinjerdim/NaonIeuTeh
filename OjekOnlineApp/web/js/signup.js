function checkData(name, element)
{
    var data = "?" + name + "=" + document.forms["signupform"][name].value;
    var xhr;
    if (window.XMLHttpRequest) {
        xhr = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }
    
    xhr.open("GET", "/validation" + data, true); 
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");                  
    xhr.send(null);
    xhr.onload = function() {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                if (xhr.responseText === 'OK') { 
                    document.getElementById(element).innerHTML = "√";
                } else {
                    document.getElementById(element).innerHTML = "X";
                }
            } 
            else {
                alert('There was a problem with the request.');
            }
        }
    }
}

function signupValidation() {
    var username = document.forms["signupform"]["username"];
    var password = document.forms["signupform"]["password"];
    var cpassword = document.forms["signupform"]["confirm-password"];
    var email = document.forms["signupform"]["email"];
    var fullname = document.forms["signupform"]["your-name"];
    var phone = document.forms["signupform"]["phone"];
    var usrVerify = Validate(username, "empty");
    var pwdVerify = Validate(password, "empty");
    var cpwdVerify = Validate(cpassword, "empty");
    var emailVerify = Validate(email, "empty", 0, 0, "email");
    var fullnameVerify = Validate(fullname, "empty", 0, 20, "length");
    var phoneVerify = Validate(phone, "empty", 9, 12, "length");
    return (usrVerify && pwdVerify && cpwdVerify && emailVerify && fullnameVerify && phoneVerify);
}