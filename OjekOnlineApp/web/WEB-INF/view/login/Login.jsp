<%-- 
    Document   : login
    Created on : 03-Nov-2017, 04:37:51
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="css/style.css">
        <script type="text/javascript" src="js/app.js"></script>
    </head>
    <body>
        <section class="content-layout">
            <header class="title">
              <div>
                <hr>
                <h1>LOGIN</h1>
                <hr>
              </div>
            </header>
            <section class="form-layout">
              <form action="ValidateLogin" method="post" name="loginform" onsubmit="return loginValidation();">
                <div class="form-input">
                  <div>Username</div>
                  <input id="username" type="text" name="username">
                </div>
                <div class="form-input">
                  <div>Password</div>
                  <input id="password" type="password" name="password">
                </div>
                <div id="button-layout">
                  <div>
                    <a href="Signup">Don't have an account?</a>
                  </div>
                  <input id="register-button" type="submit" value="GO!">
                </div>
              </form>
            </section>
          </section>
    </body>
</html>
