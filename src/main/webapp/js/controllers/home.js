/**
 * Created by alanguo on 8/6/16.
 */
alanWebsite
    .controller('homeCtrl', function($scope) {
        $scope.homeClass = "";
        $scope.activateHome = function() {
            if($scope.homeClass=="") {
                $scope.homeClass = "active";
            } else {
                $scope.homeClass = "";
            }

        }
    });