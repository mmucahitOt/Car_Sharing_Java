package carsharing.client.repositories;

import carsharing.client.InputFromUser;
import carsharing.server.dao.IDaoConnection;
import carsharing.server.dao.car.Car;
import carsharing.server.dao.car.CarDaoImpl;
import carsharing.server.dao.company.Company;
import carsharing.server.dao.company.CompanyDaoImpl;
import carsharing.server.dao.customer.Customer;
import carsharing.server.dao.customer.CustomerDaoImpl;

import java.sql.Connection;
import java.util.List;

public class CarRepo implements IDaoConnection {
    public CarDaoImpl carDao;
    final InputFromUser inputFromUser;

    public CarRepo(InputFromUser inputFromUser, CompanyDaoImpl companyDao) {
        this.inputFromUser = inputFromUser;
        this.carDao = new CarDaoImpl(companyDao);
    }
    public List<Car> listCars(Company company) {
        List<Car> cars = carDao.getCarsByCompany(company);
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
            System.out.println();
            return null;
        }
        Car.printCars(cars);
        System.out.println("0. Back");
        System.out.println();
        return cars;
    }

    public void createCar(Company company) {
        System.out.println("Enter the car name:");
        String carName = inputFromUser.getUserStringInput();
        carDao.create(new Car(null, carName, company));
        System.out.println("The car was created!");
        System.out.println();
    }

    public void dropCarTable() {
        carDao.dropTable();
    }

    @Override
    public void setConnectionCreateTable(Connection connection) {
        carDao.setConnectionCreateTable(connection);
    }
}
