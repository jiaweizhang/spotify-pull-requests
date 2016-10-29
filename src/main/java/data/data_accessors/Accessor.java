package data.data_accessors;

import org.jooq.DSLContext;

import java.sql.Connection;

public class Accessor {
    protected DSLContext myQuery;

    public Accessor(){
        DBManager db = DBManager.getInstance();
        myQuery = db.getContext();
    }
}
