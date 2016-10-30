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
        .when('/playlistmanage', {
            templateUrl: '../views/playlist_manage.html',
            controller: 'playlistManageCtrl'
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
    console.log("playlistscontroller started");
});

spotifyCollab.controller('playlistController', function ($scope, $http) {
    var playlistId = window.location.href.split('#')[2];

    console.log(playlistId);
    $scope.requests = [];

    $scope.getPlaylists = function () {
        $http({
            method: 'GET',
            url: '/api/playlists',
            headers: {'Authorization': localStorage.getItem('auth')}
        }).then(function successCallback(response) {
            $scope.requests = response.data.body;
            console.log($scope.playLists);
        }, function errorCallback(response) {
            console.log(response);
        });
    };

    $scope.vote = function(requestId, accept) {
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
            $scope.getPlaylists();
        }, function errorCallback(response) {
            console.log(response);
        });
    };

    $scope.init = function () {
        $scope.getPlaylists();
    }
});