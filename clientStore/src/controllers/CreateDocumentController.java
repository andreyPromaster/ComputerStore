package controllers;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CreateDocumentController extends BaseController implements Initializable, ISetUser {

    @FXML
    private TableView<ProductInStore> TableProduct;

    @FXML
    private TableColumn<ProductInStore, Integer> ColumnIDFindProduct;

    @FXML
    private TableColumn<ProductInStore, String> ColumnArticleFindProduct;

    @FXML
    private TableColumn<ProductInStore, String> ColumnNameFindProduct;

    @FXML
    private TableView<DocumentContentInfo> TableDocContent;

    @FXML
    private TableColumn<DocumentContentInfo, Integer> ColumnIDContent;

    @FXML
    private TableColumn<DocumentContentInfo, String> ColumnNameContent;

    @FXML
    private TableColumn<DocumentContentInfo, Integer> ColumnAmountContent;

    @FXML
    private TableColumn<DocumentContentInfo, Double> ColumnPriceContent;

    @FXML
    private ListView<Customer> ListCustomer;

    @FXML
    private ComboBox<DocumentType> ListTypeDoc;

    @FXML
    private ComboBox<DocumentState> ListStageDoc;

    @FXML
    private ListView<CategoryProduct> ListTypeProduct;

    @FXML
    private Label SumPrice;

    private User user;
    private int idDocument;

    public User getUser() {
        return user;
    }
    public void setModelUser(User user) {
        this.user = user;
    }

    private ObservableList<ProductInStore> product_list_model;
    public void setProduct_list(ObservableList product_list) {
        this.product_list_model = product_list;
    }
    public ObservableList<ProductInStore> getProduct_list() {
        return product_list_model;
    }

    private ArrayList<Store> productToUpdate = new ArrayList<>();  // this array helps
                                                // to update products that are stored in
                                               // shop. To bring amount of products up to date

    public void addProductToUpdate(Store store){
        productToUpdate.add(store);
    }

    public ArrayList<Store> getProductToUpdate() {
        return productToUpdate;
    }

    private static AdditionProductStrategy strategy;

    private void initTableDocContent(){
        ColumnIDContent.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getContent().getIdContent()));
        ColumnNameContent.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getProduct().getName()));
        ColumnAmountContent.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getContent().getAmount()));
        ColumnPriceContent.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getContent().getPrice()));

    }

    private void initTableProduct() {
        ColumnIDFindProduct.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getProduct().getIdNomeclature()));
        ColumnArticleFindProduct.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getProduct().getArticle()));
        ColumnNameFindProduct.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getProduct().getName()));
    }

    private void initListTypeDoc(){
        ListTypeDoc.getSelectionModel().clearSelection();
        client.sendMessage(Commands.GET_ALL_TYPE_DOCUMENT.toString());
        ObservableList<DocumentType> list = FXCollections.observableArrayList((ArrayList<DocumentType>)client.readObject());
        ListTypeDoc.setItems(list);

        ListTypeDoc.getSelectionModel().select(0);
    }

    private void initListStageDoc(){
        ListStageDoc.getSelectionModel().clearSelection();
        client.sendMessage(Commands.SELECT_ALL_STATE_OF_DOCUMENT.toString());
        ObservableList<DocumentState> list = FXCollections.observableArrayList((ArrayList<DocumentState>)client.readObject());
        ListStageDoc.setItems(list);

        ListStageDoc.getSelectionModel().select(0);
    }

    private void initListTypeProduct(){
        ListTypeProduct.getSelectionModel().clearSelection();
        client.sendMessage(Commands.GET_ALL_CATEGORY.toString());
        ObservableList<CategoryProduct> list = FXCollections.observableArrayList((ArrayList<CategoryProduct>)client.readObject());
        ListTypeProduct.setItems(list);

        ListTypeProduct.getSelectionModel().select(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initListTypeDoc();
        initListStageDoc();
        initListTypeProduct();
        initTableProduct();
        initTableDocContent();
        SumPrice.setText("");
    }

    private boolean saveDocument(){
        if(ListCustomer.getSelectionModel().getSelectedItem() != null){
            Document document = new Document();
            document.setIdCustomer(ListCustomer.getSelectionModel().getSelectedItem().getIdCustomer());
            document.setIdType(ListTypeDoc.getSelectionModel().getSelectedItem().getIdType());
            document.setIdState(ListStageDoc.getSelectionModel().getSelectedItem().getIdState());
            document.setUserId(getUser().getIdUser());
            client.sendMessage(Commands.INSERT_DOCUMENT.toString());
            client.sendObject(document);
            idDocument = (int)client.readObject();
            return true;
        }else {
            warningAlert("Ошибка","Выберите контрагента");
            return false;
        }
    }

    private void updateAmountProductsInStore(){

    }
    @FXML
    boolean onBtnCreateDoc(ActionEvent event) {
        if (saveDocument()){
            ObservableList<DocumentContentInfo> context = TableDocContent.getItems();
            boolean isSuccess =true;
            for(DocumentContentInfo item: context){
                client.sendMessage(Commands.INSERT_DOCUMENT_CONTEXT.toString());
                DocumentContent documentContent = item.getContent();
                documentContent.setIdDocument(idDocument);
                client.sendObject(documentContent);
                if(!client.readObject().equals("OK")){
                    isSuccess =false;
                }
            }
            for(Store store: productToUpdate){
                client.sendMessage(Commands.UPDATE_AMOUNT_PRODUCT_IN_STORE.toString());
                client.sendObject(store);
            }

            if(isSuccess==true){
                successfulAlert("Супер","Накладная сохранена!");
                TableDocContent.getItems().clear();
                TableProduct.getItems().clear();
                SumPrice.setText("");
            } else {
                warningAlert("Ошибка","что-то пошло не так");
            }
            return isSuccess;

        } else {
            return false;
        }
    }

    public void addContextInDoc(DocumentContentInfo info){

        ObservableList<DocumentContentInfo> context =  TableDocContent.getItems();
        context.add(info);
        Double sum = 0.0;
        for(int i =0; i<context.size(); i++){
            sum += context.get(i).getContent().getPrice() * context.get(i).getContent().getAmount();
        }
        SumPrice.setText(sum.toString());
    }

    public void FilterCustomerAndProductByType(ActionEvent event) {
        if (ListTypeDoc.getSelectionModel().getSelectedItem().getName().equals("Поставка")){
            strategy = new AddProductsForSupplier(client);
        } else {
            strategy = new AddProductsForBuyer(client);
// in this place only init model Products
//            client.sendMessage(Commands.GET_ALL_PRODUCT_IN_STORE.toString());
//            setProduct_list((ArrayList<ProductInStore>)client.readObject());
//            ArrayList<Nomeclature> temp = product_list.stream().map(ProductInStore::getProduct).collect(Collectors.toCollection(ArrayList::new));
//            System.out.println(temp);
//            ObservableList<Nomeclature> list_store = FXCollections.observableArrayList(temp);
        }
        ListCustomer.setItems(strategy.getCustomersForSelecting());
        ObservableList <ProductInStore> temp = strategy.getProductsForAdding();
        TableProduct.setItems(temp);
        setProduct_list(temp);
        TableDocContent.getItems().clear();
        productToUpdate.clear();
    }

    public void addProductInDocument(MouseEvent mouseEvent) {
        if (ListTypeDoc.getSelectionModel().getSelectedItem().getName().equals("Поставка")){
            showFormForAddContextInDoc(SettingsConst.STATUS_FOR_SUPPLIER, TableProduct.getSelectionModel().getSelectedItem());
        } else {
            showFormForAddContextInDoc(SettingsConst.STATUS_FOR_BUYER, TableProduct.getSelectionModel().getSelectedItem());
        }
    }

    private void showFormForAddContextInDoc(int command, ProductInStore selected_product){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/addProductInDocForm.fxml"));

            AddProductInDocFormController controller = new AddProductInDocFormController();
            controller.setCommand(command);
            controller.setSelected_product(selected_product);
            controller.setMainController(this);

            loader.setController(controller);

            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void FilterProductByCategory(MouseEvent mouseEvent) {

        ObservableList<ProductInStore> arr_filtered = getProduct_list().filtered(item -> item.getProduct().getIdCategory()
                == ListTypeProduct.getSelectionModel().getSelectedItem().getIdCategory());

        TableProduct.setItems(arr_filtered);
    }


    public void onBtnComeBack(ActionEvent event) {
        if (!TableDocContent.getItems().isEmpty()) {
            boolean answer = showConfirmation();
            if (answer) {
                boolean isSuccess = onBtnCreateDoc(event);
                if(isSuccess){
                    goHomeScene(event, getUser());
                }
            } else{
                goHomeScene(event, getUser());
            }
        } else {
            goHomeScene(event, getUser());
        }
    }

    private boolean showConfirmation() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Вы хотите выйти?");
        alert.setHeaderText("Есть несохраненные данные!");
        alert.setContentText("Сохранить их?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }
}
