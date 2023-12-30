package penguin;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class account_show {

    private final StringProperty accountName;
    private final StringProperty accountHistory;
    private final ObjectProperty<LocalDate> accountTime;

    public account_show(String accountName, String accountHistory, LocalDate accountTime) {
        this.accountName = new SimpleStringProperty(accountName);
        this.accountHistory = new SimpleStringProperty(accountHistory);
        this.accountTime = new SimpleObjectProperty<>(accountTime);
    }

    public static ObservableList<account_show> getAccounts(LocalDate date1, LocalDate date2) {
        ObservableList<account_show> accountList = FXCollections.observableArrayList();

        DBConnect connect = new DBConnect();
        try (Connection connection = connect.getConnection()) {
            if (connection != null) {
                String query = "SELECT username, action,log_date FROM account_logs WHERE log_date BETWEEN ? AND ?;";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setDate(1, java.sql.Date.valueOf(date1));
                    statement.setDate(2, java.sql.Date.valueOf(date2));

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            String accountName = resultSet.getString("account_name");
                            String accountHistory = resultSet.getString("account_history");
                            LocalDate accountTime = resultSet.getDate("account_time").toLocalDate();
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

    public static ObservableList<account_show> getAccounts() {
        return getAccounts(MainController.getSelectedDate1(), MainController.getSelectedDate2());
    }

    public StringProperty accountNameProperty() {
        return accountName;
    }

    public StringProperty accountHistoryProperty() {
        return accountHistory;
    }

    public ObjectProperty<LocalDate> accountTimeProperty() {
        return accountTime;
    }
}
