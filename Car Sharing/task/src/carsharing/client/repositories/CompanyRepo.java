package carsharing.client.repositories;

import carsharing.client.InputFromUser;
import carsharing.server.dao.IDaoConnection;
import carsharing.server.dao.company.Company;
import carsharing.server.dao.company.CompanyDaoImpl;

import java.sql.Connection;
import java.util.List;

public class CompanyRepo implements IDaoConnection {
    public CompanyDaoImpl companyDao = new CompanyDaoImpl();

    final InputFromUser inputFromUser;

    public CompanyRepo(InputFromUser inputFromUser) {
        this.inputFromUser = inputFromUser;
    }
    public List<Company> listCompanies() {
        List<Company> companies = companyDao.getAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            return null;
        }
        Company.printCompanies(companies);
        System.out.println("0. Back");
        return companies;
    }

    public List<Company> _listCompanies() {
        List<Company> companies = companyDao.getAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            return null;
        }
        Company.printCompanies(companies);
        System.out.println("0. Back");
        return companies;
    }

    public Company getCompany(int id) {
        return companyDao.getById(id);
    }

    public void createCompany() {
        System.out.println("Enter the company name:");
        String companyName = inputFromUser.getUserStringInput();
        companyDao.create(new Company(null, companyName));
        System.out.println("The company was created!");
        System.out.println();
    }

    public void dropCompanyTable() {
        companyDao.dropTable();
    }

    @Override
    public void setConnectionCreateTable(Connection connection) {
        companyDao.setConnectionCreateTable(connection);
    }
}
