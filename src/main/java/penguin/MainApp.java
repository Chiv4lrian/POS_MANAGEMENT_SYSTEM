package penguin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static penguin.MainController.logger;

public class MainApp extends Application {
    public static Stage stage1;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("penglog.fxml")));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Pengui Management System");
            Image icon_app = new Image("C:\\Users\\xgeor\\Music\\Ezio\\Projects\\POS_MANAGEMENT_SYSTEM\\src\\main\\resources\\img\\pengui.png");
            stage.getIcons().add(icon_app);
            stage.setScene(scene);
            stage.show();

            stage1 = stage;
        } catch(Exception e) {
            logger.severe("An error occurred: " + e.getMessage());
            logger.log(java.util.logging.Level.SEVERE, "Exception details: ", e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
