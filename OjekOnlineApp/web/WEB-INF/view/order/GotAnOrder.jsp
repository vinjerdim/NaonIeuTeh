
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>

<script>
    var passID = '<%out.print(request.getParameter("passid"));%>';
    var passName = '<%out.print(request.getParameter("passname"));%>';
    
    var app = angular.module("myApp", []);
    app.controller("myCtrl", function ($scope) {
        $scope.passName = passName;
        $scope.json = {driverID: "3", clientID: "5", chat: [{id: "3", msg: "hello"},
                {id: "5", msg: "woi"}, {id: "3", msg: "naon maneh"},
                {id: "3", msg: "kaideu geuwat"}]}
    });

</script> 

<div  ng-app="myApp" ng-controller="myCtrl" class="container">
    <div class="edit-profile-header">
        Looking For An Order   
    </div>
    <p class="got-an-order-green">Got An Order</p>
    <p class="got-an-order">{{passName}}</p>
    <div class="chatbox-container margin20">
        <div class="chatbox">
            <div ng-repeat="data in json.chat">
                <div ng-if="data.id == json.clientID">
                    <p class="chat-baloon left chat-baloon-left">{{ data.msg}}</p>
                </div>
                <div ng-if="data.id == json.driverID">
                    <p class="chat-baloon right chat-baloon-right">{{ data.msg}}</p>
                </div>
            </div>
        </div>
    </div>
    <form action="" method="post">
        <div class="textbox">
            <input class ="text-text" id="text" type="text" name="text"></input>
            <input class ="kirim-button" id="kirim-button" type="submit" value="Kirim">
        </div>
    </form>
</div>
</div>