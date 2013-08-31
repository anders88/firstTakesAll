var urlParam = function(name){
    var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    return results ? results[1] || 0 : 0;
}

function QuestionsCtrl($scope,$http) {
    $scope.categoryId = urlParam("id");

    $scope.questions = [];

    $http({method: "GET", url: "game/category?id=" + $scope.categoryId })
        .success(function(data) {
            $scope.questions = data;
        });


};
