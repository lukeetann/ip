package cors;

import cors.exception.CorsException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Storage {
    private File file;

    public Storage(String filePath) {
        file = new File(filePath);
    }

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