package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.User;
import model.UserInfo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InfoController extends BaseController{

    @FXML
    private TextField  IdFieldEmail;

    @FXML
    private TextField  IdFieldName;

    @FXML
    private TextField  IdFieldPhoneNumber;

    @FXML
    private DatePicker IdDateOfBirth;

    @FXML
    private TextField  IdFieldSecondName;

    public User user;

    public void setModelUser(User user){
        this.user = user;
    }
    private final EmailValidator emailValidator = new EmailValidator();

    private boolean isNumber(String str){
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean isPhoneNumber(String str){
        return str.startsWith("+375") && str.length() == 13 && isNumber(str.substring(3));
    }

    public void onBtnAddInfo(ActionEvent event) {
        if(emailValidator.validate(IdFieldEmail.getText()) && isFieldIncorrect(IdFieldEmail, 40)){
            if(isPhoneNumber(IdFieldPhoneNumber.getText())) {
                if(isFieldIncorrect(IdFieldName,45) && isFieldIncorrect(IdFieldSecondName,45)) {
                    UserInfo info = new UserInfo();
                    info.setEmail(IdFieldEmail.getText());
                    info.setFirst_name(IdFieldName.getText());
                    info.setPhone_number(IdFieldPhoneNumber.getText());
                    info.setSecond_name(IdFieldSecondName.getText());

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    LocalDate date = IdDateOfBirth.getValue();
                    if (date != null) {
                        info.setDate_of_birth(formatter.format(date));
                    }
                    client.sendMessage(Commands.INSERT_INFO_ABOUT_USER.toString());
                    client.sendObject(user);
                    client.sendObject(info);

                    try {
                        if (client.readMessage().equals("OK")) {
                            successfulAlert("Добавили)", "Успешно добавлено. Залогиньтесь)");
                            changeScene("/sample/login.fxml", event);
                        } else {
                            warningAlert("Ошибка", "Что-то пошло не так");
                        }
                    } catch (IOException exIO) {
                        exIO.printStackTrace();
                    }
                }else{
                    warningAlert("Ошибка","Слишком много символов в полях");
                }
            }else {
                warningAlert("Ошибка","Некорректный номер телефона");
            }
        } else {
            warningAlert("Ошибка","Некорректный email");
        }
    }


    public void onBtnSkipInfo(ActionEvent event) {
        UserInfo info = new UserInfo();
        client.sendMessage(Commands.INSERT_INFO_ABOUT_USER.toString());
        client.sendObject(user);
        client.sendObject(info);
        try {
            if (client.readMessage().equals("OK")) {
                changeScene("/sample/login.fxml", event);
            } else {
                warningAlert("Ошибка", "Что-то пошло не так");
            }
        }
        catch (IOException exIO){
            exIO.printStackTrace();
        }
    }
}
