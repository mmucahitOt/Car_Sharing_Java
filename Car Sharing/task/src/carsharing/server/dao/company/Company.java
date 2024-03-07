package carsharing.server.dao.company;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Company {
    private final Integer id;
    private String name;

    public Company(Integer id, String name) {
        this.id = id;
        this.name = name;
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


    public static void printCompanies(List<Company> companyList) {
        AtomicInteger index = new AtomicInteger(1);
        companyList.forEach(((company) -> {
            System.out.printf("%d. %s\n", index.get(), company.getName());
            index.getAndIncrement();
        }));
    }

    public static void _printCompanies(List<Company> companyList) {
        AtomicInteger index = new AtomicInteger(1);
        System.out.println("Company list:");
        companyList.forEach(((company) -> {
            System.out.printf("%d. %s\n", index.get(), company.getName());
            index.getAndIncrement();
        }));
    }
}
