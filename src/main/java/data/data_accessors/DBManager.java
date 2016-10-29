package data.data_accessors;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import utilities.PropertiesLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
    private static DBManager instance = null;
    private Connection myConnection;
    private DSLContext myContext;

    protected DBManager() {
        connectDatabase();
        getContext();
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private void connectDatabase() {
        Properties properties = PropertiesLoader.loadPropertiesFromPackage("application.properties");
        try {
            myConnection = DriverManager.getConnection(
                    properties.getProperty("spring.datasource.url"),
                    properties.getProperty("spring.datasource.username"),
                    properties.getProperty("spring.datasource.password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DSLContext getContext() {
        if (myContext == null) {
            myContext = DSL.using(myConnection, SQLDialect.POSTGRES);
        }
        return myContext;
    }
}
