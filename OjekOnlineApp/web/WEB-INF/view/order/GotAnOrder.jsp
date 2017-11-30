<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
<div  ng-app="myApp" ng-controller="myCtrl" class="container" ng-init="loadMessage()">
    <div class="edit-profile-header">
        Looking For An Order   
    </div>
    <p class="got-an-order-green">Got An Order</p>
    <p class="got-an-order">{{passName}}</p>
    <div class="chatbox-container margin20">
        <div id="chatbox" class="chatbox">
            <div ng-repeat="data in json.chat">
                <div ng-if="data.id === json.driverID">
                    <p class="chat-baloon right chat-baloon-right">{{data.msg}}</p>
                </div>
                <div ng-if="data.id === json.passID">
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
    app.controller("myCtrl", function ($scope, $http) {
        $scope.passName = passName;
        $scope.urlHistory = "http://localhost:3000/message/get?driverid=" + driverID +
                "&passid=" + passID;
        $scope.loadMessage = function () {
            $http.get($scope.urlHistory).then(function (response) {
                $scope.json = response.data;
                console.log($scope.json);
            }).catch(function (error) {
                console.log(error);
            });
        };
        $scope.sendMessage = function (message) {
            if (message !== null && message.length > 0) {
                $scope.urlSend = "http://localhost:3000/message/send?driverid=" + driverID +
                        "&passid=" + passID + "&message=" + message + "&sender=" + driverID;
                $http.get($scope.urlSend).then(function (response) {
                    console.log(response);
                }).catch(function (error) {
                    console.log(error);
                });
                $scope.json.chat.push({id: driverID, msg: message});
                $scope.notifySelectedDriver("2");
                $scope.message = "";
            }
        };
        $scope.notifySelectedDriver = function (code) {
            $scope.urlNotifyDriver = "http://localhost:3000/message/notify?source=" + driverID + "&notifcode=" + code +
                    "&passname=" + passName + "&destination=";
            $http.get($scope.urlNotifyDriver + passID).then(function (response) {
                console.log(response);
            }).catch(function (error) {
                console.log(error);
            });
        };
        messagingApp.onMessage(function (payload) {
            console.log("GotAnOrder.jsp : got notif > ", payload);
            if (payload.data.code === "2") {
                $scope.loadMessage();
            } else if (payload.notification.body === "1") {
                window.location.href = "OrderTransition";
            }
        });
    });
</script> 
<script>
    var objDiv = document.getElementById("chatbox");
    objDiv.scrollTop += objDiv.scrollHeight;
</script>