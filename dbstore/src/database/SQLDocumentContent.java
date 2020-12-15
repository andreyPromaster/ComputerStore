package database;

import model.*;

import java.util.ArrayList;

public class SQLDocumentContent {
    private static SQLDocumentContent instance;
    private DBConnection dbConnection;

    private SQLDocumentContent() {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLDocumentContent getInstance() {
        if (instance == null) {
            instance = new SQLDocumentContent();
        }
        return instance;
    }
    public ArrayList<DocumentContentInfo> selectContentById(int id){
        String str = "select iddocument_content, amount, price, n.name, iddocument from  document_content " +
                "join nomenclature n on n.idnomenclature = document_content.idnomenclature " +
                "where iddocument = "+ id +";";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<DocumentContentInfo> listContext = new ArrayList<>();

        for (String[] items: result){
            DocumentContentInfo contentInfo = new DocumentContentInfo();
            Nomeclature product = new Nomeclature();
            DocumentContent content = new DocumentContent();

            content.setIdContent(Integer.parseInt(items[0]));
            content.setAmount(Integer.parseInt(items[1]));
            content.setPrice(Double.parseDouble(items[2]));
            product.setName(items[3]);
            content.setIdDocument(Integer.parseInt(items[4]));

            contentInfo.setContent(content);
            contentInfo.setProduct(product);

            listContext.add(contentInfo);
        }
        return listContext;
    }

    public void insertDocumentContext(DocumentContent doc) {
        String str = "insert into document_content(amount, price, iddocument, idnomenclature) VALUES("
                + doc.getAmount()
                + ", "
                + doc.getPrice()
                + ", "
                + doc.getIdDocument()
                + ", "
                + doc.getIdNomenclature()
                + ");";
        dbConnection.execute(str);
}
    public ArrayList<DataStatistic> getDataDayPriceForMouth(){
        String str = "select date_format(datetime, '%e'), sum(amount*price) from document_content " +
                "join consignment_note cn on cn.idconsignment_note = document_content.iddocument " +
                "where iddocument_type = 2 and month(datetime) = month(now()) group by day(datetime);";
        return selectDataStatistic(str);

    }

    public ArrayList<DataStatistic> getDataDayPriceForPastMouth(){
        String str = "select date_format(datetime, '%e'), sum(amount*price) from document_content " +
                "join consignment_note cn on cn.idconsignment_note = document_content.iddocument " +
                "where iddocument_type = 2 and month(datetime) = month(DATE_ADD(NOW(), INTERVAL -1 MONTH)) " +
                "      group by day(datetime);";
        return selectDataStatistic(str);

    }

    public ArrayList<DataStatistic> selectDataStatistic(String str){

        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<DataStatistic> data = new ArrayList<>();

        for (String[] items: result){
            DataStatistic temp = new DataStatistic();
            temp.setDay(Integer.parseInt(items[0]));
            temp.setPrice(Double.parseDouble(items[1]));

            data.add(temp);
        }
        return data;
    }

    public ArrayList<DataStatistic> getDataSumByCategory(){
        String str = "select sum(price * amount), c.name from document_content " +
                "join nomenclature n on n.idnomenclature = document_content.idnomenclature " +
                "join category c on n.idcategory = c.idcategory " +
                "group by c.idcategory;";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<DataStatistic> data = new ArrayList<>();

        for (String[] items: result){
            DataStatistic temp = new DataStatistic();
            temp.setPrice(Double.parseDouble(items[0]));
            temp.setName_category(items[1]);
            data.add(temp);
        }
        return data;
    }
}
