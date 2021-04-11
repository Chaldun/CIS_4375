package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.sun.javafx.sg.prism.NGGroup;
import javafx.animation.PauseTransition;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Password_Recovery_Controller implements Initializable {
    private final String DB_URL = "jdbc:sqlserver://172.26.54.26;database=oardb";  //Connection string to Database
    final String USER = "admin";
    final String PASS = "password";
    final String GMAILusername = "naughtykiwi2@gmail.com";
    final String GMAILpassword = "549657ll";
    String ID = null;
    private boolean isMyComboBoxEmpty;
    ObservableList<String> Sec_Q1 = FXCollections.observableArrayList(); //Used for Security Question 1 combo Box
    ObservableList<String> Sec_Q2 = FXCollections.observableArrayList(); //Used for Security Question 2 combo Box
    @FXML
    private VBox Forgot_Password_Pane;
    @FXML
    private TextField Email_Recovery;
    @FXML
    private JFXButton Recovery_Login_Button;
    @FXML
    private ProgressIndicator Login_Progress;
    @FXML
    private JFXComboBox Combo_Box_Security_Q1;
    @FXML
    private JFXComboBox Combo_Box_Security_Q2;
    @FXML
    private TextField Q1_Input_Field;
    @FXML
    private TextField Q2_Input_Field;
    @FXML
    private Label Recovery_Password;
    @FXML
    private Label firstName_Label;
    @FXML
    private Label lastName_Label;
    @FXML
    private Label Email_Success_Label;




    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }


    @FXML
    private void Close_App(ActionEvent event) {System.exit(0);}

    @FXML
    private void Submit_Action(ActionEvent event) {
    }

    @FXML
    private void Recovery_Login_Button(ActionEvent event) throws IOException
    {
        VBox Login_Controller_Pane = FXMLLoader.load(getClass().getResource("Login_Page.fxml"));
        Forgot_Password_Pane.getChildren().setAll(Login_Controller_Pane);
    }

    @FXML
    private void Security_Q1(ActionEvent event) {
    }

    @FXML
    private void Security_Q1_List(MouseEvent mouseEvent)
    {
        Combo_Box_Security_Q1.setItems(Sec_Q1);
        Combo_Box_Security_Q1.setStyle("-fx-background-color: a0a2ab;");
        Sec_Q1.clear();
        Sec_Q1.setAll("In what city or town was your first job?", "What is the name of your first pet?", "What are the last five digits of your drivers licence number?");
    }

    @FXML
    private void Security_Q2(ActionEvent event) {
    }

    @FXML
    private void Security_Q2_List(MouseEvent mouseEvent)
    {
        Combo_Box_Security_Q2.setItems(Sec_Q2);
        Combo_Box_Security_Q2.setStyle("-fx-background-color: a0a2ab;");
        Sec_Q2.clear();
        Sec_Q2.setAll("In what city or town was your first job?", "What is the name of your first pet?", "What are the last five digits of your drivers licence number?");
    }

    @FXML
    private void Submit_Recovery(ActionEvent event)
    {
        if (isMyComboBoxEmpty = Combo_Box_Security_Q1.getSelectionModel().isEmpty() || Combo_Box_Security_Q2.getSelectionModel().isEmpty())
        {
            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;
            tray.setAnimationType(type);
            tray.setTitle("Unsuccessful");
            tray.setMessage("Please Choose two Security Questions");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(5));
            throw new IllegalArgumentException();
        }
        try {
            DataValidation.Password_Recovery_Validator(Email_Recovery.getText(),
                    Combo_Box_Security_Q1.getValue().toString(),
                    Q1_Input_Field.getText(),
                    Combo_Box_Security_Q2.getValue().toString(),
                    Q2_Input_Field.getText());

            String Recovery_Email = Email_Recovery.getText().toUpperCase();
            String Q1 = Combo_Box_Security_Q1.getValue().toString();
            String Q1_Answer = Q1_Input_Field.getText();
            String Q2 = Combo_Box_Security_Q2.getValue().toString();
            String Q2_Answer = Q2_Input_Field.getText();

            Connection conn = null;
            conn = DriverManager.getConnection(DB_URL,USER,PASS);



            ResultSet resultSet = conn.createStatement().executeQuery("SELECT dbo.Login.email, dbo.Login.securityQuestion1, dbo.Login.securityQuestion2," +
                    " dbo.Login.securityAnswer1, dbo.Login.securityAnswer2, dbo.Login.password, dbo.Login.firstName, dbo.Login.lastName FROM dbo.Login" +
                    " where email = ('"+Recovery_Email+"')" +
                    "  AND securityQuestion1 =('"+Q1+"')"+
                    "  AND securityQuestion2 =('"+Q2+"')" +
                    "  AND securityAnswer1 =('"+Q1_Answer+"')"+
                    "  AND securityAnswer2 =('"+Q2_Answer+"')");

            int count = 0;


            while (resultSet.next())
            {
                count=count+1;
                if (count == 1) // Here is where the email will be sent out
                {
                    Recovery_Password.setText(resultSet.getString("password"));
                    firstName_Label.setText(resultSet.getString("firstName"));
                    lastName_Label.setText(resultSet.getString("lastName"));

                    Properties prop = new Properties();
                    prop.put("mail.smtp.host", "smtp.gmail.com");
                    prop.put("mail.smtp.port", "587");
                    prop.put("mail.smtp.auth", "true");
                    prop.put("mail.smtp.starttls.enable", "true");  //TLS

                    Session session = Session.getInstance(prop,
                            new javax.mail.Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(GMAILusername, GMAILpassword);
                                }
                            });

                    try {

                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("naughtykiwi2@gmail.com"));
                        message.setRecipients(
                                Message.RecipientType.TO,
                                InternetAddress.parse(Recovery_Email)
                        );
                        message.setSubject("Outback Auto Reapir");
                        message.setText("Hi " + firstName_Label.getText() + " " + lastName_Label.getText() +
                                ",\n\nWe've received a request to retrieve your Outback Auto Repair account password. " +
                                        "Your password is \n\n**********\n" + Recovery_Password.getText() + "\n**********" +
                        "\n\nIf you didn’t submit a request to retrieve your password, please let us know at naughtykiwi2@gmail.com.");

                        Transport.send(message);

                        Email_Success_Label.setVisible(true);

                    } catch (MessagingException e) { e.printStackTrace(); }
                }
            }
            if (count == 0)
            {
                TrayNotification tray = new TrayNotification();
                AnimationType type = AnimationType.POPUP;
                tray.setAnimationType(type);
                tray.setTitle("Unsuccessful");
                tray.setMessage("No Records found");
                tray.setNotificationType(NotificationType.ERROR);
                tray.showAndDismiss(Duration.seconds(5));
            }

        } catch (SQLException e) { e.printStackTrace(); }
    }
}
