package database;

import model.CategoryProduct;
import model.Nomeclature;

import java.util.ArrayList;

public class SQLCategory {
    private static SQLCategory instance;
    private DBConnection dbConnection;

    private SQLCategory() {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLCategory getInstance() {
        if (instance == null) {
            instance = new SQLCategory();
        }
        return instance;
    }

    public ArrayList<CategoryProduct> selectAllCategoryProduct() {
        String str = "SELECT * From Category";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<CategoryProduct> listProduct = new ArrayList<>();

        for (String[] items: result){
            CategoryProduct product = new CategoryProduct();
            product.setIdCategory(Integer.parseInt(items[0]));
            product.setName(items[1]);
            listProduct.add(product);
        }
        return listProduct;
    }
}
