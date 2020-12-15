package model;

import java.io.Serializable;

public class DataStatistic implements Serializable {
    int day;
    Double price;
    String name_category;

    @Override
    public String toString() {
        return "DataStatistic{" +
                "day=" + day +
                ", price=" + price +
                ", name_category='" + name_category + '\'' +
                '}';
    }

    public String getName_category() {
        return name_category;
    }

    public void setName_category(String name_category) {
        this.name_category = name_category;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
