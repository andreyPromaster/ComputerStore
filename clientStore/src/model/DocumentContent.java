package model;
import java.io.Serializable;
import java.util.Objects;

public class DocumentContent  implements Serializable{
    private int idContent;
    private int amount;
    private double price;
    private int idDocument;
    private int idNomenclature;
    private int idStore;

    @Override
    public String toString() {
        return "DocumentContent{" +
                "idContent=" + idContent +
                ", amount=" + amount +
                ", price=" + price +
                ", idDocument=" + idDocument +
                ", idNomenclature=" + idNomenclature +
                ", idStore=" + idStore +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentContent that = (DocumentContent) o;
        return idContent == that.idContent &&
                amount == that.amount &&
                Double.compare(that.price, price) == 0 &&
                idDocument == that.idDocument &&
                idNomenclature == that.idNomenclature &&
                idStore == that.idStore;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idContent, amount, price, idDocument, idNomenclature, idStore);
    }

    public int getIdContent() {
        return idContent;
    }

    public void setIdContent(int idContent) {
        this.idContent = idContent;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }

    public int getIdNomenclature() {
        return idNomenclature;
    }

    public void setIdNomenclature(int idNomenclature) {
        this.idNomenclature = idNomenclature;
    }

    public int getIdStore() {
        return idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }
}
