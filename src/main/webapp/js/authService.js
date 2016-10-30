/**
 * Created by alanguo on 10/30/16.
 */
spotifyCollab
    .factory('Auth', function(){

        return{
            isAuthenticated : function(){
                return (localStorage.getItem("auth") !== null)
            }
        }
    });