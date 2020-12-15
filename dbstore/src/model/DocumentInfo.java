package model;

import java.io.Serializable;

public class DocumentInfo implements Serializable {

 public Document document;
 public DocumentState state;
 public DocumentType type;
 public User user;

    @Override
    public String toString() {
        return "DocumentInfo{" +
                "document=" + document +
                ", state=" + state +
                ", type=" + type +
                ", user=" + user +
                ", customer=" + customer +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer customer;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public DocumentState getState() {
        return state;
    }

    public void setState(DocumentState state) {
        this.state = state;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
