/**
 * Created by alanguo on 8/6/16.
 */

var spotifyCollab = angular.module('spotifyCollabApp', ['ngRoute']);
spotifyCollab.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: '../views/home.html',
            controller: 'homeCtrl'
        })
        .when('/logout', {
            templateUrl: '../views/logout.html',
            controller: 'logoutCtrl'
        })
        .when('/playlists', {
            templateUrl: '../views/playlists.html',
            controller: 'playlistsController'
        })
        .when('/playlist', {
            templateUrl: '../views/playlist.html',
            controller: 'playlistController'
        })
        .when('/playlistcreate', {
            templateUrl: '../views/playlist_create.html',
            controller: 'playlistCreateCtrl'
        })
        .when('/join', {
            templateUrl: '../views/join.html',
            controller: 'joinController'
        })
        .otherwise({
            templateUrl: '../views/home.html',
            controller: 'homeCtrl'
        })
});

spotifyCollab.run(function ($rootScope) {
    $rootScope.checkCookies = function () {
        var preval = localStorage.getItem('auth');
        if (preval == null) {
            $rootScope.loggedIn = false;
            return false;
        } else {
            $rootScope.loggedIn = true;
            return true;
        }
    };

});

spotifyCollab.run(function ($rootScope, $location) {

    // register listener to watch route changes
    $rootScope.$on("$routeChangeStart", function (event, next, current) {
        if ($rootScope.checkCookies() == false) {
            $location.path("/");
        }
    });
});

spotifyCollab.controller('mainController', function ($scope) {
    $scope.logout = function () {
        console.log("logged out");
        localStorage.removeItem('auth');
    }
});

spotifyCollab.controller('playlistsController', function ($scope, $http) {
    $scope.playlists = [];

    $scope.getPlaylists = function () {
        $http({
            method: 'GET',
            url: '/api/playlists',
            headers: {'Authorization': localStorage.getItem('auth')}
        }).then(function successCallback(response) {
            $scope.playlists = response.data.body;
            console.log($scope.playlists);
        }, function errorCallback(response) {
            console.log(response);
        });
    };

    $scope.init = function () {
        $scope.getPlaylists();
    };

    $scope.init();
});

spotifyCollab.controller('playlistController', function ($scope, $http) {
    $scope.playlistId = window.location.href.split('#')[2];

    $scope.requests = [];

    $scope.getPlaylist = function () {
        $http({
            method: 'GET',
            url: '/api/playlist/' + $scope.playlistId,
            headers: {'Authorization': localStorage.getItem('auth')}
        }).then(function successCallback(response) {
            $scope.requests = response.data.body;
            console.log($scope.requests);
        }, function errorCallback(response) {
            console.log(response);
        });
    };

    $scope.vote = function (requestId, accept) {
        var voteData = {
            requestId: requestId,
            accept: accept
        };
        $http({
            method: 'POST',
            url: '/api/vote',
            headers: {'Authorization': localStorage.getItem('auth')},
            data: voteData
        }).then(function successCallback(response) {
            console.log(response);
            $scope.requests = $scope.getPlaylist();
        }, function errorCallback(response) {
            console.log(response);
        });
    };

    $scope.init = function () {
        $scope.getPlaylist();
    };

    $scope.init();
});

spotifyCollab.controller('joinController', function ($scope, $http) {

    $scope.submit = function () {
        var joinData = {
            playlistId: $scope.playlistId
        };
        $http({
            method: 'POST',
            url: '/api/join',
            headers: {'Authorization': localStorage.getItem('auth')},
            data: joinData
        }).then(function successCallback(response) {
            console.log(response);
            window.location = '/#/playlist#' + $scope.playlistId;
        }, function errorCallback(response) {
            console.log(response);
        });
    };
});