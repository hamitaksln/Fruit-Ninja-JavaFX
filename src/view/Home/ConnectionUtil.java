package view.Home;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static Connection connectionDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbfruitjanissary?useUnicode=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Turkey","root", "23230*");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}


