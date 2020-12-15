package database;

import java.util.ArrayList;
import model.User;

public class SQLUsers {
    private static SQLUsers instance;
    private DBConnection dbConnection;

    private SQLUsers() {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLUsers getInstance() {
        if (instance == null) {
            instance = new SQLUsers();
        }
        return instance;
    }

    public boolean findUser(User obj){
        String str = "SELECT * From user Where username = '" + obj.getUsername() +
                "' and password = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        String status = "";
        for (String[] item: result)
            status = item[1];
        return !status.isBlank();
    }

    public void insert(User obj) {
        String str = "INSERT INTO user (username, password, role) VALUES('" + obj.getUsername()
                + "', '" + obj.getPassword() + "', " + obj.getRole()  +")";
        dbConnection.execute(str);
    }

    public int getUserId(User obj){
        String str = "SELECT userid From user Where username = '" + obj.getUsername() +
                "' and password = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        System.out.println(result);
        return Integer.parseInt(result.get(0)[0]);
    }

    public User getUser(User obj){
        String str = "SELECT * From user Where username = '" + obj.getUsername() +
                "' and password = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);

        User user = new User();
        user.setIdUser(Integer.parseInt(result.get(0)[4]));
        user.setUsername(result.get(0)[0]);
        user.setPassword(result.get(0)[1]);
        user.setRole(Integer.parseInt(result.get(0)[3]));
        System.out.println(user);
        return user;

    }

}
