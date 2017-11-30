<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
<div class="edit-profile-header">
    Make an order
</div>
<div class="progress-container">
    <div class="progress">
        <div class="progress-num">1</div> Select Destination
    </div>
    <div class="progress selected">
        <div class="progress-num">2</div> Select a driver
    </div>
    <div class="progress">
        <div class="progress-num">3</div> Chat Driver
    </div>
    <div class="progress">
        <div class="progress-num">4</div> Complete Your Order
    </div>
</div>
<div  ng-app="myApp" ng-controller="myCtrl" class="container">
    <div id="pref-driver" class="select-driver">
        <div class="selectdriver-header">
            Preferred Drivers:
        </div>
        <div ng-if="json.preference.length == 0">
            <div class="driver-not-found">Nothing to display :(</div>
        </div>
        <div ng-if="json.preference.length > 0">
            <form method="POST" action="ChatDriver">
                <div ng-repeat="data in json.preference">
                    <div class="driver-content">
                        <img src="" alt="no image" class ="driver-pic">
                        <div class="driver-name-disp">
                            {{ data.fullname}}</div>
                        <div class="driver-rating-disp">
                            ? {{ data.rating}} <span class="vote-disp">{{ data.vote}}</span>
                        </div>
                        <input type="hidden" name="driver_id" value="{{ data.driverID}}">
                        <input type="hidden" name="pick_loc" value="{{ json.pickLoc}}">
                        <input type="hidden" name="dest_loc" value="{{ json.destLoc}}">
                        <input type="hidden" name="driverUsername" value="{{ data.username}}">
                        <input type="hidden" name="fullname" value="{{ data.fullname}}">
                        <input type="submit" value="I Choose You" class="accept-button select-driver-btn" 
                               ng-click="notifySelectedDriver(data.driverID)">
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div id="other-driver" class="select-driver">
        <div class="selectdriver-header">
            Other Drivers:
        </div>
        <div ng-if="json.others.length == 0">
            <div class="driver-not-found">Nothing to display :(</div>
        </div>
        <div ng-if="json.others.length > 0">
            <form method="POST" action="ChatDriver">
                <div ng-repeat="data in json.others">
                    <div class="driver-content">
                        <img src="" alt="no image" class ="driver-pic">
                        <div class="driver-name-disp">
                            {{ data.fullname}}</div>
                        <div class="driver-rating-disp">
                            ? {{ data.rating}} <span class="vote-disp">{{ data.vote}}</span>
                        </div>
                        <input type="hidden" name="driver_id" value="{{ data.driverID}}">
                        <input type="hidden" name="pick_loc" value="{{ json.pickLoc}}">
                        <input type="hidden" name="dest_loc" value="{{ json.destLoc}}">
                        <input type="hidden" name="driverUsername" value="{{ data.username}}">
                        <input type="hidden" name="fullname" value="{{ data.fullname}}">
                        <input type="submit" value="I Choose You" class="accept-button select-driver-btn" 
                               ng-click="notifySelectedDriver(data.driverID)">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    var pickPoint = '<% out.print(request.getParameter("pick-point"));%>';
    var destination = '<% out.print(request.getParameter("destination"));%>';
    var prefDriver = '<% out.print(request.getParameter("pref-driver"));%>';
    var passID = '<%out.print(CookieManager.getCurrentAccountID(request));%>';
    var passName = '<%out.print(CookieManager.getCurrentUsername(request));%>';

    var app = angular.module('myApp', []);
    app.controller('myCtrl', function ($scope, $http) {
        $scope.urlGetDriver = "http://localhost:3000/order?pick-point=" + pickPoint +
                "&destination=" + destination + "&pref-driver=" + prefDriver;
        $scope.urlNotifyDriver = "http://localhost:3000/message/notify?passid=" + passID + 
                "&passname=" + passName + "&driverid=";
        $scope.notifySelectedDriver = function (id) {
            $http.get($scope.urlNotifyDriver + id).then(function (response) {
                console.log(response);
            }).catch(function (error) {
                console.log(error);
            });
        };
        $http.get($scope.urlGetDriver).then(function (response) {
            $scope.json = response.data;
        }).catch(function (error) {
            console.log(error);
        });
    });
</script>