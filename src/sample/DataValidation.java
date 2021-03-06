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
            tray.setMessage("Password should be 6-20 characters with at least one digit, one upper case letter, one lower case letter");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(10));
            throw new IllegalArgumentException();
        }
    }
    public static void Password_Recovery_Validator(String Rec_Email, String Q1, String Q1_Answer, String Q2, String Q2_Answer)
    {
        String Email_Regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        String Answer_Format = "^[a-zA-Z0-9 ._-]{1,25}$";
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setAnimationType(type);


        if (!Rec_Email.matches(Email_Regex))
        {
            tray.setTitle("Unsuccessful");
            tray.setMessage("Email must contain Emailname@domain.extension ");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(5));
            throw new IllegalArgumentException();
        }

        if(Q1.compareTo(Q2) == 0 || Q2.compareTo(Q1) == 0)
        {
            tray.setTitle("Unsuccessful");
            tray.setMessage("Security Questions Cannot be the same");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(5));
            throw new IllegalArgumentException();
        }

        if (!Q1_Answer.matches(Answer_Format))
        {
            tray.setTitle("Unsuccessful Format");
            tray.setMessage("A-Z and Numbers in Answer Field 1");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(5));
            throw new IllegalArgumentException();
        }

        if (!Q2_Answer.matches(Answer_Format))
        {
            tray.setTitle("Unsuccessful Format");
            tray.setMessage("A-Z and Numbers in Answer Field 2");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(5));
            throw new IllegalArgumentException();
        }

    }
    public static void SignUP_Validator(String EMAIL, String F_Name, String L_Name, String Password, String C_Password,
                                        String Sec_Q1, String Sec_Q2, String Q1_Answer, String Q2_Answer) throws Exception {
        String Email_Regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        String Name_Regex = "^[a-zA-Z -]{1,20}$";
        String Answer_Format = "^[a-zA-Z0-9 ._-]{1,25}$";
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

        if (!F_Name.matches(Name_Regex) || !L_Name.matches(Name_Regex))
        {
            tray.setTitle("Unsuccessful");
            tray.setMessage("Incorrect format for name , A-Z");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(5));
            throw new IllegalArgumentException();
        }

        if (!Password.matches(Password_Regex))
        {
            tray.setTitle("Unsuccessful Password Format ");
            tray.setMessage("Password should be 6-20 characters with at least one digit, one upper case letter, one lower case letter");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(5));
            throw new IllegalArgumentException();
        }

        if (!Password.matches(C_Password))
        {
            tray.setTitle("Passwords do not match");
            tray.setMessage("Please try again");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(5));
            throw new IllegalArgumentException();
        }

        if(Sec_Q1.compareTo(Sec_Q2) == 0 || Sec_Q2.compareTo(Sec_Q1) == 0)
        {
            tray.setTitle("Unsuccessful");
            tray.setMessage("Security Questions Cannot be the same");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(5));
            throw new IllegalArgumentException();
        }

        if (!Q1_Answer.matches(Answer_Format))
        {
            tray.setTitle("Unsuccessful Format ");
            tray.setMessage("A-Z and Numbers in Answer Field 1");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(5));
            throw new IllegalArgumentException();
        }

        if (!Q2_Answer.matches(Answer_Format))
        {
            tray.setTitle("Unsuccessful Format ");
            tray.setMessage("A-Z and Numbers in Answer Field 2");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(5));
            throw new IllegalArgumentException();
        }
    }
}
