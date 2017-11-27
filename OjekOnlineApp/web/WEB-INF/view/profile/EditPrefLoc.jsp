<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/profile.css">
        <title>Edit Preference Location</title>
        <style>
            #check-button {
                display: none;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>EDIT PREFERRED LOCATIONS</h2>
            <form action="UpdatePreference" method="POST">
                <table style="width:100%">
                    <tr>
                        <th>No</th>
                        <th>Location</th> 
                        <th>Actions</th>
                    </tr>
                    <jsp:include page="/PrefLocTabel"/>
                </table>
            </form>
            <form action="AddPreference" method="post" name="addpref">
                <div class="edit-profile-btm">
                    <h2 class="header">Add new Location:</h2>
                    <div class="form-input">
                        <input type="text" id="next-button-text" name="location" />
                        <input class="next-button" type="submit" value="Add" />
                    </div>
                </div>
            </form>
            <div class="edit-profile-btm">
                <input class="cancel-button" value="Back" onclick="window.location.href = 'Profile'"> 
            </div><br>
        </div>
        <script>
            function changeToInput(item) {
                var changeButton = item.previousSibling;

                changeButton.style.display = "inline";
                item.style.display = "none";

                var row = (item.parentElement.parentElement.children)[1];

                var inputText = (row.children)[0];
                var labelText = (row.children)[1];
                inputText.type = "text";
                labelText.style.display = "none";
            }

            function updatePrefLoc(item) {
                var row = (item.parentElement.parentElement.children)[1];
                var newlocation = ((row.children)[0]).value;
                var id = ((row.children)[2]).value;

                if (newlocation != "") {
                    var xmlhttp = getXMLHTTP();
                    xmlhttp.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            window.location.href = "EditPrefLoc";
                        }
                    };
                    xmlhttp.open("GET", "UpdatePreference?id=" + id + "&location=" + newlocation, true);
                    xmlhttp.send();
                }
            }

            function getXMLHTTP() {
                var xmlhttp;
                if (window.XMLHttpRequest) {
                    xmlhttp = new XMLHttpRequest();
                } else {
                    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }
                return xmlhttp;
            }

            function deletePrefLoc(item) {
                var row = (item.parentElement.parentElement.children)[1];
                var prefLocation = ((row.children)[1]).innerText;
                var id = ((row.children)[2]).value;
                var confirmation = confirm("Are you sure to delete " + prefLocation + "?");
                if (confirmation) {
                    var xmlhttp = getXMLHTTP();
                    xmlhttp.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            window.location.href = "EditPrefLoc";
                        }
                    };
                    xmlhttp.open("GET", "DeletePreference?id=" + id, true);
                    xmlhttp.send();
                }
            }
        </script>
    </body>
</html>