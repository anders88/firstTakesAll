function CategoriesCtrl($scope,$http) {
    $scope.categories = [];
    //$scope.categories = [{"id":2,"description":"The answer is the sum. Question: \u00274+2+7\u0027, Correct answer \u002713\u0027"},{"id":1,"description":"The answer is the same as the question. Question: \u0027hello\u0027, Correct answer \u0027hello\u0027"}];

    $http({method: "GET", url: "game"})
        .success(function(data) {
           $scope.categories = data;
        });

};

