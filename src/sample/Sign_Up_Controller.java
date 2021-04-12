package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
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
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Sign_Up_Controller implements Initializable {
    private final String DB_URL = "jdbc:sqlserver://172.26.54.26;database=oardb";  //Connection string to Database
    final String USER = "admin";
    final String PASS = "password";
    String ID = null;
    private String S_F_Name;
    private String S_L_Name;
    private String S_Email;
    private String S_Date;
    private boolean isMyComboBoxEmpty;
    ObservableList<String> Sec_Q1 = FXCollections.observableArrayList(); //Used for Security Question 1 combo Box
    ObservableList<String> Sec_Q2 = FXCollections.observableArrayList(); //Used for Security Question 2 combo Box
    @FXML
    private ProgressIndicator Sign_Up_Progress;
    @FXML
    private VBox Sign_Up_Pane;
    @FXML
    private TextField Email_Sign_Up;
    @FXML
    private JFXButton Submit_Button_Sign_Up;
    @FXML
    private JFXComboBox Combo_Box_Security_Q1_Sign_Up;
    @FXML
    private JFXComboBox Combo_Box_Security_Q2_Sign_Up;
    @FXML
    private TextField Security_A1_Sign_Up;
    @FXML
    private TextField Security_A2_Sign_Up;
    @FXML
    private TextField First_Name_Sign_Up;
    @FXML
    private TextField Last_Name_Sign_Up;
    @FXML
    private PasswordField Password_Sign_Up;
    @FXML
    private PasswordField Confirm_Password_Sign_Up;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    private void Close_App(ActionEvent event) {
    }

    @FXML
    private void Submit_Action_Sign_Up(ActionEvent event)
    {
        Sign_Up_Progress.setVisible(true);
        PauseTransition whis = new PauseTransition();
        whis.setDuration(Duration.seconds(3));
        whis.setOnFinished(dbz -> {
            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;
            tray.setAnimationType(type);
            tray.setTitle("Successfully signed up!");
            tray.setMessage("Welcome " + Email_Sign_Up.getText());
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.seconds(1.5));

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
            if (isMyComboBoxEmpty = Combo_Box_Security_Q1_Sign_Up.getSelectionModel().isEmpty() || Combo_Box_Security_Q2_Sign_Up.getSelectionModel().isEmpty())
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

            DataValidation.SignUP_Validator(Email_Sign_Up.getText().toUpperCase(), First_Name_Sign_Up.getText(), Last_Name_Sign_Up.getText(), Password_Sign_Up.getText(), Confirm_Password_Sign_Up.getText(),
                                            Combo_Box_Security_Q1_Sign_Up.getValue().toString(),Combo_Box_Security_Q2_Sign_Up.getValue().toString(),Security_A1_Sign_Up.getText(),Security_A2_Sign_Up.getText());
            S_F_Name = First_Name_Sign_Up.getText().toUpperCase();
            S_L_Name = Last_Name_Sign_Up.getText().toUpperCase();
            S_Email = Email_Sign_Up.getText().toUpperCase();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            S_Date = dtf.format(now);

            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement stmt = null;

//            stmt = conn.prepareStatement("INSERT INTO dbo.[Login] (email,password,createdAt,updatedAt,accountTypeId,securityQuestion1,securityQuestion2,securityAnswer1,securityAnswer2,firstName,lastName)" +
//                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt = conn.prepareStatement("INSERT INTO dbo.Login (email,password,createdAt,updatedAt,accountTypeId,securityQuestion1,securityQuestion2,securityAnswer1,securityAnswer2,firstName,lastName)" +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, S_Email);
            stmt.setString(2, Password_Sign_Up.getText());
            stmt.setString(3, S_Date);
            stmt.setString(4, S_Date);
            stmt.setString(5, String.valueOf(2));
            stmt.setString(6, String.valueOf(Sec_Q1));
            stmt.setString(7, String.valueOf(Sec_Q2));
            stmt.setString(8, String.valueOf(Security_A1_Sign_Up));
            stmt.setString(9, String.valueOf(Security_A2_Sign_Up));
            stmt.setString(10, S_F_Name);
            stmt.setString(11, S_L_Name);

            stmt.executeUpdate();

            conn.commit();
            stmt.close();
            conn.close();

            whis.play();
        } catch (Exception e) { Sign_Up_Progress.setVisible(false);
            e.printStackTrace();}
    }

    @FXML
    private void Recovery_Login_Button(ActionEvent event) {
    }

    @FXML
    private void Security_Q1_List_Sign_Up(MouseEvent mouseEvent) {
    }

    @FXML
    private void Security_Q2_List_Sign_Up(MouseEvent mouseEvent) {
    }

    @FXML
    private void Combo_Box_Security_Q1_Sign_Up(ActionEvent event) {
    }

    @FXML
    private void Combo_Box_Security_Q2_Sign_Up(ActionEvent event) {
    }
}
