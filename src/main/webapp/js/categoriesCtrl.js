function CategoriesCtrl($scope,$http) {
    $scope.categories = [];

    $http({method: "GET", url: "status"})
        .success(function(data) {
           $scope.categories = data;
        });

};

