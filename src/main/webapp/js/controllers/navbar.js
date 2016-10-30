/**
 * Created by alanguo on 10/29/16.
 */
spotifyCollab
    .controller('navbarCtrl', function($scope, $rootScope, $http) {

       // $rootScope.playlists = ["Lit AF", "Chill AF", "Playlist 1", "Playlist 2"]; // TO DO: need to populate using API call
        $rootScope.playlists = [{id:1, name: "Lit AF"}, {id:2, name: "Chill AF"}]; //map playlist id to name

        $http({
            method: 'GET',
            url: '/api/playlists',
            headers: {'Authorization':localStorage.getItem('auth')}
        }).then(function successCallback(response) {
            var data = response.data;
            var parsedData = JSON.parse(data);
            var newPlaylists = [];
            for (var i = 0; i < parsedData.length; i++) {
                var newPlaylistName = parsedData[i].name;
                var newPlaylistId = parsedData[i].id;
                var newPlaylist = {"id": newPlaylistId, "name":newPlaylistName};
                console.log(newPlaylist);
                newPlaylists.push(newPlaylist);
            }
            $rootScope.playlists = newPlaylists;
            console.log("All Playlists: "+$rootScope.playlists);
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
        });
    });