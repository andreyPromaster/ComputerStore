package model;
import java.io.Serializable;
import java.util.Objects;

public class Document  implements Serializable{
    private String date_time;
    private int idDocument;
    private int userId;
    private int idType;
    private int idState;
    private int idCustomer;

    @Override
    public String toString() {
        return "Document{" +
                "date_time='" + date_time + '\'' +
                ", idDocument=" + idDocument +
                ", userId=" + userId +
                ", idType=" + idType +
                ", idState=" + idState +
                ", idCustomer=" + idCustomer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return idDocument == document.idDocument &&
                userId == document.userId &&
                idType == document.idType &&
                idState == document.idState &&
                idCustomer == document.idCustomer &&
                Objects.equals(date_time, document.date_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date_time, idDocument, userId, idType, idState, idCustomer);
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdState() {
        return idState;
    }

    public void setIdState(int idState) {
        this.idState = idState;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }
}
