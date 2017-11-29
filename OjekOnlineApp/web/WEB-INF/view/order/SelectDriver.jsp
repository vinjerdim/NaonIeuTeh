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
<div ng-app="myApp" ng-controller="myCtrl">
    {{hehe}}
</div>
<script>
    var pickPoint = '<% out.print(request.getParameter("pick-point"));%>';
    var destination = '<% out.print(request.getParameter("destination"));%>';
    var prefDriver = '<% out.print(request.getParameter("pref-driver"));%>';
    var url = "http://localhost:3000/order?pick-point=" + pickPoint +
            "&destination=" + destination + "&pref-driver=" + prefDriver;
    var app = angular.module('myApp', []);
    app.controller('myCtrl', function ($scope, $http) {
        $http.get(url).then(function (response) {
            console.log(response);
            $scope.hehe = response;
        }).catch(function (error) {
            console.log("SelectDriver: error getting driver");
        });
    });
</script>