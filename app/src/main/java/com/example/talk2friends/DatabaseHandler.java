package com.example.talk2friends;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

interface OnReceiveMeetings {
    void onReceiveMeetings(ArrayList<Meeting> meetings);
}

interface ICustomFirebaseClient_Meetings {
    public void getMeetings(OnReceiveMeetings onReceiveMeetings);
    public void saveMeeting(Meeting meeting);
}

interface ICustomFirebaseClient_Update {
    public <T> void saveValue(String firstLayer, String thirdLayer, String matchValue, String targetField, T newValue);
}

class CustomFirebaseClient_Meetings implements ICustomFirebaseClient_Meetings {
    // get meetings from database
    FirebaseDatabase database = FirebaseDatabase.getInstance(Utils.FIREBASE_URL);
    DatabaseReference firstLayerRef = database.getReference("meetings");

    public void getMeetings(OnReceiveMeetings onReceiveMeetings) {

        firstLayerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Meeting>> t = new GenericTypeIndicator<ArrayList<Meeting>>() {
                };
                ArrayList<Meeting> meetings = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    //    private String name = "";
                    //    private String topic = "";
                    //    private String time = "";
                    //    private String location = "";
                    //    private ArrayList<String> participants = new ArrayList<String>();
                    //    private String key = "";
                    String tempName = data.child("name").getValue(String.class);
                    String tempTopic = data.child("topic").getValue(String.class);
                    String tempTime = data.child("time").getValue(String.class);
                    String tempLocation = data.child("location").getValue(String.class);
                    String tempKey = data.child("key").getValue(String.class);
                    ArrayList<String> tempParticipants = new ArrayList<>();
                    for (DataSnapshot participant : data.child("participants").getChildren()) {
                        tempParticipants.add(participant.getValue(String.class));
                    }

                    Meeting tempMeeting = new Meeting(tempName, tempTopic, tempTime, tempLocation, tempParticipants, tempKey);
                    meetings.add(tempMeeting);
                }

//                callback.onCallback(meetings);
                onReceiveMeetings.onReceiveMeetings(meetings);
                return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    public void saveCode(String code) {
//        codesRef.push().setValue(code);
//    }
    public void saveMeeting(Meeting meeting) {
        firstLayerRef.push().setValue(meeting);
    }
}

class CustomFirebaseClient_Update implements ICustomFirebaseClient_Update {
    public <T> void saveValue(String firstLayer, String thirdLayer, String matchValue, String targetField, T newValue) {
        // push to database
        FirebaseDatabase database = FirebaseDatabase.getInstance(Utils.FIREBASE_URL);
        DatabaseReference firstLayerRef = database.getReference(firstLayer);

        firstLayerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child(thirdLayer).exists()) {
                        if (data.child(thirdLayer).getValue().toString().equals(matchValue)) {
                            data.child(targetField).getRef().setValue(newValue);
                            return;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


public class DatabaseHandler {

    /**
     * This method creates firebase instance and references, then checks if thirdLayer is unique,
     *
     * @param firstLayer the first layer of firebase path (e.g., "users")
     * @param thirdLayer the third layer of firebase path (e.g., "email")
     * @param callback   the callback function, use "data" to get data from DataSnapshot
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
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child(thirdLayer).exists()) {
                        if (data.child(thirdLayer).getValue().toString().equals(matchValue)) {
                            callback.onCallback(data);
                            return;
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
     * This method retrieves User object based on email, and puts in callback function
     *
     * @param email    the email of the user to retrieve
     * @param callback the callback function, use "user" to get User object
     */
    public static void getUser(String email, UserCallback callback) {
        getValue("users", "email", email, new DataSnapshotCallback() {
            @Override
            public void onCallback(DataSnapshot data) {
                if (data == null) {
                    System.out.println("ERROR: User not found!!!!");
                    callback.onCallback(null);
                    return;
                }
                //    private String email = "";
                //    private String password = "";
                //    private String name = "";
                //    private String age = "";
                //    private String affiliation = "";
                //    private String type = "";
                //    private ArrayList<String> friendsList = new ArrayList<String>();
                //    private ArrayList<String> interests = new ArrayList<>();
                //    private String key = "";
                String tempEmail = data.child("email").getValue(String.class);
                String tempPassword = data.child("password").getValue(String.class);
                String tempName = data.child("name").getValue(String.class);
                String tempAge = data.child("age").getValue(String.class);
                String tempAffiliation = data.child("affiliation").getValue(String.class);
                String tempType = data.child("type").getValue(String.class);
                String tempKey = data.child("key").getValue(String.class);
                ArrayList<String> tempFriends = new ArrayList<>();
                for (DataSnapshot friend : data.child("friends").getChildren()) {
                    tempFriends.add(friend.getValue(String.class));
                }
                ArrayList<String> tempInterests = new ArrayList<>();
                System.out.println("user interests: ");
                for (DataSnapshot interest : data.child("interests").getChildren()) {
                    tempInterests.add(interest.getValue(String.class));
                    System.out.println(interest.getValue(String.class));
                }
                ArrayList<Meeting> tempMeetings = new ArrayList<>();
                for (DataSnapshot meeting : data.child("meetings").getChildren()) {
                    tempMeetings.add(meeting.getValue(Meeting.class));
                }

                User tempUser = new User(tempEmail, tempPassword, tempName, tempAge, tempAffiliation, tempType, tempFriends, tempInterests, tempMeetings, tempKey);

                callback.onCallback(tempUser);
            }
        });
    }

    /**
     * This method retrieves all Users in database, and puts in callback function
     *
     * @param callback the callback function, use "user" to get User object
     */
    public static void getUsers(UsersCallback callback) {
        // get all users from database
        FirebaseDatabase database = FirebaseDatabase.getInstance(Utils.FIREBASE_URL);
        DatabaseReference firstLayerRef = database.getReference("users");
        firstLayerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> users = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String tempEmail = data.child("email").getValue(String.class);
                    String tempPassword = data.child("password").getValue(String.class);
                    String tempName = data.child("name").getValue(String.class);
                    String tempAge = data.child("age").getValue(String.class);
                    String tempAffiliation = data.child("affiliation").getValue(String.class);
                    String tempType = data.child("type").getValue(String.class);
                    String tempKey = data.child("key").getValue(String.class);
                    ArrayList<String> tempFriends = new ArrayList<>();
                    for (DataSnapshot friend : data.child("friendsList").getChildren()) {
                        tempFriends.add(friend.getValue(String.class));
                    }
                    ArrayList<String> tempInterests = new ArrayList<>();
                    for (DataSnapshot interest : data.child("interests").getChildren()) {
                        tempInterests.add(interest.getValue(String.class));
                    }
                    ArrayList<Meeting> tempMeetings = new ArrayList<>();
                    for (DataSnapshot meeting : data.child("meetings").getChildren()) {
                        tempMeetings.add(meeting.getValue(Meeting.class));
                    }

                    User tempUser = new User(tempEmail, tempPassword, tempName, tempAge, tempAffiliation, tempType, tempFriends, tempInterests, tempMeetings, tempKey);
                    users.add(tempUser);

                    System.out.println("user email: " + tempUser.getEmail());
                }

                callback.onCallback(users);
                return;

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
     * @param callback   the callback function, use "value" to perform actions
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

                for (DataSnapshot data : dataSnapshot.getChildren()) {
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
     *
     * @param firstLayer the first layer of firebase path (e.g., "users")
     * @param value      the value to push to database
     * @return nothing
     */
    public static void pushNewValue(String firstLayer, Object value) {
        // push to database
        FirebaseDatabase database = FirebaseDatabase.getInstance(Utils.FIREBASE_URL);
        DatabaseReference firstLayerRef = database.getReference(firstLayer);

        DatabaseReference pushRef = firstLayerRef.push();
        String key = pushRef.getKey();
        // if value is a User object, set the key
        if (value instanceof User) {
            ((User) value).setKey(key);
        } else if (value instanceof Meeting) {
            ((Meeting) value).setKey(key);
        }

        pushRef.setValue(value);
    }

    /**
     * This method pushes Object value to database
     *
     * @param userEmail the user's email
     * @param friendEmail the friend's email to add to user's friend list
     * @return nothing
     */
    public static void pushNewFriend(String userEmail, String friendEmail) {
        // get current friends
        DatabaseHandler.getUser(userEmail, new UserCallback() {
            @Override
            public void onCallback(User user) {
                user.addFriend(friendEmail);

                // push to database
                CustomFirebaseClient_Update client = new CustomFirebaseClient_Update();
                DatabaseHandler.updateValue("users", "email", user.getEmail(), "friends", user.getFriends(), client);
            }
        });

        // add friend to hashmap of {Name:email}
    }

    /**
     * This method updates value in database
     *
     * @param firstLayer  the first layer of firebase path (e.g., "users")
     * @param thirdLayer  the third layer of firebase path (e.g., "email")
     * @param matchValue  the value to match in thirdLayer={(email = matchValue), etc.}
     * @param targetField the third layer of firebase path to change to newValue (e.g., "age")
     * @param newValue    the new value to update targetField to
     */
    public static <T> void updateValue(String firstLayer, String thirdLayer, String matchValue, String targetField, T newValue, ICustomFirebaseClient_Update client) {
        client.saveValue(firstLayer, thirdLayer, matchValue, targetField, newValue);
    }

    public static void getMeetings(MeetingsCallback callback, ICustomFirebaseClient_Meetings client) {
        client.getMeetings((meetings -> {
            callback.onCallback(meetings);
        }));
    }

}