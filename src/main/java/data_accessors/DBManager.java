package data_accessors;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    @Autowired
    private Environment myEnviroment;

    private static DBManager instance = null;
    private Connection myConnection;
    private DSLContext myContext;

    protected DBManager(){
        connectDatabase();
        getContext();
    }

    public static DBManager getInstance(){
        if(instance == null){
            instance = new DBManager();
        }
        return instance;
    }

    private void connectDatabase(){
        try{
            myConnection = DriverManager.getConnection(
                    myEnviroment.getProperty("datasource.url"),
                    myEnviroment.getProperty("datasource.username"),
                    myEnviroment.getProperty("datasource.password")
            );
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private DSLContext getContext(){
        if(myContext == null){
            myContext = DSL.using(myConnection, SQLDialect.POSTGRES);
        }
        return myContext;
    }
}
