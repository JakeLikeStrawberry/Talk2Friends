package com.example.talk2friends;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseHandler {

    /**
     * This method creates firebase instance and references, then checks if thirdLayer is unique,
     * @param firstLayer the first layer of firebase path (e.g., "users")
     * @param thirdLayer the third layer of firebase path (e.g., "email")
     * @param callback the callback function, use "data" to get data from DataSnapshot
     * @param matchValue the value to match in thirdLayer={(email = matchValue), etc.}
     * @param callback
     * @return nothing, but callback data is the DataSnapshot where data.child(thirdLayer) value == matchValue
     */
    public static void getValue(String firstLayer, String thirdLayer, String matchValue, DataSnapshotCallback callback) {
        // validate login:
        // check email and sha2 hash of password against firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance(Utils.FIREBASE_URL);
        DatabaseReference firstLayerRef = database.getReference(firstLayer);
        firstLayerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if (data.child(thirdLayer).exists()) {
                        if (data.child(thirdLayer).getValue().toString().equals(matchValue)) {
                            callback.onCallback(data);
                        }

                    }
                }
                callback.onCallback(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * This method creates firebase instance and references, then checks if thirdLayer is unique,
     *
     * @param firstLayer the first layer of firebase path (e.g., "users")
     * @param thirdLayer the third layer of firebase path (e.g., "email")
     * @param callback the callback function, use "value" to perform actions
     * @return nothing, callback value is "true" if unique, "false" if not unique
     */
    public static void checkIsUnique(String firstLayer, String thirdLayer, String value, Callback callback) {
        // check that email is not already in database
        // for checking if email is already in database
        FirebaseDatabase database = FirebaseDatabase.getInstance(Utils.FIREBASE_URL);
        DatabaseReference firstLayerRef = database.getReference(firstLayer);
        firstLayerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if (data.child(thirdLayer).exists()) {
                        if (data.child(thirdLayer).getValue().toString().equals(value)) {
                            // found duplicate
                            callback.onCallback("false");
                            return;
                        }
                    }
                }

                callback.onCallback("true");
                return;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * This method pushes Object value to database
     * @param firstLayer the first layer of firebase path (e.g., "users")
     * @param value the value to push to database
     * @return nothing
     */
    public static void pushToDatabase(String firstLayer, Object value) {
        // push to database
        FirebaseDatabase database = FirebaseDatabase.getInstance(Utils.FIREBASE_URL);
        DatabaseReference firstLayerRef = database.getReference(firstLayer);
        firstLayerRef.push().setValue(value);
    }

    public static void doUntilUnique(String firstLayer, String thirdLayer, Callback callback) {

    }


}
