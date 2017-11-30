<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
<div  ng-app="myApp" ng-controller="myCtrl" class="container">
    <div class="edit-profile-header">
        Looking For An Order   
    </div>
    <p class="got-an-order-green">Got An Order</p>
    <p class="got-an-order">{{passName}}</p>
    <div class="chatbox-container margin20">
        <div class="chatbox">
            <div ng-repeat="data in json.chat">
                <div ng-if="data.id == json.driver">
                    <p class="chat-baloon right chat-baloon-right">{{data.msg}}</p>
                </div>
                <div ng-if="data.id == json.pass">
                    <p class="chat-baloon left chat-baloon-left ">{{data.msg}}</p>
                </div>
            </div>
        </div>
    </div>
    <form ng-submit="sendMessage(message)">
        <div class="textbox">
            <input class ="text-text" id="text" type="text" name="text" ng-model="message">
            <input class ="kirim-button" id="kirim-button" type="submit" value="Kirim">
        </div>
    </form>
</div>
<script>
    var driverID = '<%out.print(CookieManager.getCurrentAccountID(request));%>';
    var passID = '<%out.print(request.getParameter("passid"));%>';
    var passName = '<%out.print(request.getParameter("passname"));%>';

    var app = angular.module("myApp", []);
    app.controller("myCtrl", function ($scope) {
        $scope.passName = passName;
        $scope.json = {driver: driverID, pass: passID, chat: []};
        $scope.sendMessage = function (message) {
            if (message !== null && message.length > 0) {
                $scope.json.chat.push({id : driverID, msg : message});
                $scope.message = "";
            }
        };
    });

    messagingApp.onMessage(function (payload) {
        console.log("GotAnOrder.jsp : got notif > ", payload);
        window.location.href = "OrderTransition";
    });
</script> 