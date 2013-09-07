function CategoriesCtrl($scope,$http) {
    $scope.gameStatus = [];

    $http({method: "GET", url: "status"})
        .success(function(data) {
           $scope.gameStatus = data;
        });

    setInterval(function() {
        var time = new Date().getTime();
        var urlstr = "status?dummy=" + time;
        $http({method: "GET", url: urlstr})
            .success(function(data) {
                $scope.gameStatus = data;
                $scope.$apply();
            });
    },15000);


};

