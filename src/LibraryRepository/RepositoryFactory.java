package LibraryRepository;

import LibraryModel.HasID;

/**
 * A class that represents dynamic repository selection.
 * It creates and initializes repositories of the selected type.
 */
public class RepositoryFactory {

    /**
     * Creates a repository based on the repositoryType that is given (in-memory, file or database)
     *
     * @param cls the Class that the repository is created for
     * @param repositoryType the type of the repository
     * @return a new repository
     */
    public static <T extends HasID> IRepository<T> createRepository(Class<T> cls, String repositoryType) {
        return switch (repositoryType.toLowerCase()) {
            case "inmemory" -> new InMemoryRepository<>();
            case "file" -> {
                String fileName = cls.getSimpleName().toLowerCase() + ".txt";
                String filePath = "C:\\Users\\Mihai\\IdeaProjects\\Library-Management-System\\src\\LibraryRepository\\FileRepositories\\" + fileName;
                yield new FileRepository<>(filePath);
            }
            case "database" -> throw new IllegalArgumentException("Database Repository in progress...");
            default -> throw new IllegalArgumentException("Invalid repository type: " + repositoryType);
        };
    }
}
