/**
 * Created by alanguo on 10/29/16.
 */
spotifyCollab
    .controller('playlistCreateCtrl', function ($scope, $rootScope, $http) {
        $scope.playlistName = "";
        $scope.threshold = ""; // make sure threshold validated as an int

        $scope.createPlaylist = function (playlistName, threshold) {
            var playlistData = {"playlistName": playlistName, "threshold": threshold};
            $http({
                method: 'POST',
                url: '/api/playlists',
                headers: {'Authorization': localStorage.getItem('auth')},
                data: playlistData
            }).then(function successCallback(response) {
                var data = response.data.body;
                var playlistId = data.playlistPR.id;

                window.location = '/playlist/' + playlistId;
            }, function errorCallback(response) {
                console.log(response);
            });
        };
    });