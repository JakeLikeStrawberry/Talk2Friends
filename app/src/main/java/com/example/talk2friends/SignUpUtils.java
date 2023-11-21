package com.example.talk2friends;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;

interface OnReceiveCodes {
    void onReceiveCodes(ArrayList<String> code);
}

interface ICustomFirebaseClient {
    public void getCodes(OnReceiveCodes onReceiveCodes);
    public void saveCode(String code);
}

class CustomFirebaseClient implements ICustomFirebaseClient {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://talk2friends-78719-default-rtdb.firebaseio.com/");
    DatabaseReference codesRef = database.getReference("codes");

    public void getCodes(OnReceiveCodes onReceiveCodes) {
        codesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> codes = new ArrayList<String>();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if (data.child("code").exists()) {
                        codes.add(data.child("code").getValue().toString());
                    }
                }
                onReceiveCodes.onReceiveCodes(codes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void saveCode(String code) {
        codesRef.push().setValue(code);
    }
}



public class SignUpUtils   {

//    public static final  String OUR_EMAIL = "310codingchameleons@gmail.com";

    public static boolean checkSignUpErrors(String email, String password, TextView errorText) {
        // Error with hash
        String hash = Utils.getHash(password);
        if (hash.equals("")) {
            System.out.println("Error: unable to create hash!");
            errorText.setText("Please add a password!\\nPlease try again.");
            return false;
        }

        // Error with empty password
        if (password.equals("")) {
            System.out.println("Error: password cannot be empty!");
            errorText.setText("Please add a password!\\nPlease try again.");
            return false;
        }

        // Error with empty email
        if (email.equals("")) {
            System.out.println("Error: email cannot be empty!");
            errorText.setText("Please add an email!\\nPlease try again.");
            return false;
        }

        // if email doesn't end in "@usc.edu"
        if (!email.endsWith("@usc.edu")) {
            System.out.println("Error: email must end in @usc.edu!");
            errorText.setText("Please add a valid USC email!\\nPlease try again.");
            return false;
        }

        // Error with empty username
        if (email.equals("@usc.edu")) {
            System.out.println("Error: email cannot be empty!");
            errorText.setText("Please add an email!\\nPlease try again.");
            return false;
        }

        // Error with password length > 30
        if (password.length() > 30) {
            System.out.println("Error: password is too long!");
            errorText.setText("Password is too long (> 30)!\\nPlease try again.");
            return false;
        }

        // Error with password length < 6
        if (password.length() < 6) {
            System.out.println("Error: password is too short!");
            errorText.setText("Password is too short (< 6)!\\nPlease try again.");
            return false;
        }

        // Error with email length > 64 + 8
        if (email.length() > 64 + 8) {
            System.out.println("Error: email is too long!");
            errorText.setText("Email is too long (> 72)!\\nPlease try again.");
            return false;
        }

        System.out.println("No signup errors found.");
        return true;
    }

    public static String generateValidationCode() {
        // generate 4-character alphanumeric code to send
        // 48 ~ 57: ASCII digits
        // 65 ~ 90: ASCII uppercase letters
        String code = "";
        for (int i = 0; i < 4; i++) {
            int random = (int) (Math.random() * 36);
            if (random < 10) {
                // random is a digit
                code += (char) (random + 48);
            } else {
                // random is a letter
                code += (char) (random + 55);
            }
        }

        return code;
    }

    public static void getUniqueValidationCode(Callback callback, ICustomFirebaseClient customFirebaseClient) {
        customFirebaseClient.getCodes((codes) -> {
            // check if code is already in database\
            // from: https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method/47853774
            // implement this method to call asynchronously
            // for checking if code is already in database
            String newCode = generateValidationCode();
            for (String code : codes) {
             if (code.equals(newCode)) {
                 newCode = generateValidationCode();
             }
            }
            customFirebaseClient.saveCode(newCode);
            callback.onCallback(newCode);
            return;
        });
    }

//    public static String getHash(String s) {
//        try {
//            // Create MD5 Hash
//            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
//            digest.update(s.getBytes());
//            byte messageDigest[] = digest.digest();
//
//            // Create Hex String
//            StringBuffer hexString = new StringBuffer();
//            for (int i=0; i<messageDigest.length; i++)
//                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
//            return hexString.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }



}

