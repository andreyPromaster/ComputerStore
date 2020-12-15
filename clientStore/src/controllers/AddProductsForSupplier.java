package controllers;

import com.sun.scenario.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Nomeclature;
import model.ProductInStore;

import java.util.ArrayList;

public class AddProductsForSupplier extends AdditionProductStrategy {

    public AddProductsForSupplier(Client client){
        super(client);
    }
    @Override
    public ObservableList<ProductInStore> getProductsForAdding() {
        client.sendMessage(Commands.GET_ALL_PRODUCT.toString());

        ArrayList<Nomeclature> temp = (ArrayList<Nomeclature>)client.readObject();

        ArrayList <ProductInStore> products = new ArrayList<>();
        for(Nomeclature item: temp){
            ProductInStore productInStore = new ProductInStore();
            productInStore.setProduct(item);
            products.add(productInStore);
        }

        return FXCollections.observableArrayList(products);
    }

    @Override
    public ObservableList<Customer> getCustomersForSelecting() {
        client.sendMessage(Commands.SELECT_CUSTOMER_BY_STATUS.toString());
        client.sendObject(SettingsConst.STATUS_FOR_SUPPLIER);
        return FXCollections.observableArrayList((ArrayList<Customer>)client.readObject());

    }
}
