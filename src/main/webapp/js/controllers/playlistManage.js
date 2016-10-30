/**
 * Created by alanguo on 10/29/16.
 */
spotifyCollab
    .controller('playlistManageCtrl', function($scope, $routeParams, $http) {
        $scope.param = $routeParams.param;
        var selectedPlaylistName = $scope.param;
        var selectedPlaylistId = findPlaylistId(selectedPlaylistName);

        console.log("Selected Playlist ID: "+selectedPlaylistId);

        $scope.playlistSongs = [];

        // Get all song id's given the playlist id selected from left nav
        $http({
            method: 'GET',
            url: '/api/playlists/'+selectedPlaylistId,
            headers: {'Authorization':"penis"}
        }).then(function successCallback(response) {
            var data = response.data;
            var parsedData = JSON.parse(data);
            for (var i = 0; i < parsedData.length; i++) {
                $scope.playlistSongs.push(parsedData[i]);
            }
            console.log("All Songs in Playlist: "+ $scope.playlistSongs);
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
        });

        // Get first song in list and load as embedded song
        var currentSongId = playlistSongs[0];
        $scope.songEmbedLink = "https://embed.spotify.com/?uri=spotify%3Atrack%"+currentSongId+"&theme=white";

        var findPlaylistId = function(selectedPlaylistName) {
            for(var i = 0; i < $rootScope.playlists.length; i++) {
                if ($rootScope.playlists[i].name == selectedPlaylistName) {
                    return $rootSceop.playlists[i].id;
                }
            }
        }
    });