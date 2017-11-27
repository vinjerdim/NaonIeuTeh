<%-- 
    Document   : signup
    Created on : 04-Nov-2017, 00:25:44
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up</title>
        <link rel="stylesheet" href="css/style.css">
        <script type="text/javascript" src="js/app.js"></script>
    </head>
    <body>
        <section class="content-layout">
            <header class="title">
              <div>
                <hr>
                <h1>SIGN UP</h1>
                <hr>
              </div>
            </header>
            <section class="form-layout">
              <form action="Register" method="post" name="signupform" onsubmit="return signupValidation()">
                <div class="form-input">
                  <div>Your Name</div>
                  <input id="yourname" type="text" name="yourname">
                </div>
                <div class="form-input checked-input">
                  <div>Username</div>
                  <input id="username" type="text" name="username">
                </div>
                <div class="form-input checked-input">
                  <div>Email</div>
                  <input id="email" type="text" name="email">
                </div>
                <div class="form-input">
                  <div>Password</div>
                  <input id="password" type="password" name="password">
                </div>
                <div class="form-input">
                  <div>Confirm Password</div>
                  <input id="confirmpassword" type="password" name="confirmpassword">
                </div>
                <div class="form-input">
                  <div>Phone Number</div>
                  <input id="phone" type="text" name="phonenumber">
                </div>
                <div class="driver-checkbox">
                  <input id="isdriver" type="checkbox" name="is_driver">
                  <div>Also sign me up as a driver!</div>
                </div>
                <div id="button-layout">
                  <div>
                    <a href="Login">Already have an account</a>
                  </div>
                  <input id="register-button" type="submit" value="Register">
                </div>
              </form>
            </section>
          </section>
    </body>
</html>
