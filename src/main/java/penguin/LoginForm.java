package penguin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Optional;

import static penguin.MainController.logger;

public class LoginForm {
    int counts = 0;

    @FXML
    private Button login_butt;
    @FXML
    private Button exit_butt;
    @FXML
    private Label msg_null;
    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private ImageView user_logo;
    @FXML
    private ImageView pass_logo;

    @FXML
    public void loginformie(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            exit_buttOnAction(null);
        }
    }

    // img when clicked focus on textfield
    public void focus() {
        user_logo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            user.requestFocus();
        });
        pass_logo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            password.requestFocus();
        });
    }

    // login_butt on action
    public void login_buttOnAction(ActionEvent event) {
        if (user.getText().isBlank() && password.getText().isBlank()) {
            msg_null.setVisible(true);
            msg_null.setText("Don't Leave it Blank");
        } else if (user.getText().isBlank() || password.getText().isBlank()) {
            if (user.getText().isBlank()) {
                msg_null.setVisible(true);
                msg_null.setText("Enter Your Username");
            } else {
                msg_null.setVisible(true);
                msg_null.setText("Enter Your Password");
            }
        } else {
            try {
                if (validatelogin()) {
                    String passRes = user.getText();
                    logDetails(passRes);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("main_app.fxml"));
                    Parent root = loader.load();
                    MainController con2 = loader.getController();
                    con2.setPassRes(passRes);
                    con2.displayInfo();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.show();
                } else {
                    logDetails2(user.getText());
                    if (confirmRetry()) {
                        if (counts >= 3) {
                            Stage stage = (Stage) exit_butt.getScene().getWindow();
                            stage.close();
                        } else {
                            user.setText("");
                            password.setText("");
                        }
                    }
                }
            } catch (Exception e) {
                logger.severe("An error occurred: " + e.getMessage());
                logger.log(java.util.logging.Level.SEVERE, "Exception details: ", e);
            }
        }
    }

    public boolean validatelogin() {
        DBConnect connectNow = new DBConnect();
        try (Connection connectDB = connectNow.getConnection()) {
            String verifylogin = "SELECT count(1), u_code, name, username FROM account WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connectDB.prepareStatement(verifylogin)) {
                preparedStatement.setString(1, user.getText());
                preparedStatement.setString(2, password.getText());
                ResultSet query_result = preparedStatement.executeQuery();
                return query_result.next() && query_result.getInt(1) == 1;
            }
        } catch (SQLException e) {
            logger.severe("An error occurred: " + e.getMessage());
            logger.log(java.util.logging.Level.SEVERE, "Exception details: ", e);
        }
        return false;
    }

    // exit button
    public void exit_buttOnAction(ActionEvent e) {
        Stage stage = (Stage) exit_butt.getScene().getWindow();
        stage.close();
    }

    private boolean confirmRetry() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Login Failed");
        alert.setHeaderText("Incorrect username or password");
        alert.setContentText("Do you want to try again?");

        ButtonType retryButton = new ButtonType("Retry", ButtonBar.ButtonData.YES);
        ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(retryButton, exitButton);

        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == retryButton;
    }

    private void logDetails(String personName) {
        DBConnect connectNow = new DBConnect();
        String sql = "INSERT INTO account_logs (username,action) VALUES (?,?)";
        try (Connection connectDB = connectNow.getConnection()) {
            PreparedStatement memoPs = connectDB.prepareStatement(sql);
            memoPs.setString(1, personName);
            memoPs.setString(2, "Login in to the System");
            memoPs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void logDetails2(String personName) {
        DBConnect connectNow = new DBConnect();
        String sql = "INSERT INTO account_logs (username,action) VALUES (?,?)";
        try (Connection connectDB = connectNow.getConnection()) {
            PreparedStatement memoPs = connectDB.prepareStatement(sql);
            memoPs.setString(1, personName);
            memoPs.setString(2, "Attempted Login");
            memoPs.executeUpdate();
        } catch (SQLException e) {
            logger.severe("An error occurred: " + e.getMessage());
            logger.log(java.util.logging.Level.SEVERE, "Exception details: ", e);
        }
    }
}
