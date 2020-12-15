package database;

public interface AbstractFactory {
    public  SQLUsers getUsers();
    public  SQLUserInfo getUserInfo();
    public  SQLCustomer getCustomer();
    public  SQLDocument getDocument();
    public  SQLNomenclature getNomenclature();
    public  SQLDocumentContent getDocumentContent();
    public  SQLCategory getCategory();
    public  SQLDocumentState getDocumentState();
    public  SQLDocumentType getDocumentType();
    public  SQLStore getStore();
}
