package penguin;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class logs_show {

    private final IntegerProperty logID_manage;
    private final StringProperty logsAction_manage;
    private final StringProperty manage_where;
    private final IntegerProperty manage_record;
    private final ObjectProperty<LocalDate> manage_logDate;

    public logs_show(int logID_manage, String logsAction_manage, String manage_where, int manage_record, LocalDate manage_logDate) {
        this.logID_manage = new SimpleIntegerProperty(logID_manage);
        this.logsAction_manage = new SimpleStringProperty(logsAction_manage);
        this.manage_where = new SimpleStringProperty(manage_where);
        this.manage_record = new SimpleIntegerProperty(manage_record);
        this.manage_logDate = new SimpleObjectProperty<>(manage_logDate);
    }

    public static ObservableList<logs_show> getLogs(LocalDate date1, LocalDate date2) {
        ObservableList<logs_show> logList = FXCollections.observableArrayList();

        DBConnect connect = new DBConnect();
        try (Connection connection = connect.getConnection()) {
            if (connection != null) {
                String query = "SELECT log_id, action, table_name, record_id, log_date FROM logs WHERE log_date BETWEEN ? AND ?;";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setDate(1, java.sql.Date.valueOf(date1));
                    statement.setDate(2, java.sql.Date.valueOf(date2));

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            int logID_manage = resultSet.getInt("log_id");
                            String logsAction_manage = resultSet.getString("action");
                            String manage_where = resultSet.getString("table_name");
                            int manage_record = resultSet.getInt("record_id");
                            LocalDate manage_logDate = resultSet.getDate("log_date").toLocalDate();

                            logList.add(new logs_show(logID_manage, logsAction_manage, manage_where, manage_record, manage_logDate));
                        }
                    }
                }

            } else {
                System.err.println("Failed to establish a database connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logList;
    }

    public static ObservableList<logs_show> getLogs() {
        return getLogs(MainController.getSelectedDate1(), MainController.getSelectedDate2());
    }

    public IntegerProperty logID_manageProperty() {
        return logID_manage;
    }

    public StringProperty logsAction_manageProperty() {
        return logsAction_manage;
    }

    public StringProperty manage_whereProperty() {
        return manage_where;
    }

    public IntegerProperty manage_recordProperty() {
        return manage_record;
    }

    public ObjectProperty<LocalDate> manage_logDateProperty() {
        return manage_logDate;
    }
}
