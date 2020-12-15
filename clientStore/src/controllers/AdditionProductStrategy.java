package controllers;

import javafx.collections.ObservableList;
import model.Customer;
import model.ProductInStore;

import java.util.ArrayList;

public abstract class AdditionProductStrategy {
    protected Client client;
    protected int command;

    public AdditionProductStrategy(Client client) {
        this.client = client;
    }

    public abstract ObservableList<ProductInStore> getProductsForAdding();
    public abstract ObservableList<Customer> getCustomersForSelecting();

}
