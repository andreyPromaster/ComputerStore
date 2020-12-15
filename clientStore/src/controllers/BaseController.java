package controllers;

import com.sun.scenario.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public abstract class BaseController {

    protected Client client = new Client(SettingsConst.IPADDRESS, SettingsConst.PORT);


    protected void warningAlert(String title, String content) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    protected void successfulAlert(String title, String content) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    protected void changeScene(String resource, ActionEvent event) {
        try {
            Parent home_page = FXMLLoader.load(getClass().getResource(resource));
            Scene home = new Scene(home_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(home);
            app_stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected boolean isFieldIncorrect(TextField field, int max_length) {
        return field.getText().length() <= max_length;
    }

    protected FXMLLoader changeSceneForSetModel(String resource, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Parent root = loader.load();
            Scene home = new Scene(root);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(home);
            //InfoController controller = loader.getController(); //получаем контроллер для второй формы
            //controller.setModelUser(user); // передаем необходимые параметры
            app_stage.show();
            return loader;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static boolean isDigit(String s) {
                try {
                    Double.parseDouble(s);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
        }
    }
    protected void goHomeScene(ActionEvent event, User user){
        FXMLLoader loader = changeSceneForSetModel("/sample/home_page.fxml", event);
        HomeController controller = loader.getController();
        controller.setModelUser(user);
    }
}
