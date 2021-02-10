package sk.kosickaakademia.stovcikova.chat2021;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private String url = "jdbc:mysql://itsovy.sk:3306/chat2021";
    private String nameDatabase = "mysqluser";
    private String passwordDatabase = "Kosice2021!";

    private Connection connection;
    private String login = "Heni";
    private String password = "1111111111";

    public static void main(String[] args) {
        Database database = new Database();

    }

    //connection method
    public boolean createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, nameDatabase, passwordDatabase);
            if (connection != null) {
                return true;
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 //creating new users
    public boolean insertNewUser(){
        if (createConnection()){
            String query = "INSERT INTO user (login, password) VALUES (?,?)";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1,login);
                ps.setString(2,MD5.getMd5(password));
                int result = ps.executeUpdate();
                System.out.println(result);
                connection.close();
                return true;

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }
    //changing password method
    public boolean changePassword(String oldPassword, String userName, String newPassword){
        if (createConnection()){
            String query = "UPDATE user SET password = ? WHERE login = ? AND password = ?";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1,MD5.getMd5(newPassword));
                ps.setString(2,userName);
                ps.setString(3,MD5.getMd5(oldPassword));
                int result = ps.executeUpdate();
                System.out.println(result);
                connection.close();
                return true;

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }
}

