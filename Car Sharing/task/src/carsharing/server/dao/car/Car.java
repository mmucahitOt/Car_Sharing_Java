package carsharing.server.dao.car;

import carsharing.server.dao.company.Company;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Car {
    private final Integer id;
    private String name;
    private Company company;


    public Car(Integer id, String name, Company company) {
        this.id = id;
        this.name = name;
        this.company = company;
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


    public static void printCars(List<Car> carList) {
        AtomicInteger index = new AtomicInteger(1);
        carList.forEach(((car) -> {
            System.out.printf("%d. %s\n", index.get(), car.getName());
            index.getAndIncrement();
        }));
    }

    public Company getCompany() {
        return company;
    }
}
