package carsharing.server.dao.car;


import carsharing.server.dao.company.Company;
import carsharing.server.dao.company.CompanyDaoImpl;
import carsharing.server.dao.customer.Customer;
import carsharing.server.dao.customer.CustomerDaoImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements carsharing.server.car.ICarDao {
    private Connection connection;
    CompanyDaoImpl companyDao;
    public CarDaoImpl(CompanyDaoImpl companyDao) {
        this.companyDao = companyDao;
    }

    public List<Car> getCarsByCompany(Company company) {
        String query = "SELECT * FROM CAR SORT " + "WHERE COMPANY_ID=" + company.getId() + " ORDER BY ID ASC";
        try (Statement statement = connection.createStatement()) {
            ResultSet greatHouses = statement.executeQuery(query);
            ArrayList<Car> list = new ArrayList<Car>();
            while (greatHouses.next()) {
                // Retrieve column values
                int id = greatHouses.getInt("ID");
                String name = greatHouses.getString("NAME");

                if (id == 0) continue;
                list.add(new Car(id, name, company));
            }
            return list;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Car> getUnRentedCars(Company company) {
        String query = "SELECT * FROM CAR SORT " + "WHERE COMPANY_ID=" + company.getId() + " ORDER BY ID ASC";
        try (Statement statement = connection.createStatement()) {
            ResultSet greatHouses = statement.executeQuery(query);
            ArrayList<Car> list = new ArrayList<Car>();
            while (greatHouses.next()) {
                // Retrieve column values
                int id = greatHouses.getInt("ID");
                String name = greatHouses.getString("NAME");

                if (id == 0) continue;
                list.add(new Car(id, name, company));
            }
            return list;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Car getById(int _id) {
        String query = "SELECT * FROM CAR WHERE COMPANY_ID=" + _id + ";";
        try (Statement statement = connection.createStatement()) {
            ResultSet greatHouses = statement.executeQuery(query);
            ArrayList<Customer> list = new ArrayList<Customer>();
            if (greatHouses.next()) {
                // Retrieve column values
                int id = greatHouses.getInt("ID");
                String name = greatHouses.getString("NAME");
                int companyId = greatHouses.getInt("COMPANY_ID");
                Company company = companyDao.getById(companyId);
                return new Car(id, name, company);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Car car) {

    }

    @Override
    public void delete(Car car) {

    }

    @Override
    public void create(Car car) {
        String insert = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?,?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setString(1, car.getName());
            preparedStatement.setInt(2, car.getCompany().getId());
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
            String sql =  "CREATE TABLE IF NOT EXISTS CAR" +
                    "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    " NAME  VARCHAR(255) UNIQUE NOT NULL," +
                    " COMPANY_ID INTEGER NOT NULL, " +
                    " CONSTRAINT fk_car FOREIGN KEY (COMPANY_ID)" +
                    " REFERENCES COMPANY(ID));";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropTable()  {
        try {
            Statement  stmt = connection.createStatement();
            String sql =  "DROP TABLE CAR";
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
