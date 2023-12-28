package penguin;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class deby {

    private final StringProperty personName;
    private final DoubleProperty totalDebt;
    private final ObjectProperty<LocalDate> debtDate;

    public deby(String personName, double totalDebt, LocalDate debtDate) {
        this.personName = new SimpleStringProperty(personName);
        this.totalDebt = new SimpleDoubleProperty(totalDebt);
        this.debtDate = new SimpleObjectProperty<>(debtDate);
    }

    public static ObservableList<deby> getDebts(LocalDate date1, LocalDate date2) {
        ObservableList<deby> debtList = FXCollections.observableArrayList();

        DBConnect connect = new DBConnect();
        try (Connection connection = connect.getConnection()) {
            if (connection != null) {
                String query = "SELECT person_name, product_total_debt, debt_date FROM debt WHERE debt_date BETWEEN ? AND ?;";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setDate(1, java.sql.Date.valueOf(date1));
                    statement.setDate(2, java.sql.Date.valueOf(date2));

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            String personName = resultSet.getString("person_name");
                            double totalDebt = resultSet.getDouble("product_total_debt");
                            LocalDate debtDate = resultSet.getDate("debt_date").toLocalDate();
                            debtList.add(new deby(personName, totalDebt, debtDate));
                        }
                    }
                }

            } else {
                System.err.println("Failed to establish a database connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return debtList;
    }

    public static ObservableList<deby> getDebts() {
        return getDebts(MainController.getSelectedDate1(), MainController.getSelectedDate2());
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
