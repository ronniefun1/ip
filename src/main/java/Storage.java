import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Storage {
    private final Path file;

    public Storage(String filePath) {

        this.file = Paths.get("output", filePath);
    }

    public void checkFile() {
        try {
            // Ensure parent directories exist
            if (Files.notExists(file.getParent())) {
                Files.createDirectories(file.getParent());
            }

            // Ensure file exists
            if (Files.notExists(file)) {
                Files.createFile(file);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public ArrayList<Task> load() {
        checkFile();
        ArrayList<Task> taskList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(file);
            for (String line : lines) {
                if (line.startsWith("T") || line.startsWith("E")
                        || line.startsWith("D")) {
                    Optional<Task> t = createTask(line);
                    t.ifPresent(taskList::add);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return taskList;
    }

    public void writeFile(ArrayList<Task> taskList) {
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (Task t : taskList) {
                writer.write(t.toFileString());
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void appendLine(String line) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                file,
                java.nio.file.StandardOpenOption.CREATE,
                java.nio.file.StandardOpenOption.APPEND)){
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static Optional<Task> createTask(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean status = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
        case "T":
            return Optional.of(new Todo(description, status));
        case "D":
            return Optional.of(new Deadline(description, status, parts[3]));
        case "E":
            return Optional.of(new Event(description, status, parts[3], parts[4]));
        default:
            return Optional.empty();
        }
    }
}