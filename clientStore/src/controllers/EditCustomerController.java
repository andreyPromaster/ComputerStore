package controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Customer;
import model.DocumentInfo;
import model.Nomeclature;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditCustomerController extends BaseController implements Initializable, ISetUser {
    @FXML
    private TableView<Customer> TableCustomer;

    @FXML
    private TableColumn<Customer, Integer> ColumnID;

    @FXML
    private TableColumn<Customer, String> ColumnAdr;

    @FXML
    private TableColumn<Customer, String> ColumnFullName;

    @FXML
    private TableColumn<Customer, String> ColumnPhone;

    @FXML
    private TableColumn<Customer, String> ColumnAccount;

    @FXML
    private TableColumn<Customer, String> ColumnBIK;

    @FXML
    private TableColumn<Customer, String> ColumnKPP;

    @FXML
    private TableColumn<Customer, String> ColumnINN;

    @FXML
    private ComboBox<String> ListTypeCustomer;

    @FXML
    private TextField FieldFindByName;

    @FXML
    private TextField FieldFindByAccount;

    private User user;
    private ObservableList<Customer> customersList;

    public void setCustomersList(ObservableList<Customer> customersList) {
        this.customersList = customersList;
    }

    public ObservableList<Customer> getCustomersList() {
        return customersList;
    }

    public void setModelUser(User user){
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();
    }

    private void initTableCustomer() {
        ColumnID.setCellValueFactory(new PropertyValueFactory<>("idCustomer"));
        ColumnAdr.setCellValueFactory(new PropertyValueFactory<>("addr"));
        ColumnFullName.setCellValueFactory(new PropertyValueFactory<>("full_name"));
        ColumnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        ColumnAccount.setCellValueFactory(new PropertyValueFactory<>("settlement_account"));
        ColumnBIK.setCellValueFactory(new PropertyValueFactory<>("BIK"));
        ColumnKPP.setCellValueFactory(new PropertyValueFactory<>("KPP"));
        ColumnINN.setCellValueFactory(new PropertyValueFactory<>("INN"));
    }

    private void refreshTable() {
        initTableCustomer();
        client.sendMessage(Commands.GET_ALL_CUSTOMER.toString());
        ObservableList<Customer> list = FXCollections.observableArrayList((ArrayList<Customer>)client.readObject());
        TableCustomer.setItems(list);
        setCustomersList(list);
    }

    @FXML
    void FilterCustomerByType(ActionEvent event) {
        if(ListTypeCustomer.getSelectionModel().getSelectedItem().equals("Любая категория")){
            TableCustomer.setItems(getCustomersList());
        }
        else {   // 0 - supplier, 1- buyer
            ObservableList<Customer> arr_filtered = getCustomersList().filtered(item -> item.getStatus() ==
                    ListTypeCustomer.getSelectionModel().getSelectedIndex());
            TableCustomer.setItems(arr_filtered);
        }
    }

    @FXML
    void onBtnAddCustomer(ActionEvent event) {
        showEditFormCustomer(null, true);
    }

    @FXML
    void onBtnChangeCustomer(ActionEvent event) {
        Customer selected = TableCustomer.getSelectionModel().getSelectedItem();
        if(selected != null){
            showEditFormCustomer( selected, false);
        } else {
            warningAlert("Ошибка","Выбери запись для изменения");
        }
    }

    @FXML
    void onBtnFindByAccount(ActionEvent event) {
        client.sendMessage(Commands.SELECT_CUSTOMER_BY_ACCOUNT.toString());
        String name = FieldFindByAccount.getText();
        client.sendObject(name);
        ObservableList<Customer> list = FXCollections.observableArrayList((ArrayList<Customer>)client.readObject());
        TableCustomer.setItems(list);
    }

    @FXML
    void onBtnFindByName(ActionEvent event) {
        client.sendMessage(Commands.SELECT_CUSTOMER_BY_NAME.toString());
        String name = FieldFindByName.getText();
        client.sendObject(name);
        ObservableList<Customer> list = FXCollections.observableArrayList((ArrayList<Customer>)client.readObject());
        TableCustomer.setItems(list);
    }

    private void showEditFormCustomer(Customer customer, boolean command){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/FillChangeCustomer.fxml"));

            FillChangeCustomerController controller = new FillChangeCustomerController();
            controller.setCustomer(customer);
            controller.setSaveCommand(command);
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

    public void onBtnUpdateCustomerTable(ActionEvent event) {
        refreshTable();
    }

    public void onBtnComeBack(ActionEvent event) {
        goHomeScene(event, user);
    }
}
