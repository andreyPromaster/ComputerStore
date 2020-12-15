package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.Nomeclature;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FillChangeCustomerController extends BaseController implements Initializable {
    @FXML
    private TextField FieldAddr;

    @FXML
    private TextField FieldName;

    @FXML
    private TextField FieldPhone;

    @FXML
    private TextField FieldAccount;

    @FXML
    private TextField FieldBIK;

    @FXML
    private TextField FieldKPP;

    @FXML
    private TextField FieldINN;

    @FXML
    private ComboBox<String> ListRoleCustomer;

    @FXML
    private Button btnSave;

    @FXML
    void onBtnSave(ActionEvent event) {
        Customer customer = new Customer();
        if(isFieldIncorrect(FieldAddr, 45)
                && isFieldIncorrect(FieldName,45)
                && isFieldIncorrect(FieldPhone, 13)
                && isFieldIncorrect(FieldAccount, 50)
                && isFieldIncorrect(FieldBIK, 15)
                && isFieldIncorrect(FieldKPP, 15)
                && isFieldIncorrect(FieldINN,12)
        ){

            customer.setFull_name(FieldName.getText());
            customer.setAddr(FieldAddr.getText());
            customer.setSettlement_account(FieldAccount.getText());
            customer.setPhone(FieldPhone.getText());
            customer.setINN(FieldINN.getText());
            customer.setKPP(FieldKPP.getText());
            customer.setBIK(FieldBIK.getText());
            customer.setStatus(ListRoleCustomer.getSelectionModel().getSelectedIndex());

            if(!saveCommand) { //  edit product
                client.sendMessage(Commands.UPDATE_CUSTOMER.toString());
                customer.setIdCustomer(customer_model.getIdCustomer());
            } else {
                client.sendMessage(Commands.INSERT_CUSTOMER.toString());
            }
            client.sendObject(customer);
            try {
                if (client.readMessage().equals("OK")) {
                    successfulAlert("Ура", "Изменения сохранены. Обновите таблицу!");
                } else {
                    warningAlert("Что-то пошло не так", "Ошибка");
                }
            }
            catch (IOException exIO){
                exIO.printStackTrace();
            }
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } else{
            warningAlert("Ошибка","Слишком много символов в ячейке." +
                    "Проверьте, пожалуйста!");
        }
    }
    private boolean saveCommand;
    private Customer customer_model;

    public void setSaveCommand(boolean saveCommand) {
        this.saveCommand = saveCommand;
    }

    public void setCustomer(Customer customer) {
        this.customer_model = customer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(saveCommand==false) { //for change product
        FieldAddr.setText(customer_model.getAddr());
        FieldAccount.setText(customer_model.getSettlement_account());
        FieldName.setText(customer_model.getFull_name());
        FieldPhone.setText(customer_model.getPhone());
        FieldBIK.setText(customer_model.getBIK());
        FieldINN.setText(customer_model.getINN());
        FieldKPP.setText(customer_model.getKPP());
        ListRoleCustomer.getSelectionModel().select(customer_model.getStatus());
        }
        ListRoleCustomer.getSelectionModel().select(0);
    }
}
