package database;

import model.User;
import model.UserInfo;

public class SQLUserInfo {
    private static SQLUserInfo instance;
    private DBConnection dbConnection;

    private SQLUserInfo() {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLUserInfo getInstance() {
        if (instance == null) {
            instance = new SQLUserInfo();
        }
        return instance;
    }

    public void insert(UserInfo obj) {
        String str = "INSERT INTO info (email, phone_number, date_of_birth, first_name, second_name, userid) " +
                "VALUES('" + obj.getEmail()
                + "', '" + obj.getPhone_number() + "', " + obj.getDate_of_birth()
                +  ", '" + obj.getFirst_name() +  "', '" + obj.getSecond_name()+  "', " + obj.getUserid() +")";
        dbConnection.execute(str);
    }

}
