package LibraryRepository;

import Exceptions.DatabaseException;
import LibraryModel.HasID;

import java.util.List;

/**
 * A generic interface for a repository that manages objects of type T,
 * which must implement the HasID interface.
 *
 * @param <T> the type of objects managed by this repository, which must implement HasID
 */

public interface IRepository<T extends HasID> {

    /**
     * Adds a new object to the repository.
     *
     * @param obj the object to be added to the repository
     */

    void add(T obj) throws DatabaseException;

    /**
     * Retrieves an object from the repository by its ID.
     *
     * @param id the ID of the object to retrieve
     * @return the object with the specified ID, or null if it does not exist
     */

    T get(int id) throws DatabaseException;

    /**
     * Updates an existing object in the repository.
     *
     * @param obj the object to be updated in the repository
     */

    void update(T obj) throws DatabaseException;

    /**
     * Deletes an object from the repository by its ID.
     *
     * @param id the ID of the object to be deleted
     */

    void delete(int id) throws DatabaseException;

    /**
     * Retrieves all objects in the repository.
     *
     * @return a list of all objects in the repository
     */

    List<T> getAll() throws DatabaseException;
}
