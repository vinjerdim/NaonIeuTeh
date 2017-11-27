function loginValidation() {
    var name = document.loginform.username.value;  
    var password = document.loginform.password.value;  

    if (name==null || name==""){  
      alert("Name can't be blank");  
      return false;  
    }else if(password==null || password==""){  
      alert("Password can't be blank");  
      return false;  
    }  
}

function signupValidation() {
    var name = document.signupform.yourname.value;  
    var username = document.signupform.username.value; 
    var email = document.signupform.email.value; 
    var password = document.signupform.password.value;  
    var confirm = document.signupform.confirmpassword.value; 
    var phone = document.signupform.phonenumber.value; 
   
    if(name==null || name==""){  
            alert("Name can't be empty");  
            return false;  
    }else if (username==null || username==""){  
            alert("Username can't be empty");  
            return false;
    }else if (username.length > 20){  
            alert("Username has maximum character of 20");  
            return false;	
    }else if (email==null || email==""){  
            alert("Email can't be empty");  
            return false;
    }else if(password==null || password==""){  
            alert("Password can't be empty");  
            return false;  
    }else if(confirm !== password){  
            alert("Confirm password not valid");  
            return false;  
    }else if(phone==null || phone==""){  
            alert("Phone number can't be empty");  
            return false;  
    }else if ((phone.length < 9) || (phone.length > 12)){  
            alert("Phone number must have character between 9 until 12");  
            return false;	
    }	

}

