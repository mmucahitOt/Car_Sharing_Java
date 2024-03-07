package carsharing.server.dao.customer;

import carsharing.server.dao.car.Car;
import carsharing.server.dao.company.Company;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Customer {
    private final Integer id;
    private String name;

    private Car car;

    public boolean alreadyReturned =  false;

    public Customer(Integer id, String name, Car car) {
        this.id = id;
        this.name = name;
        this.car = car;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static void printCustomers(List<Customer> customerList) {
        AtomicInteger index = new AtomicInteger(1);
        System.out.println("Customer list:");
        customerList.forEach(((customer) -> {
            System.out.printf("%d. %s\n", index.get(), customer.getName());
            index.getAndIncrement();
        }));
    }


    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

}
