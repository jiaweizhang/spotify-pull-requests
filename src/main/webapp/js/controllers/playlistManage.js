/**
 * Created by alanguo on 10/29/16.
 */
spotifyCollab
    .controller('playlistManageCtrl', function ($scope, $routeParams, $http, $rootScope) {

        // Get first song in list and load as embedded song
        var currentSongId = "";
        $scope.setEmbedLink = function (currentSongId) {
            $scope.songEmbedLink = "https://embed.spotify.com/?uri=spotify%3Atrack%" + currentSongId + "&theme=white";
        };

        $scope.accept = false;
        var voteData = {requestId: currentSongId, approve: $scope.accept};

        $scope.voteYes = function () {
            $scope.accept = true;
            voteData = {requestId: currentSongId, approve: $scope.accept};
            $scope.sendVote();
        };

        $scope.voteNo = function () {
            $scope.accept = false;
            voteData = {requestId: currentSongId, approve: $scope.accept};
            $scope.sendVote();
        };

    });