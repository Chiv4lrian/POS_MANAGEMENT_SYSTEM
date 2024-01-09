package penguin;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
    public Connection dblink;

    public Connection getConnection(){
        String databaseUser = "root";
        String databasePassword = "shitload";
        String url = "jdbc:mysql://localhost:3306/pos_records";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            dblink = DriverManager.getConnection(url,databaseUser,databasePassword);
        }catch(Exception e){
            e.printStackTrace();
        }
        return dblink;
    }
}
