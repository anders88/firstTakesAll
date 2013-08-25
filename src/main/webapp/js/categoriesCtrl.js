function CategoriesCtrl($scope,$http) {
    $scope.categories = [];

    $http({method: "GET", url: "game"})
        .success(function(data) {
           $scope.categories = data;
        });

};

