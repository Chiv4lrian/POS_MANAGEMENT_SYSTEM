package penguin;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class products {
    private final StringProperty productId;
    private final StringProperty name;
    private final StringProperty category;
    private final DoubleProperty originalPrice;
    private final DoubleProperty price;
    private final IntegerProperty quantity;
    private final IntegerProperty stockLeft;
    private final ObjectProperty<LocalDate> dateAssessed;
    private final ObjectProperty<LocalDate> expiryDate;

    public products(String productId, String name, String category, double originalPrice, double price, int quantity, int stockLeft, LocalDate dateAssessed, LocalDate expiryDate) {
        this.productId = new SimpleStringProperty(productId);
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.originalPrice = new SimpleDoubleProperty(originalPrice);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.stockLeft = new SimpleIntegerProperty(stockLeft);
        this.dateAssessed = new SimpleObjectProperty<>(dateAssessed);
        this.expiryDate = new SimpleObjectProperty<>(expiryDate);
    }

    public static ObservableList<products> getProducts(LocalDate date1, LocalDate date2) {
        ObservableList<products> productList = FXCollections.observableArrayList();

        DBConnect connect = new DBConnect();
        Connection connection = connect.getConnection();

        if (connection != null) {
            String query = "SELECT product_id, product_name, category, original_price, sell_price, stock, stock_left,date_added, expire_date FROM product WHERE date_added BETWEEN ? AND ?";

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setDate(1, java.sql.Date.valueOf(date1));
                statement.setDate(2, java.sql.Date.valueOf(date2));

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String productId = resultSet.getString("product_id");
                    String name = resultSet.getString("product_name");
                    String category = resultSet.getString("category");
                    double originalPrice = resultSet.getDouble("original_price");
                    double price = resultSet.getDouble("sell_price");
                    int quantity = resultSet.getInt("stock");
                    int stockLeft = resultSet.getInt("stock_left");
                    LocalDate dateAssessed = resultSet.getDate("date_added").toLocalDate();
                    LocalDate expiryDate = resultSet.getDate("expire_date").toLocalDate();
                    productList.add(new products(productId, name, category, originalPrice, price, quantity, stockLeft, dateAssessed, expiryDate));
                }
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Failed to establish a database connection.");
        }

        return productList;
    }

    public static ObservableList<products> getProducts() {
        return getProducts(MainController.getSelectedDate3(), MainController.getSelectedDate4());
    }

    public StringProperty productIdProperty() {
        return productId;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public DoubleProperty originalPriceProperty() {
        return originalPrice;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public IntegerProperty stockLeftProperty() {
        return stockLeft;
    }

    public ObjectProperty<LocalDate> dateAssessedProperty() {
        return dateAssessed;
    }

    public ObjectProperty<LocalDate> expiryDateProperty() {
        return expiryDate;
    }
}
