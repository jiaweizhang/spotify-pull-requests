/**
 * Created by alanguo on 10/29/16.
 */
spotifyCollab
    .controller('playlistCreateCtrl', function ($scope, $rootScope, $http) {
        $scope.submit = function () {
            var playlistData =
            {
                playlistTitle: $scope.playlistName,
                threshold: parseInt($scope.threshold)
            };

            var parameter = JSON.stringify(playlistData);
            $http.post('/api/playlists', parameter, {headers: {'Authorization': localStorage.getItem('auth')} }).
            success(function(data, status, headers, config) {
                var parsedData = data.body;
                var playlistId = parsedData.playlistPR.id;

                window.location = '/#/playlist#' + playlistId;
            }).
            error(function(data, status, headers, config) {
                console.log(data);
            });
        };
    });