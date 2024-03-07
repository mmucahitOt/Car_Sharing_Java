package carsharing.server.dao;

import java.util.List;

public interface IDao<T> extends IDaoConnection {
    public List<T> getAll();
    public T getById(int id);
    public void update(T arg);
    public void delete(T arg);
    public void create(T arg);
}
