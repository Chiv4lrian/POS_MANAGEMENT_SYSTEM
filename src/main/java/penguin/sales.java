package penguin;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class sales {
    private final IntegerProperty productId;
    private final StringProperty productName;
    private final DoubleProperty sellPrice;
    private final IntegerProperty productQuantity;
    private final DoubleProperty total;
    private final ObjectProperty<LocalDate> dateAssessed;

    public sales(int productId, String productName, double sellPrice, int productQuantity, double total, LocalDate dateAssessed) {
        this.productId = new SimpleIntegerProperty(productId);
        this.productName = new SimpleStringProperty(productName);
        this.sellPrice = new SimpleDoubleProperty(sellPrice);
        this.productQuantity = new SimpleIntegerProperty(productQuantity);
        this.total = new SimpleDoubleProperty(total);
        this.dateAssessed = new SimpleObjectProperty<>(dateAssessed);
    }

    public static ObservableList<sales> getSales(LocalDate date1, LocalDate date2) {
        ObservableList<sales> salesList = FXCollections.observableArrayList();

        DBConnect connect = new DBConnect();
        Connection connection = connect.getConnection();

        if (connection != null) {
            String query = "SELECT product_id, product_name, sell_price, product_quantity, total, date_assessed FROM sale WHERE date_assessed BETWEEN ? AND ? AND is_remove = 0";

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setDate(1, java.sql.Date.valueOf(date1));
                statement.setDate(2, java.sql.Date.valueOf(date2));

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int productId = resultSet.getInt("product_id");
                    String productName = resultSet.getString("product_name");
                    double sellPrice = resultSet.getDouble("sell_price");
                    int productQuantity = resultSet.getInt("product_quantity");
                    double total = resultSet.getDouble("total");
                    LocalDate dateAssessed = resultSet.getDate("date_assessed").toLocalDate();
                    salesList.add(new sales(productId, productName, sellPrice, productQuantity, total, dateAssessed));
                }
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Failed to establish a database connection.");
        }

        return salesList;
    }

    public static ObservableList<sales> getSales() {
        return getSales(MainController.getSelectedDate5(), MainController.getSelectedDate6());
    }

    public IntegerProperty productIdProperty() {
        return productId;
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public DoubleProperty sellPriceProperty() {
        return sellPrice;
    }

    public IntegerProperty productQuantityProperty() {
        return productQuantity;
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public ObjectProperty<LocalDate> dateAssessedProperty() {
        return dateAssessed;
    }
}
