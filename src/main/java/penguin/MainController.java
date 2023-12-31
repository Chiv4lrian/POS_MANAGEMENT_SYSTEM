package penguin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
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
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import static penguin.MainApp.stage1;

public class MainController implements Initializable {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private boolean toggle = false;
    //ChoiceBox Arrays
    ObservableList<String> checkbox1_list = FXCollections.observableArrayList("Beverages", "Condiments");
    ObservableList<String> checkbox2_list = FXCollections.observableArrayList("₱1 - ₱5", "₱6 - ₱10", "₱11 - ₱20", "₱21 - ₱50", "₱51 - ₱100", "₱101 - ₱200");
    ObservableList<String> checkbox3_list = FXCollections.observableArrayList("Lowest", "Highest");
    ObservableList<String> check_age = FXCollections.observableArrayList("Newest", "Oldest");

    //ChoiceBox Arrays End

    //new added
    @FXML
    private Pane pane_debt,pane_misc,sales_add, invent_levels, invent_app, sales_hist, main_pane, add_users, add_pane, edit_pane;

    @FXML
    private Button submit_butt2,view_disable, view_enable,submit_add_user,submit_debt,cancel_debt, submit_misc,cancel_misc,misc_butt,debt_butt,misc_showbutt,debt_showbutt,cancel_add_user, add_sales, sale_cancel, level_butt, appraisal_butt, history_butt, inventory_butt, pos_butt, report_butt, user_butt, back_to_wan, back_to_wan2, back_to_wan3, back_to_wan4, log_out, add_account_butt, add_product, back_to_invent, back_to_invent2, edit_butt;

    @FXML
    private AnchorPane inventory_pane, pos_pane, reports_pane, user_pane;

    @FXML
    private Label misc_fdate,debt_fdate,account_name, account_number, txt_report_user, txt_report_number, show_time, show_date;

    @FXML
    private ChoiceBox<String> checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, level_cat, level_price, level_sort, val_choice1, val_choice2, val_choice3, sales_cat, sales_price;
//inventory_tableview
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
    private TableView<disable_products> disable_table;

    @FXML
    private TableColumn<disable_products, String> disable_id,disable_name,disable_category;

    @FXML
    private TableColumn<disable_products, Double> disable_list,disable_price;

    @FXML
    private TableColumn<disable_products, Integer> disable_stock,disable_stockleft;

    @FXML
    private TableColumn<disable_products, LocalDate> disable_add,disable_expire;

    @FXML
    private TableView<account_show> manage_account;

    @FXML
    private TableColumn<account_show, String> account_names,account_history;

    @FXML
    private TableColumn<account_show, Timestamp> account_time;

    @FXML
    private TableColumn<logs_show, Timestamp> memo_date;

    @FXML
    private TableColumn<memo_show, Timestamp> manage_logDate;

    @FXML
    private TableView<memo_show> manage_memo;

    @FXML
    private TableColumn<memo_show, String> memo_id;

    @FXML
    private TableColumn<memo_show, String> memo_history;

    @FXML
    private TableView<logs_show> manage_logs;

    @FXML
    private TableColumn<logs_show, String> logID_manage;

    @FXML
    private TableColumn<logs_show, String> manage_record;

    @FXML
    private TableColumn<logs_show,String> logsAction_manage, manage_where;


//inventory_table_end

    //account

    public void updateAccs(){
        accountShowMethod();
    }

    public void updateMemos(){
        memoShowMethod();
    }
    public void updateLogs(){
        logsShowMethod();
    }

    private void accountShowMethod() {
        account_names.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        account_history.setCellValueFactory(new PropertyValueFactory<>("accountHistory"));
        account_time.setCellValueFactory(new PropertyValueFactory<>("accountTime"));
        account_list = account_show.getAccounts();
        manage_account.setItems(account_list);
    }

    private void memoShowMethod() {
        memo_id.setCellValueFactory(new PropertyValueFactory<>("personName"));
        memo_history.setCellValueFactory(new PropertyValueFactory<>("totalDebt"));
        memo_date.setCellValueFactory(new PropertyValueFactory<>("debtDate"));

        memo_list = memo_show.getMemoLogs();
        manage_memo.setItems(memo_list);
    }

    private void logsShowMethod(){
        logID_manage.setCellValueFactory(new PropertyValueFactory<>("logID_manage"));
        logsAction_manage.setCellValueFactory(new PropertyValueFactory<>("logsAction_manage"));
        manage_where.setCellValueFactory(new PropertyValueFactory<>("manage_where"));
        manage_record.setCellValueFactory(new PropertyValueFactory<>("manage_record"));
        manage_logDate.setCellValueFactory(new PropertyValueFactory<>("manage_logDate"));

        logs_list = logs_show.getLogs();
        manage_logs.setItems(logs_list);
    }


    //account_end

    @FXML
    private Label txt_productid, txt_date,edit_id,edit_date;

    @FXML
    private TextField code_field,name_field,user_field,pass_field,debt_fname,debt_ftotal,misc_fname,misc_ftotal,txt_productname, txt_category, txt_origprice, txt_price, txt_stock, txt_expire;

    @FXML
    private TextField edit_category,edit_listprice,edit_price,edit_stock,edit_expire;

    @FXML
    private ComboBox edit_name;

    @FXML
    private Button accountLogs_button,memoLogs_button,logsLogs_button,remove_balachie,show_products;

    //list
    @FXML
    private ObservableList<products> pro_list;
    @FXML
    private ObservableList<disable_products> disabled_list;

    @FXML
    private ObservableList<deby> debt_list;
    @FXML
    private ObservableList<miscy> misc_list;

    @FXML
    private ObservableList<account_show> account_list;
    @FXML
    private ObservableList<logs_show> logs_list;
    @FXML
    private ObservableList<memo_show> memo_list;

    //list_end

    //date_start
    @FXML
    private DatePicker from_main,to_main, from_invent, to_invent,from_sale,to_sale;

    private static final LocalDate currentDate = LocalDate.now();

    private static LocalDate selectedDate1 = currentDate;
    private static LocalDate selectedDate2 = currentDate;
    private static LocalDate selectedDate3 = currentDate;
    private static LocalDate selectedDate4 = currentDate;
    private static LocalDate selectedDate5 = currentDate;
    private static LocalDate selectedDate6 = currentDate;

    public static LocalDate getSelectedDate1() {
        return selectedDate1;
    }

    public void setSelectedDate1(LocalDate selectedDate1) {
        MainController.selectedDate1 = selectedDate1;
        debtmeth();
    }

    public static LocalDate getSelectedDate2() {
        return selectedDate2;
    }

    public void setSelectedDate2(LocalDate selectedDate2) {
        MainController.selectedDate2 = selectedDate2;
        debtmeth();
    }

    public static LocalDate getSelectedDate3() {
        return selectedDate3;
    }

    public void setSelectedDate3(LocalDate selectedDate3) {
        MainController.selectedDate3 = selectedDate3;
        UpdateTable();
    }

    public static LocalDate getSelectedDate4() {
        return selectedDate4;
    }

    public void setSelectedDate4(LocalDate selectedDate4) {
        MainController.selectedDate4 = selectedDate4;
        UpdateTable();
    }
//c
    public static LocalDate getSelectedDate5() {
    return selectedDate5;
}

    public void setSelectedDate5(LocalDate selectedDate5) {
        MainController.selectedDate5 = selectedDate5;
        UpdateTable();
    }

    public static LocalDate getSelectedDate6() {
        return selectedDate6;
    }

    public void setSelectedDate6(LocalDate selectedDate6) {
        MainController.selectedDate6 = selectedDate6;
        UpdateTable();
    }
    //date_end

    @FXML
    private final DBConnect connects = new DBConnect();

    @FXML
    private final Connection con_pro = connects.getConnection();

    @FXML
    private TableView<deby> debt_table;

    @FXML
    private TableColumn<deby, String> debt_name;

    @FXML
    private TableColumn<deby, Double> debt_total;

    @FXML
    private TableColumn<deby, LocalDate> debt_add;

    @FXML
    private TableView<miscy> misc_table;

    @FXML
    private TableColumn<miscy, String> misc_name;

    @FXML
    private TableColumn<miscy, Double> misc_total;

    @FXML
    private TableColumn<miscy, LocalDate> misc_add;

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

    public void paneMemo(){
        misc_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            pane_misc.setVisible(!pane_misc.isVisible());
            pane_debt.setVisible(false);
        });

        debt_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (pane_debt.isVisible()) {
                pane_misc.setVisible(false);
                pane_debt.setVisible(false);
            } else {
                pane_misc.setVisible(false);
                pane_debt.setVisible(true);
            }
        });
    }

    public void submit_okay(){
        submit_debt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> add_debt());
        submit_misc.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> add_misc());
        submit_add_user.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> add_account());
    }

    public void main_buttAction2() {
        back_to_wan.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> inv_vis_wan());
        back_to_wan2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> pos_vis_wan());
        back_to_wan3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> rep_vis_wan());
        back_to_wan4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> user_vis_wan());
        cancel_debt.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {pane_debt.setVisible(false); pane_misc.setVisible(false);});
        cancel_misc.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {pane_debt.setVisible(false); pane_misc.setVisible(false);});
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
        cancel_add_user.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {add_users.setVisible(false); res_acc();});
    }


    public void removeButtAction() {
            remove_balachie.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (products_table.isVisible()) {
                    disableProduct();
                    UpdateDisable();
                    UpdateTable();
                } else {
                    enableProduct();
                    UpdateTable();
                    UpdateDisable();
                }
                event.consume();
            });
        }

        //account_pane
    public void accountButton(){
        accountLogs_button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
           if(manage_account.isVisible()){
               manage_account.setVisible(false);
           }
           else {
               manage_account.setVisible(true);
               manage_memo.setVisible(false);
               manage_logs.setVisible(false);
           }
            updateAccs();
        });
        memoLogs_button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(manage_memo.isVisible()){
                manage_memo.setVisible(false);
            }
            else {
                manage_account.setVisible(false);
                manage_memo.setVisible(true);
                manage_logs.setVisible(false);
            }
            updateMemos();
        });
        logsLogs_button .addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(manage_logs.isVisible()){
                manage_logs.setVisible(false);
            }
            else {
                manage_account.setVisible(false);
                manage_memo.setVisible(false);
                manage_logs.setVisible(true);
            }
            updateMemos();
        });
    }


        //account_pane_end

    //ButtonsOnActions_End

    public void addProducts() {
        add_products_meth();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<account_show, Timestamp> account_time = new TableColumn<>("account_time");
        TableColumn<logs_show, Timestamp> manage_logDate = new TableColumn<>("manage_logDate");
        TableColumn<memo_show, Timestamp> memo_date = new TableColumn<>("memo_date");

        account_time.setCellValueFactory(cellData -> {
            SimpleObjectProperty<Timestamp> property = new SimpleObjectProperty<>();
            Timestamp timestamp = cellData.getValue().accountTimeProperty().get();
            if (timestamp != null) {
                property.setValue(timestamp);
            }
            return property;
        });

        manage_logDate.setCellValueFactory(cellData -> {
            SimpleObjectProperty<Timestamp> property = new SimpleObjectProperty<>();
            Timestamp timestamp = cellData.getValue().manage_logDateProperty().get();
            if (timestamp != null) {
                property.setValue(timestamp);
            }
            return property;
        });

        memo_date.setCellValueFactory(cellData -> {
            SimpleObjectProperty<Timestamp> property = new SimpleObjectProperty<>();
            Timestamp timestamp = cellData.getValue().debtDateProperty().get();
            if (timestamp != null) {
                property.setValue(timestamp);
            }
            return property;
        });
        populateComboBox();
        edit_name.setOnAction(event -> onComboBoxItemSelected());
        logsShowMethod();
        accountShowMethod();
        memoShowMethod();
        productmeth();
        disable_productmeth();
        updaterDT();
        dateGet();
        choice_box();
        debtmeth();
        miscmeth();
        from_main.setOnAction(event -> {
            selectedDate1 = from_main.getValue();
            debtmeth();
            miscmeth();
        });

        to_main.setOnAction(event -> {
            selectedDate2 = to_main.getValue();
            debtmeth();
            miscmeth();
        });

        from_invent.setOnAction(event -> {
            selectedDate3 = from_invent.getValue();
            productmeth();
        });

        to_invent.setOnAction(event -> {
            selectedDate4 = to_invent.getValue();
            productmeth();
        });

        from_sale.setOnAction(event -> {
            selectedDate5 = from_sale.getValue();
            productmeth();
        });

        to_sale.setOnAction(event -> {
            selectedDate6 = to_sale.getValue();
            productmeth();
        });
    }

    private void choice_box(){
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
    }

    void dateGet(){
        LocalDate currentDate = LocalDate.now();
        misc_fdate.setText(String.valueOf(currentDate));
        debt_fdate.setText(String.valueOf(currentDate));
        txt_date.setText(String.valueOf(currentDate));
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
    public void buttonEditAction(){
        submit_butt2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            edit_products_meth();
            edit_pane.setVisible(false);
        });
    }

    public void edit_products_meth() {
        String selectedProductName = (String) edit_name.getValue();

        if (selectedProductName != null) {
            java.sql.Date sqlexDate = java.sql.Date.valueOf(edit_expire.getText());
            String sql = "UPDATE product SET category = ?, original_price = ?, sell_price = ?, stock = ?, expire_date = ? WHERE product_name = ?";
            try (PreparedStatement ps = con_pro.prepareStatement(sql)) {
                ps.setString(1, edit_category.getText());
                ps.setDouble(2, Double.parseDouble(edit_listprice.getText()));
                ps.setDouble(3, Double.parseDouble(edit_price.getText()));
                ps.setInt(4, Integer.parseInt(edit_stock.getText()));
                ps.setDate(5, sqlexDate);
                ps.setString(6, selectedProductName);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    UpdateTable();
                    System.out.println("Product updated successfully.");
                } else {
                    System.out.println("No product found with the specified name.");
                }
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
    private void populateComboBox() {
        try {
            Statement statement = con_pro.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT product_name FROM product");
            ObservableList<String> productNames = FXCollections.observableArrayList();
            while (resultSet.next()) {
                productNames.add(resultSet.getString("product_name"));
            }
            edit_name.setItems(productNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void onComboBoxItemSelected() {
        String selectedProductName = (String) edit_name.getValue();
        try {
            PreparedStatement preparedStatement = con_pro.prepareStatement("SELECT * FROM product WHERE product_name = ?");
            preparedStatement.setString(1, selectedProductName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                edit_id.setText(String.valueOf(resultSet.getInt("product_id")));
                edit_date.setText(String.valueOf(resultSet.getDate("date_added").toLocalDate()));
                edit_category.setText(resultSet.getString("category"));
                edit_listprice.setText(String.valueOf(resultSet.getDouble("original_price")));
                edit_price.setText(String.valueOf(resultSet.getDouble("sell_price")));
                edit_stock.setText(String.valueOf(resultSet.getInt("stock")));
                Date expireDate = resultSet.getDate("expire_date");
                edit_expire.setText(expireDate != null ? String.valueOf(expireDate.toLocalDate()) : null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
}
    //sales_start



    //sales_end




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
    public void UpdateDisable(){
        disable_productmeth();
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

    private void disable_productmeth() {
        disable_id.setCellValueFactory(new PropertyValueFactory<>("productId"));
        disable_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        disable_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        disable_list.setCellValueFactory(new PropertyValueFactory<>("originalPrice"));
        disable_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        disable_stock.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        disable_stockleft.setCellValueFactory(new PropertyValueFactory<>("stockLeft"));
        disable_add.setCellValueFactory(new PropertyValueFactory<>("dateAssessed"));
        disable_expire.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

        disabled_list = disable_products.getDisabledProducts();

        disable_table.setItems(disabled_list);
    }

    //UPDATE KIDS END

    //ENABLE - DISABLE KIDS START
    private void enableProduct() {
        disable_products selectedItem = disable_table.getSelectionModel().getSelectedItem();

        if (selectedItem == null) return;

        String updateSql = "UPDATE product SET is_disabled = 0 WHERE product_name = ? AND category = ? AND sell_price = ? AND is_disabled = 1";

        try {
            PreparedStatement pst = con_pro.prepareStatement(updateSql);
            pst.setString(1, selectedItem.nameProperty().get());
            pst.setString(2, selectedItem.categoryProperty().get());
            pst.setDouble(3, selectedItem.priceProperty().get());
            pst.executeUpdate();

            showAlert("Product Enabled Successfully");
            UpdateTable();
            UpdateDisable();
        } catch (SQLException e) {
            showAlert("Failed to Enable the Product");
        }
    }

    private void disableProduct() {
        products selectedItem = products_table.getSelectionModel().getSelectedItem();

        if (selectedItem == null) return;

        String updateSql = "UPDATE product SET is_disabled = 1 WHERE product_name = ? AND category = ? AND sell_price = ? AND is_disabled = 0";

        try {
            PreparedStatement pst = con_pro.prepareStatement(updateSql);
            pst.setString(1, selectedItem.nameProperty().get());
            pst.setString(2, selectedItem.categoryProperty().get());
            pst.setDouble(3, selectedItem.priceProperty().get());
            pst.executeUpdate();

            showAlert("Product Disabled Successfully");
            UpdateTable();
            UpdateDisable();
        } catch (SQLException e) {
            showAlert("Failed to Disable the Product");
        }
    }

    //ENABLE - DISABLE KIDS END

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
        view_enable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            view_disable.setVisible(true);
            view_enable.setVisible(false);
            disable_table.setVisible(false);
            products_table.setVisible(true);
        });
        view_disable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            view_enable.setVisible(true);
            view_disable.setVisible(false);
            disable_table.setVisible(true);
            products_table.setVisible(false);
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

    //memo_start
    public void add_debt() {
        String sql = "INSERT INTO debt (person_name, product_total_debt, debt_date) VALUES (?, ?, ?)";
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());

        try {
            PreparedStatement ps = con_pro.prepareStatement(sql);
            ps.setString(1, debt_fname.getText());
            ps.setDouble(2, Double.parseDouble(debt_ftotal.getText()));
            ps.setDate(3, sqlDate);

            int rowsAffected = ps.executeUpdate();

            alert.setTitle("NOTICE");
            alert.setHeaderText("Pengui Management");

            logDeletion2(debt_fname.getText(), "Misc");

            if (rowsAffected > 0) {
                alert.setContentText("SUCCESSFULLY ADDED THE DEBT");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                updateDebt();
                debt_fname.clear();
                debt_ftotal.clear();
                pane_debt.setVisible(false);
            } else {
                alert.setContentText("FAILED TO ADD THE DEBT");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                debt_fname.clear();
                debt_ftotal.clear();
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


    public void updateDebt(){
        debtmeth();
    }

    private void debtmeth() {
        debt_name.setCellValueFactory(new PropertyValueFactory<>("personName"));
        debt_total.setCellValueFactory(new PropertyValueFactory<>("totalDebt"));
        debt_add.setCellValueFactory(new PropertyValueFactory<>("debtDate"));
        debt_list = deby.getDebts();
        debt_table.setItems(debt_list);
    }

    public void deleteDebt_Misc() {
        if (debt_table.isVisible()) {
            deleteDebt();
        } else {
            deleteMisc();
        }
    }

    private void deleteDebt() {
        deby selectedItem = debt_table.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("No Row Is Selected to Delete");
            return;
        }

        String deleteSql = "DELETE FROM debt WHERE person_name = ? AND product_total_debt = ?";
        logDeletion(selectedItem.personNameProperty().get(), "Debt");

        try {
            PreparedStatement pst = con_pro.prepareStatement(deleteSql);
            pst.setString(1, selectedItem.personNameProperty().get());
            pst.setDouble(2, selectedItem.totalDebtProperty().get());
            pst.executeUpdate();

            showAlert("Debt Deleted Successfully");
            updateDebt();
        } catch (SQLException e) {
            showAlert("Failed to Delete the Debt");
        }
    }

    private void deleteMisc() {
        miscy selectedItem = misc_table.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("No Row Is Selected to Delete");
            return;
        }

        String deleteSql = "DELETE FROM misc WHERE person_borrowed = ? AND person_total_borrowed = ?";
        logDeletion(selectedItem.personNameProperty().get(), "Misc");

        try {
            PreparedStatement pst = con_pro.prepareStatement(deleteSql);
            pst.setString(1, selectedItem.personNameProperty().get());
            pst.setDouble(2, selectedItem.totalBorrowedProperty().get());
            pst.executeUpdate();
            showAlert("Misc Deleted Successfully");
            updateMisc();
        } catch (SQLException e) {
            showAlert("Failed to Delete Misc");
        }
    }

    private void logDeletion(String personName, String category) {
        String memoSql = "INSERT INTO memo_logs (logs) VALUES (?)";

        try {
            PreparedStatement memoPs = con_pro.prepareStatement(memoSql);
            memoPs.setString(1, "Deleted " + category + " for: " + personName);
            memoPs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void logDeletion2(String personName, String category) {
        String memoSql = "INSERT INTO memo_logs (logs) VALUES (?)";

        try {
            PreparedStatement memoPs = con_pro.prepareStatement(memoSql);
            memoPs.setString(1, "Inserted " + category + " for: " + personName);
            memoPs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(String content) {
        alert.setTitle("NOTICE");
        alert.setHeaderText("Pengui Management");
        alert.setContentText(content);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }


    public void add_misc() {
        String sql = "INSERT INTO misc (person_borrowed, person_total_borrowed, date_assessed) VALUES (?, ?, ?)";
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());

        try {
            PreparedStatement ps = con_pro.prepareStatement(sql);
            ps.setString(1, misc_fname.getText());
            String totalText = misc_ftotal.getText();
            if (!totalText.isEmpty()) ps.setDouble(2, Double.parseDouble(totalText)); else return;
            ps.setDate(3, sqlDate);

            int rowsAffected = ps.executeUpdate();

            alert.setTitle("NOTICE");
            alert.setHeaderText("Pengui Management");

            logDeletion2(misc_fname.getText(), "Misc");

            if (rowsAffected > 0) {
                alert.setContentText("SUCCESSFULLY ADDED THE DEBT");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                updateMisc();
                misc_fname.clear();
                misc_ftotal.clear();
                pane_misc.setVisible(false);
            } else {
                alert.setContentText("FAILED TO ADD THE DEBT");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                misc_fname.clear();
                misc_ftotal.clear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateMisc(){
        miscmeth();
    }

    private void miscmeth() {
        misc_name.setCellValueFactory(new PropertyValueFactory<>("personName"));
        misc_total.setCellValueFactory(new PropertyValueFactory<>("totalBorrowed"));
        misc_add.setCellValueFactory(new PropertyValueFactory<>("dateAssessed"));
        misc_list = miscy.getMisc();
        misc_table.setItems(misc_list);
    }

    //memo_end

    //add_account_start

    public void add_account() {
        String sql = "INSERT INTO account (u_code,name,username,password) VALUES(?,?,?,?)";
        try {
            PreparedStatement ps = con_pro.prepareStatement(sql);
            ps.setString(1, code_field.getText());
            ps.setString(2, name_field.getText());
            ps.setString(3, user_field.getText());
            ps.setString(4, pass_field.getText());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                res_acc();
                add_users.setVisible(false);
            } else {
                System.out.println("Failed to add account.");
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    private void res_acc(){
        code_field.clear();
        name_field.clear();
        name_field.clear();
        user_field.clear();
    }

    //add_account_end

    //system_methods_all_end
}



