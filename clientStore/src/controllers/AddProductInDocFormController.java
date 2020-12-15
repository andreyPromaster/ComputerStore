package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddProductInDocFormController extends BaseController implements Initializable {
    @FXML
    private Label LabelName;

    @FXML
    private ListView<ProductInStore> ListPrice;

    @FXML
    private Spinner<Integer> AmountCounter;

    @FXML
    private TextField FieldPriceAdd;

    @FXML
    private Label LabelAdd;

    @FXML
    private Label LabelChoice;

    @FXML
    private Button BtnSave;


    private ProductInStore selected_product;
    private int command;
    private CreateDocumentController mainController;

    public void setMainController(CreateDocumentController mainController) {
        this.mainController = mainController;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public void setSelected_product(ProductInStore selected_product) {
        this.selected_product = selected_product;
    }

    public ProductInStore getSelected_product() {
        return selected_product;
    }

    public CreateDocumentController getMainController() {
        return mainController;
    }

    @FXML
    void onBtnSave(ActionEvent event) {
        DocumentContentInfo documentContentInfo = new DocumentContentInfo();
        if(command == SettingsConst.STATUS_FOR_SUPPLIER){
            if(isFieldIncorrect(FieldPriceAdd,7)) {
                if(isDigit(FieldPriceAdd.getText())){
                    DocumentContent documentContent = new DocumentContent();

                    documentContent.setAmount(AmountCounter.getValue());
                    documentContent.setPrice(Double.parseDouble(FieldPriceAdd.getText()));
                    documentContent.setIdNomenclature(getSelected_product().getProduct().getIdNomeclature());

                    documentContentInfo.setContent(documentContent);
                    documentContentInfo.setProduct(getSelected_product().getProduct());

                    mainController.addContextInDoc(documentContentInfo);
                    closeScene();
                }
                else{
                    warningAlert("Ошибка","Поле не должно содержать буквы");
                }
            } else {
                warningAlert("Ошибка","Слишком много символов");
            }
        }else{
            if(ListPrice.getSelectionModel().getSelectedItem() != null){
                if (ListPrice.getSelectionModel().getSelectedItem().getStore().getAmount()
                        >= AmountCounter.getValue()){

                    DocumentContent documentContent = new DocumentContent();
                    documentContent.setAmount(AmountCounter.getValue());
                    documentContent.setPrice(ListPrice.getSelectionModel().getSelectedItem().getStore().getPrice());
                    documentContent.setIdNomenclature(ListPrice.getSelectionModel().getSelectedItem().getStore().getIdNomenclature());

                    documentContentInfo.setContent(documentContent);
                    documentContentInfo.setProduct(ListPrice.getSelectionModel().getSelectedItem().getProduct());

                    Store store = ListPrice.getSelectionModel().getSelectedItem().getStore();
                    store.setAmount(store.getAmount() - AmountCounter.getValue());//model to update amount in db

                    ObservableList<ProductInStore> temp = mainController.getProduct_list();
                    for(int i=0;i< temp.size();i++){
                        if(temp.get(i).getStore().getIdStore()==store.getIdStore()){
                            temp.get(i).getStore().setAmount(store.getAmount());
                            break;
                        }else{
                            continue;
                        }
                    }

                    mainController.addProductToUpdate(store);
                    mainController.addContextInDoc(documentContentInfo);
                    closeScene();
                } else{
                    warningAlert("Ошибка","Нет товара на складе");
                }
            }else {
                warningAlert("Ошибка","Выберите цену!");
            }
        }

    }

    private void closeScene(){
        Stage stage = (Stage) BtnSave.getScene().getWindow();
        stage.close();
    }
    private void initList(){
        ObservableList<ProductInStore> arr_filtered = mainController.getProduct_list().filtered(item -> item.getProduct().getIdNomeclature()
                == getSelected_product().getProduct().getIdNomeclature());
        ListPrice.setItems(arr_filtered);
    }
    private void initSpinner(){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);
        AmountCounter.setValueFactory(valueFactory);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initSpinner();
        if(command == SettingsConst.STATUS_FOR_SUPPLIER){
            ListPrice.setVisible(false);
            LabelChoice.setVisible(false);
            LabelName.setText(getSelected_product().getProduct().getName());
        }else {
            initList();
            LabelName.setText(getSelected_product().getProduct().getName());
            LabelAdd.setVisible(false);
            FieldPriceAdd.setVisible(false);
        }
    }

    @FXML
    void gettingPrice(ActionEvent event) {

    }
}
