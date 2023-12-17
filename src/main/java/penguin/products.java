package penguin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class products {
    private String name;
    private String category;
    private double price;
    private int quantity;
    private LocalDate dateAssessed;
    private LocalDate expiryDate;

    public products(String name, String category, double price, int quantity, LocalDate dateAssessed, LocalDate expiryDate) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.dateAssessed = dateAssessed;
        this.expiryDate = expiryDate;
    }

    public static ObservableList<products> getProducts() {
        ObservableList<products> productList = FXCollections.observableArrayList();

        DBConnect connect = new DBConnect();
        Connection connection = connect.getConnection();

        if (connection != null) {
            String query = "SELECT product_name, product_category, product_price, product_quantity, date_assessed, expiry_date FROM products";

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String name = resultSet.getString("product_name");
                    String category = resultSet.getString("product_category");
                    double price = resultSet.getDouble("product_price");
                    int quantity = resultSet.getInt("product_quantity"); // changed from stock to quantity
                    LocalDate dateAssessed = resultSet.getDate("date_assessed").toLocalDate();
                    LocalDate expiryDate = resultSet.getDate("expiry_date").toLocalDate();
                    productList.add(new products(name, category, price, quantity, dateAssessed, expiryDate));
                }
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.err.println("Failed to establish a database connection.");
        }

        return productList;
    }

    // Getters for the attributes
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getDateAssessed() {
        return dateAssessed;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}
