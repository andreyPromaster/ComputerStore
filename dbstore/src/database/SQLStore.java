package database;

import model.*;
import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.engine.design.JRDesignReportFont;

import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SQLStore {
    private static SQLStore instance;
    private DBConnection dbConnection;

    private SQLStore() {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLStore getInstance() {
        if (instance == null) {
            instance = new SQLStore();
        }
        return instance;
    }
    public File getReport(String path){

        JasperReport jasperReport = null;
        try {
            jasperReport = JasperCompileManager.compileReport(path);
        } catch (JRException e) {
            e.printStackTrace();
        }

        Connection conn = dbConnection.getConnect();

        // Parameters for report
        Map<String, Object> parameters = new HashMap<String, Object>();

        JasperPrint print = null;
        try {
            print = JasperFillManager.fillReport(jasperReport,
                    parameters, conn);
        } catch (JRException e) {
            e.printStackTrace();
        }
//        JRReportFont font = new JRDesignReportFont();
//        font.setPdfFontName( "arial.ttf");
//        font.setPdfEncoding("UTF-8");
//        font.setPdfEmbedded(true);
//        print.setDefaultFont(font);


        try {
            JasperExportManager.exportReportToHtmlFile(print, "report.html");
        } catch (JRException e) {
            e.printStackTrace();
        }
        File pdf = null;
        pdf = new File("report.html");
        return pdf;


//        // Make sure the output directory exists.
//        File outDir = new File("C:/jasperoutput");
//        outDir.mkdirs();
//
////        // PDF Exportor.
//       JRPdfExporter exporter = new JRPdfExporter();
////
//        ExporterInput exporterInput = new SimpleExporterInput(print);
//        // ExporterInput
//        exporter.setExporterInput(exporterInput);
//
//        // ExporterOutput
//        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
//                "C:/jasperoutput/FirstJasperReport.pdf");
//        // Output
//        exporter.setExporterOutput(exporterOutput);
//
//        //
//        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
//        exporter.setConfiguration(configuration);
//        try {
//            exporter.exportReport();
//        } catch (JRException e) {
//            e.printStackTrace();
//        }

    }

    public ArrayList<ProductInStore> selectProductInStoreByPrice(Double startPrice, Double endPrice){
        String str = "select idstore, price, amount, c.name, n.name, n.article, n.idnomenclature, n.idcategory from store " +
                "join nomenclature n on n.idnomenclature = store.idnomenclature " +
                "join category c on c.idcategory = n.idcategory where amount>0 and price > "+ startPrice +
                " and price < "+endPrice + ";";
        return getResultFromSelect(str);
    }

    public ArrayList<ProductInStore> selectProductInStoreByName(String name){
        String str = "select idstore, price, amount, c.name, n.name, n.article, n.idnomenclature, n.idcategory from store " +
                "join nomenclature n on n.idnomenclature = store.idnomenclature " +
                "join category c on c.idcategory = n.idcategory where amount>0 and n.name like '%"+ name +
                "%' ;";
        return getResultFromSelect(str);
    }

    public ArrayList<ProductInStore> selectProductInStore(){
        String str = "select idstore, price, amount, c.name, n.name, n.article, n.idnomenclature, n.idcategory from store " +
                "join nomenclature n on n.idnomenclature = store.idnomenclature " +
                "join category c on c.idcategory = n.idcategory where amount>0;";
        return getResultFromSelect(str);
    }

    public void updateAmount(Store store){
        String str = "update store set amount = "+ store.getAmount() +" where idstore = " + store.getIdStore() + ";";
        dbConnection.execute(str);
    }
    private ArrayList<ProductInStore> getResultFromSelect(String query){
        ArrayList<String[]> result = dbConnection.getArrayResult(query);
        ArrayList<ProductInStore> listProduct = new ArrayList<>();
        for (String[] items: result){
            ProductInStore product = new ProductInStore();

            Store store = new Store();
            Nomeclature nomeclature = new Nomeclature();
            CategoryProduct categoryProduct = new CategoryProduct();

            store.setIdStore(Integer.parseInt(items[0]));
            store.setPrice(Double.parseDouble(items[1]));
            store.setAmount(Integer.parseInt(items[2]));
            store.setIdNomenclature(Integer.parseInt(items[6]));
            categoryProduct.setName(items[3]);
            nomeclature.setName(items[4]);
            nomeclature.setArticle(items[5]);
            nomeclature.setIdNomeclature(Integer.parseInt(items[6]));
            nomeclature.setIdCategory(Integer.parseInt(items[7]));

            product.setCategory(categoryProduct);
            product.setProduct(nomeclature);
            product.setStore(store);

            listProduct.add(product);
        }
        return listProduct;
    }
}
