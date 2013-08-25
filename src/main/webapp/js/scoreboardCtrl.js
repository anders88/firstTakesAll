function ScoreboardCtrl($scope,$http) {
    $scope.players= [];

    $http({method: "GET", url: "player/list"})
        .success(function(data) {
            $scope.players = data;
        });


};
