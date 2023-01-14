package com.example.supplychain;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;

public class Login {
    DatabaseConnection dbconn  = new DatabaseConnection();

    //encryption
    public static byte[] getSHA(String input){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    String getEncryptedPassword(String password){
        String encryptedPassword = "";
        BigInteger number = new BigInteger(1,getSHA(password));
        StringBuilder hexString = new StringBuilder(number.toString(16));
        encryptedPassword = hexString.toString();
        return encryptedPassword;
    }
    public boolean customerLogin(String emil,String password){
        password = getEncryptedPassword(password);
        try{
            String query = String.format("SELECT * FROM customer WHERE email = '%s' and password = '%s'",emil,password);
            ResultSet rs = dbconn.getQueryTable(query);
            if(rs == null){
                return false;
            }
            else return rs.next();//this will return true or false
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

//    public static void main(String[] args) {
//        System.out.println(getEncryptedPassword("abc"));
//    }
}
