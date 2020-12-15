package database;

import model.*;

import java.util.ArrayList;

public class SQLDocumentState {
    private static SQLDocumentState instance;
    private DBConnection dbConnection;

    private SQLDocumentState() {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLDocumentState getInstance() {
        if (instance == null) {
            instance = new SQLDocumentState();
        }
        return instance;
    }
    public ArrayList<DocumentState> selectStateOfDocument(){
        String str = "select * from document_state";
        return getResultFromSelect(str);
    }

    private ArrayList<DocumentState> getResultFromSelect(String query){
        ArrayList<String[]> result = dbConnection.getArrayResult(query);
        ArrayList<DocumentState> listState = new ArrayList<>();
        for (String[] items: result){
            DocumentState state = new DocumentState();
            state.setIdState(Integer.parseInt(items[0]));
            state.setName(items[1]);
            listState.add(state);
        }
        return listState;
    }
}
