package model;

import java.io.Serializable;

public class DocumentContentInfo implements Serializable {
    public Nomeclature product;
    public DocumentContent content;

    @Override
    public String toString() {
        return "DocumentContentInfo{" +
                "product=" + product +
                ", content=" + content +
                '}';
    }

    public Nomeclature getProduct() {
        return product;
    }

    public void setProduct(Nomeclature product) {
        this.product = product;
    }

    public DocumentContent getContent() {
        return content;
    }

    public void setContent(DocumentContent content) {
        this.content = content;
    }
}
