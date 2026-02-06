package cors;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import cors.exception.CorsException;

/**
 * Handles the loading and saving of task data to a local file.
 * This class encapsulates all file I/O operations for the CORS chatbot.
 */
public class Storage {
    /** The file object representing the data storage location. */
    private File file;

    /**
     * Constructs a Storage object with a specified file path.
     * * @param filePath The relative or absolute path to the data file (e.g., "./data/cors.csv").
     */
    public Storage(String filePath) {
        file = new File(filePath);
    }

    /**
     * Loads task data from the file into a String array.
     * Reads up to 100 lines from the storage file.
     * * @return A String array containing the raw data for each task.
     * @throws CorsException If an I/O error occurs during the reading process.
     */
    public String[] load() {
        String[] loadedTasks = new String[100];
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            for (int i = 0; i < 100; i++) {
                loadedTasks[i] = reader.readLine();
            }
        } catch (IOException e) {
            throw new CorsException("IOException");
        }
        return loadedTasks;
    }

    /**
     * Saves the current list of tasks to the storage file.
     * Overwrites the existing file content with the provided task strings.
     * * @param tasks An array of strings representing the tasks to be persisted.
     */
    public void saveToFile(String[] tasks) {
        try (PrintWriter out = new PrintWriter(file)) {
            for (String task : tasks) {
                out.println(task);
            }
        } catch (IOException e) {
            System.out.println("Unable to save to file");
        }
    }
}
