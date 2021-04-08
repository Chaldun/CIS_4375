package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Landing_Page implements Initializable {


    public Label Hidden_Text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void Show_Text(ActionEvent actionEvent) {
        Hidden_Text.setVisible(true);

    }


}
