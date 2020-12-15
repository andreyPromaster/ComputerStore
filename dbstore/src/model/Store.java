package model;

import java.io.Serializable;
import java.util.Objects;

public class Store  implements Serializable {
    private int idStore;
    private double price;
    private int amount;
    private int idNomenclature;

    @Override
    public String toString() {
        return "Store{" +
                "idStore=" + idStore +
                ", price=" + price +
                ", amount=" + amount +
                ", idNomenclature=" + idNomenclature +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return idStore == store.idStore &&
                Double.compare(store.price, price) == 0 &&
                amount == store.amount &&
                idNomenclature == store.idNomenclature;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStore, price, amount, idNomenclature);
    }

    public int getIdStore() {
        return idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getIdNomenclature() {
        return idNomenclature;
    }

    public void setIdNomenclature(int idNomenclature) {
        this.idNomenclature = idNomenclature;
    }
}
