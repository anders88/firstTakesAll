function LogCtrl($scope,$http) {
    $scope.entries = [];
    $http({method: "GET", url: "logreader"})
        .success(function(data) {
            $scope.entries = data;
        });
}
