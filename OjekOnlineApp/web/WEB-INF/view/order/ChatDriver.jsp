<%
    String driverID = request.getParameter("driver_id");
    String pickLoc = request.getParameter("pick_loc");
    String destLoc = request.getParameter("dest_loc");
    String driverUsername = request.getParameter("driverUsername");
    String fullname = request.getParameter("fullname");
%>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
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
        <div id="chatbox" class="chatbox">
            <div ng-repeat="data in json.chat">
                <div ng-if="data.id == json.pass">
                    <p class="chat-baloon right chat-baloon-right">{{data.msg}}</p>
                </div>
                <div ng-if="data.id == json.driver">
                    <p class="chat-baloon left chat-baloon-left">{{data.msg}}</p>
                </div>
            </div>
        </div>
        <form ng-submit="sendMessage(message)">
        <div class="textbox">
            <input class ="text-text" id="text" type="text" name="text" ng-model="message">
            <input class ="kirim-button" id="kirim-button" type="submit" value="Kirim">
        </div>
    </form>
        <form action="CompleteOrder" method="post">
            <input type="hidden" name="pick_loc" value=<%=pickLoc%>>
            <input type="hidden" name="driver_id" value=<%=driverID%>>
            <input type="hidden" name="dest_loc" value=<%=destLoc%>>
            <input type="hidden" name="driverUsername" value=<%=driverUsername%>>
            <input type="hidden" name="fullname" value=<%=fullname%>>
            <input class ="red-button clickable-button" id="kirim-button" type="submit" 
                   value="CLOSE" ng-click="notifySelectedDriver()">
        </form>
    </div>
</div>
<script>
    var app = angular.module("myApp", []);
    var driverID = <%out.print(driverID);%>;
    var passID = '<%out.print(CookieManager.getCurrentAccountID(request));%>';
    var passName = '<%out.print(CookieManager.getCurrentUsername(request));%>';

    app.controller("myCtrl", function ($scope, $http) {
        $scope.id = driverID;
        $scope.json = {driver: driverID, pass: passID, chat: []};
        $scope.sendMessage = function (message) {
            if (message !== null && message.length > 0) {
                $scope.json.chat.push({id: passID, msg: message});
                $scope.message = "";
            }
        };
        $scope.urlNotifyDriver = "http://localhost:3000/message/notify?passid=" + passID +
                "&passname=" + passName + "&driverid=";
        $scope.notifySelectedDriver = function () {
            $http.get($scope.urlNotifyDriver + $scope.id).then(function (response) {
                console.log(response);
            }).catch(function (error) {
                console.log(error);
            });
        };
    });

</script>
<script>
    var objDiv = document.getElementById("chatbox");
    objDiv.scrollTop += objDiv.scrollHeight;
</script>