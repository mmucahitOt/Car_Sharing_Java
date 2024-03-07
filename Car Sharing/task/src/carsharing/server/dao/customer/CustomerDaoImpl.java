package carsharing.server.dao.customer;


import carsharing.server.dao.IDao;
import carsharing.server.dao.car.Car;
import carsharing.server.dao.car.CarDaoImpl;
import carsharing.server.dao.company.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements IDao<Customer> {
    public final String name = "CUSTOMER";
    private Connection connection;
    CarDaoImpl carDao;

    public CustomerDaoImpl( CarDaoImpl carDao) {
        this.carDao = carDao;
    }
    @Override
    public List<Customer> getAll() {
        String query = "SELECT * FROM CUSTOMER SORT ORDER BY ID";
        try (Statement statement = connection.createStatement()) {
            ResultSet greatHouses = statement.executeQuery(query);
            ArrayList<Customer> list = new ArrayList<Customer>();
            while (greatHouses.next()) {
                // Retrieve column values
                int id = greatHouses.getInt("ID");
                String name = greatHouses.getString("NAME");
                int carId = greatHouses.getInt("RENTED_CAR_ID");
                Car car = carDao.getById(carId);
                if (id == 0) continue;
                list.add(new Customer(id, name, car));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Car> getUnRentedCars(Company company) {

        String query = "SELECT * FROM CUSTOMER WHERE RENTED_CAR_ID IS NOT NULL ORDER BY ID";
        try (Statement statement = connection.createStatement()) {
            ResultSet greatHouses = statement.executeQuery(query);
            ArrayList<Car> list = new ArrayList<Car>();
            while (greatHouses.next()) {
                // Retrieve column values
                int id = greatHouses.getInt("ID");
                String name = greatHouses.getString("NAME");
                int carId = greatHouses.getInt("RENTED_CAR_ID");
                Car car = carDao.getById(carId);
                if (id == 0) continue;
                list.add(car);
            }
            List<Car> cars = carDao.getCarsByCompany(company);
            List<Integer> carIds = list.stream().map((car) -> {
                return car.getId();
            }).toList();
            return cars.stream().filter((car) -> !carIds.contains(car.getId())).toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer getById(int id) {
        String query = "SELECT * FROM CUSTOMER WHERE ID=" + id + ";";
        try (Statement statement = connection.createStatement()) {
            ResultSet greatHouses = statement.executeQuery(query);
            ArrayList<Customer> list = new ArrayList<Customer>();
            if (greatHouses.next()) {
                // Retrieve column values
                int _id = greatHouses.getInt("ID");
                String name = greatHouses.getString("NAME");
                int carId = greatHouses.getInt("RENTED_CAR_ID");
                Car car = carDao.getById(carId);
                return new Customer(id, name, car);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Customer customer) {
        String insert = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setInt(1, customer.getCar().getId());
            preparedStatement.setInt(2, customer.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rentCar(Customer customer, Car car) {
        String insert = "UPDATE CUSTOMER SET RENTED_CAR_ID=? WHERE ID=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setInt(1, car.getId());
            preparedStatement.setInt(2, customer.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void returnCar(Customer customer) {
        String insert = "UPDATE CUSTOMER SET RENTED_CAR_ID=? WHERE ID=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setNull(1, Types.NULL);
            preparedStatement.setInt(2, customer.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void _returnCar(Customer customer) {
        String insert = "UPDATE CUSTOMER SET RENTED_CAR_ID=? VALUES WHERE ID=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setNull(1, Types.NULL);
            preparedStatement.setInt(2, customer.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public void create(Customer customer) {
        String insert = "INSERT INTO CUSTOMER (NAME, RENTED_CAR_ID) VALUES (?,?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setString(1, customer.getName());
            if (customer.getCar() == null) {
                preparedStatement.setNull(2, Types.NULL);
            } else {
                preparedStatement.setInt(2, customer.getCar().getId());
            }
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
            String sql =  "CREATE TABLE IF NOT EXISTS CUSTOMER" +
                    "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    " NAME  VARCHAR(255) UNIQUE NOT NULL," +
                    " RENTED_CAR_ID INTEGER, " +
                    " CONSTRAINT fk_rented_car FOREIGN KEY (RENTED_CAR_ID)" +
                    " REFERENCES CAR(ID));";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropTable()  {
        try {
            Statement  stmt = connection.createStatement();
            String sql =  "DROP TABLE CUSTOMER";
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
