package sample;

import java.sql.*;

public class Database {
    public static ResultSet rs;
    public static Connection conn;
    public static void connect_with_database(){

        try {
            Class.forName("org.postgresql.Driver");
            //https://wiki-bsse.ethz.ch/display/ITDOC/Copy+PostgreSQL+database+between+computers
           conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Antiviruses","postgres","antisoft");

        }catch (SQLException e2) {
            e2.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
