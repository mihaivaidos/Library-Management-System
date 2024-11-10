package LibraryModel;

import java.io.Serializable;

@FunctionalInterface
public interface HasID extends Serializable {
    int getID();
}
