package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class LoginController extends BaseController {

    @FXML
    TextField idFieldUsername;
    @FXML
    PasswordField idFieldPassword;

    public void onBtnLogin(ActionEvent actionEvent) {
        User user = new User();

        if(isFieldIncorrect(idFieldUsername, 20) && isFieldIncorrect(idFieldPassword, 20)) {
            user.setUsername(idFieldUsername.getText());
            user.setPassword(idFieldPassword.getText());

            client.sendMessage(Commands.IS_USER_EXIST.toString());
            client.sendObject(user);
            try {
                if (client.readMessage().equals("OK")) {
                    FXMLLoader loader = changeSceneForSetModel("/sample/home_page.fxml", actionEvent);
                    HomeController controller = loader.getController(); //получаем контроллер для второй формы
                    controller.setModelUser(getUser(user));

                } else {
                    warningAlert("Ошибка", "Нет такого пользователя. Попробуйте еще раз");
                }
            }
            catch (IOException exIO){
                exIO.printStackTrace();
            }
            } else {
            warningAlert("Ошибка","Слишком много символов в полях для вовода");
        }
    }
    private User getUser(User user){
        client.sendMessage(Commands.GET_USER.toString());
        client.sendObject(user);
        return (User)client.readObject();
    }

    public void onBntRegister(ActionEvent event) {
        changeScene("/sample/registration.fxml", event);
    }

}
