package LibraryModel;

import java.io.Serializable;

/**
 * A functional interface that defines a contract for classes that have a unique ID.
 * Classes implementing this interface should provide an implementation for retrieving the unique ID.
 */
@FunctionalInterface
public interface HasID extends Serializable {

    /**
     * Gets the unique ID of the implementing class instance.
     *
     * @return the unique ID of the instance
     */
    int getID();
}
