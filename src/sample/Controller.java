package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sk.kosickaakademia.stovcikova.chat.database.Database;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class Controller {
    public Button btn_login;
    public TextField txt_login;
    public PasswordField txt_password;
    public Label lbl_error;
    public static String loginn= "";


    private void openSecondWindow() throws IOException{
        FXMLLoader root = new FXMLLoader();
        root.setLocation(getClass().getResource("ChatMainControler.fxml"));
        Stage stage = new Stage();
        stage.setTitle("CHATING");
        stage.setScene(new Scene(root.load(), 600, 600));
        stage.setResizable(false);
        stage.show();
        ChatMainController chatMainController = root.getController();
        chatMainController.nastavMeno();
        chatMainController.setCHOICE();

    }

    public void prihlasMa(javafx.event.ActionEvent actionEvent) throws IOException {
        System.out.println("Hello! It works!");
        String login = txt_login.getText().trim();
        String password = txt_password.getText().trim();
        if (login.length()>0 && password.length()>0){
            Database db = new Database();
            boolean areYouLogin= db.login(login, password);
            if (areYouLogin){
                System.out.println("You are login");
                loginn = txt_login.getText();
                //close first window
                btn_login.getScene().getWindow().hide();
                //open new formular
                openSecondWindow();
            }else {
                System.out.println("wrong");
                lbl_error.setVisible(true);
            }

        }
    }

}

