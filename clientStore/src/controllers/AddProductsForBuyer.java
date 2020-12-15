package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Nomeclature;
import model.ProductInStore;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AddProductsForBuyer extends AdditionProductStrategy {

    public AddProductsForBuyer(Client client){
    super(client);
    }

    @Override
    public ObservableList<ProductInStore> getProductsForAdding() {
        client.sendMessage(Commands.GET_ALL_PRODUCT_IN_STORE.toString());
        return FXCollections.observableArrayList((ArrayList<ProductInStore>)client.readObject());

    }

    @Override
    public ObservableList<Customer> getCustomersForSelecting() {
        client.sendMessage(Commands.SELECT_CUSTOMER_BY_STATUS.toString());
        client.sendObject(SettingsConst.STATUS_FOR_BUYER);
        return  FXCollections.observableArrayList((ArrayList<Customer>)client.readObject());
    }
}
