package database;

import model.*;

import java.util.ArrayList;

public class SQLDocumentType {
    private static SQLDocumentType instance;
    private DBConnection dbConnection;

    private SQLDocumentType() {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLDocumentType getInstance() {
        if (instance == null) {
            instance = new SQLDocumentType();
        }
        return instance;
    }
    public ArrayList<DocumentType> selectALLTypes(){
        String str = "select * from document_type;";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<DocumentType> listType = new ArrayList<>();

        for (String[] items: result){
            DocumentType documentType = new DocumentType() ;
            documentType.setIdType(Integer.parseInt(items[0]));
            documentType.setName(items[1]);

            listType.add(documentType);
        }
        return listType;
    }
}
