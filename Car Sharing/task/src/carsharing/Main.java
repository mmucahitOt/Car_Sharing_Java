package carsharing;

import carsharing.client.UserInterface;
import carsharing.server.database.DatabaseConnection;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String dbName = getDbName(args);
        DatabaseConnection databaseConnection = DatabaseConnection.initDatabase(dbName);
        UserInterface userInterface = new UserInterface(databaseConnection);
        userInterface.commandLoop();
    }

    public static String getDbName(String[] args) {
        if (args.length > 2) {
            return args[1];
        }
        return null;
    }
}