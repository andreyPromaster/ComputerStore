package controllers;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    private Socket clientSocket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private static Client instance;
    private String message;

    public Client(String ipAddress, String port){
        try {
            clientSocket = new Socket(ipAddress, Integer.parseInt(port));
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean getFile(){
//        InputStream in = null;
//        OutputStream out = null;
//
//        try {
//            in = clientSocket.getInputStream();
//        } catch (IOException ex) {
//            System.out.println("Can't get socket input stream. ");
//        }
//        try {
//            out = new FileOutputStream("report.pdf");
//        } catch (FileNotFoundException ex) {
//            System.out.println("File not found. ");
//        }
//
//        byte[] bytes = new byte[16*1024];
//
//        int count;
//        try {
//                while ((count = in.read(bytes)) != -1) {
//                    out.write(bytes, 0, count);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        try {
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
        byte [] mybytearray  = new byte [16*1024];
        InputStream is = clientSocket.getInputStream();
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_");
        Date date = new Date();
        fos = new FileOutputStream(dateFormat.format(date).toString() +"report.html");
        bos = new BufferedOutputStream(fos);
        bytesRead = is.read(mybytearray,0,mybytearray.length);
        current = bytesRead;

//        do {
//            bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
//            if(bytesRead >= 0) current += bytesRead;
//        } while(bytesRead > -1);

        bos.write(mybytearray, 0 , current);
        bos.flush();
        System.out.println("File "
                + " downloaded (" + current + " bytes read)");
        return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
            if (fos != null) {
                fos.close();
            }
            if (bos != null) {
                bos.close();
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void sendMessage(String message){
        try {
            outStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendObject(Object object){
        try {
            outStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readMessage() throws IOException {
        try {
            message = (String) inStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    public Object readObject(){
        Object object = new Object();
        try {
            object = inStream.readObject();
        } catch (ClassNotFoundException | IOException e) {

            e.printStackTrace();
        }
        return object;
    }

    public void close() {
        try {
            clientSocket.close();
            inStream.close();
            outStream.close();
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Client getInstance(String ipAddress, String port) {
        if (instance == null) {
            instance = new Client(ipAddress, port);
        }
        return instance;
    }

}
