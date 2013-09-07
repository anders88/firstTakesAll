function CategoriesCtrl($scope,$http) {
    var sortPlayers = function(players) {
        return _.sortBy(players,function(player) {
            return 0-player.points;
        });
    };

    var sortCategories = function(categories) {
        return _.sortBy(categories,function(category) {
            return category.points;
        });
    };

    $scope.players = [];
    $scope.categories = [];


    $http({method: "GET", url: "status"})
        .success(function(data) {
           $scope.players = sortPlayers(data.players);
           $scope.categories = sortCategories(data.categories);
        });

    setInterval(function() {
        var time = new Date().getTime();
        var urlstr = "status?dummy=" + time;
        $http({method: "GET", url: urlstr})
            .success(function(data) {
                $scope.players = sortPlayers(data.players);
                $scope.categories = sortCategories(data.categories);
            });
    },15000);


};

