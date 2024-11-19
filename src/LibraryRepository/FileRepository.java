package LibraryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.Consumer;
import LibraryModel.HasID;

/**
 * A repository implementation that stores data in a file.
 *
 * @param <T> The type of objects stored in the repository, which must implement HasId.
 */

public class FileRepository<T extends HasID> implements IRepository<T> {

    private final String filePath;

    /**
     * Constructs a new FileRepository with the specified file path.
     *
     * @param filePath The path to the file where data will be stored.
     */

    public FileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void add(T obj) {
        doInFile(data -> data.putIfAbsent(obj.getID(), obj));
    }

    @Override
    public T get(int id) {
        return readDataFromFile().get(id);
    }

    @Override
    public void update(T obj) {
        doInFile(data -> data.replace(obj.getID(), obj));
    }

    @Override
    public void delete(int id) {
        doInFile(data -> data.remove(id));
    }

    @Override
    public List<T> getAll() {
        return readDataFromFile().values().stream().toList();
    }

    /**
     * Performs an operation on the data stored in the file.
     *
     * @param function The function to apply to the data.
     */

    private void doInFile(Consumer<Map<Integer, T>> function) {
        Map<Integer, T> data = readDataFromFile();
        function.accept(data);
        writeDataToFile(data);
    }

    /**
     * Reads the data from the file.
     *
     * @return The data stored in the file, or an empty map if the file is empty or does not exist.
     */

    private Map<Integer, T> readDataFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Map<Integer, T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }

    /**
     * Writes the data to the file.
     *
     * @param data The data to write to the file.
     */

    private void writeDataToFile(Map<Integer, T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

