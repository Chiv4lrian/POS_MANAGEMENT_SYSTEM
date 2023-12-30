package penguin;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class memo_show {

    private final StringProperty personName;
    private final DoubleProperty totalDebt;
    private final ObjectProperty<LocalDate> debtDate;

    public memo_show(String personName, double totalDebt, LocalDate debtDate) {
        this.personName = new SimpleStringProperty(personName);
        this.totalDebt = new SimpleDoubleProperty(totalDebt);
        this.debtDate = new SimpleObjectProperty<>(debtDate);
    }

    public static ObservableList<memo_show> getMemoLogs(LocalDate date1, LocalDate date2) {
        ObservableList<memo_show> memoList = FXCollections.observableArrayList();

        DBConnect connect = new DBConnect();
        try (Connection connection = connect.getConnection()) {
            if (connection != null) {
                String query = "SELECT id, logs, date FROM memo_logs WHERE date BETWEEN ? AND ?;";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setDate(1, java.sql.Date.valueOf(date1));
                    statement.setDate(2, java.sql.Date.valueOf(date2));

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            String personName = resultSet.getString("person_name");
                            double totalDebt = resultSet.getDouble("product_total_debt");
                            LocalDate debtDate = resultSet.getDate("debt_date").toLocalDate();
                            memoList.add(new memo_show(personName, totalDebt, debtDate));
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

    public static ObservableList<memo_show> getMemoLogs() {
        return getMemoLogs(MainController.getSelectedDate1(), MainController.getSelectedDate2());
    }

    public StringProperty personNameProperty() {
        return personName;
    }

    public DoubleProperty totalDebtProperty() {
        return totalDebt;
    }

    public ObjectProperty<LocalDate> debtDateProperty() {
        return debtDate;
    }
}
