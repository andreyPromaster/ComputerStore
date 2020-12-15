package controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.CategoryProduct;
import model.DocumentInfo;
import model.ProductInStore;
import model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewProductInStoreController extends BaseController implements Initializable, ISetUser {
    @FXML
    private TableView<ProductInStore> TableProductStore;

    @FXML
    private TableColumn<ProductInStore, Integer> ColumnID;

    @FXML
    private TableColumn<ProductInStore, String> ColumnArticle;

    @FXML
    private TableColumn<ProductInStore, String> ColumnName;

    @FXML
    private TableColumn<ProductInStore, Double> ColumnPrice;

    @FXML
    private TableColumn<ProductInStore, Integer> ColumnAmount;

    @FXML
    private ComboBox<CategoryProduct> FilterListByCategory;

    @FXML
    private TextField FieldFindByName;

    @FXML
    private TextField FieldStartPrice;

    @FXML
    private TextField FieldEndPrice;

    @FXML
    void FilterByCategory(ActionEvent event) {
        if(FilterListByCategory.getSelectionModel().getSelectedItem().getName().equals("Все категории")){
            TableProductStore.setItems(getListProduct());
        }
        else {
            ObservableList<ProductInStore> arr_filtered = getListProduct().filtered(item -> item.getCategory().getName().equals(
                    FilterListByCategory.getSelectionModel().getSelectedItem().getName()));
            TableProductStore.setItems(arr_filtered);
        }
    }

    @FXML
    void FindByName(ActionEvent event) {
        client.sendMessage(Commands.SELECT_PRODUCT_IN_STORE_BY_NAME.toString());
        client.sendObject(FieldFindByName.getText());
        ObservableList<ProductInStore> list = FXCollections.observableArrayList((ArrayList<ProductInStore>)client.readObject());
        TableProductStore.setItems(list);
    }

    @FXML
    void FindByPrice(ActionEvent event) {
        if((isFieldIncorrect(FieldEndPrice, 7)
                && isFieldIncorrect(FieldStartPrice, 7))){
            String start = FieldStartPrice.getText();
            String end = FieldEndPrice.getText();
            if(isDigit(start) && isDigit(end)){
                client.sendMessage(Commands.SELECT_PRODUCT_IN_STORE_BY_PRICE.toString());
                client.sendObject(Double.parseDouble(start));
                client.sendObject(Double.parseDouble(end));
                ObservableList<ProductInStore> list = FXCollections.observableArrayList((ArrayList<ProductInStore>)client.readObject());
                TableProductStore.setItems(list);

            }else{
                warningAlert("Ошибка","Поле должно содержать цифры");
            }

        } else {
            warningAlert("Ошибка","Привышен лимит символов");
        }
    }

    private User user;
    private ObservableList<ProductInStore> listProduct;


    public void setListProduct(ObservableList<ProductInStore> listProduct) {
        this.listProduct = listProduct;
    }

    public ObservableList<ProductInStore> getListProduct() {
        return listProduct;
    }

    public void setModelUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTableProduct();
        initListCategoryProduct(FilterListByCategory);
    }
    private void initTableProduct(){
        ColumnID.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getStore().getIdStore()));
        ColumnArticle.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getProduct().getArticle()));
        ColumnName.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getProduct().getName()));
        ColumnPrice.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getStore().getPrice()));
        ColumnAmount.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getStore().getAmount()));
    }

    private void refreshTableProduct(){
        initTableProduct();
        client.sendMessage(Commands.GET_ALL_PRODUCT_IN_STORE.toString());
        ObservableList<ProductInStore> list = FXCollections.observableArrayList((ArrayList<ProductInStore>)client.readObject());
        TableProductStore.setItems(list);
        setListProduct(list);
    }

    private void initListCategoryProduct(ComboBox<CategoryProduct> listbox){

        listbox.getSelectionModel().clearSelection();
        client.sendMessage(Commands.GET_ALL_CATEGORY.toString());
        ObservableList<CategoryProduct> list = FXCollections.observableArrayList((ArrayList<CategoryProduct>)client.readObject());

        CategoryProduct categoryProduct = new CategoryProduct();
        categoryProduct.setName("Все категории");
        list.add(categoryProduct);

        listbox.setItems(list);
        listbox.getSelectionModel().select(6);
    }

    public void onBtnComeBack(ActionEvent event) {
        goHomeScene(event, user);
    }
}
