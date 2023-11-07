package com.example.talk2friends;

import android.content.Context;
import android.content.Intent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

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

    public static void switchActivityMeetings(Context context, String email) {
        System.out.println("Switching activity to Meetings...");
        Intent intent = new Intent(context, MeetingsActivity.class);
        intent.putExtra("email", email);
        context.startActivity(intent);
    }

    public static void switchActivityProfile( Context context, String email) {
        System.out.println("Switching activity to Profile...");
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("email", email);
        context.startActivity(intent);
    }

    public static void switchActivitySignUp(Context context) {
        System.out.println("Switching activity to Sign Up...");
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }

    public static void switchActivityLogin(Context context) {
        // print to console
        System.out.println("Switching activity to Login...");

        // switch activity to SignUpActivity
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void switchActivityValidationCode(Context context, String username, String hash, String validationCode) {
        // print to console
        System.out.println("Switching activity to ValidationCode...");

        // switch activity to ValidationCodeActivity
        Intent intent = new Intent(context, ValidationCodeActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("hash",hash);
        intent.putExtra("validationCode",validationCode);
        context.startActivity(intent);
    }


}

