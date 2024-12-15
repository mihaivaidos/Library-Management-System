package LibraryRepository.DataBaseRepository;

import Exceptions.DatabaseException;
import LibraryModel.HasID;
import LibraryRepository.IRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DBRepository<T extends HasID> implements IRepository<T>, AutoCloseable {

    protected Connection connection;

    public DBRepository(String DBUrl, String DBUser, String DBPassword) throws DatabaseException {
        try
        {
            this.connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
        }
        catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    /**
     * Abstract method for subclasses to specify the table name.
     */
    protected abstract String getTableName();

    /**
     * Method to map a ResultSet row to an object of type T.
     * Must be implemented by subclasses.
     */
    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException, DatabaseException;

    @Override
    public T get(int id) throws DatabaseException {
        String SQL = "SELECT * FROM " + getTableName() + " WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return mapResultSetToEntity(rs);
            }
            else return null;
        }
        catch(SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    @Override
    public List<T> getAll() throws DatabaseException {
        String SQL = "SELECT * FROM " + getTableName();
        try(PreparedStatement statement = connection.prepareStatement(SQL)){
            ResultSet resultSet = statement.executeQuery();
            List<T> objects = new ArrayList<>();
            while(resultSet.next()){
                objects.add(mapResultSetToEntity(resultSet));
            }
            return objects;
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) throws DatabaseException {
        String SQL = "DELETE FROM " + getTableName() + " WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
        catch(SQLException e){
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

}