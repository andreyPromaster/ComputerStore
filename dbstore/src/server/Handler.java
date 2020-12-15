package server;

import database.*;
import model.*;

import java.io.*;
import java.net.Socket;

public class Handler  implements Runnable {
    protected Socket clientSocket = null;
    ObjectInputStream input;
    ObjectOutputStream output;
    private SQLFactory sqlFactory;

    public Handler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        sqlFactory = new SQLFactory();
    }

    @Override
    public void run() {
        try {
            input = new ObjectInputStream(clientSocket.getInputStream());
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            while (true) {
                String choice = input.readObject().toString();
                Commands response = Commands.valueOf(choice);
                switch (response) {
                    case CHECK_AND_SAVE_NEW_USER -> {
                        System.out.println("Запрос на добавление пользователя, клиент: " + clientSocket.getInetAddress().toString());
                        SQLUsers sqlUsers = sqlFactory.getUsers();
                        User user = (User) input.readObject();
                        if (!sqlUsers.findUser(user)) {
                            sqlUsers.insert(user);
                            output.writeObject("OK");
                        } else {
                            output.writeObject("This user exists");
                        }
                    }
                    case INSERT_INFO_ABOUT_USER -> {
                        System.out.println("Запрос на добавление информации об пользователе, клиент: " + clientSocket.getInetAddress().toString());
                        SQLUserInfo sqlInfo = sqlFactory.getUserInfo();
                        SQLUsers sqlUser = sqlFactory.getUsers();
                        User user = (User) input.readObject();
                        UserInfo info = (UserInfo) input.readObject();
                        info.setUserid(sqlUser.getUserId(user));
                        sqlInfo.insert(info);
                        output.writeObject("OK");
                    }
                    case IS_USER_EXIST -> {
                        User user = (User) input.readObject();
                        if (sqlFactory.getUsers().findUser(user)) {
                            output.writeObject("OK");
                        } else {
                            output.writeObject("User doesnt exist");
                        }
                    }
                    case INSERT_OR_UPDATE_PRODUCT -> {
                        SQLNomenclature sqlNomenclature = sqlFactory.getNomenclature();
                        Boolean saveCommand = (Boolean) input.readObject();
                        Nomeclature product_for_save = (Nomeclature) input.readObject();

                        if (saveCommand) { //  save new product
                            sqlNomenclature.insert(product_for_save);
                        } else { // edit
                            sqlNomenclature.update(product_for_save);
                        }
                        output.writeObject("OK");

                    }
                    case DELETE_PRODUCT -> {
                        SQLNomenclature sqlNomenclature = sqlFactory.getNomenclature();
                        int idProduct = (int) input.readObject();
                        sqlNomenclature.delete(idProduct);
                    }
                    case GET_ALL_PRODUCT -> {
                        SQLNomenclature sqlNomenclature = sqlFactory.getNomenclature();
                        output.writeObject(sqlNomenclature.selectAllProduct());

                    }
                    case GET_ALL_CATEGORY -> {
                        SQLCategory sqlCategory = sqlFactory.getCategory();
                        output.writeObject(sqlCategory.selectAllCategoryProduct());
                    }
                    case FIND_PRODUCT_BY -> {
                        SQLNomenclature sqlNomenclature = sqlFactory.getNomenclature();
                        int id_command = (int) input.readObject();
                        String str = (String) input.readObject();
                        output.writeObject(sqlNomenclature.findBy(str, id_command));
                    }
                    case FILTER_PRODUCT_BY_CATEGORY -> {
                        SQLNomenclature sqlNomenclature = sqlFactory.getNomenclature();
                        int id_category = (int) input.readObject();
                        output.writeObject(sqlNomenclature.selectProductByCategory(id_category));
                    }
                    case GET_ALL_INFO_DOCUMENT -> {
                        SQLDocument sqlDocument = sqlFactory.getDocument();
                        output.writeObject(sqlDocument.selectAllInfoAboutDocument());
                    }
                    case GET_ALL_TYPE_DOCUMENT -> {
                        SQLDocumentType sqlDocumentType = sqlFactory.getDocumentType();
                        output.writeObject(sqlDocumentType.selectALLTypes());

                    }
                    case GET_CONTEXT_DOCUMENT_BY_ID -> {
                        SQLDocumentContent sqlDocumentContent = sqlFactory.getDocumentContent();
                        int idDocument = (int) input.readObject();
                        output.writeObject(sqlDocumentContent.selectContentById(idDocument));
                    }
                    case GET_ALL_CUSTOMER -> {
                        SQLCustomer sqlCustomer = sqlFactory.getCustomer();
                        output.writeObject(sqlCustomer.selectAll());
                    }
                    case SELECT_CUSTOMER_BY_NAME -> {
                        SQLCustomer sqlCustomer = sqlFactory.getCustomer();
                        String name = (String) input.readObject();
                        output.writeObject(sqlCustomer.selectByName(name));
                    }
                    case SELECT_CUSTOMER_BY_ACCOUNT -> {
                        SQLCustomer sqlCustomer = sqlFactory.getCustomer();
                        String name = (String) input.readObject();
                        output.writeObject(sqlCustomer.selectByAccount(name));
                    }
                    case INSERT_CUSTOMER -> {
                        SQLCustomer sqlCustomer = sqlFactory.getCustomer();
                        Customer customer = (Customer) input.readObject();
                        sqlCustomer.insert(customer);
                        output.writeObject("OK");
                    }
                    case UPDATE_CUSTOMER -> {
                        SQLCustomer sqlCustomer = sqlFactory.getCustomer();
                        Customer customer = (Customer) input.readObject();
                        sqlCustomer.update(customer);
                        output.writeObject("OK");
                    }
                    case GET_ALL_PRODUCT_IN_STORE -> {
                        SQLStore sqlStore = sqlFactory.getStore();
                        output.writeObject(sqlStore.selectProductInStore());
                    }
                    case SELECT_PRODUCT_IN_STORE_BY_PRICE ->{
                        SQLStore sqlStore = sqlFactory.getStore();
                        Double startPrice = (Double) input.readObject();
                        Double endPrice = (Double) input.readObject();
                        output.writeObject(sqlStore.selectProductInStoreByPrice(startPrice, endPrice));
                    }
                    case SELECT_PRODUCT_IN_STORE_BY_NAME -> {
                        SQLStore sqlStore = sqlFactory.getStore();
                        String name = (String) input.readObject();
                        output.writeObject(sqlStore.selectProductInStoreByName(name));
                    }
                    case SELECT_ALL_STATE_OF_DOCUMENT -> {
                        SQLDocumentState sqlDocumentState = sqlFactory.getDocumentState();
                        output.writeObject(sqlDocumentState.selectStateOfDocument());
                    }
                    case SELECT_CUSTOMER_BY_STATUS -> {
                        SQLCustomer sqlCustomer = sqlFactory.getCustomer();
                        int status = (int) input.readObject();
                        output.writeObject(sqlCustomer.selectByStatus(status));
                    }
                    case INSERT_DOCUMENT -> {
                        SQLDocument sqlDocument = sqlFactory.getDocument();
                        Document doc = (Document) input.readObject();
                        output.writeObject(sqlDocument.insertDocument(doc));
                    }
                    case INSERT_DOCUMENT_CONTEXT -> {
                        SQLDocumentContent sqlDocumentContent = sqlFactory.getDocumentContent();
                        DocumentContent doc = (DocumentContent) input.readObject();
                        sqlDocumentContent.insertDocumentContext(doc);
                        output.writeObject("OK");
                    }
                    case GET_USER -> {
                        SQLUsers sqlUser = sqlFactory.getUsers();
                        User user = (User) input.readObject();
                        output.writeObject(sqlUser.getUser(user));
                    }
                    case GET_SUM_FOR_PAST_MOUTH -> {
                        SQLDocumentContent sqlDocumentContent = sqlFactory.getDocumentContent();
                        output.writeObject(sqlDocumentContent.getDataDayPriceForPastMouth());
                    }
                    case GET_SUM_FOR_THIS_MOUTH -> {
                        SQLDocumentContent sqlDocumentContent = sqlFactory.getDocumentContent();
                        output.writeObject(sqlDocumentContent.getDataDayPriceForMouth());
                    }
                    case GET_DATA_SUM_PRICE_PRODUCT_BY_CATEGORY -> {
                        SQLDocumentContent sqlDocumentContent = sqlFactory.getDocumentContent();
                        output.writeObject(sqlDocumentContent.getDataSumByCategory());
                    }
                    case UPDATE_AMOUNT_PRODUCT_IN_STORE -> {
                        SQLStore sqlStore = sqlFactory.getStore();
                        Store store = (Store) input.readObject();
                        sqlStore.updateAmount(store);
                    }
                    case GET_REPORT -> {
                        SQLStore sqlStore = sqlFactory.getStore();
                        sendFile(sqlStore.getReport("ProductInStore.jrxml"));
                    }
                    case GET_SALES -> {
                        SQLStore sqlStore = sqlFactory.getStore();
                        sendFile(sqlStore.getReport("SalesInMonth.jrxml"));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void sendFile(File file){

        byte [] mybytearray  = new byte [(int)file.length()];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
            os = clientSocket.getOutputStream();
            System.out.println("Sending " + "(" + mybytearray.length + " bytes)");
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
            System.out.println("Done.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bis != null) bis.close();
                if (fis != null) fis.close();
                if (os != null) fis.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
