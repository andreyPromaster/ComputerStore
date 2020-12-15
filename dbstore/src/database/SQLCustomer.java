package database;

import model.Customer;
import model.Nomeclature;

import java.util.ArrayList;

public class SQLCustomer {
    private static SQLCustomer instance;
    private DBConnection dbConnection;

    private SQLCustomer() {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLCustomer getInstance() {
        if (instance == null) {
            instance = new SQLCustomer();
        }
        return instance;
    }
    public ArrayList<Customer> selectAll() {
        String str = "SELECT * From customer";
        return getResultFromSelect(str);
    }

    public ArrayList<Customer> selectByName(String name) {
        String str = "SELECT * From customer where full_name like '%"+name+"%'";
        return getResultFromSelect(str);
    }

    public ArrayList<Customer> selectByAccount(String name){
        String str = "SELECT * From customer where settlement_account like '%"+name+"%'";
        return getResultFromSelect(str);
    }

    private ArrayList<Customer> getResultFromSelect(String query){
        ArrayList<String[]> result = dbConnection.getArrayResult(query);
        ArrayList<Customer> listCustomer = new ArrayList<>();
        for (String[] items: result){
            Customer customer = new Customer();
            customer.setIdCustomer(Integer.parseInt(items[0]));
            customer.setAddr(items[1]);
            customer.setFull_name(items[2]);
            customer.setPhone(items[3]);
            customer.setSettlement_account(items[4]);
            customer.setBIK(items[5]);
            customer.setKPP(items[6]);
            customer.setINN(items[7]);
            customer.setStatus(Integer.parseInt(items[8]));
            listCustomer.add(customer);
        }
        return listCustomer;
    }
    public void update(Customer obj) {
        String str = "UPDATE customer SET addr='"
                + obj.getAddr()
                + "', full_name='"
                + obj.getFull_name()
                + "', phone='"
                + obj.getPhone()
                + "' , settlement_account='"
                + obj.getSettlement_account()
                + "' , BIK='"
                + obj.getBIK()
                + "' , KPP='"
                + obj.getKPP()
                + "' , INN='"
                + obj.getINN()
                + "' , status="
                + obj.getStatus()
                + " where idcustomer = "
                + obj.getIdCustomer()
                +";";
        dbConnection.execute(str);
    }


    public void insert(Customer obj) {
        String str = "insert into customer(addr, full_name, phone, settlement_account, BIK, KPP, INN, status) VALUES('" +
                obj.getAddr() + "', '" + obj.getFull_name() + "', '" + obj.getPhone() +
                "', '" + obj.getSettlement_account() + "', '" + obj.getBIK() + "', '" +
                obj.getKPP() + "', '" + obj.getINN() + "', " +obj.getStatus() +")";
        dbConnection.execute(str);
    }

        public ArrayList<Customer> selectByStatus(int status){
            String str = "SELECT * From customer where status = "+ status+";";
            return getResultFromSelect(str);
        }

}
