package cors;

import java.io.IOException;

import cors.ui.Cors;
import cors.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * A GUI for Cors using FXML.
 */
public class Main extends Application {
    private Cors cors = new Cors("cors.csv");
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            assert(cors != null);
            fxmlLoader.<MainWindow>getController().setCors(cors); // inject the Cors instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
