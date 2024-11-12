package LibraryRepository;

import LibraryModel.HasID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An in-memory implementation of the IRepository interface for managing objects
 * that implement the HasID interface.
 *
 * @param <T> the type of objects managed by this repository, which must implement HasID
 */

public class InMemoryRepository<T extends HasID> implements IRepository<T>{

    private final Map<Integer, T> data = new HashMap<>();

    /**
     * Adds a new object to the repository.
     *
     * If an object with the same ID already exists in the repository, it will not be added.
     *
     * @param obj the object to be added to the repository
     */

    @Override
    public void add(T obj) {
        data.putIfAbsent(obj.getID(), obj);
    }

    /**
     * Updates an existing object in the repository.
     *
     * If the object with the specified ID does not exist, it will not be updated.
     *
     * @param obj the object to be updated in the repository
     */

    @Override
    public void update(T obj) {
        data.replace(obj.getID(), obj);
    }

    /**
     * Deletes an object from the repository by its ID.
     *
     * @param id the ID of the object to be deleted
     */

    @Override
    public void delete(int id) {
        data.remove(id);
    }

    /**
     * Retrieves an object from the repository by its ID.
     *
     * @param id the ID of the object to retrieve
     * @return the object with the specified ID, or null if it does not exist
     */

    @Override
    public T get(int id) {
        return data.get(id);
    }

    /**
     * Retrieves all objects in the repository.
     *
     * @return a list of all objects in the repository
     */

    @Override
    public List<T> getAll() {
        return data.values().stream().toList();
    }
}
