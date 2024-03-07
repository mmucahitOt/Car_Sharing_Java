package carsharing.server.database;

import carsharing.server.dao.IDaoConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class DatabaseConnection {
    // JDBC driver name and database URL
    Connection conn = null;
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_PATH = "jdbc:h2:./src/carsharing/db/";
    static final String defaultDbName = "carsharing";
    static String DB_URL = DB_PATH + defaultDbName;
    //  Database credentials
    private DatabaseConnection(String dbName) {
        this.setDB_URL(dbName);
    }
    private void setDB_URL(String dbName) {
         DB_URL = DB_PATH + (dbName != null ? dbName : defaultDbName);
    }

    private void connectToDb() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL);
        conn.setAutoCommit(true);
    }

    public static DatabaseConnection initDatabase(String dbName) throws SQLException, ClassNotFoundException {
        DatabaseConnection db = new DatabaseConnection(dbName);
        db.connectToDb();
        return db;
    }

    public void closeConnection() throws SQLException {
        this.conn.close();
    }
    public void setConnectionOfDaos(IDaoConnection[] daos) {
        Arrays.stream(daos).forEach((dao) -> dao.setConnectionCreateTable(this.conn));
    }
}