package penguin;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class account_show {

    private final StringProperty accountName;
    private final StringProperty accountHistory;
    private final ObjectProperty<Timestamp> accountTime;

    public account_show(String accountName, String accountHistory, Timestamp accountTime) {
        this.accountName = new SimpleStringProperty(accountName);
        this.accountHistory = new SimpleStringProperty(accountHistory);
        this.accountTime = new SimpleObjectProperty<>(accountTime);
    }

    public static ObservableList<account_show> getAccounts() {
        ObservableList<account_show> accountList = FXCollections.observableArrayList();

        DBConnect connect = new DBConnect();
        try (Connection connection = connect.getConnection()) {
            if (connection != null) {
                String query = "SELECT username, action, log_date FROM account_logs";

                try (PreparedStatement statement = connection.prepareStatement(query)) {

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            String accountName = resultSet.getString("username");
                            String accountHistory = resultSet.getString("action");
                            Timestamp accountTime = resultSet.getTimestamp("log_date");
                            accountList.add(new account_show(accountName, accountHistory, accountTime));
                        }
                    }
                }
            } else {
                System.err.println("Failed to establish a database connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public StringProperty accountNameProperty() {
        return accountName;
    }

    public StringProperty accountHistoryProperty() {
        return accountHistory;
    }

    public ObjectProperty<Timestamp> accountTimeProperty() {
        return accountTime;
    }
}
