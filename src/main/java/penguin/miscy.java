package penguin;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class miscy {
    private final StringProperty personName;
    private final DoubleProperty totalBorrowed;
    private final ObjectProperty<LocalDate> dateAssessed;

    public miscy(String personName, double totalBorrowed, LocalDate dateAssessed) {
        this.personName = new SimpleStringProperty(personName);
        this.totalBorrowed = new SimpleDoubleProperty(totalBorrowed);
        this.dateAssessed = new SimpleObjectProperty<>(dateAssessed);
    }

    public static ObservableList<miscy> getMisc(LocalDate date1, LocalDate date2) {
        ObservableList<miscy> miscyList = FXCollections.observableArrayList();

        try (Connection connection = new DBConnect().getConnection()) {
            if (connection != null) {
                String query = "SELECT person_borrowed, person_total_borrowed, date_assessed FROM misc WHERE date_assessed BETWEEN ? AND ?";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setDate(1, java.sql.Date.valueOf(date1));
                    statement.setDate(2, java.sql.Date.valueOf(date2));

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            String personName = resultSet.getString("person_borrowed");
                            double totalBorrowed = resultSet.getDouble("person_total_borrowed");
                            LocalDate dateAssessed = resultSet.getDate("date_assessed").toLocalDate();
                            miscyList.add(new miscy(personName, totalBorrowed, dateAssessed));
                        }
                    }
                }
            } else {
                System.err.println("Failed to establish a database connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return miscyList;
    }

    // Default implementation of getMisc using dates from MainController
    public static ObservableList<miscy> getMisc() {
        return getMisc(MainController.getSelectedDate1(), MainController.getSelectedDate2());
    }

    public String getPersonName() {
        return personName.get();
    }

    public StringProperty personNameProperty() {
        return personName;
    }

    public double getTotalBorrowed() {
        return totalBorrowed.get();
    }

    public DoubleProperty totalBorrowedProperty() {
        return totalBorrowed;
    }

    public LocalDate getDateAssessed() {
        return dateAssessed.get();
    }

    public ObjectProperty<LocalDate> dateAssessedProperty() {
        return dateAssessed;
    }
}
