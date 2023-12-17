module com.pengui.pengui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.j;

    opens penguin to javafx.fxml;
    exports penguin;
}