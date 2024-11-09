package LibraryRepository;

import LibraryModel.HasID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<T extends HasID> implements IRepository<T>{

    private final Map<Integer, T> data = new HashMap<>();

    @Override
    public void add(T obj) {
        data.putIfAbsent(obj.getID(), obj);
    }

    @Override
    public void update(T obj) {
        data.replace(obj.getID(), obj);
    }

    @Override
    public void delete(int id) {
        data.remove(id);
    }

    @Override
    public T get(int id) {
        return data.get(id);
    }

    @Override
    public List<T> getAll() {
        return data.values().stream().toList();
    }
}
