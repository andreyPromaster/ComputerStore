package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class HomeController extends BaseController implements ISetUser{

    public User user;

    public void setModelUser(User user){
        this.user = user;
    }

    //usual user - 1; admin - 0;
    public void onBtnDocument(ActionEvent event) {

        if(user.getRole() == 0){

        FXMLLoader loader = changeSceneForSetModel("/sample/workDocument.fxml", event);
        ProductController controller = loader.getController();
        controller.setModelUser(user);

        } else {
            warningAlert("У вас нет доступа!", "Нужен статус администратора");
        }
    }

    public void onBtnShowCustomer(ActionEvent event) {

        if(user.getRole() == 0){
            FXMLLoader loader = changeSceneForSetModel("/sample/editCustomer.fxml", event);
            EditCustomerController controller = loader.getController();
            controller.setModelUser(user);

        } else {
            warningAlert("У вас нет доступа!", "Нужен статус администратора");
        }
    }

    public void onBtnCreateStat(ActionEvent event) {
        if(user.getRole() == 0){
            FXMLLoader loader = changeSceneForSetModel("/sample/statistic.fxml", event);
            StatisticController controller = loader.getController();
            controller.setModelUser(user);

        } else {
            warningAlert("У вас нет доступа!", "Нужен статус администратора");
        }
    }


    public void onBtnMakeDocument(ActionEvent event) {
        FXMLLoader loader = changeSceneForSetModel("/sample/createDocument.fxml", event);
        CreateDocumentController controller = loader.getController();
        controller.setModelUser(user);
    }

    public void onBtnViewProductInStore(ActionEvent event) {
        FXMLLoader loader = changeSceneForSetModel("/sample/viewProductInStore.fxml", event);
        ViewProductInStoreController controller = loader.getController();
        controller.setModelUser(user);
    }

}
