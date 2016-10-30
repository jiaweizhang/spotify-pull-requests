/**
 * Created by alanguo on 10/29/16.
 */
spotifyCollab
    .controller('playlistCreateCtrl', function ($scope, $rootScope, $http) {
        $scope.playlistName = "";
        $scope.threshold = ""; // make sure threshold validated as an int
        // $rootScope.playlists = ["Lit AF", "Chill AF", "Playlist 1", "Playlist 2"]; // TO DO: need to populate using API call
        $rootScope.playlists = [{id: 1, name: "Lit AF"}, {id: 2, name: "Chill AF"}]; //map playlist id to name

        $scope.createPlaylist = function (playlistName, threshold) {
            var playlistData = {"playlistName": playlistName, "threshold": threshold};
            $http({
                method: 'POST',
                url: '/api/playlists',
                headers: {'Authorization': localStorage.getItem('auth')},
                data: playlistData
            }).then(function successCallback(response) {
                console.log(reponse);

                // add playlist to list of playlists
                //$rootScope.playlists.push(playlistName);

                // refresh playlists
                $scope.getPlaylists();

            }, function errorCallback(response) {
                console.log(response);
            });
        }

        $scope.getPlaylists = function() {
            $http({
                method: 'GET',
                url: '/api/playlists',
                headers: {'Authorization': localStorage.getItem('auth')}
            }).then(function successCallback(response) {
                var parsedData = response.data.body;
                var newPlaylists = [];
                for (var i = 0; i < parsedData.length; i++) {
                    var newPlaylistName = parsedData[i].name;
                    var newPlaylistId = parsedData[i].id;
                    var newPlaylist = {"id": newPlaylistId, "name": newPlaylistName};
                    console.log(newPlaylist);
                    newPlaylists.push(newPlaylist);
                }
                $rootScope.playlists = newPlaylists;
                console.log("All Playlists: " + $rootScope.playlists);
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        }
    });