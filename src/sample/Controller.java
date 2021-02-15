package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sk.kosickaakademia.stovcikova.chat.database.Database;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class Controller {
    public Button btn_login;
    public TextField txt_login;
    public PasswordField txt_password;
    public Label lbl_error;
    private String login= "";

    public void btn_clickLogin(ActionEvent actionEvent) throws IOException {
        System.out.println("Hello! It works!");
        String login = txt_login.getText().trim();
        String password = txt_password.getText().trim();
        if (login.length()>0 && password.length()>0){
            Database db = new Database();
            boolean areYouLogin= db.login(login, password);
            login= txt_login.getText();
            btn_login.getScene().getWindow().hide();
        }
    }
}
