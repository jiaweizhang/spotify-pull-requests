/**
 * Created by alanguo on 10/30/16.
 */
spotifyCollab.run(['$rootScope', '$location', 'Auth', function ($rootScope, $location, Auth) {
    $rootScope.$on('$routeChangeStart', function (event) {
        if (localStorage.getItem("auth") !== null) {
            Auth.setUser();
        }
        if (!Auth.isLoggedIn()) {
            console.log('DENY');
            event.preventDefault();
            $location.path('/home');
        }
        else {
            console.log('ALLOW');
            $location.path('/home');
        }
    });
}])
    .factory('Auth', function(){
        $rootScope.isAuthenticated = false;

        return{
            setUser : function(){
                $rootScope.isAuthenticated = true;
            },
            isLoggedIn : function(){
                return $rootScope.isAuthenticated;
            }
        }
    });