package carsharing.client.repositories;

import carsharing.client.InputFromUser;
import carsharing.server.dao.IDaoConnection;
import carsharing.server.dao.car.Car;
import carsharing.server.dao.car.CarDaoImpl;
import carsharing.server.dao.company.Company;
import carsharing.server.dao.customer.Customer;
import carsharing.server.dao.customer.CustomerDaoImpl;

import java.sql.Connection;
import java.util.List;

public class CustomerRepo implements IDaoConnection {
    public CustomerDaoImpl customerDao;

    final InputFromUser inputFromUser;

    public CustomerRepo(InputFromUser inputFromUser, CarDaoImpl carDao) {
        this.inputFromUser = inputFromUser;
        this.customerDao = new CustomerDaoImpl(carDao);
    }
    public List<Customer> listCustomers() {
        List<Customer> customers = customerDao.getAll();
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
            System.out.println();
            return null;
        }
        Customer.printCustomers(customers);
        System.out.println("0. Back");
        System.out.println();
        return customers;
    }

    public List<Car> listUnRentedCars(Company company) {
        List<Car> cars = customerDao.getUnRentedCars(company);
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

    public void createCustomer(Car car) {
        System.out.println("Enter the customer name:");
        String customerName = inputFromUser.getUserStringInput();
        customerDao.create(new Customer(null, customerName, car));
        System.out.println("The customer was created!");
        System.out.println();
    }

    public boolean rentCar(Customer customer, Car car) {
        Customer _customer = customerDao.getById(customer.getId());
        if (_customer.getCar() != null) {
            return false;
        }
        customerDao.rentCar(customer, car);
        return true;
    }
    public void returnCar(Customer customer) {
        if (customer.getCar() == null) {
            if (customer.alreadyReturned) {
                System.out.println("You've returned a rented car!");
                return;
            }
            System.out.println("You didn't rent a car!");
            System.out.println();
            return;
        }
        customer.alreadyReturned = true;
        customerDao.returnCar(customer);
        System.out.println("You've returned a rented car!");
        System.out.println();
    }

    public void dropCustomerTable() {
        customerDao.dropTable();
    }

    @Override
    public void setConnectionCreateTable(Connection connection) {
        customerDao.setConnectionCreateTable(connection);
    }


    public void rentedCar(Customer customer) {
        if (customer.getCar() == null) {
            System.out.println("You didn't rent a car!");
            System.out.println();
            return;
        }
        System.out.println("Your rented car:");
        System.out.println(customer.getCar().getName());
        System.out.println("Company:");
        System.out.println(customer.getCar().getCompany().getName());
        System.out.println();
    }
}
