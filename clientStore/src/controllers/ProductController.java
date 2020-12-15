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
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.XMLFormatter;

public class ProductController extends BaseController implements Initializable, ISetUser{

    @FXML
    private TableView<Nomeclature> TableProduct;

    @FXML
    private TableColumn<Nomeclature, Integer> ColumnID;

    @FXML
    private TableColumn<Nomeclature, String> ColumnArticle;

    @FXML
    private TableColumn<Nomeclature, String> ColumnName;

    @FXML
    private TableColumn<Nomeclature, String> ColumnDescription;

    @FXML
    private TableColumn<Nomeclature, String> ColumnComment;

    @FXML
    private ComboBox<CategoryProduct> CategoryProductList;

    @FXML
    private TextField FieldFind;

    @FXML
    private ComboBox CategoryProductListFind;


    @FXML
    void onBntChange(ActionEvent event) {
        editProduct();
    }


    @FXML
    void onBtnDelete(ActionEvent event) {
        deleteProduct();
    }

    @FXML
    void onBtnNew(ActionEvent event) {
            saveProduct();
    }

    private ObservableList<DocumentInfo> documentlist;

    private User user;

    public void setModelUser(User user){
        this.user = user;
    }

    private void setDocumentlist(ObservableList<DocumentInfo> list){
        this.documentlist = list;
    }

    public ObservableList<DocumentInfo> getDocumentlist() {
        return documentlist;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();
        refreshTableDocument();

        initListCategoryProduct(CategoryProductList);
        initListTypeDocument(FilterTypeDocument);

        initTableDocumentContent();
    }

    private void initTable() {
        ColumnID.setCellValueFactory(new PropertyValueFactory<>("idNomeclature"));
        ColumnArticle.setCellValueFactory(new PropertyValueFactory<>("article"));
        ColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        ColumnComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
    }

    private void refreshTable() {
        initTable();
        client.sendMessage(Commands.GET_ALL_PRODUCT.toString());
        ObservableList<Nomeclature> list = FXCollections.observableArrayList((ArrayList<Nomeclature>)client.readObject());
        TableProduct.setItems(list);

    }

    private void initListCategoryProduct(ComboBox<CategoryProduct> listbox){

        listbox.getSelectionModel().clearSelection();
        client.sendMessage(Commands.GET_ALL_CATEGORY.toString());
        ObservableList<CategoryProduct> list = FXCollections.observableArrayList((ArrayList<CategoryProduct>)client.readObject());
        listbox.setItems(list);

        listbox.getSelectionModel().select(0);
    }

    public void initChildrenSceneList(ComboBox<CategoryProduct> listbox){
        initListCategoryProduct(listbox);
    }

    public void refreshTableFromChildrenScene(){
        refreshTable();
    }


    private void deleteProduct() {
        Nomeclature selected = TableProduct.getSelectionModel().getSelectedItem();
        if(selected != null){
            int Id = selected.getIdNomeclature();
            client.sendMessage(Commands.DELETE_PRODUCT.toString());
            client.sendObject(Id);
            refreshTable();
        } else {
            warningAlert("Ошибка","Выберите запись");
        }
    }


    private void editProduct() { // for updating existing product
        Nomeclature selected = TableProduct.getSelectionModel().getSelectedItem();
        if(selected != null){
            showEditFormProduct( selected, false);
        } else {
            warningAlert("Ошибка","Выбери запись для изменения");
        }
    }

    private void saveProduct(){
        showEditFormProduct(null, true);
    }

    public void onListFilter(ActionEvent event) {
        initTable();
        client.sendMessage(Commands.FILTER_PRODUCT_BY_CATEGORY.toString());
        client.sendObject(CategoryProductList.getSelectionModel().getSelectedItem().getIdCategory());

        ObservableList<Nomeclature> list = FXCollections.observableArrayList((ArrayList<Nomeclature>)client.readObject());
        TableProduct.setItems(list);
    }

    private void showEditFormProduct(Nomeclature product, boolean command){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/newChangeProduct.fxml"));

            NewChangeProduct controller = new NewChangeProduct();
            controller.setProduct(product);
            controller.setSaveCommand(command);
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

    public void onBntFind(ActionEvent event) {
        client.sendMessage(Commands.FIND_PRODUCT_BY.toString());
        client.sendObject(CategoryProductListFind.getSelectionModel().getSelectedIndex());
        client.sendObject(FieldFind.getText());
        initTable();
        ObservableList<Nomeclature> list = FXCollections.observableArrayList((ArrayList<Nomeclature>)client.readObject());
        TableProduct.setItems(list);
    }

// next tab about documents

    private void initTableDocument(){
        ColumnIDDocument.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getDocument().getIdDocument()));
        ColumnDatetime.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getDocument().getDate_time()));
        ColumnTypeDocument.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getType().getName()));
        ColumnUser.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getUser().getUsername()));
        ColumnAgent.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getCustomer().getFull_name()));
        ColumnStage.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getState().getName()));
    }

    private void refreshTableDocument(){
        initTableDocument();
        client.sendMessage(Commands.GET_ALL_INFO_DOCUMENT.toString());
        ObservableList<DocumentInfo> list = FXCollections.observableArrayList((ArrayList<DocumentInfo>)client.readObject());
        TableDocument.setItems(list);
        setDocumentlist(list);
    }
    private void initTableDocumentContent(){
        ColumnIDContent.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getContent().getIdContent()));
        ColumnNameContent.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getProduct().getName()));
        ColumnAmount.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getContent().getAmount()));
        ColumnPrice.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getContent().getPrice()));

    }
    private void initListTypeDocument(ComboBox<DocumentType> listbox){

        listbox.getSelectionModel().clearSelection();
        client.sendMessage(Commands.GET_ALL_TYPE_DOCUMENT.toString());
        ObservableList<DocumentType> list = FXCollections.observableArrayList((ArrayList<DocumentType>)client.readObject());
        DocumentType type = new DocumentType();
        type.setName("Все категории");
        list.add(type);

        listbox.setItems(list);

        listbox.getSelectionModel().select(0);
    }

    @FXML
    private TableView<DocumentInfo> TableDocument;

    @FXML
    private TableColumn<DocumentInfo, Integer> ColumnIDDocument;

    @FXML
    private TableColumn<DocumentInfo, String> ColumnDatetime;

    @FXML
    private TableColumn<DocumentInfo, String> ColumnTypeDocument;

    @FXML
    private TableColumn<DocumentInfo, String> ColumnUser;

    @FXML
    private TableColumn<DocumentInfo, String> ColumnAgent;

    @FXML
    private TableColumn<DocumentInfo, String> ColumnStage;

    @FXML
    private ComboBox<DocumentType> FilterTypeDocument;

    @FXML
    private DatePicker DateStart;

    @FXML
    private DatePicker DateEnd;

    @FXML
    private TableView<DocumentContentInfo> TableContentDocument;

    @FXML
    private TableColumn<DocumentContentInfo, Integer> ColumnIDContent;

    @FXML
    private TableColumn<DocumentContentInfo, String> ColumnNameContent;

    @FXML
    private TableColumn<DocumentContentInfo, Integer> ColumnAmount;

    @FXML
    private TableColumn<DocumentContentInfo, Double> ColumnPrice;

    @FXML
    void FilterListTypeDocument(ActionEvent event) {
        if(FilterTypeDocument.getSelectionModel().getSelectedItem().getName().equals("Все категории")){
            TableDocument.setItems(getDocumentlist());
        }
        else {
        ObservableList<DocumentInfo> arr_filtered = getDocumentlist().filtered(item -> item.getType().getName().equals(
                FilterTypeDocument.getSelectionModel().getSelectedItem().getName()));
        TableDocument.setItems(arr_filtered);
        }
    }

    @FXML
    void OnBtnFilterDate(ActionEvent event) {
        LocalDate start_date = DateStart.getValue();
        LocalDate end_date = DateEnd.getValue();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (start_date != null && end_date != null){
            Date start = java.sql.Date.valueOf(start_date);
            Date end = java.sql.Date.valueOf(end_date);
            ObservableList<DocumentInfo> arr_filtered = getDocumentlist().filtered(item -> {
                try {
                    Date temp_date = format.parse(item.getDocument().getDate_time());
                    return temp_date.after(start) && temp_date.before(end);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            });
            TableDocument.setItems(arr_filtered);
        }else {
            warningAlert("Ошибка","Введите дату");
        }
    }

    public void onBtnUpdateDocument(ActionEvent event) {
        refreshTableDocument();
    }

    public void GetInformationAboutDoc(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 1){
            DocumentInfo info = TableDocument.getSelectionModel().getSelectedItem();
            if(info != null){
                client.sendMessage(Commands.GET_CONTEXT_DOCUMENT_BY_ID.toString());
                client.sendObject(info.getDocument().getIdDocument());
                ObservableList<DocumentContentInfo> list = FXCollections.observableArrayList((ArrayList<DocumentContentInfo>)client.readObject());
                initTableDocumentContent();
                TableContentDocument.setItems(list);
            }
        }
    }

    public void onBtnComeBack(ActionEvent event) {
        goHomeScene(event, user);
    }
}
