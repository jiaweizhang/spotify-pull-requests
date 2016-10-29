/**
 * Created by alanguo on 8/6/16.
 */
spotifyCollab
    .controller('homeCtrl', function($scope, $location) {
        $scope.routeToLogin = function() {
            routeTo("login")
        }
        var routeTo = function(path) {
            $location.path(path);
        }
    });