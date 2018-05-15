angular.module('gamesApp',[]).controller('gamesCtrl', function($scope, $http) {
    // Initialize web page with default data
    $scope.games = [];

    // Load data from API call
    _refreshPageData();

    // Add game to database
    $scope.addGame = function() {
        var game;
        try {
            game = {"name":$scope.u_gameName,
                    "author":$scope.u_author,
                    "release_date":$scope.u_releaseDate,
                    "tags":$scope.u_tags.replace(" ","").split(",")};
        } catch (err) {
            $scope.aerror = "Empty field names detected!";
            return;
        }

        $http({
            method : 'POST',
            url : 'http://localhost:8080/games',
            data: game,
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then( _success, _error );
    }

    // Delete game by name
    $scope.deleteGame = function(game) {
        $http({
            method : 'DELETE',
            url : 'http://localhost:8080/games',
            data: game,
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then( _success, _error );
    }

    // Edit game
    $scope.editGame = function(game) {
        $scope.u_gameName = game.name;
        $scope.u_author = game.author;
        $scope.u_releaseDate = game.release_date;
        $scope.u_tags = game.tags;
    }

    function _success(response) {
        _refreshPageData();
        _clearForm()
    }

    function _clearForm() {
        $scope.u_gameName = "";
        $scope.u_author = "";
        $scope.u_releaseDate = "";
        $scope.u_tags = "";
    }

    // Get all game collection
    function _refreshPageData() {
        $http({
            method : 'GET',
            url : 'http://localhost:8080/games'
        }).then(function successCallback(response) {
            $scope.games = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    function _error(response) {
        console.log(response.statusText);
    }

    $scope.orderByMe = function(x) {
        $scope.myOrderBy = x;
    }
});