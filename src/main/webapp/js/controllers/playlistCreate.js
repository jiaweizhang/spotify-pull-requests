/**
 * Created by alanguo on 10/29/16.
 */
spotifyCollab
    .controller('playlistCreateCtrl', function ($scope, $rootScope, $http) {
        $scope.submit = function () {
            var playlistData =
            {
                'playlistName': $scope.playlistName,
                'threshold': parseInt($scope.threshold)
            };
            $http({
                method: 'POST',
                url: '/api/playlists',
                headers: {'Authorization': localStorage.getItem('auth')},
                data: playlistData
            }).then(function successCallback(response) {
                var data = response.data.body;
                var playlistId = data.playlistPR.id;

                window.location = '/#/playlist#' + playlistId;
            }, function errorCallback(response) {
                console.log(response);
            });
        };
    });