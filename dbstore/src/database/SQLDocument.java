package database;

import model.*;

import java.util.ArrayList;

public class SQLDocument {
    private static SQLDocument instance;
    private DBConnection dbConnection;

    private SQLDocument() {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLDocument getInstance() {
        if (instance == null) {
            instance = new SQLDocument();
        }
        return instance;
    }
    public ArrayList<DocumentInfo> selectAllInfoAboutDocument(){
        String str = "select idconsignment_note, datetime, full_name, state_name, username,type_name from consignment_note " +
                "join customer c on consignment_note.idcustomer = c.idcustomer " +
                "join document_state ds on consignment_note.iddocument_state = ds.iddocument_state " +
                "join user u on u.userid = consignment_note.userid " +
                "join document_type dt on consignment_note.iddocument_type = dt.iddocument_type;";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<DocumentInfo> listDocument = new ArrayList<>();

        for (String[] items: result){
            DocumentInfo documentInfo = new DocumentInfo();
            Document document = new Document();
            Customer customer = new Customer();
            DocumentType documentType = new DocumentType() ;
            DocumentState documentState = new DocumentState();
            User user = new User();

            document.setIdDocument(Integer.parseInt(items[0]));
            document.setDate_time(items[1]);
            customer.setFull_name(items[2]);
            documentState.setName(items[3]);
            user.setUsername(items[4]);
            documentType.setName(items[5]);

            documentInfo.setDocument(document);
            documentInfo.setCustomer(customer);
            documentInfo.setState(documentState);
            documentInfo.setType(documentType);
            documentInfo.setUser(user);

            listDocument.add(documentInfo);
        }
        return listDocument;
    }

    public int insertDocument(Document doc) {
        String str = "insert into consignment_note(userid, iddocument_type, iddocument_state, idcustomer) values ("
                + doc.getUserId()
                + ", "
                + doc.getIdType()
                + ", "
                + doc.getIdState()
                + " , "
                + doc.getIdCustomer()
                + ");";
        dbConnection.execute(str);

        String str1 = "Select idconsignment_note from consignment_note where "
                + " userid = "
                + doc.getUserId()
                + " and iddocument_type = "
                + doc.getIdType()
                + " and iddocument_state = "
                + doc.getIdState()
                + " and idcustomer = "
                + doc.getIdCustomer()
                +" order by datetime desc;";
        ArrayList<String[]> result = dbConnection.getArrayResult(str1);
        return Integer.parseInt(result.get(0)[0]);
    }
}
