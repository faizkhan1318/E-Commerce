package com.example.ecomm;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.ecomm.Login.getEncryptedPassword;

    public class Signup {
        String name;
        String pass;
        String email;
        String mobile;
        String address;

        public static void customerSignup(String userName, String userMobile, String userEmail, String userPass, String userAddress) throws NoSuchAlgorithmException {
            String encryptedPassword = getEncryptedPassword(userPass);
            String query = "insert into customer(name, email, mobile, password)values('"+userName+"','"+userEmail+"','"+userMobile+"','"+encryptedPassword+"')";
            DatabaseConnection dbConn = new DatabaseConnection();
            dbConn.insertUpdate(query);
        }
    }
