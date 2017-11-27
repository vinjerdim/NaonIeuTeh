<% 
    String driverID = request.getParameter("driver_id");
    String pickLoc = request.getParameter("pick_loc");
    String destLoc = request.getParameter("dest_loc");
    String driverUsername = request.getParameter("driverUsername");
    String fullname = request.getParameter("fullname");
%>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>

<script>

var app = angular.module("myApp", []);

app.controller("myCtrl", function($scope) {
    $scope.json = {driverID:"3", clientID:"5", chat:[{id:"3",msg:"hello"},
            {id:"5",msg:"woi"},{id:"3",msg:"naon maneh"},
            {id:"3",msg:"kaideu geuwat"}]}});

</script> 

<div  ng-app="myApp" ng-controller="myCtrl" class="container">
    <div class="edit-profile-header">
        Make an order       
    </div>
    <div class="progress-container">
        <div class="progress">
            <div class="progress-num">1</div> Select a Destination
        </div>
        <div class="progress">
            <div class="progress-num">2</div> Select a Driver
        </div>
        <div class="progress selected">
            <div class="progress-num">3</div> Chat <br> Driver
        </div>
        <div class="progress">
            <div class="progress-num">4</div> Complete Your Order
        </div>
    </div>
    <div class="chatbox-container">
        <div class="chatbox">
            <div ng-repeat="data in json.chat">
                <div ng-if="id == json.clientID ">
                    <p class="chat-baloon left chat-baloon-left">{{ data.msg }}</p>
                </div>
                <div ng-if="id == json.driverID ">
                    <p class="chat-baloon right chat-baloon-right">{{ data.msg }}</p>
                </div>
            </div>
        </div>
        <form action="" method="post">
            <div class="textbox">
                <input class ="text-text" id="text" type="text" name="text"></input>
                <input class ="kirim-button" id="kirim-button" type="submit" value="Kirim">
            </div>
        </form>
        <form action="CompleteOrder" method="post">
            <input type="hidden" name="pick_loc" value=<%=pickLoc%>>
            <input type="hidden" name="driver_id" value=<%=driverID%>>
            <input type="hidden" name="dest_loc" value=<%=destLoc%>>
            <input type="hidden" name="driverUsername" value=<%=driverUsername%>>
            <input type="hidden" name="fullname" value=<%=fullname%>>
            <input class ="red-button clickable-button" id="kirim-button" type="submit" value="CLOSE">
        </form>
    </div>
</div>
