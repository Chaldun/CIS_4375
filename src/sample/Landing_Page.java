package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Landing_Page implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void Close_App(ActionEvent actionEvent) { System.exit(0); }
}
