/**
 * Created by alanguo on 10/29/16.
 */
spotifyCollab
    .controller('playlistManageCtrl', function ($scope, $routeParams, $http, $rootScope) {
        $scope.param = $routeParams.param;
        var selectedPlaylistName = $scope.param;
        var findPlaylistId = function (selectedPlaylistName) {
            for (var i = 0; i < $rootScope.playlists.length; i++) {
                if ($rootScope.playlists[i].name == selectedPlaylistName) {
                    return $rootScope.playlists[i].id;
                }
            }
        };
        var selectedPlaylistId = findPlaylistId(selectedPlaylistName);

        console.log("Selected Playlist ID: " + selectedPlaylistId);
        $scope.getSongs();
        $scope.playlistSongs = [];

        // Get first song in list and load as embedded song
        var currentSongId = "";
        $scope.songEmbedLink;
        $scope.setEmbedLink = function(currentSongId) {
            $scope.songEmbedLink = "https://embed.spotify.com/?uri=spotify%3Atrack%" + currentSongId + "&theme=white";
        }


        // Get all song id's given the playlist id selected from left nav
        $scope.getSongs = $http({
            method: 'GET',
            url: '/api/playlists/' + selectedPlaylistId,
            headers: {'Authorization': localStorage.getItem('auth')}
        }).then(function successCallback(response) {
            var data = response.data.body;
            var parsedData = data;
            for (var i = 0; i < parsedData.length; i++) {
                $scope.playlistSongs.push(parsedData[i]);
            }
            console.log("All Songs in Playlist: " + $scope.playlistSongs);

            // if there are playlist songs, set first one to current play
            if ($scope.playlistSongs.length > 0) {
                currentSongId = $scope.playlistSongs[0];
            }
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            console.log(response);
        });

        $scope.accept = false;

        $scope.voteYes = function() {
            $scope.accept = true;
        }
        $scope.voteNo = function() {
            $scope.accept = false;
        }

        var voteData = {requestId: currentSongId, approve: $scope.accept};

        $scope.sendVote = $http({
            method: 'POST',
            url: '/api/vote',
            headers: {'Authorization': localStorage.getItem('auth')},
            data: voteData
        }).then(function successCallback(response) {
            console.log(response);
            // transition to next song
            $scope.playlistSongs.shift();

            if ($scope.playlistSongs.length > 0) {
                currentSongId = $scope.playlistSongs[0];
                $scope.setEmbedLink(currentSongId);
            }

        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            console.log(response);
        });



    });