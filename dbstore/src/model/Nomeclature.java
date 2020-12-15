package model;

import java.io.Serializable;
import java.util.Objects;

public class Nomeclature   implements Serializable {
    private int idNomeclature;
    private String name;
    private  String article;
    private int idCategory;
    private String comment;
    private String description;

    @Override
    public String toString() {
        return "Nomeclature{" +
                "idNomeclature=" + idNomeclature +
                ", name='" + name + '\'' +
                ", article='" + article + '\'' +
                ", idCategory=" + idCategory +
                ", comment='" + comment + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nomeclature that = (Nomeclature) o;
        return idNomeclature == that.idNomeclature &&
                idCategory == that.idCategory &&
                Objects.equals(name, that.name) &&
                Objects.equals(article, that.article) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNomeclature, name, article, idCategory, comment, description);
    }

    public int getIdNomeclature() {
        return idNomeclature;
    }

    public void setIdNomeclature(int idNomeclature) {
        this.idNomeclature = idNomeclature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
