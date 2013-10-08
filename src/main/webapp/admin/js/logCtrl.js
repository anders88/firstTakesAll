function LogCtrl($scope,$http) {
    $scope.entries = [];
    $scope.all = [];
    $http({method: "GET", url: "logreader"})
        .success(function(data) {
            $scope.entries = data;
            $scope.all = data;
        });

    $scope.filterUpdated = function() {
        $scope.entries = _.filter($scope.all,function(entry) {
            return entry.playerId.indexOf($scope.playerIdFilter) != -1;
        });
    };
}
