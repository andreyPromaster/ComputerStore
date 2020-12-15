package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CategoryProduct;
import model.Nomeclature;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewChangeProduct extends BaseController implements Initializable {

    private boolean saveCommand;
    private Nomeclature product;

    public void setSaveCommand(boolean saveCommand) {
        this.saveCommand = saveCommand;
    }

    public void setProduct(Nomeclature product) {
        this.product = product;
    }
    private ProductController mainController;

    public void setMainController(ProductController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private TextField FieldName;

    @FXML
    private TextField FieldArticle;

    @FXML
    private TextArea FieldDescription;

    @FXML
    private TextArea FieldComment;

    @FXML
    private ComboBox<CategoryProduct> TypeProduct;

    @FXML
    private Button btnSave;


    public void onBtnSaveChange(ActionEvent event) {
        Nomeclature product_for_save = new Nomeclature();
        if(isFieldIncorrect(FieldArticle, 45) && isFieldIncorrect(FieldName,45)){
            product_for_save.setDescription(FieldDescription.getText());
            product_for_save.setComment(FieldComment.getText());
            product_for_save.setArticle(FieldArticle.getText());
            product_for_save.setName(FieldName.getText());

            product_for_save.setIdCategory(TypeProduct.getSelectionModel().getSelectedItem().getIdCategory());
            client.sendMessage(Commands.INSERT_OR_UPDATE_PRODUCT.toString());
            client.sendObject(saveCommand);

            if(!saveCommand) { //  edit product
                product_for_save.setIdNomeclature(product.getIdNomeclature());
            }
            client.sendObject(product_for_save);
            try {
                if (client.readMessage().equals("OK")) {
                    successfulAlert("Ура", "Изменения сохранены");
                } else {
                    warningAlert("Что-то пошло не так", "Ошибка");
                }
            }
            catch (IOException exIO){
                exIO.printStackTrace();
            }
            mainController.refreshTableFromChildrenScene();
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } else{
            warningAlert("Ошибка","Слишком много символов в ячейке с названием или артикулом");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(saveCommand==false){ //for change product
            FieldName.setText(product.getName());
            FieldArticle.setText(product.getArticle());
            FieldDescription.setText(product.getDescription());
            FieldComment.setText(product.getComment());
        }

        mainController.initChildrenSceneList(TypeProduct);
    }
}
