package penguin;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class memo_show {

    private final StringProperty memoID;
    private final StringProperty memoLogs;
    private final ObjectProperty<Timestamp> manage_memoDate;

    public memo_show(String memoID, String memoLogs, Timestamp manage_memoDate) {
        this.memoID = new SimpleStringProperty(memoID);
        this.memoLogs = new SimpleStringProperty(memoLogs);
        this.manage_memoDate = new SimpleObjectProperty<>(manage_memoDate);
    }

    public static ObservableList<memo_show> getMemoLogs() {
        ObservableList<memo_show> memoList = FXCollections.observableArrayList();

        DBConnect connect = new DBConnect();
        try (Connection connection = connect.getConnection()) {
            if (connection != null) {
                String query = "SELECT id, logs, date FROM memo_logs";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            String memoID = resultSet.getString("id");
                            String memoLogs = resultSet.getString("logs");
                            Timestamp manage_memoDate = resultSet.getTimestamp("date");
                            memoList.add(new memo_show(memoID, memoLogs, manage_memoDate));
                        }
                    }
                }

            } else {
                System.err.println("Failed to establish a database connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memoList;
    }

    public StringProperty personNameProperty() {
        return memoID;
    }

    public StringProperty totalDebtProperty() {
        return memoLogs;
    }

    public ObjectProperty<Timestamp> debtDateProperty() {
        return manage_memoDate;
    }

}
