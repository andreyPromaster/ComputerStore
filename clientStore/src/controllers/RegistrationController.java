package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RegistrationController extends BaseController{
    @FXML
    private PasswordField idFieldPassword;

    @FXML
    private PasswordField IdFieldPasswordRepeat;

    @FXML
    private TextField IdFieldLogin;

    @FXML
    private ComboBox  IdStatus;


    final private ArrayList<String> userStatus = new ArrayList<String>(Arrays.asList("Кассир", "Админ"));;

    public void onBtnRegister(ActionEvent actionEvent) {
        if(IdFieldPasswordRepeat.getText().equals(idFieldPassword.getText())){
            if(isFieldIncorrect(IdFieldLogin, 20)
                    && isFieldIncorrect(IdFieldPasswordRepeat,20)) {
                System.out.println(IdStatus.getSelectionModel().getSelectedItem());
                if (IdStatus.getSelectionModel().getSelectedItem() != null ){
                    User user = new User();
                    user.setUsername(IdFieldLogin.getText());
                    user.setPassword(idFieldPassword.getText());
                    user.setRole(IdStatus.getSelectionModel().getSelectedIndex());
                    // usual user - 1; admin - 0;
                    client.sendMessage(Commands.CHECK_AND_SAVE_NEW_USER.toString());
                    client.sendObject(user);
                    try {
                        if (client.readMessage().equals("OK")) {
                            successfulAlert("Ура", "Расскажите о себе");
                            FXMLLoader loader = changeSceneForSetModel("/sample/info.fxml", actionEvent);
                            InfoController controller = loader.getController(); //получаем контроллер для второй формы
                            controller.setModelUser(user);
                        } else {
                            warningAlert("Такой пользователь существует", "Повротрите попытку");
                        }
                    }
                    catch (IOException exIO){
                        exIO.printStackTrace();
                    }
                } else{
                   warningAlert("Ошибка","Выберите роль пользователя");
                }
            } else {
                warningAlert("Ошибка","Превышен лимит символов");
            }

        } else {
            warningAlert("Пароли не совпадают","Повротрите попытку");
        }
    }


}
