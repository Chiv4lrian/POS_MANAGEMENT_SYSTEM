package penguin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import static penguin.MainApp.stage1;

public class MainController implements Initializable {
    private boolean toggle = false;
    //ChoiceBox Arrays
    ObservableList<String> checkbox1_list = FXCollections.observableArrayList("Beverages", "Condiments");
    ObservableList<String> checkbox2_list = FXCollections.observableArrayList("₱1 - ₱5", "₱6 - ₱10", "₱11 - ₱20", "₱21 - ₱50", "₱51 - ₱100", "₱101 - ₱200");
    ObservableList<String> checkbox3_list = FXCollections.observableArrayList("Lowest", "Highest");
    ObservableList<String> check_age = FXCollections.observableArrayList("Newest", "Oldest");

    //ChoiceBox Arrays End

    //new added
    @FXML
    private Pane sales_add, invent_levels, invent_app, sales_hist, main_pane, add_users, add_pane, edit_pane;

    @FXML
    private Button misc_showbutt,debt_showbutt,cancel_add_user, add_sales, sale_cancel, level_butt, appraisal_butt, history_butt, inventory_butt, pos_butt, report_butt, user_butt, back_to_wan, back_to_wan2, back_to_wan3, back_to_wan4, log_out, add_account_butt, add_product, back_to_invent, back_to_invent2, edit_butt;

    @FXML
    private AnchorPane inventory_pane, pos_pane, reports_pane, user_pane;

    @FXML
    private Label account_name, account_number, txt_report_user, txt_report_number, show_time, show_date;

    @FXML
    private ChoiceBox<String> checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, level_cat, level_price, level_sort, val_choice1, val_choice2, val_choice3, sales_cat, sales_price;

    @FXML
    private TableView<products> products_table;

    @FXML
    private TableColumn<products, String> productID_col, productname_col, category_col;

    @FXML
    private TableColumn<products, Double> origprice_col, price_col;

    @FXML
    private TableColumn<products, Integer> stock_col, stockleft_col;

    @FXML
    private TableColumn<products, LocalDate> date_col, expire_col;


    @FXML
    private Label txt_productid, txt_date;

    @FXML
    private TextField txt_productname, txt_category, txt_origprice, txt_price, txt_stock, txt_expire;

    @FXML
    private Button show_products;

    @FXML
    private ObservableList<products> pro_list;

    @FXML
    private final DBConnect connects = new DBConnect();

    @FXML
    private final Connection con_pro = connects.getConnection();

    @FXML
    private TableView debt_table;

    @FXML
    private TableView misc_table;

    //TABLES_ALL_FUNCTIONS_END

    //ButtonsOnActions
    public void main_buttAction() {
        inventory_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> inv_vis_butt());
        pos_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> pos_vis_butt());
        report_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> rep_vis_butt());
        user_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> user_vis_butt());
        //logout
        log_out.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> loggy());
    }

    public void main_buttAction2() {
        back_to_wan.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> inv_vis_wan());
        back_to_wan2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> pos_vis_wan());
        back_to_wan3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> rep_vis_wan());
        back_to_wan4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> user_vis_wan());
    }

    //invent_buttons
    public void invent_buttons() {
        add_product.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> add_product_evt());
        back_to_invent.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> add_pane.setVisible(false));
        back_to_invent2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> edit_pane.setVisible(false));
        show_products.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> toggle_tab());
        edit_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> edit_pane.setVisible(true));
        add_sales.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> sales_add.setVisible(true));
        sale_cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> sales_add.setVisible(false));
        level_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> rep_levels());
        appraisal_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> rep_app());
        history_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> log_hist());
        add_account_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> add_users.setVisible(true));
        cancel_add_user.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> add_users.setVisible(false));
    }
    //ButtonsOnActions_End

    public void addProducts() {
        add_products_meth();
    }

    private String formatDate(java.sql.Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productmeth();
        updaterDT();
        checkbox1.setItems(checkbox1_list);
        checkbox1.setValue("CATEGORIES");
        checkbox2.setItems(checkbox2_list);
        checkbox2.setValue("PRICE");
        checkbox3.setItems(checkbox3_list);
        checkbox3.setValue("SORT");
        checkbox4.setItems(checkbox1_list);
        checkbox4.setValue("CATEGORIES");
        checkbox5.setItems(checkbox2_list);
        checkbox5.setValue("PRICE");
        checkbox6.setItems(checkbox3_list);
        checkbox6.setValue("SORT");
        level_cat.setItems(checkbox1_list);
        level_cat.setValue("CATEGORIES");
        level_price.setItems(checkbox2_list);
        level_price.setValue("PRICE");
        level_sort.setItems(check_age);
        level_sort.setValue("");
        val_choice1.setItems(checkbox1_list);
        val_choice1.setValue("CATEGORIES");
        val_choice2.setItems(checkbox2_list);
        val_choice2.setValue("PRICE");
        val_choice3.setItems(check_age);
        val_choice3.setValue("");
        sales_cat.setItems(checkbox1_list);
        sales_cat.setValue("CATEGORIES");
        sales_price.setItems(checkbox2_list);
        sales_price.setValue("PRICE");
    }

    //time_date

    public void updaterDT() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> DTLabels())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void DTLabels() {
        // Ensure UI updates are on the JavaFX application thread
        javafx.application.Platform.runLater(() -> {
            LocalTime currentTime = LocalTime.now();
            String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            show_time.setText(formattedTime);

            show_date.setText(LocalDate.now().toString());
        });
    }

    //time_date_end

    //system_methods_all
    private String passRes;

    public void setPassRes(String passRes) {
        this.passRes = passRes;
    }

    public void displayInfo() {
        DBConnect connectNow = new DBConnect();
        Connection connectDB = connectNow.getConnection();
        try {
            String username = passRes;
            System.out.println("Username from login: " + username);  // Debug statement

            String query = "SELECT Name, U_code FROM account WHERE Username LIKE ?";
            try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("Name");
                        String uCode = resultSet.getString("U_code");

                        System.out.println("Name from database: " + name);  // Debug statement
                        System.out.println("U_code from database: " + uCode);  // Debug statement

                        account_name.setText(name);
                        account_number.setText(uCode);
                        txt_report_user.setText(name);
                        txt_report_number.setText(uCode);
                    } else {
                        System.out.println("No data found for the username: " + username);  // Debug statement
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //for key press and button action

    //inventory_butt_action
    public void inv_vis_butt() {
        main_pane.setVisible(false);
        inventory_pane.setVisible(true);
    }

    public void inv_vis_wan() {
        main_pane.setVisible(true);
        inventory_pane.setVisible(false);
    }
    //inventory_end

    //pos_butt_action
    public void pos_vis_butt() {
        main_pane.setVisible(false);
        pos_pane.setVisible(true);
    }

    public void pos_vis_wan() {
        main_pane.setVisible(true);
        pos_pane.setVisible(false);
    }
    //pos_butt_action_end

    //report_butt_action
    public void rep_vis_butt() {
        main_pane.setVisible(false);
        reports_pane.setVisible(true);
    }

    public void rep_vis_wan() {
        main_pane.setVisible(true);
        reports_pane.setVisible(false);
    }
    //report_butt_action_end

    //user_butt_action
    public void user_vis_butt() {
        main_pane.setVisible(false);
        user_pane.setVisible(true);
    }

    public void user_vis_wan() {
        main_pane.setVisible(true);
        user_pane.setVisible(false);
    }
    //user_butt_action_end

    //system_func_start
    public void add_product_evt() {
        add_pane.setVisible(true);
        edit_pane.setVisible(false);
        String query_sql = "SELECT COALESCE(MAX(product_id) + 1, 1) AS next_proid FROM product";
        try {
            PreparedStatement autops = con_pro.prepareStatement(query_sql);
            ResultSet next_id = autops.executeQuery();
            if (next_id.next()) {
                txt_productid.setText(String.valueOf(next_id.getInt("next_proid")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
        txt_date.setText(formatDate(sqlDate));
    }

    public void rep_levels() {
        sales_hist.setVisible(false);
        invent_app.setVisible(false);
        invent_levels.setVisible(true);
    }

    public void rep_app() {
        invent_levels.setVisible(false);
        sales_hist.setVisible(false);
        invent_app.setVisible(true);
    }

    public void log_hist() {
        invent_levels.setVisible(false);
        invent_app.setVisible(false);
        sales_hist.setVisible(true);
    }

    public void toggle_tab() {
        toggle = !toggle;
        products_table.setDisable(toggle);
    }

    public void add_products_meth() {
        String sql = "INSERT INTO product (product_name, category, original_price, sell_price, stock, date_added, expire_date, stock_left) VALUES(?, ?, ?, ?, ?, ?,?,?)";
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());

        // Check if txt_expire is in the correct format (yyyy-MM-dd)
        if (isValidDateFormat(txt_expire.getText())) {
            java.sql.Date sqlexDate = java.sql.Date.valueOf(txt_expire.getText());

            try {
                PreparedStatement ps = con_pro.prepareStatement(sql);
                ps.setString(1, txt_productname.getText());
                ps.setString(2, txt_category.getText());
                ps.setDouble(3, Double.parseDouble(txt_origprice.getText()));
                ps.setDouble(4, Double.parseDouble(txt_price.getText()));
                ps.setInt(5, Integer.parseInt(txt_stock.getText()));
                ps.setDate(6, sqlDate);
                ps.setDate(7, sqlexDate);
                ps.setInt(8, Integer.parseInt(txt_stock.getText()));

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Product added successfully.");
                    UpdateTable();
                    add_pane.setVisible(false);
                } else {
                    System.out.println("Failed to add product.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showAlert();
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Date Format");
        alert.setHeaderText(null);
        alert.setContentText("Please enter the date in the yyyy-MM-dd format.");
        alert.showAndWait();
    }

    private boolean isValidDateFormat(String date) {
        try {
            java.sql.Date.valueOf(date);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    //UPDATE KIDS
    public void UpdateTable(){
        productmeth();
    }

    private void productmeth() {
        productID_col.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productname_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        category_col.setCellValueFactory(new PropertyValueFactory<>("category"));
        origprice_col.setCellValueFactory(new PropertyValueFactory<>("originalPrice"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        stock_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        stockleft_col.setCellValueFactory(new PropertyValueFactory<>("stockLeft"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("dateAssessed"));
        expire_col.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

        pro_list = products.getProducts();

        products_table.setItems(pro_list);
    }

    //UPDATE KIDS END

    //system_func_start_end

    //logout
    public void loggy(){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("penglog.fxml")));
            Scene scene = new Scene(root);
            stage1.setResizable(false);
            stage1.setMaximized(false);
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    //logout_end

    //main_misc_debt_table_cycle

    public void switchGo(){
        misc_showbutt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            misc_showbutt.setVisible(false);
            debt_table.setVisible(false);
            debt_showbutt.setVisible(true);
            misc_table.setVisible(true);
        });
        debt_showbutt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            misc_showbutt.setVisible(true);
            debt_table.setVisible(true);
            debt_showbutt.setVisible(false);
            misc_table.setVisible(false);
        });

    }

    //main_misc_debt_table_cycle_end

    @FXML
    public void keyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.L) {
            loggy();
        } else if (!inventory_pane.isVisible() && e.getCode() == KeyCode.F1) {
            inv_vis_butt();
        } else if (!pos_pane.isVisible() && e.getCode() == KeyCode.F2) {
            pos_vis_butt();
        } else if (!reports_pane.isVisible() && e.getCode() == KeyCode.F3) {
            rep_vis_butt();
        } else if (!user_pane.isVisible() && e.getCode() == KeyCode.F4) {
            user_vis_butt();
        }
    }


    @FXML
    public void keyPressed_2(KeyEvent e) {
        if ((add_pane.isVisible() || edit_pane.isVisible() || sales_add.isVisible() || invent_levels.isVisible() || invent_app.isVisible() || sales_hist.isVisible()) || add_users.isVisible() && e.getCode() == KeyCode.ESCAPE) {
            add_pane.setVisible(false);
            edit_pane.setVisible(false);
            sales_add.setVisible(false);
            invent_levels.setVisible(false);
            invent_app.setVisible(false);
            sales_hist.setVisible(false);
            add_users.setVisible(false);
        } else if  ((!add_pane.isVisible() || !edit_pane.isVisible() || !sales_add.isVisible() || !invent_levels.isVisible() || !invent_app.isVisible() || !sales_hist.isVisible()) && e.getCode() == KeyCode.ESCAPE) {
            inv_vis_wan();
            pos_vis_wan();
            rep_vis_wan();
            user_vis_wan();
        }
        else if (e.getCode() == KeyCode.CONTROL) {
            toggle_tab();
        } else if (inventory_pane.isVisible() && e.getCode() == KeyCode.F1) {
            add_product_evt();
        }else if (inventory_pane.isVisible() && e.getCode() == KeyCode.F2) {
            edit_pane.setVisible(true);
            add_pane.setVisible(false);
        } else if ((add_pane.isVisible() || edit_pane.isVisible()) && e.getCode() == KeyCode.ESCAPE) {
            add_pane.setVisible(false);
            edit_pane.setVisible(false);
        } else if (pos_pane.isVisible() && e.getCode() == KeyCode.F1){
            sales_add.setVisible(true);
        } else if (sales_add.isVisible() && e.getCode() == KeyCode.ESCAPE){
            sales_add.setVisible(false);
        } else if (reports_pane.isVisible() && e.getCode() == KeyCode.F1) {
            rep_levels();
        } else if (reports_pane.isVisible() && e.getCode() == KeyCode.F2) {
            rep_app();
        } else if (reports_pane.isVisible() && e.getCode() == KeyCode.F3) {
            log_hist();
        } else if (user_pane.isVisible() && e.getCode() == KeyCode.F1){
            add_users.setVisible(!add_users.isVisible());
        } else if (add_users.isVisible() && e.getCode() == KeyCode.ESCAPE){
            add_users.setVisible(false);
        }
    }

    //key_pressed_methods_end

    //system_methods_all_end
}



