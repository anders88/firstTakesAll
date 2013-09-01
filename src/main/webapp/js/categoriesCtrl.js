function CategoriesCtrl($scope,$http) {
    $scope.gameStatus = [];

    $http({method: "GET", url: "status"})
        .success(function(data) {
           $scope.gameStatus = data;
        });

};

