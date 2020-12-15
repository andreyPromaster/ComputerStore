package model;

import java.io.Serializable;

public class ProductInStore implements Serializable {
    Store store;
    CategoryProduct category;
    Nomeclature product;

    @Override
    public String toString() {
        return ""+getStore().getPrice() +" Оставшееся кол-во "+ getStore().getAmount();
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public CategoryProduct getCategory() {
        return category;
    }

    public void setCategory(CategoryProduct category) {
        this.category = category;
    }

    public Nomeclature getProduct() {
        return product;
    }

    public void setProduct(Nomeclature product) {
        this.product = product;
    }
}
