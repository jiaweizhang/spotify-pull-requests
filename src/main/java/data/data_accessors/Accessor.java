package data.data_accessors;

import org.jooq.DSLContext;

public class Accessor {
    protected DSLContext myQuery;

    public Accessor(){
        DBManager db = DBManager.getInstance();
        myQuery = db.getContext();
    }
}
