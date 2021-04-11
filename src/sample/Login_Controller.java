package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.sun.javafx.sg.prism.NGGroup;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class Login_Controller implements Initializable {
    private final String DB_URL = "jdbc:sqlserver://172.26.54.26;database=oardb";  // Connection String to DB
    final String USER = "admin";
    final String PASS = "password";
    String ID = null;
    @FXML
    private JFXButton Login_Button;
    @FXML
    private PasswordField Login_Password;
    @FXML
    private TextField Login_Email;
    @FXML
    private ProgressIndicator Login_Progress;
    @FXML
    private VBox Login_Controller_Pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }


    @FXML
    private void Close_App(ActionEvent actionEvent) { System.exit(0);}

    @FXML
    private void Login_Action(ActionEvent event)
    {
        Login_Progress.setVisible(true);
        PauseTransition whis = new PauseTransition();
        whis.setDuration(Duration.seconds(3));
        for (int i =0; i>=100 ; i++)
        {}
        System.out.println();
        whis.setOnFinished(dbz -> {
            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;
            tray.setAnimationType(type);
            tray.setTitle("Success!");
            tray.setMessage("Welcome " + Login_Email.getText());
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.seconds(2));

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Test.fxml"));
            Parent GUI = null;
            try {
                GUI = loader.load();
                Scene scene = new Scene(GUI);

                Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

                window.setScene(scene);
                window.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        try {
            DataValidation.Validator(Login_Email.getText().toUpperCase(), Login_Password.getText());
            String Email    = Login_Email.getText().toUpperCase();
            String Password = Login_Password.getText();
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            ResultSet resultSet = conn.createStatement().executeQuery(" SELECT DBO.[login].accountTypeId, DBO.[login].email, DBO.[login].password FROM DBO.[login]" +
                    "WHERE accountTypeId = ('"+1+"') AND Email =('"+Email+"') AND Password =('"+Password+"')");
            int count = 0;

            while (resultSet.next())
            {
                count=count+1;
            }

            if (count == 1)
            {
                whis.play();
            }

            else {Login_Progress.setVisible(false);
                TrayNotification tray = new TrayNotification();
                AnimationType type = AnimationType.SLIDE;
                tray.setAnimationType(type);
                tray.setTitle("Unsuccessful ");
                tray.setMessage("Credentials Incorrect");
                tray.setNotificationType(NotificationType.NOTICE);
                tray.showAndDismiss(Duration.seconds(5));}

        } catch (Exception e) {
            Login_Progress.setVisible(false); e.printStackTrace();
        }
    }

    @FXML
    private void Forgot_Password(ActionEvent actionEvent) throws IOException
    {
        VBox Forgot_Password_Pane = FXMLLoader.load(getClass().getResource("Password_Recovery.fxml"));
        Login_Controller_Pane.getChildren().setAll(Forgot_Password_Pane);
    }
}
