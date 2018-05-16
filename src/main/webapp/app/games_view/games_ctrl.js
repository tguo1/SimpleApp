angular.module('gamesApp',[]).controller('gamesCtrl', function($scope, $http) {
    // Hide Warnings
    $('#successInput').hide();
    $('#failedInput').hide();

    // Initialize web page with default data
    $scope.games = [];

    // Load data from API call
    _refreshPageData();

    // Add game to database
    $scope.addGame = function() {
        var game;
        try {
            game = {"name":$scope.a_gameName,
                    "author":$scope.a_author,
                    "release_date":$scope.a_releaseDate,
                    "tags":$scope.a_tags.replace(" ","").split(",")};
        } catch (err) {
            $('#failedInput').show();
            return;
        }

        $scope.updateString = "Added " + $scope.a_gameName + " to game list.";

        $http({
            method : 'POST',
            url : 'http://localhost:8080/games',
            data: game,
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then( _success, _error );

        $('#addModal').modal('hide');
    }

    // Delete game by name
    $scope.deleteGame = function(game) {

        $scope.updateString = "Removed " + game.name + " from game list.";

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
        $scope.u_tags = game.tags.toString();
        $scope.currGame = game;
    }

    // Update game
    $scope.updateGame = function() {
        try {
            $scope.currGame.name = $scope.u_gameName;
            $scope.currGame.author = $scope.u_author;
            $scope.currGame.release_date = $scope.u_releaseDate
            $scope.currGame.tags = $scope.u_tags.replace(" ","").split(",");
        } catch (err) {
            $('#failedInput').show();
            return;
        }

        $scope.updateString = "Updated " + $scope.u_gameName + " from game list.";

        $http({
            method : 'PUT',
            url : 'http://localhost:8080/games',
            data: $scope.currGame,
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then( _success, _error );

        $scope.currGame = "";
        $('#updateModal').modal('hide');
    }

    function _success(response) {
        _refreshPageData();
        _clearForm()
        $('#successInput').show();
    }

    function _clearForm() {
        $scope.a_gameName = "";
        $scope.a_author = "";
        $scope.a_releaseDate = "";
        $scope.a_tags = "";
    }

    // Get all game collection
    function _refreshPageData() {
        $scope.games = [];
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