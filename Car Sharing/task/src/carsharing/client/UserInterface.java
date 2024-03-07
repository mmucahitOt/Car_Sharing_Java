package carsharing.client;

import carsharing.client.command.MenuCommand;
import carsharing.client.command.carCommand.CarCommand;
import carsharing.client.command.companyCommand.CompanyCommand;
import carsharing.client.command.custumerCommand.CustomerCommand;
import carsharing.client.repositories.CarRepo;
import carsharing.client.repositories.CompanyRepo;
import carsharing.client.repositories.CustomerRepo;
import carsharing.server.dao.IDaoConnection;
import carsharing.server.dao.car.Car;
import carsharing.server.dao.company.Company;
import carsharing.server.dao.customer.Customer;
import carsharing.server.dao.customer.CustomerDaoImpl;
import carsharing.server.database.DatabaseConnection;

import java.sql.SQLException;
import java.util.List;

public class UserInterface {
    InputFromUser inputFromUser = new InputFromUser();
    CompanyRepo companyRepo = new CompanyRepo(inputFromUser);
    CarRepo carRepo = new CarRepo(inputFromUser, companyRepo.companyDao);
    CustomerRepo customerRepo = new CustomerRepo(inputFromUser, carRepo.carDao);

    private final DatabaseConnection connection;

    public UserInterface(DatabaseConnection connection) {
        this.connection = connection;
        IDaoConnection[] daoConnections = {companyRepo, carRepo, customerRepo};
        connection.setConnectionOfDaos(daoConnections);
    }
    public void commandLoop() throws SQLException {
        while (true) {
            MenuCommand command = inputFromUser.askForMenuCommand();

            switch (command) {
                case LOGIN_MANAGER -> loginAsManagerCommandLoop();
                case LOGIN_CUSTOMER -> loginAsCustomerCommandLoop();
                case CREATE_CUSTOMER -> createCustomer();
                case EXIT -> exitCommand();
            }
        }
    }

    private void createCustomer() {
        customerRepo.createCustomer(null);
    }

    private void loginAsCustomerCommandLoop() {
        while (true) {
            List<Customer> customers = customerRepo.listCustomers();
            if (customers != null && !customers.isEmpty()) {
                int customerIndex = inputFromUser.askForCompanyId();
                if (customerIndex != 0) {
                    Customer customer = customers.get(customerIndex - 1);
                    customerCommandLoop(customer);

                }
                if (customerIndex == 0) {
                    return;
                }
            }
            return;
        }
    }

    public void customerCommandLoop(Customer _customer) {
        while (true) {
            Customer customer = customerRepo.customerDao.getById(_customer.getId());
            CustomerCommand command = inputFromUser.askForCustomerCommand();
            switch (command) {
                case RentCar -> rentCar(customer);
                case ReturnCar -> customerRepo.returnCar(customer);
                case RentedCar -> customerRepo.rentedCar(customer);

                case Back -> {
                    return;
                }
            }
        }
    }

    public void rentCar(Customer customer) {
        if (customer.getCar() != null) {
            System.out.println("You've already rented a car!");
            System.out.println();
            return;
        }
        Company company = null;
        Car car = null;
        System.out.println("Choose a company:");
        List<Company> companies = companyRepo._listCompanies();
        if (companies != null && !companies.isEmpty()) {
            int companyIndex = inputFromUser.askForCompanyId();
            if (companyIndex != 0) {
                company = companies.get(companyIndex - 1);
            }
        } else {
            System.out.println();
            return;
        }

        System.out.println("Choose a car:");
        List<Car> cars = customerRepo.listUnRentedCars(company);
        if (cars != null && !cars.isEmpty()) {
            int carIndex = inputFromUser.askForCompanyId();
            if (carIndex != 0) {
                car = cars.get(carIndex - 1);
            }
        } else {
            System.out.println();
            return;
        }
        if (car != null) {
            customerRepo.rentCar(customer, car);

            System.out.println("You rented '" + car.getName() + "'");
            System.out.println();
        }

    }


    public void loginAsManagerCommandLoop() {
        while (true) {
            CompanyCommand command = inputFromUser.askForCompanyCommand();

            switch (command) {
                case CompanyList -> {
                    System.out.println("Choose the company:");
                    List<Company> companies = companyRepo.listCompanies();
                    if (companies != null && !companies.isEmpty()) {
                        int companyIndex = inputFromUser.askForCompanyId();
                        if (companyIndex != 0) {
                            Company company = companies.get(companyIndex - 1);
                            companyCommandLoop(company);
                        }
                    } else {
                        System.out.println();
                    }

                }
                case CreateCompany -> companyRepo.createCompany();
                case Back -> {
                    return;
                }
            }
        }
    }

    private void exitCommand() throws SQLException {
        //companyRepo.dropCompanyTable();
        //carRepo.dropCarTable();
        //customerRepo.dropCustomerTable();


        connection.closeConnection();
        System.exit(0);
    }

    public void companyCommandLoop(Company company) {
        System.out.printf("'%s' company\n", company.getName());
        while (true) {
            CarCommand command = inputFromUser.askForCarCommand();
            switch (command) {
                case CarList -> {
                    System.out.println("Car list:");
                    carRepo.listCars(company);
                }
                case CreateCar -> carRepo.createCar(company);
                case Back -> {
                    return;
                }
            }
        }
    }
}
