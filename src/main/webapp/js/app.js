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
        .when('/login', {
            templateUrl: '../views/login.html',
            controller: 'loginCtrl'
        })
        .when('/logout', {
            templateUrl: '../views/logout.html',
            controller: 'logoutCtrl'
        })
        .when('/playlistcreate', {
            templateUrl: '../views/playlist_create.html',
            controller: 'playlistCreateCtrl'
        })
        .otherwise({
            templateUrl: '../views/home.html',
            controller: 'homeCtrl'
        })
});
