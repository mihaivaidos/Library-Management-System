package LibraryRepository;

import LibraryModel.HasID;

import java.util.List;

public interface IRepository<T extends HasID> {
    void add(T obj);
    T get(int id);
    void update(T obj);
    void delete(int id);
    List<T> getAll();
}
