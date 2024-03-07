package carsharing.server.car;

import carsharing.server.dao.IDaoConnection;
import carsharing.server.dao.car.Car;
import carsharing.server.dao.company.Company;

import java.util.List;

public interface ICarDao extends IDaoConnection {
    public List<Car> getCarsByCompany(Company company);
    public Car getById(int id);
    public void update(Car arg);
    public void delete(Car car);
    public void create(Car car);
}
