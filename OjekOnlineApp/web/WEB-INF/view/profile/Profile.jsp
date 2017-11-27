<%-- 
    Document   : profile
    Created on : 04-Nov-2017, 00:23:11
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
    </head>
    <body>
        <div id="info-profile">
            <div class="subtitle-cont">
                <div class="subtitle-profile">
                    MY PROFILE
                </div>
                <a href="EditProfile" >
                    <div id="edit-pen-div">
                        <img class="edit-pen" src="" alt="EDIT">
                    </div>
                </a>
            </div>
            <jsp:include page="/DisplayProfile"/>
        </div>
    </body>
</html>