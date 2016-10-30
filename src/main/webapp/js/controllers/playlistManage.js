/**
 * Created by alanguo on 10/29/16.
 */
spotifyCollab
    .controller('playlistManageCtrl', function ($scope, $routeParams, $http, $rootScope) {

        // Get first song in list and load as embedded song
        var currentSongId = "";
        $scope.setEmbedLink = function(currentSongId) {
            $scope.songEmbedLink = "https://embed.spotify.com/?uri=spotify%3Atrack%" + currentSongId + "&theme=white";
        };

        $scope.accept = false;
        var voteData = {requestId: currentSongId, approve: $scope.accept};

        $scope.voteYes = function() {
            $scope.accept = true;
            voteData = {requestId: currentSongId, approve: $scope.accept};
            $scope.sendVote();
        };

        $scope.voteNo = function() {
            $scope.accept = false;
            voteData = {requestId: currentSongId, approve: $scope.accept};
            $scope.sendVote();
        };

        $scope.sendVote = function() {
            $http({
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
                console.log(response);
            });
        }
    });