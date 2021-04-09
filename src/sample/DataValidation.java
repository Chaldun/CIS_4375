package sample;

import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidation
{
    public static void Validator(String EMAIL, String PASSWORD) throws Exception
    {
        String Email_Regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        String Password_Regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$";
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setAnimationType(type);



        if (!EMAIL.matches(Email_Regex))
        {
            tray.setTitle("Unsuccessful");
            tray.setMessage("Email must contain Emailname@domain.extension ");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(5));
            throw new IllegalArgumentException();
        }

        if (!PASSWORD.matches(Password_Regex))
        {
            tray.setTitle("Unsuccessful");
            tray.setMessage("Password should be 6-20 characters with at least one digit, one upper case letter, one lower case letter and one special symbol (“@#$%”)");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(10));
            throw new IllegalArgumentException();
        }
    }
}
