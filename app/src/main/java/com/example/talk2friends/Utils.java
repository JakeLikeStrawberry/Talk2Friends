package com.example.talk2friends;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    // App password added and created using link: https://myaccount.google.com/apppasswords
        // after already having been signed into 310codingchameleongs@gmail account
        // regular gmail password: chameleonsthatcode

    public static final  String OUR_EMAIL = "310codingchameleons@gmail.com";
    public static final String OUR_EMAIL_PASSWORD = "sblbimliktklgzet";

    public static final String FIREBASE_URL = "https://talk2friends-78719-default-rtdb.firebaseio.com";

    public static String getHash(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
