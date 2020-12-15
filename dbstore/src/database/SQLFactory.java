package database;

public class SQLFactory implements AbstractFactory {
    @Override
    public SQLCategory getCategory() {
        return SQLCategory.getInstance();
    }

    @Override
    public SQLCustomer getCustomer() {
        return SQLCustomer.getInstance();
    }

    @Override
    public SQLDocument getDocument() {
        return SQLDocument.getInstance();
    }

    @Override
    public SQLDocumentContent getDocumentContent() {
        return SQLDocumentContent.getInstance();
    }

    @Override
    public SQLDocumentState getDocumentState() {
        return SQLDocumentState.getInstance();
    }

    @Override
    public SQLDocumentType getDocumentType() {
        return SQLDocumentType.getInstance();
    }

    @Override
    public SQLNomenclature getNomenclature() {
        return  SQLNomenclature.getInstance();
    }

    @Override
    public SQLStore getStore() {
        return SQLStore.getInstance();
    }

    @Override
    public SQLUserInfo getUserInfo() {
        return SQLUserInfo.getInstance();
    }

    @Override
    public SQLUsers getUsers() {
        return SQLUsers.getInstance();
    }
}
