package sk.kosickaakademia.stovcikova.chat.database;

import sample.MD5;
import sk.kosickaakademia.stovcikova.chat.entity.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public boolean insertNewUser() {
        if (createConnection()) {
            String query = "INSERT INTO user (login, password) VALUES (?,?)";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, login);
                ps.setString(2, MD5.getMd5(password));
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
    public boolean changePassword(String oldPassword, String userName, String newPassword) {
        if (createConnection()) {
            String query = "UPDATE user SET password = ? WHERE login = ? AND password = ?";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, MD5.getMd5(newPassword));
                ps.setString(2, userName);
                ps.setString(3, MD5.getMd5(oldPassword));
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
    
    public boolean sendMessage(int from, String toUser, String text){
        if(text==null || text.equals(""))
            return false;
        int to = getUserId(toUser);
        if(to==-1)
            return false;

        if(createConnection()){
            String query = "INSERT INTO message (fromUser, toUser, text) VALUES (?, ?, ?)";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, from);
                ps.setInt(2, to);
                ps.setString(3, text);
                int result = ps.executeUpdate();
                System.out.println(result);
                connection.close();
                return true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            System.out.println("Something wrong");
        }
        return false;
    }



    private int getUserId(String login) {
        if (login.equals("")){
            return  -1;
        }
        String query = "SELECT user.id FROM user WHERE login LIKE ?";
        if (createConnection()){
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, login);
                ResultSet rs= ps.executeQuery();
                if (rs.next()){
                    int identifier = rs.getInt("id");
                    return identifier;
                }
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            System.out.println("wrong");
        }
        return -1;
    }
    public List<Message> getMyMessages(String login) {
        if (login.equals("")){
            return null;
        }
        List<Message> list= new ArrayList<>();
        if (createConnection()){
            String query = "SELECT message.id, message.dt, message.fromUser, message.toUser, message.text"+
                    "user.login AS sender FROM message INNER JOIN user ON user.id = message.fromUser"+
                    "WHERE toUser = ?";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, getUserId(login));
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    int identifier = rs.getInt("id");
                    Date date = rs.getDate("dt");
                    String fromUser = rs.getString("sender");
                    int toUser= rs.getInt("toUser");
                    String text= rs.getString("text");
                    Message message= new Message(identifier, fromUser, login, date, text);
                    list.add(message);
                }
                connection.close();
                return list;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            System.out.println("wrong");
        }
        return  null;
    }
    public void deleteALLMyMessages(String login){
        if (login.equals("")){
            System.out.println("Write your login");
            return;
        }
        if (createConnection()){
            String query= "DELETE FROM message WHERE toUser = ?";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, getUserId(login));
                int result = ps.executeUpdate();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}


