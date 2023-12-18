package penguin;

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
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import static penguin.MainApp.stage1;

public class MainController implements Initializable {
    private boolean toggle = false;
    //ChoiceBox Arrays
    ObservableList<String> checkbox1_list = FXCollections.observableArrayList("Beverages","Condiments");
    ObservableList<String> checkbox2_list = FXCollections.observableArrayList("₱1 - ₱5", "₱6 - ₱10", "₱11 - ₱20","₱21 - ₱50","₱51 - ₱100", "₱101 - ₱200");
    ObservableList<String> checkbox3_list = FXCollections.observableArrayList("Lowest","Highest");
    //ChoiceBox Arrays End

    //new added
    @FXML
    private Pane sales_add;
    @FXML
    private Button add_sales;
    @FXML
    private Button sale_cancel;
    @FXML
    private Button level_butt;
    @FXML
    private Pane invent_levels;
    @FXML
    private Button appraisal_butt;
    @FXML
    private Pane invent_app;
    @FXML
    private Button history_butt;
    @FXML
    private Pane sales_hist;
    //new added end

    //menu_butts
    @FXML
    private Button inventory_butt;
    @FXML
    private Button pos_butt;
    @FXML
    private Button report_butt;
    @FXML
    private Button user_butt;
    //menu_butts_end

    //buttons_back_main
    @FXML
    private Button back_to_wan;
    @FXML
    private Button back_to_wan2;
    @FXML
    private Button back_to_wan3;
    @FXML
    private Button back_to_wan4;
    //buttons_back_main

    @FXML
    private Pane main_pane;
    @FXML
    private AnchorPane inventory_pane;
    @FXML
    private AnchorPane pos_pane;
    @FXML
    private AnchorPane reports_pane;
    @FXML
    private AnchorPane user_pane;
    @FXML
    private Button log_out;
    
    //name
    @FXML
    private Label account_name;
    @FXML
    private Label account_number;
    @FXML
    private Label txt_report_user;
    @FXML
    private Label txt_report_number;
    //name end
    
    //choice box
    @FXML
    private ChoiceBox<String> checkbox1;
    @FXML
    private ChoiceBox<String> checkbox2;
    @FXML
    private ChoiceBox<String> checkbox3;
    @FXML
    private ChoiceBox<String> checkbox4;
    @FXML
    private ChoiceBox<String> checkbox5;
    @FXML
    private ChoiceBox<String> checkbox6;
    //checkbox_end

    //inventory buttons/panes
    @FXML
    private Button add_product;
    @FXML
    private Button back_to_invent;
    @FXML
    private Button back_to_invent2;
    //check later
    //@FXML
    //private Button submit_butt;
    /*
    @FXML
    private Button submit_butt2;
    */
    //check later end
    @FXML
    private Button edit_butt;
    @FXML
    private Pane add_pane;
    @FXML
    private Pane edit_pane;
    //edit

    //edit end

    //TABLES_ALL_FUNCTIONS
    @FXML
    private TableView<products> products_table;
    @FXML
    private TableColumn<products, Integer> productID_col;
    @FXML
    private TableColumn<products, String> productname_col;
    @FXML
    private TableColumn<products, String> category_col;
    @FXML
    private TableColumn<products, Double> price_col;
    @FXML
    private TableColumn<products, Integer> stock_col;
    @FXML
    private TableColumn<products, LocalDate> date_col;
    @FXML
    private TableColumn<products, LocalDate> expire_col;
    @FXML
    private Label txt_productid;
    @FXML
    private TextField txt_productname;
    @FXML
    private TextField txt_category;
    @FXML
    private TextField txt_price;
    @FXML
    private TextField txt_stock;
    @FXML
    private Label txt_date;
    @FXML
    private TextField txt_expire;
    @FXML
    private Button show_products;
    @FXML
    private ObservableList<products> pro_list;
    @FXML
    private int index = -1;
    @FXML
    private DBConnect connects = new DBConnect();
    @FXML
    private Connection con_pro = connects.getConnection();
    //TABLES_ALL_FUNCTIONS_END

    //ButtonsOnActions
     public void main_buttAction(){
         inventory_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {inv_vis_butt();});
         pos_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {pos_vis_butt();});
         report_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {rep_vis_butt();});
         user_butt.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {user_vis_butt();});
         //logout
         log_out.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {loggy();});
    }

    public void main_buttAction2() {
        back_to_wan.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {inv_vis_wan();});
        back_to_wan2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {pos_vis_wan();});
        back_to_wan3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {rep_vis_wan();});
        back_to_wan4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {user_vis_wan();});
    }

    //invent_buttons
    public void invent_buttons(){
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
    }
    //ButtonsOnActions_End

    public void addProducts() { add_products_meth(); }

    private String formatDate(java.sql.Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // productID_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        productname_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        category_col.setCellValueFactory(new PropertyValueFactory<>("category"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        stock_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("dateAssessed"));
        expire_col.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

        pro_list = products.getProducts();

        products_table.setItems(pro_list);

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
    }
    
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
    public void inv_vis_butt(){
        main_pane.setVisible(false);
        inventory_pane.setVisible(true);
    }
    public void inv_vis_wan(){
        main_pane.setVisible(true);
        inventory_pane.setVisible(false);
    }
    //inventory_end

    //pos_butt_action
    public void pos_vis_butt(){
        main_pane.setVisible(false);
        pos_pane.setVisible(true);
    }
    public void pos_vis_wan(){
        main_pane.setVisible(true);
        pos_pane.setVisible(false);
    }
    //pos_butt_action_end

    //report_butt_action
    public void rep_vis_butt(){
        main_pane.setVisible(false);
        reports_pane.setVisible(true);
    }
    public void rep_vis_wan(){
        main_pane.setVisible(true);
        reports_pane.setVisible(false);
    }
    //report_butt_action_end

    //user_butt_action
    public void user_vis_butt(){
        main_pane.setVisible(false);
        user_pane.setVisible(true);
    }
    public void user_vis_wan(){
        main_pane.setVisible(true);
        user_pane.setVisible(false);
    }
    //user_butt_action_end

    //system_func_start
    public void add_product_evt(){
        add_pane.setVisible(true);
        edit_pane.setVisible(false);
        String query_sql = "SELECT MAX(product_id) + 1 AS next_proid FROM products";
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

    public void rep_levels(){
        sales_hist.setVisible(false);
        invent_app.setVisible(false);
        invent_levels.setVisible(true);
    }
    public void rep_app(){
        invent_levels.setVisible(false);
        sales_hist.setVisible(false);
        invent_app.setVisible(true);
    }
    public void log_hist(){
        invent_app.setVisible(false);
        sales_hist.setVisible(true);
    }

    public void toggle_tab(){
        toggle = !toggle;
        products_table.setDisable(toggle);
    }

    public void add_products_meth(){
        String sql = "INSERT INTO products(product_name, product_category, product_price, product_quantity, date_assessed, expiry_date) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
            java.sql.Date sqlexDate = java.sql.Date.valueOf(txt_expire.getText());
            PreparedStatement ps = con_pro.prepareStatement(sql);
            ps.setString(1, txt_productname.getText());
            ps.setString(2, txt_category.getText());
            ps.setDouble(3, Double.parseDouble(txt_price.getText()));
            ps.setInt(4, Integer.parseInt(txt_stock.getText()));
            ps.setDate(5, sqlDate);
            ps.setDate(6, sqlexDate);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product added successfully.");
                add_pane.setVisible(false);
            } else {
                System.out.println("Failed to add product.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
        if ((add_pane.isVisible() || edit_pane.isVisible() || sales_add.isVisible() || invent_levels.isVisible() || invent_app.isVisible() || sales_hist.isVisible()) && e.getCode() == KeyCode.ESCAPE) {
            add_pane.setVisible(false);
            edit_pane.setVisible(false);
            sales_add.setVisible(false);
            invent_levels.setVisible(false);
            invent_app.setVisible(false);
            sales_hist.setVisible(false);
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
        } else if (add_pane.isVisible() && e.getCode() == KeyCode.F2) {
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
            invent_levels.setVisible(false);
            invent_app.setVisible(true);
            sales_hist.setVisible(false);
        } else if (reports_pane.isVisible() && e.getCode() == KeyCode.F2) {
            invent_levels.setVisible(true);
            invent_app.setVisible(false);
            sales_hist.setVisible(false);
        } else if (reports_pane.isVisible() && e.getCode() == KeyCode.F3) {
            invent_levels.setVisible(false);
            invent_app.setVisible(false);
            sales_hist.setVisible(true);
        }
    }



    //key_pressed_methods_end

    //system_methods_all_end
}



