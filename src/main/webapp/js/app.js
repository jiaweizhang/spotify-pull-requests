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
        .when('/playlistcreate', {
            templateUrl: '../views/playlist_create.html',
            controller: 'playlistCreateCtrl'
        })
        .when('/playlistmanage/:param', {
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
            console.log("auth token is not found");
            return false;
        } else {
            $rootScope.loggedIn = true;
            console.log("auth token is found: " + preval);
            return true;
        }
    };

});

spotifyCollab.run(function ($rootScope, $location) {

    // register listener to watch route changes
    $rootScope.$on("$routeChangeStart", function (event, next, current) {
        console.log("change route");
        if ($rootScope.checkCookies() == false) {
            console.log($location.path());
            /*
             if ($location.path() == '/lumitrivia') {
             console.log("Switching to login");
             $location.path("/login");
             }
             */
            $location.path("/");
            //if ($location.path() == '/login') {
            //    $location.path("/lumitrivia");
            //}
        }
    });
});