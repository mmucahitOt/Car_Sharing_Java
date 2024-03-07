package carsharing.server.dao.company;


import carsharing.server.dao.IDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements IDao<Company> {
    public final String name = "COMPANY";
    private Connection connection;

    public CompanyDaoImpl() {

    }
    @Override
    public List<Company> getAll() {
        String query = "SELECT * FROM COMPANY SORT ORDER BY ID";
        try (Statement statement = connection.createStatement()) {
            ResultSet greatHouses = statement.executeQuery(query);
            ArrayList<Company> list = new ArrayList<Company>();
            while (greatHouses.next()) {
                // Retrieve column values
                int id = greatHouses.getInt("ID");
                String name = greatHouses.getString("NAME");
                if (id == 0) continue;
                list.add(new Company(id, name));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Company getById(int id) {
        String query = "SELECT * FROM COMPANY WHERE ID=" + id + ";";
        try (Statement statement = connection.createStatement()) {
            ResultSet greatHouses = statement.executeQuery(query);
            ArrayList<Company> list = new ArrayList<Company>();
            if (greatHouses.next()) {
                // Retrieve column values
                int _id = greatHouses.getInt("ID");
                String name = greatHouses.getString("NAME");
                return new Company(id, name);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Company company) {

    }

    @Override
    public void delete(Company company) {

    }

    //@Override
    public void createCompany_(Company company) {
        String insert = "INSERT INTO COMPANY (NAME) VALUES (" + "'" + company.getName() + "'" + ");";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(insert);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Company company) {
        String insert = "INSERT INTO COMPANY (NAME) VALUES (?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void createTable()  {
        try {
            Statement  stmt = connection.createStatement();
            //STEP 3: Execute a query
            System.out.println("Creating table in given database...");
            String sql =  "CREATE TABLE IF NOT EXISTS COMPANY" +
                    "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    " NAME  VARCHAR(255) UNIQUE NOT NULL);";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropTable()  {
        try {
            Statement  stmt = connection.createStatement();
            String sql =  "DROP TABLE COMPANY";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setConnectionCreateTable(Connection connection) {
        this.connection = connection;
        this.createTable();
    }
}
