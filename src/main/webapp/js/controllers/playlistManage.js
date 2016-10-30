/**
 * Created by alanguo on 10/29/16.
 */
spotifyCollab
    .controller('playlistManageCtrl', function($scope, $routeParams, $http, $rootScope) {
        $scope.param = $routeParams.param;
        var selectedPlaylistName = $scope.param;
        var findPlaylistId = function(selectedPlaylistName) {
            for(var i = 0; i < $rootScope.playlists.length; i++) {
                if ($rootScope.playlists[i].name == selectedPlaylistName) {
                    return $rootScope.playlists[i].id;
                }
            }
        };
        var selectedPlaylistId = findPlaylistId(selectedPlaylistName);

        console.log("Selected Playlist ID: "+selectedPlaylistId);

        $scope.playlistSongs = [];

        // Get first song in list and load as embedded song
        var currentSongId = "";
        $scope.songEmbedLink = "https://embed.spotify.com/?uri=spotify%3Atrack%"+currentSongId+"&theme=white";

        // Get all song id's given the playlist id selected from left nav
        $http({
            method: 'GET',
            url: '/api/playlists/'+selectedPlaylistId,
            headers: {'Authorization':localStorage.getItem('auth')}
        }).then(function successCallback(response) {
            var data = response.data.body;
            var parsedData = JSON.parse(data);
            for (var i = 0; i < parsedData.length; i++) {
                $scope.playlistSongs.push(parsedData[i]);
            }
            console.log("All Songs in Playlist: "+ $scope.playlistSongs);

            // if there are playlist songs, set first one to current play
            if ($scope.playlistSongs.length > 0) {
                currentSongId = $scope.playlistSongs[0];
            }
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
        });




    });