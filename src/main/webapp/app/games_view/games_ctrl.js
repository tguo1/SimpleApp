angular.module('gamesApp',[]).controller('gamesCtrl', function($scope, $http) {

    defaultImage = "app/img/game.jpg";

    // Initialize web page with default data
    $scope.games = [];

    // Clear forms
    _clearForm();
    // Load data from API call
    _refreshPageData();

    // Add game to database
    $scope.addGame = function() {
        var game;
        var imgs = [];

        try {
            var hasEmpty = $scope.a_gameName.length*$scope.a_author.length*$scope.a_releaseDate.length*$scope.a_tags.length == 0;
            if (hasEmpty) {
                throw err;
            }

            imgs[0] = ($scope.a_img1.length == 0)? defaultImage : $scope.a_img1;
            imgs[1] = ($scope.a_img2.length == 0)? defaultImage : $scope.a_img2;
            imgs[2] = ($scope.a_img3.length == 0)? defaultImage : $scope.a_img3;

            game = {"name":$scope.a_gameName,
                    "author":$scope.a_author,
                    "release_date":$scope.a_releaseDate,
                    "tags":$scope.a_tags.replace(" ","").split(","),
                    "imgs":imgs};
        } catch (err) {
            _displayError();
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

        $('#gameinfoModal').modal('hide');
    }

    // Edit game
    $scope.editGame = function(game) {
        $scope.u_gameName = game.name;
        $scope.u_author = game.author;
        $scope.u_releaseDate = game.release_date;
        $scope.u_tags = game.tags.toString();
        $scope.u_img1 = game.imgs[0];
        $scope.u_img2 = game.imgs[1];
        $scope.u_img3 = game.imgs[2];

        $scope.currGame = game;

        $('#gameinfoModal').modal('hide');
    }

    // Update game
    $scope.updateGame = function() {
        try {
            var hasEmpty = $scope.u_gameName.length*$scope.u_author.length*$scope.u_releaseDate.length*$scope.u_tags.length == 0;
            if (hasEmpty) {
                throw err;
            }
            $scope.currGame.name = $scope.u_gameName;
            $scope.currGame.author = $scope.u_author;
            $scope.currGame.release_date = $scope.u_releaseDate;
            $scope.currGame.tags = $scope.u_tags.replace(" ","").split(",");
            $scope.currGame.imgs[0] = $scope.u_img1;
            $scope.currGame.imgs[1] = $scope.u_img2;
            $scope.currGame.imgs[2] = $scope.u_img3;
        } catch (err) {
            _displayError();
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

    $scope.orderByMe = function(x) {
        if ($scope.myOrderBy == x) {
            $scope.myOrderBy = "-" + x;
        } else {
            $scope.myOrderBy = x;
        }
    }

    // Populate info modal
    $scope.displayInfo = function(x) {
        $scope.gameName = x.name;
        $scope.author = x.author;
        $scope.releaseDate = x.release_date;
        $scope.tags = x.tags;

        if (x.imgs == null) {
            x.imgs = [];
            x.imgs[0] = defaultImage;
            x.imgs[1] = defaultImage;
            x.imgs[2] = defaultImage;
        }

        $scope.image1 = x.imgs[0];
        $scope.image1Text = $scope.gameName;
        $scope.image2 = x.imgs[1];
        $scope.image2Text = $scope.gameName;
        $scope.image3 = x.imgs[2];
        $scope.image3Text = $scope.gameName;

        $scope.currGame = x;

        _placeImage();
    }

    // Place images
    function _placeImage() {
        $('#img1').html('<img src="' + $scope.image1 + '" alt="Controller"/>\n' +
        '                                            <div class="carousel-caption">\n' +
        '                                                <h3>' + $scope.image1Text + '</h3>\n' +
        '                                            </div>');

        $('#img2').html('<img src="' + $scope.image2 + '" alt="Controller"/>\n' +
            '                                            <div class="carousel-caption">\n' +
            '                                                <h3>' + $scope.image2Text + '</h3>\n' +
            '                                            </div>');

        $('#img3').html('<img src="' + $scope.image3 + '" alt="Controller"/>\n' +
            '                                            <div class="carousel-caption">\n' +
            '                                                <h3>' + $scope.image3Text + '</h3>\n' +
            '                                            </div>');
    }

    // Display alerts
    function _displayError() {
        $('#alertBar').html('<div class="alert alert-danger fade in" id="failedInput">\n' +
            '        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>\n' +
            '        <strong>Invalid Input. Empty fields detected.</strong>\n' +
            '    </div>');
    }

    function _displaySuccess() {
        $('#alertBar').html('<div class="alert alert-success fade in" id="successInput">\n' +
            '        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>\n' +
            '        <strong>Success! ' + $scope.updateString + '</strong>\n' +
            '    </div>');
    }

    // Clear add game modal
    function _clearForm() {
        $scope.a_gameName = "";
        $scope.a_author = "";
        $scope.a_releaseDate = "";
        $scope.a_tags = "";
        $scope.a_img1 = "";
        $scope.a_img2 = "";
        $scope.a_img3 = "";
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

    function _success(response) {
        _refreshPageData();
        _clearForm();
        _displaySuccess();
    }
});