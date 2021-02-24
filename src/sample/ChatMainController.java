package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sk.kosickaakademia.stovcikova.chat.database.Database;
import sk.kosickaakademia.stovcikova.chat.entity.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatMainController extends Controller{

    public Label meno;
    public TextField prijemcaSpravy;
    public TextField sprava;
    public Label logoutLabel;
    public ComboBox comboBox;
    public TextArea list_of_messages;

    public void nastavMeno(){
        meno.setText(loginn);
    }

    public void posliSpravu(ActionEvent actionEvent) {
        String teloSpravy = sprava.getText();
        String prijemca = ((String)comboBox.getValue());
        if (teloSpravy != null && teloSpravy != "" && prijemca !=null && prijemca != "" ){
            Database database = new Database();
            database.sendMessage(database.getUserId(loginn), prijemca, teloSpravy);

        }
    }

    public void odhlasMa(MouseEvent mouseEvent) {
        logoutLabel.getScene().getWindow().hide();
    }

   /* public void refreshButton(ActionEvent){
        posliSpravu().clear();
        if (sprava.isEmpty())
        posliSpravu().appendText("You do not new message");

    }*/
   public void setCHOICE(){
       List<String> list = new Database().AllUsers();
       if(list.isEmpty()){
           return;
       }else{
           for(String s : list){
               comboBox.getItems().add(s);
           }
       }

   }

    public void addSmile(ActionEvent actionEvent) {
        String string = sprava.getText();
        char znak = '\u26C4';
        sprava.setText(string + znak);
    }

    public void zobrazSpravy(ActionEvent actionEvent) {
        list_of_messages.clear();

        List<Message> list = new Database().getMyMessages(loginn);
        if(list.isEmpty()){
            list_of_messages.appendText("You do not have any new messages");
        }
        else{
            for(int i = list.size()-1; i >= 0 ; i--){
                Message message = list.get(i);
                Date date = message.getDt();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                String strDate = formatter.format(date);
                String od = message.getFrom();
                String komu = loginn;
                String messagee = message.getText();
                list_of_messages.appendText(strDate + " " +od + " " + komu + " " + messagee + '\n');
            }
        }
    }
}
