package penguin;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class logs_show {

    private final StringProperty logID_manage;
    private final StringProperty logsAction_manage;
    private final StringProperty manage_where;
    private final StringProperty manage_record;
    private final ObjectProperty<Timestamp> manage_logDate;

    public logs_show(String logID_manage, String logsAction_manage, String manage_where, String manage_record, Timestamp manage_logDate) {
        this.logID_manage = new SimpleStringProperty(logID_manage);
        this.logsAction_manage = new SimpleStringProperty(logsAction_manage);
        this.manage_where = new SimpleStringProperty(manage_where);
        this.manage_record = new SimpleStringProperty(manage_record);
        this.manage_logDate = new SimpleObjectProperty<>(manage_logDate);
    }

    public static ObservableList<logs_show> getLogs() {
        ObservableList<logs_show> logList = FXCollections.observableArrayList();

        DBConnect connect = new DBConnect();
        try (Connection connection = connect.getConnection()) {
            if (connection != null) {
                String query = "SELECT log_id, action, table_name, record_id, log_date FROM logs";

                try (PreparedStatement statement = connection.prepareStatement(query)) {

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            String logID_manage = resultSet.getString("log_id");
                            String logsAction_manage = resultSet.getString("action");
                            String manage_where = resultSet.getString("table_name");
                            String manage_record = resultSet.getString("record_id");
                            Timestamp manage_logDate = resultSet.getTimestamp("log_date");

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

    public StringProperty logID_manageProperty() {
        return logID_manage;
    }

    public StringProperty logsAction_manageProperty() {
        return logsAction_manage;
    }

    public StringProperty manage_whereProperty() {
        return manage_where;
    }

    public StringProperty manage_recordProperty() {
        return manage_record;
    }

    public ObjectProperty<Timestamp> manage_logDateProperty() {
        return manage_logDate;
    }
}
