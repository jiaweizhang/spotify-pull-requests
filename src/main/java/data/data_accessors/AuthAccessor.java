package data.data_accessors;

import data.Users;
import org.jooq.Record;

import static db.tables.Users.USERS;
import static org.jooq.impl.DSL.row;

public class AuthAccessor extends Accessor {

    public AuthAccessor(){
        super();
    }

    public boolean isExist(String userID){
        return myQuery.select().from(USERS)
                .where(USERS.SPOTIFY_USERID.equal(userID))
                .fetchOne() != null;
    }

    public int insertUser(Users user){
       int output = myQuery
               .insertInto(USERS,USERS.SPOTIFY_USERID,USERS.EMAIL,USERS.AUTHORIZATION_CODE,USERS.REFRESH_TOKEN,USERS.ACCESS_TOKEN,USERS.EXPIRATION)
               .values(user.mySpotifyUserID,user.myEmail,user.myAuthToken,user.myRefreshToken,user.myAccessToken,user.myExpiration).execute();
       return output;
    }

    public Users getUser(String userID){
        Record userRecord = myQuery.select().from(USERS).where(USERS.SPOTIFY_USERID.equal(userID)).fetchOne();
        Users user = new Users(
                userRecord.getValue(USERS.SPOTIFY_USERID),
                userRecord.getValue(USERS.EMAIL),
                userRecord.getValue(USERS.AUTHORIZATION_CODE),
                userRecord.getValue(USERS.REFRESH_TOKEN),
                userRecord.getValue(USERS.ACCESS_TOKEN),
                userRecord.getValue(USERS.EXPIRATION));
        return user;
    }

    public int updateAccessTokenAndExpirationToken(Users user){
        return myQuery.update(USERS).set(row(USERS.ACCESS_TOKEN,USERS.EXPIRATION),row(user.myAccessToken,user.myExpiration)).where(USERS.SPOTIFY_USERID.equal(user.mySpotifyUserID)).execute();
    }



}
