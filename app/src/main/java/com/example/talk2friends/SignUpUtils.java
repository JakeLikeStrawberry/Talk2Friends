package com.example.talk2friends;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignUpUtils   {

//    public static final  String OUR_EMAIL = "310codingchameleons@gmail.com";

    public static String checkSignUpErrors(String password, TextView errorText) {
        // Error with hash
        String hash = Utils.getHash(password);
        if (hash.equals("")) {
            System.out.println("Error: unable to create hash!");
            errorText.setText("Please add a password!\\nPlease try again.");
            return "";
        }

        // Error with empty password
        if (password.equals("")) {
            System.out.println("Error: password cannot be empty!");
            errorText.setText("Please add a password!\\nPlease try again.");
            return "";
        }

        System.out.println("No signup errors found.");
        return hash;
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

    public static void getUniqueValidationCode(Callback callback) {

        // check if code is already in database\
        // from: https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method/47853774
        // implement this method to call asynchronously
        // for checking if code is already in database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://talk2friends-78719-default-rtdb.firebaseio.com/");
        DatabaseReference codesRef = database.getReference("codes");
        codesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("hihihi");
                String code = generateValidationCode();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if (data.child("code").exists()) {
                        while (data.child("code").getValue().toString().equals(code)) {
                            System.out.println("Generated code already in database! Creating new code...");
                            code = generateValidationCode();
                        }
                        callback.onCallback(code);

                        // add code instance to database
                        codesRef.push().setValue(code);
                        return;
                    }
                }
                System.out.println("datasnapshot child code does not exist! Initializing for the first time...");
                callback.onCallback(code);

                // add code instance to database
                codesRef.push().setValue(code);
                return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
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

