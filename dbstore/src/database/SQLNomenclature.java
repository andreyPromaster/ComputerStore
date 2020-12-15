package database;

import model.Nomeclature;

import java.util.ArrayList;

public class SQLNomenclature {
    private static SQLNomenclature instance;
    private DBConnection dbConnection;

    private SQLNomenclature() {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLNomenclature getInstance() {
        if (instance == null) {
            instance = new SQLNomenclature();
        }
        return instance;
    }

    public Nomeclature selectProduct(int idProduct) {
        String str = "SELECT * FROM nomenclature WHERE idPassenger = " + idProduct;
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        Nomeclature product = new Nomeclature();
        for (String[] items : result) {
            product.setIdNomeclature(Integer.parseInt(items[0]));
            product.setName(items[1]);
            product.setArticle(items[2]);
            product.setComment(items[4]);
            product.setDescription(items[5]);
        }
        return product;
    }

    public ArrayList<Nomeclature> selectAllProduct() {
        String str = "SELECT * From nomenclature";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<Nomeclature> listProduct = new ArrayList<>();

        for (String[] items: result){
            Nomeclature product = new Nomeclature();
            product.setIdNomeclature(Integer.parseInt(items[0]));
            product.setName(items[1]);
            product.setArticle(items[2]);
            product.setComment(items[4]);
            product.setDescription(items[5]);
            listProduct.add(product);
        }
        return listProduct;
    }

    public ArrayList<Nomeclature> selectProductByCategory(int id) {
        String str = "SELECT * From nomenclature where idcategory = "+id+";";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<Nomeclature> listProduct = new ArrayList<>();

        for (String[] items: result){
            Nomeclature product = new Nomeclature();
            product.setIdNomeclature(Integer.parseInt(items[0]));
            product.setName(items[1]);
            product.setArticle(items[2]);
            product.setComment(items[4]);
            product.setDescription(items[5]);
            listProduct.add(product);
        }
        return listProduct;
    }

    public void update(Nomeclature obj) {
        String str = "UPDATE nomenclature SET name='"
                + obj.getName()
                + "', article='"
                + obj.getArticle()
                + "', comment='"
                + obj.getComment()
                + "' , description='"
                + obj.getDescription()
                + "', idcategory = "
                + obj.getIdCategory()
                + " where idnomenclature = "
                + obj.getIdNomeclature()
                +";";
        dbConnection.execute(str);
    }

    public void delete(int idProduct) {
        String str = "DELETE FROM nomenclature WHERE idnomenclature = " + idProduct;
        dbConnection.execute(str);
    }

    public void insert(Nomeclature obj) {
        String str = "INSERT INTO nomenclature (name, article, comment, description, idCategory) VALUES('" +
                obj.getName() + "', '" + obj.getArticle() + "', '" + obj.getComment() +
                "', '" + obj.getDescription() + "', " + obj.getIdCategory() +")";
        dbConnection.execute(str);
    }

    public ArrayList<Nomeclature> findBy(String find_str, int id_command) {
        String str = "SELECT * From nomenclature where ";
        if(id_command==0){
        str = str.concat("article");

        } else if (id_command == 1){
            str = str.concat("name");
        }
        else{
            str = str.concat("description");
        }
        String str1 = " like '%"+find_str+"%';";
        ArrayList<String[]> result = dbConnection.getArrayResult(str + str1);
        ArrayList<Nomeclature> listProduct = new ArrayList<>();

        for (String[] items: result){
            Nomeclature product = new Nomeclature();
            product.setIdNomeclature(Integer.parseInt(items[0]));
            product.setName(items[1]);
            product.setArticle(items[2]);
            product.setComment(items[4]);
            product.setDescription(items[5]);
            listProduct.add(product);
        }
        return listProduct;
    }
}
