angular.module('gamesApp',[]).controller('gamesCtrl', function($scope, $http) {
    // Initialize web page with default data
    $scope.games = [];

    // Load data from API call
    _refreshPageData();

    // Get all game collection
    function _refreshPageData() {
        $http({
            method : 'GET',
            url : 'http://localhost:8080/games'
        }).then(function successCallback(response) {
            $scope.games = response.data.gameRepository;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    // Populate game info fields
    $scope.update = function() {
        $scope.gameName = $scope.selectedGame.name;
        $scope.gameAuthor = $scope.selectedGame.author;
        $scope.gameTags = $scope.selectedGame.tags;
    }
});