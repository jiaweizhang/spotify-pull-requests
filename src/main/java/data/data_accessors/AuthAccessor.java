package data.data_accessors;

import data.Users;
import org.jooq.Record;

import static db.tables.Users.USERS;

public class AuthAccessor extends Accessor {

    public AuthAccessor() {
        super();
    }

    public boolean isExist(String spotifyId) {
        return myQuery.select().from(USERS)
                .where(USERS.SPOTIFY_ID.equal(spotifyId))
                .fetchOne() != null;
    }

    public int createUser(Users user) {
        return myQuery
                .insertInto(USERS, USERS.SPOTIFY_ID, USERS.EMAIL, USERS.AUTHORIZATION_CODE, USERS.REFRESH_TOKEN, USERS.ACCESS_TOKEN, USERS.EXPIRATION)
                .values(user.spotifyId, user.email, user.authorizationCode, user.refreshToken, user.accessToken, user.expiration).execute();
    }

    public Users getUser(String spotifyId) {
        Record userRecord = myQuery.select().from(USERS).where(USERS.SPOTIFY_ID.equal(spotifyId)).fetchOne();
        Users user = new Users(
                userRecord.getValue(USERS.SPOTIFY_ID),
                userRecord.getValue(USERS.EMAIL),
                userRecord.getValue(USERS.AUTHORIZATION_CODE),
                userRecord.getValue(USERS.REFRESH_TOKEN),
                userRecord.getValue(USERS.ACCESS_TOKEN),
                userRecord.getValue(USERS.EXPIRATION));
        return user;
    }

    public int updateUser(Users user) {
        return myQuery.update(USERS)
                .set(USERS.EMAIL, user.email)
                .set(USERS.AUTHORIZATION_CODE, user.authorizationCode)
                .set(USERS.REFRESH_TOKEN, user.refreshToken)
                .set(USERS.ACCESS_TOKEN, user.accessToken)
                .set(USERS.EXPIRATION, user.expiration)
                .where(USERS.SPOTIFY_ID.equal(user.spotifyId))
                .execute();
    }


}
