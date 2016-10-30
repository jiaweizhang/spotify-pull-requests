/**
 * Created by alanguo on 10/29/16.
 */
spotifyCollab
    .controller('loginCtrl', [ '$scope', function ($scope, $window) {
        var link = "https://accounts.spotify.com/authorize/?client_id=7f5795cf20ba45b08ca27a4a8f92cf23&response_type=code&redirect_uri=http%3A%2F%2Feasywebapi.com%3A8080%2Fredirect&scope=playlist-read-private%20playlist-modify-public%20playlist-modify-private&state=spr"

        //submit
        $scope.login = function () {
            $window.open(link);
        };
    }])