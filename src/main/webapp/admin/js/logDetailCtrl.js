 var urlParam = function(name){
    var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    return results ? results[1] || 0 : 0;
}

function LogDetailCtrl($scope,$http) {
    $scope.lineStyle = function(detail) {
        return (detail === "WRONG") ? "error" : "";
    };
    $scope.details = [];
    $scope.logid = urlParam("id");
    $http({method: "GET", url: "logreader/detail?id=" + $scope.logid})
        .success(function(data) {
            $scope.details = data;
        });

}
